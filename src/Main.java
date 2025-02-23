import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input nama file 
        System.out.print("Nama file input (.txt): ");
        String filename = scanner.nextLine();
        System.out.println();

        if (!FileIO.loadInputFile(filename)) {
            scanner.close();
            return;
        }

        // Brute force dan perhitungan waktu eksekusi
        BruteForceSolver solver = new BruteForceSolver(FileIO.board, FileIO.pieces);
        long startTime = System.currentTimeMillis();
        solver.solve();
        long endTime = System.currentTimeMillis();

        // Output hasil waktu pencarian dan jumlah iterasi
        System.out.println("\nWaktu pencarian: " + (endTime - startTime) + " ms");
        System.out.println();
        System.out.println("Banyak kasus yang ditinjau: " + solver.getIterationCount());
        System.out.println();

        // Output file .txt dan gambar bila terdapat solusi
        if (solver.getBoard().isFull()){
            // Output file .txt
            System.out.print("Apakah anda ingin menyimpan solusi (.txt)? (ya/tidak): ");
            String outputFile = scanner.nextLine().trim();

            if (outputFile.equalsIgnoreCase("ya")) {
                FileIO.saveSolutionToFile(filename);
            }

            // Output file gambar
            System.out.print("Apakah anda ingin menyimpan solusi (.png)? (ya/tidak): ");
            String outputImage = scanner.nextLine().trim();

            if (outputImage.equalsIgnoreCase("ya")) {
                FileIO.saveSolutionAsImage(filename);
            }
        }

        scanner.close();
    }
}
