package com.fzz.model.vo;


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

    private Date createTime;

    private Date loginTime;

    private String email;

}
