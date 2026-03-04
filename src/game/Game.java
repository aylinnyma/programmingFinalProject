package game;

import java.util.*;

public class Game {
    private final Scanner scanner = new Scanner(System.in);

    public boolean start() {

        // universal settings
        boolean gameOn = true;
        int partsFinished = 0;
        int lives = 3;
        int level = 0;
        String fileType = "";

        // initial banner
        System.out.println(ascii(1));
        System.out.println("\n".repeat(3));
        // ask user for a username
        System.out.println("ENTER USERNAME: ");
        String username = scanner.nextLine();

        System.out.println("ACCESSING CLEARANCE LEVELS FOR: " + username.toUpperCase());
        System.out.println(ascii(3));

        // while loop to make sure user's level is within range
        while (level < 1 || level > 3) {
            System.out.println(ascii(2));
            System.out.println("PLEASE SELECT DESIRED CLEARANCE LEVEL (1-3): ");
            // verify user's input is an int
            if (scanner.hasNextInt()) {
                level = scanner.nextInt();
                if (level < 1 || level > 3) System.out.println(">>> ACCESS DENIED: INVALID LEVEL.");
            } else {
                System.out.println(">>> ERROR: NUMERIC INPUT REQUIRED.");
            }
            scanner.nextLine();
        }

        // make sure user selects correct file
        List<String> allowedTypes;
        switch (level) {
            case 2 -> allowedTypes = Arrays.asList("D", "E", "F", "G");
            case 3 -> allowedTypes = Arrays.asList("H", "I", "J", "K", "L");
            default -> allowedTypes = Arrays.asList("A", "B", "C");
        }

        boolean validFile = false;
        while (!validFile) {
            System.out.println("\nAVAILABLE FILES FOR CLEARANCE LEVEL " + level + ": " + allowedTypes);
            System.out.print("SELECT FILE IDENTIFIER: ");
            fileType = scanner.nextLine().toUpperCase().trim();

            if (allowedTypes.contains(fileType)) {
                validFile = true;
            } else {
                System.out.println(">> ACCESS DENIED: FILE OUTSIDE YOUR CLEARANCE RANGE.");
                System.out.println(">> PLEASE SELECT FROM: " + allowedTypes);
            }
        }

        // initialize govFile
        GovFile govFile = new GovFile("data");
        Random rand = new Random();

        System.out.println("\nESTABLISHING SECURE CONNECTION...");
        System.out.println(ascii(4));

        // game flow
        while (gameOn) {
            // cast file type letter to char because getNextChunk() receives a char
            List<String> chunk = govFile.getNextChunk(fileType.charAt(0), true);
            if (chunk.isEmpty()) {
                break;
            }

            /*
            --- old printing system
            for (String s : chunk) {
                System.out.println(s);
            }

            new with format:
             */

            printClassifiedData(chunk, fileType);


            // first stop
            System.out.println("--- DATA STREAM INTERRUPTED ---\n");
            System.out.println("SECURITY WARNING: FILE ENCRYPTED. VERIFICATION REQUIRED.");


            boolean puzzleSolved = false;

            // loop for puzzles, if user fails, he stays in the loop and loses a life
            while (!puzzleSolved && lives > 0) {
                System.out.println("SYSTEM INTEGRITY: " + lives + " ATTEMPTS REMAINING.");
                int randPuzzle = rand.nextInt(5) + 1;

                // call the puzzle and check result
                if (puzzle(randPuzzle, level)) {
                    puzzleSolved = true;
                    partsFinished++;
                    System.out.println(">>> PROGRESS SAVED. LOADING NEXT DATA CHUNK...");
                } else {
                    lives--;
                    if (lives > 0) {
                        System.out.println(">>> CRITICAL ERROR: ATTEMPT FAILED. GENERATING NEW ENCRYPTION KEY...");
                    }
                }
            }

            if (lives == 0) {
                gameOn = false;
                break;
            }
        }

        // if user wins
        if (lives > 0) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("FILE DECRYPTION COMPLETE. ACCESS GRANTED, " + username.toUpperCase());
            System.out.println("ALL SECRETS REVEALED.");
            System.out.println("=".repeat(50));
            return true;
        } else {
            // if user runs out of lives
            System.out.println("\n" + "!".repeat(50));
            System.out.println("TERMINAL LOCKED. SYSTEM ATTEMPTS DEPLETED.");
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
        // returns result of each puzzle to the start() flow
        return switch (randPuzzle) {
            case 1 -> PuzzleManager.findSequence(level);
            case 2 -> PuzzleManager.binaryDecoder(level);
            case 3 -> PuzzleManager.logicGate(level);
            case 4 -> PuzzleManager.caesarShift(level);
            case 5 -> PuzzleManager.guessNum(level);
            default -> false;
        };
    }

    // AI method to format files
    private void printClassifiedData(List<String> chunk, String fileID) {
        String top    = "╔" + "═".repeat(78) + "╗";
        String header = "║ [ CLASSIFIED DATA STREAM - FILE: " + fileID + " ]" + " ".repeat(78 - 36) + "║";
        String divider = "╠" + "═".repeat(78) + "╣";
        String bottom = "╚" + "═".repeat(78) + "╝";

        System.out.println("\n" + top);
        System.out.println(header);
        System.out.println(divider);

        for (String line : chunk) {
            // %-76s means: left-align the string and pad with spaces up to 76 characters
            System.out.printf("║  %-74s  ║%n", line);
        }

        System.out.println(bottom + "\n");
    }
}