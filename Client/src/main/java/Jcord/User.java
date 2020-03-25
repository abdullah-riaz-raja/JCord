package Jcord;

import java.io.Serializable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import java.util.Date;

public class User implements Serializable {
    private static final long serialVersionUID = 3610722771006373724L;

    private String userName;
    
    // have to use byte[] because Image is not serializable
    private byte[]  profilePicture;

    public Date lastActivity;
    private boolean isOnline;
    public int width;
    public int height;
    
    public User(String name, Image profilePic){
        this.userName = name;
        //this.profilePicture = profilePic;
        this.width = (int) profilePic.getWidth();
        this.height = (int) profilePic.getHeight();
        this.profilePicture =  new byte[this.width * this.height * 4];

        this.lastActivity = new Date(System.currentTimeMillis());
       
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

    public void setLastActivity(Date lastActivity){this.lastActivity = lastActivity;}
    public Date getLastActivity(){return lastActivity;}
    
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