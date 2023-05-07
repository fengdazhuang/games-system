package com.fzz.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class DopTestVO {
    private Long id;

    private String sampleNumber;

    private String name;

    private Date registrationTime;

    private String inspector;

}
