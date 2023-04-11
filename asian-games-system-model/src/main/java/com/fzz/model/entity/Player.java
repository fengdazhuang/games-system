package com.fzz.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "player")
public class Player {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String name;

    private String photo;

    private Integer sex;

    private String country;

    private String competitionName;

    private String email;

    private Date createTime;

    @TableLogic
    private Integer isDeleted;

}
