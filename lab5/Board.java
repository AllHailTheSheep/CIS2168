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

    public Board(int s, int[][] init_board) {
        SIZE = s;
        try {
            int test = init_board[SIZE - 1][SIZE - 1];
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Argument size must be SIZE x SIZE!", e);
        };
        board = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = init_board[i][j];
            }
        }
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
        Integer longest = Integer.MIN_VALUE;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Integer temp = String.valueOf(board[i][j]).length();
                if (temp > longest) {
                    longest = temp;
                }
            }
        }
        // TODO: finish this using longest as spacer
        for (int i = 0; i < SIZE; i ++) {
            for (int j = 0; j < SIZE; j++) {
                String temp = String.valueOf(board[i][j]);
                sb.append(temp);
                for (int k = 0; k < longest - temp.length() + 2; k++) {
                    sb.append(" ");
                }
            }
            sb.append("\n");
        }
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
