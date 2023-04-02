package com.fzz.common.enums;

public enum ResponseStatusEnum {

    SUCCESS(200,true,""),
    FAILED(404,false,""),

    //admin
    ADMIN_NOT_EXISTS(201,false,"管理员用户名错误或不存在"),
    ADMIN_PASSWORD_ERROR(202,false,"管理员密码错误")







    ;



    private final Integer code;

    private final String message;

    private final Boolean success;

    ResponseStatusEnum(Integer code,Boolean success,String message){
        this.code=code;
        this.success=success;
        this.message=message;
    }


    public Integer code(){
        return code;
    }

    public String message(){
        return message;
    }

    public Boolean success(){
        return success;
    }
}
