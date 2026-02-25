package game;

import static game.PuzzleManager.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to THE BREACH.");
        GovFile govFile = new GovFile("data");
        PuzzleManager puzzle = new PuzzleManager();

        if (!govFile.loadRandomFile()) {
            System.out.println("No .txt files in 'data' or folder missing.");
            return;
        }

        System.out.println("Using file: " + govFile.getChosenFileName());
        System.out.println();

        while (govFile.hasNextPart()) {
            govFile.displayNextPart();
            // your puzzle logic here – run puzzle, only continue when solved
            findSequence(2);
        }

        System.out.println("End of file.");
    }
}