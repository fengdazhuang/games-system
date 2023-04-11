package com.fzz.api.controller.personnal;

import com.fzz.common.result.ReturnResult;
import com.fzz.model.bo.AdminLoginBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Api(value = "AdminController")
@RequestMapping("/api1/admin")
public interface AdminControllerApi {


    @PostMapping("/login")
    @ApiOperation(value = "管理员登录")
    ReturnResult login(@Valid @RequestBody AdminLoginBO adminLoginBO,
                       HttpServletRequest request,
                       HttpServletResponse response) throws Exception;

    @GetMapping("/getCodeImage")
    @ApiOperation(value = "获取验证码")
    ReturnResult getCodeImage();

    @PostMapping("/logout")
    @ApiOperation(value = "管理员登出")
    ReturnResult logout(Long id,
                       HttpServletRequest request,
                       HttpServletResponse response) throws Exception;
}
