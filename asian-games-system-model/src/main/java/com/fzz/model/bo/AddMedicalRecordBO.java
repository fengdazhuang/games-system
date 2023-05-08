package com.fzz.model.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class AddMedicalRecordBO {

    /**
     * 身份类型
     * 0 运动员   1 裁判  2 志愿者  3管理人员  4 观众 5其他
     */
    @NotNull(message = "身份类型不能为空")
    private Integer identityType;

    @NotNull(message = "身份id不能为空")
    private Long identityId;

    /**
     * 就诊时间类型
     * 0 赛中  1 赛外
     */
    @NotNull(message = "就诊时间类型不能为空")
    private Integer timeType;

    @NotBlank(message = "病情描述不能为空")
    private String situation;

    @NotBlank(message = "医生备注不能为空")
    private String doctorRemark;

    @NotBlank(message = "医生姓名不能为空")
    private String doctorName;

    private String medicine;

    /**
     * 是否转诊
     * 0 不转真   1 转诊
     */
    @NotNull(message = "就诊时间类型不能为空")
    private Integer isReferral;

    private String hospital;


}
