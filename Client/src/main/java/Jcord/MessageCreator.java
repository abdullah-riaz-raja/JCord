package Jcord;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.Charset;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;

import Server.Utils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

/**
 * This class handles the messaging aspect of the application
 *
 * @see Application
 */
public class MessageCreator {
    public static ArrayList<String> emojiList;
    public static ArrayList<ContextMenu> emojiMenu;
    public static TextArea messageBox;

    /**
     * This method generates a node for the application
     *
     * @param stage Stage of the application
     * @param user User of the client
     * @param handler CommunicationClient of the application
     * @return Node of the messaging graphical user interface
     */
    public static Node GenerateMessageBox(Stage stage, User user, CommunicationClient handler) {
        // pane is just for testing, actual class will just be functional
        HBox pane = new HBox();

        // Defaults to Message channel name
        messageBox = new TextArea();
        messageBox.setPrefHeight(30);
        messageBox.setWrapText(true);
        messageBox.setPrefWidth(1500);
        messageBox.setMaxWidth(1500);
        messageBox.setFont(new Font("Whitney", 12));
        messageBox.getStyleClass().add("message");

        // Setting up event handler for sending messages
        messageBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (event.getCode() == KeyCode.ENTER) {
                    // Setting up message to be sent
                    String text = messageBox.getText();

                    // Checks if the user is connected before sending a message
                    if (handler.establishConnection()) {
                        Date time = new Date(System.currentTimeMillis());
                        Message newMsg = new Message(user, time, messageBox.getText());

                        try {
                            handler.sendMessage(newMsg);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    // clear text
                    messageBox.setText("");
                    messageBox.requestFocus();
                }

            }
        });

        // Setting add file icon
        ImageView addIcon= new ImageView (new Image("Images/addButton.png"));
        addIcon.setFitHeight(30);
        addIcon.setFitWidth(30);
        addIcon.setPreserveRatio(true);

        // Setting up add file button
        Button attachment = new Button();
        attachment.setGraphic(addIcon);
        attachment.setPadding(new Insets(2,2,2,2));
        attachment.getStyleClass().add("roundAttachmenButton");
        attachment.setAlignment(Pos.BOTTOM_CENTER);

        // Event handler that attaches and sends a file to the other users
        attachment.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage);
            
            // TODO send file over sockets
        });

        // Setting Up Emojis
        byte[] emojiBytes;
        emojiList = new ArrayList<>();
        for (int i = 129; i < 189; i++)
        {
            emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) i};
            emojiList.add(new String(emojiBytes, Charset.forName("UTF-8")));
        }

        // Setting up emoji button
        Button emojiButton = new Button();
        emojiButton.setText(emojiList.get(0));
        emojiButton.setPadding(new Insets(4,4,4,4));
        emojiButton.setAlignment(Pos.BOTTOM_CENTER);
        emojiButton.setMinHeight(35);
        emojiButton.setMinWidth(35);
        emojiButton.setStyle("-fx-background-color: #ffffff; -fx-font-size: 26");

        // Change emoji button image on hover
        emojiButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                emojiButton.setText(emojiList.get((int) (Math.random() * 60)));
            }
        });

        // Setting Emoji Menu
        emojiMenu = new ArrayList<ContextMenu>();
        for (int i = 0; i<10;i++)
        {
            emojiMenu.add(new ContextMenu());
            for (int j = 0; j < 6; j++)
            {
                emojiMenu.get(i).getItems().add(addEmojiMenuItem(emojiList.get(i * 6 + j)));
            }
        }

        // Setting Emoji Menu Functions
        emojiButton.setOnAction(e -> {
            for (int i = 0; i < emojiMenu.size(); i++) {
                emojiMenu.get(i).show(emojiButton, emojiButton.getLayoutX() + (i *50), emojiButton.getLayoutY());
            }
        });

        // Add all components together
        pane.getChildren().addAll(attachment,messageBox, emojiButton);
        pane.getStyleClass().add("messageCreate");
        
        return pane;
    }

    /**
     * This method adds a string to the messaging box
     *
     * @param word String of the word
     */
    public static void appendMessageBox(String word)
    {
        messageBox.setText(messageBox.getText() + word);
    }

    /**
     * This method creates an emoji menu item for the emoji menu
     *
     * @param emoji String value of the emoji
     * @return MenuItem of the new emoji
     */
    public static MenuItem addEmojiMenuItem(String emoji)
    {
        MenuItem emojiItem = new MenuItem(emoji);
        emojiItem.setStyle(".menu-item:focused {-fx-background-color: #FFFFFF;}");
        emojiItem.setOnAction(e ->{
            appendMessageBox(emoji);

            // Close all opened emoji menus
            for (int i = 0; i < emojiMenu.size();i++)
            {
                emojiMenu.get(i).hide();
            }
        });
        return emojiItem;
    }
}