package com.fzz.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class QueryPlayerVO {

    private Long id;

    private String name;

    private String photo;

    private String mongoId;

    private Integer sex;

    private String country;

    private String competitionName;

    private String competitionCategory;

    /**
     * 运动员抵达情况
     * 0 已抵达   1 未抵达
     */
    private Integer arrivalStatus;

    /**
     * 运动员健康情况
     * 0 健康   1 良好  2 较差
     */
    private Integer healthyStatus;

    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh", timezone = "GMT+8")
    private Date createTime;


}
