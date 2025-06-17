package komunikator.kpodlaski.fis.base;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketConnection implements Sender, MessageInformer, Runnable {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private List<MessageListener> listeners = new ArrayList<>();
    private boolean isRunning = false;

    public static SocketConnection create(Socket socket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream());

        return new SocketConnection(socket, in, out);
    }
    private SocketConnection(Socket socket,  BufferedReader in, PrintWriter out) {
        this.socket = socket;
        this.in = in;
        this.out = out;
        isRunning = true;
        Thread t = new Thread(this);
        t.start();

    }
    public void run(){
        try {
            while(isRunning){
                String msg = null;
                msg = in.readLine();
                messageArrived(msg);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void messageArrived(String message) {
        for(MessageListener listener : listeners) {
            listener.newMessage(message);
        }
    }
    @Override
    public void addListener(MessageListener listener) {
        listeners.add(listener);
    }

    @Override
    public void sendMessage(String message) {
        out.println(message);
        out.flush();
    }
}
