/**
 *
 *  @author Sasor-Adamczyk Mateusz S22454
 *
 */

package zad1;


import java.util.List;
import java.util.concurrent.ExecutionException;

public class ClientTask implements Runnable {

    private Client          client;
    private List<String>    requests;
    private boolean         showSendRes;

    private ClientTask(Client c, List<String> reqList, boolean showRes){
        this.client = c;
        this.requests = reqList;
        this.showSendRes = showRes;
    }

    public static ClientTask create(Client c, List<String> reqList, boolean showRes) {
        return new ClientTask(c, reqList, showRes);
    }

    public String get() throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public void run() {

    }
}
