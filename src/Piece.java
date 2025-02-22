import java.util.ArrayList; // Built-in list dinamis dari Java
import java.util.List; // Built-in interface list dari Java

public class Piece {
    // Atribut Piece
    private char label;
    private char[][] shape;

    // Konstruktor Piece
    public Piece(char label, char[][] shape) {
        this.label = label;
        this.shape = shape;
    }

    // Getter: label dan shape
    public char getLabel() {
        return label;
    }

    public char[][] getShape() {
        return shape;
    }

    /* Piece pada IQ Puzzler Pro dapat dirotasi dan dapat dicerminkan sehingga
     seluruh kemungkinan konfigurasi perlu dicoba dalam algoritma BruteForce */

    // Getter: mendapatkan list berisi seluruh kemungkinan konfigurasi piece (yang telah dirotasi dan dicerminkan)
    public List<char[][]> getRotationsAndMirrors() {
        // Pembuatan list shape hasil seluruh konfigurasi rotasi dan mirror
        List<char[][]> rotatedAndMirroredShapes = new ArrayList<>();

        // Proses loading seluruh konfigurasi rotasi dan pencerminan
        char[][] currentShape = shape;

        for (int i = 0; i < 4; i++) {
            currentShape = rotate(currentShape);
            rotatedAndMirroredShapes.add(currentShape);
            rotatedAndMirroredShapes.add(mirror(currentShape));
        }
        return rotatedAndMirroredShapes;
    }

    // Method untuk merotasi Piece
    public char[][] rotate(char[][] currentShape) {
        // Dimensi shape
        int shapeRow = currentShape.length;
        int shapeCol = currentShape[0].length;

        // Pembuatan shape hasil rotasi 
        char[][] rotatedShape = new char[shapeCol][shapeRow];
        
        for (int i = 0; i < shapeRow; i++) {
            for (int j = 0; j < shapeCol; j++) {
                rotatedShape[j][shapeRow - i - 1] = currentShape[i][j];
            }
        }
        return rotatedShape;
    }

    // Method untuk mencerminkan piece
    public char[][] mirror(char[][] currentShape) {
        // Dimensi shape
        int shapeRow = currentShape.length;
        int shapeCol = currentShape[0].length;

        // Pembuatan shape hasil mirror 
        char[][] mirroredShape = new char[shapeRow][shapeCol];

        for (int i = 0; i < shapeRow; i++) {
            for (int j = 0; j < shapeCol; j++) {
                mirroredShape[i][shapeCol - j - 1] = currentShape[i][j];
            }
        }
        return mirroredShape;
    }

}
