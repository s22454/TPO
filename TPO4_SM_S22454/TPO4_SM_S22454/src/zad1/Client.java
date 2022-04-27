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
            System.out.println("Lacze z serverem!");
            channel = SocketChannel.open(new InetSocketAddress(host, port));
            channel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String send(String s) {
        try {
            System.out.println("Wysylam: " + s);
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            channel.write(ByteBuffer.wrap(s.getBytes(StandardCharsets.UTF_8)));
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