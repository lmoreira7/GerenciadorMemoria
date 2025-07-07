package consumer;

import memoryManager.Manager;
import request.Request;

public class Consumer extends Thread{
    Manager memoryManager;
    public long beginTime;
    public long finishTime;

    public Consumer(Manager memoryManager) {
        this.memoryManager = memoryManager;
    }

    public void run() {
        beginTime = System.currentTimeMillis();
        try {
            while(true) {
                Request removeRequest = memoryManager.removeRequest();

                if(removeRequest == null) {
                    break;
                }

                if(!memoryManager.freeSpaceHeap(removeRequest.getTamanhoRequest())) {
                    int memoryFree = 0;

                    while(memoryFree < (0.3 * memoryManager.getHeap().getTamanhoHeap())) {
                        Request old = memoryManager.removeRequest();

                        if(old == null) {
                            break;
                        }

                        memoryFree += old.getTamanhoRequest();
                        memoryManager.desalocaHeap(old.getIdRequest(), old.getTamanhoRequest());
                    }
                }

                memoryManager.desalocaHeap(removeRequest.getIdRequest(), removeRequest.getTamanhoRequest());
            }
        } catch (Exception error) {
            error.printStackTrace();
        }

        finishTime = System.currentTimeMillis();
    }
}
