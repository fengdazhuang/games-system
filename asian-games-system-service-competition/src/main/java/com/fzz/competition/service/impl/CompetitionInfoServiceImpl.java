package com.fzz.competition.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzz.competition.mapper.CompetitionInfoMapper;
import com.fzz.competition.service.CompetitionInfoService;
import com.fzz.model.bo.AddCompetitionInfoBO;
import com.fzz.model.entity.CompetitionInfo;
import com.fzz.model.vo.QueryCompetitionInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CompetitionInfoServiceImpl extends ServiceImpl<CompetitionInfoMapper, CompetitionInfo> implements CompetitionInfoService {


//    @Override
//    public List<CompetitionInfo> listComNamesByComCategoryId(Integer competitionCategoryId) {
//        LambdaQueryWrapper<CompetitionInfo> queryWrapper=new LambdaQueryWrapper<>();
//        queryWrapper.eq(CompetitionInfo::getCompetitionCategoryId,competitionCategoryId);
//        return this.list(queryWrapper);
//
//    }

    @Override
    @Transactional
    public boolean saveCompetitionInfo(AddCompetitionInfoBO addCompetitionInfo) {
        CompetitionInfo competitionInfo=new CompetitionInfo();
        BeanUtils.copyProperties(addCompetitionInfo,competitionInfo);
        competitionInfo.setCreateTime(new Date());
        competitionInfo.setUpdateTime(new Date());
        return this.save(competitionInfo);
    }

    @Override
    public List<QueryCompetitionInfoVO> listComInfos() {

        List<CompetitionInfo> list = this.list();
        List<QueryCompetitionInfoVO> competitionInfoVOS = list.stream().map(((item -> {
            QueryCompetitionInfoVO queryCompetitionInfoVO = new QueryCompetitionInfoVO();
            BeanUtils.copyProperties(item, queryCompetitionInfoVO);
            return queryCompetitionInfoVO;
        }))).collect(Collectors.toList());
        return competitionInfoVOS;
    }
}
