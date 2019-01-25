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

    protected boolean judgeLegal(double[][] A, double[] B, double[] X) {
        boolean noExistNull = A != null && B != null && X != null;
        boolean lengthLegal = (A.length == A[0].length) && (A.length == B.length) && (B.length == X.length);
        return noExistNull && lengthLegal;
    }

    /**
     * 判断两个数组是否符合精度要求
     *
     * @param x
     * @param result
     * @return
     */
    protected boolean meetTheAccuracyRequirements(double[] x, double[] result) {
        for (int i = 0; i < x.length; i++) {
            if(Math.abs(x[i]-result[i])>getPrecision()){
                return false;
            }
        }
        return true;
    }

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
