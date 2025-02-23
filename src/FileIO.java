import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileIO {
    public static int boardRows, boardCols, totalPieces;
    public static Board board = null;
    public static List<Piece> pieces = null;

    public static boolean loadInputFile(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            // Membaca N M P: boardRows, boardCols, totalPieces
            String[] firstLine = reader.readLine().trim().split(" ");
            if (firstLine.length != 3) {
                reader.close();
                System.out.println("Error: Format baris pertama (N M P) salah");
                return false;
            }

            boardRows = Integer.parseInt(firstLine[0]); // N
            boardCols = Integer.parseInt(firstLine[1]); // M
            totalPieces = Integer.parseInt(firstLine[2]); // P

            // Validasi input N M P
            if (boardRows <= 0 || boardCols <= 0 || totalPieces <= 0 || totalPieces > 26) {
                reader.close();
                System.out.println("Error: Dimensi papan atau jumlah pieces tidak valid (maksimal 26)!");
                return false;
            }

            // Read mode (baris kedua) harus "DEFAULT"
            String mode = reader.readLine().trim();
            if (!mode.equals("DEFAULT")) {
                reader.close();
                System.out.println("Error: Mode solver harus DEFAULT!");
                return false;
            }

            // Construct Board
            board = new Board(boardRows, boardCols);
            pieces = new ArrayList<>();

            // Baca seluruh Piece
            List<String> pieceLines = new ArrayList<>();
            char currentLetter = '\0';
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                // Mendeteksi char baru pertama (penanda Piece baru)
                char firstChar = detectFirstCharacter(line);
                
                if (firstChar != currentLetter) {
                    if (!pieceLines.isEmpty()) {
                        pieces.add(createPieceFromLines(currentLetter, pieceLines));
                        pieceLines.clear();
                    }
                    currentLetter = firstChar;
                }

                // Validasi input hanya char 'A' - 'Z' dan ' '
                if (!line.matches("[A-Z ]+")) {
                    reader.close();
                    System.out.println("Error: Format blok tidak valid: " + line);
                    return false;
                }

                pieceLines.add(line.replace(" ", "."));
            }

            if (!pieceLines.isEmpty()) {
                pieces.add(createPieceFromLines(currentLetter, pieceLines));
            }

            // Memastikan jumlah Piece sama dengan input P
            if (pieces.size() != totalPieces) {
                reader.close();
                System.out.println("Error: Jumlah blok tidak sesuai (diharapkan: " + totalPieces + ", ditemukan: " + pieces.size() + ").");
                return false;
            }

            reader.close();
            return true;

        } catch (IOException e) {
            System.out.println("Error: File tidak ditemukan atau tidak bisa dibuka.");
            return false;
        }
    }

    // Method untuk mendeteksi char baru pertama
    private static char detectFirstCharacter(String line) {
        for (char c : line.toCharArray()) {
            if (c != ' ') return c;
        }
        return '\0';
    }

    // Methode untuk mengubah input menjadi objek Piece
    private static Piece createPieceFromLines(char label, List<String> shapeLines) {
        char[][] shape = convertToCharArray(shapeLines);
        return new Piece(label, shape);
    }

    // Method untuk mengubah String menjadi list of list of char (char[][])
    private static char[][] convertToCharArray(List<String> shapeLines) {
        int maxCols = shapeLines.stream().mapToInt(String::length).max().orElse(0);
        char[][] shape = new char[shapeLines.size()][maxCols];

        for (int i = 0; i < shapeLines.size(); i++) {
            String row = shapeLines.get(i);
            for (int j = 0; j < maxCols; j++) {
                shape[i][j] = (j < row.length()) ? row.charAt(j) : '.';
            }
        }
        return shape;
    }

    // Method untuk menyimpan solusi (state papan) pada sebuah file txt
    public static void saveSolutionToFile(String inputFilename) {
        if (board == null) {
            System.out.println("Error: No board to save.");
            return;
        }

        // Memastikan directory sudah ada
        File solutionDir = new File("test/solution_file/");
        if (!solutionDir.exists()) {
            solutionDir.mkdirs();
        }

        File file = new File(inputFilename);
        String baseName = file.getName().replace(".txt", "");
        String solutionFilename = "test/solution_file/solution_" + baseName + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(solutionFilename))) {
            for (char[] row : board.getGrid()) {
                for (char cell : row) {
                    writer.write(cell + " ");
                }
                writer.newLine();
            }
            System.out.println("Solution saved to: " + solutionFilename);
        } catch (IOException e) {
            System.out.println("Error: Failed to save solution file.");
        }
    }

    // Method untuk menyimpan solusi (state papan) menjadi sebuah Image (.png)
    public static void saveSolutionAsImage(String inputFilename) {
        if (board == null) {
            System.out.println("Error: No board to save.");
            return;
        }
    
        // Memastikan directory sudah ada
        File solutionDir = new File("test/solution_image/");
        if (!solutionDir.exists()) {
            solutionDir.mkdirs();
        }
    
        File file = new File(inputFilename);
        String baseName = file.getName().replace(".txt", ""); 
        String solutionFilename = "test/solution_image/solution_" + baseName + ".png";
    
        // Image size
        int cellSize = 50;
        int rows = board.getRows();
        int cols = board.getCols();
        int width = cols * cellSize;
        int height = rows * cellSize;
    
        // BufferedImage
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
    
        // Menggambar papan
        char[][] grid = board.getGrid();
        Map<Character, Color> colorMap = new HashMap<>();
        Color[] availableColors = {
            Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.MAGENTA, Color.CYAN,
            new Color(255, 165, 0), new Color(128, 0, 128), new Color(255, 215, 0),
            new Color(0, 128, 128), new Color(128, 128, 0), new Color(255, 105, 180)
        };
    
        int colorIndex = 0;
        for (char[] row : grid) {
            for (char cell : row) {
                if (cell != '.' && !colorMap.containsKey(cell)) {
                    colorMap.put(cell, availableColors[colorIndex % availableColors.length]);
                    colorIndex++;
                }
            }
        }
    
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                char cell = grid[row][col];
                graphics.setColor(colorMap.getOrDefault(cell, Color.WHITE));
                graphics.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
                graphics.setColor(Color.BLACK);
                graphics.drawRect(col * cellSize, row * cellSize, cellSize, cellSize);
            }
        }
    
        graphics.dispose();
    
        // Simpan Image
        try {
            ImageIO.write(image, "png", new File(solutionFilename));
            System.out.println("Solution image saved to: " + solutionFilename);
        } catch (IOException e) {
            System.out.println("Error: Failed to save solution image.");
        }
    }
}
