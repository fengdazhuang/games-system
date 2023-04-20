package com.fzz.personnel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fzz.model.bo.AddAdminBO;
import com.fzz.model.bo.ResetPasswordBO;
import com.fzz.model.bo.UpdateAdminStatusBO;
import com.fzz.model.entity.Admin;
import com.fzz.model.vo.QueryAdminVO;

public interface AdminService extends IService<Admin> {

    Admin adminIsExists(String username);

    Page<QueryAdminVO> pageAdmins(Integer pageNumber, Integer pageSize, String name);

    boolean saveAdmin(AddAdminBO addAdminBO);

    boolean usernameIsExists(String username);

    boolean updateAdminStatus(UpdateAdminStatusBO updateAdminStatusBO);

    void resetAdminPassword(ResetPasswordBO resetPasswordBO);

    void setAdminLoginTime(Admin admin);

    Admin getAdminInfo(Long id);

    boolean updateAdminInfo(Admin admin);
}
