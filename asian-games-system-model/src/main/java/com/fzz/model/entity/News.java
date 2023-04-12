package com.fzz.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;

@Data
public class News {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String title;

    private String content;

    private String category;

    private Integer articleType;

    private String articleCover;

    private Integer articleStatus;

    private Integer isAppoint;

    private String mongoFileId;

    private Long publisherId;

    private Date publishTime;

    private Date createTime;

    private Date updateTime;

    @TableLogic
    private Integer isDeleted;





}
