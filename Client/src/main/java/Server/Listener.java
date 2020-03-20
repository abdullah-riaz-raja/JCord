package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import Jcord.MessageViewers;

class ServerComms{
    int newMessagePort;
    int newMessageRequestPort;
    ServerSocket socket;
    Socket listen;
    ArrayList<MessageViewers> sessionMessages = new ArrayList<MessageViewers>();

    public ServerComms(int newMessagePort,int newMessageRequestPort){
         this.newMessagePort = newMessagePort;
         this.newMessageRequestPort = newMessageRequestPort;
    }

    public void listenForNewMessage() {
        try {
            this.socket = new ServerSocket(this.newMessagePort);
            while(true){
                listen = this.socket.accept();
                ObjectInputStream is= new ObjectInputStream(this.listen.getInputStream());
                sessionMessages.add((MessageViewers)is.readObject());
                
                
                System.out.println(sessionMessages.get((sessionMessages.size()-1)).getMessage());
            }
            //this.listen.close();
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getNewestId(){
        // send over network 
        int newId = this.sessionMessages.size()-1;
    }

    public void listenForNewMessageRequest(){

    }

}

public class Listener {
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        int messageReceivePort = Integer.parseInt(Utils.getServerInfo("communication.json").get("server-message-receive-port"));
        int checkForNew = Integer.parseInt(Utils.getServerInfo("communication.json").get("check-for-new-message"));
        
        ServerComms coms = new ServerComms(messageReceivePort,checkForNew);
        
        Thread mesageListen = new Thread(){
            @Override
            public void run() {
                coms.listenForNewMessage();
            }
        };

        Thread sendMsg = new Thread(){
            @Override
            public void run() {
                coms.listenFoeNewMessage();
            }
        };  
        
        
        
        
        
        mesageListen.start();
        sendMsg.start();
    }
}
