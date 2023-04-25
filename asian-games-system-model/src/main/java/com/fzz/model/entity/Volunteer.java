package com.fzz.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "volunteer")
public class Volunteer {

    @TableId(type= IdType.ASSIGN_ID)
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

    /**
     * 职业
     */
    private String profession;

    /**
     * 密码
     */
    private String password;

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

    /**
     * 分配任务类型
     */
    private Integer risk;

    /**
     * 账号注册时间
     */
    private Date createTime;

    /**
     * 申请志愿者时间
     */
    private Date applyTime;

    /**
     * 信息更新时间
     */
    private Date updateTime;

    /**
     * 申请进度条
     * 0 未申请  1 已申请，待审核，未分配  2 已申请，已审核，未分配  3 已申请，已审核，已分配  4 未通过
     */
    private Integer process;

    /**
     * 申请备注，如个人特长，经历
     */
    private String comment;

    /**
     * 用户个人信息完善状态       未完善个人信息不能申请志愿者
     * 0 未完善信息 1 已完善信息
     */
    private Integer status;


}
