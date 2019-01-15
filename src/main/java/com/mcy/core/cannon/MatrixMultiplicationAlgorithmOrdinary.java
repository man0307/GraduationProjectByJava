package com.mcy.core.cannon;

import java.util.ArrayList;

public class MatrixMultiplicationAlgorithmOrdinary extends MatrixMultiplicationAlgorithm {

    @Override
    public int[][] computeByArray(int[][] matrix1, int[][] matrix2) {
        int row = matrix1.length;
        int column = matrix2[0].length;
        int computeLength = matrix1[0].length;
        int[][] result = new int[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                for (int k = 0; k < computeLength; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        return result;
    }

    @Override
    public ArrayList<ArrayList<Integer>> computeByList(ArrayList<ArrayList<Integer>> matrix1, ArrayList<ArrayList<Integer>> matrix2) {
        return null;
    }
}
