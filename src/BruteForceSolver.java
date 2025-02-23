import java.util.List; // Built-in interface list dari Java

// Brute Force
public class BruteForceSolver {
    // Atribut Solver
    private Board board;
    private List<Piece> listOfPieces;
    private int iterationCount = 0;

    // Konstruktor Piece
    public BruteForceSolver(Board board, List<Piece> listOfPieces) {
        this.board = board;
        this.listOfPieces = listOfPieces;
    }

    // Getter: iterationCount
    public int getIterationCount() {
        return iterationCount;
    }
    
    /* Puzzle solver brute force: backtracking */
    
    // Method bantuan untuk memulai backtacking
    public void solve() {
        backtrack(0);
    }

    private void backtrack(int index) {
        // Menambah hitungan iterasi
        iterationCount++;

        // Basis: seluruh Piece sudah ditempatkan pada Board
        if (index == listOfPieces.size()) {
            return; 
        }

        // Brute force seluruh kemungkinan transformasi Piece dan posisi pada Board 
        Piece piece = listOfPieces.get(index);
        for (Piece transformedPiece : piece.getRotationsAndMirrors()) {;
            for (int r = 0; r < board.getRows(); r++) {
                for (int c = 0; c < board.getCols(); c++) {
                    // Pengecekan penempatan piece selaku aturan IQ Puzzler Pro
                    if (board.canPlacePiece(transformedPiece, r, c)) {
                        board.placePiece(transformedPiece, r, c);
                        // DFS
                        backtrack(index + 1);
                        // Rollback
                        board.removePiece(transformedPiece, r, c);
                    }
                }
            }
        }
    }

}