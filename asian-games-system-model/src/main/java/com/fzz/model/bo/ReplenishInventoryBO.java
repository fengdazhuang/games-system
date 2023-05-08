package com.fzz.model.bo;

import lombok.Data;

@Data
public class ReplenishInventoryBO {

    private Long id;

    private Integer oldNumber;


    /**
     * 数量
     */
    private Integer addNumber;

    /**
     * 负责人
     */
    private String principal;

    /**
     * 负责人联系方式
     */
    private String contact;

    /**
     * 花费钱财
     */
    private double expense;
}
