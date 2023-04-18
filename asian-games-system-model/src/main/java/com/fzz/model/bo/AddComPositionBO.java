package com.fzz.model.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddComPositionBO {

    private Integer id;

	@NotBlank(message = "比赛场所不能为空")
    private String position;

	@NotBlank(message = "比赛项目不能为空")
	private String competition_item;

	private String specific_position;

	@NotBlank(message = "赛区名称不能为空")
	private String area;

}
