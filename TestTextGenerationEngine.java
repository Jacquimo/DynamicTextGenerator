import static org.junit.Assert.*;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.*;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.*;
import java.util.Map.Entry;
import java.util.jar.*;

public class TestTextGenerationEngine {
	
	@Test
	public void testJar() throws Exception {
		JarFile jarFile = new JarFile("submission/StringArrayMap.jar");
		Enumeration e = jarFile.entries();

		URL[] urls = { new URL("jar:file:submission/StringArrayMap.jar!/") };
		URLClassLoader cl = URLClassLoader.newInstance(urls);
	    
		Class c = cl.loadClass("StringArrayMap");
	    final Field field = c.getField("checkString"); //.getDeclaredField("checkString");
	   
	    /*AccessController.doPrivileged(new PrivilegedExceptionAction() {
	    	public Object run() throws Exception {
	    		if (!field.isAccessible()) {
	    			field.setAccessible(true);
	    		}
	    		
	    		assertEquals("", "initialImplementation", (String) field.get(c.newInstance()));
	    		return null;
	    	}
	    });*/
	   
	  //field.setAccessible(true);
	    //assertEquals("", "initialImplementation", (String)field.get(null));
	    
	    File file = new File("StringArrayMap.jar");
	    URL url = file.toURI().toURL();
	    URL[] u = new URL[]{url};
	    ClassLoader loader = new URLClassLoader(u);
	    Class map = loader.loadClass("StringArrayMap");
	    Field f = map.getField("checkString");
	    assertEquals("", "initialImplementation", (String)f.get(null));
	}

   @Test
   //@ScoringWeight(0.0273)
   //Test if the sentence terminates correctly
   public void testShouldTerminate00() {
       String msg = "shouldTerminate: are sentences terminated correctly?";

       String [] terminators = {"Sam.", "terrified!", "world?"};

       for(int i = 0; i < terminators.length; i++) {
           assertTrue(msg, TextGenerationEngine.shouldTerminate(terminators[i]));
       }
   }

   @Test
   //@ScoringWeight(0.0273)
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
   //@ScoringWeight(0.0273)
   //Test when there are no terminating characters at all
   public void testShouldTerminate02() {
       String msg = "shouldTerminate: was there a terminating char at all?";
       
       String[] noTerminators = {"Rowdy", "like", "pizza", "", " ", "Pass"};

       for(int i = 0; i < noTerminators.length; i++) {
           assertFalse(msg, TextGenerationEngine.shouldTerminate(noTerminators[i]));
       }
   }

   @Test
   //@ScoringWeight(0.0273)
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
   //@ScoringWeight(0.0273)
   //Test when there is punc char without any terminating char
   public void testShouldTerminate04() {
       String msg = "shouldTerminate: were there only punctuation characters at the end?";
       
       String[] puncNoTerminator = { "end\"", "code;", "reason:", "after,", "(comment)", ")", "(", "test'" };

       for(int i = 0; i < puncNoTerminator.length; i++) {
           assertFalse(msg, TextGenerationEngine.shouldTerminate(puncNoTerminator[i]));
       }
   }

   @Test
   //@ScoringWeight(0.0273)
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
   //@ScoringWeight(0.0273)
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
   //@ScoringWeight(0.0273)
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
	    	   
	    	   prefixes = TestPrefixGenerator.updatePrefixStrings(prefixes, suffix);
	    	   if (TextGenerationEngine.shouldTerminate(suffix))
	    		   break;
	       }
	       
	       assertFalse("generateSentence: did you properly terminate your sentence?", s.hasNext());
       }
   }

   @Test
   //@ScoringWeight(0.0272)
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
   //@ScoringWeight(0.0272)
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
   //@ScoringWeight(0.0272)
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
