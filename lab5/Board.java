package lab5;

public class Board {
    private int SIZE;
    private int[][] board = null;

    public Board(int s) {
        if (s <= 0) {
            throw new IllegalArgumentException("Size must be greater than zero!");
        }
        this.SIZE = s;
        boardInitializer();
    }

    private void boardInitializer() {
        board = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = -1;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SIZE; i ++) {
            for (int j = 0; j < SIZE; j++) {
                sb.append(String.valueOf(board[i][j]));
                for (int space = 0; space < 3 - String.valueOf(board[i][j]).length(); space++) {
                    sb.append(" ");
                }
            }
            sb.append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }

    public int getSize() {
        return this.SIZE;
    }

    public void setAtPos(int value, int row, int col) {
        if (!validate(row) || !validate(col)) {
            throw new IllegalArgumentException("Invalid position, row and col must both be nonnegative and less than than SIZE = " + String.valueOf(SIZE));
        }
        board[row][col] = value;
    }

    public int getAtPos(int row, int col) {
        return this.board[row][col];
    }

    private boolean validate(int x) {
        if (x < 0 || x >= SIZE) {
            return false;
        }
        return true;
    }
}
