public class User {
    int uid;
    int [] RoomAuth;
    String userName;
    String img;

    public User(int uid, int [] RoomAuth, String userName, String img){
        this.uid = uid;
        this.RoomAuth = RoomAuth;
        this.userName = userName;
        this.img=img;
    }

    public void setUserName(String Changing){
        this.userName = Changing;
    }

}
