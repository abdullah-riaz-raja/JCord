package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;


import Jcord.MessageViewers;

class listenForMessage implements Runnable{
    int port;
    ServerSocket socket;
    Socket listen;

    public listenForMessage (int port){
         this.port = port;
    }

    @Override
    public void run() {
       
        try {
            this.socket = new ServerSocket(this.port);
            while(true){
                listen = this.socket.accept();
                ObjectInputStream is= new ObjectInputStream(this.listen.getInputStream());
                MessageViewers received = (MessageViewers)is.readObject();
                
                System.out.println(received.getMessage());
            }
            //this.listen.close();
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
public class Listener {
    int port;
    ServerSocket socket;
    Socket listen;

    /*
    public void listenForMessage(int port) throws IOException, ClassNotFoundException {
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
    */
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        int messageReceivePort = Integer.parseInt(Utils.getServerInfo("communication.json").get("server-message-receive-port"));
        Thread mesageListen = new Thread(new listenForMessage(messageReceivePort));
        mesageListen.start();;

    }
}
