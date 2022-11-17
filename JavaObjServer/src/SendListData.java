import java.util.ArrayList;
import java.util.Map;

public class SendListData {

    Map<Integer,User> userList;
    Map<Integer,Room> roomList;
    public SendListData(
            Map<Integer,User> userList, Map<Integer,Room> roomList){
        this.roomList = roomList;
        this.userList = userList;
    }
}
