import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Room {
    int rid;
    ArrayList<Integer> userAuth = new ArrayList<>();
    String  roomName;

    ArrayList<Chat> chat = new ArrayList<>();

    public Room(int rid, ArrayList<Integer> userAuth, String roomName){
        this.rid = rid;
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

}
