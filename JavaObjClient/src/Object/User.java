package Object;

import java.util.ArrayList;

public class User{
    int uid; // ���� ID
    int code; // protocol code
    String state; // Online or Sleep ��� 
    ArrayList<Integer> RoomAuth = new ArrayList<>(); // ������ ���� ������ �� ���
    String userName; // ���� �̸�
    String img; // ������ ������ ����

    public User(int uid, int code, String state, ArrayList<Integer> roomAuth, String userName, String img) {
        this.uid = uid;
        this.code = code;
        this.state = state;
        RoomAuth = roomAuth;
        this.userName = userName;
        this.img = img;
    }


    public static final class UserBuilder {
        private int uid;
        private int code;
        private String state;
        private ArrayList<Integer> RoomAuth;
        private String userName;
        private String img;

        //Use Builder pattern
        private UserBuilder() {
        }

        public static UserBuilder anUser() {
            return new UserBuilder();
        }

        public UserBuilder setUid(int uid) {
            this.uid = uid;
            return this;
        }

        public UserBuilder setCode(int code) {
            this.code = code;
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
            User user = new User(uid, code, state, null, userName, img);
            user.RoomAuth = this.RoomAuth;
            return user;
        }
    }
}