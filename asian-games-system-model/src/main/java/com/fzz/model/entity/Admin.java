package com.fzz.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "admin")
public class Admin {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private String picture;

    private Integer sex;

    /**
     * 0 正常
     * 1 禁用
     */
    private Integer status;

    private Date createTime;

    private Date loginTime;

    private String email;

}
