package com.fzz.common.enums;

public enum NewsStatusEnum {

    PUBLISHED(1),
    WITHDRAWED(2),
    UNPUBLISHED(3);



    private final Integer code;

    NewsStatusEnum(Integer code){
        this.code=code;
    }


    public Integer code(){
        return code;
    }

}
