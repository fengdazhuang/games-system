package com.fzz.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("volunteer_position")
public class VolPosition {

    @TableId(type= IdType.ASSIGN_ID)
    private Long id;

    private String name;

    private String position;

    private String risk;

    private Integer volunteerCount;

    private Integer principalId;

    private Date createTime;

    @TableLogic
    private Integer isDeleted;
}
