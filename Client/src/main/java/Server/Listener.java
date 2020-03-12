package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;


import Jcord.MessageViewers;

public class Listener {
    int port;
    ServerSocket socket;
    Socket listen;

    public Listener(int port) throws IOException, ClassNotFoundException {
        this.port = port;
        try {
            this.socket = new ServerSocket(this.port);
            listen = this.socket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    
        ObjectInputStream is= new ObjectInputStream(this.listen.getInputStream());
        MessageViewers received = (MessageViewers)is.readObject();
        
        System.out.println(received.getDate());

        this.listen.close();
    }

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        System.out.println("Starting Server");
        new Listener(2542);
    }
}
