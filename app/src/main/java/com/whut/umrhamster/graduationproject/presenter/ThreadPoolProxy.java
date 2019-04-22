package com.whut.umrhamster.graduationproject.presenter;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolProxy {
    private ThreadPoolExecutor executor;
    private int corePoolSize;
    private int maximumPoolSize;
    private long keepAliveTime;

    public ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long keepAliveTime){
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.keepAliveTime = keepAliveTime;
    }

    public ThreadPoolExecutor initThreadPoolExecutor(){
        if (executor == null){
            synchronized (ThreadPoolProxy.class){
                if (executor == null){
                    executor = new ThreadPoolExecutor(corePoolSize,
                            maximumPoolSize,
                            keepAliveTime,
                            TimeUnit.SECONDS,
                            new LinkedBlockingQueue<Runnable>(),
                            Executors.defaultThreadFactory(),
                            new ThreadPoolExecutor.AbortPolicy());
                }
            }
        }
        return executor;
    }

    public void  execute(Runnable task){
        initThreadPoolExecutor();
        executor.execute(task);
    }

    public void remove(Runnable task){
        initThreadPoolExecutor();
        executor.remove(task);
    }

    public Future<?> submit(Runnable task){
        initThreadPoolExecutor();
        return executor.submit(task);
    }
}
