package lab5.Sudoku;

import lab5.Board;

public class Sudoku {
    public static void main(String[] args) {
        // this is a puzzle intentionally designed to work against backtracking algorithms, so give it time
        int[][] startBoard = {{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                              { 0, 0, 0, 0, 0, 3, 0, 8, 5 },
                              { 0, 0, 1, 0, 2, 0, 0, 0, 0 },
                              { 0, 0, 0, 5, 0, 7, 0, 0, 0 },
                              { 0, 0, 4, 0, 0, 0, 1, 0, 0 },
                              { 0, 9, 0, 0, 0, 0, 0, 0, 0 },
                              { 5, 0, 0, 0, 0, 0, 0, 7, 3 },
                              { 0, 0, 2, 0, 1, 0, 0, 0, 0 },
                              { 0, 0, 0, 0, 4, 0, 0, 0, 9 }};
        Board b = new Board(9, startBoard);
        if (solve(b, 0, 0)) {
            System.out.println(b.toString());
        } else {
            System.out.println("No solution found.");
        }
    }

    public static boolean solve(Board b, int r, int c) {
        int boardSize = b.getSize();

        // if algorithm would move to r=8 c=9 we are done
        if (r == boardSize - 1 && c == boardSize) {
            return true;
        }


        // if c would be 9 we need to increment row and set c to zero
        if (c == boardSize) {
            c = 0;
            r++;
        }

        // if current pos is not zero, move to the next pos
        if (b.getAtPos(r, c) > 0) {
            return solve(b, r, c + 1);
        }

        // try different numbers
        for (int i = 1; i <= 9; i++) {
            // if the number is safe, set it and move to the next pos
            if (isSafe(b, i, r, c)) {
                b.setAtPos(i, r, c);
                if (solve(b, r, c + 1)) {
                    return true;
                }
            }
            // remove value if it did not lead to a solution
            b.setAtPos(0, r, c);
        }
        return false;
    }

    public static boolean isSafe(Board b, int v, int r, int c) {
        // check within same column
        for (int i = 0; i <= 8; i++) {
            if (b.getAtPos(i, c) == v) {
                return false;
            }
        }

        // check within same row
        for (int i = 0; i <= 8; i++) {
            if (b.getAtPos(r, i) == v) {
                return false;
            }
        }

        // check in 3x3 matrix
        int rConst = r - r % 3;
        int cConst = c - c % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (b.getAtPos(i + rConst, j + cConst) == v) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isValid(int size, int r, int c) {
        return r >= size && c >= size ? true : false;
    }
}
