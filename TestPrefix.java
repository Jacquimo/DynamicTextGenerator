import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;
import net.sf.webcat.annotations.*;

public class TestPrefix {
   
   @Test(timeout = 100)
   @ScoringWeights(.0083)
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
   @ScoringWeights(.0083)
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
   @ScoringWeights(.0083)
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
   @ScoringWeights(.0083)
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
   @ScoringWeights(.0083)
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
   @ScoringWeights(.0083)
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
   @ScoringWeights(.0083)
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
   @ScoringWeights(.0083)
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
   @ScoringWeights(.0084)
   @Hint("check getRandomSuffix");
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

   @Test(timeout = 100)
   @ScoringWeights(.0084)
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
   @ScoringWeights(.0084)
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
   @ScoringWeights(.0084)
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
}

