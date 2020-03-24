package Jcord;

import java.io.Serializable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;

public class User implements Serializable {
    private String userName;
    
    // have to use byte[] because Image is not serializable
    private byte[]  profilePicture;
    
    private boolean isOnline;
    public int width;
    public int height;
    
    public User(String name, Image profilePic){
        this.userName = name;
        //this.profilePicture = profilePic;
        this.width = (int) profilePic.getWidth();
        this.height = (int) profilePic.getHeight();
        this.profilePicture =  new byte[this.width * this.height * 4];
       
        profilePic.getPixelReader().getPixels(0, 0, this.width, this.height, PixelFormat.getByteBgraInstance(),
        this.profilePicture, 0, this.width * 4);

    }

    public void setUsernmane(String userName){
        this.userName = userName;
    }

    public void setProfilePicture(Image picture){
        this.width = (int) picture.getWidth();
        this.height = (int) picture.getHeight();
        this.profilePicture =  new byte[this.width * this.height * 4];
        
        picture.getPixelReader().getPixels(0, 0, this.width, this.height, PixelFormat.getByteBgraInstance(),
        this.profilePicture, 0, this.width * 4);
    }

    public void setIsOnline(boolean active){
        this.isOnline = active;
    }
    
    public String getUsername(){
        return this.userName;
    }

    public Image getProfilePicture(){
        // going from byte[] to Image
        WritableImage image3 = new WritableImage(width, height);
        image3.getPixelWriter().setPixels(0, 0, width, height,
        PixelFormat.getByteBgraInstance(),
        this.profilePicture, 0, width * 4);
        

        // need to convert from byte[] to Image
        ImageView image = new ImageView(image3);

        return image.getImage();
    }

    public boolean getisOnline(){

        return this.isOnline;
    }



}