package com.mcy.core.cannon;

import java.util.ArrayList;

/**
 * 用Cannon算法实现矩阵相乘
 *
 * @author manchaoyang 2019/1/15
 */
public class MatrixMultiplicationAlgorithmCannon extends MatrixMultiplicationAlgorithm {

    /**
     * 按照一定的规则将矩阵分成的块循环整体左移
     *
     * @param matrix
     * @param subMatrixLength
     * @param step
     */
    private void loopLeftShift(int[][] matrix, int subMatrixLength, int step) {
        int rowMatrixCount = getMatrixCount(matrix.length, subMatrixLength);
        for (int i = 0; i < rowMatrixCount; i++) {
            loopLeftShiftByRow(matrix, subMatrixLength, i, step);
        }
    }

    /**
     * 按照一定的规则将矩阵分成的块循环整体上移
     *
     * @param matrix
     * @param subMatrixLength
     * @param step
     */
    private void loopUpShift(int[][] matrix, int subMatrixLength, int step) {
        int colMatrixCount = getMatrixCount(matrix.length, subMatrixLength);
        for (int j = 0; j < colMatrixCount; j++) {
            loopUpShiftByColumn(matrix, subMatrixLength, j, step);
        }
    }


    /**
     * 按照一定的规则将矩阵分成的块按某列循环移动
     *
     * @param matrix
     * @param subMatrixLength
     * @param step
     */
    private void loopLeftShiftByRow(int[][] matrix, int subMatrixLength, int rowNum, int step) {
        int colMatrixCount = getMatrixCount(matrix.length, subMatrixLength);
        int[][] subMatrix = extractSubMatrix(matrix, rowNum, 0, subMatrixLength);
        int nowIndex = 0;
        for (int k = 0; k < colMatrixCount; k++) {
            int nextIndex = (nowIndex + step) % colMatrixCount;
            subMatrix = copyMatrixToPointIndex(matrix, subMatrix, rowNum, nextIndex);
            nowIndex = nextIndex;
        }
    }

    /**
     * 按照一定的规则将矩阵分成的块按某行循环移动
     *
     * @param matrix
     * @param subMatrixLength
     * @param step
     */
    private void loopUpShiftByColumn(int[][] matrix, int subMatrixLength, int columnNum, int step) {
        int colMatrixCount = getMatrixCount(matrix.length, subMatrixLength);
        int[][] subMatrix = extractSubMatrix(matrix, 0, columnNum, subMatrixLength);
        int nowIndex = 0;
        for (int k = 0; k < colMatrixCount; k++) {
            int nextIndex = (nowIndex + step) % colMatrixCount;
            subMatrix = copyMatrixToPointIndex(matrix, subMatrix, nextIndex, columnNum);
            nowIndex = nextIndex;
        }
    }

    private int getMatrixCount(int matrixLength, int subMatrixLength) {
        return matrixLength / subMatrixLength;
    }

    /**
     * 按照子矩阵编号抽取子矩阵
     *
     * @param matrix
     * @param i
     * @param j
     * @return
     */
    private int[][] extractSubMatrix(int[][] matrix, int i, int j, int subMatrixLength) {
        int[][] subMatrix = new int[subMatrixLength][subMatrixLength];
        int begRow = i * subMatrixLength;
        int begCol = j * subMatrixLength;
        for (int row = 0; row < subMatrixLength; row++) {
            for (int col = 0; col < subMatrixLength; col++) {
                subMatrix[row][col] = matrix[begRow + row][begCol + col];
            }
        }
        return subMatrix;
    }

    /**
     * 将指定的子矩阵拷贝到相应的位置将原子矩阵元素返回
     *
     * @param matrix
     * @param subMatrix
     * @param i
     * @param j
     * @return
     */
    private int[][] copyMatrixToPointIndex(int[][] matrix, int[][] subMatrix, int i, int j) {
        int subMatrixLength = subMatrix.length;
        int[][] oldSubMatrix = new int[subMatrixLength][subMatrixLength];
        int begRow = i * subMatrixLength;
        int begCol = j * subMatrixLength;
        for (int row = 0; row < subMatrixLength; row++) {
            for (int col = 0; col < subMatrixLength; col++) {
                oldSubMatrix[row][col] = matrix[begRow + row][begCol + col];
                matrix[begRow + row][begCol + col] = subMatrix[row][col];
            }
        }
        return oldSubMatrix;
    }

    private int getSubMatrixLength(int matrixLength) {
        int processorsCount = Runtime.getRuntime().availableProcessors();
        int subMatrixLength = (int) Math.sqrt(processorsCount);
        while (subMatrixLength > 0 && matrixLength % subMatrixLength != 0) {
            subMatrixLength--;
        }
        return subMatrixLength;
    }

    /**
     * 进行Cannon之前先进行数据对准
     *
     * @param matrix1
     * @param matrix2
     */
    private void dataAlignment(int[][] matrix1, int[][] matrix2) {
        //先按行移动matrix1
        //再按列移动matrix2
    }

    @Override
    public int[][] computeByArray(int[][] matrix1, int[][] matrix2) {
        if (judgeMatrixLegal(matrix1, matrix2)) {
            return null;
        }
        int[][] result = new int[matrix1.length][matrix1[0].length];
        int matrixLength = matrix1.length;
        int subMatrixLength = getSubMatrixLength(matrixLength);
        //数据对准

        //计算点积
        return result;
    }

    @Override
    public ArrayList<ArrayList<Integer>> computeByList(ArrayList<ArrayList<Integer>> matrix1, ArrayList<ArrayList<Integer>> matrix2) {
        return null;
    }

    /**
     * 每个计算单元代表一个处理器线程
     */
    private class ComputingUnit implements Runnable {

        public void run() {

        }
    }
}
