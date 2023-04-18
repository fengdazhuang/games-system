package com.fzz.service.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzz.api.BaseController;
import com.fzz.api.controller.service.VolunteerControllerApi;
import com.fzz.common.enums.ResponseStatusEnum;
import com.fzz.common.result.ReturnResult;
import com.fzz.common.utils.RedisUtil;
import com.fzz.model.bo.VolunteerRegisterBO;
import com.fzz.model.entity.Volunteer;
import com.fzz.model.vo.PreVolunteerVO;
import com.fzz.model.vo.VolunteerVO;
import com.fzz.service.service.IEmailService;
import com.fzz.service.service.VolunteerService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class VolunteerController extends BaseController implements VolunteerControllerApi {

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private IEmailService emailService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public ReturnResult register(VolunteerRegisterBO volunteerRegisterBO) {
        String email = volunteerRegisterBO.getEmail();



        return ReturnResult.ok();
    }

    @Override
    public ReturnResult sendEmailCode(String email) {
        Volunteer volunteer = volunteerService.getVolunteerByEmail(email);
        if(volunteer!=null){
            return ReturnResult.error(ResponseStatusEnum.VOLUNTEER_IS_ALREADY_EXISTS);
        }
        String randomCode = RandomStringUtils.randomAlphanumeric(10);
        emailService.sendSimpleMail(Arrays.asList(email),"亚运会志愿者注册",randomCode);
        redisUtil.set(REDIS_VOLUNTEER_EMAIL_CODE+":"+email,randomCode,30*60);
        return ReturnResult.ok();
    }

    @Override
    public ReturnResult pageVolunteers(Integer pageNumber, Integer pageSize, Integer volunteerType, String risk) {
        if(pageNumber<=0){
            pageNumber=COMMON_START_PAGE;
        }
        if(pageSize<=0){
            pageSize=COMMON_PAGE_SIZE;
        }
        Page<VolunteerVO> volunteerVOPage = volunteerService.pageVolunteers(pageNumber,pageSize,volunteerType,risk);
        return ReturnResult.ok(volunteerVOPage);
    }

    @Override
    public ReturnResult pagePreVolunteers(Integer pageNumber, Integer pageSize, Integer orderType) {
        if(pageNumber<=0){
            pageNumber=COMMON_START_PAGE;
        }
        if(pageSize<=0){
            pageSize=COMMON_PAGE_SIZE;
        }
        Page<PreVolunteerVO> volunteerVOPage = volunteerService.pagePreVolunteers(pageNumber,pageSize,orderType);
        return ReturnResult.ok(volunteerVOPage);
    }
}
