package com.fzz.service.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzz.model.entity.VolPosition;
import com.fzz.model.entity.Volunteer;
import com.fzz.model.vo.VolPositionVO;
import com.fzz.service.mapper.VolPositionMapper;
import com.fzz.service.service.VolPositionService;
import com.fzz.service.service.VolunteerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VolPositionServiceImpl extends ServiceImpl<VolPositionMapper, VolPosition> implements VolPositionService {

    @Autowired
    private VolunteerService volunteerService;

    @Override
    public Page<VolPositionVO> pageVolunteerPositions(Integer pageNumber, Integer pageSize) {
        Page<VolPosition> volPositionPage=new Page<>(pageNumber,pageSize);
        Page<VolPositionVO> volPositionVOPage=new Page<>();
        this.page(volPositionPage);
        BeanUtils.copyProperties(volPositionPage,volPositionVOPage,"records");
        List<VolPositionVO> volPositionVOList = volPositionPage.getRecords().stream().map(((item -> {
            VolPositionVO volPositionVO = new VolPositionVO();
            BeanUtils.copyProperties(item, volPositionVO);
            Volunteer volunteer = volunteerService.getById(item.getPrincipalId());
            volPositionVO.setName(volunteer.getName());
            volPositionVO.setPrincipalPhoto(volunteer.getPhoto());
            volPositionVO.setPrincipalEmail(volunteer.getEmail());
            return volPositionVO;
        }))).collect(Collectors.toList());
        volPositionVOPage.setRecords(volPositionVOList);
        return volPositionVOPage;
    }
}
