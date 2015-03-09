import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;

public class TestTextGenerationEngine {

   @Test
   //Test if the sentence terminates correctly
   public void testShouldTerminate00() {
       String msg = "shouldTerminate: are sentences terminated correctly?";

       String [] terminators = {"I am Sam.", "I am terrified!", "What in the world?"};

       for(int i = 0; i < terminators.length; i++) {
           assertTrue(msg, TextGenerationEngine.shouldTerminate(terminators[i]));
       }
   }

   @Test
   //Test if there is a terminating character in middle of sentence
   public void testShouldTerminate01() {
       String msg = "shouldTerminate: was there a terminating char in the "
                    + "middle of the sentence?";

       String [] terminators = {"St. John is alive? Really now",
                                ".java files contain your code",
                                "Not sure...let's go the police"};

       for(int i = 0; i < terminators.length; i++) {
           assertFalse(msg, TextGenerationEngine.shouldTerminate(terminators[i]));
       }
   }

   @Test
   //Test when there are no terminating characters at all
   public void testShouldTerminate02() {
       String msg = "shouldTerminate: was there a terminating char at all?";

       String [] noTerminators = {"Rowdy won in seconds",
                                  "I like pizza and pie",
                                  "You shall not pass"};

       for(int i = 0; i < noTerminators.length; i++) {
           assertFalse(msg, TextGenerationEngine.shouldTerminate(noTerminators[i]));
       }
   }

   @Test
   //Test when there is punctuation char before terminating char
   public void testShouldTerminate03() {
       String msg = "shouldTerminate: were there any punctuation characters "
                    + "before a terminating char?";

       String [] puncArray = {"You know it's not personal.",
                              "You know she's a girl right?",
                              "Wait, isn't that an ogre?"};

       for(int i = 0; i < puncArray.length; i++) {
           assertTrue(msg, TextGenerationEngine.shouldTerminate(puncArray[i]));
       }
   }

   @Test
   //Test when there is punc char without any terminating char
   public void testShouldTerminate04() {
       String msg = "shouldTerminate: were there only punctuation characters?";

       String [] puncNoTerminator = {"It's a small world after all",
                                     "Why didn't you ask me",
                                     "You're killing me slowly"};

       for(int i = 0; i < puncNoTerminator.length; i++) {
           assertFalse(msg, TextGenerationEngine.shouldTerminate(puncNoTerminator[i]));
       }
   }

   @Test
   //Test when punc char is a non-final char (ie. it's)
   public void testShouldTerminate05() {
       String msg = "shouldTerminate: are you checking for punctuating "
                    + "characters correctly?";

       String [] nonFinal = {"You know I'm crazy,",
                             "Where is the house,",
                             "There are three things: "};

       for(int i = 0; i < nonFinal.length; i++) {
           assertFalse(msg, TextGenerationEngine.shouldTerminate(nonFinal[i]));
       }
   }

   @Test
   //Tests the output of generateSentence() for formatting
   public void testGenerateSentence00() {
       String msg = "generateSentence: is your output formatted correctly?";

   }

   @Test(timeout = 100)
   //Test the validity of the output of generateSentence()
   public void testGenerateSentence01() {
       String msg = "generateSentence: are your prefixes-suffixes pairing correct?";
       StringArrayMap actual = new StringArrayMap();
       PrefixGenerator.trainPrefixMap(actual, "hamlet.txt");
       String sentence = TextGenerationEngine.generateSentence(actual);
   }

   @Test
   //Test if start of sentence prefix changes
   public void testRetraining00() {
       String msg = "retrain: did the start of your sentence change?";

   }

   @Test
   //Test if length of prefix can be set back to original after change
   public void testRetraining01() {
       String msg = "retrain: can your prefix be set back through retraining "
                    + "after it was changed already?";

   }

   @Test
   //Test if prefix length of two objects are different after training
   public void testRetrainging02() {
       String msg = "retrain: are you changing the length of your prefix "
                    + "correctly?";

   }
}
