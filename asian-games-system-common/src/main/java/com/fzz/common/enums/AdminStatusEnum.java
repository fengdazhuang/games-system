package com.fzz.common.enums;

public enum AdminStatusEnum {

    DISABLED(1),
    AVAILABLE(0),;


    private final Integer code;

    AdminStatusEnum(Integer code){
        this.code=code;
    }


    public Integer code(){
        return code;
    }

}
