public class Board {
    // Atribut Board
    private int rows, cols;
    private char[][] grid;

    // Konstruktor Board
    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new char[rows][cols];

        // Mengisi grid dengan char '.' sebagai tanda kosong
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = '.';
            }
        }
    }

    // Getter: rows, cols, dan grid
    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public char[][] getGrid() {
        return grid;
    }
}
