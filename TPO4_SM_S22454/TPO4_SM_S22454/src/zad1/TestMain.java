package zad1;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TestMain {
    public static void main(String[] args) {

        new Thread(() -> {
            Server server = new Server("localhost", 25565);
            server.startServer();
        }).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Socket socket = null;
        try {
            socket = new Socket("localhost", 25565);
            socket.getOutputStream().write("lol".getBytes(StandardCharsets.UTF_8));
            socket.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Client client = new Client("localhost", 25565, "12");
        client.connect();


        while (true) {
            try {
                Thread.sleep(500);
                client.send("lol");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
