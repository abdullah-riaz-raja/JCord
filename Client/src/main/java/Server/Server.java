package Server;

import java.io.Serializable;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.HashSet;
import java.util.TimerTask;
import java.util.Timer;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import Jcord.User;
import Jcord.Message;

public class Server {
    private static ArrayList<Message> sessionMessages = new ArrayList<Message>();
    private static HashSet<User> onlineUsers = new HashSet<>();
    private static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        ExecutorService executor = Executors.newCachedThreadPool();
        
        // Initialize Server Socket
        ServerSocket serverSocket = new ServerSocket(
                Integer.parseInt(Utils.getServerInfo("communication.json").get("server-message-receive-port")));

        try {
            // Routinely updates server info every 30 seconds
            Timer updateServerInformation = new Timer();
            updateServerInformation.schedule(new UpdateServerInfo(), 0, 10000);

            // Listens for a new request
            while (!serverSocket.isClosed()) {
                // Creates a new thread to handle request
                executor.submit(new ClientHandler(serverSocket.accept()));
                //Thread newClient = new Thread(new ClientHandler(serverSocket.accept()));
                //newClient.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
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
            System.out.println("New Thread");
            try {
                socket.setTcpNoDelay(true);
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                inputStream = new ObjectInputStream(socket.getInputStream());

                // Handle Incoming Messages
                while (socket.isConnected()) {
                    
                    Object input = inputStream.readObject();

                    if (input instanceof Message) {
                        Message message = (Message) input;
                        message.setMessageId(sessionMessages.size()+1);

                        lock.lock();
                        sessionMessages.add(message);
                        lock.unlock();
                    }else if(input instanceof Integer){
                        Integer id = (Integer)input;
                        //System.out.println(id);

                        lock.lock();
                        outputStream.writeObject(new ArrayList<Message>(sessionMessages.subList(id, sessionMessages.size())));
                        lock.unlock();
                        //outputStream.flush();
                    }else if(input instanceof User)
                    {
                        onlineUsers.add((User)input);

                        // Sending Set Of Online Users
                        lock.lock();
                        outputStream.writeObject(onlineUsers);
                        lock.unlock();
                    }
                }
            } catch (EOFException e) {

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
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

                if (lock.isHeldByCurrentThread()){
                    lock.unlock();
                }
            }
        }
    }

    private static class UpdateServerInfo extends TimerTask
    {
        public void run()
        {
            // Update Online Users Information
            for (Iterator<User> iterator = onlineUsers.iterator(); iterator.hasNext();)
            {
                User user = iterator.next();
                if (Math.abs(Math.abs(user.getLastActivity().getTime() - System.currentTimeMillis())) > 30000)
                {
                    iterator.remove();
                }
            }
        }
    }
}