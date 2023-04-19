package com.fzz.model.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ResetVolunteerRiskBO {

    @NotNull(message = "志愿者id不能为空")
    private Long id;

    @NotBlank(message = "志愿者任务不能为空")
    private Integer risk;
}
