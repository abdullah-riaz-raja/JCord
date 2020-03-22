package Jcord;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.function.Consumer;

public class CommunicationClient implements Runnable{
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private Socket remote = null;
    private String ip;
    private int port;
    private Consumer<Message> chatAppender;
    private Consumer<Message> voicePlay;

    public CommunicationClient(String host, int port) throws IOException {
        this.ip = host;
        this.port = port;
        this.remote = new Socket(this.ip, this.port);
        outputStream = new ObjectOutputStream(this.remote.getOutputStream());
        inputStream =  new ObjectInputStream(this.remote.getInputStream());
    }

    public CommunicationClient(String host, int port, Consumer<Message> chatAppender, Consumer<Message> voicePlay) {
        this.ip = host;
        this.port = port;
        this.chatAppender = chatAppender;
        this.voicePlay = voicePlay;
    }

    public void run()
    {
        try {
            // Setting Up Streams and Port
            this.remote = new Socket(this.ip, this.port);
            outputStream = new ObjectOutputStream(this.remote.getOutputStream());
            inputStream =  new ObjectInputStream(this.remote.getInputStream());

            // Continue Listening For Server Response
            while(remote.isConnected())
            {
                Message message = (Message) inputStream.readObject();
                if (message != null)
                {
                    System.out.println("Message Not Null");
                    switch(message.getMessageType())
                    {
                        case MESSAGE:
                            System.out.println("Of Type Message");
                            chatAppender.accept(message);
                            break;
                        case VOICEMESSAGE:
                            System.out.println("Of Type Voice");
                            voicePlay.accept(message);
                            break;
                    }
                }

            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally
        {
            closeConnection();
        }
    }

    // returns true if succesful, otherwise false
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

    public void sendMessage(Message message){
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