import javax.swing.*;
import java.util.ArrayList;

public class User {
    int uid;
    String state;
    ArrayList<Integer> RoomAuth;
    String userName;
    ImageIcon Profileimg;

    public User() {
    }

    public User(int uid, String state, ArrayList<Integer> RoomAuth, String userName) {
        this.uid = uid;
        this.state = state;
        this.RoomAuth = RoomAuth;
        this.userName = userName;
    }

    public void setUserName(String Changing) {
        this.userName = Changing;
    }

    public void setState(String state) {
        this.state = state;
    }
    public void setImg(ImageIcon img){
        this.Profileimg=img;
    }


}
