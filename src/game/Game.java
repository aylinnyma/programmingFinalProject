package game;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class Game {
    private final Scanner scanner = new Scanner(System.in);

    public boolean start() {
        boolean gameOn = true;
        int partsFinished = 0; // Track progress (0 to 3)
        int lives = 3;         // Overall general attempts
        int level = 0;
        String fileType = "";

        // 1. Initial Banner and Username
        System.out.println(ascii(1));
        System.out.println("\n".repeat(3));
        System.out.println("ENTER USERNAME: ");
        String username = scanner.nextLine();

        System.out.println("ACCESSING CLEARANCE LEVELS FOR: " + username.toUpperCase());
        System.out.println(ascii(3)); // Loading bar

        // 2. Level Selection with Error Handling
        while (level < 1 || level > 3) {
            System.out.println(ascii(2)); // Show folder hierarchy
            System.out.println("PLEASE SELECT DESIRED CLEARANCE LEVEL (1-3): ");
            if (scanner.hasNextInt()) {
                level = scanner.nextInt();
                if (level < 1 || level > 3) System.out.println(">> ACCESS DENIED: INVALID LEVEL.");
            } else {
                System.out.println(">> ERROR: NUMERIC INPUT REQUIRED.");
            }
            scanner.nextLine(); // Clear buffer
        }

        // 3. File Selection with Validation
        List<String> allowedTypes = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L");
        boolean validFile = false;
        while (!validFile) {
            System.out.print("SELECT FILE IDENTIFIER (A-L): ");
            fileType = scanner.nextLine().toUpperCase().trim();
            if (allowedTypes.contains(fileType)) {
                validFile = true;
            } else {
                System.out.println(">> SECURITY ALERT! INVALID FILE TYPE DETECTED.");
            }
        }

        // Initialize Managers
        GovFile govFile = new GovFile("data");
        Random rand = new Random();

        System.out.println("\nESTABLISHING SECURE CONNECTION...");
        System.out.println(ascii(4));

        // 4. Main Game Flow
        while (gameOn && partsFinished < 3) {
            // FIX: Changed fileType to fileType.charAt(0)
            List<String> chunk = govFile.getNextChunk(fileType.charAt(0), true);

            if (chunk.isEmpty()) {
                System.out.println(">> ERROR: FILE DATA CORRUPTED OR NOT FOUND.");
                return false;
            }

            System.out.println("\n--- DATA STREAM START ---");
            chunk.forEach(System.out::println);
            System.out.println("--- DATA STREAM INTERRUPTED ---\n");

            System.out.println("SECURITY WARNING: FILE ENCRYPTED. VERIFICATION REQUIRED.");

            boolean puzzleSolved = false;

            // Loop for puzzles: if they fail, they stay on this "part" but lose a life
            while (!puzzleSolved && lives > 0) {
                System.out.println("SYSTEM INTEGRITY: " + lives + " ATTEMPTS REMAINING.");
                int randPuzzle = rand.nextInt(5) + 1;

                // Call the puzzle and check result
                if (puzzle(randPuzzle, level)) {
                    puzzleSolved = true;
                    partsFinished++;
                    System.out.println(">> PROGRESS SAVED. LOADING NEXT DATA CHUNK...");
                } else {
                    lives--;
                    if (lives > 0) {
                        System.out.println(">> CRITICAL ERROR: ATTEMPT FAILED. GENERATING NEW ENCRYPTION KEY...");
                    }
                }
            }

            if (lives <= 0) {
                gameOn = false;
            }
        }

        // 5. Final Win/Loss Condition
        if (partsFinished == 3) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("FILE DECRYPTION COMPLETE. ACCESS GRANTED, " + username.toUpperCase());
            System.out.println("ALL SECRETS REVEALED.");
            System.out.println("=".repeat(50));
            return true;
        } else {
            System.out.println("\n" + "!".repeat(50));
            System.out.println("TERMINAL LOCKED. SYSTEM LIVES DEPLETED.");
            System.out.println("SECURITY HAS BEEN NOTIFIED. GOODBYE.");
            System.out.println("!".repeat(50));
            return false;
        }
    }

    private String ascii(int type) {
        String logo = """
                ┌───────────────────────────────────────────────────────────────────────────────┐
                │ ████████╗██╗  ██╗███████╗    ██████╗ ██████╗ ███████╗ █████╗  ██████╗██╗  ██╗ │
                │ ╚══██╔══╝██║  ██║██╔════╝    ██╔══██╗██╔══██╗██╔════╝██╔══██╗██╔════╝██║  ██║ │
                │    ██║   ███████║█████╗      ██████╔╝██████╔╝█████╗  ███████║██║     ███████║ │
                │    ██║   ██╔══██║██╔══╝      ██╔══██╗██╔══██╗██╔══╝  ██╔══██║██║     ██╔══██║ │
                │    ██║   ██║  ██║███████╗    ██████╔╝██║  ██║███████╗██║  ██║╚██████╗██║  ██║ │
                │    ╚═╝   ╚═╝  ╚═╝╚══════╝    ╚═════╝ ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝ │
                └───────────────────────────────────────────────────────────────────────────────┘
                """;

        String folderHierarchy = """
                    ROOT:/SYSTEM/ARCHIVE/INTERNAL/
                    ├── [1] SYSTEM_LOGS/CRYPTID_DATABASE/
                    │   ├── [A] MM-Ω/APPALACHIA-CLASSIFIED
                    │   ├── [B] CH-Θ/LIVESTOCK-CLASSIFIED
                    │   └── [C] SW-∆/DESERT-CLASSIFIED
                    ├── [2] SYSTEM_LOGS/PROJECT_OBSCURA/
                    │   ├── [D] FI-Ψ/SOUTH-PACIFIC-CLASSIFIED
                    │   ├── [E] RWI-Ξ/EXTRATERRESTRIAL-CLASSIFIED
                    │   ├── [F] PBB-Ω/UAP-CLASSIFIED
                    │   └── [G] VGI-Σ/BRAZIL-CLASSIFIED
                    └── [3] SYSTEM_LOGS/BLACK_CEDAR/
                        ├── [H] SHC-Φ/THERMAL-CLASSIFIED
                        ├── [I] MKU-Σ/COGNITION-CLASSIFIED
                        ├── [J] A11-Δ/LUNAR-CLASSIFIED
                        ├── [K] NWO-PRIME/ORIGIN
                        └── [L] PQN-Λ/INUTERO-CLASSIFIED
                    """;

        return switch (type) {
            case 1 -> logo;
            case 2 -> folderHierarchy;
            case 3 -> "LOADING: [███▒▒▒▒▒▒▒▒▒▒▒▒▒]";
            case 4 -> "LOADING: [████████████▒▒▒▒]";
            default -> "";
        };
    }

    private boolean puzzle(int randPuzzle, int level) {
        // Return the boolean result of each puzzle back to the start() flow
        return switch (randPuzzle) {
            case 1 -> PuzzleManager.findSequence(level);
            case 2 -> PuzzleManager.binaryDecoder(level);
            case 3 -> PuzzleManager.logicGate(level);
            case 4 -> PuzzleManager.caesarShift(level);
            case 5 -> PuzzleManager.guessNum(level);
            default -> false;
        };
    }
}