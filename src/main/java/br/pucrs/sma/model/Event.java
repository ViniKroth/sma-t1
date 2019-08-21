package br.pucrs.sma.model;

import type.EventType;

public class Event {

    private EventType eventType;
    private Double executionTime;

    public Event(EventType eventType, Double executionTime) {
        this.eventType = eventType;
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

    @Override
    public String toString() {
        return "Event{" +
                "eventType=" + eventType +
                ", executionTime=" + executionTime +
                '}';
    }
}
