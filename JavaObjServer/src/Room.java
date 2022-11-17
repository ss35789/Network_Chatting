import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Room {
    int rid;
    ArrayList<Integer> userAuth = new ArrayList<>();
    String  roomName;

    Map<Integer, Chat> chat = new HashMap<>();

    public Room(int rid, ArrayList<Integer> userAuth, String roomName){
        this.rid = rid;
        this.userAuth = userAuth;
        this.roomName = roomName;

    }

    public void createChat(int uid, Chat chatting){
        this.chat.put(uid,chatting);
    }

}
