/**
 *
 *  @author Sasor-Adamczyk Mateusz S22454
 *
 */

package zad1;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ClientTask implements Runnable {

    private Client          client;
    private List<String>    requests;
    private boolean         showSendRes;

    private boolean             isItDone;
    private ArrayList<String>   responses;

    private ClientTask(Client c, List<String> reqList, boolean showRes){
        this.client = c;
        this.requests = reqList;
        this.showSendRes = showRes;
        this.isItDone = false;
        this.responses = new ArrayList<>();
    }

    public static ClientTask create(Client c, List<String> reqList, boolean showRes) {
        return new ClientTask(c, reqList, showRes);
    }

    public String get() throws InterruptedException, ExecutionException {

        while (!isItDone){
            Thread.sleep(200);
        }

        if (responses.size() != 0){
            StringBuilder stringBuilder = new StringBuilder();

            for (String s : responses)
                stringBuilder.append(s + "\n");

            return stringBuilder.toString();
        } else{
            return "";
        }
    }

    @Override
    public void run() {

        client.connect();
        client.send("login " + client.getId());

        for (String s : requests) {
            String res = client.send(s);
            if (showSendRes) System.out.println(res);;
        }

        String log = client.send("bye and log transfer");
        responses.add(log);
        this.isItDone = true;
    }
}
