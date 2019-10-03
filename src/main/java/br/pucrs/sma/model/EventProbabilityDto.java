package br.pucrs.sma.model;

public class EventProbabilityDto {

    private EventType eventType;
    private int fromQueueId;
    private int toQueueId;
    private double probability;

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public int getFromQueueId() {
        return fromQueueId;
    }

    public void setFromQueueId(int fromQueueId) {
        this.fromQueueId = fromQueueId;
    }

    public int getToQueueId() {
        return toQueueId;
    }

    public void setToQueueId(int toQueueId) {
        this.toQueueId = toQueueId;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }
}
