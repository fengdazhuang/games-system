package com.fzz.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class QueryNewsVO {

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






}
