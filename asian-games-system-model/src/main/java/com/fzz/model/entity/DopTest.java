package com.fzz.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class DopTest {

    private Long id;

    private Long playerId;

    private String name;

    private String country;

    private String examinationPosition;

    /**
     * 检测结果
     * 0 异常  1 正常 2 检测中
     */
    private Integer examinationResult;

    private String inspector;

    private String sampleNumber;

    /**
     * 检查类型
     * 0  赛内检查
     * 1  赛外检查
     */
    private Integer examinationType;

    private Date registrationTime;

    private Date examinationTime;

}
