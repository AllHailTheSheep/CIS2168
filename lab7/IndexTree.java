package lab7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


// Your class. Notice how it has no generics.
// This is because we use generics when we have no idea what kind of data we are getting
// Here we know we are getting two pieces of data:  a string and a line number
public class IndexTree {

	// This is your root
	// again, your root does not use generics because you know your nodes
	// hold strings, an int, and a list of integers
	IndexNode root;
	private IndexTree left;
	private IndexTree right;

	// Make your constructor
	// It doesn't need to do anything
	public IndexTree() {
		this.root = null;
		this.left = null;
		this.right = null;
	}

	public IndexTree(String root, int line) {
		this.root = new IndexNode(root, line);
		this.left = null;
		this.right = null;
	}

	// complete the methods below

	// if root is null, then we make a new root
	// if the owrd to be inserted is less than the current root, insert in the left
	// tree;
	// if greater, in the in the right, creating either if they dont exist and
	// incrementing the occurence if equal
	// making this return a boolean makes it easy to debug
	// it looks ugly but its not really that bad and it should make intuitive since
	public boolean add(String word, int lineNumber) {
		if (this.root == null) {
			// success case: node added to root
			this.root = new IndexNode(word, lineNumber);
			return true;
		} else {
			if (this.root.word.compareTo(word) < 0) {
				// success case: node added on left
				if (this.left != null) {
					this.left.add(word, lineNumber);
				} else {
					this.left = new IndexTree(word, lineNumber);
				}
				return true;
			} else if (this.root.word.compareTo(word) > 0) {
				// success case: node added on right
				if (this.right != null) {
					this.right.add(word, lineNumber);
				} else {
					this.right = new IndexTree(word, lineNumber);
				}
				return true;
			} else {
				// success case: occurence added to node that already exists
				this.root.addOccurence(lineNumber);
				return true;
			}
		}
	}

	// returns true if the word is in the index
	public boolean contains(String word) {
		if (this.root.word.compareTo(word) == 0) {
			// success case: the word is the current root
			return true;
		} else {
			// if word would occur on left, check if left is null. if is isnt, check that
			// branch, if it is, return false.
			if (this.root.word.compareTo(word) < 0) {
				if (this.left != null) {
					return this.left.contains(word);
				} else {
					return false;
				}
			}
			// if word would occur on right, check if right is null. if is isnt, check that
			// branch, if it is, return false.
			if (this.root.word.compareTo(word) > 0) {
				if (this.right != null) {
					return this.right.contains(word);
				} else {
					return false;
				}
			}
		}
		return false;
	}

	// call your recursive method
	// use book as guide
	public boolean delete(String word) {
		// System.out.println("Deleting " + word);
		return delete(word, this, null);
	}
	public boolean delete(String word, IndexTree parent, IndexTree abs_root) throws IllegalArgumentException {
		if (abs_root == null) {
			abs_root = parent;
		}
		if (!this.contains(word)) {
			throw new IllegalArgumentException("Cannot delete " + word + " because it is not in the index.");
		}
		// if word is current root start deletion process
		if (this.root.word.compareTo(word) == 0) {
			// check if node has children, if so, save the children, delete the node, then re-add the children
			if (this.left == null && this.right == null) {
				// no children, delete the node by removing the current root and setting the parents directional child to null
				if (parent.left.contains(word)) {
					parent.left = null;
				} else if (parent.right.contains(word)) {
					parent.right = null;
				}
				this.root = null;
				return true;
			} else {
				// save children, delete the node by removing the current root and setting the parents directional child, and re-add all children to root param
				LinkedList<IndexNode> children = this.getChildren();
				children.remove(this.root);
				if (parent.left != null && parent.left.contains(word)) {
					parent.left = null;
				} else if (parent.right != null && parent.right.contains(word)) {
					parent.right = null;	
				}
				this.root = null;
				for (IndexNode child : children) {
					// re-add all children to the first parent
					if (abs_root.root != null) {
						for (int i = 0; i < child.occurences; i++) {
							// System.out.println("Adding child " + child + " occurence on line " + child.list.get(i));
							abs_root.add(child.word, child.list.get(i));
						}
					} else {
						// TODO: add support for deleting the absolute root of the tree
						throw new IllegalArgumentException("Please do not remove the absolute root. It makes the developer very angry.");
					}
				}
				return true;
			}
		}
		// if the word occurs on left run delete on that tree
		if (this.root.word.compareTo(word) < 0) {
			return this.left.delete(word, this, parent);
		}
		// if word would occurr on right run delete on that tree
		if (this.root.word.compareTo(word) > 0) {
			return this.right.delete(word, this, parent);
		}
		return false;
	}

	public LinkedList<IndexNode> getChildren() {
		LinkedList<IndexNode> children = new LinkedList<IndexNode>();
		if (this.left == null && this.right == null) {
			children.add(this.root);
			return children;
		} else if (this.left != null) {
			LinkedList<IndexNode> temp = this.left.getChildren();
			temp.add(this.root);
			for (IndexNode child : temp) {
				children.add(child);
			}
		} else if (this.right != null) {
			LinkedList<IndexNode> temp = this.right.getChildren();
			temp.add(this.root);
			for (IndexNode child : temp) {
				children.add(child);
			}
		}
		return children;
	}

	// your recursive case
	// remove the word and all the entries for the word
	// This should be no different than the regular technique.
	// private IndexNode delete(IndexNode root, String word) {
	// return null;
	// }

	// prints all the words in the index in inorder order
	// To successfully print it out
	// this should print out each word followed by the number of occurrences and the
	// list of all occurrences
	// each word and its data gets its own line
	private static Logger log = LogManager.getLogger();
	public void printIndex() {
		if (this.left != null) {
			this.left.printIndex();
		}
		log.info(this.root.toString());
		System.out.println(this.root.toString());
		if (this.right != null) {
			this.right.printIndex();
		}
	}

	public static void main(String[] args) {
		
		IndexTree index = new IndexTree();

		// add all the words to the tree
		try {
			Scanner in = new Scanner(new File("lab7/shakespeare.txt"));
			int lines = 1;
			while (in.hasNextLine()) {
				String line = in.nextLine();
				// remove everything not a character, number, newline, space, or hyphen
				line = line.replaceAll("[^a-zA-Z0-9' \n-]+", "");
				// replace all sets of space with one space
				line = line.replaceAll("\\s+", " ");
				// remove all hyphens not inbetween two characters (we will run it again after removing erraneous apostrophes)
				line = line.replaceAll("(\\b-\\B)|(\\B-\\b)", "");
				// remove all apostrophes not in between two characters
				line = line.replaceAll("(\\b'\\B)|(\\B'\\b)", "");
				line = line.replaceAll("(\\b-\\B)|(\\B-\\b)", "");
				String[] words = line.toLowerCase().split(" ");
				for (String word : words) {
					index.add(word, lines);
				}
				lines++;
			}
		} catch (FileNotFoundException e) {
			System.err.println("File not found.");
			e.printStackTrace();
			System.exit(1);
		}

		// this gets hard to look at, so i prefer looking at the log4j file, the only thing it contains is this output
		index.printIndex();

		// test removing a word from the index
		index.delete("north");
		System.out.println(index.contains("north")); // false
		System.out.println(index.contains("")); // true for some reason?

		// this will not work because removing the root is hard and "the" is the first word in the text file
		index.delete("the");
	}

}
