public class User{
    int uid;
    String state;
    int [] RoomAuth;
    String userName;
    String img;

    public User(int uid,String state, int [] RoomAuth, String userName, String img){
        this.uid = uid;
        this.state = state;
        this.RoomAuth = RoomAuth;
        this.userName = userName;
        this.img=img;
    }

    public void setUserName(String Changing){
        this.userName = Changing;
    }

}
