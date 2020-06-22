package common.json.message;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class MessageBuilder {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
    private String user;
    private LocalDateTime date;
    private String message;

    MessageBuilder() { }

    public MessageBuilder setUser(String user) {
        this.user = user;
        return this;
    }

    public MessageBuilder setMessage(String message) {
        this.message = message;
        return this;
    }

    public MessageBuilder setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }
   public MessageBuilder setDate(String d){
        this.date = LocalDateTime.parse(d, DATE_FORMAT);
        return this;
   }

    public MessagePacket build() {
        //error check, nothing can be null or empty
        if (user == null || user.isEmpty()  ) {
            throw new IllegalArgumentException("User can't be null or empty.");
        }
        if (message == null || message.isEmpty()  ) {
            throw new IllegalArgumentException("Message can't be null or empty.");
        }
        if(date == null || date.toString().equals("null")){
            System.out.println(date);
            throw new IllegalArgumentException("Date can't be null or empty.");
        }
        MessagePacket  messagePacket = new MessagePacket(user, date, message);
        return messagePacket;
    }
}
