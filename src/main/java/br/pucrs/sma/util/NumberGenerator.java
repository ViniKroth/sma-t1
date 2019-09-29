package br.pucrs.sma.util;

/**
 * A Singleton Class that is a Linear congruential generator of random numbers
 */
public class NumberGenerator {

    private static long a = 1140671485;
    private static long c = 12820163;
    private static long M = 16777218;
    private static long X0 = 1;
    private static long Xi = X0;

    private static NumberGenerator instance;

    // These Variables are only used during test mode, you can turn it off by changing it to false
    private static boolean isTestMode = true;
    private int testCont = 0;
    public static double[] randomNumbersTest = {0.3276, 0.8851, 0.1643, 0.5542, 0.6813, 0.7221, 0.9881};

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
        if (isTestMode)
            return generateNextTest();
        else
            return generateNextRandom();
    }

    public synchronized double generateNextRandom() {
        Xi = (a * X0 + c) % M;
        X0 = Xi;
        // Sets the value to be between 0 and 1 and it should contain only 4 decimal numbers
        double value = (double) Xi / M;
        return Utils.convertToFourScale(value);
    }

    public synchronized double generateNextTest() {
        return randomNumbersTest[testCont++];
    }

    public synchronized boolean isFinished() {
        if (isTestMode && testCont >= randomNumbersTest.length) return true;
        else return false;
    }
}
