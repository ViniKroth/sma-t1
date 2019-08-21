package br.pucrs.sma.queue;

import br.pucrs.sma.model.Event;
import br.pucrs.sma.util.NumberGenerator;
import type.EventType;

import java.util.*;

// PT-BR: Escalonador
public class Scheduler {

    private List<Event> events;
    private List<Double> timeArray;

    private int fromArrival;
    private int toArrival;
    private int fromLeave;
    private int toLeave;
    private List<Event> checkedEvents;

    public Scheduler(int fromArrival, int toArrival, int fromLeave, int toLeave) {
        this.events = new ArrayList<>();
        this.timeArray = new ArrayList<>();
        this.fromArrival = fromArrival;
        this.toArrival = toArrival;
        this.fromLeave = fromLeave;
        this.toLeave = toLeave;
        checkedEvents = new ArrayList<>();
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<Double> getTime() {
        return timeArray;
    }


    public void printScheduler() {
        for (int i = 0; i < events.size(); i++) {
            System.out.println("Evento: " + events.get(i) + " Tempo: " + timeArray.get(i));
        }
    }

    public void schedule(EventType eventType, double globalTime) {
        Event event;
        if (eventType.equals(EventType.ARRIVAL)) {
            event = new Event(eventType, globalTime + NumberGenerator.getInstance()
                    .nextRandom(fromArrival, toArrival));
        } else {
            event = new Event(eventType, globalTime + NumberGenerator.getInstance()
                    .nextRandom(fromLeave, toLeave));
        }
        events.add(event);
        orderList();
    }

    public Event nextEvent() throws Exception {
        if (events.isEmpty())
            throw new Exception("Empty event list");
        Event event = events.remove(0);
        checkedEvents.add(event);
        return event;
    }

    private void orderList() {
        Collections.sort(events, Comparator.comparing(Event::getExecutionTime));
    }

    public void checkList(){
		System.out.println(checkedEvents.toString());
	}

}
