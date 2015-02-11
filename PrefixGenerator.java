import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

//This branch contains the code for running an Order-2 Markov Chain text generator


public class PrefixGenerator {
	public static int prefixSize = 1;
	
	public static Map<List<String>,Prefix> generateTable(String filename) {
		Map<List<String>,Prefix> table = new HashMap<List<String>, Prefix>();
		trainPrefixTable(table, filename);
		return table;
	}
	
	public static void trainPrefixTable(Map<List<String>, Prefix> table, String filename) {
		// Open scanner on the file
		Scanner text;
		try {
			text = new Scanner(new File(filename));
		} catch (IOException io) {
			System.out.printf("File '%s' failed to open\n", filename);
			return;
		}
		
		// Assumes that the file has at least 1 word in it
		if (!text.hasNext()) {
			System.out.println("File is empty");
			return;
		}
		
		// Empty string prefix denotes the start of a sentence
		ArrayList<String> prefixStrings = Prefix.emptyInput;
		
		
		// Train over each word in the text every word
		while(text.hasNext()) {
			/*String prefixStr = text.next();
			char punct = prefixStr.charAt(prefixStr.length() - 1);

			// If the word starts with punctuation, update the prefix connections and set prevPrefix to the starting punctuation
			if (prefixStr.length() > 1 && isPunctuation(prefixStr.charAt(0))) {
				char start = prefixStr.charAt(0);
				prefixStr = prefixStr.substring(1);
				updateConnections(table, prevPrefix.toString(), start + "");
				prevPrefix = getPrefix(table, start + "");
			}
			
			// Handle the case where the word ends in punctuation
			if (prefixStr.length() > 1 && isPunctuation(punct)) {
				prefixStr = prefixStr.substring(0, prefixStr.length() - 1);
				updateConnections(table, prefixStr, punct + "");
			}
			
			Prefix currentPrefix = getPrefix(table, prefixStr);
			prevPrefix.addSuffix(currentPrefix);
			
			// update prevPrefix object
			if (isPunctuation(punct)) {
				// If the punctuation mark is a '.' or ';' then a new sentence is starting
				if (punct == '.' || punct == ';' || punct == '!') {
					prevPrefix = getPrefix(table, "");
				}
				else {
					prevPrefix = getPrefix(table, punct + "");
				}
			}
			else {
				prevPrefix = currentPrefix;
			}
			*/
			
			// Update the previous prefix with a reference to the first prefix in the suffix string
			Prefix prevPrefix = getPrefix(table, prefixStrings);
			String suffix = text.next();
			ArrayList<Prefix> prefixes = adjustForPunctuation(table, prefixStrings, suffix);
			prevPrefix.addSuffix(prefixes.get(0).getPrefix(1));
			
			// Update the prefixString values
			prefixStrings = new ArrayList<String>();
			if (!TextGenerationEngine.shouldTerminate(suffix)) {
				prefixStrings.add(prefixes.get(prefixes.size()-1).getPrefix(0));
				prefixStrings.add(prefixes.get(prefixes.size()-1).getPrefix(1));
				prevPrefix = prefixes.get(prefixes.size()-1); // Get the last prefix object
			}
			else {
				prevPrefix = table.get(Prefix.emptyInput);
				prefixStrings = Prefix.emptyInput;
			}
		}
		
		// Handle case where final sentence doesn't end in a period
		// If it ended in a period, prevPrevious will be set to the empty string (signifying the start of a new sentence)
		//if (!prevPrefix.toString().equals(""))
		//	updateConnections(table, prevPrefix.toString(), ".");
		
		
		text.close();
	}
	
	public static Prefix getPrefix(Map<List<String>, Prefix> table, ArrayList<String> prefixStrings) {		
		Prefix prefix = table.get(prefixStrings);
		if (prefix == null) {
			prefix = new Prefix(prefixStrings);
			table.put(prefixStrings, prefix);
		}
		return prefix;
	}
	
	// This needs to be updated
	/*private static void updateConnections(Map<String[], Prefix> table, String[] prefixStrings, String suffix) {
		Prefix prefix = getPrefix(table, prefixStr);
		Prefix suffix = getPrefix(table, suffixStr);
		
		prefix.addSuffix(suffix);
	}*/
	
	public static boolean isPunctuation(char c) {
		return c == '.' || c == ',' || c == '?' || c == '!' || c == ';' || c == ':' || c == '"' || c == '(' || c == ')';
	}
	
	// Updates prefix string array so that the first element is removed and a new string is added as the last element
	public static ArrayList<String> updatePrefixStrings(List<String> prefs, String str) {
		ArrayList<String> ret = new ArrayList<String>();
		for (int i = 0; i < prefs.size() - 1; ++i) {
			ret.add(prefs.get(i+1));
		}
		ret.add(str);
		
		return ret;
	}
	
	/**
	 * Split prefix string into (potentially) multiple prefix objects to handle punctuation
	 * @param str
	 * @return
	 */
	public static ArrayList<Prefix> adjustForPunctuation(Map<List<String>, Prefix> table, ArrayList<String> prevPrefixes, String str) {
		if (str.length() <= 1) {
			Prefix ret = getPrefix(table, updatePrefixStrings(prevPrefixes, str));
			return (new ArrayList<Prefix>() {{ add(ret); }});
		}
		
		Prefix prev = null; // Variable used so that we can update the connections of prefix objects and suffixes
		
		int start = 0;
		ArrayList<Prefix> prefixes = new ArrayList<Prefix>();
		char c = str.charAt(start);
		
		// Loop over all of the starting punctuation marks
		while (isPunctuation(c)) {
			prevPrefixes = updatePrefixStrings(prevPrefixes, c + "");
			Prefix prefix = getPrefix(table, prevPrefixes);
			
			// Update the previous prefix's suffix list
			if (prev != null)
				prev.addSuffix(c + "");
			prev = prefix;
			
			prefixes.add(prefix);
			c = str.charAt(++start);
		}
		str = str.substring(start);
		
		// Add the actual word to the array of prefixes
		int punctStart = indexOfLastPunctuation(str);
		String strSuffix = str.substring(0, punctStart);
		if (prev != null)
			prev.addSuffix(strSuffix);
		
		prevPrefixes = updatePrefixStrings(prevPrefixes, strSuffix);
		Prefix current = getPrefix(table, prevPrefixes);
		prefixes.add(current);
		prev = current;
		// At this point, prev is guaranteed to never be null
		
		// Add any ending punctuation, if there is any
		if (punctStart < str.length()) {
			str = str.substring(punctStart);
			while (str.length() > 0) {
				c = str.charAt(0);
				strSuffix = c + "";
				prev.addSuffix(strSuffix);
				
				// Get the associated prefix object and update the list and pointer to the previous prefix
				prevPrefixes = updatePrefixStrings(prevPrefixes, strSuffix);
				current = getPrefix(table, prevPrefixes);
				prefixes.add(current);
				prev = current;
				
				// To insure that we don't index out of bounds when there is only 1 character in the string
				if (str.length() <= 1)
					break;
				else
					str = str.substring(1);
			}
		}
		
		// At this point, each prefix object found/created from this string should be in order and have had their suffix lists updated
		
		//Prefix[] ret = prefixes.toArray(new Prefix[prefixes.size()]);
		return prefixes;
	}
	
	// Returns the index of the last punctuation mark so that the return value's index works with the substring non-inclusive ending
	private static int indexOfLastPunctuation(String str) {
		int ret = str.length();
		while (ret > 0) {
			if (!isPunctuation(str.charAt(str.length()-1)))
				break;
			else
				--ret;
		}
		return ret;
	}
}







