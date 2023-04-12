package com.fzz.personnel.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class EmailContent {

    private Map<String,Object> map = new HashMap<>();

    private String content;

    public EmailContent(String username,String password){
        this.content="您的亚运会管理员账户已创建成功！账户信息如下：\n用户名："+username+"\n初始密码："+
                password+"\n请保管好你的账户信息，可登录系统修改账户信息。";
    }

    public EmailContent(String password){
        this.content="您的亚运会管理员账户密码已重置！\n"+"重置密码为："+password+"\n请保管好你的账户密码，可登录系统修改您的账户密码。";
    }

    public static String getCreateAdminEmailContent(String username, String password){
        return new EmailContent(username,password).content;
    }

    public static String getResetPasswordEmailContent(String password){
        return new EmailContent(password).content;
    };

}
