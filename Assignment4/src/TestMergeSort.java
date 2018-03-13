
/**
 * Java class to answer Q1 of Assignment 4, which calls for an implementation of a merge sort algorithm.
 * 
 * @author Kevin Li
 * @version March 12, 2018
 */
public class TestMergeSort {
	
	/**
	 * Main method to run mergesort() with the example String array.
	 * 
	 * @param args default
	 */
	public static void main(String[] args) {
		// example array in the assignment specifications
		String[] test1 = {"Lisa", "Adam", "John", "Vicky", "George", "Beth", "Kate", "Aaron", "Jinny"};
		
		// print the initial array
		printMe(test1);
		
		// sort the example array using merge sort
		test1 = mergesort(test1);
		
		// print the sorted array to see the results
		printMe(test1);
	}
	
	/** 
	 * The recursive merge sort method that sorts a String array.
	 * 
	 * Base case: the array passed in is of size 0 or 1, just return it
	 * 
	 * If not base case, create 2 arrays half the size of the original one, then copy the original one's contents
	 * into the halves and sort the halves' contents by calling mergesort() on itself. When the arrays reach the base
	 * case, we compare adjacent arrays, starting with arrays of size 1, then size 2, then size 4 and so on, and
	 * put them in order until we obtain the sorted original array.
	 * 
	 * @param theArray The String array to be sorted using a recursive merge sort algorithm
	 * @return the sorted array
	 */
	public static String[] mergesort(String[] theArray) {
		// return the array untouched if the length is 1 or 0
		if (theArray.length < 2) return theArray;
		
		// create 2 arrays with half the length of the original one
		String[] front = new String[theArray.length % 2 == 0 ? theArray.length / 2 : theArray.length / 2 + 1];
		String[] back = new String[theArray.length - front.length];
		
		// copy the values into the halves
		for (int i = 0; i < front.length; i++) front[i] = theArray[i];
		for (int i = 0; i < back.length; i++) back[i] = theArray[front.length + i];
		
		// sort the halves recursively
		front = mergesort(front);
		back = mergesort(back);
		
		// keep track of the index of each half
		int frontIndex = 0;
		int backIndex = 0;
		
		// compare the front of each half array and copy the lesser one into the original array
		for (int i = 0; i < theArray.length; i++) {
			
			// check if we've already gone through all of the front half
			if (frontIndex < front.length) {
				
				// there's still things in the front half, check if we've already gone through all of the back half
				if (backIndex < back.length) {
					
					// we're in the middle of either half, so compare the strings at the current indices
					// copy the string with the first non-same character that's closest to 'a' and advance the respective half's index
					if (compareStrings(front[frontIndex], back[backIndex]))
						theArray[i] = front[frontIndex++];
					else
						theArray[i] = back[backIndex++];
				} else  // only the front half remains
					theArray[i] = front[frontIndex++];
			} else // only the back half remains
				theArray[i] = back[backIndex++];
		}
		
		// return the sorted array
		return theArray;
	}
	
	/**
	 * Compares String a to b, returns true if the first non-same
	 * character of a is closer to 'a' than b. Otherwise (i.e. the
	 * first non-same character of b is closer to 'a') return false;
	 * 
	 * @param a One of the string to compare with
	 * @param b The other string to compare with
	 * @return true if the first non-same character of a is closer to 'a' than b
	 */
	private static boolean compareStrings(String a, String b) {
		// initially assume that the first character of a is closer to 'a' than b is
		boolean flag = true;
		
		// ignore case
		a = a.toLowerCase();
		b = b.toLowerCase();
		
		// check index i of a and b to see if they are the same until we reach either the end of a or b
		// if they are not same, determine which is closer to 'a' and then break out of loop
		for (int i = 0; i < (a.length() > b.length() ? b.length() : a.length()); i++) {
			if (a.charAt(i) != b.charAt(i)) {
				if (a.charAt(i) > b.charAt(i))
					flag = false;
				break;
			} // else we need to check the next char, so continue iteration
		}
		return flag;
	}
	
	/**
	 * Prints the given String array with spaces between each element.
	 * 
	 * @param theArray The String array to be printed
	 */
	private static void printMe(String[] theArray) {
		String str = "";
		for (int i = 0; i < theArray.length; i++) str += theArray[i] + " ";
		System.out.println(str.trim()); // trims off the space at the end, not exactly necessary but I included it anyway
	}
}
