package com.fzz.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class MedicalRecord {

    @TableId(type= IdType.ASSIGN_ID)
    private Long id;

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

    private String hospital;

    private Date createTime;

}
