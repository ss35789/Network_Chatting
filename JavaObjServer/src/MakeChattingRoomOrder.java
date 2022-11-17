import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MakeChattingRoomOrder implements Serializable {
    ArrayList<Integer> userAuth;
    String roomName;

    public MakeChattingRoomOrder(String roomName, ArrayList<Integer> userAuth){
        this.roomName = roomName;
        this.userAuth =userAuth;
    }


}
