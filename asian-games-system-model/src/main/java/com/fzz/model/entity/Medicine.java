package com.fzz.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Medicine {

    @TableId(type= IdType.ASSIGN_ID)
    private Long id;

    private String name;

    /**
     * 厂家
     */
    private String maker;

    /**
     * 规格
     */
    private String specs;

    /**
     * 库存
     */
    private Integer inventory;

    /**
     * 疗效
     */
    private String efficacy;

    private Date createTime;


}
