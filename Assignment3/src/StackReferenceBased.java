/**
 * Stack implementation using references as opposed to an array.
 * Contains an inner class Node to assist with implementation.
 * 
 * @author Kevin Li
 * @version February 26, 2018
 */
public class StackReferenceBased {
	// The node at the top of the stack
	private Node top;
	
	// An int to keep track of the number of items in the stack
	private int items;
	
	/**
	 * Initialize an empty stack (top is null, 0 items).
	 */
	public StackReferenceBased() {
		top = null;
		items = 0;
	}
	
	/**
	 * Pops all items held by the stack and returns an array of objects that 
	 * stack held within its nodes. The 0th index contains top, and the last
	 * index contains the bottom-most item.
	 * 
	 * @return an array of Objects held by the nodes of the stack
	 */
	public Object[] popAll() {
		Object[] output = new Object[items];
		for (int i = 0; i < items; i++)
			output[i] = pop();
		return output;
	}
	
	/**
	 * Returns true if the stack is empty (top is null and no items held).
	 * Otherwise return false.
	 *
	 *@return true if empty stack, false otherwise
	 */
	public boolean isEmpty() {
		return top == null && items == 0;
	}
	
	/**
	 * Pushes the desired data onto the top of the stack.
	 * Increments the number of items in the stack by 1.
	 * 
	 * @param data The desired data to be added to the stack
	 */
	public void push(Object data) {
		Node newTop = new Node(data);
		newTop.setNext(top);
		top = newTop;
		items++;
	}
	
	/**
	 * Returns the topmost item on the stack and removes it from the stack.
	 * Decrements the item count in the stack as well.
	 * 
	 * @return The topmost item in the stack
	 */
	public Object pop() {
		Object output = peek();
		top = top.getNext();
		items--;
		return output;
	}
	
	/**
	 * Returns the topmost item on the stack but doesn't remove it.
	 * 
	 * @return The topmost item in the stack
	 */
	public Object peek() {
		return top.getData();
	}
	
	/**
	 * Inner class to assist with implementing a reference based stack.
	 * Contains just the constructor, 2 mutator methods, and 2 getter methods.
	 * 
	 * @author Kevin Li
	 * @version February 26, 2018
	 *
	 */
	private class Node {
		// The data held by the node, can be String, Integer, Character, Double, anything really
		private Object data;
		
		// Reference to the next node
		private Node next;
		
		/**
		 * Constructor for Node objects. Takes in any object and generates a reference to null for next.
		 * 
		 * @param data The desired data to be held by this node
		 */
		private Node(Object data) {
			this.data = data;
			next = null;
		}
		
		/** 
		 * Return the data held by the node.
		 * 
		 * @return The object held by the node
		 */
		private Object getData() {
			return data;
		}
		
		/**
		 * Return the next node held by this node.
		 * 
		 * @return The next node
		 */
		private Node getNext() {
			return next;
		}
		
		/**
		 * Set this node's held object to the one passed in.
		 * 
		 * @param o The desired object that this node will now hold
		 */
		private void setData(Object o) {
			data = o;
		}
		
		/**
		 * Sets the node that this nodes references.
		 * 
		 * @param n The node that this node is to reference
		 */
		private void setNext(Node n) {
			next = n;
		}
	}
}
