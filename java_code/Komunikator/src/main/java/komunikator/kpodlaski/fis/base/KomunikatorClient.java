package komunikator.kpodlaski.fis.base;

import java.io.IOException;
import java.net.Socket;

public abstract class KomunikatorClient implements MessageListener {
    private SocketConnection sockConn;
    //Interfejs graficzny

    public void connect(String ip, int port) throws IOException {
        Socket socket = new Socket(ip, port);
        sockConn = SocketConnection.create(socket);
        sockConn.addListener(this);
    }

    protected void sendMessage(String message){
        sockConn.sendMessage(message+"\n");
    }

}
