package com.mcy.core.producerAndConsumerProblem;

import org.junit.Test;

public class PerformanceConsumerAndProducerTest {

    PerformanceConsumerAndProducer performanceConsumerAndProducer = new PerformanceConsumerAndProducer();

    @Test
    public void showTest(){
        performanceConsumerAndProducer.show(5,5,100);
    }
}
