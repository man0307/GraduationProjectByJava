package com.mcy.core.cannon;

import java.util.ArrayList;

/**
 * 计算矩阵乘法的控制类
 * @author manchaoyang
 */
public class MatrixMultiplication {
    private MatrixMultiplication matrixMultiplication;



    public int[][] computeByArray(int[][] matrix1,int[][] matrix2){
        return null;
    }

    public ArrayList<ArrayList<Integer>> computeByList(ArrayList<ArrayList<Integer>> matrix1,
                                                       ArrayList<ArrayList<Integer>> matrix2){
        return null;
    }

    public void setMatrixMultiplication(MatrixMultiplication matrixMultiplication) {
        this.matrixMultiplication = matrixMultiplication;
    }

    public MatrixMultiplication(MatrixMultiplication matrixMultiplication){
        this.matrixMultiplication = matrixMultiplication;
    }

    private boolean judgeMatrixLegal(int[][] matrix1,int[][] matrix2){
        return false;
    }

    private boolean judgeMatrixLegal(ArrayList<ArrayList<Integer>> matrix1,
                                     ArrayList<ArrayList<Integer>> matrix2){
        return false;
    }



}
