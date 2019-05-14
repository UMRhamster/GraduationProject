package com.whut.umrhamster.graduationproject.utils.other;

import android.util.Log;

public class TimeUtil {
    public static String int2String(int time){
        int minutes = time/60;
        int seconds = time%60;
        return fix0(minutes)+":"+fix0(seconds);
    }

    public static String millint2String(int mills){
        return int2String(mills/1000);
    }

    public static String millint2String(long mills){
        return int2String((int) (mills/1000));
    }

    public static String int2header(int duration){
        if (duration > 600000){
            return duration/3600+"小时";
        }else {
            return duration/60+"分钟";
        }
    }

    public static String time2String(int second){
        int minutes = second/60;
        int seconds = second%60;
        String str = seconds+"秒";

        if (minutes != 0){
            str=minutes+"分钟"+str;
        }
        return str;
    }

    public static String fix0(int value){
        if (value < 10){
            return "0"+value;
        }
        return ""+value;
    }

    //个人信息界面学习时长等级分级
    public static int[] time2level(int totalTime){
        int[] level = {1,0};
        int months = totalTime/(30*24*60*60);
        int weeks = totalTime/(7*24*60*60);
        int days = totalTime/(24*60*60);
        int hours = totalTime/(60*60);
        int minutes = totalTime/60;
        Log.d("--------TimeUtil------",""+minutes+" "+hours+" "+days+" "+weeks+" "+months);
        if (minutes < 999){  //小于999分钟
            level[0] = 1;
            level[1] = minutes%999;
        }else if (hours != 0 && hours < 999 && minutes > 999){  //大于999分钟且小于999小时
            level[0] = 2;
            level[1] = hours%999;
        }else if (days != 0 && days < 999 && hours > 999){      //大于999小时且小于999天
            level[0] = 3;
            level[1] = days%999;
        }else if (weeks != 0 && weeks < 999 && days > 999){     //大于999天且小于999周
            level[0] = 4;
            level[1] = weeks%999;
        }else if (months != 0 && weeks > 999){  //大于999周
            level[0] = 5;
            level[1] = months%999;
        }
        return level;
    }
}
