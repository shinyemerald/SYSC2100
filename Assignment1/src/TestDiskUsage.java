import java.io.File;

/**
 * Java class to complete question 1 in Assignment 1.
 * 
 * @author Kevin Li
 * @version January 18, 2018
 */
public class TestDiskUsage {
	/**
	 * Main method that invokes the recursive method and passes the root directory
	 * 
	 * @param args by default
	 */
	public static void main(String[] args) {
		//start the recursion with the starting directory
		diskUsage(new File("C:\\Users\\Kevin\\Documents\\School\\2nd Year\\Winter 2018\\SYSC 2100\\Assignments\\Assignment1\\SYSC2100"));
	}
	
	/**
	 * Recursive method that goes through the give directory and returns its size in bytes.
	 * Also prints its own size along with its path, and does this with every directory and file within.
	 * 
	 * @param start The directory to go through
	 * @return The size of the directory that is passed in in bytes
	 */
	private static long diskUsage(File start) {
		//list of things in the given directory
		File[] files = start.listFiles();
		
		//size in bytes starts at 0
		long size = 0;
		
		//go through the list of things
		for (File current : files) {
			//check if it's a directory
			if (current.isDirectory()) {
				//if it is a directory, invoke the method with it
				size += diskUsage(current);
			} else { //not a directory so must be a file
				//print out its size and associated path
				System.out.println(current.length() + "\t" + current.getAbsolutePath());
				
				//tack its size to the directory
				size += current.length();
			}
		}
		//print out the directory's size in bytes and its path
		System.out.println(size + "\t" + start.getAbsolutePath());
		
		//return its size (primarily used for recursive calls)
		return size;
	}
}
