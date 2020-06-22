package common.json.message;

public class PacketBuilderFactory {
	
    private static final String PACKAGE = "common.json.message.";
    
    private PacketBuilderFactory() {
    }
    @SuppressWarnings("unchecked")
    public static <T> T createFactory(String builderName) throws IllegalArgumentException{
        try {
            Class<?> clazz = Class.forName(PACKAGE + builderName);
            Object builder = clazz.newInstance();
            return (T) builder;
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException  e)

        { throw new IllegalArgumentException("bad builder name: " + builderName, e);
    }
  }
   
    public static <T> T createFactory(Class<T> type) throws IllegalArgumentException{
        return createFactory(type.getSimpleName());
    }
}
