package br.pucrs.sma.queue;

import br.pucrs.sma.model.Event;
import br.pucrs.sma.model.EventType;

// Simple Queue Structure
public class Queue {

    private int queueSize = 0;
    private double globalTime = 0;
    private int losses = 0;

    // Kendall Notation
    private char A = 'G'; // distribution of arrival
    private char B = 'G'; // distribution of departure
    private int C = 1;    // number of servers in the line
    private int K = 1;    // queue capacity

    private double queueStates[];

    private Scheduler scheduler;

    // The Constructor will create the basic table for the queue with the following columns filled:
    // queueSize | globalTime | States 0...n |
    public Queue(int c, int k, Scheduler scheduler) {
        this.C = c;
        this.K = k;
        this.scheduler = scheduler;
        this.queueStates = new double[K + 1];
        for (int i = 0; i < queueStates.length; i++) {
            queueStates[i] = 0;
        }
    }

    public void arrive(Event event) {
        // if (queueSize > K) throw new Exception("Queue reached maximum size");
        updateTime(event.getExecutionTime());

        if (queueSize < K) {
            queueSize++;
            if (queueSize <= C) scheduler.schedule(EventType.LEAVE, globalTime);
        } else losses++;

        scheduler.schedule(EventType.ARRIVAL, globalTime);
    }

    public void leave(Event event) throws Exception {
        if (queueSize < 0) throw new Exception("Queue is empty");

        updateTime(event.getExecutionTime());
        queueSize--;

        if (queueSize >= C) scheduler.schedule(EventType.LEAVE, globalTime);
    }

    private void updateTime(double eventTime) {
        queueStates[queueSize] += eventTime - globalTime;
        globalTime = eventTime;
    }

    public void printPercentages() {
        for (int i = 0; i < queueStates.length; i++)
            System.out.println("Estado: " + i + " = " + queueStates[i] / globalTime);

        System.out.println("Perdas: " + losses);
    }

    // Getters
    public int getQueueSize() { return this.queueSize; }
}
