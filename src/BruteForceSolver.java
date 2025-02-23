import java.util.List; // Built-in interface list dari Java

// Brute Force
public class BruteForceSolver {
    // Atribut Solver
    private Board board;
    private List<Piece> listOfPieces;
    private int iterCount = 0;

    // Konstruktor Piece
    public BruteForceSolver(Board board, List<Piece> listOfPieces) {
        this.board = board;
        this.listOfPieces = listOfPieces;
    }

    // Getter: iterationCount
    public int getIterationCount() {
        return iterCount;
    }
    
    /* Puzzle solver brute force: backtracking */
    
    // Method bantuan untuk memulai backtacking
    public void solve() {
        backtrack(0);
    }

    private boolean backtrack(int idx) {
        // Menambah hitungan iterasi
        iterCount++;

        // Basis: seluruh Piece sudah ditempatkan pada Board
        if (idx == listOfPieces.size()) {
            if (board.isFull()) {
                return true;
            }
            return false; 
        }

        // Brute force seluruh kemungkinan transformasi Piece dan posisi pada Board 
        Piece piece = listOfPieces.get(idx);
        List<Piece> transformedPieces = piece.getRotationsAndMirrors();

        for (int i = 0; i < 8; i++) {
            Piece transformedPiece = transformedPieces.get(i);

            for (int row = 0; row < board.getRows(); row++) {
                for (int col = 0; col < board.getCols(); col++) {
                    // Pengecekan penempatan piece selaku aturan IQ Puzzler Pro
                    if (board.canPlacePiece(transformedPiece, row, col)) {
                        board.placePiece(transformedPiece, row, col);
                        // Melanjutkan proses searching (berhenti bila sudah menemukan solusi)
                        if (backtrack(idx + 1)) {
                            return true;
                        }
                        // Rollback: kembali pada level state-tree sebelumnya
                        board.removePiece(transformedPiece, row, col);
                    }
                }
            }
        }
        return false;
    }

}