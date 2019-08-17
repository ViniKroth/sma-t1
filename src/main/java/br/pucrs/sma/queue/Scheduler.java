package br.pucrs.sma.queue;

import java.util.ArrayList;
import java.util.List;

// PT-BR: Escalonador
public class Scheduler {

    private List<Integer> events = new ArrayList<>();
    private List<Double> timeArray = new ArrayList<>();
    private List<Double> drawArray = new ArrayList<>();

    public Scheduler() {}

    public void insert(int event, double time, double draw) {
        if((event != 0 && event != 1) || time < 0 || (draw != -1 && draw < 0))
            throw new IllegalArgumentException("Event, time and draw must be valid");

        events.add(event);
        timeArray.add(time);
        drawArray.add(draw);
    }

    public List<Integer> getEvents() { return events; }

    public List<Double> getTime() {	return timeArray; }

    public List<Double> getDraw() {	return drawArray; }

}

