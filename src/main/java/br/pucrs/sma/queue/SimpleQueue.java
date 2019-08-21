package br.pucrs.sma.queue;

import br.pucrs.sma.model.Event;
import type.EventType;

// PT-BR: Estrutura de Fila Simples
public class SimpleQueue {

    private int size = 0;
    private double globalTime = 0;
    private int losses = 0;

    // Kendall Notation
    private char A = 'G'; // distribution
    private char B = 'G';
    private int C = 1; // number of servers in the line
    private int K = 1; // queue capacity

    private double queueStates[];

    private Scheduler scheduler;

    public SimpleQueue(int size, double globalTime, int c, int k, Scheduler scheduler) {
        this.size = size;
        this.globalTime = globalTime;
        C = c;
        K = k;
        this.scheduler = scheduler;
        queueStates = new double[K + 1];
        for (int i = 0; i < queueStates.length; i++) {
            queueStates[i] = 0;
        }
    }

    public SimpleQueue(int size, int globalTime, int c, int k, int fromArrival, int toArrival, int fromLeave, int toLeave,
                       double[] queueStates, Scheduler scheduler) {

        this.size = size;
        this.globalTime = globalTime;
        C = c;
        K = k;
        this.scheduler = scheduler;
    }

    public void arrive(Event event) throws Exception {
        updateTime(event.getExecutionTime());
        if (size > K)
            throw new Exception("Queue reached maximum size");

        // Cont Tempo
        if (size < K) {
            size++;
            if (size <= 2) {
                scheduler.schedule(EventType.LEAVE, globalTime);
            }
        }
        else
            losses++;
        scheduler.schedule(EventType.ARRIVAL, globalTime);
    }

    public void leave(Event event) throws Exception {
        if (size < 0)
            throw new Exception("Queue is empty");
        updateTime(event.getExecutionTime());
        size--;
        if (size >= 2) {
            scheduler.schedule(EventType.LEAVE, globalTime);
        }
    }

    public int getSize() {
        return this.size;
    }

    private void updateTime(double eventTime) {
        queueStates[size]  += eventTime - globalTime;
        globalTime = eventTime;
    }

    public void printPercentages () {
        for(int i =0; i<queueStates.length; i++)
        System.out.println(" Estado: " + i + " = " + queueStates[i]/globalTime);

        System.out.println("Perdas: " + losses);
    }

}
