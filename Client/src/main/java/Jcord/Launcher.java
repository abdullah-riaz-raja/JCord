package Jcord;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Platform;

import Server.Utils;

/*
public class Launcher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public static void main(String[] args) throws FileNotFoundException {
        HashMap <String,String> ipInfo = Utils.getServerInfo("communication.json");
        
        String ip = ipInfo.get("server-remote-ip");
        int port = Integer.parseInt(ipInfo.get("server-message-receive-port"));

        CommunicationClient server = new CommunicationClient(ip, port);
        
        
        server.establishConnection();
        
        Date time = new Date(System.currentTimeMillis());

        User userTest = new User("Coleman2247", new Image("TestPreDataBase/index.jpeg"));
        String message = new String("Hello");

        MessageViewers test = new MessageViewers(userTest, time, message);
        
        try {
            server.sendMessage(test);
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }
}


*/


public class Launcher extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox pane = new VBox();
        pane.getStyleClass().add("body-pane");

        User userTest = new User("Cole",new Image("TestPreDataBase/index.jpeg"));

        String message = new String("Hello");


        //MessageViewers msg = new MessageViewers(userTest, time,message);
        //MessageViewers msg2 = new MessageViewers(userTest, time,"Hello THere");

        //pane.getChildren().addAll(msg.generateMessageViewNode(),msg2.generateMessageViewNode(),MessageCreator.GenerateMessageBox());
        
        ScrollPane ptest = new ScrollPane();
        VBox messageViewHolder = new VBox();

        // Establishing Server Info Path
        HashMap<String, String> info = null;
        try {
            info = Utils.getServerInfo("communication.json");
        } catch (FileNotFoundException e) {
            System.out.println("Could not Find communcation json when sending msg");
            e.printStackTrace();
        }

        // Connecting To Server
        CommunicationClient handler = new CommunicationClient(info.get("server-remote-ip"),
                Integer.parseInt(info.get("server-message-receive-port")),
                textMessage ->
                {
                    Platform.runLater(()->{
                        Date time = new Date(System.currentTimeMillis());
                        Message test = (Message) textMessage;
                        messageViewHolder.getChildren().add(test.generateMessageViewNode());
                    });
                },
                voiceMessage ->
                {

                });

        new Thread(handler).start();


        /* Message Filler
        for(int i =0; i <1; i++){
            Date time = new Date(System.currentTimeMillis());
            Message test = new Message(userTest, time,message);
            messageViewHolder.getChildren().add(test.generateMessageViewNode());
        }
        */

        
        ptest.setContent(messageViewHolder);
        ptest.getStyleClass().add("messageWindow");

        pane.getChildren().addAll(ptest,MessageCreator.GenerateMessageBox(primaryStage,userTest,handler));
        Scene scene = new Scene(pane);
        
        scene.getStylesheets().add("customCss.css");
      
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
}
 