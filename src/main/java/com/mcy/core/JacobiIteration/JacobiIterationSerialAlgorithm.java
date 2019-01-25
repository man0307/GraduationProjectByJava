package com.mcy.core.JacobiIteration;

import java.util.Arrays;

/**
 * @author manchaoyang
 * 串行算法-雅克比迭代
 */
public class JacobiIterationSerialAlgorithm extends JacobiIteration {

    @Override
    public double[] jacobiIterationCompute(double[][] A, double[] B, double[] X) {
        if (!judgeLegal(A, B, X)) {
            return new double[0];
        }
        double[] result = new double[X.length];
        int N = X.length;
        double temp = 0d;
        while (true) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    temp += A[i][j] * X[j];
                }
                temp -= X[i] * A[i][i];
                result[i] = (B[i] - temp) / A[i][i];
                temp = 0d;
            }
            if (meetTheAccuracyRequirements(X, result)) {
                break;
            }
            System.arraycopy(result, 0, X, 0, X.length);
        }
        return result;
    }
}
