package com.fzz.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class QueryJudgeVO {

    private Long id;

    private String name;

    private String email;

    private String competitionName;

    private String competitionCategory;

    private Integer sex;

    private String country;

    private Date createTime;


}
