package com.fzz.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class PreVolunteerVO {

    private Long id;

    /**
     * 志愿者姓名
     */
    private String name;

    /**
     * 志愿者照片
     */
    private String photo;



    /**
     * 性别
     */
    private Integer sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 职业
     */
    private String profession;

    /**
     * 邮箱
     */
    private String email;


    /**
     * 证件类型，如居民身份证，港澳台身份证，前台的单选框写死
     */
    private String certificateType;

    /**
     * 证件号码
     */
    private String certificateNumber;

    /**
     * 比赛期间居住地址
     */
    private String address;

    /**
     * 志愿者类型
     * 0 赛事志愿者  1 城市志愿者
     */
    private Integer volunteerType;

    /**
     * 申请的志愿意向
     */
    private String intention;


    private String teamId;

    private Integer risk;


    /**
     * 申请志愿者时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh", timezone = "GMT+8")
    private Date applyTime;

    /**
     * 信息更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh", timezone = "GMT+8")
    private Date updateTime;


    /**
     * 申请备注，如个人特长，经历
     */
    private String comment;



}
