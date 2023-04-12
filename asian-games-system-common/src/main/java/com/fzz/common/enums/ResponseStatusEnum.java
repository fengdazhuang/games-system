package com.fzz.common.enums;

public enum ResponseStatusEnum {

    SUCCESS(200,true,""),
    FAILED(404,false,""),

    //admin
    ADMIN_NOT_EXISTS(201,false,"管理员用户名错误或不存在"),
    ADMIN_PASSWORD_ERROR(202,false,"管理员密码错误"),
    ADMIN_NOT_AVAILABLE(203,false ,"该管理员已被禁用" ),
    ADMIN_USERNAME_ALREADY_EXISTS(204,false , "该用户名已存在"),
    ADMIN_CREATE_ERROR(205, false , "管理员添加失败"),
    ADMIN_STATUS_MODIFY_ERROR(206,false ,"管理员状态修改失败" ),

    COMPETITION_CATEGORY_ADD_ERROR(301,false ,"赛事类型添加失败" ),
    COMPETITION_INFO_ADD_ERROR(302,false , "赛事项目添加失败"),

    SYSTEM_VALIDATE_CODE_ERROR(600,false ,"验证码错误" ),
    SYSTEM_BUSY_ERROR(601,false ,"系统繁忙，请稍后重试" ),
    SYSTEM_DATE_PARSER_ERROR(602,false , "时间转换错误"),

    PLAYER_CREATE_ERROR(701,false ,"运动员添加失败" ),
    PLAYER_DELETE_ERROR(702,false ,"运动员删除失败" ),
    PLAYER_NOT_EXISTS(703,false , "该运动员不存在"),
    PLAYER_MODIFY_ERROR(704,false ,"修改运动员失败" ),


    JUDGE_CREATE_ERROR(801,false ,"裁判添加失败" ),
    JUDGE_DELETE_ERROR(802,false ,"裁判删除失败" ),
    JUDGE_NOT_EXISTS(803,false , "该裁判不存在"),
    JUDGE_MODIFY_ERROR(804,false ,"修改裁判失败" ),



    NEWS_CREATE_ERROR(901,false , "新闻创建失败" ),
    NEWS(902,false , "新闻创建失败" );




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
