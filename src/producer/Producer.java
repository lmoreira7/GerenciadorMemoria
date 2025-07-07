package producer;

import memoryManager.Manager;
import request.Request;

import java.util.concurrent.BlockingQueue;

public class Producer extends Thread{
    private Manager memoryManager;
    private int numRequest;
    public long beginTime;
    public long finishTime;
    private final BlockingQueue<Request> listRequest;

    public Producer(int numRequest, BlockingQueue<Request> listRequest) {
        this.numRequest = numRequest;
        this.listRequest = listRequest;
    }

    public void run() {
        beginTime = System.currentTimeMillis();
        try {
            for(int i = 0 ; i < numRequest; i++) {
                listRequest.put(new Request(i+1));
            }

            listRequest.put(new Request(-1));
            listRequest.put(new Request(-1));

        } catch (Exception error) {
            error.printStackTrace();
        }
        finishTime = System.currentTimeMillis();
    }
}
