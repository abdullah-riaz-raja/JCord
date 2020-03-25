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
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import Server.Utils;

public class Launcher extends Application {
    DataInputStream fromServer = null;
    VBox messageViewHolder = new VBox();
    int newestMessageId = 0;
    User user;
    CommunicationClient handler;


    Scene genMainScene(Stage primaryStage) throws IOException {
        Login login = new Login();
        
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


        this.handler = new CommunicationClient(info.get("server-remote-ip"),
                Integer.parseInt(info.get("server-message-receive-port")));


        VBox sendMsgPane = new VBox();
        sendMsgPane.getStyleClass().add("body-pane");
        ScrollPane scroll = new ScrollPane();
        scroll.setPrefViewportHeight(1000);



        scroll.setContent(messageViewHolder);
        scroll.getStyleClass().add("messageWindow");
        scroll.vvalueProperty().bind(messageViewHolder.heightProperty());

        Node topBar = Header.header(user,primaryStage);

        sendMsgPane.getChildren().addAll(topBar, scroll, MessageCreator.GenerateMessageBox(primaryStage, user, handler));
        HBox pane = new HBox();
        
        pane.getChildren().add(sendMsgPane);
        HBox.setHgrow(scroll,Priority.ALWAYS);

        PeopleOnlineViewer onlinePeople = new PeopleOnlineViewer(user);
        pane.getChildren().add(onlinePeople.generatePeopleOnline());

        
        Scene scene = new Scene(pane);
        /*
        //People Online Viewer
        HBox message1 = new HBox();
        PeopleOnlineViewer test1 = new PeopleOnlineViewer(user);
        message1.getChildren().add(test1.generatePeopleOnline());
        //
        scroll.setMinWidth(800);
        scroll.setMaxWidth(800);

        HBox message2 = new HBox();

        message2.getChildren().add(MessageCreator.GenerateMessageBox(primaryStage,user,handler));

        pane.getChildren().addAll(scroll, message2);

        VBox pane2 = new VBox();
        pane2.getChildren().addAll(message1);

        HBox pane3 = new HBox();

        scene = new Scene(pane3);
        pane3.getChildren().addAll(pane, pane2);

        
        */
        scene.getStylesheets().add("customCss.css");

        Runnable addNewMessage = new ListenForNewMessage(this);
        Thread checker = new Thread(addNewMessage);
        checker.start();

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

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = genMainScene(primaryStage);


     

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public VBox addMessageToView(Message messsage, VBox view) {
        view.getChildren().add(messsage.generateMessageViewNode());
        return view;
    }

    public class UserComparator implements Comparator<User> {
        @Override
        public int compare(User user1, User user2) {
            return user1.getUsername().compareTo(user2.getUsername());
        }
    }

    private class ListenForNewMessage implements Runnable {
        Launcher currentClient;
        ArrayList<User> userOnlineArrayList;
        int currentId = 0;
        public ListenForNewMessage(Launcher instance) {
            this.currentClient = instance;
        }

        @Override
        public void run() {
            while (true) {
                // TODO Auto-generated method stub
                //Platform.runLater(() -> {
                    try {
                        ArrayList<Message> newMsg = this.currentClient.handler.getNewMessage(currentClient.newestMessageId);

                        currentId = currentClient.newestMessageId;

                        for (Message i : newMsg) {
                            Platform.runLater(() -> {
                                this.currentClient.messageViewHolder.getChildren().add(i.generateMessageViewNode());
                                System.out.println(i.getMessage());
                            });
                            currentId = i.getMessageId();
                        }

                        currentClient.newestMessageId = currentId;

                        currentClient.user.setLastActivity(new Date(System.currentTimeMillis()));

                        HashSet<User> newUser = this.currentClient.handler.getNewUser(currentClient.user);

                        userOnlineArrayList= new ArrayList<User>(newUser);
                        
                        Collections.sort(userOnlineArrayList, new UserComparator());

                        //System.out.println(userOnlineArrayList);
                        
                        for (User i : userOnlineArrayList) {
                            // TODO : add users
                            System.out.println(i.getUsername());
                         }
                    }catch (ClassNotFoundException | IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                //});

                
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}
 