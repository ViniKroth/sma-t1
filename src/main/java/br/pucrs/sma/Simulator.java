package br.pucrs.sma;

import br.pucrs.sma.model.*;
import br.pucrs.sma.queue.Queue;
import br.pucrs.sma.queue.Scheduler;
import br.pucrs.sma.util.NumberGenerator;
import br.pucrs.sma.util.Utils;

import java.util.*;

public class Simulator {

    private List<Queue> queues;
    private Scheduler scheduler = new Scheduler(this);
    private double globalTime = 0;
    private Scanner in = new Scanner(System.in);

    public Simulator(ConfigDto configDto) throws Exception {
        populateSystem(configDto);
        run();
    }

    private void populateSystem(ConfigDto configDto) throws Exception {
        NumberGenerator.setMaxRandoms(configDto.getMaxRandoms());
        NumberGenerator.setX0(configDto.getX0());
        NumberGenerator.setA(configDto.getA());
        NumberGenerator.setC(configDto.getC());
        NumberGenerator.setM(configDto.getM());
        queues = new ArrayList<>();
        for (QueueDto queueDto : configDto.getQueues()) {
            queues.add(new Queue(scheduler, this, queueDto.getMinArrivalUnitTime(), queueDto.getMaxArrivalUnitTime(),
                    queueDto.getMinLeaveUnitTime(), queueDto.getMaxLeaveUnitTime(), queueDto.getC(), queueDto.getK(), queueDto.getArrivalTime()));
        }

        Collections.sort(queues, Comparator.comparing(Queue::getId));

        for (EventProbabilityDto event : configDto.getEventProbabilities()) {
            queues.get(event.getFromQueueId()).addEventProbability(new Event(event.getEventType(),
                    queues.get(event.getFromQueueId()), queues.get(event.getToQueueId())), event.getProbability());
        }
    }

    public void run() throws Exception {

        // Schedule all arrival times for each queue
        for (Queue queue : queues) {
            if (queue.getArrivalTime() > 0) {
                scheduler.addEvent(new Event(EventType.ARRIVAL, null, queue, queue.getArrivalTime()));
            }
        }

        Event event = null;
        while (!NumberGenerator.getInstance().isFinished()) {
            event = scheduler.nextEvent();
            switch (event.getEventType()) {
                case ARRIVAL:
                    event.getToQueue().arriveFromNothing(event, event.getFromQueue());
                    break;
                case LEAVE:
                    event.getFromQueue().leaveToNothing(event);
                    break;
                case TRANSITION:
                    event.getFromQueue().transitionQueues(event, event.getFromQueue(), event.getToQueue());
                    break;
                default:
                    throw new Exception("Invalid EventType detected!");
            }
        }

        System.out.println("\nScheduler History:");
        scheduler.printScheduler();
        System.out.println();

        System.out.println("Total execution time: " + Utils.convertToFourScale(globalTime));
        System.out.println();

        System.out.println("QUEUE STATES:");
        printQueueStates(event);
    }

    public void updateTime(double eventTime) {
        for (Queue q : queues) {
            try {
                q.getQueueStates()[q.getQueueSize()] += eventTime - globalTime;
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        globalTime = eventTime;
    }

    public double getGlobalTime() {
        return globalTime;
    }

    public void printQueueStates(Event event) {
        for (Queue q : queues) {
            System.out.println("\nResults for Queue " + q.getId() + " (" + q.getA() + '|' + q.getB() + "|" + q.getC() + "|" + q.getK() + ") " +
                    "[Arrival u.t: " + q.getMinArrivalUnitTime() + "..." + q.getMaxArrivalUnitTime() + " - Leave u.t: " + q.getMinLeaveUnitTime() + "..." + q.getMaxLeaveUnitTime() + "]");
            q.printStates();
            System.out.println("Losses: " + q.getLosses());
        }
    }
}
