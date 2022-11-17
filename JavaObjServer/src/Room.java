import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Room {
    int rid;
    ArrayList<Integer> authUser = new ArrayList<>();
    String  roomName;

    Map<Integer, String> chat = new HashMap<>();

    public Room(int rid, ArrayList<Integer> authUser, String roomName){
        this.rid = rid;
        this.authUser = authUser;
        this.roomName = roomName;

    }

    public void createChat(int uid, String chatting){
        this.chat.put(uid,chatting);
    }

}
