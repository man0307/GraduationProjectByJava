package com.mcy.core.synchronizationAndMutualExclusion;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author manchaoyang 2018/1/17
 * 互斥问题 就是指一个资源可能会被多个线程或者进程争用 但是同一时间却只能被一个线程或者进程来使用
 */
public class MutuallyExclusive {

    public static class DataTemp {

        private int data = 0;

        public synchronized void addDataWithClock() {
            data++;
        }

        ;

        public void addDataWithoutClock() {
            data++;
        }

        public int getData() {
            return data;
        }
    }

    private static int TEST_NUMBER = 100;

    /**
     * 进行加锁自增
     */
    private static class TaskThread1 implements Runnable {

        private static Integer number = 0;

        private CountDownLatch countDownLatch;

        private DataTemp dataTemp;

        public TaskThread1(CountDownLatch countDownLatch, DataTemp dataTemp) {
            this.countDownLatch = countDownLatch;
            this.dataTemp = dataTemp;
        }

        public void run() {
            for (int i = 0; i < 100; i++) {
                dataTemp.addDataWithClock();
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

        private DataTemp dataTemp;

        public TaskThread2(CountDownLatch countDownLatch, DataTemp dataTemp) {
            this.countDownLatch = countDownLatch;
            this.dataTemp = dataTemp;
        }


        public void run() {
            for (int i = 0; i < 100; i++) {
                dataTemp.addDataWithoutClock();
            }
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
        DataTemp dataTemp = new DataTemp();
        CountDownLatch countDownLatch = new CountDownLatch(TEST_NUMBER);
        long begTime = System.currentTimeMillis();
        for (int i = 0; i < TEST_NUMBER; i++) {
            new Thread(new TaskThread1(countDownLatch, dataTemp)).start();
        }
        countDownLatch.await();
        long endTime = System.currentTimeMillis();
        System.out.println("加锁的方式得到的最终结果为:" + dataTemp.getData());
        System.out.println("加锁的方式需要 " + (endTime - begTime) + " ms");
        /**
         * 调用不加锁的方法
         */
        countDownLatch = new CountDownLatch(TEST_NUMBER);
        dataTemp = new DataTemp();
        MutuallyExclusive exclusive1 = new MutuallyExclusive();
        begTime = System.currentTimeMillis();
        for (int i = 0; i < TEST_NUMBER; i++) {
            new Thread(new TaskThread2(countDownLatch, dataTemp)).start();
        }
        countDownLatch.await();
        endTime = System.currentTimeMillis();
        System.out.println("不加锁的方式得到的最终结果为:" + dataTemp.getData());
        System.out.println("不加锁的方式需要 " + (endTime - begTime) + " ms");
    }
}
