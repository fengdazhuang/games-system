package com.fzz.model.bo;

import lombok.Data;


@Data
public class ListPlayersBO {

    private Integer competitionNameId;

    private Integer competitionCategoryId;

    private String name;

    private Integer pageNumber;

    private Integer pageSize;

    private String country;
}
