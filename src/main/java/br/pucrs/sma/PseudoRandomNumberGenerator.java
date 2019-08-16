package br.pucrs.sma;

public class PseudoRandomNumberGenerator {

    private static long a = 1140671485;
    private static long c = 12820163;
    private static long M = 16777218;
    private static long X0;
    private static long Xi;
    private static boolean initialized;

//	public static synchronized void initializeGenerator(long a, long c, long m, long seed) {
//
//	}a

    public static synchronized double nextRandom() {
        X0 = 1;
        Xi = X0;
        Xi = (a * X0 + c) % M;
        X0 = Xi;
        return (double) Xi / M;
    }

}
