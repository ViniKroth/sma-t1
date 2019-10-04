package br.pucrs.sma.model;

public class QueueDto {

    private int id;
    private double minArrivalUnitTime;
    private double maxArrivalUnitTime;
    private double minLeaveUnitTime;
    private double maxLeaveUnitTime;
    private int C;
    private int K;
    private double arrivalTime;

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

    public double getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
