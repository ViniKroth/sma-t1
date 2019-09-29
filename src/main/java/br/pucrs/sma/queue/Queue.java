package br.pucrs.sma.queue;

import br.pucrs.sma.model.Event;
import br.pucrs.sma.model.EventType;
import br.pucrs.sma.model.QueueProbChange;
import br.pucrs.sma.util.Utils;

import java.util.ArrayList;
import java.util.List;

// Simple Queue Structure
public class Queue {

    private int id;

    private double minArrivalUnitTime;
    private double maxArrivalUnitTime;

    private double minLeaveUnitTime;
    private double maxLeaveUnitTime;

    private int queueSize = 0;
    private double globalTime = 0;
    private int losses = 0;

    // Kendall Notation
    private char A = 'G'; // distribution of arrival
    private char B = 'G'; // distribution of departure
    private int C = 1;    // number of servers in the line
    private int K = 1;    // queue capacity

    List<QueueProbChange<Queue, Double>> queueProbChangeList = new ArrayList<>();
    private double queueStates[];
    private double arrivalTime = 0;

    private Scheduler scheduler;

    public Queue(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

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

    /*
     * It creates a probability of a client to change from this queue to the given queue.
     * The probability can not be higher than 100 percent, or 1.
     */
    public void addQueueProbChange(Queue queue, Double probChange) throws Exception {
        if(probChange > 1 || (getMaximumProbChange() + probChange) > 1) {
            throw new Exception("Probabilidade de mudança de fila maior que 100%!");
        } else {
            queueProbChangeList.add(new QueueProbChange<>(queue, probChange));
        }
    }

    private Double getMaximumProbChange(){
        Double sum = 0.0;
        for (int i = 0; i < queueProbChangeList.size(); i++) {
            sum += queueProbChangeList.get(i).probChance;
        }
        return sum;
    }

    //CH-X event
    public void arriveFromNothing(Event event, Queue fromQueue) {
        // if (queueSize > K) throw new Exception("Queue reached maximum size");
        updateTime(event.getExecutionTime());

        if (queueSize < K) {
            queueSize++;
            if (queueSize <= C) scheduler.schedule(EventType.LEAVE, this, null, globalTime);
        } else losses++;

        scheduler.schedule(EventType.ARRIVAL, fromQueue, this,  globalTime);
    }

    //SA-X event
    public void leaveToNothing(Event event) throws Exception {
        if (queueSize < 0) throw new Exception("Queue is empty");

        updateTime(event.getExecutionTime());
        queueSize--;

        if (queueSize >= C) scheduler.schedule(EventType.LEAVE, this, null, globalTime);
    }

    //P-XY event
    public void transitionQueues(Event event, Queue fromQueue, Queue toQueue){
        queueSize--;
        if(queueSize >= C){
            if(true)
            scheduler.schedule(EventType.TRANSITION, fromQueue, toQueue,  globalTime);
        }
    }

    private void updateTime(double eventTime) {
        queueStates[queueSize] += eventTime - globalTime;
        globalTime = eventTime;
    }

    public void printPercentages() {
        for (int i = 0; i < queueStates.length; i++)
            System.out.println("State and %: " + i + " = " + Utils.convertToFourScale(queueStates[i] / globalTime));

        System.out.println("Losses: " + losses);
    }

    // ============== Getters and Setters ==============================================================================
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

    public double getGlobalTime() {
        return globalTime;
    }

    public void setGlobalTime(double globalTime) {
        this.globalTime = globalTime;
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
        C = c;
    }

    public int getK() {
        return K;
    }

    public void setK(int k) {
        K = k;
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

    public List<QueueProbChange<Queue, Double>> getQueueProbChangeList() {
        return queueProbChangeList;
    }

    public void setQueueProbChangeList(List<QueueProbChange<Queue, Double>> queueProbChangeList) {
        this.queueProbChangeList = queueProbChangeList;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
