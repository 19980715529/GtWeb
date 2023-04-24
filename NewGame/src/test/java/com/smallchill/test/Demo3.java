package com.smallchill.test;
/**
 * 线程池
 */

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Demo3 {
    static ExecutorService threadPool = Executors.newCachedThreadPool();

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 10; i++) {
            //线程池执行
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());//获取当前执行线程的名字
                    Thread th = new Thread();
                    th.setName("第一批新线程");
                    th.start();
                    System.out.println(th.getName());
                }
            });
        }
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    int i = 0;
                    if (i<1) {
                        //线程池执行
                        threadPool.execute(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println(Thread.currentThread().getName() + "第二批");
                                threadPool.shutdown();
                                System.out.println("线程结束");
                            }
                        });
                    }
                }
            }, 3000);
        System.out.println("线程等待");
        threadPool.wait(3000);
    }
}
