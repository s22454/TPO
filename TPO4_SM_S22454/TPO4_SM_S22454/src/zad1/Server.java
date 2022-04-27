/**
 *
 *  @author Sasor-Adamczyk Mateusz S22454
 *
 */

package zad1;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

public class Server {

    private ServerSocketChannel serverSocketChannel;
    private Selector            selector;
    private Thread              serverThread;
    
    private String  host;
    private int     port;
    private boolean serverIsRunning;

    private ArrayList<String>                   serverLogs;
    private HashMap<String, ArrayList<String>>  usersLogs;
    private HashMap<SocketAddress,String>       loggedUsers;

    public Server(String host, int port) {
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

                while(serverIsRunning) {
                    selector.select();

                    Set keys = selector.selectedKeys();
                    Iterator iterator = keys.iterator();

                    while(iterator.hasNext()){

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

                Thread.currentThread().interrupt();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        serverThread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        serverIsRunning = false;

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        serverThread.interrupt();
    }

    public String getServerLog() {
        if (serverLogs.size() == 0)
            return "";

        StringBuilder stringBuilder = new StringBuilder();

        for (String s : serverLogs)
            stringBuilder.append(s + "\n");

        return stringBuilder.substring(0,stringBuilder.length() - 1);
    }

    private void serviceRequest(SocketChannel socketChannel){
        if (!socketChannel.isOpen()) return;

        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            socketChannel.read(byteBuffer);
            byteBuffer.flip();
            String request = String.valueOf(StandardCharsets.UTF_8.decode(byteBuffer));
            String nowLogged = "";

            if (loggedUsers.containsKey(socketChannel.getRemoteAddress()))
                nowLogged = loggedUsers.get(socketChannel.getRemoteAddress());

            if (request.matches("login\\s.*") && nowLogged.length() == 0){

                nowLogged = request.substring(6);
                usersLogs.put(nowLogged, new ArrayList<>());
                usersLogs.get(nowLogged).add("=== " + nowLogged + " log start ===");
                usersLogs.get(nowLogged).add("logged in");

                serverLogs.add(nowLogged + " logged in at " + LocalDateTime.now().toLocalTime());
                loggedUsers.put(socketChannel.getRemoteAddress(), nowLogged);

                byteBuffer.clear();
                socketChannel.write(ByteBuffer.wrap("logged in".getBytes(StandardCharsets.UTF_8)));

            } else if (request.matches("bye")){

                serverLogs.add(nowLogged + " logged out at " + LocalDateTime.now().toLocalTime());
                usersLogs.get(nowLogged).add("logged out");
                usersLogs.get(nowLogged).add("=== " + nowLogged + " log end ===");
                loggedUsers.remove(socketChannel.getRemoteAddress());

                byteBuffer.clear();
                socketChannel.write(ByteBuffer.wrap("logged out".getBytes(StandardCharsets.UTF_8)));

            } else if (request.matches("bye and log transfer")){

                serverLogs.add(nowLogged + " logged out at " + LocalDateTime.now().toLocalTime());
                usersLogs.get(nowLogged).add("logged out");
                usersLogs.get(nowLogged).add("=== " + nowLogged + " log end ===");

                StringBuilder stringBuilder = new StringBuilder();
                for (String s : usersLogs.get(nowLogged))
                    stringBuilder.append(s + "\n");
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);

                loggedUsers.remove(socketChannel.getRemoteAddress());

                byteBuffer.clear();
                socketChannel.write(ByteBuffer.wrap(stringBuilder.toString().getBytes(StandardCharsets.UTF_8)));

            } else if (request.matches("\\d{4}-\\d{2}-\\d{2}\\s\\d{4}-\\d{2}-\\d{2}") || request.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}\\s\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}")){

                int x       = request.indexOf(" ");
                String from = request.substring(0, x);
                String to   = request.substring(x + 1);
                String res  = Time.passed(from,to);

                serverLogs.add(nowLogged + " request at " + LocalDateTime.now().toLocalTime() + ": \"" + request + "\"");
                usersLogs.get(nowLogged).add("Request: " + request);
                usersLogs.get(nowLogged).add("Result:");
                usersLogs.get(nowLogged).add(res);

                byteBuffer.clear();
                socketChannel.write(ByteBuffer.wrap(res.getBytes(StandardCharsets.UTF_8)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
