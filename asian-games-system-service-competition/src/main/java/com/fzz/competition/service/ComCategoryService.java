package com.fzz.competition.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fzz.model.bo.AddComCategoryBO;
import com.fzz.model.entity.ComCategory;

import java.util.List;

public interface ComCategoryService extends IService<ComCategory> {

    List<ComCategory> listComCategorys();

    boolean saveCompetitionCategory(AddComCategoryBO addCompetitionCategory);
}
