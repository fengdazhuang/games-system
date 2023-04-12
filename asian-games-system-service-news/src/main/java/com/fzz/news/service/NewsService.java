package com.fzz.news.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fzz.model.entity.News;
import com.fzz.model.vo.QueryNewsVO;

import java.util.Date;

public interface NewsService extends IService<News> {
    Page<QueryNewsVO> pageNews(Integer pageNumber, Integer pageSize, Date startDate, Date endDate, String keyword, Integer status);
}
