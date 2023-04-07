package com.fzz.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "judge")
public class QueryJudgeVO {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String name;

    private String email;

    private String competitionName;

    private String competitionCategory;

    private Integer sex;

    private String country;

    private Date createTime;

    @TableLogic
    private Integer isDeleted;


}
