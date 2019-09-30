package br.pucrs.sma.model;

public class EventProbability {
    private final Event event;
    private final double probability;

    public EventProbability(Event event, double probability) {
        this.event = event;
        this.probability = probability;
    }

    public Event getEvent() {
        return event;
    }

    public double getProbability() {
        return probability;
    }

	@Override
	public String toString() {
		return "EventProbability [event=" + event + ", probability=" + probability + "]";
	}   
}

