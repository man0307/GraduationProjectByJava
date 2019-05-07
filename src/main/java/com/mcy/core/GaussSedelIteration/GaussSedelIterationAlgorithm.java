package com.mcy.core.GaussSedelIteration;

public abstract class GaussSedelIterationAlgorithm {

    private double errorLimit = 0.0001;

    private int maxIterationTimes = 1000;

    /**
     * @param A 系数矩阵
     * @param B 常数向量
     * @param X 零级向量
     * @return
     */
    public abstract double[] gaussSedelIteration(double[][] A, double[] B, double[] X) throws InterruptedException;

    public double getErrorLimit() {
        return errorLimit;
    }

    public void setErrorLimit(double errorLimit) {
        this.errorLimit = errorLimit;
    }

    public int getMaxIterationTimes() {
        return maxIterationTimes;
    }

    public void setMaxIterationTimes(int maxIterationTimes) {
        this.maxIterationTimes = maxIterationTimes;
    }
}
