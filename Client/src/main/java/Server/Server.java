package Server;

import java.io.Serializable;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

import Jcord.User;
import Jcord.Message;

/**
 * This class is the server for JCord
 *
 * @see ArrayList
 * @see HashSet
 * @see ReentrantLock
 */
public class Server {
    private static ArrayList<Message> sessionMessages = new ArrayList<Message>();
    private static HashSet<User> onlineUsers = new HashSet<User>();
    private static ReentrantLock lock = new ReentrantLock();

    /**
     * Mainline logic that sets up the server
     *
     * @see ServerSocket
     * @see Socket
     * @see ExecutorService
     * @see Executors
     * @see Timer
     * @throws ClassNotFoundException
     * @throws IOException
     */
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Closing Threads and Sockets
            executor.shutdown();
            serverSocket.close();
            
        }
    }

    /**
     * Helper class that handles reading the client's messages
     * and writing response back to the client
     *
     * @see Thread
     * @see Socket
     * @see ObjectOutputStream
     * @see ObjectInputStream
     */
    private static class ClientHandler extends Thread {
        private Socket socket;
        private ObjectOutputStream outputStream;
        private ObjectInputStream inputStream;

        /**
         * This constructor takes in a Socket and initialize the object
         *
         * @param socket value of the socket
         * @see Socket
         */
        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        /**
         * This method overrides the {link Thread#run()} method
         * and handles the reading and writing of the client
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            // Process Request
            try {
                // Setting Up Streams
                socket.setTcpNoDelay(true);
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                inputStream = new ObjectInputStream(socket.getInputStream());

                // Handle Incoming Messages
                while (socket.isConnected()) {
                    
                    Object input = inputStream.readObject();

                    if (input instanceof Message) {
                        Message message = (Message) input;
                        message.setMessageId(sessionMessages.size()+1);

                        // Adding new message to message history
                        lock.lock();
                        sessionMessages.add(message);
                        lock.unlock();
                    }else if(input instanceof Integer){
                        Integer id = (Integer)input;

                        // Sending ArrayList of new messages
                        lock.lock();
                        outputStream.reset();
                        outputStream.writeObject(new ArrayList<Message>(sessionMessages.subList(id, sessionMessages.size())));
                        lock.unlock();
                    }else if(input instanceof User)
                    {
                        onlineUsers.add((User)input);

                        // Sending set of new users
                        lock.lock();
                        outputStream.reset();
                        outputStream.writeObject(onlineUsers);
                        lock.unlock();
                    }
                }
            } catch (EOFException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
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

                // Unlocking Lock
                if (lock.isHeldByCurrentThread()){
                    lock.unlock();
                }
            }
        }
    }

    /**
     * Helper class that updates the server's information
     *
     * @see TimerTask
     */
    private static class UpdateServerInfo extends TimerTask
    {
        /**
         * This method overrides the {link TimerTask#run()} method
         * and updates the server's variables based on the amount of time passed
         *
         * @see TimerTask#run()
         */
        @Override
        public void run()
        {
            // Update Online Users Information
            for (Iterator<User> iterator = onlineUsers.iterator(); iterator.hasNext();)
            {
                // Comparing Activity Timer To Current Time
                User user = iterator.next();
                if ( (System.currentTimeMillis() - user.getLastActivity().getTime())  > 30000)
                {
                    iterator.remove();
                }
            }
        }
    }
}