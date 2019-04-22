package com.whut.umrhamster.graduationproject.utils.other;

public class TimeUtil {
    public static String int2String(int time){
        int minutes = time/60;
        int seconds = time&60;
        return minutes+":"+seconds;
    }
}
