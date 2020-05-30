package com.zygame.fishgame.utils;

public class ThreadPoolFactory {

    // 1.2.对外提供方法, 获取一个默认大小的线程池
    public static ThreadPoolProxy getDefaultPool() {
        return new ThreadPoolProxy(5, 30, 3000);
    }

    // 1.3.对外提供方法, 获取一个由外部指定大小的线程池
    public static ThreadPoolProxy getPoolProxy(int corePoolSize, int maximumPoolSize) {
        return new ThreadPoolProxy(corePoolSize, maximumPoolSize, 3000);
    }

}
