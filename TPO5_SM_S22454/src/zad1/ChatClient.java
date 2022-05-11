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
import java.util.ArrayList;

public class ChatClient {

    private String  id;
    private String  host;
    private int     port;

    private SocketChannel   socketChannel;
    private boolean         loginStatus;

    private ArrayList<String> chat;

    public ChatClient(String host, int port, String id) {
        this.id             = id;
        this.host           = host;
        this.port           = port;
        this.loginStatus    = false;
        this.chat           = new ArrayList<>();
    }

    public void login(){
        try {

            socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
            socketChannel.configureBlocking(false);

            send("login " + id);
            loginStatus = true;

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            while (loginStatus){

                socketChannel.read(byteBuffer);

                if (byteBuffer.toString() != null){

                    byteBuffer.flip();
                    chat.add(String.valueOf(StandardCharsets.UTF_8.decode(byteBuffer)));
                    byteBuffer.clear();

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logout(){
        try {

            send("log out " + id);
            socketChannel.close();
            loginStatus = false;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String req){
        try {

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            socketChannel.write(ByteBuffer.wrap(req.getBytes(StandardCharsets.UTF_8)));
            byteBuffer.clear();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getChatView(){

        StringBuilder stringBuilder = new StringBuilder();
        for (String s : chat)
            stringBuilder.append(s + "\n");

        return stringBuilder.toString();
    }
}
