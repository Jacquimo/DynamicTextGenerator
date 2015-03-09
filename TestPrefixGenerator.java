import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestPrefixGenerator {
	
	// Test if they add the first word in a sentence to the hashmap
	@Test(timeout = 100)
	public void testTrainPrefixMap_01() {
		Prefix.initializeSentenceStartArray();
		String filename = "trainPrefixMaptest01.txt";
		Prefix test = new Prefix(Prefix.getStartOfSentencePrefixes());
		test.addSuffix("hello");
		
		StringArrayMap actual = new StringArrayMap();
		PrefixGenerator.trainPrefixMap(actual, filename);
		assertTrue("trainPrefixMap: fails to add prefix to StringArrayMap", 
				actual.getPrefix(Prefix.getStartOfSentencePrefixes()).equals(test));
	}
	
	// Test if they update their prefix to include the last word seen
	@Test(timeout = 100)
	public void testTrainPrefixMap_02() {
		Prefix.initializeSentenceStartArray();
		String filename = "trainPrefixMaptest02.txt";
		String[] prefixes = Prefix.getStartOfSentencePrefixes();
		prefixes[prefixes.length-1] = "hello";
		
		StringArrayMap actual = new StringArrayMap();
		PrefixGenerator.trainPrefixMap(actual, filename);
		Prefix result = actual.getPrefix(prefixes);
		assertTrue("trainPrefixMap: fails to update prefix string with most recent suffix", 
				result.getSuffixString(0).equals("world") && result.getNumSuffixes() == 1);
	}
	
	// Test if they check for sentence termination
	@Test(timeout = 100)
	public void testTrainPrefixMap_03() {
		Prefix.initializeSentenceStartArray();
		String filename = "trainPrefixMaptest03.txt";
		String[] prefixes = Prefix.getStartOfSentencePrefixes();
		String msg = "trainPrefixMap: incorrect suffixes for the start of sentence prefix. Did you "
				+ "correctly update the prefix when a sentence ended?";
		
		StringArrayMap actual = new StringArrayMap();
		PrefixGenerator.trainPrefixMap(actual, filename);
		Prefix result = actual.getPrefix(prefixes);
		assertTrue(msg, result.getNumSuffixes() == 2);
		assertTrue(msg, result.getSuffixString(0).equals("hello"));
		assertTrue(msg, result.getSuffixString(1).equals("test"));
	}
	
	// Test if they only use a period to test for sentence termination
	@Test(timeout = 100)
	public void testTrainPrefixMap_04() {
		Prefix.initializeSentenceStartArray();
		String[] prefixes = Prefix.getStartOfSentencePrefixes();
		String msg = "trainPrefixMap: incorrect suffixes for the start of sentence prefix. Did you "
				+ "only check if sentences ended in a period?";
		
		for (int i = 0; i < 2; ++i) {
			String filename = i == 0 ? "trainPrefixMaptest04-1.txt" : "trainPrefixMaptest04-2.txt";
			StringArrayMap actual = new StringArrayMap();
			PrefixGenerator.trainPrefixMap(actual, filename);
			Prefix result = actual.getPrefix(prefixes);
			assertTrue(msg, result.getNumSuffixes() == 3);
			assertTrue(msg, result.getSuffixString(1).equals(i == 0 ? "test?" : "test!"));
			assertTrue(msg, result.getSuffixString(2).equals(i == 0 ? "question" : "exclamation"));
		}
	}
	
    //Making sure the number of keys are equal
    @Test(timeout = 100)
    public void testTrainPrefixMap_05() {
        String filename = "hamlet.txt";
        StringArrayMap expected = new StringArrayMap();
        StringArrayMap actual = new StringArrayMap();
        TestPrefixGenerator.trainPrefixMap(expected, filename);
        PrefixGenerator.trainPrefixMap(actual, filename);
        
        Iterator ex = expected.getKeysIterator();
        Iterator act = actual.getKeysIterator();
        
        String msg = "trainPrefixMap: Check the amount of prefix objects in your StringArrayMap";
        
        while (ex.hasNext()) {
            assertTrue(msg, act.hasNext());
            Object e = ex.next();
            Object a = act.next();
        }
        assertFalse(msg, act.hasNext());
    }
    
    // check if they converted all words to lower case
    @Test(timeout = 100)
    public void testTrainPrefixMap_06() {
    	String filename = "hamlet.txt";
        StringArrayMap actual = new StringArrayMap();
        PrefixGenerator.trainPrefixMap(actual, filename);
        Iterator act = actual.getKeysIterator();
        String msg = "trainPrefixMap: Check if you converted all words to lowercase";
        
        while (act.hasNext()) {
        	Map.Entry<List<String>, Prefix> entry = (Entry<List<String>, Prefix>) act.next();
        	for (String str : entry.getKey()) {
        		if (str != null)
        			assertFalse(msg, containsUpperCase(str));
        	}
        }
    }
    
    private static boolean containsUpperCase(String str) {
    	for (char c : str.toCharArray())
    		if (c >= 'A' && c <= 'Z')
    			return true;
    	
    	return false;
    }

    // check to see if their hamlet training matches ours
    @Test(timeout = 150)
    public void testTrainPrefixMap_07() {
        String filename = "hamlet.txt";
        StringArrayMap expected = new StringArrayMap();
        StringArrayMap actual = new StringArrayMap();
        TestPrefixGenerator.trainPrefixMap(expected, filename);
        PrefixGenerator.trainPrefixMap(actual, filename);
        
        Iterator ex = expected.getKeysIterator();
        
        String msg = "";
        
        while (ex.hasNext()) {
        	Map.Entry<List<String>, Prefix> entry = (Entry<List<String>, Prefix>) ex.next();
        	Prefix a = actual.getPrefix(entry.getKey().toArray(new String[entry.getKey().size()]));
        	assertNotNull("trainPrefixMap: Did you add all prefixes to the StringArrayMap", a);
        	
        	Prefix e = entry.getValue();
        	assertTrue("trainPrefixMap: Check what String[] keys you are using in your StringArrayMap "
        			+ "or check if your Prefix class' equals method works", e.equals(a));
        	assertTrue("trainPrefixMap: Do you have the right number of suffixes "
        			+ "for the \"" + a.toString() + "\" prefix", e.getNumSuffixes() == a.getNumSuffixes());
        	
        	for (int i = 0; i < e.getNumSuffixes(); ++i) {
        		assertTrue("trainPrefixMap: Did you correctly add suffixes to the \"" + a.toString()
        				+ "\" prefix", e.getSuffixString(i).equals(a.getSuffixString(i)));
        	}
        }
    }
    
    // check if they accidentally change the start of sentence prefix
    @Test(timeout = 100)
    public void testTrainPrefixMap_08() {
    	String filename = "hamlet.txt";
    	StringArrayMap actual = new StringArrayMap();
    	PrefixGenerator.trainPrefixMap(actual, filename);
    
    	String msg = "trainPrefixMap: Did your training cause your start of sentence prefix to change";
    	
    	String[] startOfSentence = Prefix.getStartOfSentencePrefixes();
    	for (String str : startOfSentence)
    		assertTrue(msg, str.equals(""));
    }
    
    
    
	private static void trainPrefixMap(StringArrayMap map, String filename) {
		// Open scanner on the file
		Scanner text;
		try {
			text = new Scanner(new File(filename));
		} catch (IOException io) {
			System.out.printf("File '%s' failed to open\n", filename);
			return;
		}
		
		// Assumes that the file has at least 1 word in it
		if (!text.hasNext()) {
			System.out.println("File is empty");
			text.close();
			return;
		}
		
		Prefix.initializeSentenceStartArray();
		// Empty string prefix denotes the start of a sentence
		String[] prefixStrings = Prefix.getStartOfSentencePrefixes();
		
		
		// Train over each word in the text every word
		while(text.hasNext()) {			
			String suffix = text.next().toLowerCase();
			Prefix current = getPrefix(map, prefixStrings);
			current.addSuffix(suffix);
			prefixStrings = updatePrefixStrings(prefixStrings, suffix);
			
			if (TextGenerationEngine.shouldTerminate(suffix))
				prefixStrings = Prefix.getStartOfSentencePrefixes();
		}
		
		text.close();
	}
	
	private static Prefix getPrefix(StringArrayMap map, String[] prefixes) {
		Prefix ret = map.getPrefix(prefixes);
		if (ret == null) {
			ret = new Prefix(prefixes);
			map.putPrefix(prefixes, ret);
		}
		return ret;
	}
	
	private static String[] updatePrefixStrings(String[] prefixes, String nextPrefix) {
		String[] ret = new String[prefixes.length];
		// Copy over all the prefix strings except the first
		for (int i = 0; i < prefixes.length - 1; ++i)
			ret[i] = prefixes[i+1];
		ret[ret.length-1] = nextPrefix;
		return ret;
	}
}
