package com.fzz.news.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzz.api.BaseController;
import com.fzz.api.controller.NewsControllerApi;
import com.fzz.common.enums.NewsStatusEnum;
import com.fzz.common.enums.ResponseStatusEnum;
import com.fzz.common.result.ReturnResult;
import com.fzz.model.bo.AddNewsBO;
import com.fzz.model.entity.News;
import com.fzz.model.vo.QueryNewsVO;
import com.fzz.news.service.NewsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class NewsController extends BaseController implements NewsControllerApi {

    @Autowired
    private NewsService newsService;


    @Override
    public ReturnResult addNews(AddNewsBO addNewsBO) {
        Integer isAppoint = addNewsBO.getIsAppoint();
        News news=new News();
        BeanUtils.copyProperties(addNewsBO,news);
        news.setCreateTime(new Date());
        news.setUpdateTime(new Date());

        //即时发布
        if(isAppoint==0){
            news.setArticleStatus(1);
            boolean res = newsService.save(news);
            if(res){
                return ReturnResult.ok();
            }
            return ReturnResult.error(ResponseStatusEnum.NEWS_CREATE_ERROR);
        } else {
            news.setArticleStatus(3);

        }

        return null;
    }

    @Override
    public ReturnResult queryNews(Integer pageNumber, Integer pageSize,
                                  Date startDate, Date endDate, String keyword, Integer status) {
        if(pageNumber<=0){
            pageNumber=COMMON_START_PAGE;
        }
        if(pageSize<=0){
            pageSize=COMMON_PAGE_SIZE;
        }
        Page<QueryNewsVO> queryNewsVOPage = newsService.pageNews(pageNumber,pageSize,startDate,endDate,keyword,status);
        return ReturnResult.ok(queryNewsVOPage);
    }
}
