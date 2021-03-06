/**
 * Java class to answer Problem 1 in Assignment 2. Contains 2 inner classes,
 * ListReferenceBased and Node, to assist with demonstrating a Linked List.
 * 
 * @author Kevin Li
 * @version February 7, 2018
 */
public class TestListReferenceBased {
	// a static Linked List that will be used in testing
	private static ListReferenceBased myList;
	
	/**
	 * Main method that demonstrates the linked list using the examples in Assignment 2.
	 * 
	 * @param args default
	 */
	public static void main(String[] args) {
		setup();
		
		// Display the items in the linked list
		displayList();
		
		// Add Integer Object valued 13 at index 0 and display the items in the linked list
		myList.add(new Integer(13), 0);
		displayList();
		
		// Add Integer Object valued 17 at index 2 and display the items in the linked list
		myList.add(new Integer(17), 2);
		displayList();
		
		// Remove Integer Object at index 4 and display the items in the linked list
		myList.remove(4);
		displayList();
	}
	
	/**
	 * Set up the linked list used as a demo on Assignment 2.
	 */
	private static void setup() {
		// initialize the linked list
		myList = new TestListReferenceBased().new ListReferenceBased();
		
		// The list on the Assignment sheet is 12, 3, 25, 18
		// So I add them in that order
		myList.add(new Integer(12),  0);
		myList.add(new Integer(3), 1);
		myList.add(new Integer(25), 2);
		myList.add(new Integer(18), 3);
	}
	
	/**
	 * Print out the integer values in the format on Assignment 2.
	 */
	private static void displayList() {
		// adds # of items and some text to a String
		String output = myList.size() + " Items in the linked list: ";
		for (int i = 0; i < myList.size(); i++) {
			// append the integers to the end of String output
			output += myList.get(i).getValue();
			
			//add a comma and space, except for after the last integer
			if (i != myList.size() - 1)
				output += ", ";
		}
		// print it
		System.out.println(output);
	}
	
	/**
	 * Implementation of a linked list that contains the methods specified in
	 * Assignment 2.
	 * 
	 * @author Kevin Li
	 * @version February 7, 2018
	 */
	private class ListReferenceBased {
		// keeps track of its item count
		private int numItems;
		
		// reference to the first node, which then links to every other one
		private Node head;
		
		/** 
		 * Constructor that initializes an empty linked list.
		 */
		private ListReferenceBased() {
			head = null;
			numItems = 0;
		}
		
		/**
		 * Returns true if the linked list has no nodes, otherwise it returns false.
		 * 
		 * @return true if the linked list is empty (no nodes), false otherwise
		 */
		private boolean isEmpty() {
			return numItems == 0;
		}
		
		/**
		 * Return the number of nodes in the linked list.
		 * 
		 * @return number of nodes
		 */
		private int size() {
			return numItems;
		}
		
		/**
		 * Adds a new node holding the given value at the given index.
		 * 
		 * @param value the value we want the new node to have
		 * @param index the index where we want the new node to be
		 */
		private void add(Integer value, int index) {
			Node curr = head;
			
			// special case where we want the new node to be the first node
			if (index == 0) {
				head = new Node(value);
				head.setNext(curr);
			} else {
				// cycle through nodes until we reach the index we want
				// i < index - 1 because we want curr to be the node before the index
				for (int i = 0; i < index - 1; i++)
					curr = curr.getNext();
				
				// create the new node
				Node temp = new Node(value);
				
				// first set the new node to be referencing the current index
				temp.setNext(curr.getNext());
				
				// then set the index - 1 node to refence the new node
				curr.setNext(temp);
			}
			// account for the new node
			numItems++;
		}
		
		/**
		 * Remove the node at the specified index.
		 * 
		 * @param index The index of the node to be removed.
		 */
		private void remove(int index) {
			// special case where we want to remove the head node
			if (index == 0)
				head = head.getNext();
			else {
				Node curr = head;
				
				// cycle through nodes until we reach the node before the one we want to delete
				for (int i = 0; i < index - 1; i++)
					curr = curr.getNext();
				
				// set the node's next to be the one after the node to delete
				curr.setNext(curr.getNext().getNext());
				
				// the node to be deleted is now marked for garbage collection
			}
			// account for the deleted node
			numItems--;
		}
		
		/**
		 * Return the node at the given index.
		 * 
		 * @param index The index of the node that we want
		 * 
		 * @return a node at the given index
		 */
		private Node get(int index) {
			Node curr = head;
			for (int i = 0; i < index; i++)
				curr = curr.getNext();
			return curr;
		}
		
		/**
		 * Remove all nodes.
		 */
		private void removeAll() {
			// set head to be what head is referencing until head itself is null
			while (head != null)
				head = head.getNext();
			
			// account for the empty list
			numItems = 0;
		}
	}
	
	/**
	 * Node object that holds a reference to the next node and the held Integer object.
	 * 
	 * @author Kevin Li
	 * @version February 7, 2018
	 */
	private class Node {
		// reference to next node
		private Node next;
		
		// held Integer object
		private Integer value;
		
		/** 
		 * Constructor that creates a node with the given Integer object and
		 * a reference to a null node.
		 * 
		 * @param value The Integer object we want the node to hold.
		 */
		private Node(int value) {
			setValue(value);
			setNext(null);
		}
		
		/**
		 * Returns the held Integer object.
		 * 
		 * @return the held Integer object
		 */
		private int getValue() {
			return value;
		}
		
		/**
		 * Return the node that this references.
		 * 
		 * @return the next node
		 */
		private Node getNext() {
			return next;
		}
		
		/** 
		 * Change the value of the held Integer object.
		 * 
		 * @param value The new value that we want the Integer to have
		 */
		private void setValue(int value) {
			this.value = value;
		}
		
		/**
		 * Set the node that this references to the given one.
		 * 
		 * @param next The new node that we want this to reference
		 */
		private void setNext(Node next) {
			this.next = next;
		}
	}
}
