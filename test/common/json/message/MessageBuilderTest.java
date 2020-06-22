package common.json.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MessageBuilderTest {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
    private ObjectMapper objectMapper;
    private MessageBuilder builder;
    private LocalDateTime now;
    private String user;
    private String message;

   @BeforeEach
    final void setup(){
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        builder = PacketBuilderFactory.createFactory(MessageBuilder.class);
        user = "min";
        message = "message";
        now= LocalDateTime.of(2018, 2, 15, 13, 50);
    }

     @Test
    final void testBuildWithStringDate(){
       String ld = "05/03/20 5:11 PM";
       MessagePacket messagePacket = builder.setDate(ld).setMessage(message).setUser(user).build();
       MessagePacket mp = new MessagePacket("min", LocalDateTime.parse(ld,DATE_FORMAT),"message" );
       assertEquals(mp, messagePacket);

     }

     @Test
    final void testBuild(){
         MessagePacket messagePacket = builder.setUser(user).setMessage(message).setDate(now).build();
         assertEquals(user, messagePacket.getUsername());
         assertEquals(message,messagePacket.getMessage());
         assertEquals(now,messagePacket.getDate());
     }

     @Test
     final void testBuildEmptyUser(){
      builder.setMessage(message)
              .setDate(now)
              .setUser("");
        assertThrows(IllegalArgumentException.class, ()->builder.build());
     }

    @Test
    final void testBuildNullUser(){
         builder.setMessage(message)
                .setDate(now)
                .setUser(null);
         assertThrows(IllegalArgumentException.class, ()->builder.build());
    }

    @Test
    final void testBuildEmptyMessage(){
           builder.setMessage("")
                  .setDate(now)
                  .setUser(null);
            assertThrows(IllegalArgumentException.class, ()->builder.build());
    }

    @Test
    final void testBuildNullMessage(){
        builder.setMessage(null)
                .setDate(now)
               .setUser(user);
        assertThrows(IllegalArgumentException.class, ()->builder.build());
    }

    @Test
    final void testBuildNullDate(){
      // LocalDateTime dt = null;
        //builder.setDate(dt)
        builder.setMessage(message)
               .setDate((LocalDateTime)null)
               .setUser(user);
        assertThrows(IllegalArgumentException.class, ()->builder.build());
    }

    @Test
    final void testBuildNullDateString(){
        assertThrows(
                NullPointerException.class, ()->builder.setMessage(message)
                        .setDate((String) null)
                        .setUser(user).build());
    }

    @Test
    final void testObjectToJson() throws JsonProcessingException {
        MessagePacket p1 = builder.setMessage(message).setUser(user).setDate(now).build();
       String s = objectMapper.writeValueAsString(p1);
        MessagePacket p2 = objectMapper.readValue(s, MessagePacket.class);
        assertEquals(p1, p2);

    }


    @Test
    final void testJsonToObject() throws JsonProcessingException {
        MessagePacket p1 = builder.setUser(user).setDate(now).setMessage(message).build();
        ObjectNode node = objectMapper.createObjectNode();
        node.put( "user", p1.getUsername());
        node.set( "date", objectMapper.valueToTree(p1.getDate()));
        node.put( "message", p1.getMessage());
        MessagePacket p2 = objectMapper.treeToValue( node, MessagePacket.class);
        assertEquals(p1,p2);
    }

}
