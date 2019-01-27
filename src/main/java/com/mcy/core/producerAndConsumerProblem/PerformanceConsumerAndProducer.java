package com.mcy.core.producerAndConsumerProblem;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author manchaoyang
 */
public class PerformanceConsumerAndProducer {
    private static int CORE_THREAD_NUMBER = 20;

    private ThreadFactory performanceConsumerAndProducerThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("PerformanceConsumerAndProducer-pool-%d").build();

    private ExecutorService performanceConsumerAndProducerThreadPool = new ThreadPoolExecutor(CORE_THREAD_NUMBER, CORE_THREAD_NUMBER * 5,
            100L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1024), performanceConsumerAndProducerThreadFactory,
            new ThreadPoolExecutor.AbortPolicy());

    public void show(int consumerNumber, int producerNumber, int queueSize) {
        SynchronizedQueue synchronizedQueue = new SynchronizedQueue(queueSize);
        for(int i=0;i<consumerNumber;i++){
            performanceConsumerAndProducerThreadPool.execute(new Consumer(synchronizedQueue));
        }
        for(int i=0;i<producerNumber;i++){
            performanceConsumerAndProducerThreadPool.execute(new Producer(synchronizedQueue));
        }
    }

    public static void main(String[] args){
        PerformanceConsumerAndProducer performanceConsumerAndProducer = new PerformanceConsumerAndProducer();
        performanceConsumerAndProducer.show(2,2,100);
        while (true){}
    }
}
