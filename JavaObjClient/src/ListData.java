import java.util.Map;

public class ListData extends Object{

    Map<Integer,User> userList;
    Map<Integer,Room> roomList;
    public ListData(
            Map<Integer,User> userList, Map<Integer,Room> roomList){
        this.roomList = roomList;
        this.userList = userList;
    }
}
