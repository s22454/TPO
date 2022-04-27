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

    private HashMap<String, ArrayList<String>> logs;

    public Server(String host, int port) {
        this.host = host;
        this.port = port;
        this.serverIsRunning = false;
        this.logs = new HashMap<>();
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

            System.out.println(request);

            if (request.matches("login .*")){

                byteBuffer.clear();
                socketChannel.write(ByteBuffer.wrap("logged in".getBytes(StandardCharsets.UTF_8)));

            } else if (request.matches("bye")){

                byteBuffer.clear();
                socketChannel.write(ByteBuffer.wrap("logged out".getBytes(StandardCharsets.UTF_8)));

            } else if (request.matches("bye and log transfer")){

                byteBuffer.clear();
                socketChannel.write(ByteBuffer.wrap("zawartosc".getBytes(StandardCharsets.UTF_8)));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
