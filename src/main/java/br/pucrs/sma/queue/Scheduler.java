package br.pucrs.sma.queue;

import br.pucrs.sma.Simulator;
import br.pucrs.sma.model.Event;
import br.pucrs.sma.util.NumberGenerator;
import br.pucrs.sma.model.EventType;

import java.util.*;

// PT-BR: Escalonador
public class Scheduler {

    private List<Event> events;
    private List<Double> timeArray;
    private List<Event> checkedEvents;
    private Simulator simulator;

    public Scheduler(Simulator simulator) {
        this.events = new ArrayList<>();
        this.timeArray = new ArrayList<>();
        this.checkedEvents = new ArrayList<>();
        this.simulator = simulator;
    }

    public void addEvent(Event event) {
        events.add(event);
        orderList();
        checkedEvents.add(event);
    }

    public void schedule(EventType eventType, Queue fromQueue, Queue toQueue) throws Exception {
        if (NumberGenerator.getInstance().isFinished())
            return;
        switch (eventType) {
            case ARRIVAL:
                addEvent(new Event(eventType, fromQueue, toQueue, simulator.getGlobalTime() + draw(toQueue.getMinArrivalUnitTime(), toQueue.getMaxArrivalUnitTime(), NumberGenerator.getInstance().nextRandom())));
                break;
            case LEAVE:
            case TRANSITION:
                addEvent(new Event(eventType, fromQueue, toQueue, simulator.getGlobalTime() + draw(fromQueue.getMinLeaveUnitTime(), fromQueue.getMaxLeaveUnitTime(), NumberGenerator.getInstance().nextRandom())));
                break;
            default:
                throw new Exception("Invalid EventType detected!");
        }
    }

    public Event nextEvent() throws Exception {
        if (events.isEmpty())
            throw new Exception("Empty event list");
        Event event = events.remove(0);
        return event;
    }

    private double draw(double from, double to, double value) {
        return (to - from) * value + from;
    }

    private void orderList() {
        Collections.sort(events, Comparator.comparing(Event::getExecutionTime));
    }

    public void printScheduler() {
        for(Event e : checkedEvents) {
        	System.out.println(e);
        }
    }

    // Get methods
    public List<Event> getEvents() {
        return events;
    }

	@Override
	public String toString() {
		return "Scheduler [events=" + events + ", timeArray=" + timeArray + ", checkedEvents=" + checkedEvents + "]";
	}

    
}
