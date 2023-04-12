package com.fzz.model.bo;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class AddNewsBO {

    @NotBlank(message = "标题不能为空")
    private String title;

    @NotBlank(message = "内容不能为空")
    private String content;

    @NotBlank(message = "分类不能为空")
    private String category;

    @NotNull(message = "文章类型不能为空")
    @Min(value = 1,message = "文章类型错误")
    @Max(value = 2,message = "文章类型错误")
    private Integer articleType;

    private String articleCover;

    @NotNull(message = "发布类型不能为空")
    @Min(value = 0,message = "发布类型错误")
    @Max(value = 1,message = "发布类型错误")
    private Integer isAppoint;

    @NotNull(message = "发布者id不能为空")
    private Long publisherId;

    private Date publishTime;


}
