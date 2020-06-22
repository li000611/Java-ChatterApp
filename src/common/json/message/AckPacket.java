package common.json.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Objects;

public class AckPacket {
    private String username;
    private String error;
    private  int ack;

    @JsonCreator
    protected AckPacket(@JsonProperty("user") String user,
                        @JsonProperty("error") String error,
                        @JsonProperty("ack") int ack)  {

        this.username = user;
        this.error = error;
        this.ack = ack;
    }

    public String getUsername() {
        return username;
    }

    public String getError(){
        return error;
    }

    public int getAck() {
        return ack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AckPacket)) return false;
        AckPacket ackPacket = (AckPacket) o;
        return ack == ackPacket.ack &&
                Objects.equals(username, ackPacket.username) &&
                Objects.equals(error, ackPacket.error);
    }

    @Override

    public int hashCode() {
        return Objects.hash(username, error, ack);
    }

    @Override
    public String toString() {
        return "AckPacket{" +
                "username='" + username + '\'' +
                ", error='" + error + '\'' +
                ", ack=" + ack +
                '}';
    }
}
