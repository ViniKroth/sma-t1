package br.pucrs.sma.util;

public class NumberGenerator {

    private static long a = 1140671485;
    private static long c = 12820163;
    private static long M = 16777218;
    private static long X0 = 1;
    private static long Xi = X0;

    private static NumberGenerator instance;
    private static int id = 0;

    private NumberGenerator() {}

    public static NumberGenerator getInstance()
    {
        if (instance == null)
        {
            synchronized(NumberGenerator.class)
            {
                if (instance == null)
                {
                    instance = new NumberGenerator();
                }
            }
        }

        return instance;
    }

    public synchronized double nextRandom(int from, int to) {
        Xi = (a * X0 + c) % M;
        X0 = Xi;
        // Sets the value to be between 0 and 1 and it should contain only 4 decimal numbers
        double value = (double) Xi / M;
        double scale = Math.pow(10, 4);
        return Math.round(convertToRange(from, to, value) * scale) / scale;
    }

    private synchronized double convertToRange(int from, int to, double value){
        return (to - from) * value + from;
    }

    public synchronized int nextId() {
        return ++id;
    }
}
