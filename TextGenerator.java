import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Scanner;


public class TextGenerator {
	private static ArrayList<String> terminators = new ArrayList<String>() {{
		add("");
		add(".");
		add("!");
	}};
	
	public static Dictionary<String,Prefix> table = null;
	
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		
		while (true) {
			int decision = -1;
			
			// Prompt user input/action
			do {
				promptUser();
				try {
					decision = in.nextInt();
					in.nextLine();
				} catch (Exception e) {
					System.out.println("Invalid program action");
				}
			} while(decision < 0);
			
			switch(decision){
			case 0:
				System.out.println("Program Ending");
				return;
				
			case 1:
				System.out.printf("\nDynamically Generated Text\n\n");
				String sentence = generateSentence();
				System.out.printf("%s\n\n", sentence);
				break;
				
			case 2:
				String filename;
				System.out.print("Enter file name: ");
				filename = in.next();
				in.nextLine();
				if (table == null)
					table = PrefixGenerator.generateTable(filename);
				PrefixGenerator.trainPrefixTable(table, filename);
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
		Prefix current = table.get("");
		
		do {
			
			Prefix next = current.getRandomSuffix();
			// If it is not the start of a sentence, append a space (to add the spaces between the words)
			if (!current.toString().equals("") && !PrefixGenerator.isPunctuation(next.toString().charAt(0)))
				ret.append(" ");
			
			ret.append(next.toString());
			current = next;
			
			if (current.getNumSuffixes() <= 0)
				break;
			
		} while (!terminators.contains(current.toString()));
		//ret.append(current.toString());
		
		return ret.toString();
	}
	
	public static boolean isTerminator(String c) {
		return terminators.contains(c);
	}
}





