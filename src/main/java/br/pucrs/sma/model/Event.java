package br.pucrs.sma.model;

import br.pucrs.sma.queue.Queue;
import br.pucrs.sma.util.Utils;

public class Event {

    private EventType eventType;
    private Queue fromQueue;
    private Queue toQueue;
    private Double executionTime;

    public Event(EventType eventType, Queue fromQueue, Queue toQueue){
        this.eventType = eventType;
        this.fromQueue = fromQueue;
        this.toQueue = toQueue;
    }

    public Event(EventType eventType, Queue fromQueue, Queue toQueue, Double executionTime) {
        this.eventType = eventType;
        this.fromQueue = fromQueue;
        this.toQueue = toQueue;
        this.executionTime = executionTime;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Double getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Double executionTime) {
        this.executionTime = executionTime;
    }

    public Queue getFromQueue() {
        return fromQueue;
    }

    public void setFromQueue(Queue fromQueue) {
        this.fromQueue = fromQueue;
    }

    public Queue getToQueue() {
        return toQueue;
    }

    public void setToQueue(Queue toQueue) {
        this.toQueue = toQueue;
    }

    @Override
    public String toString() {
        String str;
        if(fromQueue != null && toQueue != null) {
            str = "Event{" +
                    "eventType=" + eventType +
                    ", fromQueue=" + fromQueue.getId() +
                    ", toQueue=" + toQueue.getId() +
                    ", executionTime=" + Utils.convertToFourScale(executionTime) +
                    '}';
        } else if(fromQueue != null) {
            str =  "Event{" +
                    "eventType=" + eventType +
                    ", fromQueue=" + fromQueue.getId() +
                    ", toQueue=" + "null" +
                    ", executionTime=" + Utils.convertToFourScale(executionTime) +
                    '}';
        } else {
            str =  "Event{" +
                    "eventType=" + eventType +
                    ", fromQueue=" + "null" +
                    ", toQueue=" + toQueue.getId() +
                    ", executionTime=" + Utils.convertToFourScale(executionTime) +
                    '}';
        }
        return str;
    }
}
