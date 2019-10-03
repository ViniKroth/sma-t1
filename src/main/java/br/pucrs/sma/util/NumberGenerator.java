package br.pucrs.sma.util;

/**
 * A Singleton Class that is a Linear congruential generator of random numbers
 */
public class NumberGenerator {

    private static long a;
    private static long c;
    private static long M;
    private static long X0;
    private static long Xi = X0;

    private static NumberGenerator instance;

    // Variables for testing purpose
    public static boolean testMode = false;
    private static int maxRandoms = 16;
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
        if (testMode)
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

    public static long getA() {
        return a;
    }

    public static void setA(long a) {
        NumberGenerator.a = a;
    }

    public static long getC() {
        return c;
    }

    public static void setC(long c) {
        NumberGenerator.c = c;
    }

    public static long getM() {
        return M;
    }

    public static void setM(long m) {
        M = m;
    }

    public static long getX0() {
        return X0;
    }

    public static void setX0(long x0) {
        X0 = x0;
    }

    public static long getXi() {
        return Xi;
    }

    public static void setXi(long xi) {
        Xi = xi;
    }

    public static int getMaxRandoms() {
        return maxRandoms;
    }

    public static void setMaxRandoms(int maxRandoms) {
        NumberGenerator.maxRandoms = maxRandoms;
    }
}
