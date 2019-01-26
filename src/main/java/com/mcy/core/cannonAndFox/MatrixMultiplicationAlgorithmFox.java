package com.mcy.core.cannonAndFox;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author manchaoyang
 * 用Fox乘法来计算方阵乘积
 */
public class MatrixMultiplicationAlgorithmFox extends MatrixMultiplicationAlgorithm {

    private static int AVAILABLE_PROCESSORS = 4;

    private ThreadFactory cannonComputeThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("cannonAndFox-compute-matrix-pool-%d").build();

    private ExecutorService cannonThreadPool = new ThreadPoolExecutor(AVAILABLE_PROCESSORS, AVAILABLE_PROCESSORS,
            100L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1024), cannonComputeThreadFactory,
            new ThreadPoolExecutor.AbortPolicy());

    @Override
    public long[][] computeByArray(final long[][] matrix1, final long[][] matrix2) throws InterruptedException {
        if (judgeMatrixLegal(matrix1, matrix2)) {
            return null;
        }
        long[][] result = new long[matrix1.length][matrix1[0].length];
        int matrixLength = matrix1.length;
        final int subMatrixLength = getSubMatrixLength(matrixLength);
        //Fox算法无需数据对准 直接进行√p 次的算法循环即可
        for (int m = 0; m < matrixLength / subMatrixLength; m++) {
            CountDownLatch countDownLatch = new CountDownLatch((matrixLength / subMatrixLength) * (matrixLength / subMatrixLength));
            //选取对角块
            for (int i = 0; i < matrixLength / subMatrixLength; i++) {
                int colNum = (i + m) % (matrixLength / subMatrixLength);
                for (int h = 0; h < matrixLength / subMatrixLength; h++) {
                    cannonThreadPool.execute(new ComputingUnitTask(matrix1, matrix2, result, i, colNum, (i + m) % (matrixLength / subMatrixLength), h, i, h, countDownLatch, subMatrixLength));
                }
            }
            countDownLatch.await();
        }

        return result;
    }


    private class ComputingUnitTask implements Runnable {
        /**
         * 下面三个矩阵不产生写冲突 不需要加锁
         */
        private long[][] matrix1;
        private long[][] matrix2;
        private long[][] result;
        private int i;
        private int j;
        private int row;
        private int column;
        private int resultRow;
        private int resultColumn;
        private CountDownLatch taskNumber;
        private int subMatrixLength;

        public ComputingUnitTask(long[][] matrix1, long[][] matrix2, long[][] result, int i, int j,
                                 int row, int column, int resultRow, int resultColumn,
                                 CountDownLatch taskNumber, int subMatrixLength) {
            this.matrix1 = matrix1;
            this.matrix2 = matrix2;
            this.result = result;
            this.i = i;
            this.j = j;
            this.row = row;
            this.column = column;
            this.resultRow = resultRow;
            this.resultColumn = resultColumn;
            this.taskNumber = taskNumber;
            this.subMatrixLength = subMatrixLength;
        }

        public void run() {
            int rowBeg = i * subMatrixLength;
            int columnBeg = j * subMatrixLength;
            int rowData = row * subMatrixLength;
            int columnData = column * subMatrixLength;
            int resRow = resultRow * subMatrixLength;
            int resColumn = resultColumn * subMatrixLength;
            for (int h = 0; h < subMatrixLength; h++) {
                for (int m = 0; m < subMatrixLength; m++) {
                    for (int k = 0; k < subMatrixLength; k++) {
                        result[resRow + h][resColumn + m] += matrix1[rowBeg + h][columnBeg + k] * matrix2[rowData + k][columnData + m];
                    }
                }
            }
            taskNumber.countDown();
        }
    }

    private int getSubMatrixLength(int matrixLength) {
        int mod = (int) Math.sqrt(AVAILABLE_PROCESSORS);
        while (mod > 0 && matrixLength % mod != 0) {
            mod--;
        }
        return matrixLength / mod;
    }

}
