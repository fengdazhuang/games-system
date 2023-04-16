package com.fzz.model.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class QueryAdminVO {

    private Integer id;

    private String username;

    private String name;

    private Integer sex;

    /**
     * 0 正常
     * 1 禁用
     */
    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh", timezone = "GMT+8")
    private Date loginTime;

    private String email;

}
