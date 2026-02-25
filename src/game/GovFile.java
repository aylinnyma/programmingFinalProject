package game;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GovFile {

    private static final int LINES_PER_CHUNK = 5;

    private final File secretFile;
    private List<List<String>> chunks;
    private int currentChunkIndex;
    private String chosenFileName;

    public GovFile(String dataFolderPath) {
        this.secretFile = new File(dataFolderPath);
        this.chunks = new ArrayList<>();
        this.currentChunkIndex = 0;
        this.chosenFileName = null;
    }

    /**
     * Picks a random .txt file from the data folder, reads it, and splits it into parts.
     *
     * @return true if a file was loaded successfully, false otherwise
     */
    public boolean loadRandomFile() {
        if (!secretFile.exists() || !secretFile.isDirectory()) {
            return false;
        }

        File[] textFiles = secretFile.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".txt");
            }
        });

        if (textFiles == null || textFiles.length == 0) {
            return false;
        }

        Random random = new Random();
        File chosenFile = textFiles[random.nextInt(textFiles.length)];
        chosenFileName = chosenFile.getName();

        List<String> lines;
        try {
            lines = Files.readAllLines(chosenFile.toPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return false;
        }

        chunks = splitIntoChunks(lines, LINES_PER_CHUNK);
        currentChunkIndex = 0;
        return true;
    }

    /**
     * Returns the name of the currently loaded file, or null if none loaded.
     */
    public String getChosenFileName() {
        return chosenFileName;
    }

    /**
     * Returns whether there is another part to display.
     */
    public boolean hasNextPart() {
        return currentChunkIndex < chunks.size();
    }

    /**
     * Returns the total number of parts.
     */
    public int getTotalParts() {
        return chunks.size();
    }

    /**
     * Displays the next part on the terminal. Does nothing if there are no more parts.
     */
    public void displayNextPart() {
        if (!hasNextPart()) {
            return;
        }

        int partNumber = currentChunkIndex + 1;
        System.out.println("Part " + partNumber + "/" + chunks.size() + ":");
        System.out.println("----------------------------------------");

        for (String line : chunks.get(currentChunkIndex)) {
            System.out.println(line);
        }

        System.out.println("----------------------------------------");
        currentChunkIndex++;
    }

    private static List<List<String>> splitIntoChunks(List<String> lines, int chunkSize) {
        List<List<String>> result = new ArrayList<>();
        for (int i = 0; i < lines.size(); i += chunkSize) {
            int end = Math.min(i + chunkSize, lines.size());
            result.add(new ArrayList<>(lines.subList(i, end)));
        }
        return result;
    }
}
