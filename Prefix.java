import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Prefix class that represents prefixes used in a Markov Chain text generator. A prefix can have a fixed but arbitrary number of context words.
 * @author Gray Houston
 * 
 * @version 2/12/15
 *
 */
public class Prefix {
	/*private String first;
	private String second;
	*/
	public static int NUM_CONTEXT_WORDS = 2;
	
	private String[] prefixStrs;
	private String[] suffixes;
	private int numSuffixes; 
	
	public static String[] emptyInput = null;
	
	public static void initializeEmptyInput() {
		emptyInput = new String[NUM_CONTEXT_WORDS];
		for (int i = 0; i < Prefix.NUM_CONTEXT_WORDS; ++i)
			emptyInput[i] = "";
	}
	
	/**
	 * Constructor that takes an array of prefix strings as an argument
	 * @param prefixStrings - the array of prefix strings
	 */
	public Prefix(String[] prefixStrings) {
		prefixStrs = prefixStrings;
		suffixes = new String[8];
		numSuffixes = 0;
	}
	
	/**
	 * Adds a suffix that is comprised of the specified strings as prefix strings.
	 * The order of the suffix strings is important and significant
	 * @param strs - the strings that comprise the suffix (either variable args. or an array)
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
	 * Selects a random suffix from the list of suffixes. This function is used in the text generation stage
	 * @return - a random suffix string
	 */
	public String getRandomSuffix() {
		// (high - low) * Math.random() + low
		int index = (int) (this.suffixes.length * Math.random());
		return this.suffixes[index];
	}
	
	/**
	 * Determines equality among Prefix objects. Two Prefix objects are considered equal if they both
	 * have the exact same string prefixes in the same order.
	 * @param obj - Object to determine equality against
	 */
	public boolean equals(Object obj) {
		Prefix other;
		if (obj instanceof Prefix)
			other = (Prefix)obj;
		else
			return false;
		
		for (int i = 0; i < prefixStrs.length; ++i) {
			if (this.prefixStrs[i] != other.prefixStrs[i])
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
		
		for (int i = 0; i < prefixStrs.length; ++i) {
			ret.append(prefixStrs[i]);
			ret.append(" ");
		}
		return ret.toString();
	}
}





