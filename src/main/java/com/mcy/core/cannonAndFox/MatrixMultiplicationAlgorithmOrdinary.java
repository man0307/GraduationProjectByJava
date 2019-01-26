package com.mcy.core.cannonAndFox;

/**
 * @author manchaoyang
 */
public class MatrixMultiplicationAlgorithmOrdinary extends MatrixMultiplicationAlgorithm {

    @Override
    public long[][] computeByArray(long[][] matrix1, long[][] matrix2) {
        int row = matrix1.length;
        int column = matrix2[0].length;
        int computeLength = matrix1[0].length;
        long[][] result = new long[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                for (int k = 0; k < computeLength; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        return result;
    }

}
