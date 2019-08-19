package br.pucrs.sma.queue;

public class Simulator {

	private int event;
	private int queueIndex;
	private double timer;
	private double queueStates[]; // length=param K
	private SimpleQueue queue;
	private Scheduler scheduler;

	public Simulator(int K, SimpleQueue queue, Scheduler scheduler) {
		queueStates = new double[K + 1];
		for (int i = 0; i < queueStates.length; i++) {
			queueStates[i] = 0;
		}
		this.event = 0;
		this.queueIndex = 0;
		this.timer = 0.0;
		this.queue = queue;
		this.scheduler = scheduler;
	}

	public void run() throws Exception {
		// testando uma execucao
		System.out.println("\nSIMULADOR:");
		System.out.println("Evento: " + event + " FILA: " + queueIndex + " Tempo: " + timer + "0: " + queueStates[0]
				+ "  1: " + queueStates[1] + "  2: " + queueStates[2] + "  3: " + queueStates[3]);
		
		// Estado inicial: Chegada = 2,00
		this.queue.arrive();
		
		System.out.println("\nESCALONADOR:");
		System.out.println("  Eventos(0 - Saida  1 - Chegada)  ");
		this.scheduler.printScheduler();
	}
}
