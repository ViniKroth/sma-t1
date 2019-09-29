package br.pucrs.sma;

import br.pucrs.sma.util.NumberGenerator;

public class App {

    public static void main(String[] args) throws Exception {
        System.out.println("############# SIMULADOR DE FILAS #############");
        System.out.println("Alunos: Israel - Larissa - Vinicius");
        Simulator simulator = new Simulator();
        //testRandomNumberGenerator();
    }

    public static void testRandomNumberGenerator() {
        for (int i = 0; i < NumberGenerator.maxRandoms; i++)
            System.out.println(NumberGenerator.getInstance().nextRandom());
    }
}
