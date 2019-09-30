package br.pucrs.sma.util;

/**
 * A Singleton Class that is a Linear congruential generator of random numbers
 */
public class NumberGenerator {

    private static long a = 1140671485;
    private static long c = 12820163;
    private static long M = 16777218;
    public static long X0 = 1;
    private static long Xi = X0;

    //public static int maxRandoms = Integer.MAX_VALUE;

    private static NumberGenerator instance;

    // Variables for testing purpose
    public static int maxRandoms = 16;
    public static int indexArray=0;
    public static double[] randomNumbersTest = {0.2176, 0.0103, 0.1109, 0.3456, 0.9910, 0.2323, 0.9211, 0.0322, 0.1211, 0.5131, 0.7208, 0.9172, 0.9922, 0.8324, 0.5011, 0.2931};

    private NumberGenerator() {
    }

    public static NumberGenerator getInstance() {
        if (instance == null) {
            synchronized (NumberGenerator.class) {
                if (instance == null) {
                    instance = new NumberGenerator();
                }
            }
        }
        return instance;
    }
    
    public synchronized double nextRandom() {
    	if(indexArray >= randomNumbersTest.length)
    		System.exit(0);
    	return randomNumbersTest[indexArray++];
    }    

//    public synchronized double nextRandom() {
//        maxRandoms--;
//        Xi = (a * X0 + c) % M;
//        X0 = Xi;
//        return (double) Xi / M;
//    }

    public synchronized boolean isFinished() {
        return maxRandoms <= 0;
    }
}
