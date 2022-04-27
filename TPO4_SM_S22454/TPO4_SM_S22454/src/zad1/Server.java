/**
 *
 *  @author Sasor-Adamczyk Mateusz S22454
 *
 */

package zad1;


import javax.swing.text.html.HTMLDocument;
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
        try {
            System.out.println("Server Startuje!");
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        serverIsRunning = false;
    }

    public boolean getServerLog() {
        return true;
    }

    private void serviceRequest(SocketChannel socketChannel){
        if (!socketChannel.isOpen()) return;

        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            socketChannel.read(byteBuffer);
            byteBuffer.flip();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
