package com.fzz.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class MedicineList {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long MedicineId;

    private String medicineName;

    /**
     * 变动数量
     */
    private Integer number;

    /**
     * 0 出          1 入
     */
    private Integer type;

    private Date createTime;




}
