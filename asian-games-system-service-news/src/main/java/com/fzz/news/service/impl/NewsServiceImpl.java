package com.fzz.news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzz.api.config.RabbitmqDelayConfig;
import com.fzz.common.enums.NewsStatusEnum;
import com.fzz.common.enums.ResponseStatusEnum;
import com.fzz.common.exception.CustomException;
import com.fzz.common.utils.SnowFlakeUtil;
import com.fzz.model.bo.AddNewsBO;
import com.fzz.model.entity.News;
import com.fzz.model.vo.QueryNewsVO;
import com.fzz.news.mapper.NewsMapper;
import com.fzz.news.service.NewsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    public Page<QueryNewsVO> pageNews(Integer pageNumber, Integer pageSize, Date startDate, Date endDate, String keyword, Integer status) {
        Page<News> newsPage=new Page<>(pageNumber,pageSize);
        LambdaQueryWrapper<News> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(status!=null,News::getArticleStatus,status);
        queryWrapper.le(endDate!=null,News::getUpdateTime,endDate);
        queryWrapper.ge(startDate!=null,News::getUpdateTime,startDate);
        queryWrapper.like(StringUtils.isNotBlank(keyword),News::getTitle,keyword);
        this.page(newsPage,queryWrapper);
        Page<QueryNewsVO> queryNewsVOPage=new Page<>();
        BeanUtils.copyProperties(newsPage,queryNewsVOPage,"records");
        List<QueryNewsVO> queryNewsVOS = newsPage.getRecords().stream().map((item -> {
            QueryNewsVO queryNewsVO = new QueryNewsVO();
            BeanUtils.copyProperties(item, queryNewsVO);
            return queryNewsVO;
        })).collect(Collectors.toList());
        queryNewsVOPage.setRecords(queryNewsVOS);
        return queryNewsVOPage;
    }

    @Override
    @Transactional
    public void saveNews(AddNewsBO addNewsBO) {
        Integer isAppoint = addNewsBO.getIsAppoint();
        News news=new News();
        BeanUtils.copyProperties(addNewsBO,news);
        long id = new SnowFlakeUtil(12, 13).getNextId();
        news.setId(id);
        news.setCreateTime(new Date());
        news.setUpdateTime(new Date());

        //即时发布
        if(isAppoint==0){
            news.setArticleStatus(1);
            news.setPublishTime(new Date());
        } else {
            news.setArticleStatus(3);
        }
        boolean res = this.save(news);
        if(!res){
            throw new CustomException(ResponseStatusEnum.NEWS_CREATE_ERROR);
        }
        publishDelayedNews(news);
    }

    @Override
    @Transactional
    public void updateDelayedArticle(Long id) {
        LambdaUpdateWrapper<News> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.eq(News::getId,id);
        updateWrapper.set(News::getArticleStatus, NewsStatusEnum.PUBLISHED.code());
        updateWrapper.set(News::getIsAppoint,0);
        this.update(updateWrapper);
    }


    public void publishDelayedNews(News news){
        Date publishTime = news.getPublishTime();
        int delayTime= (int) (publishTime.getTime()-new Date().getTime());
        MessagePostProcessor messagePostProcessor=new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                message.getMessageProperties().setDelay(delayTime);
                return message;
            }
        };
        rabbitTemplate.convertAndSend(RabbitmqDelayConfig.EXCHANGE_DELAY,"delay.news.publish",news.getId(),messagePostProcessor);
    }
}
