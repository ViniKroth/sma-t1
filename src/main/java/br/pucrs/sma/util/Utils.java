package br.pucrs.sma.util;

public class Utils {

    public static double convertToFourScale(double value){
        return Math.round(value * 10000.0) / 10000.0;
    }

    public static double convertToTwoScale(double value){
        return Math.round(value * 100.0) / 100.0;
    }
}
