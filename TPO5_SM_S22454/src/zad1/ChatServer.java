/**
 *
 *  @author Sasor-Adamczyk Mateusz S22454
 *
 */

package zad1;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
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

    private ArrayList<String>                   serverLogs;
    private HashMap<String, ArrayList<String>>  usersLogs;
    private HashMap<SocketAddress,String>       loggedUsers;

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

                Thread.currentThread().interrupt();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        serverThread.start();
    }

    public void serviceRequest(SocketChannel socketChannel){

    }

    public void stopServer() {
    }

    public boolean getServerLog() {
    }


}
