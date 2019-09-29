package br.pucrs.sma.queue;

import br.pucrs.sma.model.Event;
import br.pucrs.sma.model.EventType;
import br.pucrs.sma.util.NumberGenerator;

public class Simulator {

    private Queue queue;
    private Scheduler scheduler;

    public Simulator(Queue queue, Scheduler scheduler) {
        this.queue = queue;
        this.scheduler = scheduler;
    }

    public void run() throws Exception {
        Event event = new Event(EventType.ARRIVAL, 2.0);
        scheduler.getEvents().add(event);

        while(!NumberGenerator.getInstance().isFinished()) {
            event = scheduler.nextEvent();
            if (event.getEventType().equals(EventType.ARRIVAL)) queue.arrive(event);
            else queue.leave(event);
        }

        scheduler.checkList();
        queue.printPercentages();
    }
}
