package game;

import java.util.Scanner;

public class Game {
    private final Scanner scanner = new Scanner(System.in);

    public void start() {

       // String name = scanner.nextLine();
       // Player player = new Player(name);

        //System.out.println("Welcome, " + player.getName() + ".");
        // Later: load files and start gameplay

        String userName;
        int level;


    }

    private String ascii(){
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
                    ├── [0] SYSTEM_LOGS/
                    │   ├── BOOT_SEQUENCE.LOG
                    │   └── USER_ACCESS.DB
                    ├── [1] PROJECT_OBSCURA/
                    │   ├── SCP_096_FILE.CRPT
                    │   ├── SCP_173_REPORT.CRPT
                    │   └── SITE_19_MAP.IMG
                    ├── [2] CRYPTID_DATABASE/
                    │   ├── MOTHMAN_SIGHTINGS.TXT
                    │   └── JERSEY_DEVIL_DNA.BIN
                    └── [3] ██████████/
                        └── [ACCESS_DENIED]
                    """;

    }
}
