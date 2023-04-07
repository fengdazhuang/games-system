package com.fzz.competition.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fzz.model.bo.AddCompetitionCategory;
import com.fzz.model.entity.CompetitionCategory;
import com.fzz.model.entity.Player;
import com.fzz.model.vo.QueryCountryVO;

import java.util.List;

public interface CompetitionCategoryService extends IService<CompetitionCategory> {

    List<CompetitionCategory> listComCategorys();

    boolean saveCompetitionCategory(AddCompetitionCategory addCompetitionCategory);
}
