import memoryManager.Manager;
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
            int numRequest = 155000;

            Manager memoryManager = new Manager(tamanhoHeap);

            long executionTimeBegin = System.currentTimeMillis();
            for(int i = 0; i < numRequest; i++) {
                Request newRequest = new Request(i+1);
                memoryManager.alocaRequest(newRequest);
                if(!memoryManager.freeSpaceHeap(newRequest.getTamanhoRequest())) {
                    int memoryFree = 0;
                    while(memoryFree < (0.3*memoryManager.getHeap().getTamanhoHeap())) {
                        Request removeRequest = memoryManager.removeRequest();
                        if(removeRequest == null) {
                            break;
                        }
                        memoryFree += removeRequest.getTamanhoRequest();
                        memoryManager.desalocaHeap(removeRequest.getIdRequest(), removeRequest.getTamanhoRequest());
                    }
                }
                memoryManager.alocaHeap(newRequest.getIdRequest(), newRequest.getTamanhoRequest());
            }
            long executionTimeFinish = System.currentTimeMillis();

            FileWriter relatorio = getWriter(numRequest, tamanhoHeap, (executionTimeFinish - executionTimeBegin));
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