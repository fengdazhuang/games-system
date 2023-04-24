package com.fzz.model.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class UpdateFriendLinkBO {

    @NotNull(message = "友情链接的id不能为空")
    private Integer id;

    @NotBlank(message = "友情链接的url不能为空")
    private String url;

    @NotBlank(message = "友情链接的logo不能为空")
    private String logo;


}
