package com.example.captain.compass;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by captain on 2018/1/30.
 */

public class ThreadPoolManager {
    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static void fixedThreadPoolExecute(Runnable runnable) {
        fixedThreadPool.execute(runnable);
    }
}
