package br.pucrs.sma.model;

import br.pucrs.sma.queue.Queue;

import java.util.List;

public class ConfigDto {
    private Integer maxRandoms;
    private List<QueueDto> queues;
    private Long a;
    private Long c;
    private Long M;
    private Long x0;
    private List<EventProbabilityDto> eventProbabilities;

    public Integer getMaxRandoms() {
        return maxRandoms;
    }

    public void setMaxRandoms(Integer maxRandoms) {
        this.maxRandoms = maxRandoms;
    }

    public List<QueueDto> getQueues() {
        return queues;
    }

    public void setQueues(List<QueueDto> queues) {
        this.queues = queues;
    }

    public Long getA() {
        return a;
    }

    public void setA(Long a) {
        this.a = a;
    }

    public Long getC() {
        return c;
    }

    public void setC(Long c) {
        this.c = c;
    }

    public Long getM() {
        return M;
    }

    public void setM(Long m) {
        M = m;
    }

    public Long getX0() {
        return x0;
    }

    public void setX0(Long x0) {
        this.x0 = x0;
    }

    public List<EventProbabilityDto> getEventProbabilities() {
        return eventProbabilities;
    }

    public void setEventProbabilities(List<EventProbabilityDto> eventProbabilities) {
        this.eventProbabilities = eventProbabilities;
    }
}
