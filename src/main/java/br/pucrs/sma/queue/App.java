package br.pucrs.sma.queue;

import br.pucrs.sma.util.NumberGenerator;

import java.util.ArrayList;
import java.util.List;

public class App {

	private static Scheduler scheduler = new Scheduler();
	// index 0=Fila; 1=Tempo; 2+ = consumidores
	private static List<Integer> answer = new ArrayList<Integer>();
	private static SimpleQueue queue;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		for (int i = 0; i < 10; i++) {
			System.out.println(NumberGenerator.getInstance().nextRandom(4, 9));
		}

		queue = new SimpleQueue('G', 'G', 1, 3, 1, 2, 3, 6, scheduler, 2);

		Simulator simulator = new Simulator(3, queue, scheduler);
		simulator.run();

	}

}
