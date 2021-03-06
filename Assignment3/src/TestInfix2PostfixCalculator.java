import java.util.StringTokenizer;

/**
 * Class with a main method to test the given test cases for Infix2PostfixCalculator,
 * which is also implemented in this with the required methods (convertPostFix and getPostFix).
 * Infix2PostfixCalculator converts infix notation expressions into postfix notation expressions
 * and is capable of parsing varying amounts of space between operands and operators,
 * brackets, addition/subtraction, and multiplication/division.
 * 
 * @author Kevin Li
 * @version February 26, 2018
 */
public class TestInfix2PostfixCalculator {
	
	/**
	 * Prints out the test cases in the format presented in the PDF.
	 * 
	 * @param args by default
	 */
	public static void main(String[] args) {
		printResults("(10 + 3 * 4 / 6)");
		printResults("12*3 - 4 + (18/6)");
		printResults("35 - 42* 17 /2 + 10");
		printResults("3 * (4 + 5)");
		printResults("3 * ( 17 - (5+2))/(2+3)");
	}
	
	/** 
	 * Converts the given infix notation expression into a postfix notation expression.
	 * 
	 * @param infix An infix notation expression with no errors and unary operators as a String
	 * @return the equivalent postfix notation expression as a String
	 */
	private static String convertPostFix(String infix) {
		// wipe all spaces, they are undesirable
		infix = infix.replaceAll("\\s", "");
		
		// stack for holding operators
		StackReferenceBased opStack = new StackReferenceBased();
		
		// initialize a char for holding the char at the current index to evaluate
		char curr;
		
		// output of the method, starting off as an empty String
		String output = "";
		
		// int for holding the index
		int i = 0;
		
		// cycle through until i reaches the length of the space-less infix expression
		while (i != infix.length()) {
			// char at current index to analyze
			curr = infix.charAt(i);
			
			// check if the current index's char is not an operator
			if (precedence(curr) == -1) {
				// if not an operator, need to extract the number between operators
				
				// initialize ending index for substring
				int j = i + 1;
				
				//try substring length of 1 starting on index i
				String operand = infix.substring(i, j);
				
				// increment the ending index until either the ending index exceeds the length of the original string
				// of an operator is found at the end of the substring
				while (j != infix.length() && precedence(infix.charAt(j)) == -1) {
					j++;
					operand = infix.substring(i, j);
				}
				
				// update the current index
				i = j;
				
				// append the extracted operand to the output with a space
				output += " " + operand;
 			} else { // it's an operator
 				// increment the index
 				i++;
 				
 				// check for curr being a right bracket
 				// in the case of right bracket, we need to pop and append all operators until a left bracket
				if (curr == ')') {
					
					// pop the stack until a left bracket comes up
					for (Object o = opStack.pop(); o.toString().charAt(0) != '('; o = opStack.pop()) {
						char c = o.toString().charAt(0);
						
						// append the popped operators, but no brackets
						if (precedence(c) != 0)
							output += " " + c;
					}
				} else { // current char is not a right bracket
					
					/* 
					 * Just push the current operator onto the stack if and only if:
					 * 
					 * 		the stack is empty and
					 * 		the current operator is a left bracket and
					 * 		the current operator has higher precedence than the operator on top of the stack
					 * 
					 * otherwise if the current operator has the same or less precedence as the one on top we 
					 * need to pop and append until the top 3 conditions is satisfied, then push the current operator.
					 */
					
					// check for the latter conditions, and pop and append until the top 3 conditions is satisfied
					while (!opStack.isEmpty() && curr != '(' && precedence(opStack.peek().toString().charAt(0)) >= precedence(curr)) {
						char c = opStack.pop().toString().charAt(0);
						
						// don't add brackets
						if (precedence(c) != 0)
							output += " " + c;
					}
					
					// push current operator after passing or skipping the pop and append cycle
					opStack.push(curr);
				}
			}
		}
		
		// done going through the expression
		// just pop and append the remaining operators on the stack
		while (!opStack.isEmpty()) {
			char c = opStack.pop().toString().charAt(0);
			
			// no brackets
			if (precedence(c) != 0)
				output += " " + c;
		}
		
		// remove leading space from the first use of output += " " + something; and return
		return output.trim();
	}
	
	/**
	 * Tokenizes the formatted postfix notation expression, then uses a stack to
	 * evaluate it.
	 * 
	 * @param postfix a formatted postfix notation expression
	 * @return the result of evaluating the expression
	 */
	private static int getPostFix(String postfix) {
		// Tokenize the postfix notation expression, since I made sure there are spaces between everything in the other method
		StringTokenizer tokens = new StringTokenizer(postfix);
		
		// Stack for holding operands and operators
		StackReferenceBased stack = new StackReferenceBased();
		
		// Continue running this until there are no more operands or operators
		while (tokens.hasMoreTokens()) {
			String curr = tokens.nextToken();
			
			// if operator is obtained from tokens, pop the stack twice and operate on them, then push the result on the stack
			// otherwise its an operand, so just push it on the stack
			if (precedence(curr.charAt(0)) != -1)
				stack.push(doMath(stack.pop(), stack.pop(), curr.charAt(0)));
			else
				stack.push(curr);
		}
		
		// the final result is at the top of the stack, so just pop it and make it an int
		return Integer.valueOf(stack.pop().toString());
	}
	
	/**
	 * Helper method for determining which operator to use based on the given
	 * char representation of the operator. Operates on the two given operands.
	 * 
	 * @param a one of the operands
	 * @param b the other operand
	 * @param op char representation of the operator to use
	 * @return the result 
	 */
	private static int doMath(Object a, Object b, char op) {
		// the first popped (a) should be the antecedent operand
		// the second popped (b) should be the precedent operand
		// important for subtraction and division
		int x = Integer.valueOf(b.toString());
		int y = Integer.valueOf(a.toString());
		
		// switch case for different operators
		switch (op) {
			case '+':
				return x + y;
			case '-':
				return x - y;
			case '*':
				return x * y;
			case '/':
				return x / y;
		}
		return -1;
	}
	
	/**
	 * Helper method to determine the precedence of an operator given as a char.
	 * Also capable of determining whether the given char is an operator or not (return -1).
	 * 
	 * @param op the char find the precedence level of
	 * @return -1 if not operator, 0 for brackets, 1 for add/sub, 2 for multiplication/division
	 */
	private static int precedence(char op) {
		if (op == '(' || op == ')')
			return 0;
		if (op == '+' || op == '-')
			return 1;
		if (op == '*' || op == '/')
			return 2;
		return -1;
	}
	
	/**
	 * Prints out the given test cases in the format shown in the PDF.
	 * 
	 * @param infix The original infix string to be transformed into postfix and evaluated
	 */
	private static void printResults(String infix) {
		System.out.println("infix: " + infix);
		System.out.println("postfix: " + convertPostFix(infix));
		System.out.println("result: " + getPostFix(convertPostFix(infix)) + "\n");
	}
}
