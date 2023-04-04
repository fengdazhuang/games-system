package com.fzz.common.utils;

import com.wf.captcha.*;

import java.util.Random;

/**
 * 随机生成验证码工具类
 */
public class ValidateCodeUtils {
    /**
     * 随机生成验证码
     * @param length 长度为4位或者6位
     * @return
     */
    public static Integer generateValidateCode(int length){
        Integer code =null;
        if(length == 4){
            code = new Random().nextInt(9999);//生成随机数，最大为9999
            if(code < 1000){
                code = code + 1000;//保证随机数为4位数字
            }
        }else if(length == 6){
            code = new Random().nextInt(999999);//生成随机数，最大为999999
            if(code < 100000){
                code = code + 100000;//保证随机数为6位数字
            }
        }else{
            throw new RuntimeException("只能生成4位或6位数字验证码");
        }
        return code;
    }

    /**
     * 随机生成指定长度字符串验证码
     * @param length 长度
     * @return
     */
    public static String generateValidateCode4String(int length){
        Random rdm = new Random();
        String hash1 = Integer.toHexString(rdm.nextInt());
        String capstr = hash1.substring(0, length);
        return capstr;
    }


    /**
     * 生成验证码图片
     * @return 图片的base64字符串
     */
    public static SpecCaptcha validateCodeImage() {
        //png图
        SpecCaptcha captcha = new SpecCaptcha(130, 40);
        System.out.println(captcha.text());
        return captcha;
        /*//gif动图
        GifCaptcha gifCaptcha = new GifCaptcha(130, 40);
        FileOutputStream outputStream1 = new FileOutputStream(path + "1gif类型.gif");
        //设置纯大写字母验证码TYPE_ONLY_UPPER
        gifCaptcha.setCharType(Captcha.TYPE_ONLY_UPPER);
        //设置字体
        gifCaptcha.setFont(Captcha.FONT_5);
        System.out.println(gifCaptcha.text());
        gifCaptcha.out(outputStream1);
        //中文类型
        ChineseCaptcha chineseCaptcha = new ChineseCaptcha(130, 40);
        FileOutputStream outputStream2 = new FileOutputStream(path + "2中文类型.png");
        System.out.println(chineseCaptcha.text()); chineseCaptcha.out(outputStream2);
        //中文类型gif动图
        ChineseGifCaptcha chineseGifCaptcha = new ChineseGifCaptcha(130, 40);
        FileOutputStream outputStream3 = new FileOutputStream(path + "3中文类型动图.gif");
        System.out.println(chineseGifCaptcha.text()); chineseGifCaptcha.out(outputStream3);
        //算术类型
        ArithmeticCaptcha arithmeticCaptcha = new ArithmeticCaptcha(180, 40);
        FileOutputStream outputStream4 = new FileOutputStream(path + "4算术类型.png");
        //设置几位数的
        // 运算，几个数计算
        arithmeticCaptcha.setLen(3);
        //打印算式
        System.out.println(arithmeticCaptcha.getArithmeticString());
        //打印算式的结果
        System.out.println(arithmeticCaptcha.text());
        arithmeticCaptcha.out(outputStream4);*/

    }
}
