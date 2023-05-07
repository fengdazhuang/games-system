package com.fzz.model.bo;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ApplyVolunteerBO {


    @NotNull(message = "志愿者id不能为空")
    private Long id;

    @NotBlank(message = "志愿者志愿意向不能为空")
    private String intention;

    @NotBlank(message = "志愿者简历不能为空")
    private String comment;

    @NotNull(message = "加入志愿服务点方式不能为空")
    /**
     * 加入志愿服务点的方式
     * 0 加入指定志愿者团队
     * 1 按照官方分配
     */
    @Max(value = 1,message = "申请服务点类型格式错误")
    @Min(value = 0,message = "申请服务点类型格式错误")
    private Integer type;

    private String teamId;


}
