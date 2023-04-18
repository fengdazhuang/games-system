package com.fzz.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "competition_category")
public class ComCategory {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private String principal;

    private Date createTime;

    private Date updateTime;

    @TableLogic
    private Integer isDeleted;
}
