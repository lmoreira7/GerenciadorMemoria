package producer;

import memoryManager.Manager;
import request.Request;

public class Producer extends Thread{
    private Manager memoryManager;
    private int numRequest;
    public long beginTime;
    public long finishTime;

    public Producer(Manager memoryManager, int numRequest) {
        this.memoryManager = memoryManager;
        this.numRequest = numRequest;
    }

    public void run() {
        beginTime = System.currentTimeMillis();
        for(int i = 0; i < numRequest; i++) {
            Request newRequest = new Request(i+1);
            try {
                memoryManager.alocaRequest(newRequest);
                memoryManager.alocaHeap(newRequest.getIdRequest(), newRequest.getTamanhoRequest());
            } catch (Exception error) {
                error.printStackTrace();
            }
        }
        finishTime = System.currentTimeMillis();
    }
}
