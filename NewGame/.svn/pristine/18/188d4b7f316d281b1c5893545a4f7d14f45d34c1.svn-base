package com.smallchill.test;

public class Demo1 extends Thread{

    public Demo1(String name){
        super(name);
    }

    @Override
    public void run() {
        while (!isInterrupted())
        System.out.println(getName()+"线程执行了");
    }

    public static void main(String[] args) {
        Demo1 d1 = new Demo1("ThreadOne");
        Demo1 d2 = new Demo1("ThreadTwo");
        d1.setName("线程1");
        d1.start();
        d2.start();
        d1.interrupt();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
