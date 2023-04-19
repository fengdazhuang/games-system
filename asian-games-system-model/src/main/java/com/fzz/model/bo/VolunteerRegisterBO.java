package com.fzz.model.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class VolunteerRegisterBO {

    @NotBlank(message = "邮箱不能为空")
    private String email;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "验证码不能为空")
    private String validateCode;

}
