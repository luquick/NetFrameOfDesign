package com.luquick.http.download;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 作者：Created by Luquick on 2018/6/25.
 * 邮箱: xxxxxx@163.com
 * QQ号：930982728
 * 微信：p11225630
 * 作用：xxxxxxx
 */
public class ThreadPollTest {
    ArrayBlockingQueue<Runnable> mQueue = new ArrayBlockingQueue<>(10);

    public void Test() {/*
        //ArrayBlockingQueue的测试
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 4, 60, TimeUnit.MILLISECONDS, mQueue);
        for (int i = 0; i < 14; i++) {
            final int index = i;
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("index = " + index + " mQueue size = "+mQueue.size());
                }
            });
        }*/

        /**
         * 终止线程 Test
         */
        MyRunnable runnable = new MyRunnable();
        Thread thread = new Thread(runnable);
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        thread.interrupt();//当前状态中断，会抛出异常
        runnable.flag = false;
    }

    /**
     * 终止线程测试
     */
    class MyRunnable implements Runnable {
        private volatile boolean flag = true;// voloatie 变量同步更改
        @Override
        public void run() {
            while (flag) {
                try {
                    Thread.sleep(2000);
                    System.out.println("running .... ");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
