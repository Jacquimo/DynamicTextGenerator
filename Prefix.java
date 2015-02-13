import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//This branch contains the code for running an Order-2 Markov Chain text generator


/**
 * Prefix class that represents prefixes used in a Markov Chain text generator. A prefix can only have 1 prefix string
 * @author Gray Houston
 *
 */
public class Prefix {
	/*private String first;
	private String second;
	*/
	public static int NUM_CONTEXT_WORDS = 2;
	
	protected String[] prefixStrs;
	protected ArrayList<String> suffixes;
	
	public static ArrayList<String> emptyInput = null;
	
	public static void initializeEmptyInput() {
		emptyInput = new ArrayList<String>();
		for (int i = 0; i < NUM_CONTEXT_WORDS; ++i)
			emptyInput.add("");
	}
	
	/**
	 * Constructor that takes a single prefix string arguments
	 * @param strs - (variable arg.) array of strings that holds the prefixes
	 */
	/*public Prefix(String f, String s) {
		this.first = f;
		this.second = s;
		suffixes = new ArrayList<String>();
	}*/
	
	public Prefix(String[] prefixStrings) {
		this.prefixStrs = prefixStrings;
		suffixes = new ArrayList<String>();
	}
	
	public Prefix(ArrayList<String> prefs) {
		prefixStrs = new String[prefs.size()];
		for (int i = 0; i < prefixStrs.length; ++i)
			prefixStrs[i] = prefs.get(i);
		suffixes = new ArrayList<String>();
	}
	
	/**
	 * Adds a suffix that is comprised of the specified strings as prefix strings.
	 * The order of the suffix strings is important and significant
	 * @param strs - the strings that comprise the suffix (either variable args. or an array)
	 */
	public void addSuffix(String str) {
		this.suffixes.add(str);
	}
	
	/*public String getSuffix(int index) {
		return this.suffixes.get(index);
	}*/
	
	public String getRandomSuffix() {
		// (high - low) * Math.random() + low
		int index = (int) (this.suffixes.size() * Math.random());
		return this.suffixes.get(index);
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
		
		//if (!this.first.equals(other.first) || !this.second.equals(other.second))
		//	return false;
		
		for (int i = 0; i < prefixStrs.length; ++i) {
			if (this.prefixStrs[i] != other.prefixStrs[i])
				return false;
		}
		
		return true;
	}
	
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





