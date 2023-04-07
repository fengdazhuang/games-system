package com.fzz.common.enums;

public enum ResponseStatusEnum {

    SUCCESS(200,true,""),
    FAILED(404,false,""),

    //admin
    ADMIN_NOT_EXISTS(201,false,"管理员用户名错误或不存在"),
    ADMIN_PASSWORD_ERROR(202,false,"管理员密码错误"),
    ADMIN_NOT_AVAILABLE(203,false ,"该管理员已被禁用" ),

    COMPETITION_CATEGORY_ADD_ERROR(301,false ,"赛事类型添加失败" ),
    COMPETITION_INFO_ADD_ERROR(302,false , "赛事项目添加失败"),

    SYSTEM_VALIDATE_CODE_ERROR(600,false ,"验证码错误" ),
    SYSTEM_BUSY_ERROR(601,false ,"系统繁忙，请稍后重试" ) ;




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
