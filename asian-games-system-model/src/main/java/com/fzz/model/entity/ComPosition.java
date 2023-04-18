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

	private String competition_item;

	private String specific_position;

	private String area;

	private Date create_time;
}
