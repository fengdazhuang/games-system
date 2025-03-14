package com.fzz.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzz.common.enums.OrderColumnEnum;
import com.fzz.common.enums.ResponseStatusEnum;
import com.fzz.common.exception.CustomException;
import com.fzz.common.utils.RedisUtil;
import com.fzz.model.bo.*;
import com.fzz.model.dto.VolTeamDto;
import com.fzz.model.entity.VolDirection;
import com.fzz.model.entity.VolPosition;
import com.fzz.model.entity.Volunteer;
import com.fzz.model.vo.PreVolunteerVO;
import com.fzz.model.vo.VolBaseInfoVO;
import com.fzz.model.vo.VolunteerVO;
import com.fzz.service.mapper.VolunteerMapper;
import com.fzz.service.service.VolDirectionService;
import com.fzz.service.service.VolPositionService;
import com.fzz.service.service.VolunteerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VolunteerServiceImpl extends ServiceImpl<VolunteerMapper, Volunteer> implements VolunteerService {

    private static final String REDIS_POSITION_VOLUNTEER_COUNTS = "redis_position_volunteer_counts";
    @Autowired
    private VolDirectionService volDirectionService;

    @Autowired
    private VolPositionService volPositionService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Page<VolunteerVO> pageVolunteers(Integer pageNumber, Integer pageSize, Integer volunteerType, Integer risk) {
        Page<Volunteer> page=new Page<>(pageNumber,pageSize);
        Page<VolunteerVO> volunteerVOPage=new Page<>();
        BeanUtils.copyProperties(page,volunteerVOPage,"records");
        LambdaQueryWrapper<Volunteer> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(volunteerType!=null,Volunteer::getVolunteerType,volunteerType);
        queryWrapper.eq(risk!=null,Volunteer::getRisk,risk);
        queryWrapper.eq(Volunteer::getStatus,1);
        queryWrapper.in(Volunteer::getProcess,2,3);
        this.page(page,queryWrapper);
        List<VolunteerVO> volunteerVOList = page.getRecords().stream().map(((item -> {
            VolunteerVO volunteerVO = new VolunteerVO();
            BeanUtils.copyProperties(item, volunteerVO);
            Integer riskId = item.getRisk();
            if(riskId!=null){
                VolDirection volDirection = getVolDirection(riskId);
                volunteerVO.setRisk(volDirection.getName());
            }
            return volunteerVO;
        }))).collect(Collectors.toList());
        volunteerVOPage.setRecords(volunteerVOList);
        return volunteerVOPage;
    }

    /**
     * 通过服务方向的id获取服务方向
     * @param riskId id
     * @return 服务方向
     */
    private VolDirection getVolDirection(Integer riskId){
        List<VolDirection> list = volDirectionService.listAllVolDirections();
        for(VolDirection volDirection:list){
            if(volDirection.getId()==riskId){
                return volDirection;
            }
        }
        return null;
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
        queryWrapper.eq(Volunteer::getStatus,1);
        queryWrapper.eq(Volunteer::getProcess,1);
        this.page(page,queryWrapper);
        List<PreVolunteerVO> volunteerVOList = page.getRecords().stream().map(((item -> {
            PreVolunteerVO preVolunteerVO = new PreVolunteerVO();
            BeanUtils.copyProperties(item, preVolunteerVO);
            String teamId = item.getTeamId();
            if(StringUtils.isNotBlank(teamId)){
                VolPosition volPosition = volPositionService.getById(teamId);
                preVolunteerVO.setRisk(volPosition.getRisk());
            }
            return preVolunteerVO;
        }))).collect(Collectors.toList());
        volunteerVOPage.setRecords(volunteerVOList);
        return volunteerVOPage;
    }

    @Override
    public Volunteer getVolunteerByEmail(String email) {
        LambdaQueryWrapper<Volunteer> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Volunteer::getEmail,email);
        return this.getOne(queryWrapper);
    }

    @Override
    public VolTeamDto getVolTeamInfo(String teamId) {
        VolTeamDto volTeamDto=new VolTeamDto();

        VolPosition volPosition = volPositionService.getById(teamId);
        volTeamDto.setTeamId(teamId);
        volTeamDto.setName(volPosition.getName());
        volTeamDto.setPosition(volPosition.getPosition());

        List<Volunteer> volunteerList = getVolunteersByTeamId(teamId);
        List<VolBaseInfoVO> volBaseInfoVOList = volunteerList.stream().map(((item -> {
            VolBaseInfoVO volBaseInfoVO = new VolBaseInfoVO();
            BeanUtils.copyProperties(item, volBaseInfoVO);
            return volBaseInfoVO;
        }))).collect(Collectors.toList());
        volTeamDto.setMembers(volBaseInfoVOList);
        return volTeamDto;
    }


    @Override
    public List<Volunteer> getVolunteersByTeamId(String teamId) {
        LambdaQueryWrapper<Volunteer> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(teamId),Volunteer::getTeamId,teamId);

        return this.list(queryWrapper);
    }

    @Override
    public Volunteer getVolunteerDetailById(Long id) {
        return this.getById(id);
    }

    @Override
    @Transactional
    public boolean applyVolunteer(ApplyVolunteerBO applyVolunteerBO) {
        Integer type = applyVolunteerBO.getType();
        String teamId = applyVolunteerBO.getTeamId();
        LambdaUpdateWrapper<Volunteer> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.eq(Volunteer::getId,applyVolunteerBO.getId());
        updateWrapper.set(Volunteer::getApplyTime,new Date());
        updateWrapper.set(Volunteer::getIntention,applyVolunteerBO.getIntention());
        updateWrapper.set(Volunteer::getComment,applyVolunteerBO.getComment());
        updateWrapper.set(Volunteer::getProcess,1);

        if(type==0&&StringUtils.isNotBlank(teamId)){
            updateWrapper.set(Volunteer::getTeamId, teamId);
        }
        else if(type==1){
            updateWrapper.set(Volunteer::getTeamId, null);
        }
        else if(type==0&&!StringUtils.isNotBlank(teamId)){
            throw new CustomException(ResponseStatusEnum.SYSTEM_BUSY_ERROR);
        }
        updateWrapper.set(Volunteer::getJoinType,type );
        return this.update(updateWrapper);
    }

    @Override
    public boolean updateVolunteerType(Long id, Integer volunteerType) {
        LambdaUpdateWrapper<Volunteer> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.eq(Volunteer::getId,id);
        updateWrapper.set(Volunteer::getVolunteerType,volunteerType);
        return this.update(updateWrapper);
    }

    @Override
    public boolean perfectOrUpdateVolunteerInfo(VolunteerInfoBO volunteerInfoBO) {
        Volunteer volunteer=new Volunteer();
        BeanUtils.copyProperties(volunteerInfoBO,volunteer);
        volunteer.setUpdateTime(new Date());
        volunteer.setStatus(1);
        return this.updateById(volunteer);
    }

    @Override
    @Transactional
    public boolean forgetVolunteerPassword(VolunteerRegisterBO volunteerRegisterBO) {
        String email = volunteerRegisterBO.getEmail();
        String password = volunteerRegisterBO.getPassword();
        LambdaUpdateWrapper<Volunteer> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.eq(Volunteer::getEmail,email);
        updateWrapper.set(Volunteer::getPassword,password);
        return this.update(updateWrapper);
    }

    @Override
    @Transactional
    public boolean updateVolunteerPassword(ModifyPasswordBO modifyPasswordBO) {
        Long id = modifyPasswordBO.getId();
        String newPassword = modifyPasswordBO.getNewPassword();
        LambdaUpdateWrapper<Volunteer> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.eq(Volunteer::getId,id);
        updateWrapper.set(Volunteer::getPassword,newPassword);
        return this.update(updateWrapper);
    }

    @Override
    public Volunteer volunteerIsExists(String email) {
        LambdaQueryWrapper<Volunteer> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Volunteer::getEmail,email);
        Volunteer volunteer = this.getOne(queryWrapper);
        if(volunteer==null){
            throw new CustomException(ResponseStatusEnum.VOLUNTEER_LOGIN_ERROR);
        }
        return volunteer;
    }

    @Override
    @Transactional
    public boolean updateVolunteerRisk(ResetVolunteerRiskBO resetVolunteerRiskBO) {
        Integer risk = resetVolunteerRiskBO.getRisk();
        Long id = resetVolunteerRiskBO.getId();
        LambdaUpdateWrapper<Volunteer> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.eq(Volunteer::getId,id);
        updateWrapper.set(Volunteer::getRisk,risk);
        return this.update(updateWrapper);
    }

    @Override
    @Transactional
    public boolean saveVolunteer(Volunteer volunteer) {
        return this.save(volunteer);
    }

    @Override
    @Transactional
    public boolean doReview(DoReviewBO doReviewBO) {
        Integer status = doReviewBO.getStatus();
        LambdaUpdateWrapper<Volunteer> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.eq(Volunteer::getId,doReviewBO.getId());
        if(status==0){
            updateWrapper.set(Volunteer::getProcess,4);
        }
        if(status==1){
            Integer risk = doReviewBO.getRisk();
            String teamId = doReviewBO.getTeamId();
            if(risk!=null){
                updateWrapper.set(Volunteer::getProcess,3);
                updateWrapper.set(Volunteer::getRisk,risk);
                updateWrapper.set(Volunteer::getTeamId,teamId);
                redisUtil.increment(REDIS_POSITION_VOLUNTEER_COUNTS+":"+teamId,1);
            }else{
                updateWrapper.set(Volunteer::getProcess,2);
            }

        }
        return this.update(updateWrapper);
    }


}
