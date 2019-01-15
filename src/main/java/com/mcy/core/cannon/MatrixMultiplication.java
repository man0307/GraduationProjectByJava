package com.mcy.core.cannon;

import java.util.ArrayList;

/**
 * 计算矩阵乘法的控制类
 *
 * @author manchaoyang
 */
public class MatrixMultiplication {
    private MatrixMultiplicationAlgorithm matrixMultiplicationAlgorithm;


    public int[][] computeByArray(int[][] matrix1, int[][] matrix2) {
        return matrixMultiplicationAlgorithm.computeByArray(matrix1,matrix2);
    }

    public ArrayList<ArrayList<Integer>> computeByList(ArrayList<ArrayList<Integer>> matrix1,
                                                       ArrayList<ArrayList<Integer>> matrix2) {
        return null;
    }

    public void setMatrixMultiplicationAlgorithm(MatrixMultiplicationAlgorithm matrixMultiplicationAlgorithm) {
        this.matrixMultiplicationAlgorithm = matrixMultiplicationAlgorithm;
    }

    public MatrixMultiplication(MatrixMultiplicationAlgorithm matrixMultiplicationAlgorithm) {
        this.matrixMultiplicationAlgorithm = matrixMultiplicationAlgorithm;
    }

    private boolean judgeMatrixLegal(int[][] matrix1, int[][] matrix2) {
        return false;
    }

    private boolean judgeMatrixLegal(ArrayList<ArrayList<Integer>> matrix1,
                                     ArrayList<ArrayList<Integer>> matrix2) {
        return false;
    }


}
