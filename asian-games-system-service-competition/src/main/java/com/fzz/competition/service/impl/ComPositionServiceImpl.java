package com.fzz.competition.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzz.competition.mapper.ComPositionMapper;
import com.fzz.competition.service.ComPositionService;
import com.fzz.model.bo.AddComPositionBO;
import com.fzz.model.entity.ComPosition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ComPositionServiceImpl extends ServiceImpl<ComPositionMapper, ComPosition> implements ComPositionService {
    @Override
    @Transactional
    public boolean saveComPosition(AddComPositionBO addComPositionBO) {
        ComPosition comPosition=new ComPosition();
        BeanUtils.copyProperties(addComPositionBO,comPosition);
        comPosition.setCreate_time(new Date());
        return this.save(comPosition);
    }

    @Override
    @Transactional
    public boolean removeComPositionById(Integer id) {

        return this.removeById(id);
    }

    @Override
    public List<ComPosition> listComPositions(String area, String keyword) {
        LambdaQueryWrapper<ComPosition> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(keyword),ComPosition::getCompetition_item,keyword);
        queryWrapper.eq(ComPosition::getArea,area);
        return this.list(queryWrapper);
    }
}
