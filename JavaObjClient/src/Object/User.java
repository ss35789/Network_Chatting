package Object;

import java.util.ArrayList;

public class User{
    int uid; // 유저 ID
    String state; // Online or Sleep 모드 
    ArrayList<Integer> RoomAuth; // 유저가 접근 가능한 방 목록
    String userName; // 유저 이름
    String img; // 유저의 프로필 사진

    public User(int uid, String state, ArrayList<Integer> roomAuth, String userName, String img) {
        this.uid = uid;
        this.state = state;
        RoomAuth = roomAuth;
        this.userName = userName;
        this.img = img;
    }

    public static final class UserBuilder {
        private int uid;
        private String state;
        private ArrayList<Integer> RoomAuth;
        private String userName;
        private String img;

        private UserBuilder() {
        }

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

        public UserBuilder setImg(String img) {
            this.img = img;
            return this;
        }

        public User build() {
            User user = new User(uid, state, null, userName, img);
            user.RoomAuth = this.RoomAuth;
            return user;
        }
    }
}