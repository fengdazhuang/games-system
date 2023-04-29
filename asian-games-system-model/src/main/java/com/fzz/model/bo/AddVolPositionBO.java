package com.fzz.model.bo;


import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class AddVolPositionBO {

    @NotBlank(message = "志愿点名称不能为空")
    private String name;

    @NotBlank(message = "志愿点地址不能为空")
    private String position;

    @NotBlank(message = "志愿点服务方向不能为空")
    private Integer risk;

    @NotBlank(message = "志愿点志愿者类型不能为空")
    private Integer volunteerType;



}
