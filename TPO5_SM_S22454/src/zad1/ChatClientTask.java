/**
 *
 *  @author Sasor-Adamczyk Mateusz S22454
 *
 */

package zad1;


import java.util.List;
import java.util.concurrent.ExecutionException;

public class ChatClientTask implements Runnable{

    private ChatClient      client;
    private List<String>    msgs;
    private int             wait;

    private ChatClientTask(ChatClient client, List<String> msgs, int wait){
        this.client = client;
        this.msgs   = msgs;
        this.wait   = wait;
    }

    public static ChatClientTask create(ChatClient c, List<String> msgs, int wait) {
        return new ChatClientTask(c,msgs,wait);
    }

    @Override
    public void run() {

        client.login();

        for (String s : msgs){
            if (wait != 0) {
                try {
                    Thread.sleep(wait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            client.send(s);
        }

        if (wait != 0) {
            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        client.logout();

        if (wait != 0) {
            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void get() throws InterruptedException, ExecutionException {
    }

    public ChatClient getClient(){
        return client;
    }
}
