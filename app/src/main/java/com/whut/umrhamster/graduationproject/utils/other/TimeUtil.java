package com.whut.umrhamster.graduationproject.utils.other;

public class TimeUtil {
    public static String int2String(int time){
        int minutes = time/60;
        int seconds = time&60;
        return fix0(minutes)+":"+fix0(seconds);
    }

    public static String int2header(int duration){
        int second = duration;
        if (duration > 600000){
            return duration/3600+"小时";
        }else {
            return duration/60+"分钟";
        }
    }

    public static String fix0(int value){
        if (value < 10){
            return "0"+value;
        }
        return ""+value;
    }
}
