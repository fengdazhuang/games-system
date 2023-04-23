package com.fzz.model.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ComInfoBO {


    @NotNull(message = "赛事id不能为空")
    private Integer id;

    @NotBlank(message = "类型名称不能为空")
    private String name;

    @NotBlank(message = "负责人不能为空")
    private String principal;

    @NotBlank(message = "赛事图片不能为空")
    private String picture;

    @NotBlank(message = "赛事类型不能为空")
    private String type;

    @NotBlank(message = "赛事介绍不能为空")
    private String info;

}
