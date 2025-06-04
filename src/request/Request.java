package request;

import java.util.Random;

public class Request {
    private final int idRequest;
    private final int tamanhoRequest;

    public Request(int idRequest) {
        Random random = new Random();
        this.idRequest = idRequest;
        tamanhoRequest = ((random.nextInt(16,1024))/4);
    }

    public int getIdRequest() {
        return idRequest;
    }

    public int getTamanhoRequest() {
        return tamanhoRequest;
    }
}
