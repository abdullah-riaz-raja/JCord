package Server;

import java.io.Serializable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import Jcord.User;
import Jcord.Message;
import Jcord.MessageType.*;

public class Server {
    private static ArrayList<Message> sessionMessages = new ArrayList<Message>();

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        // Initialize Server Socket
        ServerSocket serverSocket = new ServerSocket(
                Integer.parseInt(Utils.getServerInfo("communication.json").get("server-message-receive-port")));

        try {
            // Listens for a new request
            while (!serverSocket.isClosed()) {
                // Creates a new thread to handle request
                Thread newClient = new Thread(new ClientHandler(serverSocket.accept()));
                newClient.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close Socket
            serverSocket.close();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private ObjectOutputStream outputStream;
        private ObjectInputStream inputStream;
        private User user;
        
        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            // Process Request
            try {
                socket.setTcpNoDelay(true);
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                inputStream = new ObjectInputStream(socket.getInputStream());

                // Handle Incoming Messages
                while (socket.isConnected()) {
                    Object input = inputStream.readObject();

                    if (input instanceof Message) {
                        Message message = (Message) input;
                        sessionMessages.add(message);
                        message.setMessageId(sessionMessages.size());
                        //outputStream.writeObject(sessionMessages.size());
                        /*
                         * switch (message.getMessageType()) { case MESSAGE:
                         * outputStream.writeObject(message); outputStream.flush(); break;
                         * 
                         * case MESSAGEID: message.setMessageId(sessionMessages.size() + 1);
                         * outputStream.writeObject(message); outputStream.flush(); break; }
                         */
                    }else if(input instanceof Integer){
                        Integer id = (Integer)input;
                        System.out.println(id);
                        outputStream.writeObject(returnMessages(id));
                        //outputStream.flush();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // Close Streams
                // Closing Output Stream
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Closing Input Stream
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Closing Socket
                try {
                    System.out.println("Client Has Left: " + socket);
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private ArrayList<Message> returnMessages(int id) {
            ArrayList<Message> requestedMessages = new ArrayList<Message>();
            
            for(int i=id; i<sessionMessages.size(); i++){
                requestedMessages.add(sessionMessages.get(i));
            }
            return requestedMessages;
    
        }
    }
}