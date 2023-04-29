package com.fzz.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzz.model.bo.AddVolPositionBO;
import com.fzz.model.entity.VolDirection;
import com.fzz.model.entity.VolPosition;
import com.fzz.model.entity.Volunteer;
import com.fzz.model.vo.VolPositionVO;
import com.fzz.service.mapper.VolPositionMapper;
import com.fzz.service.service.VolDirectionService;
import com.fzz.service.service.VolPositionService;
import com.fzz.service.service.VolunteerService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VolPositionServiceImpl extends ServiceImpl<VolPositionMapper, VolPosition> implements VolPositionService {

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private VolDirectionService volDirectionService;

    @Override
    public Page<VolPositionVO> pageVolunteerPositions(Integer pageNumber, Integer pageSize, Integer risk,
                                                      Integer volunteerType, String name) {
        Page<VolPosition> volPositionPage=new Page<>(pageNumber,pageSize);
        Page<VolPositionVO> volPositionVOPage=new Page<>();
        LambdaQueryWrapper<VolPosition> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(risk!=null,VolPosition::getRisk,risk);
        queryWrapper.eq(volunteerType!=null,VolPosition::getVolunteerType,volunteerType);
        queryWrapper.like(StringUtils.isNotBlank(name),VolPosition::getName,name);

        this.page(volPositionPage);
        BeanUtils.copyProperties(volPositionPage,volPositionVOPage,"records");

        List<VolPositionVO> volPositionVOList = volPositionPage.getRecords().stream().map(((item -> {
            VolPositionVO volPositionVO = new VolPositionVO();
            BeanUtils.copyProperties(item, volPositionVO);
            Volunteer volunteer = volunteerService.getById(item.getPrincipalId());
            VolDirection volDirection = getVolDirection(item.getRisk());
            if(volDirection!=null){
                volPositionVO.setRisk(volDirection.getName());
            }
            volPositionVO.setName(volunteer.getName());
            volPositionVO.setPrincipalPhoto(volunteer.getPhoto());
            volPositionVO.setPrincipalEmail(volunteer.getEmail());
            return volPositionVO;
        }))).collect(Collectors.toList());
        volPositionVOPage.setRecords(volPositionVOList);
        return volPositionVOPage;
    }

    @Override
    public boolean addVolunteerPosition(AddVolPositionBO addVolPositionBO) {
        VolPosition volPosition=new VolPosition();
        BeanUtils.copyProperties(addVolPositionBO,volPosition);
        volPosition.setCreateTime(new Date());
        String id = RandomStringUtils.randomNumeric(6);
        volPosition.setId(id);
        return this.save(volPosition);
    }



    @Override
    public List<VolPosition> listVolPositionsByRisk(Integer risk) {
        LambdaQueryWrapper<VolPosition> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(VolPosition::getRisk,risk);
        return this.list(queryWrapper);
    }


    /**
     * 通过服务方向的id获取服务方向
     * @param riskId id
     * @return 服务方向
     */
    private VolDirection getVolDirection(Integer riskId){
        if(riskId==null) return null;
        List<VolDirection> list = volDirectionService.listAllVolDirections();
        for(VolDirection volDirection:list){
            if(volDirection.getId()==riskId){
                return volDirection;
            }
        }
        return null;
    }
}
