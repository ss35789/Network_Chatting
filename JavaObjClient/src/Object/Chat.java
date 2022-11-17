package Object;

import java.util.Date;

public class Chat {
    String msg;
    Date date;

    public Chat(String msg, Date date) {
        this.msg = msg;
        this.date = date;
    }

    public static final class ChatBuilder {
        private String msg;
        private Date date;

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

        public Chat build() {
            return new Chat(msg, date);
        }
    }
}