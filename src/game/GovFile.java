package game;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GovFile {

    // variables declaration
    private static final int LINES_PER_CHUNK = 45;
    private final File dataFolder;
    private final Map<Character, String> fileMapping;
    private List<List<String>> chunks;
    private int currentChunkIndex;
    private Character currentKey;

    // constructor
    public GovFile(String dataFolderPath) {
        this.dataFolder = new File(dataFolderPath);
        this.fileMapping = new HashMap<>();
        this.chunks = new ArrayList<>();
        this.currentChunkIndex = 0;
        this.currentKey = null;
        /*
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

         */
        // fixed mapping of files
        fileMapping.put('A', "mothman.txt");
        fileMapping.put('B', "chupacabras.txt");
        fileMapping.put('C', "skinwalkers.txt");
        fileMapping.put('D', "friendshipIsland.txt");
        fileMapping.put('E', "incidentRoswell.txt");
        fileMapping.put('F', "blueBook.txt");
        fileMapping.put('G', "varginhaIncident.txt");
        fileMapping.put('H', "humanCombustion.txt");
        fileMapping.put('I', "mkUltra.txt");
        fileMapping.put('J', "moonLanding.txt");
        fileMapping.put('K', "newWorldOrder.txt");
        fileMapping.put('L', "prenatalQuantumNightmare.txt");
    }

    // load the file corresponding to the given key and split it into 45 line sized chunks
    private boolean loadFileForKey(char key) {
        // security folder check
        if (!dataFolder.exists() || !dataFolder.isDirectory()) {
            return false;
        }

        String fileName = fileMapping.get(key);
        // if no file for this key
        if (fileName == null) {
            return false;
        }

        // important: check if file exists
        File chosenFile = new File(dataFolder, fileName);
        if (!chosenFile.exists() || !chosenFile.isFile()) {
            return false;
        }

        List<String> lines;
        try {
            lines = Files.readAllLines(chosenFile.toPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return false;
        }

        this.chunks = splitIntoChunks(lines);
        this.currentChunkIndex = 0;
        this.currentKey = key;

        return true;
    }

    // returns whether there is another part to display for the currently loaded files
    public boolean hasNextPart() {
        return currentChunkIndex < chunks.size();
    }

    // AI method for splitting texts
    private static List<List<String>> splitIntoChunks(List<String> lines) {
        List<List<String>> result = new ArrayList<>();
        for (int i = 0; i < lines.size(); i += GovFile.LINES_PER_CHUNK) {
            int end = Math.min(i + GovFile.LINES_PER_CHUNK, lines.size());
            result.add(new ArrayList<>(lines.subList(i, end)));
        }
        return result;
    }

    // main method
    public List<String> getNextChunk(char fileLetter, boolean keepGoing) {
        if (!keepGoing) {
            return Collections.emptyList();
        }

        char key = Character.toUpperCase(fileLetter);

        // if nothing has been loaded yet, load the file
        if (currentKey == null || !currentKey.equals(key)) {
            boolean loaded = loadFileForKey(key);
            if (!loaded) {
                return Collections.emptyList();
            }
        }

        if (!hasNextPart()) {
            return Collections.emptyList();
        }

        List<String> part = chunks.get(currentChunkIndex);
        currentChunkIndex++;
        return part;
    }
}