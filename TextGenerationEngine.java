import java.io.File;
import java.util.Scanner;

/**
 * CS 180 - Dynamic Generation Project
 * 
 * This class prompts the user for an action and can dynamically generate sentences if the program has already been trained.
 * 
 * @author (Your Name) <(YourEmail@purdue.edu)>
 * 
 * @lab (Your Lab Section)
 * 
 * @version (Today's Date)
 *
 */
public class TextGenerationEngine {	
	private static String[] terminators = { ".", "!", "?" };
	private static String[] trainedTexts = new String[8];
	private static int numTextsTrained = 0;
	private static StringArrayMap map = null;
	private static int numSentances = 1;
	
	/**
	 * Adds a file to the list of files that the program has been trained on
	 * @param filename - name of the file to add
	 */
	public static void addTrainedTexts(String filename) {
		trainedTexts[numTextsTrained++] = filename;
		if (numTextsTrained >= trainedTexts.length) {
			String[] temp = new String[2 * trainedTexts.length];
			for (int i = 0; i < trainedTexts.length; ++i)
				temp[i] = trainedTexts[i];
			trainedTexts = temp;
		}
	}
	
	/**
	 * Determines if the program has been trained on a file
	 * @param filename - the name of the file to check
	 * @return
	 */
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
				for (int i = 0; i < numSentances; ++i) {
					String sentence = generateSentence(map);
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
				
				retrain(length);
				
				System.out.println("All texts re-trained\n");
				break;
				
			case 4:
				int num = -1;
				do {
					System.out.print("Num. of Sentences: ");
					num = in.nextInt();
					if (num < 0)
						System.out.println("Invalid input");
				} while (num < 0);
				numSentances = num;
				System.out.println();
				break;
				
			default:
				System.out.printf("Invalid program action\n\n");
			}
		}
	}
	
	/**
	 * Determines if a specified character is punctuation
	 * @param c - the character to check
	 * @return
	 */
	public static boolean isPunctuation(char c) {
		return c == '.' || c == ',' || c == '?' || c == '!' || c == ';' || c == ':' || c == '"' ||
				c == '(' || c == ')' || c == '\'';
	}
	
	/**
	 * Determines if the specified string is at the end of a sentence. A word is defined to be at the end of a sentence
	 * if a string contained within the "terminators" list occurs at the end of the word before any non-punctuation 
	 * characters occur. The terminator string doesn't need to be at the very end of the string for it to signal the end
	 * of the sentence.
	 * <P>
	 * Examples <P>
	 * goodbye." - end of sentence <P>
	 * good. - end of sentence <P>
	 * 1.5 - NOT end of sentence
	 * @param suffix - the word to check
	 * @return - true if word occurs at the end of a sentence
	 */
	public static boolean shouldTerminate(String suffix) {
		// TODO: determine if the given string is at the end of a sentence
		
		return false;
	}
	
	/**
	 * Using a StringArrayMap of String[]'s to Prefix objects, dynamically generate a sentence by selecting a random suffix 
	 * of the current prefix. 
	 * @return
	 */
	public static String generateSentence(StringArrayMap map) {
		// TODO: Implement text generation algorithm described in lab specification
		return "";
	}
	
	/**
	 * Make a new StringArrayMap and train it on all of the files stored in the trainedtexts array, but with
	 * the specified number of words in a prefix
	 * @param prefixLength
	 */
	public static void retrain(int prefixLength) {
		// TODO: implement the method
	}
}
