package br.pucrs.sma.queue;

import br.pucrs.sma.model.Event;
import type.EventType;

public class Simulator {

     // length=param K
    private SimpleQueue queue;
    private Scheduler scheduler;

    public Simulator(SimpleQueue queue, Scheduler scheduler) {

        this.queue = queue;
        this.scheduler = scheduler;
    }

    public void run() throws Exception {
        // testando uma execucao
//        System.out.println("\nSIMULADOR:");
//        System.out.println("Evento: " + event + " FILA: " + queueIndex + " Tempo: " + globalTime + "0: " + queueStates[0]
//                + "  1: " + queueStates[1] + "  2: " + queueStates[2] + "  3: " + queueStates[3]);

        // Estado inicial: Chegada = 2,00
        Event event = new Event(EventType.ARRIVAL, 2.0);
        scheduler.events.add(event);

        for (int i = 0; i < 100000; i++) {
             event = scheduler.nextEvent();
            if (event.getEventType().equals(EventType.ARRIVAL)) {
                queue.arrive(event);
            } else
                queue.leave(event);
        }

		scheduler.checkList();
        queue.printPercentages();
//        System.out.println("\nESCALONADOR:");
//        System.out.println("  Eventos(0 - Saida  1 - Chegada)  ");
       // this.scheduler.printScheduler();
    }
}
