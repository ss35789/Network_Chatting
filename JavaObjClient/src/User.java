import java.util.ArrayList;

public class User{
    int uid; // 유저 ID
    String state; // Online or Sleep 모드 
    ArrayList<Integer> RoomAuth = new ArrayList<>(); // 유저가 접근 가능한 방 목록
    String userName; // 유저 이름
    String img; // 유저의 프로필 사진

    public User(int uid,String state, ArrayList<Integer> RoomAuth, String userName, String img){
        this.uid = uid;
        this.state = state;
        this.RoomAuth = RoomAuth;
        this.userName = userName;
        this.img=img;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setState(String state){
        this.state = state;
    }

}