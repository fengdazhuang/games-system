package com.fzz.personnel.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fzz.api.controller.AdminControllerApi;
import com.fzz.common.enums.ResponseStatusEnum;
import com.fzz.common.result.ReturnResult;
import com.fzz.model.bo.AdminLoginBO;
import com.fzz.model.entity.Admin;
import com.fzz.personnel.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController implements AdminControllerApi {

    @Autowired
    private AdminService adminService;

    @Override
    public Object login(AdminLoginBO adminLoginBO) {
        String username = adminLoginBO.getUsername();
        LambdaQueryWrapper<Admin> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUsername,username);
        Admin admin = adminService.getOne(queryWrapper);
        if(admin==null){
            return ReturnResult.error(ResponseStatusEnum.ADMIN_NOT_EXISTS);
        }
        String password = adminLoginBO.getPassword();
        if(!password.equals(admin.getPassword())){
            return ReturnResult.error(ResponseStatusEnum.ADMIN_PASSWORD_ERROR);
        }

        return ReturnResult.ok(admin);
    }

}
