package com.mcy.core.cannon;

import java.util.ArrayList;

/**
 * 计算矩阵乘法的接口
 */
public abstract class MatrixMultiplicationAlgorithm {

    protected boolean judgeMatrixLegal(long[][] matrix1, long[][] matrix2) {
        return matrix1 == null || matrix2 == null || matrix1.length != matrix2.length
                || matrix1.length != matrix1[0].length || matrix2.length != matrix2[0].length;
    }

    /**
     * 二维数组形式的矩阵相乘
     *
     * @param matrix1
     * @param matrix2
     * @return
     */
    public abstract long[][] computeByArray(long[][] matrix1, long[][] matrix2) throws InterruptedException;

    protected void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (j != matrix[0].length - 1) {
                    System.out.print(matrix[i][j] + ",");
                } else {
                    System.out.print(matrix[i][j]);
                }
            }
            System.out.println();
        }
    }
}
