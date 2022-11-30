package Object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListData extends Object {

    Map<Integer, User> userList;
    Map<Integer, Room> roomList;
    Room room;

    public ListData() {
    }


    //(int uid,String state, ArrayList<Integer> RoomAuth, String userName, String img)
    public String getUserListToString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            str.append(i + ":");
            str.append(user.uid + ",");
            str.append(user.state + ",");
            str.append("[");
            for (int j = 0; j < user.RoomAuth.size(); i++) {
                str.append(user.RoomAuth.get(j));
                if (j != user.RoomAuth.size() - 1) str.append(".");

            }
            str.append("],");
            str.append(user.userName + ",");
            str.append(user.img);

            str.append(" ");
        }

        return str.toString();
    }
    // 0:0,Online,[1.2.4],user1,file 1:1,Online,[1.2.4],user2,file ...


//    int rid;
//    ArrayList<Integer> userAuth = new ArrayList<>();
//    String  roomName;
//
//    Map<Integer, Chat> chat = new HashMap<>();

    public String getRoomListToString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < roomList.size(); i++) {
            Room room = roomList.get(i);

            str.append(i + ":");
            str.append(room.rid + ",");

            str.append("[");
            for (int j = 0; j < room.userAuth.size(); j++) {
                str.append(room.userAuth.get(j) + ".");
            }
            str.append("],");

            str.append(room.roomName + ",");

            str.append(room.getChatToString());
//            str.append("<");
//            for(int c=0;c<room.chat.size();c++){
//                str.append(room.chat.get(c).uid+"-");
//                str.append(room.chat.get(c).msg+"-");
//                str.append(room.chat.get(c).date.toString());
//                if(c!=room.chat.size()-1)str.append("@");
//            }
//            str.append(">");

            str.append(" ");
        }

        return str.toString();
    }
    //0:0,[1,4,5],roomnameLLL,<0-????????????-date.toString()@1-???????-date.toString()@...> ...

    public String getRoomToString() {
        StringBuilder str = new StringBuilder();
        str.append(room.roomName);
        str.append(",");
        str.append("[");
        for (int i = 0; i < room.userAuth.size(); i++) {
            str.append(room.userAuth.get(i)+".");
        }
        str.deleteCharAt(str.length()-1);
        str.append("]");
        return str.toString();
    }


    public String AllListData() {
        String allList = "";
        allList = getUserListToString();
        allList += "|";
        allList += getRoomListToString();
        return allList;
    }

    //Use Builder pattern
    public static final class ListDataBuilder {
        private Map<Integer, User> userList;
        private Map<Integer, Room> roomList;
        private Room room;

        public ListDataBuilder() {
        }

        public static ListDataBuilder aListData() {
            return new ListDataBuilder();
        }

        public ListDataBuilder setUserList(Map<Integer, User> userList) {
            this.userList = userList;
            return this;
        }

        public ListDataBuilder setRoomList(Map<Integer, Room> roomList) {
            this.roomList = roomList;
            return this;
        }

        public ListDataBuilder setRoom(Room room) {
            this.room = room;
            return this;
        }

        public ListData build() {
            ListData listData = new ListData();
            listData.userList = this.userList;
            listData.room = this.room;
            listData.roomList = this.roomList;
            return listData;
        }
    }
}
