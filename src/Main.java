import consumer.Consumer;
import memoryManager.Manager;
import producer.Producer;
import request.Request;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try {
            System.out.println("Informe o tamanho da Heap em KB: ");
            Scanner scan = new Scanner(System.in);
            int tamanhoHeap = ((scan.nextInt()*1024)/4);
            int numRequest = 500000;

            Manager memoryManager = new Manager(tamanhoHeap);
            Producer producer = new Producer(memoryManager, numRequest);
            Consumer consumer = new Consumer(memoryManager);

            producer.start();
            consumer.start();

            producer.join();
            consumer.join();

            long timeProducer = (producer.finishTime - producer.beginTime);
            long timeConsumer = (consumer.finishTime - consumer.beginTime);

            long executationTime = (timeProducer + timeConsumer);

            FileWriter relatorio = getWriter(numRequest, tamanhoHeap, executationTime);
            relatorio.close();

        } catch (Exception executionError) {
            System.err.println("Erro: ");
            executionError.printStackTrace(System.err);
        }
    }

    private static FileWriter getWriter(int numRequest, int tamanhoHeap, long executionTime) throws IOException {
        FileWriter relatorio = new FileWriter("Relatorio");
        relatorio.write("============================\n");
        relatorio.write("N° de requisições: " + numRequest + "\n");
        relatorio.write("Tamanho da Heap: " + (tamanhoHeap*4) + " Bytes\n");
        relatorio.write("Tempo de execução: " + executionTime + "ms\n");
        relatorio.write("============================\n");
        return relatorio;
    }
}