import com.sun.org.apache.xpath.internal.operations.Div;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Room {
    int rid;
    ArrayList<Integer> userAuth;
    String  roomName;

    ArrayList<Chat> chat = new ArrayList<>();
    String ChatDiv = DivString.ChatDiv;
    String ChatListDiv = DivString.ChatListDiv;
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
            str.append(c.uid+ChatDiv+c.msg+ChatDiv+c.date);
            if(i!=chat.size()-1)str.append(ChatListDiv);
        }
        str.append(">");
        return str.toString();
    }


    public void createChat(Chat chatting){
        this.chat.add(chatting);
    }

}
