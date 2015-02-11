import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Scanner;

//This branch contains the code for running an Order-2 Markov Chain text generator


public class PrefixGenerator {
	public static int prefixSize = 1;
	
	public static Dictionary<String,Prefix> generateTable(String filename) {
		Dictionary<String,Prefix> table = new Hashtable();
		trainPrefixTable(table, filename);
		return table;
	}
	
	public static void trainPrefixTable(Dictionary<String, Prefix> table, String filename) {
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
		Prefix prevPrefix = getPrefix(table, "");
		
		// Train over each word in the text every word
		while(text.hasNext()) {
			String prefixStr = text.next();
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
		}
		
		// Handle case where final sentence doesn't end in a period
		// If it ended in a period, prevPrevious will be set to the empty string (signifying the start of a new sentence)
		if (!prevPrefix.toString().equals(""))
			updateConnections(table, prevPrefix.toString(), ".");
		
		
		text.close();
	}
	
	public static Prefix getPrefix(Dictionary<String, Prefix> table, String prefixStr) {
		Prefix prefix = table.get(prefixStr);
		if (prefix == null) {
			prefix = new Prefix(prefixStr);
			table.put(prefixStr, prefix);
		}
		return prefix;
	}
	
	private static void updateConnections(Dictionary<String, Prefix> table, String prefixStr, String suffixStr) {
		Prefix prefix = getPrefix(table, prefixStr);
		Prefix suffix = getPrefix(table, suffixStr);
		
		prefix.addSuffix(suffix);
	}
	
	public static boolean isPunctuation(char c) {
		return c == '.' || c == ',' || c == '?' || c == '!' || c == ';' || c == ':' || c == '"' || c == '(' || c == ')';
	}
}







