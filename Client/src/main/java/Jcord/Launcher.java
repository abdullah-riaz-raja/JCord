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
        
        for(int i =0; i <20; i++){
            Date time = new Date(System.currentTimeMillis());
            MessageViewers test = new MessageViewers(userTest, time,message);
            messageViewHolder.getChildren().add(test.generateMessageViewNode());
        }
        
        ptest.setContent(messageViewHolder);
        ptest.getStyleClass().add("messageWindow");

        pane.getChildren().addAll(ptest,MessageCreator.GenerateMessageBox(primaryStage,userTest));
        Scene scene = new Scene(pane);
        
        scene.getStylesheets().add("customCss.css");
      
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
}
 