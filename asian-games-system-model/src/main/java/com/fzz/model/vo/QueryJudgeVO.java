package com.fzz.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class QueryJudgeVO {

    private Long id;

    private String name;

    private String email;

    private String competitionName;

    private String competitionCategory;

    private Integer sex;

    private String country;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh", timezone = "GMT+8")
    private Date createTime;


}
