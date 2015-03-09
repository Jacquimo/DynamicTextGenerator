import java.util.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class TestPrefixGenerator {
	
	// Test if they add the first word in a sentence to the hashmap
	@Test(timeout = 100)
	public void testTrainPrefixMap_01() {
		Prefix.initializeSentenceStartArray();
		String filename = "test01.txt";
		Prefix test = new Prefix(Prefix.getStartOfSentencePrefixes());
		test.addSuffix("hello");
		
		StringArrayMap actual = new StringArrayMap();
		PrefixGenerator.trainPrefixMap(actual, filename);
		assertTrue("Fails to add prefix to StringArrayMap", 
				actual.getPrefix(Prefix.getStartOfSentencePrefixes()).equals(test));
	}
	
	// Test if they update their prefix to include the last word seen
	@Test(timeout = 100)
	public void testTrainPrefixMap_02() {
		Prefix.initializeSentenceStartArray();
		String filename = "test02.txt";
		String[] prefixes = Prefix.getStartOfSentencePrefixes();
		
		StringArrayMap actual = new StringArrayMap();
		PrefixGenerator.trainPrefixMap(actual, filename);
		Prefix result = actual.getPrefix(prefixes);
		assertTrue("Fails to update prefix string with most recent suffix", 
				result.getSuffixString(0).equals("world") && result.getNumSuffixes() == 1);
	}
	
	@Test(timeout = 100)
	public void testTrainPrefixMap_03() {
		
	}
	
    //Making sure the number of keys are equal
    @Test(timeout = 100)
    public void testTrainPrefixMap_10() {
        String filename = "hamlet.txt";
        StringArrayMap expected = new StringArrayMap();
        StringArrayMap actual = new StringArrayMap();
        PrefixGenerator.trainPrefixMap(expected, filename);
        PrefixGenerator.trainPrefixMap(actual, filename);
        
        Iterator ex = expected.getKeysIterator();
        Iterator act = actual.getKeysIterator();
        
        String msg = "trainPrefixMap: Check the amount of prefix Strings you have.";
        
        while (ex.hasNext()) {
            assertTrue(msg, act.hasNext());
            Object e = ex.next();
            Object a = act.next();
        }
    }
    
    //Making sure the number of keys are equal
    public static void testTrainPrefixMap_11(StringArrayMap actual, String filename) {
        StringArrayMap expected = new StringArrayMap();
        PrefixGenerator.trainPrefixMap(expected, filename);
        
        Iterator ex = expected.getKeysIterator();
        Iterator act = actual.getKeysIterator();
        
        String msg = "trainPrefixMap: Check the amount of prefix Strings you have.";
        
        while (act.hasNext()) {
            assertTrue(msg, ex.hasNext());
        }
    }
    /*
     * 
     * 
     Prefix e = expected.getPrefix((String[]) ex.next());
     Prefix a = actual.getPrefix((String[]) act.next());
     
     if (!e.equals(a)) // check to see that the prefix strings are the same
     return false;
     
     if (e.getNumSuffixes() != a.getNumSuffixes())
     return false;
     
     }
     if (act.hasNext()) // if this case were true, then the actual number of keys differs from the expected number of keys
     return false;
     
     return true;
     }
     */
}
