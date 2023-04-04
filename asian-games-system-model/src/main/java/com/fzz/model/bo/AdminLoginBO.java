package com.fzz.model.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AdminLoginBO {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "验证码不能为空")
    private String validateCode;

    @NotBlank(message = "验证码不能为空")
    private String key;
}
