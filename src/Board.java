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

    /* Method untuk memeriksa constraint penyimpanan Piece pada Board:
        1. Tidak menimpa piece lain
        2. Tidak keluar dari Board atau IndexOutOfBounds */ 
    public boolean canPlacePiece(Piece piece, int row, int col) {
        // Dimensi Piece
        char[][] pieceShape = piece.getShape();
        int pieceRow = pieceShape.length;
        int pieceCol = pieceShape[0].length;

        // Pengecekan
        for (int i = 0; i < pieceRow; i++) {
            for (int j = 0; j < pieceCol; j++) {
                // Tidak keluar dari Board atau 
                if ((row + i > rows) || (col + j > cols)) {
                    return false;
                }
                // Tidak menimpa piece lain
                if ((pieceShape[i][j] != '.') && (grid[row + i][col + j] != '.')) {
                    return false;
                }
            }
        }
        return true;
    }
    
    // Method untuk menyimpan Piece pada Board
    public void placePiece(Piece piece, int row, int col) {
        // Dimensi Piece
        char[][] pieceShape = piece.getShape();
        int pieceRow = pieceShape.length;
        int pieceCol = pieceShape[0].length;

        // Menempatkan piece
        for (int i = 0; i < pieceRow; i++) {
            for (int j = 0; j < pieceCol; j++) {
                if (pieceShape[i][j] != '.') {
                    grid[row + i][col + j] = pieceShape[i][j];
                }
            }
        }
    }

    // Method untuk menghapus Piece pada Board
    public void removePiece(Piece piece, int row, int col) {
        // Dimensi Piece
        char[][] pieceShape = piece.getShape();
        int pieceRow = pieceShape.length;
        int pieceCol = pieceShape[0].length;

        // Menghapus piece
        for (int i = 0; i < pieceRow; i++) {
            for (int j = 0; j < pieceCol; j++) {
                if (pieceShape[i][j] != '.') {
                    grid[row + i][col + j] = '.';
                }
            }
        }
    }

    // Method untuk mengecek apakah Board sudah penuh atau belum
    public boolean isFull() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; i < cols; j++) {
                if (grid[i][j] == '.') {
                    return false;
                }
            }
        }
        return true;
    }

    // Method untuk print Board ke terminal dengan warna
    public void printBoard() {
        
    }

}
