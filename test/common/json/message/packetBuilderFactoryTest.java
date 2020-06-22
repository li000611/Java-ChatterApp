package common.json.message;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class packetBuilderFactoryTest {

    @Test
    //What happens if we give a bad name that does not correspond to one of our builders
    final void testCreateFactoryStringBadName() {
        assertThrows(IllegalArgumentException.class,()->PacketBuilderFactory.createFactory("hello"));
    }

    @Test
    //What happens if the name is null
    final void testCreateFactoryStringNull(){
        assertThrows(IllegalArgumentException.class,()->PacketBuilderFactory.createFactory((String)null));
    }

    @Test
    //What happens if the name is AckBuilder
    final void testCreateFactoryStringAck() {

        assertNotNull(PacketBuilderFactory.createFactory("AckBuilder"));
    }

    @Test
    //What if it's MessageBuilder
    final void testCreateFactoryStringMessage(){
        assertNotNull(PacketBuilderFactory.createFactory("MessageBuilder"));
    }

  @Test
   //What if it's a legitimate class that we have in our package
  //but not one of our Builder classes?
    final void testCreateFactoryClassOfBadClass(){
        assertThrows(IllegalArgumentException.class,()-> PacketBuilderFactory.createFactory(AckPacket.class));
    }

   @Test
   //What if it's the null class
   // ie, (Class<?>null is the argument
    final void testCreateFactoryClassOfNull(){
        assertThrows(NullPointerException.class,()->PacketBuilderFactory.createFactory((Class<?>)null));
    }

    @Test
    //What happens if it's AckBuilder?(String)
    final void testCreateFactoryClassOfAck(){
        assertNotNull(PacketBuilderFactory.createFactory(AckBuilder.class));
    }

    @Test
    //What What happens if it's MessageBuilder?(String)
    final void testCreateFactoryClassOfMessage(){
        assertNotNull(PacketBuilderFactory.createFactory(MessageBuilder.class));
    }
}
