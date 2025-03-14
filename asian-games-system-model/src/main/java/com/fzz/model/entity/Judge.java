package com.fzz.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "judge")
public class Judge {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String name;

    private String email;

    private String mongoId;

    private String competitionName;

    private Integer sex;

    private String country;

    /**
     * 运动员抵达情况
     * 0 已抵达   1 未抵达
     */
    private Integer arrivalStatus;

    /**
     * 运动员健康情况
     * 0 健康   1 良好  2 较差
     */
    private Integer healthyStatus;

    private Date createTime;

    @TableLogic
    private Integer isDeleted;


}
