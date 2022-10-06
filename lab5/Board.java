package lab5;

public class Board {
    private int rows;
    private int columns;
    private int[][] board = null;

    public Board(int rows, int columns) {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException("Rows and columns must be greater than zero!");
        }
        this.rows = rows;
        this.columns = columns;
        boardInitializer();
    }

    private void boardInitializer() {
        board = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board[i][j] = 0;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i ++) {
            for (int j = 0; j < columns; j++) {
                sb.append(String.valueOf(board[i][j]));
                sb.append("  ");
            }
            sb.append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }

    public int getRows() {
        return this.rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return this.columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getAtPos(int row, int column) {
        return this.board[row][column];
    }
}
