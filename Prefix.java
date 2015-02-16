import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Prefix class that represents prefixes used in a Dynamic Text Generator. A prefix can have a fixed but arbitrary number of context words.
 * @author Gray Houston <grayhouston@purdue.edu>
 * 
 * @version 2/16/15
 *
 */
public class Prefix {
	public static int NUM_CONTEXT_WORDS = 3;
	
	private String[] prefixes;
	private String[] suffixes;
	private int numSuffixes; 
	
	// The array of prefix strings that denote the start of a sentence is an array of empty strings
	private static String[] sentenceStartPrefixes = null;
	
	/**
	 * The array of prefix strings that denote the start of a sentence is an array of empty strings. This method must be called and the array must be initialized
	 * every time the program is trained on a new file so that the sentenceStartPrefixes array will have the correct length (i.e. correct number of elements).
	 * <P>
	 * If this method is not called every time a new file is trained, the program may inexplicably fail (especially if the length of the prefix has been changed).
	 */
	public static void initializeSentenceStartArray() {
		sentenceStartPrefixes = new String[NUM_CONTEXT_WORDS];
		for (int i = 0; i < Prefix.NUM_CONTEXT_WORDS; ++i)
			sentenceStartPrefixes[i] = "";
	}
	
	/**
	 * Returns a String[] whose contents (empty strings) are used to retrieve the Start-of-Sentence Prefix object.
	 * <P>
	 * This Start-of-Sentence Prefix object is special in that is used to determine the first word in a dynamically generated sentence.
	 * @return a copy of the startOfSentencePrefixes array (using the Arrays class copyOf method)
	 */
	public static String[] getStartOfSentencePrefixes() {
		return Arrays.copyOf(sentenceStartPrefixes, sentenceStartPrefixes.length);
	}
	
	/**
	 * Constructor that takes an array of prefix strings as an argument
	 * @param prefixStrings - the array of prefix strings
	 */
	public Prefix(String[] prefixStrings) {
		prefixes = prefixStrings;
		suffixes = new String[8];
		numSuffixes = 0;
	}
	
	/**
	 * Returns the number of suffixes that this Prefix object contains
	 * @return the value of the numSuffixes private variable
	 */
	public int getNumSuffixes() {
		return numSuffixes;
	}
	
	/**
	 * Returns the number of prefixes that this Prefix object contains
	 * @return the length of the prefixes String[]
	 */
	public int getNumPrefixes() {
		return prefixes.length;
	}
	
	/**
	 * Returns the prefix string at a specified index.
	 * <P>
	 * This method does not perform bounds checking. Therefore, passing an invalid index will throw an exception.
	 * @param index - the i'th index string
	 * @return the string at 'index' in the prefixes String[]
	 */
	public String getPrefixString(int index) {
		return prefixes[index];
	}
	
	/**
	 * Adds a suffix string to the array of all possible suffixes that appear after this prefix. This method allows for multiple copies of the same string
	 * to be added to the array.
	 * @param str - the string suffix (the word that appears directly after this prefix)
	 */
	public void addSuffix(String str) {
		suffixes[numSuffixes++] = str;
		if (numSuffixes >= suffixes.length) {
			String[] temp = new String[suffixes.length * 2];
			for (int i = 0; i < suffixes.length; ++i)
				temp[i] = suffixes[i];
			suffixes = temp;
		}
	}
	
	/**
	 * Selects a random suffix from the array of suffixes. This function is integral to the sentence generation stage. This function works by using Math.random() to select
	 * a valid index for the array of suffixes. This method returns the string at that index.
	 * <P>
	 * Warining: If the suffix array is empty (i.e. suffixes.length == 0), this method will throw an Array Index Out-of-Bounds Exception. Therefore, before calling this method,
	 * you may want to check the number of suffixes first.
	 * @return - a random suffix string (from the suffixes String[])
	 */
	public String getRandomSuffix() {
		// (high - low) * Math.random() + low
		int index = (int) (numSuffixes * Math.random());
		return this.suffixes[index];
	}
	
	/**
	 * Determines equality among Prefix objects. Two Prefix objects are considered equal if they both have the exact same string prefixes in the same order.
	 * @param obj - Object to determine equality against
	 */
	public boolean equals(Object obj) {
		Prefix other;
		if (obj instanceof Prefix)
			other = (Prefix)obj;
		else
			return false;
		
		for (int i = 0; i < prefixes.length; ++i) {
			if (this.prefixes[i] != other.prefixes[i])
				return false;
		}
		
		return true;
	}
	
	/**
	 * The string form of a prefix object is its list of prefixes converted to a whitespace delimited string
	 */
	public String toString() {
		// Since what makes a prefix unique is its prefixes, the prefixes are what should be printed for this class
		StringBuilder ret = new StringBuilder();
		
		for (int i = 0; i < prefixes.length; ++i) {
			ret.append(prefixes[i]);
			ret.append(" ");
		}
		return ret.toString();
	}
}





