package Jcord;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CommunicationClient {
    Socket remote = null;
    String ip;
    int port;

    public CommunicationClient(String host, int port) {
        this.ip = host;
        this.port = port;
    }

    // returns true if succesful, otherwise false
    public boolean establishConnection() {
        try {
            this.remote = new Socket(this.ip, this.port);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could Not Connect to Remote Server. Is the server down?");
            return false;
        }
    }

    public void sendMessage(MessageViewers message) throws IOException {
        ObjectOutputStream os = new ObjectOutputStream(this.remote.getOutputStream());
        os.writeObject(message);
    }

    class listenForNewMessage{
        
    }
}