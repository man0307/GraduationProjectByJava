package com.mcy.core.cannon;

import java.util.ArrayList;

/**
 * 计算矩阵乘法的接口
 */
public abstract class MatrixMultiplicationAlgorithm {

    protected boolean judgeMatrixLegal(int[][] matrix1, int[][] matrix2) {
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
    public abstract int[][] computeByArray(int[][] matrix1, int[][] matrix2);

    /**
     * 链表形式的矩阵相乘
     *
     * @return
     */
    public abstract ArrayList<ArrayList<Integer>> computeByList(ArrayList<ArrayList<Integer>> matrix1,
                                                                ArrayList<ArrayList<Integer>> matrix2);

}
