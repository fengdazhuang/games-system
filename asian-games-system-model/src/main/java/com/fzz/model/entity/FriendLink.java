package com.fzz.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("friend_link")
public class FriendLink {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private String url;

    private String logo;

    private Integer status;

    private Date createTime;
}
