package br.pucrs.sma;

import br.pucrs.sma.queue.Scheduler;
import br.pucrs.sma.queue.Queue;
import br.pucrs.sma.queue.Simulator;
import br.pucrs.sma.util.NumberGenerator;

public class App {

    public static void main(String[] args) throws Exception {
        Scheduler scheduler = new Scheduler(1, 2, 3, 6);
        Queue queue = new Queue(1, 3, scheduler);

        //testRandomNumberRenerator();
        Simulator simulator = new Simulator(queue, scheduler);
        simulator.run();
    }

    public static void testRandomNumberRenerator() {
        for (int i = 0; i < 100; i++)
            System.out.println(NumberGenerator.getInstance().nextRandom());
    }
}
