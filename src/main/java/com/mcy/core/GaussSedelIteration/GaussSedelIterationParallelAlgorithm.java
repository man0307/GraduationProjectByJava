package com.mcy.core.GaussSedelIteration;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

import static com.mcy.core.utils.IterationUtils.judgeLegal;
import static com.mcy.core.utils.IterationUtils.meetTheAccuracyRequirements;

/**
 * @author manchaoyang
 * 高斯赛尔德 并行算法
 */
public class GaussSedelIterationParallelAlgorithm extends GaussSedelIterationAlgorithm {

    private static int CORE_THREAD_NUMBER = 20;

    private ThreadFactory gaussSedelIterationThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("gaussSedel-pool-%d").build();

    private ExecutorService gaussSedelThreadPool = new ThreadPoolExecutor(CORE_THREAD_NUMBER, CORE_THREAD_NUMBER * 5,
            100L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1024), gaussSedelIterationThreadFactory,
            new ThreadPoolExecutor.AbortPolicy());


    @Override
    public double[] gaussSedelIteration(double[][] A, double[] B, double[] X) throws InterruptedException {
        if (!judgeLegal(A, B, X)) {
            return null;
        }
        double[] res = new double[A.length];
        System.arraycopy(X, 0, res, 0, X.length);
        int N = A.length;
        int iterationTimes = 0;
        while (iterationTimes < getMaxIterationTimes()) {
            CountDownLatch countDownLatch = new CountDownLatch(N);
            for (int i = 0; i < N; i++) {
                gaussSedelThreadPool.execute(new GaussSedelComputeNode(A,X,B,res,countDownLatch,i));
            }
            countDownLatch.await();
            if (meetTheAccuracyRequirements(X, res, getErrorLimit())) {
                break;
            }
            System.arraycopy(res, 0, X, 0, X.length);
        }
        return res;
    }

    private class GaussSedelComputeNode implements Runnable {

        private double[][] A;
        private double[] X;
        private double[] B;
        private double[] res;
        private CountDownLatch countDownLatch;
        private int index;

        public GaussSedelComputeNode(double[][] a, double[] x, double[] b, double[] res, CountDownLatch countDownLatch, int index) {
            A = a;
            X = x;
            B = b;
            this.res = res;
            this.countDownLatch = countDownLatch;
            this.index = index;
        }

        public void run() {
            int N = res.length;
            double midRes = 0.0;
            for (int j = 0; j < index; j++) {
                midRes += A[index][j] * res[j];
            }
            for (int j = index + 1; j < N; j++) {
                midRes += A[index][j] * X[j];
            }
            res[index] = (B[index] - midRes) / A[index][index];
            countDownLatch.countDown();
        }
    }
}
