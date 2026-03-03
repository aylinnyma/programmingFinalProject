package game;

public class Main {
    public static void main(String[] args) {
        // 1. Create an instance of your Game class
        Game myGame = new Game();

        // 2. Clear the terminal (optional visual flair)
        System.out.print("\033[H\033[2J");
        System.out.flush();

        // 3. Call the start method
        System.out.println("--- INITIALIZING SYSTEM BOOT ---");
        boolean result = myGame.start();

        // 4. Final feedback based on the return value
        if (result) {
            System.out.println("\n[SYSTEM] SESSION LOGGED: SUCCESSFUL DATA EXTRACTION.");
        } else {
            System.out.println("\n[SYSTEM] SESSION LOGGED: UNAUTHORIZED ACCESS ATTEMPT.");
        }

        System.out.println("TERMINATING CONNECTION...");
    }
}