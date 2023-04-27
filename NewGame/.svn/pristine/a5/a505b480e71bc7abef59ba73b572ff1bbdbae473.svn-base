package com.smallchill.test;
/**
 * 带返回的线程
 */

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Demo2 extends Thread implements Callable<Integer> {
    public Demo2(String name) {
        super(name);
    }

    public static void main(String[] args) {
        Demo2 d2 = new Demo2("ThreadTwo");
        d2.start();
        FutureTask<Integer> task = new FutureTask<Integer>(d2);
        Thread th = new Thread(task);
        th.setName("2号执行");
        System.out.println(th.getName()+"执行");
        th.start();
        try {
            Integer end = task.get();
            System.out.println("算法返回值" + end);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("算法计算中");
        Thread.sleep(3000);
        return 1;
    }

    @Override
    public void run() {
        for(int i =0;i<5;i++){
            System.out.println(getName() + "线程执行了");
        }
    }
}
