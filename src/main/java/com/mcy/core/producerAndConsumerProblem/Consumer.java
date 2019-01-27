package com.mcy.core.producerAndConsumerProblem;

/**
 * @author manchaoyang
 */
public class Consumer implements Runnable {
    private SynchronizedQueue synchronizedQueue;

    public Consumer(SynchronizedQueue synchronizedQueue) {
        this.synchronizedQueue = synchronizedQueue;
    }

    public void run() {
        while (true) {
            try {
                synchronizedQueue.outData();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
