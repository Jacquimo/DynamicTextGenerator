import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Scanner;

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
			System.out.println("Invalid file name");
			return;
		}
		
		// Assumes that the file has at least 1 word in it
		if (!text.hasNext()) {
			System.out.println("File is empty");
			return;
		}
		
		// Empty string prefix denotes the start of a sentence
		Prefix prevPrefix = getPrefix(table, "");
		
		while(text.hasNext()) {
			String prefixStr = text.next();
			char punct = prefixStr.charAt(prefixStr.length() - 1);
			
			// Handle the case where the word ends in punctuation
			if (isPunctuation(punct)) {
				prefixStr = prefixStr.substring(0, prefixStr.length() - 1);
				updateConnections(table, prefixStr, punct + "");
			}
			
			Prefix currentPrefix = getPrefix(table, prefixStr);
			prevPrefix.addSuffix(currentPrefix);
			
			// update prevPrefix object
			if (isPunctuation(punct)) {
				// If the punctuation mark is a '.' or ';' then a new sentence is starting
				if (punct == '.' || punct == ';') {
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
	
	private static boolean isPunctuation(char c) {
		return c != '.' && c != ',' && c != '?' && c != '!' && c != ';' && c != '"';
	}
}







