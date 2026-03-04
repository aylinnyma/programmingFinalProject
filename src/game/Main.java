package game;

public class Main {
    public static void main(String[] args) {

        Game myGame = new Game();

        // start game
        System.out.println("--- INITIALIZING SYSTEM BOOT ---");
        boolean result = myGame.start();

        // end message based on result value
        if (result) {
            System.out.println("\n[SYSTEM] SESSION LOGGED: SUCCESSFUL DATA EXTRACTION.");
        } else {
            System.out.println("\n[SYSTEM] SESSION LOGGED: UNAUTHORIZED ACCESS ATTEMPT.");
        }

        System.out.println("TERMINATING CONNECTION...");
    }
}