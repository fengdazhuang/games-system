package com.fzz.news.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzz.api.BaseController;
import com.fzz.api.controller.news.NewsControllerApi;
import com.fzz.common.enums.ResponseStatusEnum;
import com.fzz.common.result.ReturnResult;
import com.fzz.model.bo.AddNewsBO;
import com.fzz.model.vo.QueryNewsVO;
import com.fzz.news.service.NewsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class NewsController extends BaseController implements NewsControllerApi {

    @Autowired
    private NewsService newsService;


    @Override
    public ReturnResult addNews(AddNewsBO addNewsBO) {
        Integer articleType = addNewsBO.getArticleType();
        String articleCover = addNewsBO.getArticleCover();
        if(articleType==2){
            addNewsBO.setArticleCover(null);
        }else if(articleType==1&&StringUtils.isBlank(articleCover)){
            return ReturnResult.error(ResponseStatusEnum.NEWS_COVER_IS_NULL);
        }
        newsService.saveNews(addNewsBO);
        return ReturnResult.ok();

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

    @Override
    public ReturnResult deleteNews(Long id) {
        boolean res=newsService.removeNewsById(id);
        if(res){
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.NEWS_DELETE_ERROR);
    }

    @Override
    public ReturnResult updateNews(AddNewsBO addNewsBO) {
        boolean res = newsService.updateNews(addNewsBO);
        return null;
    }

    @Override
    public ReturnResult withdrawNews(Long id) {
        boolean res=newsService.withdrawNewsById(id);
        if(res){
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.NEWS_WITHDRAW_ERROR);
    }
}
