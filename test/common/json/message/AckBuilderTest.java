package common.json.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AckBuilderTest {
    private ObjectMapper objectMapper;
    private AckBuilder builder;
    private String user;
    private String error;
    private int ack;

    @BeforeEach
    final void setup() {
         objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        builder = PacketBuilderFactory.createFactory("AckBuilder");
        user = "min";
        error = "wrong";
        ack = 1000;
    }

    @Test
    final void testBuild() {
        //in this test simply test the basics of the builder assuming everything works correctly.
        //create an AckPacket using the builder.
        //call every setter of the builder, use the fields user, error, and ack.
        //assert every getter of the created AckPacket against user, error, and ack.
        AckPacket packet  = builder.setAck(ack).setError(error).setUser(user).build();
        assertEquals(user, packet.getUsername());
        assertEquals(error,packet.getError());
        assertEquals(ack, packet.getAck());
    }

    @Test
    final void testBuildNoAck(){
        //in this test do not call the setter for ack when using the builder.
        //setAck must be call so if it is not the build() method will throw an IllegalArgumentException.
        //call every setter of the builder except setAck(). use the fields user and error.
        //use assertThrows for builder.build().
        builder
                .setUser(user)
                .setError(error);
        assertThrows(IllegalArgumentException.class, ()->builder.build());
    }



    @Test
    final void testBuildEmptyUser()  {
        //in this test pass an empty string to setUser.
        //if the user is empty the build() method will throw an IllegalArgumentException.
        //call every setter of the builder. use the fields ack, error and empty string for user.
        //use assertThrows for builder.build().
        builder
                .setUser("")
                .setError(error)
                .setAck(ack);
        assertThrows(IllegalArgumentException.class,()->builder.build());
    }

    @Test
    final void testBuildNullUser()  {
        //in this test pass a null to setUser.
        //if the user is null the build() method will throw an IllegalArgumentException.
        //call every setter of the builder. use the fields ack, error and null for user.
        //use assertThrows for builder.build().
        builder.setUser(null)
                .setError(error)
                .setAck(ack);
        assertThrows(IllegalArgumentException.class,()->builder.build());

    }

    @Test
    final void testBuildNullError()  {
        //in this test simply test the basics of the builder assuming everything works correctly except error is null this time.
        //create an AckPacket using the builder.
        //call every setter of the builder, use the fields user, ack, and null for error.
        //assert every getter of the created AckPacket against user, error, and ack. getError should return null.
        AckPacket packet  = builder.setAck(ack).setError(null).setUser(user).build();
        assertEquals(user, packet.getUsername());
        assertNull(packet.getError());
        assertEquals(ack, packet.getAck());
    }

    @Test
    final void testBuildEmptyError(){
        //in this test pass an empty string to setError.
        //if the error is empty the build() method will throw an IllegalArgumentException.
        //call every setter of the builder. use the fields ack, user and empty string for error.
        //use assertThrows for builder.build().
        builder.setUser(user)
                .setError("")
                .setAck(ack);
        assertThrows(IllegalArgumentException.class,()->builder.build());
    }

    @Test
    final void testObjectToJson()throws JsonProcessingException {
        //in this test simply test the basics of converting an object to json using objectMapper.
        //create an AckPacket using the builder called p1.
        //call every setter of the builder, use the fields user, error, and ack.
        //convert p1 to json using objectMapper::writeValueAsString.
        //convert json string to AckPacket named p2 using objectMapper::readValue.
        //use assert equals and compare p1 and p2.
        AckPacket p1 = builder.setError(error).setUser(user).setAck(ack).build();
        String s = objectMapper.writeValueAsString(p1);
        AckPacket p2 = objectMapper.readValue(s, AckPacket.class);
        assertEquals(p1, p2);
    }

    @Test
    final void testObjectToJsonNull() throws JsonProcessingException {
        //in this test simply test the basics of converting an object to json using objectMapper. in this case error is null.
        //create an AckPacket using the builder called p1.
        //call every setter of the builder, use the fields user, ack, and null for error.
        //convert p1 to json using objectMapper::writeValueAsString.
        //convert json string to AckPacket named p2 using objectMapper::readValue.
        //use assert equals and compare p1 and p2.
        AckPacket p1 = builder.setError(null).setUser(user).setAck(ack).build();
        String d = objectMapper.writeValueAsString(p1);
        AckPacket p2 = objectMapper.readValue(d, AckPacket.class);
        assertEquals(p1,p2);

    }

    @Test
    final void testJsonToObject() throws JsonProcessingException {
        //in this test convert a json to an AckPacket. there are 2 ways of doing this:
        //either make a json string and pass it to objectMapper::writeValueAsString or
        //(recommended) create an Object of type ObjectNode called node. behaves similarly to a map.
        //use the method node::put to populate the node, like: node.put( "user", p1.getUsername());
        //put all the variables user, error and ack in the node.
        //before making the node make an AckPacket using the builder called p1.
        //call every setter of the builder, use the fields user, ack, and error.
        //next use objectMapper::treeToValue to convert the node to a AckPacket named p2.
        //use assert equals and compare p1 and p2.
        AckPacket p1  = builder.setAck(ack).setError(error).setUser(user).build();
        ObjectNode node = objectMapper.createObjectNode();
        node.put( "user", p1.getUsername());
        node.put( "error", p1.getError());
        node.put( "ack", p1.getAck());
        AckPacket p2 = objectMapper.treeToValue( node, AckPacket.class);
        assertEquals(p1,p2);
    }
}
