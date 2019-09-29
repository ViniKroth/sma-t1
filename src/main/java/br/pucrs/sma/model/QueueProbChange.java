package br.pucrs.sma.model;

public class QueueProbChange<Queue, ProbChance> {
    public final Queue queue;
    public final ProbChance probChance;

    public QueueProbChange(Queue queue, ProbChance probChance) {
        this.queue = queue;
        this.probChance = probChance;
    }
}
