package com.fzz.model.bo;

import lombok.Data;

import java.util.Date;

@Data
public class AddMedicineBO {

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
     * 疗效
     */
    private String efficacy;



}
