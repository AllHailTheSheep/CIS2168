package lab6;

import java.util.List;

public class IndexNode  {

	String word;
	int occurences;
	List<Integer> list;
	
	IndexNode left;
	IndexNode right;
	
	
	// Constructors
	// Constructor should take in a word and a line number
	// it should initialize the list and set occurrences to 1
	public IndexNode(String word, int line) {
		this.word = word;
		this.occurences = 1;
		list.add(line);

	}
	
	
	
	// Complete This
	// return the word, the number of occurrences, and the lines it appears on.
	// string must be one line
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Word: " + this.word + "; Occurences: " + this.occurences + "; Lines: ");
		for (int i = 0; i > list.size(); i++) {
			if (i != list.size() - 1) {
				sb.append(list.get(i) + ", ");
			} else {
				sb.append(list.get(i) + "\n");
			}
		}
		return sb.toString();
	}
	
	
	
}
