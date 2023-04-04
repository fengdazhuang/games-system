package com.fzz.personnel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzz.common.enums.ResponseStatusEnum;
import com.fzz.common.exception.CustomException;
import com.fzz.model.entity.Admin;
import com.fzz.personnel.mapper.AdminMapper;
import com.fzz.personnel.service.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {


    @Override
    public Admin adminIsExists(String username) {
        LambdaQueryWrapper<Admin> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUsername,username);
        Admin admin = this.getOne(queryWrapper);
        if(admin==null){
            throw new CustomException(ResponseStatusEnum.ADMIN_NOT_EXISTS);
        }
        return admin;
    }
}
