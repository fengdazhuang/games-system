package com.fzz.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("volunteer_position")
public class VolPosition {

    @TableId
    private String id;

    private String name;

    private String position;

    private Integer risk;

    private Integer volunteerCounts;

    private Integer volunteerType;

    private Integer principalId;

    private Date createTime;

    @TableLogic
    private Integer isDeleted;
}
