import consumer.Consumer;
import memoryManager.Manager;
import producer.Producer;
import request.Request;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public static void main(String[] args) {

        try {
            System.out.println("Informe o tamanho da Heap em KB: ");
            Scanner scan = new Scanner(System.in);
            int tamanhoHeap = ((scan.nextInt()*1024)/4);
            int numRequest = 1000000;

            Manager memoryManager = new Manager(tamanhoHeap);

            BlockingQueue<Request> listRequest = new LinkedBlockingQueue<>();

            Producer producer = new Producer(numRequest, listRequest);
            Consumer consumer1 = new Consumer(memoryManager, listRequest);
            Consumer consumer2 = new Consumer(memoryManager, listRequest);

            long start = System.currentTimeMillis();

            producer.start();
            consumer1.start();
            consumer2.start();

            producer.join();
            consumer1.join();
            consumer2.join();

            //long timeProducer = (producer.finishTime - producer.beginTime);
            //long timeConsumer = (consumer.finishTime - consumer.beginTime);

            //long executationTime = (timeProducer + timeConsumer);

            long end = System.currentTimeMillis();


            FileWriter relatorio = getWriter(numRequest, tamanhoHeap, (end - start));
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