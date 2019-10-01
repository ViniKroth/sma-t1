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
    public static boolean testMode = false;
    public static int maxRandoms = 16;
    public static int indexArray = 0;
    public static double[] randomNumbersTest;

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
    	if(testMode)
    		return nextRandomTest();
    	return nextRandomTrue();
    }
    
    public synchronized double nextRandomTest() {
    	maxRandoms--;
    	return randomNumbersTest[indexArray++];
    }    

    public synchronized double nextRandomTrue() {
        maxRandoms--;
        Xi = (a * X0 + c) % M;
        X0 = Xi;
        return (double) Xi / M;
    }

    public synchronized boolean isFinished() {
        return maxRandoms <= 0;
    }
    
    public synchronized void setRandomNumbersTest(double[] randomNumbersTest) {
    	this.randomNumbersTest = randomNumbersTest;
    }
}
