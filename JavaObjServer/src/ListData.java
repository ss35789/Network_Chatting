import java.util.Map;
import java.util.ArrayList;

public class ListData extends Object{

    Map<Integer, User> userList;
    Map<Integer,Room> roomList;
    String ListDiv = "|_#$%";
    String RoomListDiv = "$#@";
    String RoomDiv = "<_^$%#";


    public ListData(
            Map<Integer, User> userList, Map<Integer,Room> roomList){
        this.roomList = roomList;
        this.userList = userList;
    }
    //(int uid,String state, ArrayList<Integer> RoomAuth, String userName, String img)
    public String getUserListToString(){
        StringBuilder str= new StringBuilder();
        for(int i=0;i<userList.size();i++){
            User user = userList.get(i);
            str.append(i+":");
            str.append(user.uid+",");
            str.append(user.state+",");
            str.append("[");
            for(int j=0;j<user.RoomAuth.size();i++){
                str.append(user.RoomAuth.get(j));
                if(j!=user.RoomAuth.size()-1)str.append(".");

            }
            str.append("],");
            str.append(user.userName+",");
            str.append(user.Profileimg.toString());

            str.append(" ");
        }

        return str.toString();
    }
    // 0:0,Online,[1.2.4],user1,file 1:1,Online,[1.2.4],user2,file ...


//    int rid;
//    ArrayList<Integer> userAuth = new ArrayList<>();
//    String  roomName;
//
//    ArrayList<Chat> chat = new ArrayList<>();

    public String getRoomListToString(){
        StringBuilder str= new StringBuilder();
        for(int i=0;i<roomList.size();i++){
            Room room = roomList.get(i);

            str.append(i+":");
            str.append(room.rid+RoomDiv);

            str.append("[");
            for(int j=0;j<room.userAuth.size();j++){
                str.append(room.userAuth.get(j));
                if(j!=room.userAuth.size()-1){
                    str.append(".");
                }
            }
            str.append("]"+RoomDiv);

            str.append(room.roomName+RoomDiv);

            str.append(room.getChatToString());
//            str.append("<");
//            for(int c=0;c<room.chat.size();c++){
//                str.append(room.chat.get(c).uid+"-");
//                str.append(room.chat.get(c).msg+"-");
//                str.append(room.chat.get(c).img);
//                str.append(room.chat.get(c).date);
//                if(c!=room.chat.size()-1)str.append("@");
//            }
//            str.append(">");

            str.append(RoomListDiv);
        }

        return str.toString();
    }
    //0:0RoomDiv[1.4.5]RoomDivroomnameLLLRoomDiv<0ChatDivぞしぞしぞぞChatDivdate.toString()ChatListDiv1ChatDivででででででChatDivdate.toString()ChatListDiv...> ...

    public String AllListData(){

        String allList="";
        allList = getUserListToString();
        allList += ListDiv;
        allList += getRoomListToString();
        return allList;
    }

}
