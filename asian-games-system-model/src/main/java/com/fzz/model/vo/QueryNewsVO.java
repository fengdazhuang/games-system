package com.fzz.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    private Integer readCounts;

    private String mongoFileId;

    private Long publisherId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh", timezone = "GMT+8")
    private Date publishTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh", timezone = "GMT+8")
    private Date updateTime;






}
