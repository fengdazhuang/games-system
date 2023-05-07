package com.fzz.common.utils;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    /**
     *  @author  mudaren
     *  @info  对当前时间进行增加 返回增加后的时间
     *  @param  day date
     *  @return time
     * */
    public static Date addDay(Date date, Integer day){
        if(date==null){
            date=new Date();
        }
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        try {
            calendar.add(Calendar.DAY_OF_MONTH,day);
        }catch (Exception e){
            calendar.add(Calendar.DAY_OF_MONTH,0);
        }
        return calendar.getTime();
    }

}
