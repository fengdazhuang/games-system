package com.fzz.model.bo;

import com.fzz.model.entity.Volunteer;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class VolunteerLoginBO {

    @NotBlank(message = "邮箱不能为空")
    private String email;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "验证码不能为空")
    private String validateCode;

    @NotBlank(message = "验证码的key不能为空")
    private String key;

}
