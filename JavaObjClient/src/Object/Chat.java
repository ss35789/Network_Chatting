package Object;

import com.sun.org.apache.xpath.internal.operations.Div;

import javax.swing.*;
import java.util.Date;

public class Chat {
    int uid;
    String msg;
    String date;
    ImageIcon img= new ImageIcon("");

    public Chat(int uid, String date) {
        this.uid = uid;
        this.date = date;
    }
    public static final class ChatBuilder {
        private int uid;
        private String msg;
        private String date;
        private ImageIcon img;

        public ChatBuilder() {
        }

        public static ChatBuilder aChat() {
            return new ChatBuilder();
        }

        public ChatBuilder setUid(int uid) {
            this.uid = uid;
            return this;
        }

        public ChatBuilder setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public ChatBuilder setDate(String date) {
            this.date = date;
            return this;
        }

        public ChatBuilder setImg(ImageIcon img) {
            this.img = img;
            return this;
        }

        public Chat build() {
            Chat chat = new Chat(uid, date);
            chat.img = this.img;
            chat.msg = this.msg;
            return chat;
        }
    }

    //Getters

    public int getUid() {
        return uid;
    }

    public String getMsg() {
        return msg;
    }

    public String getDate() {
        return date;
    }

    public ImageIcon getImg() {
        return img;
    }

    public String toString(Chat chat){
        StringBuilder str= new StringBuilder();

        str.append(chat.uid);
        str.append(DivString.ChatDiv);
        str.append(msg);
        str.append(DivString.ChatDiv);
        str.append(date);
        str.append(DivString.ChatDiv);
        str.append(img);
        str.append(DivString.ChatDiv);

        return str.toString();
    }

}