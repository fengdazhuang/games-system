package com.fzz.personnel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzz.common.enums.AdminStatusEnum;
import com.fzz.common.enums.ResponseStatusEnum;
import com.fzz.common.exception.CustomException;
import com.fzz.model.bo.AddAdminBO;
import com.fzz.model.entity.Admin;
import com.fzz.model.vo.QueryAdminVO;
import com.fzz.personnel.mapper.AdminMapper;
import com.fzz.personnel.service.AdminService;
import com.fzz.personnel.service.IEmailService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    private IEmailService iEmailService;


    @Override
    public Admin adminIsExists(String username) {
        Admin admin = getAdminByUsername(username);
        if(admin==null){
            throw new CustomException(ResponseStatusEnum.ADMIN_NOT_EXISTS);
        }
        return admin;
    }

    private Admin getAdminByUsername(String username){
        LambdaQueryWrapper<Admin> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUsername,username);
        return this.getOne(queryWrapper);
    }

    @Override
    public Page<QueryAdminVO> pageAdmins(Integer pageNumber, Integer pageSize, String name) {
        Page<Admin> adminPage=new Page<>(pageNumber,pageSize);
        LambdaQueryWrapper<Admin> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(name), Admin::getUsername,name);
        this.page(adminPage,queryWrapper);
        Page<QueryAdminVO> queryAdminVOPage=new Page<>();
        BeanUtils.copyProperties(adminPage,queryAdminVOPage,"records");
        List<QueryAdminVO> queryAdminVOList = adminPage.getRecords().stream().map((item -> {
            QueryAdminVO queryAdminVO=new QueryAdminVO();
            BeanUtils.copyProperties(item, queryAdminVO);
            return queryAdminVO;
        })).collect(Collectors.toList());
        queryAdminVOPage.setRecords(queryAdminVOList);
        return queryAdminVOPage;
    }

    @Override
    @Transactional
    public boolean saveAdmin(AddAdminBO addAdminBO) {
        Admin admin=new Admin();
        BeanUtils.copyProperties(addAdminBO,admin);
        admin.setCreateTime(new Date());
        return this.save(admin);
    }

    @Override
    public boolean usernameIsExists(String username){
        Admin admin = getAdminByUsername(username);
        return admin != null;
    }

    @Override
    public boolean updateStatusById(Integer id,Integer status) {
        LambdaUpdateWrapper<Admin> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.eq(Admin::getId,id);
        updateWrapper.set(Admin::getStatus, status.equals(AdminStatusEnum.AVAILABLE.code())
                ?AdminStatusEnum.DISABLED.code():AdminStatusEnum.AVAILABLE.code());
        return this.update(updateWrapper);
    }

    @Override
    public void resetAdminPassword(AddAdminBO addAdminBO) {
        String username = addAdminBO.getUsername();
        String email = addAdminBO.getEmail();
        String randomPassword = RandomStringUtils.randomAlphanumeric(10);
        LambdaUpdateWrapper<Admin> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.eq(Admin::getUsername,username);
        updateWrapper.set(Admin::getPassword,randomPassword);
        this.update(updateWrapper);
        iEmailService.sendSimpleMail(Arrays.asList(email),null,randomPassword);

    }


}
