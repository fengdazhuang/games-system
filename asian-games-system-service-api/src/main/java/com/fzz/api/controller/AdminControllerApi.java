package com.fzz.api.controller;

import com.fzz.model.bo.AdminLoginBO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
public interface AdminControllerApi {


    @PostMapping("/login")
    public Object login(AdminLoginBO adminLoginBO);
}
