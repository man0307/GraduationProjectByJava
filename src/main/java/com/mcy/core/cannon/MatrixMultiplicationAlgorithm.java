package com.mcy.core.cannon;

import java.util.ArrayList;

/**
 * 计算矩阵乘法的接口
 */
public interface MatrixMultiplicationAlgorithm {
    /**
     * 二维数组形式的矩阵相乘
     * @param matrix1
     * @param matrix2
     * @return
     */
    public int[][] computeByArray(int[][] matrix1,int[][] matrix2);

    /**
     * 链表形式的矩阵相乘
     * @return
     */
    public ArrayList<ArrayList<Integer>> computeByList(ArrayList<ArrayList<Integer>> matrix1,
                                                       ArrayList<ArrayList<Integer>> matrix2);

}
