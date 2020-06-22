package common.json.message;

import com.fasterxml.jackson.core.JsonProcessingException;

import static java.lang.Integer.MAX_VALUE;

public class AckBuilder {
    private String user;
    private String error;
    private int  ack = Integer.MAX_VALUE;

    protected AckBuilder(){}

    public AckBuilder setUser(String user){
        this.user = user;
        return this;
    }

    public AckBuilder setError(String error){
        this.error = error;
        return this;
    }

    public AckBuilder setAck( int ack){
        this.ack = ack;
        return this;
    }

    public AckPacket build() {

        if (user == null || user.isEmpty()  ) {
            throw new IllegalArgumentException();
        }
        if (error != null &&  error.isEmpty() ) {
            throw new IllegalArgumentException();
        }
        if(ack == Integer.MAX_VALUE){
            throw new IllegalArgumentException();
        }

        AckPacket ackPacket = new AckPacket(user, error, ack);
        return ackPacket;

    }

}
