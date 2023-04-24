package com.fzz.personnel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzz.common.enums.ResponseStatusEnum;
import com.fzz.common.exception.CustomException;
import com.fzz.model.bo.AddAdminBO;
import com.fzz.model.bo.ResetPasswordBO;
import com.fzz.model.bo.UpdateAdminStatusBO;
import com.fzz.model.entity.Admin;
import com.fzz.model.vo.QueryAdminVO;
import com.fzz.personnel.entity.EmailContent;
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


    /**
     * 更新管理员登陆时间
     * @param admin 登陆管理员
     */
    @Transactional
    public void setAdminLoginTime(Admin admin){
        LambdaUpdateWrapper<Admin> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.set(Admin::getLoginTime,new Date());
        this.update(admin,updateWrapper);
    }

    @Override
    public Admin getAdminInfo(Long id) {
        return this.getById(id);
    }

    @Override
    public boolean updateAdminInfo(Admin admin) {
        return this.updateById(admin);
    }


    @Override
    public Page<QueryAdminVO> pageAdmins(Integer pageNumber, Integer pageSize, String name) {
        Page<Admin> adminPage=new Page<>(pageNumber,pageSize);
        LambdaQueryWrapper<Admin> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(name), Admin::getName,name);
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
        String randomPassword = RandomStringUtils.randomAlphanumeric(10);
        admin.setPassword(randomPassword);
        admin.setCreateTime(new Date());
        boolean res = this.save(admin);
        if(res){
            String username = admin.getUsername();
            String password = admin.getPassword();
            iEmailService.sendSimpleMail(Arrays.asList(admin.getEmail()),"亚运会管理员账户创建成功",
                    EmailContent.getCreateAdminEmailContent(username,password));
        }
        return res;
    }

    @Override
    public boolean usernameIsExists(String username){
        Admin admin = getAdminByUsername(username);
        return admin != null;
    }

    @Override
    @Transactional
    public boolean updateAdminStatus(List<UpdateAdminStatusBO> updateAdminStatusBOList) {
        boolean flag;
        for(UpdateAdminStatusBO item:updateAdminStatusBOList){
            Integer id = item.getId();
            Integer status = item.getStatus();
            LambdaUpdateWrapper<Admin> updateWrapper=new LambdaUpdateWrapper<>();
            updateWrapper.eq(Admin::getId,id);
            updateWrapper.set(Admin::getStatus, status);
            flag = this.update(updateWrapper);
            if(!flag){
                return false;
            }
        }
        return true;
    }

    @Override
    @Transactional
    public void resetAdminPassword(ResetPasswordBO resetPasswordBO) {
        Integer id = resetPasswordBO.getId();
        String email = resetPasswordBO.getEmail();
        String randomPassword = RandomStringUtils.randomAlphanumeric(10);
        LambdaUpdateWrapper<Admin> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.eq(Admin::getId,id);
        updateWrapper.set(Admin::getPassword,randomPassword);
        this.update(updateWrapper);
        iEmailService.sendSimpleMail(Arrays.asList(email),"亚运会管理系统密码重置",
                EmailContent.getResetPasswordEmailContent(randomPassword));

    }


}
