package lab3;
import java.util.Iterator;
import java.util.Scanner;

public class CircularLinkedList<E> implements Iterable<E> {
	Node<E> head = null;
	Node<E> tail = null;
	Integer size = 0;

	// Implement a constructor
	public CircularLinkedList(Scanner in) {
		Integer res = null;
		System.out.print("Home many elements should the list have? ");
		try {
			res = in.nextInt();
		} catch (Exception e) {
			System.err.println("Could not read from console: " + e.getMessage());
			System.exit(-1);
		}
		if (res != null) {
			for (Integer i = 1; i <= res; i++) {
				add((E)i);
			}
		}
	}

	// i use this constructor in junit tests, it just instantializes an empty list
	public CircularLinkedList(boolean b) {
	}


	// Return Node<E> found at the specified index
	// Be sure to check for out of bounds cases
	// this function is solely used for internal purposes, which i did not need, so I did not implement this. it would
	// be very easy to use the strategy in add and remove for this though.
	private Node<E> getNode(int index) throws IndexOutOfBoundsException {
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
	public void add(int index, E item) throws IndexOutOfBoundsException {
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
	public void remove(int index) throws IndexOutOfBoundsException {
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
			Iterator<E> it = iterator();
			for (int i = 0; i < size; i++) {
				E item = it.next();
				sb.append(item + "" + delim );
			}
		}
		return sb.toString();
	}

	public Iterator<E> iterator() {
		return new ListIterator<E>();
	}
	
	// This class is not static because it needs to reference its parent class
	private class ListIterator<E> implements Iterator<E> {
		Node<E> nextItem;
		Node<E> prev;
		public int index;
		
		@SuppressWarnings("unchecked")
		// Creates a new iterator that starts at the head of the list
		public ListIterator() {
			nextItem = (Node<E>) tail;
			index = 0;
		}

		// Returns true if there is a next node
		// note that i never use this function as we will always have a next node since the list is circular
		public boolean hasNext() {
			return nextItem.next == null ? false : true;
		}
		
		// Advances the iterator to the next item
		// Should wrap back around to the head
		public E next() {
			if (nextItem == null) {
				return null;
			}
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
			CircularLinkedList.this.remove(index);
			index--;
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
		Scanner in = new Scanner(System.in);
		CircularLinkedList<Integer> cll = new CircularLinkedList<Integer>(in);
		System.out.print("How many soldiers should be counted before killing one? ");
		Integer k = in.nextInt();
		System.out.println();
		Iterator<Integer> it = cll.iterator();
		boolean first = true;
		int round = 0;
		while (cll.size > 2) {
			System.out.println("Round " + ++round);
			Integer toKill = null;
			for (int i = 0; i < k - 1; i++) {
				toKill = it.next();
			}
			if (first) {
				System.out.println("RIP soldier " + String.valueOf(toKill + 1));
				first = false;
			} else {
				System.out.println("RIP soldier " + toKill);
			}
			it.remove();
			it.next();
			System.out.println(cll.toString() + "\n");
		}
		String[] winners = cll.toString().split(" ==> ");
		System.out.println("The winners are " + winners[0] + " and " + winners[1]);
	}



	
	
}
