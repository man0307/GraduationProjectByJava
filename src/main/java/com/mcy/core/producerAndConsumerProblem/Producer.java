package com.mcy.core.producerAndConsumerProblem;

import java.util.Random;

/**
 * @author manchaoyang
 */
public class Producer implements Runnable {

    private Random random;

    private SynchronizedQueue synchronizedQueue;

    public Producer(SynchronizedQueue synchronizedQueue) {
        this.synchronizedQueue = synchronizedQueue;
        random = new Random();
    }

    public void run() {
        while (true) {
            try {
                synchronizedQueue.putData(random.nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
