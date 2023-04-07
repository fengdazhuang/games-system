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

    private Integer competitionNameId;

    private Integer competitionCategoryId;

    private Integer sex;

    private String country;

    private Date createTime;

    @TableLogic
    private Integer isDeleted;


}
