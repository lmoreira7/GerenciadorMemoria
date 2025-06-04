package heap;

public class Heap {
    private int[] heap;
    private final int tamanhoHeap;
    private int firstFreePosition;

    public Heap(int tamanhoHeap) {
        this.tamanhoHeap = tamanhoHeap;
        heap = new int[tamanhoHeap];
    }

    public int[] getHeap() {
        return heap;
    }

    public int getTamanhoHeap() {
        return tamanhoHeap;
    }

    public int getFirstFreePosition() {
        return firstFreePosition;
    }

    public void setHeap(int[] newHeap) {
        this.heap = newHeap;
    }

    public void setPosLivre(int firstFreePosition) {
        this.firstFreePosition = firstFreePosition;
    }
}
