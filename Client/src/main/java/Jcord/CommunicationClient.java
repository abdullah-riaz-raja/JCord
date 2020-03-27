package Jcord;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.HashSet;
import java.net.Socket;

/**
 * This class handles the communication to the server
 *
 * @see Socket
 * @see ObjectOutputStream
 * @see ObjectInputStream
 */
public class CommunicationClient{
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private Socket remote = null;
    private String ip;
    private int port;

    /**
     * This constructor takes in the ip and port of the server and initializes this class
     *
     * @param host String value of the ip address
     * @param port int value of the port
     * @throws IOException
     */
    public CommunicationClient(String host, int port) throws IOException {
        this.ip = host;
        this.port = port;
        this.remote = new Socket(this.ip, this.port);
        outputStream = new ObjectOutputStream(this.remote.getOutputStream());
        inputStream =  new ObjectInputStream(this.remote.getInputStream());
    }

    /**
     * This method checks if the user is still connected
     * @return boolean value of socket being connected
     */
    public boolean establishConnection() {
        if(remote.isConnected())
        {
            return true;
        }else
        {
            System.out.println("Unable to connect to the server.");
            return false;
        }
    }

    /**
     * This generic method takes in a message and sends the message to the server
     *
     * @param message the message that needs to be sent
     * @param <T> denotes the type parameter of message
     * @throws IOException
     */
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

    /**
     * This method takes in a message id value and request the server the new messages
     *
     * @param id int of the latest message id
     * @return ArrayList<Message> of the newest messages
     * @throws IOException
     * @throws ClassNotFoundException
     */
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

    /**
     * This method takes in the current user object and request the server of the other user's status
     * @param user
     * @return HashSet<User> of the online users
     * @throws IOException
     * @throws ClassNotFoundException
     */
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

    /**
     * This method closes the streams and sockets connected to the server
     *
     * @see Socket
     */
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