package com.fzz.model.bo;

import lombok.Data;


@Data
public class AddMedicalRecordBO {

    /**
     * 身份类型
     * 0 运动员   1 裁判  2 志愿者  3管理人员  4 观众 5其他
     */
    private Integer identityType;

    private Long identityId;

    /**
     * 就诊时间类型
     * 0 赛中  1 赛外
     */
    private Integer timeType;

    private String situation;

    private String doctorRemark;

    private String doctorName;

    private String medicine;

    /**
     * 是否转诊
     * 0 不转真   1 转诊
     */
    private Integer isReferral;

    private String hospital;


}
