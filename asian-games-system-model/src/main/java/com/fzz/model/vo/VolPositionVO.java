package com.fzz.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class VolPositionVO {

    private Long id;

    private String name;

    private String position;

    private String risk;

    private Integer volunteerCount;

    private String principalName;

    private String principalEmail;

    private String principalPhoto;

    private Date createTime;

}
