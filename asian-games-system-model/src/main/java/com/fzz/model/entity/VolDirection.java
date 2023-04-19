package com.fzz.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("volunteer_direction")
public class VolDirection {

    @TableId(type= IdType.AUTO)
    private Integer id;

    private String name;

    private String info;

    private Integer volunteerType;

    private Date createTime;
}
