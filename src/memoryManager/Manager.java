package memoryManager;

import heap.Heap;
import request.Request;

import java.util.LinkedList;

public class Manager {
    private final Heap heap;
    private final LinkedList<Request> listRequest = new LinkedList<>();

    public Manager(int tamanhoHeap) {
        this.heap = new Heap(tamanhoHeap);
    }

    public synchronized void alocaHeap(int idRequest, int tamanhoRequest) {
        int index = heap.getFirstFreePosition();

        while(tamanhoRequest > 0) {
            heap.getHeap()[index] = idRequest;
            index++;
            tamanhoRequest--;
        }

        heap.setPosLivre((heap.getFirstFreePosition() + tamanhoRequest));
    }

    public synchronized void desalocaHeap(int idRequest, int tamanhoRequest) {
        int indexRemove = 0;
        while(tamanhoRequest > 0) {
            if(heap.getHeap()[indexRemove] == idRequest) {
                heap.getHeap()[indexRemove] = 0;
                tamanhoRequest--;
            }

            indexRemove++;
        }
        desfragmentaHeap();
    }

    private void desfragmentaHeap() {
        int[] heapDesgrafmentada = new int[heap.getTamanhoHeap()];
        int indexHeap = 0;
        for(int i = 0; i < heap.getTamanhoHeap(); i++) {
            if(heap.getHeap()[i] != 0) {
                heapDesgrafmentada[indexHeap] = heap.getHeap()[i];
                indexHeap++;
            }
        }
        heap.setPosLivre(indexHeap);
        heap.setHeap(heapDesgrafmentada);
    }

    public synchronized void alocaRequest(Request newRequest) {
        listRequest.addLast(newRequest);
    }

    public synchronized Request removeRequest() {
        if(listRequest.isEmpty()) {
            return null;
        }

        return listRequest.removeFirst();
    }

    public Heap getHeap() {
        return heap;
    }

    public boolean freeSpaceHeap(int tamanhoRequest) {
        if(tamanhoRequest > ((heap.getTamanhoHeap() - heap.getFirstFreePosition()))) {
            return false;
        }

        return true;
    }

}
