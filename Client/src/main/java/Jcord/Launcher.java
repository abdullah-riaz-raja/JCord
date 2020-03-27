package Jcord;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TimerTask;
import java.util.Timer;

import Server.Server;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.event.EventHandler;

import Server.Utils;

/**
 * This class extends {@link Application} and creates a chat application that interacts with the server
 * by sending and receiving messages
 *
 * @see Application
 * @see CommunicationClient
 */
public class Launcher extends Application {
    DataInputStream fromServer = null;
    VBox messageViewHolder = new VBox();
    PeopleOnlineViewer onlinePeople1 = new PeopleOnlineViewer();
    int newestMessageId = 0;
    User user;
    CommunicationClient handler;

    /**
     * This method initializes the main scene for the application
     *
     * @param primaryStage Stage of the application
     * @return Scene of the main stage
     * @throws IOException
     * @see Scene
     */
    Scene genMainScene(Stage primaryStage) throws IOException {
        Login login = new Login();

        // Checks if the user has previously logged in
        if(!login.isSignedIn()){
            Stage createAccStage = new Stage();
            Scene createAccountScene = login.createAccount(createAccStage);

            createAccStage.setScene(createAccountScene);
            createAccStage.showAndWait();

            user = login.getUser();
            user.setIsOnline(true);

        }else{
            user = login.getUserFromFile();
            System.out.println(user.getUsername());
        }

        if(user == null){
            System.exit(0);
        }

        // Establishing Server Info Path
        HashMap<String, String> info = null;
        try {
            info = Utils.getServerInfo("communication.json");
        } catch (FileNotFoundException e) {
            System.out.println("Could not Find communcation json when sending msg");
            e.printStackTrace();
        }

        // Initializing the client handler to connect to the server
        this.handler = new CommunicationClient(info.get("server-remote-ip"),
                Integer.parseInt(info.get("server-message-receive-port")));


        // Setting up chat box
        VBox sendMsgPane = new VBox();
        sendMsgPane.getStyleClass().add("body-pane");
        ScrollPane scroll = new ScrollPane();
        scroll.setPrefViewportHeight(1000);
        scroll.setContent(messageViewHolder);
        scroll.getStyleClass().add("messageWindow");
        scroll.vvalueProperty().bind(messageViewHolder.heightProperty());

        // Setting up User Header
        Node topBar = Header.header(user,primaryStage);

        // Display Server Channels
        ChannelsDisplay channels = new ChannelsDisplay();

        // Setting up sending messages box
        sendMsgPane.getChildren().addAll(topBar,channels.node1(),scroll,MessageCreator.GenerateMessageBox(primaryStage, user, handler));
        HBox pane = new HBox();
        pane.getChildren().add(sendMsgPane);
        HBox.setHgrow(scroll,Priority.ALWAYS);

        // Setting up other user's status view

        // Setting up the application scene
        Scene scene = new Scene(pane);
        scene.getStylesheets().add("customCss.css");

        // Setting up listener for server responses
        Runnable addNewMessage = new ListenForNewMessage(this);
        Thread checker = new Thread(addNewMessage);
        checker.start();

        // Setting up event handler for closing the application
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                handler.closeConnection();
                Platform.exit();
                System.exit(0);
            }
        });

        return scene;


    }

    /**
     * This method overrides {@link Application#start()} and is the mainline logic
     * of the application
     *
     * @param primaryStage Stage of the application
     * @throws Exception
     * @see Scene
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = genMainScene(primaryStage);

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * This method initializes the node of the messages for the message view
     *
     * @param messsage Message received from the server
     * @param view VBox of the message view
     * @return VBox with the new message
     * @see VBox
     */
    public VBox addMessageToView(Message messsage, VBox view) {
        view.getChildren().add(messsage.generateMessageViewNode());
        return view;
    }

    /**
     * This helper class implements {@link Comparator} and compares the users
     * alphabetically for the other user's status view
     *
     * @see PeopleOnlineViewer
     */
    public class UserComparator implements Comparator<User> {
        /**
         * This method overrides {@link Comparator#compare()} and compares the
         * username of two users
         *
         * @param user1 first User being compared
         * @param user2 second User being compared
         * @return int value of the comparison
         */
        @Override
        public int compare(User user1, User user2) {
            return user1.getUsername().compareTo(user2.getUsername());
        }
    }

    /**
     * This helper class implements {@link Runnable} and uses an instance of
     * {@link CommunicationClient} to help read and write to the server to update the client-end
     * of the application
     *
     * @see CommunicationClient
     * @see Runnable
     */
    private class ListenForNewMessage implements Runnable {
        Launcher currentClient;
        ArrayList<User> userOnlineArrayList;
        int currentId = 0;

        /**
         * This constructor takes in the instance of the application
         * @param instance Launcher of the application
         */
        public ListenForNewMessage(Launcher instance) {
            this.currentClient = instance;
        }

        /**
         * This method overrides {@link Runnable#run()} and continuously sending and receiving messages
         */
        @Override
        public void run() {
            while (true) {
                try {
                    // Sends current message ID and receive new messages
                    ArrayList<Message> newMsg = this.currentClient.handler.getNewMessage(currentClient.newestMessageId);
                    currentId = currentClient.newestMessageId;

                    // Add new messages to message view
                    for (Message i : newMsg) {
                        Platform.runLater(() -> {
                            this.currentClient.messageViewHolder.getChildren().add(i.generateMessageViewNode());
                            System.out.println(i.getMessage());
                        });
                        currentId = i.getMessageId();
                    }
                    currentClient.newestMessageId = currentId;

                    // Sends current time the user is using the application to server and receive update on status
                    currentClient.user.setLastActivity(new Date(System.currentTimeMillis()));
                    HashSet<User> newUser = this.currentClient.handler.getNewUser(currentClient.user);

                    // Sorts the users
                    Collections.sort(userOnlineArrayList, new UserComparator());
                    userOnlineArrayList= new ArrayList<User>(newUser);

                    // TODO : add users

                }catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }

                // Set listener to sleep
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
