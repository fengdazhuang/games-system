package com.fzz.service.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzz.api.BaseController;
import com.fzz.api.controller.service.VolunteerControllerApi;
import com.fzz.common.result.ReturnResult;
import com.fzz.model.bo.VolunteerRegisterBO;
import com.fzz.model.vo.PreVolunteerVO;
import com.fzz.model.vo.VolunteerVO;
import com.fzz.service.service.IEmailService;
import com.fzz.service.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VolunteerController extends BaseController implements VolunteerControllerApi {

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private IEmailService emailService;

    @Override
    public ReturnResult register(VolunteerRegisterBO volunteerRegisterBO) {


        return ReturnResult.ok();
    }

    @Override
    public ReturnResult sendValidateCode(String email) {
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
