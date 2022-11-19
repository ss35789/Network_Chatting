import java.util.Date;

public class Chat {
    int uid;
    String msg;

    Date date;

    public Chat(int uid,String msg){
        this.uid=uid;
        this.msg = msg;
        this.date = new Date();
    }
}
