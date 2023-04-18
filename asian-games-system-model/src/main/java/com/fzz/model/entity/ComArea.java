package com.fzz.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "competition_area")
public class ComArea {

    @TableId(type= IdType.AUTO)
    private Integer id;

    private String name;

    private Date createTime;
}
