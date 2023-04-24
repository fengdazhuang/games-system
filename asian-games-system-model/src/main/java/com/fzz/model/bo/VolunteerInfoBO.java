package com.fzz.model.bo;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class VolunteerInfoBO {

    @NotNull(message = "志愿者id不能为空")
    private Long id;

    /**
     * 志愿者姓名
     */
    @NotBlank(message = "志愿者姓名不能为空")
    private String name;

    /**
     * 志愿者照片
     */
    @NotBlank(message = "志愿者姓名不能为空")
    private String photo;


    /**
     * 性别
     */
    @NotNull(message = "性别不能为空")
    @Min(value = 0,message = "性别信息错误")
    @Max(value = 1,message = "性别信息错误")
    private Integer sex;

    /**
     * 年龄
     */
    @NotNull(message = "年龄不能为空")
    private Integer age;

    /**
     * 职业
     */
    @NotBlank(message = "职业不能为空")
    private String profession;


    /**
     * 证件类型，如居民身份证，港澳台身份证，前台的单选框写死
     */
    @NotBlank(message = "证件类型不能为空")
    private String certificateType;

    /**
     * 证件号码
     */
    @NotBlank(message = "证件号码不能为空")
    private String certificateNumber;

    /**
     * 比赛期间居住地址
     */
    @NotBlank(message = "居住地址不能为空")
    private String address;

    @NotBlank(message = "志愿者服务意向不能为空")
    private String intention;

    /**
     * 申请备注，如个人特长，经历
     */
    @NotBlank(message = "个人特长，经历不能为空")
    private String comment;



}
