import static org.junit.Assert.*;

import org.junit.Test;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import net.sf.webcat.annotations.*;

public class Proj4Test {

    @Test(timeout = 100)
    @ScoringWeight(.0083)
    @Hint("check initializeSentenceStartArray")
    //Test if the initial array of prefixes are empty strings with a size of 3
        public void testInitializeSentenceStartArray() {
        String msg = "initializeSentenceStartArray: did you " +
            "initialize your array correctly?";
        String [] emptyTest = {"", "", ""};
        
        Prefix.initializeSentenceStartArray();
        assertArrayEquals(msg, emptyTest, Prefix.getStartOfSentencePrefixes());
    }
    
    @Test(timeout = 100)
    @ScoringWeight(.0083)
    @Hint("check Prefix")
    //Test if the constructor initializes the prefix array correctly
        public void testPrefixConstructor() {
        String msg = "Prefix: did you initialize your prefix array correctly?";
        String [] testArray = {"Happy", "Howard", "Harry",
            "James", "Jacod", "John",
            "Arnold", "Andy", "Amy"};
        
        Prefix p = new Prefix(testArray);
        
        for(int i = 0; i < testArray.length; i++) {
            assertEquals(msg, testArray[i], p.getPrefixString(i));
        }
    }
    
    @Test(timeout = 100)
    @ScoringWeight(.0083)
    @Hint("check getNumSuffixes")
    //Test if the number of suffixes changes
        public void testGetNumSuffixes00() {
        String msg = "getNumSuffixes: initial number of suffixes should be 0";
        String [] emptyTest = {"", "", ""};
        
        //Number of suffixes should be 0
        Prefix p = new Prefix(emptyTest);
        
        assertEquals(msg, 0, p.getNumSuffixes());
    }
    
    @Test(timeout = 100)
    @ScoringWeight(.0083)
    @Hint("check getNumSuffixes")
    //Test if the number of suffixes changes
        public void testGetNumSuffixes01() {
        String msg = "getNumSuffixes: does the number of suffixes change correctly?";
        
        String [] emptyTest = {"", "", ""};
        
        Prefix p = new Prefix(emptyTest);
        p.addSuffix("testing");
        
        assertEquals(msg, 1, p.getNumSuffixes());
    }
    
    @Test(timeout = 100)
    @ScoringWeight(.0083)
    @Hint("check getNumSuffixes")
    //Test if the num of prefixes change
        public void testGetNumPrefixes() {
        String msg = "getNumPrefixes: is the number of prefixes correct?";
        
        String [] size4 = {"test1", "test2", "test3", "test4"};
        String [] size3 = {"I", "wub", "chu"};
        
        Prefix p = new Prefix(size4);
        Prefix q = new Prefix(size3);
        
        assertTrue(msg, p.getNumPrefixes() > q.getNumPrefixes());
    }
    
    @Test(timeout = 100)
    @ScoringWeight(.0083)
    @Hint("check getPrefixString")
    //Test if prefix returned at "index" matches ours
        public void testGetPrefixString() {
        String msg = "getPrefixString: string does not exist or string returned "
            + "string is incorrect.";
        
        String [] testArray = {"index0", "index1", "index2", "index3"};
        
        Prefix p = new Prefix(testArray);
        
        assertEquals(msg, testArray[2], p.getPrefixString(2));
    }
    
    @Test(timeout = 100)
    @ScoringWeight(.0083)
    @Hint("check addSuffix")
    //Test if number of suffixes chances
        public void testAddSuffix() {
        String msg = "addSuffix: was the suffix added correctly?";
        String [] testArray = {"index0", "index1", "index2", "index3"};
        
        //Number of suffixes should be 0
        Prefix p = new Prefix(testArray);
        
        p.addSuffix("Jonny");
        p.addSuffix("Jacob");
        p.addSuffix("Piper-Smith");
        
        assertEquals(msg, 3, p.getNumSuffixes());
    }
    
    @Test(timeout = 100)
    @ScoringWeight(.0083)
    @Hint("check getSuffixString")
    //Test if suffix returned at "index" matches ours
        public void testGetSuffixString() {
        String msg = "getSuffixString: string doesnot exist or returned "
            + "string is incorrect.";
        
        String [] testArray = {"index0", "index1", "index2", "index3"};
        Prefix p = new Prefix(testArray);
        
        p.addSuffix("index0");
        p.addSuffix("index1");
        p.addSuffix("index2");
        
        assertEquals(msg, "index1", p.getPrefixString(1));
    }
    
    @Test(timeout = 100)
    @ScoringWeight(.0084)
    @Hint("check getRandomSuffix")
    //Test if a randomly selected suffix is within the array
    public void testGetRandomSuffix() {
        String msg = "getRandomSuffix: suffix does not exist.";
        
        String [] testArray = {"index0", "index1", "index2", "index3"};
        Prefix p = new Prefix(testArray);
        
        p.addSuffix("index0");
        p.addSuffix("index1");
        p.addSuffix("index2");
        
        String [] suffixes = new String [3];
        
        String ranSuffix = p.getRandomSuffix();
        ranSuffix.trim();
        
        for(int i = 0; i < p.getNumSuffixes(); i++) {
            suffixes[i] = p.getSuffixString(i);
        }
        
        assertTrue(msg, Arrays.asList(suffixes).contains(ranSuffix));
    }
    
    @Test(timeout = 100)
    @ScoringWeight(.0084)
    @Hint("check toString")
    //Test if prefixes are converted to Strings
        public void testToString() {
        String msg = "toString: prefixes weren't converted to Strings correctly.";
        
        String [] testArray = {"index0", "index1", "index2", "index3"};
        Prefix p = new Prefix(testArray);
        
        String expected = "index0 index1 index2 index3 ";
        
        assertEquals(msg, expected, p.toString());
    }
    
    @Test(timeout = 100)
    @ScoringWeight(.0084)
    @Hint("check equals")
    //Test if two prefix objects are the same
        public void testEquals00() {
        String msg = "equals: Prefix objects are not the same.";
        
        String [] testArray = {"Boom", "Shaka", "Laka"};
        
        Prefix p = new Prefix(testArray);
        Prefix q = new Prefix(testArray);
        
        assertTrue(msg, p.equals(q));
    }
    
    @Test(timeout = 100)
    @ScoringWeight(.0084)
    @Hint("check equals")
    //Test if two prefix objects are differebt
        public void testEquals01() {
        String msg = "equals: Prefix objects should be different.";
        
        String [] testArray = {"Boom", "Shaka", "Laka"};
        String [] arrayTest = {"I", "Love","Rock", "n", "Roll"};
        
        Prefix p = new Prefix(testArray);
        Prefix q = new Prefix(arrayTest);
        
        assertFalse(msg, p.equals(q));
    }
    
// Test if they add the first word in a sentence to the hashmap
    @Test(timeout = 100)
    @ScoringWeight(0.075)
    @Hint("check trainPrefixMap")
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
    @ScoringWeight(0.075)
    @Hint("check trainPrefixMap")
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
    @ScoringWeight(0.075)
    @Hint("check trainPrefixMap")
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
    @ScoringWeight(0.075)
    @Hint("check trainPrefixMap")
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
    @ScoringWeight(0.075)
    @Hint("check trainPrefixMap")
    public void testTrainPrefixMap_05() {
        String filename = "hamlet.txt";
        StringArrayMap expected = new StringArrayMap();
        StringArrayMap actual = new StringArrayMap();
        trainPrefixMap(expected, filename);
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
    @ScoringWeight(0.075)
    @Hint("check trainPrefixMap")
    public void testTrainPrefixMap_06() {
        String filename = "hamlet.txt";
        StringArrayMap actual = new StringArrayMap();
        PrefixGenerator.trainPrefixMap(actual, filename);
        Iterator act = actual.getKeysIterator();
        String msg = "trainPrefixMap: Check if you converted all words to lowercase";
        
        assertTrue(msg, act.hasNext());
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
    @ScoringWeight(0.075)
    @Hint("check trainPrefixMap")
    public void testTrainPrefixMap_07() {
        String filename = "hamlet.txt";
        StringArrayMap expected = new StringArrayMap();
        StringArrayMap actual = new StringArrayMap();
        trainPrefixMap(expected, filename);
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
    @ScoringWeight(0.075)
    @Hint("check trainPrefixMap")
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
    
    protected static String[] updatePrefixStrings(String[] prefixes, String nextPrefix) {
        String[] ret = new String[prefixes.length];
        // Copy over all the prefix strings except the first
        for (int i = 0; i < prefixes.length - 1; ++i)
            ret[i] = prefixes[i+1];
        ret[ret.length-1] = nextPrefix;
        return ret;
    }
    
    @Test
    @ScoringWeight(0.0273)
    @Hint("check shouldTerminate")
    //Test if the sentence terminates correctly
        public void testShouldTerminate00() {
        String msg = "shouldTerminate: are sentences terminated correctly?";
        
        String [] terminators = {"Sam.", "terrified!", "world?"};
        
        for(int i = 0; i < terminators.length; i++) {
            assertTrue(msg, TextGenerationEngine.shouldTerminate(terminators[i]));
        }
    }
    
    @Test
    @ScoringWeight(0.0273)
    @Hint("check shouldTerminate")
    //Test if there is a terminating character in middle of sentence
        public void testShouldTerminate01() {
        String msg = "shouldTerminate: was there a terminating char in the "
            + "middle of the word?";
        
        String[] terminators = { "St.John", ".java", "tosh.O", "5.3", "sure...well", "?-", "we!ght"};
        
        for(int i = 0; i < terminators.length; i++) {
            assertFalse(msg, TextGenerationEngine.shouldTerminate(terminators[i]));
        }
    }
    
    @Test
    @ScoringWeight(0.0273)
    @Hint("check shouldTerminate")
    //Test when there are no terminating characters at all
        public void testShouldTerminate02() {
        String msg = "shouldTerminate: was there a terminating char at all?";
        
        String[] noTerminators = {"Rowdy", "like", "pizza", "", " ", "Pass"};
        
        for(int i = 0; i < noTerminators.length; i++) {
            assertFalse(msg, TextGenerationEngine.shouldTerminate(noTerminators[i]));
        }
    }
    
    @Test
    @ScoringWeight(0.0273)
    @Hint("check shouldTerminate")
    //Test when there is punctuation char before terminating char
        public void testShouldTerminate03() {
        String msg = "shouldTerminate: were there any punctuation characters "
            + "before a terminating char?";
        
        String[] puncArray = { "personal.\"", "ogre!)", "huh?\"", ".(", "Yelp!;", "done.'" };
        
        
        for(int i = 0; i < puncArray.length; i++) {
            assertTrue(msg, TextGenerationEngine.shouldTerminate(puncArray[i]));
        }
    }
    
    @Test
    @ScoringWeight(0.0273)
    @Hint("check shouldTerminate")
    //Test when there is punc char without any terminating char
        public void testShouldTerminate04() {
        String msg = "shouldTerminate: were there only punctuation characters at the end?";
        
        String[] puncNoTerminator = { "end\"", "code;", "reason:", "after,", "(comment)", ")", "(", "test'" };
        
        for(int i = 0; i < puncNoTerminator.length; i++) {
            assertFalse(msg, TextGenerationEngine.shouldTerminate(puncNoTerminator[i]));
        }
    }
    
    @Test
    @ScoringWeight(0.0273)
    @Hint("check shouldTerminate")
    //Test when punc char is a non-final char (ie. it's)
        public void testShouldTerminate05() {
        String msg = "shouldTerminate: are you checking for punctuating "
            + "characters correctly?";
        
        String[] nonFinal = { "It's", "(start", "\"CS180", "Class::variable", ")-though" };
        
        for(int i = 0; i < nonFinal.length; i++) {
            assertFalse(msg, TextGenerationEngine.shouldTerminate(nonFinal[i]));
        }
    }
    
    @Test
    @ScoringWeight(0.0273)
    @Hint("check generateSentence")
    //Tests the output of generateSentence() for formatting
        public void testGenerateSentence00() {
        String msg = "generateSentence: is your output formatted correctly?";
        StringArrayMap actual = new StringArrayMap();
        PrefixGenerator.trainPrefixMap(actual, "generateSentencetest00.txt");
        
        String expected = "Will you go,\nI need some cookies!";
        String act = TextGenerationEngine.generateSentence(actual);
        assertEquals(msg, expected, act);
        
    }
    
    @Test(timeout = 200)
    @ScoringWeight(0.0273)
    @Hint("check generateSentence")
    //Test the validity of the output of generateSentence()
    // may break this up into multiple test cases
    public void testGenerateSentence01() {
        String msg = "generateSentence: are your prefixes-suffix pairings correct?";
        StringArrayMap actual = new StringArrayMap();
        PrefixGenerator.trainPrefixMap(actual, "hamlet.txt");
        
        for (int index = 0; index < 20; ++index) {
            String sentence = TextGenerationEngine.generateSentence(actual).toLowerCase().replace('\n', ' ').trim();
            Scanner s = new Scanner(sentence);
            assertTrue("generateSentence: does your program generate any output?", s.hasNext());
            String[] prefixes = Prefix.getStartOfSentencePrefixes();
            
            while (s.hasNext()) {
                String suffix = s.next();
                Prefix pref = actual.getPrefix(prefixes);
                assertNotNull(msg, pref);
                
                int i = 0;
                for (i = 0; i < pref.getNumSuffixes(); ++i)
                    if (pref.getSuffixString(i).equals(suffix))
                    break;
                assertFalse(msg, i >= pref.getNumSuffixes());
                
                prefixes = updatePrefixStrings(prefixes, suffix);
                if (TextGenerationEngine.shouldTerminate(suffix))
                    break;
            }
            
            assertFalse("generateSentence: did you properly terminate your sentence?", s.hasNext());
        }
    }
    
    @Test
    @ScoringWeight(0.0272)
    @Hint("check retraining")
    //Test if start of sentence prefix changes
        public void testRetraining00() {
        String msg = "retrain: did the start of sentence prefix change?";
        StringArrayMap actual = new StringArrayMap();
        PrefixGenerator.trainPrefixMap(actual, "hamlet.txt");
        
        actual = TextGenerationEngine.retrain(5);
        String[] start = Prefix.getStartOfSentencePrefixes();
        assertTrue(msg, start.length == 5);
        
        actual = TextGenerationEngine.retrain(2);
        start = Prefix.getStartOfSentencePrefixes();
        assertTrue(msg, start.length == 2);
    }
    
    @Test
    @ScoringWeight(0.0272)
    @Hint("check retraining")
    //Test if length of prefix can be set back to original after change
        public void testRetraining01() {
        String msg = "retrain: can your prefix be set back through retraining "
            + "after it was changed already?";
        StringArrayMap actual = new StringArrayMap();
        PrefixGenerator.trainPrefixMap(actual, "hamlet.txt");
        
        String[] start = Prefix.getStartOfSentencePrefixes();
        actual = TextGenerationEngine.retrain(7);
        actual = TextGenerationEngine.retrain(start.length);
        
        assertTrue(msg, start.length == Prefix.getStartOfSentencePrefixes().length);
    }
    
    @Test
    @ScoringWeight(0.0272)
    @Hint("check retraining")
    //Test if prefix length of two objects are different after training
        public void testRetrainging02() {
        String msg = "retrain: are you changing the length of your prefix "
            + "correctly?";
        StringArrayMap original = new StringArrayMap();
        PrefixGenerator.trainPrefixMap(original, "hamlet.txt");
        String[] start = Prefix.getStartOfSentencePrefixes();
        
        TextGenerationEngine.retrain(4); // do initial retrain so that we absolutely know length of original prefix
        StringArrayMap retrain = TextGenerationEngine.retrain(5);
        
        Iterator entries = retrain.getKeysIterator();
        while(entries.hasNext()) {
            Map.Entry<List<String>, Prefix> entry = (Entry<List<String>, Prefix>) entries.next();
            assertTrue(msg, entry.getKey().size() == 5);
        }
    }
}

