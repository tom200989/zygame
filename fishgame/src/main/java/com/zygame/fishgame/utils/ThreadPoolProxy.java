package com.zygame.fishgame.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolProxy {

    private int corePoolSize;// 核心线程数
    private int maximumPoolSize;// 最大有效线程数
    private long keepAliveTime;// 持续活跃时间
    private ThreadPoolExecutor mMExecutor;

    // 2 --> 声明构造方法, 声明三个属性

    /**
     * 初始化创建线程池对象的参数
     *
     * @param corePoolSize    核心线程数
     * @param maximumPoolSize 最大线程数
     * @param keepAliveTime   最大保持活跃时间
     */
    public ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.keepAliveTime = keepAliveTime;
    }

    // 3 --> 提供方法获取单例线程池

    /**
     * 获取一个线程池
     */
    public ThreadPoolExecutor getPoolExcutor(Runnable runnable) {
        // 3.1.单例 --> 注意, 在工厂模式中还需要用到一个单例, 确保线程池永远唯一
        // 先非空判断
        // --> 有, 则不创建
        // --> 没有, 则再加锁, 再非空判断
        // 非空判断
        if (mMExecutor == null) {
            synchronized (ThreadPoolExecutor.class) {
                // 再次判断, 防止多个对象挤入
                if (mMExecutor == null) {
                    // 4.创建线程池执行者
                    mMExecutor = new ThreadPoolExecutor(corePoolSize,// 核心线程数
                            maximumPoolSize,// 最大线程数
                            keepAliveTime,// 最大保留活动时间
                            TimeUnit.SECONDS,// 活动时间单位(秒,分,时)
                            new ArrayBlockingQueue<>(1000),// 任务队列
                            Executors.defaultThreadFactory(),// 线程工厂
                            new ThreadPoolExecutor.DiscardOldestPolicy()// 异常处理器
                    );
                }
            }
        }

        return mMExecutor;

    }

    // 5.对外提供执行一个线程任务的方法

    /**
     * 执行一个线程
     */
    public void executeTask(Runnable runnable) {
        mMExecutor = getPoolExcutor(runnable);
        mMExecutor.execute(runnable);
    }

    // 6.对外提供方法 --> 提交一个任务到候选队列

    /**
     * 提交一个线程(提交并不等于执行)
     */
    public void SubmitTask(Runnable runnable) {
        mMExecutor = getPoolExcutor(runnable);
        mMExecutor.submit(runnable);
    }

    // 7.对外提供方法 --> 结束一个线程任务

    /**
     * 销毁一个线程
     */
    public void removewTask(Runnable runnable) {
        mMExecutor = getPoolExcutor(runnable);
        mMExecutor.remove(runnable);
    }

    /**
     * 清空全部线程
     */
    public void shutAll() {
        if (!mMExecutor.isShutdown()) {
            mMExecutor.shutdownNow();
        }
    }

}
