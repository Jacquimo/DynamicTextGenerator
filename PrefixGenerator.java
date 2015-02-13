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
		Prefix.initializeEmptyInput();
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
		ArrayList<String> prefixStrings = new ArrayList<String>(Prefix.emptyInput);
		
		
		// Train over each word in the text every word
		while(text.hasNext()) {			
			// Update the previous prefix with a reference to the first prefix in the suffix string
			Prefix prevPrefix = getPrefix(table, prefixStrings);
			String suffix = text.next().toLowerCase();
			ArrayList<Prefix> prefixes = adjustForPunctuation(table, prefixStrings, suffix);
			// add as a suffix the last prefix string in the first prefix object
			prevPrefix.addSuffix(prefixes.get(0).prefixStrs[prefixes.get(0).prefixStrs.length - 1]); 
			
			prefixStrings = new ArrayList<String>(prefixStrings);
			// Update the prefixString values
			//prefixStrings = new ArrayList<String>();
			String[] temp = prefixes.get(prefixes.size()-1).toString().trim().split(" ");
			for (int i = 0; i < temp.length; ++i) {
				prefixStrings.remove(0);
				prefixStrings.add(temp[i]);
			}
			
			//prefixStrings.add(prefixes.get(prefixes.size()-1).getPrefix(0));
			//prefixStrings.add(prefixes.get(prefixes.size()-1).getPrefix(1));
			
			// Can use clear method of array list here since we have already assigned the prefixStrings var.
			// to point to a newly allocated array list
			if (TextGenerationEngine.shouldTerminate(prefixes.get(prefixes.size()-1).prefixStrs[Prefix.NUM_CONTEXT_WORDS - 1])) 
				prefixStrings = new ArrayList<String>(Prefix.emptyInput);
		}
		
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
			if (!isPunctuation(str.charAt(ret-1)))
				break;
			else
				--ret;
		}
		return ret;
	}
}







