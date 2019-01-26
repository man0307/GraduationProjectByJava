package com.mcy.core.GaussSedelIteration;

import static com.mcy.core.utils.IterationUtils.judgeLegal;
import static com.mcy.core.utils.IterationUtils.meetTheAccuracyRequirements;

public class GaussSedelIterationSerialAlgorithm extends GaussSedelIterationAlgorithm {


    @Override
    public double[] gaussSedelIteration(double[][] A, double[] B, double[] X) {
        if (!judgeLegal(A, B, X)) {
            return null;
        }
        double[] res = new double[A.length];
        System.arraycopy(X, 0, res, 0, X.length);
        int N = A.length;
        int iterationTimes = 0;
        while (iterationTimes < getMaxIterationTimes()) {
            for (int i = 0; i < N; i++) {
                double midRes = 0.0;
                for (int j = 0; j < i; j++) {
                    midRes += A[i][j] * res[j];
                }
                for (int j = i + 1; j < N; j++) {
                    midRes += A[i][j] * X[j];
                }
                res[i] = (B[i] - midRes) / A[i][i];
            }
            if(meetTheAccuracyRequirements(X,res,getErrorLimit())){
                break;
            }
            System.arraycopy(res, 0, X, 0, X.length);
        }
        return res;
    }
}
