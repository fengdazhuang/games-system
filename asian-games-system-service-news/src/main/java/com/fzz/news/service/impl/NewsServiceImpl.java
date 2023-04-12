package com.fzz.news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzz.model.entity.News;
import com.fzz.model.vo.QueryNewsVO;
import com.fzz.news.mapper.NewsMapper;
import com.fzz.news.service.NewsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {


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
}
