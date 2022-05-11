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
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            send("login " + id);

            int tmp = 0;
            while (tmp == 0)
                tmp = socketChannel.read(byteBuffer);

            loginStatus = true;
            byteBuffer.flip();
//            System.out.println("Jako " + id + " dostałem: " + StandardCharsets.UTF_8.decode(byteBuffer));
            byteBuffer.flip();
            chat.add(String.valueOf(StandardCharsets.UTF_8.decode(byteBuffer)));

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void logout(){
        try {

            send("log out " + id);

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            int tmp = 0;
            while (tmp == 0)
                tmp = socketChannel.read(byteBuffer);

            byteBuffer.flip();
//            System.out.println("Jako " + id + " dostałem: " + StandardCharsets.UTF_8.decode(byteBuffer));
            chat.add(String.valueOf(StandardCharsets.UTF_8.decode(byteBuffer)));

            loginStatus = false;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String req){
        try {

//            System.out.println(id + " wysyłam: " + req);
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            socketChannel.write(ByteBuffer.wrap(req.getBytes(StandardCharsets.UTF_8)));
            byteBuffer.clear();

            int tmp = 0;
            while (tmp == 0)
                tmp = socketChannel.read(byteBuffer);

            byteBuffer.flip();
            String resp = String.valueOf(StandardCharsets.UTF_8.decode(byteBuffer));
//            System.out.println("Jako " + id + " dostałem: " + resp);
            char c = 219;
            String[] messages = resp.split(String.valueOf(c));
//            System.out.print("i przetłumaczyłem na: ");
            for (String s : messages) {
//                System.out.println(s + ", ");
                    chat.add(s);
            }

        } catch (IOException  e) {
            e.printStackTrace();
        }
    }

    public String getChatView(){

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("=== " + id + " chat view\n");
        for (String s : chat)
            stringBuilder.append(s + "\n");

        return stringBuilder.toString();
    }
}
