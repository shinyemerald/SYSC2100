import java.util.Scanner;
import java.util.LinkedList;
import java.io.File;

/**
 * Java class to complete question 2 in Assignment 1.
 * 
 * @author Kevin Li
 * @version January 18, 2018
 */
public class TestFindPath {
	private static String target;
	private static LinkedList<String> results;
	
	/**
	 * Main method that gets the user to input the initial directory,
	 * target name, and invoke the recursive method. Then it print out
	 * all paths of the target file.
	 * 
	 * @param args by default
	 */
	public static void main(String[] args) {
		//scanner to get user input
		Scanner userInput = new Scanner(System.in);
		
		//get user to enter initial directory to search within
		System.out.println("Enter the path of the root folder:\n");
		String initialDirectory = userInput.nextLine();
		
		//get user to specify the target file
		System.out.println("Enter file name to be found:\n");
		target = userInput.nextLine();
		
		//close the scanner
		userInput.close();
		results = new LinkedList<String>();
		
		//invoke the recursive method and passes the initial directory
		findPath(new File(initialDirectory));
		
		//print out all paths of the that have the target file
		for (String s : results)
			System.out.println(s);
	}
	
	/**
	 * Recursive method that searches through the given directory, invokes itself
	 * again when it comes across another directory, and checks the file if its
	 * name matches the target to add to the results.
	 * 
	 * @param start the directory to search through
	 */
	private static void findPath(File start) {
		//list of things within the given directory
		File[] files = start.listFiles();
		
		//cycle through the files
		for (File current : files) {
			//check if the current file is a directory
			if (current.isDirectory()) {
				//it's another directory so invoke itself to search through that
				findPath(current);
			} else if (current.getName().equals(target)) { //else it's a file, so check if it matches the target
				//it's a match, so we add its path to the results
				results.addFirst("The path of the " + target + " is " + current.getAbsolutePath());
			}
		} //end for each loop
	}
}
