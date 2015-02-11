import java.util.ArrayList;
import java.util.Arrays;

/**
 * Prefix class that represents prefixes used in a Markov Chain text generator. A prefix can only have 1 prefix string
 * @author Gray Houston
 *
 */
public class Prefix {
	private String prefix;
	private ArrayList<Prefix> suffixes;
	
	/**
	 * Constructor that takes a single prefix string arguments
	 * @param strs - (variable arg.) array of strings that holds the prefixes
	 */
	public Prefix(String str) {
		this.prefix = str;
		suffixes = new ArrayList<Prefix>();
	}
	
	public void addSuffix(Prefix p) {
		this.suffixes.add(p);
	}
	
	/**
	 * Adds a suffix that is comprised of the specified strings as prefix strings.
	 * The order of the suffix strings is important and significant
	 * @param strs - the strings that comprise the suffix (either variable args. or an array)
	 */
	public void addSuffix(String str) {
		this.suffixes.add(new Prefix(str));
	}
	
	public Prefix getSuffix(int index) {
		return this.suffixes.get(index);
	}
	
	public int getNumSuffixes() {
		return suffixes.size();
	}
	
	/*
	public String prefixStringAt(int index) {
		return this.prefix[index];
	}
	*/
	
	public Prefix getRandomSuffix() {
		// (high - low) * Math.random() + low
		int index = (int) (this.suffixes.size() * Math.random());
		return this.suffixes.get(index);
	}
	
	
	/**
	 * Determines if the given variable num. of strings are prefix strings (regardless of their order)
	 * @param strs - the variable number of string prefixes to search for
	 * @return - whether or not the specified strings are prefix strings.
	 */
	/*public boolean containsPrefixes(String... strs) {
		return containsHelper(true, strs);
	}

	public boolean containsSuffixes(String... strs) {
		return containsHelper(false, strs);
	}
	
	private boolean containsHelper(boolean searchPrefixes, String... strs) {
		if (searchPrefixes) {
			ArrayList<String> searchArray = new ArrayList<String>(Arrays.asList(this.prefixes));
			for (int i = 0; i < strs.length; ++i) {
				if (!searchArray.contains(strs[i]))
					return false;
			}
			return true;
		}
		else {
			//ArrayList<Prefix>
			
			// Search speed optimization (make sure list isn't too long)
			if (strs.length > this.suffixes.size())
				return false;
			
			for (int i = 0; i < strs.length; ++i) {
				if (!this.suffixes.contains(strs[i])) {
					return false;
				}
			}
		}
		
		return true;
	}
	*/
	
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
		
		/*if (other.prefixes.length != this.prefixes.length)
			return false;
		
		// Order of the prefix strings is important/defining
		for (int i = 0; i < this.prefixes.length; ++i) {
			if (!this.prefixes[i].equals(other.prefixes[i]))
				return false;
		}
		*/
		
		return this.prefix.equals(other.prefix);
	}
	
	public String toString() {
		/*StringBuilder sb = new StringBuilder();
		for (int i = 0; i < prefixes.length; ++i) {
			sb.append(this.prefixes[i]);
			sb.append(" ");
		}
		
		return sb.toString();
		*/
		
		return this.prefix;
	}
}





