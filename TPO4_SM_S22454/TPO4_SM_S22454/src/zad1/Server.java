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
import java.util.Iterator;
import java.util.Set;

public class Server {

    private ServerSocketChannel serverSocketChannel;
    private Selector            selector;
    
    private String  host;
    private int     port;

    private boolean serverIsRunning;

    public Server(String host, int port) {
        this.host = host;
        this.port = port;
        this.serverIsRunning = false;
    }

    public void startServer() {
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
                        // metoda do obslugi zlecenia
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
    }

    public boolean getServerLog() {
        return true;
    }

    private void serviceRequest(SocketChannel socketChannel){
        if (!socketChannel.isOpen()) return;

        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            socketChannel.read(byteBuffer);
            System.out.println(new String(byteBuffer.array()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
