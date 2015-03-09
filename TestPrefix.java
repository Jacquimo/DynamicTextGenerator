import static org.junit.Assert.*;
import org.junit.Test;

public class TestPrefix {

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
   //Test when the initial array has not been initialized
   public void testGetStartOfSentencePrefixes() {
       String msg = "getStartOfSentencePrefixes: should your array be null?";
                    
       assertNull(msg, Prefix.getStartOfSentencePrefixes()); 
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
   public void testGetNumSuffixes() {
   }
}

