package com.fzz.model.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ResetPasswordBO {

    @NotNull(message = "管理员id不能为空")
    private Integer id;

    @NotBlank(message = "管理员邮箱不能为空")
    private String email;
}
