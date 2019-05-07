package com.mcy.core.producerAndConsumerProblem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author manchaoyang
 * 使用Lock来实现多生产者多消费者
 */
public class SynchronizedQueue {

    private LinkedList<Integer> synchronizedQueue = new LinkedList<Integer>();

    private int size = 0;

    private int limit;

    private Lock lock = new ReentrantLock();

    private Condition conditionPro = lock.newCondition();

    private Condition conditionCon = lock.newCondition();

    private boolean flag;

    public SynchronizedQueue(int limit) {
        this.limit = limit;
    }

    public void putData(Integer data) throws InterruptedException {
        lock.lock();
        try {
            while (size == limit || flag) {
                conditionCon.signal();
                conditionPro.await();
            }
            System.out.println("生产者:" + Thread.currentThread().getName() + " 向缓冲区加入了:" + data);
            synchronizedQueue.offer(data);
            size++;
            flag = true;
            conditionCon.signal();
        } finally {
            lock.unlock();
        }
    }

    public void outData() throws InterruptedException {
        lock.lock();
        try {
            while (size == 0 || !flag) {
                conditionCon.await();
            }
            Integer data = synchronizedQueue.poll();
            System.out.println("消费者:" + Thread.currentThread().getName() + " 消费了缓冲区中的数据:" + data);
            size--;
            flag = false;
            conditionPro.signal();
        } finally {
            lock.unlock();
        }
    }
}
