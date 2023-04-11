package com.fzz.competition.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fzz.model.bo.AddCompetitionInfo;
import com.fzz.model.entity.CompetitionInfo;

import java.util.List;

public interface CompetitionInfoService extends IService<CompetitionInfo> {

//    List<CompetitionInfo> listComNamesByComCategoryId(Integer competitionCategoryId);

    boolean saveCompetitionInfo(AddCompetitionInfo addCompetitionInfo);

    List<CompetitionInfo> listComInfos();
}
