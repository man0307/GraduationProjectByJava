package com.mcy.core.cannon;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * 用Cannon算法实现矩阵相乘
 *
 * @author manchaoyang 2019/1/15
 */
public class MatrixMultiplicationAlgorithmCannon extends MatrixMultiplicationAlgorithm {

    private static int AVAILABLE_PROCESSORS = 16;

    private ThreadFactory cannonComputeThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("cannon-compute-matrix-pool-%d").build();

    private ExecutorService cannonThreadPool = new ThreadPoolExecutor(AVAILABLE_PROCESSORS, AVAILABLE_PROCESSORS,
            100L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1024), cannonComputeThreadFactory,
            new ThreadPoolExecutor.AbortPolicy());

    /**
     * 按照一定的规则将矩阵分成的块循环整体左移
     *
     * @param matrix
     * @param subMatrixLength
     * @param step
     */
    private void loopLeftShift(long[][] matrix, int subMatrixLength, int step) {
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
    private void loopUpShift(long[][] matrix, int subMatrixLength, int step) {
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
    private void loopLeftShiftByRow(long[][] matrix, int subMatrixLength, int rowNum, int step) {
        int colMatrixCount = getMatrixCount(matrix.length, subMatrixLength);
        long[][] subMatrix = extractSubMatrix(matrix, rowNum, 0, subMatrixLength);
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
    private void loopUpShiftByColumn(long[][] matrix, int subMatrixLength, int columnNum, int step) {
        int colMatrixCount = getMatrixCount(matrix.length, subMatrixLength);
        long[][] subMatrix = extractSubMatrix(matrix, 0, columnNum, subMatrixLength);
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
    private long[][] extractSubMatrix(long[][] matrix, int i, int j, int subMatrixLength) {
        long[][] subMatrix = new long[subMatrixLength][subMatrixLength];
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
    private long[][] copyMatrixToPointIndex(long[][] matrix, long[][] subMatrix, int i, int j) {
        int subMatrixLength = subMatrix.length;
        long[][] oldSubMatrix = new long[subMatrixLength][subMatrixLength];
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
        int mod = (int) Math.sqrt(AVAILABLE_PROCESSORS);
        while (mod > 0 && matrixLength % mod != 0) {
            mod--;
        }
        return matrixLength / mod;
    }

    /**
     * 进行Cannon之前先进行数据对准
     *
     * @param matrix1
     * @param matrix2
     */
    private void dataAlignment(long[][] matrix1, long[][] matrix2, int subMatrixLength) {
        for (int i = 1; i < matrix1.length / subMatrixLength; i++) {
            loopLeftShiftByRow(matrix1, subMatrixLength, i, i);
        }
        for (int j = 1; j < matrix2[0].length / subMatrixLength; j++) {
            loopUpShiftByColumn(matrix2, subMatrixLength, j, j);
        }
    }

    @Override
    public long[][] computeByArray(final long[][] matrix1, final long[][] matrix2) throws InterruptedException {
        if (judgeMatrixLegal(matrix1, matrix2)) {
            return null;
        }
        long[][] result = new long[matrix1.length][matrix1[0].length];
        int matrixLength = matrix1.length;
        final int subMatrixLength = getSubMatrixLength(matrixLength);
        //数据对准
        dataAlignment(matrix1, matrix2, subMatrixLength);
        //计算点积
        for (int m = 0; m < matrixLength / subMatrixLength; m++) {
            CountDownLatch countDownLatch = new CountDownLatch((matrixLength / subMatrixLength) * (matrixLength / subMatrixLength));
            long begTime = System.currentTimeMillis();
            for (int i = 0; i < matrixLength / subMatrixLength; i++) {
                for (int j = 0; j < matrixLength / subMatrixLength; j++) {
                    cannonThreadPool.execute(new ComputingUnitTask(matrix1, matrix2, result, i, j, m, countDownLatch, subMatrixLength));
                }
            }
            countDownLatch.await();

//            loopLeftShift(matrix1, subMatrixLength, 1);
//            loopUpShift(matrix2, subMatrixLength, 1);
        }

        return result;
    }


    /**
     * 每个计算单元代表一个处理器线程
     */
    private class ComputingUnitTask implements Runnable {
        /**
         * 下面三个矩阵不产生写冲突 不需要加锁
         */
        private long[][] matrix1;
        private long[][] matrix2;
        private long[][] result;
        private int i;
        private int j;
        private int times;
        private CountDownLatch taskNumber;
        private int subMatrixLength;

        public ComputingUnitTask(long[][] matrix1, long[][] matrix2, long[][] result, int i, int j, int times,
                                 CountDownLatch taskNumber, int subMatrixLength) {
            this.matrix1 = matrix1;
            this.matrix2 = matrix2;
            this.result = result;
            this.i = i;
            this.j = j;
            this.taskNumber = taskNumber;
            this.subMatrixLength = subMatrixLength;
            this.times = times;
        }

        public void run() {
            int rowBeg = i * subMatrixLength;
            int columnBeg = j * subMatrixLength;
            int rowData = (i + times) % (matrix1.length / subMatrixLength) * subMatrixLength;
            int columnData = (j + times) % (matrix1.length / subMatrixLength) * subMatrixLength;
            for (int h = 0; h < subMatrixLength; h++) {
                for (int m = 0; m < subMatrixLength; m++) {
                    for (int k = 0; k < subMatrixLength; k++) {
                        result[rowBeg + h][columnBeg + m] += matrix1[rowBeg + h][columnData + k] * matrix2[rowData + k][columnBeg + m];
                    }
                }
            }

            taskNumber.countDown();
        }

        private void matrixMultiplication() {

        }
    }

}
