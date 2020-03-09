package Jcord;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class MessageCreator{

    public static Node GenerateMessageBox(){
        // pane is just for testing, actual class will just be functional
        HBox pane = new HBox();

        // Defaults to Message channel name
        TextField messageBox = new TextField();
        messageBox.setPrefHeight(30);
        messageBox.setMaxHeight(30);
        messageBox.setPrefWidth(1500);
        messageBox.setMaxWidth(1500);
        messageBox.setFont(new Font("Whitney", 12));
        messageBox.setId("messageCreate");
       
        messageBox.setOnAction(e -> {
            // call send message function
        });



        
        ImageView addIcon= new ImageView (new Image("Images/addButton.png"));
        addIcon.setFitHeight(30);
        addIcon.setFitWidth(30);
        addIcon.setPreserveRatio(true);

        Button attachment = new Button();
        attachment.setGraphic(addIcon);
        attachment.setId("addButton");
        attachment.setPadding(new Insets(2,2,2,2));
        attachment.setId("roundAttachmenButton");
        attachment.setAlignment(Pos.BOTTOM_CENTER);

        attachment.setOnAction(e -> {
            // call attachment function    
        });



        pane.getChildren().addAll(attachment,messageBox);        
        pane.setId("messageCreatePane");
        
        return pane;
    }


}