package com.fzz.medical.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzz.common.utils.JsonUtils;
import com.fzz.common.utils.RedisUtil;
import com.fzz.common.utils.TimeUtil;
import com.fzz.medical.mapper.DopTestMapper;
import com.fzz.medical.service.DopTestService;
import com.fzz.model.bo.AddDopTestBO;
import com.fzz.model.bo.SubmitDopResult;
import com.fzz.model.entity.DopTest;
import com.fzz.model.entity.Player;
import com.fzz.model.vo.DopTestVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DopTestServiceImpl extends ServiceImpl<DopTestMapper, DopTest> implements DopTestService {

    @Autowired
    private RedisUtil redisUtil;

    public static final String REDIS_PLAYER_INFO = "redis_player_info";


    @Override
    public Page<DopTest> pageResult(Integer pageNumber, Integer pageSize, Integer examinationType,
                           Integer examinationResult, Date examinationTime, String country, String name) {
        Page<DopTest> dopTestPage = new Page<>(pageNumber,pageSize);
        LambdaQueryWrapper<DopTest> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(country),DopTest::getCountry,country);
        queryWrapper.eq(examinationType!=null,DopTest::getExaminationType,examinationType);
        queryWrapper.eq(examinationResult!=null,DopTest::getExaminationResult,examinationResult);
        queryWrapper.like(StringUtils.isNotBlank(name),DopTest::getName,name);
        queryWrapper.ge(examinationTime!=null,DopTest::getExaminationTime,examinationTime);
        queryWrapper.le(examinationTime!=null,DopTest::getExaminationTime, TimeUtil.addDay(examinationTime,1));
        Page<DopTest> page = this.page(dopTestPage, queryWrapper);
        return page;
    }

    @Override
    public Page<DopTestVO> pageExamination(Integer pageNumber, Integer pageSize, String sampleNumber) {
        Page<DopTest> dopTestPage = new Page<>(pageNumber,pageSize);
        LambdaQueryWrapper<DopTest> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(sampleNumber),DopTest::getSampleNumber,sampleNumber);
        queryWrapper.eq(DopTest::getExaminationResult,2);
        Page<DopTest> page = this.page(dopTestPage, queryWrapper);
        List<DopTestVO> dopTestVOList = page.getRecords().stream().map(((item -> {
            DopTestVO dopTestVO = new DopTestVO();
            BeanUtils.copyProperties(item, dopTestVO);
            return dopTestVO;
        }))).collect(Collectors.toList());
        Page<DopTestVO> dopTestVOPage=new Page<>();
        BeanUtils.copyProperties(dopTestPage,dopTestVOPage,"records");
        dopTestVOPage.setRecords(dopTestVOList);
        return dopTestVOPage;
    }

    @Override
    public boolean saveDopTest(AddDopTestBO addDopTestBO) {
        Long playerId = addDopTestBO.getPlayerId();
        DopTest dopTest=new DopTest();
        dopTest.setPlayerId(playerId);
        dopTest.setSampleNumber(addDopTestBO.getSampleNumber());
        dopTest.setRegistrationTime(new Date());
        dopTest.setInspector(addDopTestBO.getInspector());
        String playerJson = redisUtil.get(REDIS_PLAYER_INFO + ":" + playerId);
        Player player = JsonUtils.jsonToPojo(playerJson, Player.class);
        if(player!=null){
            dopTest.setCountry(player.getCountry());
            dopTest.setName(player.getName());
        }

        dopTest.setExaminationResult(2);
        dopTest.setExaminationType(addDopTestBO.getExaminationType());
        dopTest.setExaminationPosition(addDopTestBO.getExaminationPosition());
        return this.save(dopTest);
    }

    @Override
    public boolean updateExaminationResult(List<SubmitDopResult> submitDopResultList) {
        List<Long> idList = submitDopResultList.stream().map((SubmitDopResult::getId)).collect(Collectors.toList());
        LambdaUpdateWrapper<DopTest> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.set(DopTest::getExaminationResult,submitDopResultList.get(0).getResult());
        updateWrapper.in(DopTest::getId,idList);
        return this.update(updateWrapper);
    }
}
