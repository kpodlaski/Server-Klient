package komunikator.kpodlaski.fis.base;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class KomunikatorServer implements MessageListener {
    ServerSocket ss;
    List<SocketConnection> clients = new ArrayList<>();

    public KomunikatorServer(ServerSocket ss){
        this.ss = ss;
    }

    public void start() throws IOException {
        while(true){
           Socket s = ss.accept();
           SocketConnection sockConn = SocketConnection.create(s);
           sockConn.addListener(this);
           clients.add(sockConn);  //ryzyko wielowątkowe

        }
    }

    @Override
    public void newMessage(String message) {
        for(SocketConnection sockConn : clients){ //ryzyko wielowątkowe
            sockConn.sendMessage(message);
        }
    }


    public static int port = 7777;
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(port);
        KomunikatorServer server = new KomunikatorServer(ss);
        server.start();
    }
}
