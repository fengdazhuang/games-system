package com.fzz.api.controller.personnal;

import com.fzz.common.result.ReturnResult;
import com.fzz.model.bo.AddAdminBO;
import com.fzz.model.bo.AdminLoginBO;
import com.fzz.model.bo.ResetPasswordBO;
import com.fzz.model.bo.UpdateAdminStatusBO;
import com.fzz.model.entity.Admin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/listAdmins")
    @ApiOperation(value = "分页查询所有管理员")
    ReturnResult listAdmins(@RequestParam Integer pageNumber,
                            @RequestParam Integer pageSize,
                            @RequestParam String name);

    @PostMapping("/addAdmin")
    @ApiOperation(value = "添加管理员")
    ReturnResult addAdmin(@RequestBody AddAdminBO addAdminBO);

    @GetMapping("/queryAdmin")
    @ApiOperation(value = "查询该用户名是否已经存在")
    ReturnResult queryAdmin(@RequestParam String username);

    @PutMapping("/updateStatus")
    @ApiOperation(value = "修改管理员状态")
    ReturnResult updateStatus(@RequestBody UpdateAdminStatusBO updateAdminStatusBO);

    @PutMapping("/resetPassword")
    @ApiOperation(value = "重置管理员密码")
    ReturnResult resetPassword(@RequestBody ResetPasswordBO resetPasswordBO);

}
