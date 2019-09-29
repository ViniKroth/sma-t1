package br.pucrs.sma.queue;

import br.pucrs.sma.model.Event;
import br.pucrs.sma.model.EventType;

public class Simulator {

    // length=param K
    private SimpleQueue queue;
    private Scheduler scheduler;

    public Simulator(SimpleQueue queue, Scheduler scheduler) {
        this.queue = queue;
        this.scheduler = scheduler;
    }

    public void run() throws Exception {
        Event event = new Event(EventType.ARRIVAL, 2.0);
        scheduler.getEvents().add(event);

        for (int i = 0; i < 10; i++) {
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
