package com.fzz.model.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddDopTestBO {

    @NotNull(message = "运动员id不能为空")
    private Long playerId;

    @NotBlank(message = "样品编号不能为空")
    private String sampleNumber;

    @NotBlank(message = "被检人员的base64图片不能为空")
    private String base64;

    @NotBlank(message = "检测人员不能为空")
    private String inspector;

    @NotBlank(message = "检测地点不能为空")
    private String examinationPosition;

    @NotNull(message = "检测类型不能为空")
    private Integer examinationType;

}
