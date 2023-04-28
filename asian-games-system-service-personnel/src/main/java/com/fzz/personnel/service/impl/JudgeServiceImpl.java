package com.fzz.personnel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzz.common.utils.JsonUtils;
import com.fzz.common.utils.RedisUtil;
import com.fzz.model.bo.AddJudgeBO;
import com.fzz.model.entity.Judge;
import com.fzz.model.vo.QueryJudgeVO;
import com.fzz.personnel.mapper.JudgeMapper;
import com.fzz.personnel.service.JudgeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl extends ServiceImpl<JudgeMapper, Judge> implements JudgeService {

    private static final String REDIS_COMPETITION_INFOS = "redis_competition_infos";
    private static final String REDIS_JUDGE_INFO = "redis_judge_info";
    private static final String REDIS_JUDGE_MONGO_IDS = "redis_judge_mongo_ids";

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public boolean saveJudge(AddJudgeBO addJudgeBO) {
        Long id = addJudgeBO.getId();
        Judge judge=new Judge();
        BeanUtils.copyProperties(addJudgeBO,judge);
        judge.setCreateTime(new Date());
        boolean res = this.save(judge);
        if(res){
            redisUtil.set(REDIS_JUDGE_INFO+":"+id,JsonUtils.objectToJson(judge));
            return true;
        }
        return false;
    }

    @Override
    public boolean removeJudges(Long[] id) {
        boolean res = this.removeByIds(Arrays.asList(id));
        if(res){
            for(Long judgeId:id){
                redisUtil.del(REDIS_JUDGE_INFO+":"+judgeId);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePlayerById(AddJudgeBO addJudge) {
        Judge judge=new Judge();
        BeanUtils.copyProperties(addJudge,judge);
        return this.updateById(judge);
    }

    @Override
    public Page<QueryJudgeVO> pageJudges(Integer pageNumber, Integer pageSize, String competitionName,
                                         String name, String country, Integer arrivalStatus, Integer healthyStatus) {
        Page<Judge> playerPage=new Page<>(pageNumber,pageSize);
        LambdaQueryWrapper<Judge> queryWrapper=new LambdaQueryWrapper<>();
        String[] names = competitionName.split(",");
        queryWrapper.eq(StringUtils.isNotBlank(country),Judge::getCountry, country);
        queryWrapper.eq(arrivalStatus!=null, Judge::getArrivalStatus,arrivalStatus);
        queryWrapper.eq(healthyStatus!=null, Judge::getHealthyStatus,healthyStatus);
        queryWrapper.in(names.length>0&&!names[0].equals(""), Judge::getCompetitionName, (Object[]) names);
        queryWrapper.like(StringUtils.isNotBlank(name),Judge::getName,name);
        this.page(playerPage,queryWrapper);
        Page<QueryJudgeVO> queryJudgeVOPage=new Page<>();
        BeanUtils.copyProperties(playerPage,queryJudgeVOPage,"records");
        Set<String> set = new HashSet<>();
        List<QueryJudgeVO> queryJudgeVOS = playerPage.getRecords().stream().map((item -> {
            QueryJudgeVO queryJudgeVO=new QueryJudgeVO();
            BeanUtils.copyProperties(item, queryJudgeVO);
            set.add(item.getMongoId());
            return queryJudgeVO;
        })).collect(Collectors.toList());
        redisUtil.set(REDIS_JUDGE_MONGO_IDS,JsonUtils.objectToJson(set));
        queryJudgeVOPage.setRecords(queryJudgeVOS);
        return queryJudgeVOPage;
    }

/*    private String getComNameById(Integer categoryId ,Integer infoId){
        String comInfosStr = redisUtil.get(REDIS_COMPETITION_INFOS + ":" + categoryId);
        List<ComInfo> comInfos = JsonUtils.jsonToList(comInfosStr, ComInfo.class);
        for(ComInfo comInfo:comInfos){
            if(comInfo.getId().equals(infoId)){
                return comInfo.getName();
            }
        }
        return "未知错误";
    }*/
}
