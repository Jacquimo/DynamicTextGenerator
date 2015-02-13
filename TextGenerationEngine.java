
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class TextGenerationEngine {	
	private static String[] terminators = { ".", "!", "?" };
	private static String[] breakChars =  { ",", ";", ":" };
	
	private static String[] trainedTexts = new String[8];
	private static int numTextsTrained = 0;
	
	public static StringArrayMap map = new StringArrayMap();
	public static int numSentences = 1;
	
	public static void addTrainedTexts(String filename) {
		trainedTexts[numTextsTrained++] = filename;
		if (numTextsTrained >= trainedTexts.length) {
			String[] temp = new String[2 * trainedTexts.length];
			for (int i = 0; i < trainedTexts.length; ++i)
				temp[i] = trainedTexts[i];
			trainedTexts = temp;
		}
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in); // This scanner is closed in the switch case where the program is selected to end
		while (true) {
			int decision = -1;
			
			// Prompt user input/action
			do {
				promptUser();
				try {
					decision = in.nextInt();
					
				} catch (Exception e) {
					decision = -1;
				}
				in.nextLine();
			} while(decision < 0);
			
			switch(decision){
			case 0:
				System.out.printf("\nProgram Ending\n");
				in.close();
				return;
				
			case 1:
				if (numTextsTrained < 1) {
					System.out.printf("Program has not been trained yet\n\n");
					break;
				}
				
				System.out.printf("\nDynamically Generated Text\n\n");
				for (int i = 0; i < numSentences; ++i) {
					String sentence = generateSentence();
					System.out.printf("%s\n\n", sentence);
				}
				break;
				
			case 2:
				String filename = null;
				do {
					System.out.print("Enter file name ('0' for menu): ");
					filename = in.next();
					in.nextLine();
					
					// Allow user to return to main menu
					if (filename.equals("0"))
						break;
					
					// Check if program has already been trained on this text
					if (haveTrainedText(filename)) {
						System.out.printf("Program has already been trained on this text\n\n");
						filename = null;
						continue;
					}
					
					// Check that the file is valid
					File check = new File(filename);
					if (!check.isFile()) {
						System.out.printf("Invalid file name\n\n");
						filename = null;
						continue;
					}
					
					PrefixGenerator.trainPrefixMap(map, filename);
				
				} while (filename == null);
				
				addTrainedTexts(filename);
				System.out.println();
				break;
				
			case 3:
				int length = -1;
				do {
					System.out.print("Number of Words in Prefix: ");
					length = in.nextInt();
					if (length <= 0)
						System.out.println("Invalid input");
					in.nextLine();
				} while (length <= 0);
				
				Prefix.NUM_CONTEXT_WORDS = length;
				System.out.println("All texts re-trained\n");
				
				Prefix.initializeEmptyInput();
				map = new StringArrayMap();
				for (int i = 0; i < numTextsTrained; ++i)
					PrefixGenerator.trainPrefixMap(map, trainedTexts[i]);
				break;
				
			case 4:
				int num = -1;
				do {
					System.out.print("Num. of Sentences: ");
					num = in.nextInt();
					if (num < 0)
						System.out.println("Invalid input");
				} while (num < 0);
				numSentences = num;
				break;
				
			default:
				System.out.printf("Invalid program action\n\n");
			}
		}
	}
	
	public static boolean haveTrainedText(String filename) {
		for (int i = 0; i < numTextsTrained; ++i)
			if (filename.equals(trainedTexts[i]))
				return true;
		
		return false;
	}
	
	public static void promptUser() {
		System.out.println("0 - Terminate Program");
		System.out.println("1 - Generate Sentence");
		System.out.println("2 - Train Program on File");
		System.out.println("3 - Change Number of Words in Prefix");
		System.out.println("4 - Number of Sentences to Generate");
		System.out.print("Action: ");
	}
	
	public static String generateSentence() {
		StringBuilder ret = new StringBuilder();
		
		// The starting word is the empty string
		
		String[] prefixStrings = Prefix.getEmptyInput();
		Prefix current = map.getPrefix(prefixStrings);
		String next = "";
		
		do {
			next = current.getRandomSuffix();
			ret.append(" ");

			String mostRecentWord = current.getPrefixString(current.getNumPrefixes()-1);
			if (mostRecentWord.length() > 0 && contains(breakChars, mostRecentWord.charAt(mostRecentWord.length()-1) + ""))
				ret.deleteCharAt(ret.length()-1);
			
			// If there should be a comma, delete the space 
			//if (next.length() > 0 && PrefixGenerator.isPunctuation(next.charAt(0)))
			//	ret.deleteCharAt(ret.length()-1);
			
			// Invoke special rules for formatting the return string
			if (next.equalsIgnoreCase("i"))
				ret.append("I");
			else
				ret.append(next);
			
			if (contains(breakChars, next.charAt(next.length()-1) + ""))
				ret.append("\n");
			
			prefixStrings = PrefixGenerator.updatePrefixStrings(prefixStrings, next);
			current = map.getPrefix(prefixStrings);
			
			if (current == null || current.getNumSuffixes() <= 0)
				break;
			
		} while (!shouldTerminate(next));
		
		// Make the sentence start with an upper case letter
		char upperFirstChar = ret.substring(1, 2).toUpperCase().charAt(0);
		ret.deleteCharAt(0);
		ret.setCharAt(0, upperFirstChar);
		
		return ret.toString().trim();
	}
	
	public static boolean isPunctuation(char c) {
		return c == '.' || c == ',' || c == '?' || c == '!' || c == ';' || c == ':' || c == '"' || c == '(' || c == ')';
	}
	
	public static boolean shouldTerminate(String suffix) {
		if (suffix.equals(""))
			return false;
		
		for (int i = suffix.length()-1; i >= 0; --i) {
			char c = suffix.charAt(i);
			if (contains(terminators, c + ""))
				return true;
			if (!isPunctuation(c))
				return false;
		}
		
		return false;
	}
	
	public static boolean contains(String[] array, String element) {
		for (int i = 0; i < array.length; ++i)
			if (array[i].equals(element))
				return true;
		
		return false;
	}
}
