package br.pucrs.sma;

import br.pucrs.sma.model.Event;
import br.pucrs.sma.model.EventType;
import br.pucrs.sma.queue.Queue;
import br.pucrs.sma.queue.Scheduler;
import br.pucrs.sma.util.NumberGenerator;
import br.pucrs.sma.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Simulator {

    private List<Queue> queues = new ArrayList<>();
    private Scheduler scheduler = new Scheduler(this);
    private double globalTime = 0;
    private Scanner in = new Scanner(System.in);
    private int count = 0;


    public Simulator() throws Exception {
    	// Pick the scenario
    	//exerciseOneTest();
    	//exerciseTwoTest();
    	modelTest();
    	//askUser();

        run();
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
        
        System.out.println("QUEUE STATES:");
        printPercentages(event);
    }
	
    public void updateTime(double eventTime) {
    	for(Queue q : queues) {
    		//System.out.println(count++);
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
    
    public void printPercentages(Event event) {
    	System.out.println("Last Event: " + event.getEventType());
    	System.out.println("Global Time: " + globalTime);
    	
    	for(Queue q : queues) {
    		System.out.println("\nResults for Queue " + q.getId()+ " (" + q.getA() + '|' + q.getB() + "|" + q.getC() + "|" + q.getK() + ") : ");
    		q.printStates();
    		System.out.println("Losses: " + q.getLosses());
    		
    	}          
    }
    
    private void modelTest() throws Exception {
    	NumberGenerator.maxRandoms = 100000;
    	double[] randomNumbersTest = {0.3281, 0.1133, 0.3332, 0.5634, 0.1099, 0.1221, 0.7271, 0.0301, 0.8291, 0.3131, 0.5232, 0.7291, 0.9129, 0.8723, 0.4101, 0.2209};
    	NumberGenerator.getInstance().setRandomNumbersTest(randomNumbersTest);  
    	NumberGenerator.X0 = 1557486468894L; 
    	
    	Queue one = new Queue(scheduler, this);
    	one.setId(0);
    	one.setC(1);
    	one.setK(999);
    	one.setMinArrivalUnitTime(1);
    	one.setMaxArrivalUnitTime(4);
    	one.setMinLeaveUnitTime(1);
    	one.setMaxLeaveUnitTime(5);
    	one.setArrivalTime(1.0);
    	
    	Queue two = new Queue(scheduler, this);
    	two.setId(1);
    	two.setC(3);
    	two.setK(5);
    	two.setMinLeaveUnitTime(5);
    	two.setMaxLeaveUnitTime(10);
    	two.setArrivalTime(0);
    	
    	Queue three = new Queue(scheduler, this);
    	three.setId(2);
    	three.setC(2);
    	three.setK(8);
    	three.setMinLeaveUnitTime(10);
    	three.setMaxLeaveUnitTime(20);
    	three.setArrivalTime(0);  
    	
    	queues.add(one);
    	queues.add(two);
    	queues.add(three);
    	
    	queues.get(0).addEventProbability(new Event(EventType.TRANSITION, queues.get(0), queues.get(1)), 0.8);
    	queues.get(0).addEventProbability(new Event(EventType.TRANSITION, queues.get(0), queues.get(2)), 0.2);
    	
    	queues.get(1).addEventProbability(new Event(EventType.TRANSITION, queues.get(1), queues.get(0)), 0.3);
    	queues.get(1).addEventProbability(new Event(EventType.TRANSITION, queues.get(1), queues.get(2)), 0.5);
    	queues.get(1).addEventProbability(new Event(EventType.LEAVE, queues.get(1), null), 0.2);
    	
    	queues.get(2).addEventProbability(new Event(EventType.TRANSITION, queues.get(2), queues.get(1)), 0.7);
    	queues.get(2).addEventProbability(new Event(EventType.LEAVE, queues.get(2), null), 0.3);
    }

    private void exerciseTwoTest() throws Exception {
    	NumberGenerator.testMode = true;
    	double[] randomNumbersTest = {0.3281, 0.1133, 0.3332, 0.5634, 0.1099, 0.1221, 0.7271, 0.0301, 0.8291, 0.3131, 0.5232, 0.7291, 0.9129, 0.8723, 0.4101, 0.2209};
    	NumberGenerator.getInstance().setRandomNumbersTest(randomNumbersTest);
    	
    	Queue one = new Queue(scheduler, this);
    	one.setId(0);
    	one.setC(2);
    	one.setK(3);
    	one.setMinArrivalUnitTime(1);
    	one.setMaxArrivalUnitTime(2);
    	one.setMinLeaveUnitTime(2);
    	one.setMaxLeaveUnitTime(3);
    	one.setArrivalTime(2.0);
    	
    	Queue two = new Queue(scheduler, this);
    	two.setId(1);
    	two.setC(3);
    	two.setK(5);
    	two.setMinArrivalUnitTime(1);
    	two.setMaxArrivalUnitTime(2);
    	two.setMinLeaveUnitTime(4);
    	two.setMaxLeaveUnitTime(5);
    	two.setArrivalTime(1.0);
    	
    	queues.add(one);
    	queues.add(two);
    	
    	queues.get(0).addEventProbability(new Event(EventType.TRANSITION, queues.get(0), queues.get(1)), 1);
    	
    	queues.get(1).addEventProbability(new Event(EventType.LEAVE, queues.get(1), null), 0.6);    	
    	queues.get(1).addEventProbability(new Event(EventType.TRANSITION, queues.get(1), queues.get(0)), 0.4);    	    	
    }
    
    private void exerciseOneTest() throws Exception {
    	NumberGenerator.testMode = true;
    	double[] randomNumbersTest = {0.2176, 0.0103, 0.1109, 0.3456, 0.9910, 0.2323, 0.9211, 0.0322, 0.1211, 0.5131, 0.7208, 0.9172, 0.9922, 0.8324, 0.5011, 0.2931};
    	NumberGenerator.getInstance().setRandomNumbersTest(randomNumbersTest);
    	
    	Queue one = new Queue(scheduler, this);
    	one.setId(0);
    	one.setC(2);
    	one.setK(4);
    	one.setMinArrivalUnitTime(2);
    	one.setMaxArrivalUnitTime(3);
    	one.setMinLeaveUnitTime(4);
    	one.setMaxLeaveUnitTime(7);
    	one.setArrivalTime(3.0);
    	
    	Queue two = new Queue(scheduler, this);
    	two.setId(1);
    	two.setC(1);
    	two.setK(999);
    	two.setMinLeaveUnitTime(4);
    	two.setMaxLeaveUnitTime(8);
    	two.setArrivalTime(0);
    	
    	queues.add(one);
    	queues.add(two);
    	
    	queues.get(0).addEventProbability(new Event(EventType.TRANSITION, queues.get(0), queues.get(1)), 0.7);
    	queues.get(0).addEventProbability(new Event(EventType.LEAVE, queues.get(0), null), 0.3);
    	
    	queues.get(1).addEventProbability(new Event(EventType.LEAVE, queues.get(1), null), 1);
		
	}
   
    public void askUser() throws Exception {
      System.out.println("Defina o valor da semente XO para o gerador de numeros aleatorios:");
      NumberGenerator.X0 = in.nextLong();

      System.out.println("Defina a quantidade de numeros aleatorios a serem gerados:");
      NumberGenerator.maxRandoms = in.nextInt();

      System.out.println("Defina a quantidade de filas do sistema:");
      int numberOfQueues = in.nextInt();

      for (int i = 0; i < numberOfQueues; i++)
          queues.add(new Queue(scheduler, this));

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
