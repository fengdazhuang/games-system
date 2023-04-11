package com.fzz.model.bo;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddJudgeBO {

    private Long id;

    @NotBlank(message = "裁判姓名不能为空")
    private String name;

    @NotBlank(message = "照片不能为空")
    private String photo;

    @NotNull(message = "性别不能为空")
    @Max(value = 1,message = "请输入正确的性别")
    @Min(value = 0,message = "请输入正确的性别")
    private Integer sex;

    @NotBlank(message = "国家不能为空")
    private String country;

    @NotBlank(message = "赛事名称不能为空")
    private String competitionName;

    @NotBlank(message = "邮箱不能为空")
    private String email;

}
