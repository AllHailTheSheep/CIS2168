package lab3;
import java.util.Iterator;

public class CircularLinkedList<E> implements Iterable<E> {
	// Please see questions in CircularLinkedList.iterator() and CircularLinkedList.toString()
	Node<E> head = null;
	Node<E> tail = null;
	Integer size = 0;

	// Implement a constructor
	public CircularLinkedList() {
	}

	// Return Node<E> found at the specified index
	// Be sure to check for out of bounds cases
	private Node<E> getNode(int index) {
		// TODO: finish CircularLinkedList.getNode() function
		if (index >= size || index < 0) {
			throw new IndexOutOfBoundsException("Index " + index + " out of bounds for " + size + " nodes");
		}
		
		

		return null;
	}

	// Add a node to the end of the list
	// HINT: Use the overloaded add method as a helper method
	public void add(E item) {
		Node<E> newNode = new Node<E>(item);
		if (head == null) {
			// if head is null, this element is the first node in the list and is the head
			head = newNode;
		} else {
			// if head is not null, this element not the first in the list and the focus end must be linked to the
			// new tail
			tail.next = newNode;
		}
		// set tail to the new node and tail.next to head so the links are circular
		tail = newNode;
		tail.next = head;
		// increment size
		size++;
	}

	
	// Cases to handle:
	//      Out of bounds
	//      Adding an element to an empty list
	//      Adding an element to the front
	//      Adding an element to the end
	//      Adding an element in the middle
	// HINT: Remember to keep track of the list's size
	public void add(int index, E item) {
		Node<E> newNode = new Node<E>(item);
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Index " + index + " out of bounds for " + size + " nodes");
		}
		if (index == size || head == null) {
			// we already have a function for adding an element to the end and dealing with the first
			add(item);
		} else if (index == 0) {
			// to make a new first element, we need to
			// set the newNode to the tails next,
			// the newNode's next to the focus head,
			// and the newNode as head
			tail.next = newNode;
			newNode.next = head;
			head = newNode;
			size++;
		} else {
			// starting at head, iterate till the focus node is the position, then repeat the process above
			for (int i = 0; i < index; i++) {
				tail = head;
				head = head.next;
			}
			tail.next = newNode;
			newNode.next = head;
			head = newNode;
			// now we have to iterate back to the start so we can accurately add from indexes again
			for (int i = 0; i <= size - index; i++) {
				tail = head;
				head = head.next;
			}
			size++;
		}
	}

	// Cases to handle:
	//      Out of bounds
	//      Removing the last element in the list
	//      Removing the first element in the list
	//      Removing the last element
	//      Removing an element from the middle
	// HINT: Remember to keep track of the list's size
	public void remove(int index) {
		// TODO: add cases for removing head without cycling through the list
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index " + index + " out of bounds for " + size + " nodes");
		}
		if (size == 1) {
			// remove the only element from the list
			head = null;
			tail = null;
			size--;
			return;
		}
		// to delete an element we just cycle through elements keeping track of the last three then link the last to
		// the first (it will be less optimized but we should be able to optimize after we get a prototype) 
		Node<E> beforeTail = null;
		for (int i = 0; i <= index; i++) {
			if (beforeTail == null) {
				beforeTail = tail;
			} else {
				beforeTail = beforeTail.next;
			}
			tail = tail.next;
			head = head.next;
		}
		beforeTail.next = head;
		tail = beforeTail;
		size--;
		// now we have to iterate back to the start so we can accurately remove from indexes again
		for (int i = 0; i < size - index; i++) {
			tail = tail.next;
			head = head.next;
		}
		return;
	}
	
	// Stringify your list
	// Cases to handle:
	//      Empty list
	//      Single element list
	//      Multi-element list
	// Use "==>" to delimit each node
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String delim = " ==> ";
		if (size == 0) {
			sb.append("empty list");
		} else if (size == 1) {
			sb.append(head + delim);
		} else {
			ListIterator<E> it = new ListIterator<E>();
			for (int i = 0; i < size; i++) {
				E item = it.next();
				sb.append(item + "" + delim );
			}
		}
		return sb.toString();
	}

	public Iterator<E> iterator() {
		/** Question: Why does this function exist? I defined my own iterators because I couldn't really
		 * figure out why and how to use this one. The only difference as I can tell is that this one casts ListIterator
		 * to an Iterator. Why should we do that instead of using the iterator we've defined that implements Iterator?
		 * Just wondering the functional difference. Thanks!
		*/
		return new ListIterator<E>();
	}
	
	// This class is not static because it needs to reference its parent class
	private class ListIterator<E> implements Iterator<E> {
		Node<E> nextItem;
		Node<E> prev;
		int index;
		
		@SuppressWarnings("unchecked")
		// Creates a new iterator that starts at the head of the list
		public ListIterator() {
			nextItem = (Node<E>) tail;
			index = 0;
		}

		// Returns true if there is a next node
		public boolean hasNext() {
			return nextItem.next == null ? false : true;
		}
		
		// Advances the iterator to the next item
		// Should wrap back around to the head
		public E next() {
			// TODO: test thsi function
			prev = nextItem;
			nextItem = nextItem.next;
			if (index + 1 == size) {
				index = 0;
			} else {
				index++;
			}
			return nextItem.item;
		}
		
		// Remove the last node visted by the .next() call
		// For example, if we had just created an iterator,
		// the following calls would remove the item at index 1 (the second person in the ring)
		// next() next() remove()
		// Use CircularLinkedList.this to reference the CircularLinkedList parent class
		public void remove() {
			// TODO: test if this works as intended
			CircularLinkedList.this.remove(index);
		}
		
	}
	
	// The Node class
	private static class Node<E>{
		E item;
		Node<E> next = null;
		
		public Node(E item) {
			this.item = item;
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(item);
			return sb.toString();
		}
		
	}
	
	public static void main(String[] args){
		// TODO: jesus christ bro just make some fucking unit tests
		CircularLinkedList<Integer> cll = new CircularLinkedList<Integer>();
		System.out.println(cll.toString()); // empty list
		cll.add(0, 0);			 
		System.out.println(cll.toString()); // 0
		cll.remove(0);
		System.out.println(cll.toString()); // empty list
		cll.add(0, 0);
		System.out.println(cll.toString()); // 0
		cll.add(1);
		System.out.println(cll.toString()); // 0 ==> 1
		cll.add(2, 2);
		System.out.println(cll.toString()); //  0 ==> 1 ==> 2 ==>
		cll.add(2, 999);
		System.out.println(cll.toString()); // 0 ==> 1 ==> 999 ==> 2 ==>
		cll.remove(2);
		System.out.println(cll.toString()); // 0 ==> 1 ==> 2
		cll.remove(1);
		System.out.println(cll.toString()); // 0 ==> 2 ==>
		cll.add(1, 1);
		System.out.println(cll.toString()); // 0 ==> 1 ==> 2 ==>
		// TODO: do the assignment
		
		
		
		
		
		
	}



	
	
}
