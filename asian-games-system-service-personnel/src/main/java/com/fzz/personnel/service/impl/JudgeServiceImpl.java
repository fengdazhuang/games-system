package com.fzz.personnel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzz.common.utils.JsonUtils;
import com.fzz.common.utils.RedisUtil;
import com.fzz.common.utils.SnowFlakeUtil;
import com.fzz.model.bo.AddJudgeBO;
import com.fzz.model.entity.Judge;
import com.fzz.model.vo.QueryJudgeVO;
import com.fzz.personnel.mapper.JudgeMapper;
import com.fzz.personnel.service.JudgeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl extends ServiceImpl<JudgeMapper, Judge> implements JudgeService {

    private static final String REDIS_COMPETITION_INFOS = "redis_competition_infos";
    private static final String REDIS_JUDGE_INFO = "redis_judge_info";

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public boolean saveJudge(AddJudgeBO addJudge) {
        Judge judge=new Judge();
        BeanUtils.copyProperties(addJudge,judge);
        SnowFlakeUtil snowFlakeUtil = new SnowFlakeUtil (12,13);
        Long snowFlakeId  = snowFlakeUtil.getNextId();
        judge.setId(snowFlakeId);
        judge.setCreateTime(new Date());
        boolean res = this.save(judge);
        if(res){
            redisUtil.set(REDIS_JUDGE_INFO+":"+snowFlakeId,JsonUtils.objectToJson(judge));
            return true;
        }
        return false;
    }

    @Override
    public boolean removeJudge(Long id) {
        boolean res = this.removeById(id);
        if(res){
            redisUtil.del(REDIS_JUDGE_INFO+":"+id);
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
    public Page<QueryJudgeVO> pageJudges(Integer pageNumber, Integer pageSize, String competitionName, String name, String country) {
        Page<Judge> playerPage=new Page<>(pageNumber,pageSize);
        LambdaQueryWrapper<Judge> queryWrapper=new LambdaQueryWrapper<>();
        String[] names = competitionName.split(",");
        queryWrapper.eq(StringUtils.isNotBlank(country),Judge::getCountry, country);
        queryWrapper.in(names.length>0&&!names[0].equals(""), Judge::getCompetitionName, (Object[]) names);
        queryWrapper.like(StringUtils.isNotBlank(name),Judge::getName,name);
        this.page(playerPage,queryWrapper);
        Page<QueryJudgeVO> queryJudgeVOPage=new Page<>();
        BeanUtils.copyProperties(playerPage,queryJudgeVOPage,"records");
        List<QueryJudgeVO> queryJudgeVOS = playerPage.getRecords().stream().map((item -> {
            QueryJudgeVO queryJudgeVO=new QueryJudgeVO();
            BeanUtils.copyProperties(item, queryJudgeVO);
            return queryJudgeVO;
        })).collect(Collectors.toList());
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
