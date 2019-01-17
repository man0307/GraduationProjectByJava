package com.mcy.core.cannon;

import org.junit.Test;

import java.util.Arrays;

public class MatrixMultiplicationAlgorithmTest {
    /**
     * 40*40的矩阵
     */
    private long[][] matrix1 = new long[1000][1000];

    private long[][] matrix2 = new long[1000][1000];;

    long[][] matrix3 = {
            {1, 2, 3, 4},
            {1, 2, 3, 4},
            {1, 2, 3, 4},
            {1, 2, 3, 4},
    };

    long[][] matrix4 = {
            {1, 2, 3, 4},
            {1, 2, 3, 4},
            {1, 2, 3, 4},
            {1, 2, 3, 4},
    };

    private void mockMatrix(long[][] array){
        int number = 1;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                array[i][j] = number++;
            }
        }
    }
    @Test
    public void computeByArrayTest() throws InterruptedException {
        mockMatrix(matrix1);
        mockMatrix(matrix2);
        MatrixMultiplication matrixMultiplication = new MatrixMultiplication(new MatrixMultiplicationAlgorithmOrdinary());
        long begTime = System.currentTimeMillis();
        long[][] result = matrixMultiplication.computeByArray(matrix1, matrix2);
        long endTime = System.currentTimeMillis();
        System.out.println((endTime - begTime) + " ms");
//        printMatrix(result);
    }

    @Test
    public void cannonTest() throws InterruptedException {
        mockMatrix(matrix1);
        mockMatrix(matrix2);
        MatrixMultiplication matrixMultiplication = new MatrixMultiplication(new MatrixMultiplicationAlgorithmCannon());
        long begTime = System.currentTimeMillis();
        long[][] result = matrixMultiplication.computeByArray(matrix1, matrix2);
        long endTime = System.currentTimeMillis();
        System.out.println((endTime - begTime) + " ms");
//        printMatrix(result);
    }

    private void printMatrix(long[][] matrix) {
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
