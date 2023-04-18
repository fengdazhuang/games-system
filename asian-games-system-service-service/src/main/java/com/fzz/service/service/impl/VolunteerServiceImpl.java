package com.fzz.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzz.common.enums.OrderColumnEnum;
import com.fzz.model.entity.Volunteer;
import com.fzz.model.vo.PreVolunteerVO;
import com.fzz.model.vo.VolunteerVO;
import com.fzz.service.mapper.VolunteerMapper;
import com.fzz.service.service.VolunteerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VolunteerServiceImpl extends ServiceImpl<VolunteerMapper, Volunteer> implements VolunteerService {
    @Override
    public Page<VolunteerVO> pageVolunteers(Integer pageNumber, Integer pageSize, Integer volunteerType, String risk) {
        Page<Volunteer> page=new Page<>(pageNumber,pageSize);
        Page<VolunteerVO> volunteerVOPage=new Page<>();
        BeanUtils.copyProperties(page,volunteerVOPage,"records");
        LambdaQueryWrapper<Volunteer> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(volunteerType!=null,Volunteer::getVolunteerType,volunteerType);
        queryWrapper.eq(StringUtils.isNotBlank(risk),Volunteer::getRisk,risk);
        this.page(page,queryWrapper);
        List<VolunteerVO> volunteerVOList = page.getRecords().stream().map(((item -> {
            VolunteerVO volunteerVO = new VolunteerVO();
            BeanUtils.copyProperties(item, volunteerVO);
            return volunteerVO;
        }))).collect(Collectors.toList());
        volunteerVOPage.setRecords(volunteerVOList);
        return volunteerVOPage;
    }

    @Override
    public Page<PreVolunteerVO> pagePreVolunteers(Integer pageNumber, Integer pageSize, Integer orderType) {
        Page<Volunteer> page=new Page<>(pageNumber,pageSize);
        Page<PreVolunteerVO> volunteerVOPage=new Page<>();
        BeanUtils.copyProperties(page,volunteerVOPage,"records");
        LambdaQueryWrapper<Volunteer> queryWrapper=new LambdaQueryWrapper<>();
        if(orderType==OrderColumnEnum.AGE.code()){
            queryWrapper.orderByAsc(Volunteer::getAge);
        }else if(orderType==OrderColumnEnum.NAME.code()){
            queryWrapper.orderByAsc(Volunteer::getName);
        } else if(orderType==OrderColumnEnum.APPLY_TIME.code()){
            queryWrapper.orderByAsc(Volunteer::getApplyTime);
        }
        this.page(page,queryWrapper);
        List<PreVolunteerVO> volunteerVOList = page.getRecords().stream().map(((item -> {
            PreVolunteerVO preVolunteerVO = new PreVolunteerVO();
            BeanUtils.copyProperties(item, preVolunteerVO);
            return preVolunteerVO;
        }))).collect(Collectors.toList());
        volunteerVOPage.setRecords(volunteerVOList);
        return volunteerVOPage;
    }
}
