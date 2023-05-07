package com.fzz.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class VolPositionVO {

    private String id;

    private String name;

    private String position;

    private Integer volunteerType;

    private String risk;

    private Integer volunteerCounts;

    private String principal;

    private String principalEmail;


    private String principalPhoto;

    private Date createTime;

}
