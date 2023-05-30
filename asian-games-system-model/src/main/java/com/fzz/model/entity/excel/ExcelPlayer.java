package com.fzz.model.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ExcelPlayer {

    @ExcelProperty(value = "运动员id")
    private Long id;

    @ExcelProperty(value = "姓名")
    private String name;


    @ExcelProperty(value = "性别")
    private Integer sex;

    @ExcelProperty(value = "国籍")
    private String country;

    @ExcelProperty(value = "运动项目")
    private String competitionName;

    @ExcelProperty(value = "邮箱")
    private String email;

    @ExcelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 运动员抵达情况
     * 0 已抵达   1 未抵达
     */
    @ExcelProperty(value = "抵达情况")
    private Integer arrivalStatus;

    /**
     * 运动员健康情况
     * 0 健康   1 良好  2 较差
     */
    @ExcelProperty(value = "健康状况")
    private Integer healthyStatus;


}
