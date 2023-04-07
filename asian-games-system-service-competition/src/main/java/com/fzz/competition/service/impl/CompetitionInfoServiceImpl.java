package com.fzz.competition.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzz.competition.mapper.CompetitionInfoMapper;
import com.fzz.competition.service.CompetitionInfoService;
import com.fzz.model.bo.AddCompetitionInfo;
import com.fzz.model.entity.CompetitionInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CompetitionInfoServiceImpl extends ServiceImpl<CompetitionInfoMapper, CompetitionInfo> implements CompetitionInfoService {


    @Override
    public List<CompetitionInfo> listComNamesByComCategoryId(Integer competitionCategoryId) {
        LambdaQueryWrapper<CompetitionInfo> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(CompetitionInfo::getCompetitionCategoryId,competitionCategoryId);
        return this.list(queryWrapper);

    }

    @Override
    @Transactional
    public boolean saveCompetitionInfo(AddCompetitionInfo addCompetitionInfo) {
        CompetitionInfo competitionInfo=new CompetitionInfo();
        BeanUtils.copyProperties(addCompetitionInfo,competitionInfo);
        return this.save(competitionInfo);
    }
}
