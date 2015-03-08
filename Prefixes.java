import java.util.*;

public class Prefixes {
    public static void main(String [] args) {
        String filename = "hamlet.txt";
        StringArrayMap expected = new StringArrayMap();
        PrefixGenerator.trainPrefixMap(expected, filename);
        Iterator ex = expected.getKeysIterator();
        
        while(ex.hasNext()) {
            
            System.out.println(ex.next());
        }
    }
}
        