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
	public static void trainPrefixMap(StringArrayMap map, String filename) {
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
		String[] prefixStrings = Prefix.getEmptyInput();
		
		
		// Train over each word in the text every word
		while(text.hasNext()) {			
			String suffix = text.next().toLowerCase();
			Prefix current = getPrefix(map, prefixStrings);
			current.addSuffix(suffix);
			prefixStrings = updatePrefixStrings(prefixStrings, suffix);
			
			if (TextGenerationEngine.shouldTerminate(suffix))
				prefixStrings = Prefix.getEmptyInput();
		}
		
		text.close();
	}
	
	public static Prefix getPrefix(StringArrayMap map, String[] prefixes) {
		Prefix ret = map.getPrefix(prefixes);
		if (ret == null) {
			ret = new Prefix(prefixes);
			map.putPrefix(prefixes, ret);
		}
		return ret;
	}
	
	public static String[] updatePrefixStrings(String[] prefixes, String nextPrefix) {
		String[] ret = new String[prefixes.length];
		// Copy over all the prefix strings except the first
		for (int i = 0; i < prefixes.length - 1; ++i)
			ret[i] = prefixes[i+1];
		ret[ret.length-1] = nextPrefix;
		return ret;
	}
}







