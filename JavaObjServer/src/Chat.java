import javax.swing.*;
import java.util.Date;

public class Chat {
    int uid;
    String msg;
    String date;

    public ImageIcon img;
    public Chat(int uid,String msg,String date){
        this.uid=uid;
        this.msg = msg;
        this.date = date;
    }
    public void setImg(ImageIcon img) {
        this.img = img;
    }
}
