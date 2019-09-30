package br.pucrs.sma;

import br.pucrs.sma.model.Event;
import br.pucrs.sma.model.EventType;
import br.pucrs.sma.queue.Queue;
import br.pucrs.sma.queue.Scheduler;
import br.pucrs.sma.util.NumberGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Simulator {

    private List<Queue> queues = new ArrayList<>();
    private Scheduler scheduler = new Scheduler();
    private Scanner in = new Scanner(System.in);


    public Simulator() throws Exception {
        System.out.println("Defina o valor da semente XO para o gerador de numeros aleatorios:");
        NumberGenerator.X0 = in.nextLong();

        System.out.println("Defina a quantidade de numeros aleatorios a serem gerados:");
        NumberGenerator.maxRandoms = in.nextInt();

        System.out.println("Defina a quantidade de filas do sistema:");
        int numberOfQueues = in.nextInt();

        for (int i = 0; i < numberOfQueues; i++)
            queues.add(new Queue(scheduler));

        System.out.println("#### Definindo parametros de cada fila das " + numberOfQueues + " filas... ####");

        for (int i = 0; i < numberOfQueues; i++) {
            registerBaseQueueParams(i);

            System.out.println("Deseja definir roteamento da fila " + i + " para outra fila? Se sim, digite o id da fila destino. Se nao, digite um valor negativo");
            int routingTo = in.nextInt();
            if (!(routingTo <= -1)) {
                System.out.println("Defina a probabilidade de roteamento da fila (valor entre 0 e 1): " + i + " para a fila " + routingTo + ":");
                queues.get(i).addEventProbability(new Event(EventType.TRANSITION, queues.get(i), queues.get(routingTo)), in.nextDouble());
            }

            System.out.println("Deseja definir roteamento da fila " + i + " para fora do sistema? Digite um valor natural para sim, ou negativo para nao:");
            if (!(in.nextInt() <= -1)) {
                System.out.println("Defina a probabilidade de roteamento da fila (valor entre 0 e 1): " + i + " para fora do sistema:");
                queues.get(i).addEventProbability(new Event(EventType.LEAVE, queues.get(i), null), in.nextDouble());
            }

            System.out.println("Deseja definir roteamento da fila " + i + " para si mesmo? Digite um valor natural para sim, ou negativo para nao:");
            if (!(in.nextInt() <= -1)) {
                System.out.println("Defina a probabilidade de roteamento da fila (valor entre 0 e 1): " + i + " para fora do sistema:");
                queues.get(i).addEventProbability(new Event(EventType.TRANSITION, queues.get(i), queues.get(i)), in.nextDouble());
            }
        }
        run();
    }

    public void run() throws Exception {

        // Schedule all arrival times for each queue
        for (Queue queue : queues) {
            if (queue.getArrivalTime() > 0) {
                scheduler.addEvent(new Event(EventType.ARRIVAL, null, queue, queue.getArrivalTime()));
            }
        }

        Event event;
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

        scheduler.checkList();
        // queue.printPercentages();
    }

    /**
     * This method asks for the user to register the parameters of an specific queue from the array
     */
    private void registerBaseQueueParams(int i) {
        System.out.println("Definindo parametros da Fila " + i);
        queues.get(i).setId(i);

        System.out.println("Defina o número de SERVIDORES: ");
        queues.get(i).setC(in.nextInt());

        System.out.println("Defina a CAPACIDADE da fila: (digite -1 para infinita) ");
        int K = in.nextInt();
        if (K == -1) K = Integer.MAX_VALUE;
        queues.get(i).setK(K);

        System.out.println("Defina a frequencia MINIMA de ENTRADA em unidades de tempo: ");
        queues.get(i).setMinArrivalUnitTime(in.nextDouble());
        System.out.println("Defina a frequencia MAXIMA de ENTRADA em unidades de tempo: ");
        queues.get(i).setMaxArrivalUnitTime(in.nextDouble());

        System.out.println("Defina a frequencia MINIMA de SAIDA em unidades de tempo: ");
        queues.get(i).setMinLeaveUnitTime(in.nextDouble());
        System.out.println("Defina a frequencia MAXIMA de SAIDA em unidades de tempo: ");
        queues.get(i).setMaxLeaveUnitTime(in.nextDouble());

        System.out.println("Defina o tempo inicial de chegada na fila (nao havendo, defina 0): ");
        queues.get(i).setArrivalTime(in.nextDouble());
    }
}
