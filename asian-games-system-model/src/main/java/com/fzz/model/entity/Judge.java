package com.fzz.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "judge")
public class Judge {

    @TableId
    private Long id;

    private String name;

    private String email;

    private Integer competitionName;

    private Integer sex;

    private String country;

    private Date createTime;

    @TableLogic
    private Integer isDeleted;


}
