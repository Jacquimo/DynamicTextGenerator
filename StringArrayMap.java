import java.util.*;
import java.util.Map.Entry;

/**
 * This class maps a string array (key) to a prefix objects (value). On average, it will retrieve the prefix objects in constant time on average.
 * @author Gray Houston
 * 
 * @version 2/12/15
 *
 */
public class StringArrayMap {
	private Map<List<String>, Prefix> map;
	
	/**
	 * Instantiates the StringArrayMap object.
	 */
	public StringArrayMap() {
		map = new HashMap<List<String>, Prefix>();
	}
	
	/**
	 * Returns the Prefix object to which the String[] key is mapped, or null if this map contains no mapping for the String[].
	 * @param prefixes - the String[] key whose associated Prefix object is to be returned
	 * @return the Prefix object associated with the key, or null if that key has not been mapped
	 */
	public Prefix getPrefix(String[] prefixes) {
		List<String> temp = new ArrayList<String>(Arrays.asList(prefixes));
		Prefix ret = map.get(temp);
		return ret;
	}
	
	/**
	 * Associates the specified Prefix object with the specified String[] key in this map).
	 * If the map previously contained a mapping for the String[] key, the old Prefix object is replaced by the specified Prefix object
	 * @param prefixes String[] key with which the specified Prefix object is to be associated
	 * @param pref Prefix object to be associated with the specified key
	 */
	public void putPrefix(String[] prefixes, Prefix pref) {
		List<String> temp = new ArrayList<String>(Arrays.asList(prefixes));
		map.put(temp, pref);
	}
	
	protected Iterator getKeysIterator() {
		return map.entrySet().iterator();
	}
	
	/**
	 * Gives a printed summary of this string map, listing all the trained
	 * string prefixes, with the corresponding possible non-null suffixes. Note
	 * that the order is arbitrary, and is not guaranteed to be consistent
	 */
	public void printMap() {
		Iterator itr = this.getKeysIterator();
		while (itr.hasNext()) {
			Entry<List<String>, Prefix> entry = (Entry) itr.next();
			List<String> key = entry.getKey();
			Prefix prefix = entry.getValue();
			String suffixes = "{";
			String comma = "";
			for (int i = 0; i < prefix.getNumSuffixes(); i++) {
				String suffix = prefix.getSuffixString(i);
				if (suffix != null) {
					suffixes += comma + suffix;
					comma = ", ";
				}
			}
			suffixes += "}";
			System.out.println(key + ": " + suffixes);
		}
	} 
}

class TestingJar {
	static final String checkString = "initialImplementation";
}
