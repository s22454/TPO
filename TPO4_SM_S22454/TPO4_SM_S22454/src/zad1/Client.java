/**
 *
 *  @author Sasor-Adamczyk Mateusz S22454
 *
 */

package zad1;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Client {

    private SocketChannel channel;

    private String  host;
    private String  id;
    private int     port;

    public Client(String host, int port, String id) {
        this.host = host;
        this.port = port;
        this.id = id;
    }

    public void connect() {
        try {
            channel = SocketChannel.open(new InetSocketAddress(host, port));
            channel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String send(String s) {
        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.put(s.getBytes(StandardCharsets.UTF_8));

            channel.write(byteBuffer);
            byteBuffer.clear();

            channel.read(byteBuffer);
            byteBuffer.flip();

            return new String(byteBuffer.array());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Cos poszlo nie tak";
    }
}
