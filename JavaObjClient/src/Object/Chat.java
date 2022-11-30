package Object;

import javax.swing.*;
import java.util.Date;

public class Chat {
    int uid;
    String msg;
    String date;
    ImageIcon img;

    public Chat(int uid, String msg, String date) {
        this.uid = uid;
        this.msg = msg;
        this.date = date;
    }

    //Use Builder pattern
    public static final class ChatBuilder {
        private int uid;
        private String msg;
        private String date;

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

        public Chat build() {
            return new Chat(uid, msg, date);
        }
    }
}