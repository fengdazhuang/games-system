package com.fzz.competition.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fzz.model.bo.AddCompetitionInfoBO;
import com.fzz.model.entity.CompetitionInfo;
import com.fzz.model.vo.QueryCompetitionInfoVO;

import java.util.List;

public interface CompetitionInfoService extends IService<CompetitionInfo> {

//    List<CompetitionInfo> listComNamesByComCategoryId(Integer competitionCategoryId);

    boolean saveCompetitionInfo(AddCompetitionInfoBO addCompetitionInfo);

    List<QueryCompetitionInfoVO> listComInfos();
}
