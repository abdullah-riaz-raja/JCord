package Jcord;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.HashSet;

public class CommunicationClient{
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private Socket remote = null;
    private String ip;
    private int port;

    public CommunicationClient(String host, int port) throws IOException {
        this.ip = host;
        this.port = port;
        this.remote = new Socket(this.ip, this.port);
        outputStream = new ObjectOutputStream(this.remote.getOutputStream());
        inputStream =  new ObjectInputStream(this.remote.getInputStream());
    }

    // returns true if successful, otherwise false
    public boolean establishConnection() {
        if(remote.isConnected())
        {
            return true;
        }else
        {
            System.out.println("Could Not Connect to Remote Server. Is the server down?");
            return false;
        }
    }

    public <T> void sendMessage(T message){
        try
        {
            outputStream.writeObject(message);
            outputStream.flush();
        }catch(IOException e)
        {
            e.printStackTrace();
            System.out.println("Failed to send message.");
        }
    }

    public ArrayList<Message> getNewMessage(int id) throws IOException, ClassNotFoundException {
        outputStream.writeObject(id);
        outputStream.flush();
        Object response = inputStream.readObject();

        ArrayList<Message> newMsg = new ArrayList<Message>();
        if (response instanceof ArrayList){
            newMsg = (ArrayList<Message>)response;
        }

        return newMsg;
    }

    public HashSet<User> getNewUser(User user) throws IOException, ClassNotFoundException {
        outputStream.writeObject(user);
        outputStream.flush();
        Object response = inputStream.readObject();

        HashSet<User> newUsers = new HashSet<User>();
        if (response instanceof HashSet){
            newUsers = (HashSet<User>)response;
        }

        return newUsers;
    }

    public void closeConnection()
    {
        try {
            outputStream.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        // Closing Input Stream
        try {
            inputStream.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        // Closing Socket
        try {
            remote.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}