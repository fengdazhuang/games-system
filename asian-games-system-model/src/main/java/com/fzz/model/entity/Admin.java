package com.fzz.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "admin")
public class Admin {

    @TableId
    private Long id;

    private String username;

    private String password;

    private Integer sex;

    private Date createTime;

    private Date loginTime;

    private String email;

}
