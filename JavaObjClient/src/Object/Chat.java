package Object;

import java.util.Date;

public class Chat {
    String msg; // message
    Date date; // message 생성 시간
    int code; // portocol code

    public Chat(String msg, Date date, int code) {
        this.msg = msg;
        this.date = date;
        this.code = code;
    }


    public static final class ChatBuilder {
        private String msg;
        private Date date;
        private int code;

        private ChatBuilder() {
        }

        public static ChatBuilder aChat() {
            return new ChatBuilder();
        }

        public ChatBuilder setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public ChatBuilder setDate(Date date) {
            this.date = date;
            return this;
        }

        public ChatBuilder setCode(int code) {
            this.code = code;
            return this;
        }

        public Chat build() {
            return new Chat(msg, date, code);
        }
    }
}