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
import java.util.*;

public class Server {

    private ServerSocketChannel serverSocketChannel;
    private Selector            selector;
    
    private String  host;
    private int     port;
    private boolean serverIsRunning;

    private HashMap<String, ArrayList<String>>  logs;
    private String                              nowLogged;

    public Server(String host, int port) {
        this.host = host;
        this.port = port;
        this.serverIsRunning = false;
        this.logs = new HashMap<>();
        nowLogged = "";
    }

    public void startServer() {
        new Thread(() -> {
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

//                Thread.currentThread().interrupt();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        serverIsRunning = false;
    }

    public String getServerLog() {
        return "log";
    }

    private void serviceRequest(SocketChannel socketChannel){
        if (!socketChannel.isOpen()) return;

        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            socketChannel.read(byteBuffer);
            byteBuffer.flip();
            String request = String.valueOf(StandardCharsets.UTF_8.decode(byteBuffer));

            if (request.matches("login\\s.*") && nowLogged.length() == 0){

                logs.put(request.substring(6), new ArrayList<>());
                nowLogged = request.substring(6);
                logs.get(nowLogged).add("=== " + nowLogged + " log start ===");
                logs.get(nowLogged).add("logged in");

                byteBuffer.clear();
                socketChannel.write(ByteBuffer.wrap("logged in".getBytes(StandardCharsets.UTF_8)));

            } else if (request.matches("bye")){

                logs.get(nowLogged).add("logged out");
                logs.get(nowLogged).add("=== " + nowLogged + " log end ===");
                nowLogged = "";

                byteBuffer.clear();
                socketChannel.write(ByteBuffer.wrap("logged out".getBytes(StandardCharsets.UTF_8)));

            } else if (request.matches("bye and log transfer")){

                logs.get(nowLogged).add("logged out");
                logs.get(nowLogged).add("=== " + nowLogged + " log end ===");

                StringBuilder stringBuilder = new StringBuilder();
                for (String s : logs.get(nowLogged))
                    stringBuilder.append(s + "\n");



                nowLogged = "";

                byteBuffer.clear();
                socketChannel.write(ByteBuffer.wrap(stringBuilder.toString().getBytes(StandardCharsets.UTF_8)));

            } else if (request.matches("\\d{4}-\\d{2}-\\d{2}\\s\\d{4}-\\d{2}-\\d{2}") || request.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}\\s\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}")){

                int x       = request.indexOf(" ");
                String from = request.substring(0, x);
                String to   = request.substring(x + 1);
                String res  = Time.passed(from,to);

                logs.get(nowLogged).add("Request: " + request);
                logs.get(nowLogged).add("Result:");
                logs.get(nowLogged).add(res);

                byteBuffer.clear();
                socketChannel.write(ByteBuffer.wrap(res.getBytes(StandardCharsets.UTF_8)));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
