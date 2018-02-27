import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Java class to answer Problem 2 in Assignment 2. Contains all three supplemented helper methods
 * and a main method that compares the time required to brute-force-search for a specified pattern
 * between LinkedLists and ArrayLists.
 * 
 * @author Kevin Li
 * @version February 7, 2018
 */
public class CountSubstrings {
	/**
	 * Main method that gets inputs from the user for the file to look through and the
	 * pattern to find, then brute-force-searches the file for the pattern using ArrayLists
	 * and LinkedLists representation of the pattern and compares the times.
	 * 
	 * @param args default
	 */
	public static void main(String[] args) {
		// grab desired file and pattern
		String fileName = openFile();
		String pattern = getPattern();
		
		/* Using ArrayLists */
		// get starting time
		long startTimeArray = System.currentTimeMillis();
		
		// initialize its count to 0
		int arrayCount = 0;
		
		// initialize 2 ArrayLists, one for the pattern, one for the placeholder
		ArrayList<Character> patternArray = new ArrayList<Character>();
		ArrayList<Character> placeholderArray = new ArrayList<Character>();
		
		// convert the pattern into an ArrayList of char
		convertStringToList(pattern, patternArray);
		
		// get the count
		try {
			arrayCount = readAndMatchDocument(fileName, patternArray, placeholderArray);
		} catch (FileNotFoundException e) {} // exceptions already handled
		
		// get final time
		long finalTimeArray = System.currentTimeMillis() - startTimeArray;
		
		
		/* Using LinkedLists */
		// get starting time
		long startTimeLinked = System.currentTimeMillis();
		
		// initialize its count to 0
		int linkedCount = 0;
		
		// initialize 2 LinkedLists, one for the pattern, one for the placeholder
		LinkedList<Character> patternLinked = new LinkedList<Character>();
		LinkedList<Character> placeholderLinked = new LinkedList<Character>();
		
		// convert the pattern into a LinkedLIst of char
		convertStringToList(pattern, patternLinked);
		
		// get the count
		try {
			linkedCount = readAndMatchDocument(fileName, patternLinked, placeholderLinked);
		} catch (FileNotFoundException e) {} // exceptions already handled
		
		// get final time
		long finalTimeLinked = System.currentTimeMillis() - startTimeLinked;
		
		// print the results as shown on the Assignment sheet
		System.out.println("Using ArrayLists: " + arrayCount + " matches, derived in " + finalTimeArray + " milliseconds.");
		System.out.println("Using LinkedLists: " + linkedCount + " matches, derived in " + finalTimeLinked + " milliseconds.");
	}
	
	/**
	 * Returns the lowest index at which substring pattern begins in text (or else -1).
	 * 
	 * @param text The text which we want to search through
	 * @param pattern The pattern we want to find in text
	 * @return the index if exists, otherwise -1
	 */
	private static int findBrute(List<Character> text, List<Character> pattern) {
		int n = text.size();
		int m = pattern.size();
		for (int i = 0; i <= n - m; i++) { // try every starting index within text
			int k = 0; // k is index into pattern
			while (k < m && text.get(i + k) == pattern.get(k)) // kth character of pattern matches
				k++;
			if (k == m) // if we reach the end of the pattern,
				return i; // substring text[i--i+m-1] is a match
		}
		return -1; // search failed
	}
	
	/**
	 * Repeatedly prompt user for filename until a file with such a name exists and
	 * can be opened.
	 * 
	 * @return a file path that exists and can be opened
	 */
	private static String openFile() {
		BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in));
		String inFilePath = "";
		BufferedReader inFileReader;
		boolean pathsOK = false;
		while (!pathsOK) {
			try {
				System.out.print("Please enter the path for the input file: ");
				inFilePath = keyboardReader.readLine();
				inFileReader = new BufferedReader(new FileReader(inFilePath));
				pathsOK = true;
				inFileReader.close();
			} // try
			catch (IOException e) {
				System.out.println(e);
			} // catch I/O exception
		} // while
		return inFilePath;
	} // method openFiles
	
	/**
	 * Prompts user repeatedly until a proper string is entered, to be used as the
	 * pattern to find in the file.
	 * 
	 * @return the desired pattern
	 */
	private static String getPattern() {
		Scanner userInput = new Scanner(System.in);
		String pattern = "";
		boolean goodInput = false;
		System.out.println("Enter pattern to look for: ");
		while (!goodInput) {
			pattern = userInput.nextLine();
			// there's really no way of getting bad input, but i'll include this anyway
			if (!pattern.equals(null))
				goodInput = true;
			else
				System.out.println("Pattern cannot be null.\nEnter pattern to look for: ");
		}
		userInput.close();
		return pattern;
	}
	
	/**
	 * Helper method to convert a string to a List. Loops over all characters in
	 * the string and may not be all that efficient - may be better to read in
	 * the file character by character until we hit whitespace.
	 * 
	 * @param in The string to convert into a List
	 * @param out The resultant List converted from a String
	 */
	private static void convertStringToList(String in, List<Character> out) {
		char[] input_chars = in.toCharArray();
		out.clear();
		for (int i = 0; i < input_chars.length; i++)
			out.add(input_chars[i]);
	}
	
	/**
	 * Iterate over all strings in input file to determine whether the input
	 * string is a substring in any of these strings. Returns the number of
	 * times such a match exists.
	 * 
	 * @param filename The file we want to search through
	 * @param pattern The pattern we're looking for
	 * @param Input placeholder list (will be cleared every time a new string is used)
	 * @return number of instances where the pattern shows up in the given file
	 * @throws FileNotFoundException
	 */
	public static int readAndMatchDocument(String filename, List<Character> pattern, List<Character> Input) throws FileNotFoundException {
		StringTokenizer tokens;
		String line, textword;
		int count = 0;
		// open file anew to ensure we start at the first character
		BufferedReader inFileReader = new BufferedReader(new FileReader(filename));
		try {
			while (true) {
				line = inFileReader.readLine();
				if (line == null)
					break;
				tokens = new StringTokenizer(line);
				// for all the words in the line
				while (tokens.hasMoreTokens()) {
					textword = tokens.nextToken();
					convertStringToList(textword, Input);
					if (findBrute(Input, pattern) != -1)
						count = count + 1;
				} // end while tokens
			} // end while true
		} catch (IOException e) {
			System.out.println(e);
		} // catch I/O exception}
		try {
			inFileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
	}
}
