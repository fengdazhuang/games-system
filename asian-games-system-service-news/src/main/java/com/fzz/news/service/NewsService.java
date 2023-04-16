package com.fzz.news.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fzz.model.bo.AddNewsBO;
import com.fzz.model.entity.News;
import com.fzz.model.vo.QueryNewsVO;

import java.util.Date;

public interface NewsService extends IService<News> {
    Page<QueryNewsVO> pageNews(Integer pageNumber, Integer pageSize, Date startDate, Date endDate, String keyword, Integer status);

    void saveNews(AddNewsBO addNewsBO);

    void updateDelayedArticle(Long id);

    boolean removeNewsById(Long id);

    boolean withdrawNewsById(Long id);

    boolean updateNews(AddNewsBO addNewsBO);
}
