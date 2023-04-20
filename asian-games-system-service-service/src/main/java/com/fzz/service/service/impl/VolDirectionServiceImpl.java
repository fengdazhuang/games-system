package com.fzz.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzz.model.entity.VolDirection;
import com.fzz.service.mapper.VolDirectionMapper;
import com.fzz.service.service.VolDirectionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VolDirectionServiceImpl extends ServiceImpl<VolDirectionMapper, VolDirection> implements VolDirectionService {
    @Override
    public List<VolDirection> listVolDirections(Integer volunteerType) {
        LambdaQueryWrapper<VolDirection> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(VolDirection::getVolunteerType,volunteerType);
        return this.list(queryWrapper);
    }
}
