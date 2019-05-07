package com.mcy.core.utils;

/**
 * @author manchaoyang
 * 2019/1/24
 */
public class IterationUtils {
    /**
     * 判断两个数组是否符合精度要求
     *
     * @param x
     * @param result
     * @return
     */
    public static boolean meetTheAccuracyRequirements(double[] x, double[] result, double errorLimit) {
        for (int i = 0; i < x.length; i++) {
            if (Math.abs(x[i] - result[i]) > errorLimit) {
                return false;
            }
        }
        return true;
    }


    /**
     * 判断高斯-赛尔德与雅克比的输入是否符合要求
     *
     * @param A
     * @param B
     * @param X
     * @return
     */
    public static boolean judgeLegal(double[][] A, double[] B, double[] X) {
        boolean noExistNull = A != null && B != null && X != null;
        boolean lengthLegal = (A.length == A[0].length) && (A.length == B.length) && (B.length == X.length);
        return noExistNull && lengthLegal;
    }
}
