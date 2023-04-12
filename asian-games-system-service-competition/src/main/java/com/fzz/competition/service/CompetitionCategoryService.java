package com.fzz.competition.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fzz.model.bo.AddCompetitionCategoryBO;
import com.fzz.model.entity.CompetitionCategory;

import java.util.List;

public interface CompetitionCategoryService extends IService<CompetitionCategory> {

    List<CompetitionCategory> listComCategorys();

    boolean saveCompetitionCategory(AddCompetitionCategoryBO addCompetitionCategory);
}
