package com.fzz.news.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fzz.model.entity.News;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NewsMapper extends BaseMapper<News> {
}
