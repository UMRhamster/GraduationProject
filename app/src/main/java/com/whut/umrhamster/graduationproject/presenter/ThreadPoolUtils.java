package com.whut.umrhamster.graduationproject.presenter;

public class ThreadPoolUtils {
    public static void runTaskInThreadPool(Runnable task){
        ThreadPoolFactory.getCommonThreadPool().execute(task);
    }
}
