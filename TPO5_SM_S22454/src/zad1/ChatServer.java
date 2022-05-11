/**
 *
 *  @author Sasor-Adamczyk Mateusz S22454
 *
 */

package zad1;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class ChatServer {

    private ServerSocketChannel serverSocketChannel;
    private Selector            selector;
    private Thread              serverThread;

    private String  host;
    private int     port;
    private boolean serverIsRunning;

    private HashMap<String, ArrayList<String>>  usersLogs;
    private HashMap<String, SocketChannel>      loggedUsers;
    private ArrayList<String>                   serverLogs;

    public ChatServer(String host, int port) {
        this.host               = host;
        this.port               = port;
        this.serverIsRunning    = false;
        this.usersLogs          = new HashMap<>();
        this.serverLogs         = new ArrayList<>();
        this.loggedUsers        = new HashMap<>();
    }

    public void startServer() {

        serverThread = new Thread(() -> {
            try {
                serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.configureBlocking(false);
                serverSocketChannel.socket().bind(new InetSocketAddress(host, port));
                serverIsRunning = true;

                selector = Selector.open();
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

                while (serverIsRunning){

                    selector.select();

                    Set keys            = selector.selectedKeys();
                    Iterator iterator   = keys.iterator();

                    while (iterator.hasNext()){

                        SelectionKey key = (SelectionKey) iterator.next();
                        iterator.remove();

                        if (key.isAcceptable()){
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            continue;
                        }

                        if (key.isReadable()){
                            SocketChannel socketChannel = (SocketChannel) key.channel();
                            serviceRequest(socketChannel);
                        }
                    }
                }
            } catch (IOException e) {
               e.printStackTrace();
            }
        });

        serverThread.start();
        System.out.println("Server started\n");

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void serviceRequest(SocketChannel socketChannel){
        if (!socketChannel.isOpen()) return;

        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            socketChannel.read(byteBuffer);
            byteBuffer.flip();
            String request = String.valueOf(StandardCharsets.UTF_8.decode(byteBuffer));
//            System.out.println("Jestem server dostałem: " + request);

            if (request.matches("login\\s.+")){

                String currentUser = request.substring(6);
                loggedUsers.put(currentUser, socketChannel);
                usersLogs.put(currentUser, new ArrayList<>());
                usersLogs.get(currentUser).add("=== " + currentUser + " chat view");

                serverLogs.add(LocalDateTime.now().toLocalTime() + " " + currentUser + " logged in");

                char tmp = 219;
                sendToAll(currentUser + " logged in");

            } else if (request.matches("log out\\s.+")) {

                String currentUser = request.substring(8);

                sendToAll(currentUser + " logged out");

                loggedUsers.remove(currentUser);
                serverLogs.add(LocalDateTime.now().toLocalTime() + " " + currentUser + " logged out");

            } else if (request.matches("got it")){

            }else {

                String currentUser = "";
                for (String s : loggedUsers.keySet())
                    if (loggedUsers.get(s) == socketChannel)
                        currentUser = s;

                char tmp = 219;
                sendToAll(currentUser + ": " + request + tmp);

                serverLogs.add(LocalDateTime.now().toLocalTime() + " " + currentUser + ": " + request);

            }

        }catch (IOException  e){
            e.printStackTrace();
        }
    }

    public void sendToAll(String s){
        for (String user : loggedUsers.keySet()) {
            try {
                usersLogs.get(user).add(s);
//                System.out.println("Jako serwer wysyłam: " + s);
                loggedUsers.get(user).write(ByteBuffer.wrap(s.getBytes(StandardCharsets.UTF_8)));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopServer() {

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (loggedUsers.size() != 0){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        serverIsRunning = false;
        serverThread.interrupt();
        System.out.println("Server stopped\n");
    }

    public String getServerLog() {

        StringBuilder stringBuilder = new StringBuilder();

        for (String s : serverLogs)
            stringBuilder.append(s + "\n");

        return stringBuilder.toString();
    }


}
