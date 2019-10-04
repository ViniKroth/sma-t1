package br.pucrs.sma.queue;

import br.pucrs.sma.Simulator;
import br.pucrs.sma.model.Event;
import br.pucrs.sma.model.EventType;
import br.pucrs.sma.model.EventProbability;
import br.pucrs.sma.util.NumberGenerator;
import br.pucrs.sma.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// Simple Queue Structure
public class Queue {

    private int id;

    private double minArrivalUnitTime;
    private double maxArrivalUnitTime;

    private double minLeaveUnitTime;
    private double maxLeaveUnitTime;

    private int queueSize = 0;
    private int losses = 0;

    // Kendall Notation
    private char A = 'G'; // distribution of arrival
    private char B = 'G'; // distribution of departure
    private int C = 1; // number of servers in the line
    private int K = 1; // queue capacity

    private List<EventProbability> eventProbabilityList = new ArrayList<>();
    private double queueStates[];
    private double arrivalTime = 0;

    private Scheduler scheduler;
    private Simulator simulator;

    public Queue(Scheduler scheduler, Simulator simulator, double minArrivalUnitTime, double maxArrivalUnitTime,
                 double minLeaveUnitTime, double maxLeaveUnitTime, int C, int K, double arrivalTime) {
        this.minArrivalUnitTime = minArrivalUnitTime;
        this.maxArrivalUnitTime = maxArrivalUnitTime;
        this.minLeaveUnitTime = minLeaveUnitTime;
        this.maxLeaveUnitTime = maxLeaveUnitTime;
        this.C = C;
        setK(K);
        this.arrivalTime = arrivalTime;
        this.scheduler = scheduler;
        this.simulator = simulator;
    }

    /*
     * It creates a probability of a client to change from this queue to the given
     * queue. The probability can not be higher than 100 percent, or 1.
     */
    public void addEventProbability(Event event, double probability) throws Exception {
        if (probability > 1 || (getMaximumEventProbability() + probability) > 1) {
            throw new Exception("Probabilidade de mudança de fila maior que 100%!");
        } else {
            eventProbabilityList.add(new EventProbability(event, probability));
            orderEventProbabilityList();
        }
    }

    private double getMaximumEventProbability() {
        double sum = 0.0;
        for (int i = 0; i < eventProbabilityList.size(); i++) {
            sum += eventProbabilityList.get(i).getProbability();
        }
        return sum;
    }

    private void orderEventProbabilityList() {
        Collections.sort(eventProbabilityList, Comparator.comparing(EventProbability::getProbability,
                Comparator.reverseOrder()));
    }

    private Event routing(List<EventProbability> eventProbabilityList) {
        if (eventProbabilityList.size() == 1)
            return eventProbabilityList.get(0).getEvent();
        double rnd = NumberGenerator.getInstance().nextRandom();
        double sumProbabilities = 0.0;
        for (EventProbability event : eventProbabilityList) {
            sumProbabilities += event.getProbability();
            if (rnd < sumProbabilities) {
                return event.getEvent();
            }
        }
        return null;
    }

    // CH-X event
    public void arriveFromNothing(Event event, Queue fromQueue) throws Exception {
        simulator.updateTime((event.getExecutionTime()));

        if (queueSize < K) {
            queueSize++;
            if (queueSize <= C) {
                Event chosenEvent = routing(eventProbabilityList);
                scheduler.schedule(chosenEvent.getEventType(), chosenEvent.getFromQueue(), chosenEvent.getToQueue());
            }
        } else
            losses++;

        scheduler.schedule(EventType.ARRIVAL, fromQueue, this);
    }

    // SA-X event
    public void leaveToNothing(Event event) throws Exception {
        if (queueSize < 0)
            throw new Exception("Queue is empty");

        try {
            simulator.updateTime((event.getExecutionTime()));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        queueSize--;

        if (queueSize >= C) {
            scheduler.schedule(EventType.LEAVE, this, null);
        }
    }

    // P-XY event
    public void transitionQueues(Event event, Queue fromQueue, Queue toQueue) throws Exception {
        simulator.updateTime((event.getExecutionTime()));

        queueSize--;
        if (queueSize >= C) {
            Event chosenEvent = routing(eventProbabilityList);
            scheduler.schedule(chosenEvent.getEventType(), chosenEvent.getFromQueue(), chosenEvent.getToQueue());
        }

        if (toQueue.getQueueSize() < toQueue.getK()) {
            toQueue.setQueueSize(toQueue.getQueueSize() + 1);
            if (toQueue.getQueueSize() <= toQueue.getC()) {
                Event chosenEvent = routing(toQueue.getEventProbabilityList());
                scheduler.schedule(chosenEvent.getEventType(), chosenEvent.getFromQueue(), chosenEvent.getToQueue());
            }
        } else {
            toQueue.setLosses(toQueue.getLosses() + 1);
        }
    }

    // ============== Getters and Setters
    // ==============================================================================
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMinArrivalUnitTime() {
        return minArrivalUnitTime;
    }

    public void setMinArrivalUnitTime(double minArrivalUnitTime) {
        this.minArrivalUnitTime = minArrivalUnitTime;
    }

    public double getMaxArrivalUnitTime() {
        return maxArrivalUnitTime;
    }

    public void setMaxArrivalUnitTime(double maxArrivalUnitTime) {
        this.maxArrivalUnitTime = maxArrivalUnitTime;
    }

    public double getMinLeaveUnitTime() {
        return minLeaveUnitTime;
    }

    public void setMinLeaveUnitTime(double minLeaveUnitTime) {
        this.minLeaveUnitTime = minLeaveUnitTime;
    }

    public double getMaxLeaveUnitTime() {
        return maxLeaveUnitTime;
    }

    public void setMaxLeaveUnitTime(double maxLeaveUnitTime) {
        this.maxLeaveUnitTime = maxLeaveUnitTime;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public char getA() {
        return A;
    }

    public void setA(char a) {
        A = a;
    }

    public char getB() {
        return B;
    }

    public void setB(char b) {
        B = b;
    }

    public int getC() {
        return C;
    }

    public void setC(int c) {
        this.C = c;
    }

    public int getK() {
        return K;
    }

    public void setK(int k) {
        this.K = k;
        this.queueStates = new double[K + 1];
        for (int i = 0; i < queueStates.length; i++)
            queueStates[i] = 0;
    }

    public double[] getQueueStates() {
        return queueStates;
    }

    public void setQueueStates(double[] queueStates) {
        this.queueStates = queueStates;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public List<EventProbability> getEventProbabilityList() {
        return eventProbabilityList;
    }

    public void setEventProbabilityList(List<EventProbability> eventProbabilityList) {
        this.eventProbabilityList = eventProbabilityList;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void printStates() {
        for (int i = 0; i < queueStates.length; i++) {
            if (queueStates[i] == 0)
                return;
            System.out.println("State " + i + " - Timer: " + Utils.convertToFourScale(queueStates[i]) + " Percentage: "
                    + Utils.convertToTwoScale((queueStates[i] / simulator.getGlobalTime()) * 100) + "%");
        }
    }

    @Override
    public String toString() {
        return "Queue [id=" + id + ", minArrivalUnitTime=" + minArrivalUnitTime + ", maxArrivalUnitTime="
                + maxArrivalUnitTime + ", minLeaveUnitTime=" + minLeaveUnitTime + ", maxLeaveUnitTime="
                + maxLeaveUnitTime + ", queueSize=" + queueSize + ", losses=" + losses + ", A=" + A + ", B=" + B
                + ", C=" + C + ", K=" + K + ", eventProbabilityList=" + eventProbabilityList + ", queueStates="
                + Arrays.toString(queueStates) + ", arrivalTime=" + arrivalTime + ", scheduler=" + scheduler + "]";
    }

}
