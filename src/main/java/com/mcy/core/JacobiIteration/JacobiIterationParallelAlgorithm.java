package com.mcy.core.JacobiIteration;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

import static com.mcy.core.utils.IterationUtils.judgeLegal;
import static com.mcy.core.utils.IterationUtils.meetTheAccuracyRequirements;

/**
 * @author manchaoyang
 * 2019/1/24
 * 多线程-雅克比迭代求解线性方程的解
 */
public class JacobiIterationParallelAlgorithm extends JacobiIteration {

    private static int CORE_THREAD_NUMBER = 20;

    private ThreadFactory jacobiComputeThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("jacobi-pool-%d").build();

    private ExecutorService jacobiThreadPool = new ThreadPoolExecutor(CORE_THREAD_NUMBER, CORE_THREAD_NUMBER * 5,
            100L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1024), jacobiComputeThreadFactory,
            new ThreadPoolExecutor.AbortPolicy());


    @Override
    public double[] jacobiIterationCompute(double[][] A, double[] B, double[] X) throws InterruptedException {
        if (!judgeLegal(A, B, X)) {
            return new double[0];
        }
        double[] result = new double[A.length];
        int N = A.length;
        while (true) {
            CountDownLatch countDownLatch = new CountDownLatch(N);
            for (int i = 0; i < N; i++) {
                jacobiThreadPool.execute(new JacobiIterationComputeNode(A, B, X, result, i, countDownLatch));
            }
            countDownLatch.await();
            if (meetTheAccuracyRequirements(X, result, getPrecision())) {
                break;
            }
            System.arraycopy(result, 0, X, 0, X.length);
        }
        return result;
    }

    private class JacobiIterationComputeNode implements Runnable {
        private double[][] A;
        private double[] B;
        private double[] X;
        private double[] result;
        private int index;
        private CountDownLatch countDownLatch;

        public JacobiIterationComputeNode(double[][] A, double[] B, double[] X, double[] result, int index, CountDownLatch countDownLatch) {
            this.A = A;
            this.B = B;
            this.X = X;
            this.result = result;
            this.index = index;
            this.countDownLatch = countDownLatch;
        }


        public void run() {
            int N = result.length;
            double mid_result = 0.0;
            for (int j = 0; j < N; j++) {
                mid_result += A[index][j] * X[j];
            }
            mid_result -= X[index] * A[index][index];
            result[index] = (B[index] - mid_result) / A[index][index];
            countDownLatch.countDown();
        }
    }
}
