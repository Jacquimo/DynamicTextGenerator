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
	private String first;
	private String second;
	private ArrayList<String> suffixes;
	
	public static ArrayList<String> emptyInput = new ArrayList<String>() {{ add(""); add(""); }};
	
	/**
	 * Constructor that takes a single prefix string arguments
	 * @param strs - (variable arg.) array of strings that holds the prefixes
	 */
	public Prefix(String f, String s) {
		this.first = f;
		this.second = s;
		suffixes = new ArrayList<String>();
	}
	
	public Prefix(String[] prefixStrings) {
		this.first = prefixStrings[0];
		this.second = prefixStrings[1];
		suffixes = new ArrayList<String>();
	}
	
	public Prefix(ArrayList<String> prefs) {
		this.first = prefs.get(0);
		this.second = prefs.get(1);
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
	
	public String getSuffix(int index) {
		return this.suffixes.get(index);
	}
	
	public int getNumSuffixes() {
		return suffixes.size();
	}
	
	public String getPrefix(int i) {
		return i == 0 ? first : second;
	}
	
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
		
		if (!this.first.equals(other.first) || !this.second.equals(other.second))
			return false;
		
		return true;
	}
	
	public String toString() {
		// Since what makes a prefix unique is its prefixes, the prefixes are what should be printed for this class
		return this.first + " " + this.second;
	}
}





