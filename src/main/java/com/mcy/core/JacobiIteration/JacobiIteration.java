package com.mcy.core.JacobiIteration;

import org.omg.CORBA.MARSHAL;

/**
 * 雅克比迭代
 */
public abstract class JacobiIteration {

    /**
     * 设置最大跌打次数
     */
    private int iterationTimes = 10000;

    /**
     * 设置结果的精度
     */
    private double precision = 0.001;

    /**
     * 用雅克比算法求解线性方程的解
     *
     * @param A
     * @param B
     * @param X
     * @return
     */
    public abstract double[] jacobiIterationCompute(double[][] A, double[] B, double[] X) throws InterruptedException;

    public int getIterationTimes() {
        return iterationTimes;
    }

    public void setIterationTimes(int iterationTimes) {
        this.iterationTimes = iterationTimes;
    }

    public double getPrecision() {
        return precision;
    }

    public void setPrecision(double precision) {
        this.precision = precision;
    }
}
