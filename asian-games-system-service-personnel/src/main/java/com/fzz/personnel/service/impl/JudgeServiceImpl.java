package com.fzz.personnel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzz.common.utils.JsonUtils;
import com.fzz.common.utils.RedisUtil;
import com.fzz.model.bo.ListJudgesBO;
import com.fzz.model.entity.CompetitionInfo;
import com.fzz.model.entity.Judge;
import com.fzz.model.entity.Player;
import com.fzz.model.vo.QueryJudgeVO;
import com.fzz.model.vo.QueryPlayerVO;
import com.fzz.personnel.mapper.JudgeMapper;
import com.fzz.personnel.service.JudgeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl extends ServiceImpl<JudgeMapper, Judge> implements JudgeService {

    private static final String REDIS_COMPETITION_INFOS = "redis_competition_infos";

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Page<QueryJudgeVO> pageJudges(ListJudgesBO listJudgesBO) {
        Integer pageNumber = listJudgesBO.getPageNumber();
        Integer pageSize = listJudgesBO.getPageSize();
        Integer competitionCategoryId = listJudgesBO.getCompetitionCategoryId();
        String name = listJudgesBO.getName();
        Integer competitionNameId = listJudgesBO.getCompetitionNameId();
        String country = listJudgesBO.getCountry();
        Page<Judge> playerPage=new Page<>(pageNumber,pageSize);
        LambdaQueryWrapper<Judge> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(country),Judge::getCountry, country);
        queryWrapper.eq(competitionCategoryId!=null,Judge::getCompetitionCategoryId,competitionCategoryId);
        queryWrapper.eq(competitionNameId!=null,Judge::getCompetitionNameId,competitionNameId);
        queryWrapper.like(StringUtils.isNotBlank(name),Judge::getName,name);
        this.page(playerPage,queryWrapper);

        Page<QueryJudgeVO> queryJudgeVOPage=new Page<>();
        BeanUtils.copyProperties(playerPage,queryJudgeVOPage,"records");
        List<QueryJudgeVO> queryJudgeVOS = playerPage.getRecords().stream().map((item -> {
            String comName = getComNameById(item.getCompetitionCategoryId(), item.getCompetitionNameId());
            QueryJudgeVO queryJudgeVO=new QueryJudgeVO();
            BeanUtils.copyProperties(item, queryJudgeVO);
            queryJudgeVO.setCompetitionName(comName);
            return queryJudgeVO;
        })).collect(Collectors.toList());
        queryJudgeVOPage.setRecords(queryJudgeVOS);

        return queryJudgeVOPage;
    }

    private String getComNameById(Integer categoryId ,Integer infoId){
        String comInfosStr = redisUtil.get(REDIS_COMPETITION_INFOS + ":" + categoryId);
        List<CompetitionInfo> competitionInfos = JsonUtils.jsonToList(comInfosStr, CompetitionInfo.class);
        for(CompetitionInfo competitionInfo:competitionInfos){
            if(competitionInfo.getId().equals(infoId)){
                return competitionInfo.getName();
            }
        }
        return "未知错误";
    }
}
