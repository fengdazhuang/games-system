package com.fzz.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzz.common.utils.JsonUtils;
import com.fzz.common.utils.RedisUtil;
import com.fzz.model.entity.VolDirection;
import com.fzz.service.mapper.VolDirectionMapper;
import com.fzz.service.service.VolDirectionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VolDirectionServiceImpl extends ServiceImpl<VolDirectionMapper, VolDirection> implements VolDirectionService {

    private static final String REDIS_ALL_VOLUNTEER_DIRECTIONS = "redis_all_volunteer_directions";
    @Autowired
    private RedisUtil redisUtil;


    @Override
    public List<VolDirection> listVolDirections(Integer volunteerType) {
        LambdaQueryWrapper<VolDirection> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(volunteerType!=null,VolDirection::getVolunteerType,volunteerType);
        return this.list(queryWrapper);
    }

    @Override
    public List<VolDirection> listAllVolDirections() {
        List<VolDirection> list = null;
        String json = redisUtil.get(REDIS_ALL_VOLUNTEER_DIRECTIONS);
        if(StringUtils.isNotBlank(json)){
            list=JsonUtils.jsonToList(json,VolDirection.class);
        }else{
            list=this.list();
        }
        return list;
    }
}
