package com.fzz.model.bo;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DoReviewBO {

    @NotNull(message = "审核的志愿者id不能为空")
    private Long id;

    @NotNull(message = "审核状态不能为空")
    @Max(value = 1 , message = "志愿者状态不正确")
    @Min(value = 0 , message = "志愿者状态不正确")
    private Integer status;

    private Integer risk;

    private String teamId;

    @NotBlank(message = "志愿者邮箱不能为空")
    private String email;

    private String emailContent;




}
