package game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class PuzzleManager {

    // ================== FIND SEQUENCE PATTERN ========================
    public static boolean findSequence(int level) {
        Random rand = new Random();
        Scanner input = new Scanner(System.in);
        // initializers for later (usage only in switch statement)
        int num;
        int num2;
        int ans; // FIXED: Changed to int to avoid .0 in string comparison
        int start;

        int choice = ((level == 1) ? rand.nextInt(1, 4) : (level == 2) ? rand.nextInt(4, 7) : rand.nextInt(7, 9));
        int attempts = 2;
        ArrayList<String> sequence = new ArrayList<String>();
        String answer = "";

        // define a random pattern depending on the level
        switch (choice) {
            case 1:
                // simple addition
                num = rand.nextInt(5) + 1;
                sequence.add("1");
                for (int i = 0; i < 4; i++) {
                    int n = Integer.parseInt(sequence.get(i)) + num;
                    sequence.add(Integer.toString(n));
                }
                ans = Integer.parseInt(sequence.getLast()) + num;
                answer = Integer.toString(ans);
                break;

            case 2:
                // num to the power of 2 or 3
                num = rand.nextInt(2, 4);
                for (int i = 1; i < 6; i++) {
                    int n = (int) Math.pow(i, num);
                    sequence.add(Integer.toString(n));
                }
                // next term is (6 ^ num)
                ans = (int) Math.pow(6, num);
                answer = Integer.toString(ans);
                break;

            case 3:
                // double of n
                num = rand.nextInt(1, 50);
                sequence.add(Integer.toString(num));
                for (int i = 0; i < 4; i++) {
                    num = num * 2;
                    sequence.add(Integer.toString(num));
                }
                ans = Integer.parseInt(sequence.getLast()) * 2;
                answer = Integer.toString(ans);
                break;

            case 4:
                // fibonacci-like sequence
                num = rand.nextInt(1, 26);
                sequence.add(Integer.toString(num));
                for (int i = 0; i < 5; i++) {
                    sequence.add(Integer.toString(num));
                    num += Integer.parseInt(sequence.get(i));
                }
                ans = Integer.parseInt(sequence.getLast())
                        + Integer.parseInt(sequence.get(sequence.size() - 2));
                answer = Integer.toString(ans);
                break;

            case 5:
                // alternating addition / subtraction
                num = rand.nextInt(-10, 16);
                num2 = rand.nextInt(1, 16);
                start = rand.nextInt(-100, 501);
                sequence.add(Integer.toString(start));

                int n;
                for (int i = 0; i < 5; i++) {
                    int current = Integer.parseInt(sequence.get(i));
                    if (i % 2 == 0) {
                        n = current + num;
                    } else {
                        n = current + num2;
                    }
                    sequence.add(Integer.toString(n));
                }
                ans = Integer.parseInt(sequence.getLast()) + num2; // next diff is num2
                answer = Integer.toString(ans);
                break;

            case 6:
                // increasing difference
                num = rand.nextInt(1, 501);
                for (int i = 3; i < 8; i++) {
                    sequence.add(Integer.toString(num));
                    num += i;
                }
                // FIXED: Corrected parsing logic to add 8 instead of concatenating it
                ans = Integer.parseInt(sequence.getLast()) + 8;
                answer = Integer.toString(ans);
                break;

            case 7:
                // multiply + add
                num = rand.nextInt(1, 6);
                num2 = rand.nextInt(1, 13);
                start = rand.nextInt(-50, 500);
                sequence.add(Integer.toString(start));
                int n2;
                for (int i = 0; i < 5; i++) {
                    n2 = (Integer.parseInt(sequence.get(i)) * num) + num2;
                    sequence.add(Integer.toString(n2));
                }
                ans = (Integer.parseInt(sequence.getLast()) * num) + num2;
                answer = Integer.toString(ans);
                break;

            case 8:
                // binary addition
                num = rand.nextInt(1, 9);
                for (int i = 0; i < 5; i++) {
                    String binary = Integer.toBinaryString(num);
                    sequence.add(binary);
                    num++;
                }
                sequence.add(Integer.toBinaryString(num));
                ans = Integer.parseInt(sequence.getLast(), 2);
                answer = Integer.toString(ans);
                break;

            default:
                return false;
        }

        System.out.println("FIND THE PATTERN TO COMPLETE THE FOLLOWING SERIES: ");
        for (String val : sequence) {
            System.out.print(val + " ");
        }
        System.out.print("?");
        System.out.println();

        System.out.println("YOU HAVE 2 ATTEMPTS...");

        // core logic
        while (attempts > 0) {
            String guess = input.nextLine().trim();
            if (guess.equals(answer)) {
                System.out.println("NUMBER DECIPHERED CORRECTLY. ACCESS GRANTED.");
                return true;
            } else {
                attempts--;
                if (attempts > 0) {
                    System.out.println("INCORRECT. PLEASE TRY AGAIN. YOU HAVE " + attempts + " ATTEMPT(S) REMAINING");
                }
            }
        }

        System.out.println("OUT OF ATTEMPTS. ACCESS DENIED. (Correct answer was: " + answer + ")");
        return false;
    }


    // ================== BINARY DECODER ========================
    public static boolean binaryDecoder(int level) {
        Random rand = new Random();
        Scanner input = new Scanner(System.in);

        // choose number based on level
        int num;
        if (level == 1) {
            num = rand.nextInt(255) + 1;
        } else if (level == 2) {
            num = rand.nextInt(9999 - 255) + 256;
        } else {
            num = rand.nextInt(90000) + 10000;
        }

        int type = rand.nextInt(2);
        int attempts = 2;

        String binary = Integer.toBinaryString(num);
        String decimal = Integer.toString(num);

        while (attempts > 0) {
            if (type == 0) {
                System.out.println("PLEASE INTRODUCE THE DECIMAL VALUE CORRESPONDING TO THE FOLLOWING BINARY NUMBER:");
                System.out.println("                        " + binary);
            } else {
                System.out.println("PLEASE INTRODUCE THE BINARY VALUE CORRESPONDING TO THE FOLLOWING DECIMAL NUMBER:");
                System.out.println("                        " + decimal);
            }

            String guess = input.nextLine().trim();
            attempts--;

            String target = (type == 0) ? decimal : binary;

            if (guess.equals(target)) {
                System.out.println("CONVERSION SUCCESSFUL. ACCESS GRANTED.");
                return true;
            } else if (attempts > 0) {
                System.out.println("INCORRECT INPUT. TRY AGAIN. YOU HAVE " + attempts + " REMAINING ATTEMPT.");
            }
        }

        System.out.println("OUT OF ATTEMPTS. ACCESS DENIED.");
        return false;
    }


    // ===================== CAESAR'S SHIFT ========================
    // FIXED: Changed signature to take only 'level' to match Game.java call
    public static boolean caesarShift(int level) {
        int attempts = 2;
        String word = genWord(level);
        Random rand = new Random();

        int maxShift = (level == 1) ? 1 : (level == 2) ? 2 : 4;
        int shift = 1 + rand.nextInt(maxShift);

        String upper = word.toUpperCase();
        char[] chars = upper.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char letter = chars[i];
            if (letter >= 'A' && letter <= 'Z') {
                chars[i] = (char) ('A' + (letter - 'A' + shift) % 26);
            }
        }

        String cipher = new String(chars);
        System.out.println("Decipher this Word: " + cipher);

        Scanner input = new Scanner(System.in);

        while (attempts > 0) {
            System.out.println("Enter your guess:");
            String guess = input.nextLine().trim();

            if (word.equalsIgnoreCase(guess)) {
                System.out.println("WORD GUESSED CORRECTLY. CLEARANCE LEVEL REMOVED.");
                return true;
            }

            attempts--;
            if (attempts > 0) {
                System.out.println("INCORRECT. PLEASE TRY AGAIN. YOU HAVE " + attempts + " ATTEMPT" + (attempts >= 2 ? "S " : " ") + "LEFT.");
            }
        }

        System.out.println("OUT OF ATTEMPTS. ACCESS DENIED. (Word was: " + word + ")");
        return false;
    }


    // ================== NUMBER GUESS ========================
    public static boolean guessNum(int level) {
        Random rand = new Random();
        int maxNum;
        int num;
        int guessAtt;

        switch (level) {
            case 3:
                maxNum = 100;
                num = rand.nextInt(100) + 1;
                guessAtt = 7;
                break;
            case 2:
                maxNum = 50;
                num = rand.nextInt(50) + 1;
                guessAtt = 6;
                break;
            default:
                maxNum = 10;
                num = rand.nextInt(10) + 1;
                guessAtt = 4;
        }

        Scanner input = new Scanner(System.in);
        System.out.println("PLEASE INTRODUCE ACCESS NUMBER (HINT: IT IS A NUMBER FROM 1 TO " + maxNum + "). YOU HAVE A MAXIMUM OF " + guessAtt + " ATTEMPTS.");

        while (guessAtt > 0) {
            if (!input.hasNextInt()) {
                input.next(); // clear invalid input
                continue;
            }
            int guess = input.nextInt();
            input.nextLine(); // FIXED: Consume newline

            if (guess == num) {
                System.out.println("NUMBER GUESSED CORRECTLY. ACCESS ALLOWED.");
                return true;
            }
            guessAtt--;
            if (guessAtt > 0) {
                if (guess < num) {
                    System.out.println("THE NUMBER IS HIGHER. ATTEMPTS REMAINING: " + guessAtt);
                } else {
                    System.out.println("THE NUMBER IS LOWER. ATTEMPTS REMAINING: " + guessAtt);
                }
            }
        }

        System.out.println("OUT OF ATTEMPTS. UNAUTHORIZED USER. ACCESS DENIED.");
        return false;
    }


    // ================== LOGIC GATE ========================
    public static boolean logicGate(int level) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int a, b, c;
        int answer = 0;
        int gate, expr;
        int attempts = 2;

        switch (level) {
            case 1:
                a = random.nextInt(2);
                b = random.nextInt(2);
                gate = random.nextInt(3);
                System.out.println("\nInputs: A = " + a + ", B = " + b);
                switch (gate) {
                    case 0 -> { System.out.println("Gate: AND"); answer = a & b; }
                    case 1 -> { System.out.println("Gate: OR"); answer = a | b; }
                    case 2 -> { System.out.println("Gate: XOR"); answer = a ^ b; }
                }
                break;
            case 2:
                a = random.nextInt(2);
                b = random.nextInt(2);
                gate = random.nextInt(5);
                System.out.println("\nInputs: A = " + a + ", B = " + b);
                switch (gate) {
                    case 0 -> { System.out.println("Gate: NOT (A)"); answer = (a == 0) ? 1 : 0; }
                    case 1 -> { System.out.println("Gate: NAND"); answer = (a & b) == 1 ? 0 : 1; }
                    case 2 -> { System.out.println("Gate: NOR"); answer = (a | b) == 1 ? 0 : 1; }
                    case 3 -> { System.out.println("Gate: XOR"); answer = a ^ b; }
                    case 4 -> { System.out.println("Gate: XNOR"); answer = (a ^ b) == 1 ? 0 : 1; }
                }
                break;
            case 3:
                a = random.nextInt(2);
                b = random.nextInt(2);
                c = random.nextInt(2);
                expr = random.nextInt(6);
                System.out.println("\nInputs: A = " + a + ", B = " + b + ", C = " + c);
                int notA = (a == 0) ? 1 : 0;
                int notC = (c == 0) ? 1 : 0;
                switch (expr) {
                    case 0 -> { System.out.println("Expression: (A AND B) OR (NOT C)"); answer = (a & b) | notC; }
                    case 1 -> { System.out.println("Expression: (A OR B) AND (NOT C)"); answer = (a | b) & notC; }
                    case 2 -> { System.out.println("Expression: (A NAND B) OR C"); answer = ((a & b) == 1 ? 0 : 1) | c; }
                    case 3 -> { System.out.println("Expression: (A XOR B) AND C"); answer = (a ^ b) & c; }
                    case 4 -> { System.out.println("Expression: NOT(A AND B) OR C"); answer = ((a & b) == 1 ? 0 : 1) | c; }
                    case 5 -> { System.out.println("Expression: (A OR B) AND (B OR C)"); answer = (a | b) & (b | c); }
                }
                break;
            default: return false;
        }

        while (attempts > 0) {
            System.out.println("YOUR ANSWER (0 or 1): ");
            if (scanner.hasNextInt()) {
                int userGuess = scanner.nextInt();
                scanner.nextLine();
                if (userGuess == answer) {
                    System.out.println("ANSWER CORRECT. ACCESS ALLOWED.");
                    return true;
                }
            } else {
                scanner.nextLine();
            }
            attempts--;
            if (attempts > 0) System.out.println("ANSWER INCORRECT. ATTEMPTS LEFT: " + attempts);
        }
        return false;
    }

    // ================== HELPERS ========================
    public static String genWord(int level) {
        String[] easy = {"UFO", "Fog", "Hex", "Orb", "Cult", "Omen", "Rune", "Veil", "Myth", "Pact"};
        String[] medium = {"Cipher", "Shade", "Wraith", "Sigil", "Haunt", "Feral", "Shadow", "Phantom", "Oracle", "Occult", "Portal", "Relic", "Spirit", "Secret", "Cryptid", "Eclipse", "Enigma", "Ritual", "Specter", "Unknown", "Whisper", "Hidden", "Conclave", "Illusion", "Intrigue"};
        String[] hard = {"Moonrise", "Poltergy", "Revenant", "Veilborn", "Grimoire", "Apparition", "Conspiracy", "Forbidden", "Otherworld", "Watchers", "Darkcraft", "Blacksite", "Bloodmoon", "Gatekeeper", "Nightstalk", "Shadowborn", "Truthseek", "Mindcontrol", "Necromancer", "Skinwalker", "Interdimensional", "Cryptozoology", "Paranormality", "Extraterrestrial"};

        Random rand = new Random();
        if (level == 1) return easy[rand.nextInt(easy.length)];
        if (level == 2) return medium[rand.nextInt(medium.length)];
        // FIXED: Used array.length to avoid index errors
        return hard[rand.nextInt(hard.length)];
    }
}