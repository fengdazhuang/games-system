package com.fzz.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class QueryPlayerVO {

    private Long id;

    private String name;

    private String photo;

    private Integer sex;

    private String country;

    private String competitionName;

    private String competitionCategory;

    private String email;

    private Date createTime;


}
