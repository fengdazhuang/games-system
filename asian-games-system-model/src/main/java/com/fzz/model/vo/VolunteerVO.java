package com.fzz.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class VolunteerVO {

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
     * 邮箱
     */
    private String email;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 年龄
     */
    private Integer age;

    private Integer process;

    private Integer volunteerType;

    /**
     * 分配任务类型
     */
    private String risk;

    private String profession;

    private String address;

    private String teamId;


    /**
     * 申请志愿者时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh", timezone = "GMT+8")
    private Date applyTime;


}
