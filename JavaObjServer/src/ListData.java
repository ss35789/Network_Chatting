import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListData extends Object{

    Map<Integer,User> userList;
    Map<Integer,Room> roomList;
    public ListData(
            Map<Integer,User> userList, Map<Integer,Room> roomList){
        this.roomList = roomList;
        this.userList = userList;
    }
    //(int uid,String state, ArrayList<Integer> RoomAuth, String userName, String img)
    public String getUserListToString(){
        StringBuilder str= new StringBuilder();
        for(int i=0;i<userList.size();i++){
            str.append(i+":");
            str.append(userList.get(i).uid+",");
            str.append(userList.get(i).state+",");
            str.append("[");
            for(int j=0;j<userList.get(i).RoomAuth.size();i++){
                str.append(userList.get(i).RoomAuth.get(j)+".");
            }
            str.append("]");
            str.append(userList.get(i).userName+",");
            str.append(userList.get(i).img);

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
//    public String getUserListToString(){
//
//    }

}
