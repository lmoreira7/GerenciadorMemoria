package producer;

import memoryManager.Manager;
import request.Request;

public class Producer extends Thread{
    private Manager memoryManager;
    private int numRequest;

    public Producer(Manager memoryManager, int numRequest) {
        this.memoryManager = memoryManager;
        this.numRequest = numRequest;
    }

    public void run() {

        for(int i = 0; i < numRequest; i++) {
            Request newRequest = new Request(i+1);
            try {
                memoryManager.alocaRequest(newRequest);
                memoryManager.alocaHeap(newRequest.getIdRequest(), newRequest.getTamanhoRequest());
            } catch (Exception error) {
                error.printStackTrace();
            }
        }
    }
}
