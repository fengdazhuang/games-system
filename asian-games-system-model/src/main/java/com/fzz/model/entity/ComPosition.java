package com.fzz.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "competition_position")
public class ComPosition {

    @TableId(type= IdType.AUTO)
    private Integer id;

    private String position;

	private String competitionItem;

	private String specificPosition;

	private String area;

	private Date createTime;
}
