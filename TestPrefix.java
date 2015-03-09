import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;

public class TestPrefix {
   /* 
   @Test
   //Test when the initial array has not been initialized
   public void testGetStartOfSentencePrefixes() {
       String msg = "getStartOfSentencePrefixes: initial array for start of sentence should be null.";
       
       //String [] nullArray = Prefix.getStartOfSe
       String[] foo = new String[1];
       boolean nullThrown = false;
       try {
           foo = Prefix.getStartOfSentencePrefixes();
       }
       catch (NullPointerException ex) {
           foo = null;
       }
       
       assertNull(msg, foo); 
   }
   */
   
   @Test
   //Test if the initial array of prefixes are empty strings with a size of 3
   public void testInitializeSentenceStartArray() {
       String msg = "initializeSentenceStartArray: did you " +
                    "initialize your array correctly?";
       String [] emptyTest = {"", "", ""};

       Prefix.initializeSentenceStartArray();
       assertArrayEquals(msg, emptyTest, Prefix.getStartOfSentencePrefixes());
   }

   @Test
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

   @Test
   //Test if the number of suffixes changes
   public void testGetNumSuffixes00() {
       String msg = "getNumSuffixes: initial number of suffixes should be 0";
       String [] emptyTest = {"", "", ""};

       //Number of suffixes should be 0
       Prefix p = new Prefix(emptyTest);

       assertEquals(msg, 0, p.getNumSuffixes());
   }

   @Test
   //Test if the number of suffixes changes
   public void testGetNumSuffixes01() {
       String msg = "getNumSuffixes: does the number of suffixes change correctly?";

       String [] emptyTest = {"", "", ""};

       Prefix p = new Prefix(emptyTest);
       p.addSuffix("testing");

       assertEquals(msg, 1, p.getNumSuffixes());
   }

   @Test
   //Test if the num of prefixes change
   public void testGetNumPrefixes() {
       String msg = "getNumPrefixes: is the number of prefixes correct?";

       String [] size4 = {"test1", "test2", "test3", "test4"};
       String [] size3 = {"I", "wub", "chu"};

       Prefix p = new Prefix(size4);
       Prefix q = new Prefix(size3);

       assertTrue(msg, p.getNumPrefixes() > q.getNumPrefixes());
   }

   @Test
   //Test if prefix returned at "index" matches ours
   public void testGetPrefixString() {
       String msg = "getPrefixString: string does not exist or string returned "
                    + "string is incorrect.";

       String [] testArray = {"index0", "index1", "index2", "index3"};

       Prefix p = new Prefix(testArray);

       assertEquals(msg, testArray[2], p.getPrefixString(2));
   }

   @Test
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

   @Test
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

   @Test
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

       for(int i = 0; i < p.getNumSuffixes(); i++) {
           suffixes[i] = p.getSuffixString(i);
       }

       assertTrue(msg, Arrays.asList(suffixes).contains(ranSuffix));
   }

   @Test
   //Test if prefixes are converted to Strings
   public void testToString() {
       String msg = "toString: prefixes weren't converted to Strings correctly.";

       String [] testArray = {"index0", "index1", "index2", "index3"};
       Prefix p = new Prefix(testArray);

       String expected = "index0 index1 index2 index3 ";

       assertEquals(msg, expected, p.toString());
   }
}

