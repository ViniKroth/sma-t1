package br.pucrs.sma.queue;

import br.pucrs.sma.util.NumberGenerator;

import java.util.LinkedList;
import java.util.Queue;

// PT-BR: Estrutura de Fila Simples
public class SimpleQueue {

    private int size = 0;
    private int timer = 0;

    // Kendall Notation
    private char A = 'G'; // distribution
    private char B = 'G';
    private int C = 1; // number of servers in the line
    private int K = 1; // queue capacity

    private int fromArrival;
    private int toArrival;
    private int fromLeave;
    private int toLeave;

    public SimpleQueue(int C, int K){
        this.C = C;
        this.K = K;
    }

    public SimpleQueue(char A, char B, int C, int K, int fromArrival, int toArrival, int fromLeave, int toLeave){
        this.A = A;
        this.B = B;
        this.C = C;
        this.K = K;
        this.fromArrival = fromArrival;
        this.toArrival = toArrival;
        this.fromLeave = fromLeave;
        this.toLeave = toLeave;
    }

    public void arrive() throws Exception {
        if(size >= K)
            throw new Exception("Queue reached maximum size");

        // Cont Tempo
        if(size < K){
            size++;
            if(size<=1) {
                // agendaSaida(timer+NumberGenerator.getInstance().nextRandom(fromLeave,toLeave))
                scheduleLeave(timer,NumberGenerator.getInstance().nextRandom(fromLeave,toLeave));
            }
        }
        // agendaChegada(timer+NumberGenerator.getInstance().nextRandom(fromArrival,toArrival))
        scheduleArrival(timer, NumberGenerator.getInstance().nextRandom(fromArrival,toArrival));

    }

    public void leave() throws Exception {
        if (size <= 0)
            throw new Exception("Queue is empty");
        timer++;
        size--;
        if (size >= 1) {
            // agendaSaida(timer+NumberGenerator.getInstance().nextRandom(fromLeave,toLeave))
            scheduleLeave(timer,NumberGenerator.getInstance().nextRandom(fromLeave,toLeave));
        }
    }

    public double scheduleLeave(double timer, double rnd_number) {
        // U(A,B) = (B-A) x rnd_number + A
        double draw = (toLeave - fromLeave) * rnd_number + fromLeave;
        return timer + draw;
    }

    public double scheduleArrival(double timer, double rnd_number) {
        // U(A,B) = (B-A) x rnd_number + A
        double draw = (toArrival - fromArrival) * rnd_number + fromArrival;
        return timer + draw;
    }


    public int getSize(){
        return this.size;
    }
}
