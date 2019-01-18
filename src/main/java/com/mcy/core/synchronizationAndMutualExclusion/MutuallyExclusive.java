package com.mcy.core.synchronizationAndMutualExclusion;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author manchaoyang 2018/1/17
 * 互斥问题 就是指一个资源可能会被多个线程或者进程争用 但是同一时间却只能被一个线程或者进程来使用
 */
public class MutuallyExclusive {

    private static int TEST_NUMBER = 102400;

    /**
     * 进行加锁自增
     */
    private static class TaskThread1 implements Runnable {

        private static Integer number = 0;

        private CountDownLatch countDownLatch;

        public TaskThread1(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        public void run() {
            synchronized (number) {
                ++number;
            }
            countDownLatch.countDown();
        }

        public static Integer getNumber() {
            return number;
        }
    }


    /**
     * 进行不加锁自增
     */
    private static class TaskThread2 implements Runnable {

        private static Integer number = 0;

        private CountDownLatch countDownLatch;

        public TaskThread2(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        public void run() {
            number++;
            countDownLatch.countDown();
        }

        public static Integer getNumber() {
            return number;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        /**
         * 调用加锁的方法
         */
        MutuallyExclusive exclusive = new MutuallyExclusive();
        CountDownLatch countDownLatch = new CountDownLatch(TEST_NUMBER);
        long begTime = System.currentTimeMillis();
        for (int i = 0; i < TEST_NUMBER; i++) {
            new TaskThread1(countDownLatch).run();
        }
        countDownLatch.await();
        long endTime = System.currentTimeMillis();
        System.out.println("加锁的方式得到的最终结果为:" + TaskThread1.getNumber());
        System.out.println("加锁的方式需要 " + (endTime - begTime) + " ms");
        /**
         * 调用不加锁的方法
         */
        countDownLatch = new CountDownLatch(TEST_NUMBER);
        MutuallyExclusive exclusive1 = new MutuallyExclusive();
        begTime = System.currentTimeMillis();
        for (int i = 0; i < TEST_NUMBER; i++) {
            new TaskThread2(countDownLatch).run();
        }
        countDownLatch.await();
        endTime = System.currentTimeMillis();
        System.out.println("不加锁的方式得到的最终结果为:" + TaskThread2.getNumber());
        System.out.println("不加锁的方式需要 " + (endTime - begTime) + " ms");
    }
}
