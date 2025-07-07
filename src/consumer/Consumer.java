package consumer;

import memoryManager.Manager;
import request.Request;

import java.util.concurrent.BlockingQueue;

public class Consumer extends Thread{
    Manager memoryManager;
    public long beginTime;
    public long finishTime;
    private final BlockingQueue<Request> listRequest;

    public Consumer(Manager memoryManager, BlockingQueue<Request> listRequest) {
        this.memoryManager = memoryManager;
        this.listRequest = listRequest;
    }

    public void run() {
        beginTime = System.currentTimeMillis();
        try {
            while(true) {
                Request request = listRequest.take();

                if (request.getIdRequest() == -1) {
                    break;
                }

                synchronized (memoryManager) {
                    if (!memoryManager.freeSpaceHeap(request.getTamanhoRequest())) {
                        int memoryFree = 0;

                        while (memoryFree < (0.3 * memoryManager.getHeap().getTamanhoHeap())) {
                            Request old = memoryManager.removeRequest();

                            if (old == null) {
                                break;
                            }

                            memoryFree += old.getTamanhoRequest();
                            memoryManager.desalocaHeap(old.getIdRequest(), old.getTamanhoRequest());
                        }
                    }
                    memoryManager.alocaRequest(request);
                    memoryManager.alocaHeap(request.getIdRequest(), request.getTamanhoRequest());
                }
            }
        } catch (Exception error) {
            error.printStackTrace();
        }

        finishTime = System.currentTimeMillis();
    }
}
