package com.fzz.model.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddCompetitionCategory {


    @NotBlank(message = "类型名称不能为空")
    private String name;

    @NotBlank(message = "负责人不能为空")
    private String principal;

}
