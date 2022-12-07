package Object;

import javax.swing.*;
import java.util.ArrayList;

public class User{
    int uid; // 유저 ID
    String state; // Online or Sleep 모드 
    ArrayList<Integer> RoomAuth; // 유저가 접근 가능한 방 목록
    String userName; // 유저 이름
    ImageIcon img; // 유저의 프로필 사진

    public User(int uid, String state, ArrayList<Integer> roomAuth, String userName, ImageIcon img) {
        this.uid = uid;
        this.state = state;
        RoomAuth = roomAuth;
        this.userName = userName;
        this.img = img;
    }

    // Use Builder pattern
    public static final class UserBuilder {
        private int uid;
        private String state;
        private ArrayList<Integer> RoomAuth;
        private String userName;
        private ImageIcon img;

        public UserBuilder() {}

        public static UserBuilder anUser() {
            return new UserBuilder();
        }

        public UserBuilder setUid(int uid) {
            this.uid = uid;
            return this;
        }

        public UserBuilder setState(String state) {
            this.state = state;
            return this;
        }

        public UserBuilder setRoomAuth(ArrayList<Integer> RoomAuth) {
            this.RoomAuth = RoomAuth;
            return this;
        }

        public UserBuilder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public UserBuilder setImg(ImageIcon img) {
            this.img = img;
            return this;
        }

        public User build() {
            User user = new User(uid, state, null, userName, img);
            user.RoomAuth = this.RoomAuth;
            return user;
        }
    }

    // Getters 선언
    public int getUid() {
        return uid;
    }

    public String getState() {
        return state;
    }

    public ArrayList<Integer> getRoomAuth() {
        return RoomAuth;
    }

    public String getUserName() {
        return userName;
    }

    public ImageIcon getImg() {
        return img;
    }

    public void setState(String state) {
        this.state = state;
    }
}