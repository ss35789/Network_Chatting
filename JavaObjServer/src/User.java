import java.util.ArrayList;

public class User{
    int uid;
    String state;
    ArrayList<Integer> RoomAuth = new ArrayList<>();
    String userName;
    String img;

    public User(int uid,String state, ArrayList<Integer> RoomAuth, String userName, String img){
        this.uid = uid;
        this.state = state;
        this.RoomAuth = RoomAuth;
        this.userName = userName;
        this.img=img;
    }

    public void setUserName(String Changing){
        this.userName = Changing;
    }

    public void setState(String state){
        this.state = state;
    }

}
