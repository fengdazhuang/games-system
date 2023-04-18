package com.fzz.common.enums;

public enum OrderColumnEnum {

    AGE(1),
    NAME(2),
    APPLY_TIME(3);

    private final Integer code;

    OrderColumnEnum(Integer code){
        this.code=code;
    }


    public Integer code(){
        return code;
    }

}
