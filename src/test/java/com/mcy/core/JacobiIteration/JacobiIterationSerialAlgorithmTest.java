package com.mcy.core.JacobiIteration;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class JacobiIterationSerialAlgorithmTest {

    private JacobiIterationSerialAlgorithm jacobiIterationSerialAlgorithm = new JacobiIterationSerialAlgorithm();

    private JacobiIterationParallelAlgorithm jacobiIterationParallelAlgorithm = new JacobiIterationParallelAlgorithm();

    private Random random = new Random();

    private double[][] mockMatrixA(int n) {
        double[][] res = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res[i][j] = random.nextInt(100)*1.0;
            }
        }
        return res;
    }

    private double[] mockMatrixB(int n) {
        double[] res = new double[n];
        for (int i = 0; i < n; i++) {
            res[i] = random.nextInt(100)*1.0 ;
        }
        return res;
    }

    private double[] mockMatrixX(int n) {
        double[] res = new double[n];
        return res;
    }


    @Test
    public void jacobiIterationSerialAlgorithmTest() throws InterruptedException {
        double[][] A = mockMatrixA(3);
        double[] B = mockMatrixB(3);
        double[] X = mockMatrixX(3);
        System.out.println(Arrays.deepToString(A));
        System.out.println(Arrays.toString(B));
        System.out.println(Arrays.toString(X));

        long begTime = System.currentTimeMillis();
        double[] result = jacobiIterationSerialAlgorithm.jacobiIterationCompute(A, B, X);
        long endTime = System.currentTimeMillis();
        System.out.println("串行雅克比算法耗时:" + (endTime - begTime) + "ms" + Arrays.toString(result));


        long begTime1 = System.currentTimeMillis();
        double[] result1 = jacobiIterationParallelAlgorithm.jacobiIterationCompute(A, B, X);
        long endTime1 = System.currentTimeMillis();
        System.out.println("并行雅克比算法耗时:" + (endTime1 - begTime1) + "ms" + Arrays.toString(result1));

    }
}
