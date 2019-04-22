package com.whut.umrhamster.graduationproject.presenter;

public class ThreadPoolFactory {
    private static ThreadPoolProxy commonThreadPool;

    public static final int COMMON_CORE_POOL_SIZE = 5;
    public static final int COMMON_MAX_POOL_SIZE = 5;
    public static final long COMMON_KEEP_ALIVE_TIME = 1;

    private ThreadPoolFactory(){}

    public static ThreadPoolProxy getCommonThreadPool(){
        if (commonThreadPool == null){
            synchronized (ThreadPoolProxy.class){
                if (commonThreadPool == null){
                    commonThreadPool = new ThreadPoolProxy(COMMON_CORE_POOL_SIZE,COMMON_MAX_POOL_SIZE,COMMON_KEEP_ALIVE_TIME);
                }
            }
        }
        return commonThreadPool;
    }
}
