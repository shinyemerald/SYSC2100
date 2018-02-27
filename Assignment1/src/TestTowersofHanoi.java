/**
 * Java class to complete question 3 in Assignment 1.
 * 
 * @author Kevin Li
 * @version January 24, 2018
 */
public class TestTowersofHanoi {
	//static variable to keep track because couldn't figure out how to implement non-static
	private static int instructionNumber;
	
	/**
	 * Main method. Starts the recursive method.
	 * 
	 * @param args by default
	 */
	public static void main(String[] args) {
		//instruction number starts at 0 because it gets incremented in the recursive method
		instructionNumber = 0;
		
		solveTowers(3, 'L', 'R', 'M');
	}
	
	/**
	 * The recursive method that calls itself when it needs to move a smaller disk.
	 * 
	 * @param n The disk we intend to move with the method call
	 * @param source The starting pole that the disk comes from
	 * @param destination The pole that we intend to place the disk on
	 * @param spare The remaining pole
	 */
	private static void solveTowers(int n, char source, char destination, char spare) {
		//don't do anything if we're moving disk 0
		if (n == 0) return;
		
		/* In order to move disk n from the source to the destination, we need to move disk n - 1 to the spare.
		 * So we call the method again, with n - 1 and the destination set to the spare tower.
		 */
		solveTowers(n - 1, source, spare, destination);
		
		/* Increment instruction number when ready to print an instruction.
		 * Print the instruction that moves disk n from source to destination.
		 */
		instructionNumber++;
		System.out.println(instructionNumber + ". Move disk " + n + " from " + source + " to " + destination);
		
		/*
		 * After the n - 1 disk was moved from the original source to the original spare,
		 * we want to move it on top of disk n at the original destination.
		 */
		solveTowers(n - 1, spare, destination, source);
	}
}
