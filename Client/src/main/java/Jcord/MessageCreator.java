package Jcord;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import Server.Utils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MessageCreator {

    public static Node GenerateMessageBox(Stage stage, User user) {
        // pane is just for testing, actual class will just be functional
        HBox pane = new HBox();

        // Defaults to Message channel name
        TextArea messageBox = new TextArea();
        messageBox.setPrefHeight(30);
        messageBox.setWrapText(true);
        messageBox.setPrefWidth(1500);
        messageBox.setMaxWidth(1500);
        messageBox.setFont(new Font("Whitney", 12));
        messageBox.getStyleClass().add("message");

        messageBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    String text = messageBox.getText();

                    // send over sockets to main server

                    HashMap<String, String> info = null;
                    try {
                        info = Utils.getServerInfo("communication.json");
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        System.out.println("Could not Find communcation json when sending msg");
                        e.printStackTrace();
                    }

                    CommunicationClient sendMsg = new CommunicationClient(info.get("client-remote-ip"),
                            Integer.parseInt(info.get("server-message-receive-port")));

                    if (sendMsg.establishConnection()) {
                        Date time = new Date(System.currentTimeMillis());

                        MessageViewers newMsg = new MessageViewers(user, time, messageBox.getText());

                        // public MessageViewers(User user,Date timeSent,String msg){

                        try {
                            sendMsg.sendMessage(newMsg);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    
                    
                    }

                    // clear text
                    messageBox.setText("");
                }
            }
        });


        
        ImageView addIcon= new ImageView (new Image("Images/addButton.png"));
        addIcon.setFitHeight(30);
        addIcon.setFitWidth(30);
        addIcon.setPreserveRatio(true);

        Button attachment = new Button();
        attachment.setGraphic(addIcon);
        attachment.setPadding(new Insets(2,2,2,2));
        attachment.getStyleClass().add("roundAttachmenButton");
        attachment.setAlignment(Pos.BOTTOM_CENTER);

        attachment.setOnAction(e -> {
                
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage);
            
            // send file over sockets
        
        });



        pane.getChildren().addAll(attachment,messageBox);        
        pane.getStyleClass().add("messageCreate");
        
        return pane;
    }


}