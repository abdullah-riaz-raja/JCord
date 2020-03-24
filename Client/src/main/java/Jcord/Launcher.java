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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.application.Platform;
import javafx.event.EventHandler;
import Server.Utils;



public class Launcher extends Application {
    DataInputStream fromServer = null;
    VBox messageViewHolder = new VBox();
    int newestMessageId = 0;
    CommunicationClient handler;

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox pane = new VBox();
        pane.getStyleClass().add("body-pane");

        User userTest = new User("Cole", new Image("TestPreDataBase/index.jpeg"));

        String message = new String("Hello");

        ScrollPane ptest = new ScrollPane();

        Date time = new Date(System.currentTimeMillis());

        // messageViewHolder = addMessageToView(new Message(userTest, time, "hello"),
        // messageViewHolder);

        // Establishing Server Info Path
        HashMap<String, String> info = null;
        try {
            info = Utils.getServerInfo("communication.json");
        } catch (FileNotFoundException e) {
            System.out.println("Could not Find communcation json when sending msg");
            e.printStackTrace();
        }
        /*
         * // Connecting To Server CommunicationClient handler = new
         * CommunicationClient(info.get("server-remote-ip"),
         * Integer.parseInt(info.get("server-message-receive-port")), textMessage -> {
         * Platform.runLater(()->{ Date time = new Date(System.currentTimeMillis());
         * Message test = (Message) textMessage;
         * messageViewHolder.getChildren().add(test.generateMessageViewNode()); }); },
         * voiceMessage -> {
         * 
         * });
         * 
         * new Thread(handler).start();
         * 
         */
        /*
         * Message Filler for(int i =0; i <1; i++){ Date time = new
         * Date(System.currentTimeMillis()); Message test = new Message(userTest,
         * time,message);
         * messageViewHolder.getChildren().add(test.generateMessageViewNode()); }
         */

        this.handler = new CommunicationClient(info.get("server-remote-ip"),
                Integer.parseInt(info.get("server-message-receive-port")));

        ptest.setContent(messageViewHolder);
        ptest.getStyleClass().add("messageWindow");

        pane.getChildren().addAll(ptest, MessageCreator.GenerateMessageBox(primaryStage, userTest, handler));
        Scene scene = new Scene(pane);

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
 