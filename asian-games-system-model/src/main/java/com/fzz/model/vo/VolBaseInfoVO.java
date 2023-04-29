package com.fzz.model.vo;

import lombok.Data;


/**
 * 志愿者基本信息（简要信息）
 */
@Data
public class VolBaseInfoVO {
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
     * 比赛期间居住地址
     */
    private String address;


    /**
     * 分配任务类型
     */
    private Integer risk;


    /**
     * 志愿者所属志愿者团队id
     */
    private String teamId;


}
