package Object;

import java.util.ArrayList;

public class Room {
    int rid;
    ArrayList<Integer> userAuth = new ArrayList<>();
    String  roomName;
    ArrayList<Chat> chat = new ArrayList<>();

    public Room(ArrayList<Integer> userAuth, String roomName) {
        this.userAuth = userAuth;
        this.roomName = roomName;
    }

    public String getChatToString(){
        StringBuilder str= new StringBuilder();
        str.append("<");
        for(int i=0;i<chat.size();i++){
            Chat c= chat.get(i);
            str.append(c.uid+"-"+c.msg+"-"+c.date.toString());
            if(i!=chat.size()-1)str.append("@");
        }
        str.append(">");
        return str.toString();
    }


    public void createChat(Chat chatting){
        this.chat.add(chatting);
    }


    public static final class RoomBuilder {
        private int rid;
        private ArrayList<Integer> userAuth;
        private String roomName;
        private ArrayList<Chat> chat;

        public RoomBuilder() {
        }

        public static RoomBuilder aRoom() {
            return new RoomBuilder();
        }

        public RoomBuilder setRid(int rid) {
            this.rid = rid;
            return this;
        }

        public RoomBuilder setUserAuth(ArrayList<Integer> userAuth) {
            this.userAuth = userAuth;
            return this;
        }

        public RoomBuilder setRoomName(String roomName) {
            this.roomName = roomName;
            return this;
        }

        public RoomBuilder setChat(ArrayList<Chat> chat) {
            this.chat = chat;
            return this;
        }

        public Room build() {
            Room room = new Room(userAuth, roomName);
            room.chat = this.chat;
            room.rid = this.rid;
            return room;
        }
    }

    //Getters

    public int getRid() {
        return rid;
    }

    public ArrayList<Integer> getUserAuth() {
        return userAuth;
    }

    public String getRoomName() {
        return roomName;
    }

    public ArrayList<Chat> getChat() {
        return chat;
    }
}
