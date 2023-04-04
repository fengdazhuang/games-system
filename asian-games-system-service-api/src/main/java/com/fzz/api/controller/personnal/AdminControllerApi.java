package com.fzz.api.controller.personnal;

import com.fzz.common.result.ReturnResult;
import com.fzz.model.bo.AdminLoginBO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequestMapping("/admin")
public interface AdminControllerApi {


    @PostMapping("/login")
    ReturnResult login(@Valid @RequestBody AdminLoginBO adminLoginBO,
                       HttpServletRequest request,
                       HttpServletResponse response) throws Exception;

    @GetMapping("/getCodeImage")
    ReturnResult getCodeImage(String key);

    @PostMapping("/logout")
    ReturnResult logout(Long id,
                       HttpServletRequest request,
                       HttpServletResponse response) throws Exception;
}
