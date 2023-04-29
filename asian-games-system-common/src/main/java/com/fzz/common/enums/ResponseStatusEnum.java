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
    ADMIN_MODIFY_INFO_ERROR(207,false ,"管理员个人详细信息修改失败" ),
    UN_LOGIN(208,false ,"用户未登录" ),
    TICKET_INVALID(209,false,"会话失效，请重新登录！"),


    COMPETITION_CATEGORY_ADD_ERROR(301,false ,"赛事类型添加失败" ),
    COMPETITION_INFO_ADD_ERROR(302,false , "赛事项目添加失败"),
    COMPETITION_AREA_CREATE_ERROR(303,false ,"比赛赛区添加失败" ),
    COMPETITION_POSITION_CREATE_ERROR(304,false ,"比赛场所添加失败" ),
    COMPETITION_POSITION_DELETE_ERROR(305,false , "比赛场所删除失败"),
    COMPETITION_INFO_DELETE_ERROR(306,false ,"比赛项目删除失败" ),
    COMPETITION_INFO_UPDATE_ERROR(307,false ,"比赛项目修改失败" ),

    SYSTEM_VALIDATE_CODE_ERROR(600,false ,"验证码错误" ),
    SYSTEM_BUSY_ERROR(601,false ,"系统繁忙，请稍后重试" ),
    SYSTEM_DATE_PARSER_ERROR(602,false , "时间转换错误"),
    SYSTEM_FILE_NOT_FOUND(603,false ,"文件找不到了" ),
    FACE_NOT_FOUND(604,false , "人脸信息未找到"),

    FRIENDLINK_CREATE_ERROR(651,false ,"友情链接添加失败" ),
    FRIENDLINK_MODIFY_STATUS_ERROR(652, false, "友情链接修改状态失败"),
    FRIENDLINK_UPDATE_INFO_ERROR(653,false ,"友情链接修改失败" ),

    PLAYER_CREATE_ERROR(701,false ,"运动员添加失败" ),
    PLAYER_DELETE_ERROR(702,false ,"运动员删除失败" ),
    PLAYER_NOT_EXISTS(703,false , "该运动员不存在"),
    PLAYER_MODIFY_ERROR(704,false ,"修改运动员失败" ),


    JUDGE_CREATE_ERROR(801,false ,"裁判添加失败" ),
    JUDGE_DELETE_ERROR(802,false ,"裁判删除失败" ),
    JUDGE_NOT_EXISTS(803,false , "该裁判不存在"),
    JUDGE_MODIFY_ERROR(804,false ,"修改裁判失败" ),



    NEWS_CREATE_ERROR(901,false , "新闻创建失败" ),
    NEWS(902,false , "新闻创建失败" ),
    NEWS_COVER_IS_NULL(903,false , "新闻的封面为空"),
    NEWS_DELETE_ERROR(904,false ,"新闻删除失败" ),
    NEWS_WITHDRAW_ERROR(905,false,"新闻撤回失败"),

    //VOLUNTEER
    VOLUNTEER_IS_ALREADY_EXISTS(1001,false , "该邮箱志愿者账号已存在"),
    VOLUNTEER_REVIEW_ERROR(1002,false ,"志愿者审核失败" ),
    VOLUNTEER_VALIDATE_CODE_ERROR(1003,false ,"邮箱验证码错误" ),
    VOLUNTEER_REGISTER_FAILED(1004,false ,"志愿者注册失败" ),
    VOLUNTEER_REST_RISK_ERROR(1005,false ,"重新分配志愿者任务失败" ),
    VOLUNTEER_LOGIN_ERROR(1006,false ,"登录信息有误" ),
    VOLUNTEER_MODIFY_PASSWORD_FAILED(1007,false ,"密码修改失败" ),
    VOLUNTEER_MODIFY_INFO_FAILED(1008, false, "志愿者信息更新失败"),
    VOLUNTEER_MODIFY_TYPE_FAILED(1009,false ,"志愿者类型选择失败，请稍后再试" ),
    VOLUNTEER_APPLY_FAILED(1010,false ,"志愿者申请失败" ),
    VOLUNTEER_IS_NOT_EXISTS(1011,false ,"志愿者不存在，请先注册" ),
    VOLUNTEER_POSITION_CREATE_ERROR(1012,false , "志愿服务点创建失败"),
    VOLUNTEER_POSITION_DELETE_ERROR(1013, false, "志愿服务点删除失败");




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
