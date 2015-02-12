
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

//This branch contains the code for running an Order-2 Markov Chain text generator


public class TextGenerationEngine {
	private static ArrayList<String> terminators = new ArrayList<String>() {{
		add(".");
		add("!");
		add("?");
	}};
	private static ArrayList<String> breakOnChars = new ArrayList<String>() {{
		add(",");
		add(";");
		add(":");
	}};
	private static ArrayList<String> trainedTexts = new ArrayList<String>();
	
	public static Map<List<String>, Prefix> table = null;
	
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
					System.out.printf("Invalid program action\n\n");
				}
				in.nextLine();
			} while(decision < 0);
			
			switch(decision){
			case 0:
				System.out.printf("\nProgram Ending\n");
				in.close();
				return;
				
			case 1:
				if (trainedTexts.size() < 1) {
					System.out.printf("Program has not been trained yet\n\n");
					break;
				}
				System.out.printf("\nDynamically Generated Text\n\n");
				String sentence = generateSentence();
				System.out.printf("%s\n\n", sentence);
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
					if (trainedTexts.contains(filename)) {
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
					
					if (table == null)
						table = PrefixGenerator.generateTable(filename); // This function will call the training function as well
					else
						PrefixGenerator.trainPrefixTable(table, filename);
				} while (filename == null);
				
				trainedTexts.add(filename);
				System.out.println();
				break;
			}
		}
	}
	
	public static void promptUser() {
		System.out.println("0 - Terminate Program");
		System.out.println("1 - Generate Sentence");
		System.out.println("2 - Train Program on File");
		System.out.print("Action: ");
	}
	
	public static String generateSentence() {
		StringBuilder ret = new StringBuilder();
		
		// The starting word is the empty string
		
		ArrayList<String> prefixStrings = Prefix.emptyInput;
		Prefix current = table.get(prefixStrings);
		String next = "";
		
		do {
			next = current.getRandomSuffix();
			ret.append(" ");
			if (breakOnChars.contains(current.getPrefix(1)))
				ret.deleteCharAt(ret.length()-1);
			
			// If there should be a comma, delete the space 
			if (next.length() > 0 && PrefixGenerator.isPunctuation(next.charAt(0)))
				ret.deleteCharAt(ret.length()-1);
			
			// Invoke special rules for formatting the return string
			if (next.equalsIgnoreCase("i"))
				ret.append("I");
			else
				ret.append(next);
			
			if (breakOnChars.contains(next)) {
				ret.append("\n");
			}
			
			prefixStrings = PrefixGenerator.updatePrefixStrings(prefixStrings, next);
			current = table.get(prefixStrings);
			
			if (current == null || current.getNumSuffixes() <= 0)
				break;
			
		} while (!shouldTerminate(next));
		
		// Make the sentence start with an upper case letter
		char upperFirstChar = ret.substring(1, 2).toUpperCase().charAt(0);
		ret.deleteCharAt(0);
		ret.setCharAt(0, upperFirstChar);
		
		return ret.toString().trim();
	}
	
	public static boolean shouldTerminate(String suffix) {
		if (suffix.equals(""))
			return false;
		
		for (int i = suffix.length()-1; i >= 0; --i) {
			char c = suffix.charAt(i);
			if (terminators.contains(c + ""))
				return true;
		}
		
		return false;
	}
}
