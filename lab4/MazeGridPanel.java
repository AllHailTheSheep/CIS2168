package lab4;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class MazeGridPanel extends JPanel {
	// blue: visited
	// white: unvisited
	// red: exit
	private int rows;
	private int cols;
	private Cell[][] maze;

	// Extra credit
	public void generateMazeByDFS() {
		boolean[][] visited;
		Stack<Cell> stack = new Stack<Cell>();
		Cell start = maze[0][0];
		stack.push(start);
		
	}

	

	public void solveMaze() {
		Stack<Cell> stack = new Stack<Cell>();
		Cell start = maze[0][0];
		start.setBackground(Color.GREEN);
		Cell finish = maze[rows - 1][cols - 1];
		finish.setBackground(Color.RED);
		stack.push(start);

		// Implement your algorithm here
		boolean finished = false;
		while (!finished && !stack.isEmpty()) {
			Cell cell = stack.peek();
			boolean moved = false;
			for (int i = 0; i < 4; i++) {
				Cell focus = cellManipulator(cell, i);
				if (focus == null) {
					continue;
				} else if (canMove(cell, i) && visited(focus)) {
					stack.push(focus);
					focus.setBackground(Color.BLUE);
					moved = true;
				}
			}
			if (!moved) {
				cell.setBackground(Color.BLACK);
				stack.pop();
			}
		}
	}

	private Cell cellManipulator(Cell c, int dir) {
		if (dir < 0 || dir > 3) {
			System.err.println("Index needs to be between 0 and 3");
			System.exit(-1);
		}
		System.out.println(c.row + " " + c.col + " " + dir);
		Cell toReturn = null;
		switch(dir) {
			// north
			case 0: {
				if (isInMaze(c.row + 1, c.col)) {
					toReturn = maze[c.row + 1][c.col];
				}
				break;
			}
			// east
			case 1: {
				if (isInMaze(c.row, c.col + 1)) {
					toReturn = maze[c.row][c.col + 1];
				}
				break;
			}
			// south
			case 2: {
				if (isInMaze(c.row - 1, c.col)) {
					toReturn = maze[c.row - 1][c.col];
				}
				break;
			}
			// west
			case 3: {
				if (isInMaze(c.row, c.col - 1)) {
					toReturn = maze[c.row][c.col - 1];
				}
				break;
			}
		}
		return toReturn;

	}

	static boolean isInMaze(int row, int col) {
		if (row < 0 || row >= 25 || col < 0 || col >= 25) {
			return false;
		} else {
			return true;
		}
	}

	private boolean canMove(Cell c, int dir) {
		if (dir < 0 || dir > 3) {
			System.err.println("Index needs to be between 0 and 3");
			System.exit(-1);
		}
		switch(dir) {
			// north
			case 0: {
				Cell focus = maze[c.row + 1][c.col];
				if (!visited(focus)) {
					return true;
				}
				break;
			}
			// east
			case 1: {
				Cell focus = maze[c.row][c.col + 1];
				if (!visited(focus)) {
					return true;
				}
				break;
			}
			// south
			case 2: {
				Cell focus = maze[c.row - 1][c.col];
				if (!visited(focus)) {
					return true;
				}
				break;
			}
			// west
			case 3: {
				Cell focus = maze[c.row][c.col - 1];
				if (!visited(focus)) {
					return true;
				}
				break;
			}
		}
		return false;
	}


	public boolean visited(int row, int col) {
		Cell c = maze[row][col];
		Color status = c.getBackground();
		if (status.equals(Color.WHITE) || status.equals(Color.RED)) {
			return false;
		}

		return true;
	}

	public boolean visited(Cell c) {
		Color status = c.getBackground();
		if (status.equals(Color.WHITE) || status.equals(Color.RED)) {
			return false;
		}

		return true;
	}

	public void generateMaze() {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {

				if (row == 0 && col == 0) {
					continue;
				} else if (row == 0) {
					maze[row][col].westWall = false;
					maze[row][col - 1].eastWall = false;
				} else if (col == 0) {
					maze[row][col].northWall = false;
					maze[row - 1][col].southWall = false;
				} else {
					boolean north = Math.random() < 0.5;
					if (north) {
						maze[row][col].northWall = false;
						maze[row - 1][col].southWall = false;
					} else {
						maze[row][col].westWall = false;
						maze[row][col - 1].eastWall = false;
					}
					maze[row][col].repaint();
				}
			}
		}

		this.repaint();
	}

	public MazeGridPanel(int rows, int cols) {
		this.setPreferredSize(new Dimension(800, 800));
		this.rows = rows;
		this.cols = cols;
		this.setLayout(new GridLayout(rows, cols));
		this.maze = new Cell[rows][cols];
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				maze[row][col] = new Cell(row, col);
				this.add(maze[row][col]);
			}
		}

		this.generateMaze();
		this.solveMaze();
	}
}
