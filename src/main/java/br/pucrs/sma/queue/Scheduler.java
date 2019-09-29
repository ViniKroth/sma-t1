package br.pucrs.sma.queue;

import br.pucrs.sma.model.Event;
import br.pucrs.sma.util.NumberGenerator;
import br.pucrs.sma.model.EventType;

import java.util.*;

// PT-BR: Escalonador
public class Scheduler {

    private int fromArrival;
    private int toArrival;
    private int fromLeave;
    private int toLeave;

    private List<Event> events;
    private List<Double> timeArray;
    private List<Event> checkedEvents;

    public Scheduler() {

    }

    // PT-BR: Recebe as configurações de Unidade de tempo da fila
    public Scheduler(int fromArrival, int toArrival, int fromLeave, int toLeave) {
        this.fromArrival = fromArrival;
        this.toArrival = toArrival;
        this.fromLeave = fromLeave;
        this.toLeave = toLeave;

        this.events = new ArrayList<>();
        this.timeArray = new ArrayList<>();
        this.checkedEvents = new ArrayList<>();
    }

    public void addEvent(Event event) {
        events.add(event);
        orderList();
    }

    public void schedule(EventType eventType, Queue fromQueue, Queue toQueue, double globalTime) throws Exception {
        if (NumberGenerator.getInstance().isFinished())
            return;
        switch (eventType) {
            case ARRIVAL:
                addEvent(new Event(eventType, fromQueue, toQueue, globalTime + draw(fromArrival, toArrival, NumberGenerator.getInstance().nextRandom())));
                break;
            case LEAVE:
                addEvent(new Event(eventType, fromQueue, toQueue, globalTime + draw(fromLeave, toLeave, NumberGenerator.getInstance().nextRandom())));
                break;
            case TRANSITION:
                addEvent(new Event(eventType, fromQueue, toQueue, globalTime + draw(fromLeave, toLeave, NumberGenerator.getInstance().nextRandom())));
                break;
            default:
                throw new Exception("Invalid EventType detected!");
        }
    }

    public Event nextEvent() throws Exception {
        if (events.isEmpty())
            throw new Exception("Empty event list");
        Event event = events.remove(0);
        checkedEvents.add(event);
        return event;
    }

    private double draw(int from, int to, double value) {
        return (to - from) * value + from;
    }

    private void orderList() {
        Collections.sort(events, Comparator.comparing(Event::getExecutionTime));
    }

    public void checkList() {
        System.out.println(checkedEvents.toString());
    }

    // Get methods
    public List<Event> getEvents() {
        return events;
    }

    // Print
    public void printScheduler() {
        for (int i = 0; i < events.size(); i++) {
            System.out.println("Evento: " + events.get(i) + " Tempo: " + timeArray.get(i));
        }
    }
}
