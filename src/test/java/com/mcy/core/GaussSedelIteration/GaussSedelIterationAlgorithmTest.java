package com.mcy.core.GaussSedelIteration;

import org.junit.Test;

import java.util.Arrays;

public class GaussSedelIterationAlgorithmTest {

    private GaussSedelIterationAlgorithm gaussSedelIterationAlgorithmSerial = new GaussSedelIterationSerialAlgorithm();

    private GaussSedelIterationAlgorithm gaussSedelIterationParallelAlgorithm = new GaussSedelIterationParallelAlgorithm();


    @Test
    public void serialTest() throws InterruptedException {
        double[][] a={{5,2,1},{-1,4,2},{2,-3,10}};
        double b[]={-12,20,3};
        double[] x={0,0,0};

        double[] res = gaussSedelIterationAlgorithmSerial.gaussSedelIteration(a,b,x);

        System.out.println(Arrays.toString(res));
    }

    @Test
    public void parallelTest() throws InterruptedException {
        double[][] a={{5,2,1},{-1,4,2},{2,-3,10}};
        double b[]={-12,20,3};
        double[] x={0,0,0};

        double[] res = gaussSedelIterationParallelAlgorithm.gaussSedelIteration(a,b,x);

        System.out.println(Arrays.toString(res));
    }
}
