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

			Cell northCell = cellManipulator(cell, 0);
			Cell eastCell = cellManipulator(cell, 1);
			Cell southCell = cellManipulator(cell, 2);
			Cell westCell = cellManipulator(cell, 3);

			if (cell.equals(finish)) {
				finished = true;
			}
			if (northCell != null && !visited(northCell) && !cell.northWall) {
				System.out.println("can move north to " + northCell.row + " " + northCell.col);
				stack.push(northCell);
				cell.setBackground(Color.GRAY);
			} else if (eastCell != null && !visited(eastCell) && !cell.eastWall) {
				System.out.println("can move east to " + eastCell.row + " " + eastCell.col);
				stack.push(eastCell);
				cell.setBackground(Color.GRAY);
			} else if (southCell != null && !visited(southCell) && !cell.southWall) {
				System.out.println("can move south to " + southCell.row + " " + southCell.col);
				stack.push(southCell);
				cell.setBackground(Color.GRAY);
			} else if (westCell != null && !visited(westCell) && !cell.westWall) {
				System.out.println("can move west to " + westCell.row + " " + westCell.col);
				stack.push(westCell);
				cell.setBackground(Color.GRAY);
			} else {
				cell.setBackground(Color.CYAN);
				stack.pop();
			}
		}
	}

	public Cell cellManipulator(Cell c, int dir) {
		if (dir < 0 || dir > 3) {
			System.err.println("Index needs to be between 0 and 3");
			System.exit(-1);
		}
		Cell toReturn = null;
		switch(dir) {
			// north
			case 0: {
				if (isInMaze(c.row - 1, c.col)) {
					toReturn = maze[c.row - 1][c.col];
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
				if (isInMaze(c.row + 1, c.col)) {
					toReturn = maze[c.row + 1][c.col];
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

	public boolean isInMaze(int row, int col) {
		if (row < 0 || row >= 25 || col < 0 || col >= 25) {
			return false;
		} else {
			return true;
		}
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
