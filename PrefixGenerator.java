import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class provides methods to train the Dyanamic Text Generator program. This class only provides static methods and should never be instantiated.
 * 
 * @author Gray Houston <grayhouston@purdue.edu>
 * 
 * @version 2/16/15
 *
 */
public class PrefixGenerator {
	
	/**
	 * Using a given map of String[]'s to Prefix objects and the name of the training file, this method trains the program by associating Prefix objects
	 * with the suffix string (i.e. the word that appears directly after the current prefix). The resulting trained Prefix objects are stored in the
	 * given map.
	 * <P>
	 * It is valid that the map object can contain the results of prior training when this method is called. In fact, supporting this case is required to
	 * allow your program to be trained on multiple files.
	 * 
	 * @param map - the HashMap of String[] to Prefix objects. 
	 * @param filename - the name of the file on which to train the map
	 */
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
			text.close();
			return;
		}
		
		Prefix.initializeSentenceStartArray();
		// Empty string prefix denotes the start of a sentence
		String[] prefixStrings = Prefix.getStartOfSentencePrefixes();
		
		
		// Train over each word in the text every word
		while(text.hasNext()) {			
			String suffix = text.next().toLowerCase();
			Prefix current = getPrefix(map, prefixStrings);
			current.addSuffix(suffix);
			prefixStrings = updatePrefixStrings(prefixStrings, suffix);
			
			if (TextGenerationEngine.shouldTerminate(suffix))
				prefixStrings = Prefix.getStartOfSentencePrefixes();
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







