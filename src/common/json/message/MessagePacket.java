package common.json.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Objects;

public class MessagePacket {
    private String username;
    private LocalDateTime date;
    private String message;

    @JsonCreator
    public MessagePacket(@JsonProperty("user") String user,
                         @JsonProperty("date") LocalDateTime date,
                         @JsonProperty("message") String message){
        this.username = user;
        this.date = date;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessagePacket)) return false;
        MessagePacket that = (MessagePacket) o;
        return username.equals(that.username) &&
                date.equals(that.date) &&
                message.equals(that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, date, message);
    }

    @Override
    public String toString() {
        return "MessagePacket{" +
                "username='" + username + '\'' +
                ", date=" + date +
                ", message='" + message + '\'' +
                '}';
    }
}
