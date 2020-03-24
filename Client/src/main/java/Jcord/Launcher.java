package Jcord;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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
    CommunicationClient handler;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Login login = new Login();
        User user;
        
        if(!login.isSignedIn()){
            Stage createAccStage = new Stage();

            Scene createAccountScene = login.createAccount(createAccStage);

            createAccStage.setScene(createAccountScene);
            createAccStage.showAndWait();
           
            user = login.getUser();

        }else{
            user = login.getUserFromFile();
            System.out.println(user.getUsername());
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
        ScrollPane ptest = new ScrollPane();
        
        ptest.setContent(messageViewHolder);
        ptest.getStyleClass().add("messageWindow");

        Node topBar = Header.header();

        sendMsgPane.getChildren().addAll(topBar, ptest, MessageCreator.GenerateMessageBox(primaryStage, user, handler));
        HBox pane = new HBox();
        
        pane.getChildren().add(sendMsgPane);
        HBox.setHgrow(ptest,Priority.ALWAYS);

        PeopleOnlineViewer onlinePeople = new PeopleOnlineViewer(user);
        pane.getChildren().add(onlinePeople.generatePeopleOnline());

        
        Scene scene = new Scene(pane);
        /*
        //People Online Viewer
        HBox message1 = new HBox();
        PeopleOnlineViewer test1 = new PeopleOnlineViewer(user);
        message1.getChildren().add(test1.generatePeopleOnline());
        //
        ptest.setMinWidth(800);
        ptest.setMaxWidth(800);

        HBox message2 = new HBox();

        message2.getChildren().add(MessageCreator.GenerateMessageBox(primaryStage,user,handler));

        pane.getChildren().addAll(ptest, message2);

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


        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public VBox addMessageToView(Message messsage, VBox view) {
        view.getChildren().add(messsage.generateMessageViewNode());
        return view;
    }

    private class ListenForNewMessage implements Runnable {
        Launcher currentClient;

        public ListenForNewMessage(Launcher instance) {
            this.currentClient = instance;
        }

        @Override
        public void run() {
            while (true) {
                // TODO Auto-generated method stub
                Platform.runLater(() -> {
                    try {
                        ArrayList<Message> newMsg = this.currentClient.handler.getNewMessage(currentClient.newestMessageId);
                           
                            int currentId = currentClient.newestMessageId;
                            
                            for (Message i : newMsg) {
                                this.currentClient.messageViewHolder.getChildren().add(i.generateMessageViewNode());
                                System.out.println(i.getMessage());
                                currentId = i.getMessageId();
                            
                        }
                            currentClient.newestMessageId = currentId;
                    }catch (ClassNotFoundException | IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                });
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}
 