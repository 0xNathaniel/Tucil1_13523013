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
    
    // Puzzle solver brute force: backtracking 
    
    // Method bantuan untuk memulai backtacking
    public void solve() {
        boolean isSolved = backtrack(0);
        // Output hasil berdasarkan status solving
        if (isSolved) {
            board.printBoard();
            return;
        }
        System.out.println("Puzzle tidak memiliki solusi!");
    }

    // Method proses backtracking: mengembalikan true bila terdapat solusi
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
        List<Piece> rotatedAndMirroredPieces = piece.getRotationsAndMirrors();

        for (int i = 0; i < 8; i++) {
            Piece currentPiece = rotatedAndMirroredPieces.get(i);

            for (int row = 0; row < board.getRows(); row++) {
                for (int col = 0; col < board.getCols(); col++) {
                    // Pengecekan penempatan piece selaku aturan IQ Puzzler Pro
                    if (board.canPlacePiece(currentPiece, row, col)) {
                        board.placePiece(currentPiece, row, col);
                        // Melanjutkan proses searching (berhenti bila sudah menemukan solusi)
                        if (backtrack(idx + 1)) {
                            return true;
                        }
                        // Rollback: kembali pada level state-tree sebelumnya
                        board.removePiece(currentPiece, row, col);
                    }
                }
            }
        }
        return false;
    }
}