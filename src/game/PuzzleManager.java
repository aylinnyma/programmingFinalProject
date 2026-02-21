package game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class PuzzleManager {

    // ================== FIND SEQUENCE PATTERN ========================
    public static boolean findSequence(int level){
        Random rand = new Random();
        Scanner input = new Scanner(System.in);
        // initializers for later (usage only in switch statement)
        int num;
        int num2;
        double ans;
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
                answer = Double.toString(ans);
                break;

            case 2:
                // num to the power of 2 or 3
                num = rand.nextInt(2, 4);
                for (int i = 1; i < 6; i++) {
                    int n = (int) Math.pow(i, num);
                    sequence.add(Integer.toString(n));
                }
                // next term is (6 ^ num)
                ans = Math.pow(6, num);
                answer = Double.toString(ans);
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
                answer = Double.toString(ans);
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
                answer = Double.toString(ans);
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
                answer = Double.toString(ans);
                break;

            case 6:
                // increasing difference
                num = rand.nextInt(1, 501);
                for (int i = 3; i < 8; i++) {
                    sequence.add(Integer.toString(num));
                    num += i;
                }
                ans = Integer.parseInt(sequence.getLast() + 8);
                answer = Double.toString(ans);
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
                ans = (Integer.parseInt(sequence.getLast()) * num ) + num2;
                answer = Double.toString(ans);
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
                answer = Double.toString(ans);
                break;

            default:
                return false;
        }

        System.out.println("FIND THE PATTERN TO COMPLETE THE FOLLOWING SERIES: ");
        for (String n : sequence){
            System.out.print(n + " ");
        }
        System.out.print("?");
        System.out.println();

        System.out.println("YOU HAVE 2 ATTEMPTS...");

        // core logic
        while (attempts > 0) {
            String guess = input.nextLine();
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

        System.out.println("OUT OF ATTEMPTS. ACCESS DENIED.");
        return false;
    }


    // ================== BINARY DECODER ========================
    public static boolean binaryDecoder(int level) {
        Random rand = new Random();
        Scanner input = new Scanner(System.in);

        // choose number based on level
        int num;
        if (level == 1) {
            // 1–255
            num = rand.nextInt(255) + 1;
        } else if (level == 2) {
            // 256–9999
            num = rand.nextInt(9999 - 255) + 256;
        } else {
            // 10000–99999
            num = rand.nextInt(90000) + 10000;
        }

        // type == 0: user converts binary -> decimal
        // type == 1: user converts decimal -> binary
        int type = rand.nextInt(2);
        int attempts = 2;

        // number into binary and decimal strings
        String binary = Integer.toBinaryString(num);
        String decimal = Integer.toString(num);

        while (attempts > 0) {
            if (type == 0) {
                System.out.println("PLEASE INTRODUCE THE DECIMAL VALUE CORRESPONDING TO THE FOLLOWING BINARY NUMBER:");
                System.out.println("                        " + binary);
                String guess = input.nextLine().trim();
                attempts--;
                if (guess.equals(decimal)) {
                    System.out.println("CONVERSION SUCCESSFUL. ACCESS GRANTED.");
                    return true;
                } else if (attempts > 0) {
                    System.out.println("INCORRECT INPUT. TRY AGAIN. YOU HAVE " + attempts + " REMAINING ATTEMPT.");
                }
            } else {
                System.out.println("PLEASE INTRODUCE THE BINARY VALUE CORRESPONDING TO THE FOLLOWING DECIMAL NUMBER:");
                System.out.println("                        " + decimal);
                String guess = input.nextLine().trim();
                attempts--;
                if (guess.equals(binary)) {
                    System.out.println("CONVERSION SUCCESSFUL. ACCESS GRANTED.");
                    return true;
                } else if (attempts > 0) {
                    System.out.println("INCORRECT INPUT. TRY AGAIN. YOU HAVE " + attempts + " REMAINING ATTEMPT.");
                }
            }
        }

        System.out.println("OUT OF ATTEMPTS. ACCESS DENIED.");
        return false;
    }


    // ===================== CAESAR'S SHIFT ========================
    public static boolean caesarShift(int level, int attempts){
        String word = genWord(level);
        Random rand = new Random();

        // determine the max amount of shifts per level
        // easy:1-2, medium:1-4, hard:1-6
        int maxShift = (level == 1) ? 1 : (level == 2) ? 2 : 4;
        int shift = 1 + rand.nextInt(maxShift);

        // "encryption" (case insensitive --> convert to uppercase)
        String upper = word.toUpperCase();
        char[] chars = upper.toCharArray();

            //caesar shift logic
        for (int i = 0; i < chars.length; i++) {
            char letter = chars[i];
            if (letter >= 'A' && letter <= 'Z') {
                chars[i] = (char) ('A' + (letter - 'A' + shift) % 26);
            }
        }

        // save and print word to be deciphered
        String cipher = new String(chars);
        System.out.println("Decipher this Word: " + cipher);

        // evaluate user's input
        Scanner input = new Scanner(System.in);

        while (attempts > 0) {
            System.out.println("Enter your guess:");
            String guess = input.nextLine();

            // case user guesses and wins
            if (word.equalsIgnoreCase(guess)) {
                System.out.println("WORD GUESSED CORRECTLY. CLEARANCE LEVEL REMOVED.");
                return true;
            }

            // case user guesses incorrectly
            attempts--;
            if (attempts > 0) {
                System.out.println("INCORRECT. PLEASE TRY AGAIN. YOU HAVE " + attempts + " ATTEMPT" + (attempts >= 2 ? "S " : " ") + "LEFT.");
            }
        }

        // user loses
        System.out.println("OUT OF ATTEMPTS. ACCESS DENIED.");
        return false;
    }


    // ================== NUMBER GUESS ========================
    public static boolean guessNum(int level){
        Random rand = new Random();

        // create random number based on level
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

        // evaluate user's input
        Scanner input = new Scanner(System.in);
        System.out.println("PLEASE INTRODUCE ACCESS NUMBER (HINT: IT IS A NUMBER FROM 1 TO " + maxNum + "). YOU HAVE A MAXIMUM OF " + guessAtt + " ATTEMPTS.");
            //core game logic
        while (guessAtt > 0) {
            int guess = input.nextInt();
            if (guess == num) {
                // case user guesses and wins
                System.out.println("NUMBER GUESSED CORRECTLY. ACCESS ALLOWED.");
                return true;
            }
            guessAtt--;
            if (guess < num) {
                System.out.println("THE NUMBER IS HIGHER, PLEASE TRY AGAIN. ATTEMPTS REMAINING: " + guessAtt);
            } else {
                System.out.println("THE NUMBER IS LOWER, PLEASE TRY AGAIN. ATTEMPTS REMAINING: " + guessAtt);
            }
        }

        // case user runs out of attempts
        System.out.println("OUT OF ATTEMPTS. UNAUTHORIZED USER. ACCESS DENIED.");
        return false;
    }



    // ================== LOGIC GATE ========================
    public static void logicGate(){

    }



    // ================== HELPERS ========================
    public static String genWord(int level) {
        // generate new word from different complexity levels

        String[] easy = {"UFO", "Fog", "Hex", "Orb", "Cult", "Omen", "Rune", "Veil", "Myth", "Pact"};
        String[] medium = {"Cipher", "Shade", "Wraith", "Sigil", "Haunt", "Feral", "Shadow", "Phantom", "Oracle", "Occult", "Portal", "Relic", "Spirit", "Secret", "Cryptid", "Eclipse", "Enigma", "Ritual", "Specter", "Unknown", "Whisper", "Hidden","Conclave", "Illusion", "Intrigue"};
        String[] hard = {"Moonrise", "Poltergy", "Revenant", "Veilborn", "Grimoire",  "Apparition", "Conspiracy", "Forbidden", "Otherworld", "Watchers", "Darkcraft", "Blacksite", "Bloodmoon", "Gatekeeper", "Nightstalk", "Shadowborn", "Truthseek", "Mindcontrol", "Necromancer", "Skinwalker",  "Interdimensional", "Cryptozoology", "Paranormality", "Extraterrestrial"
        };

        Random rand = new Random();

        if (level == 1) {
            int n = rand.nextInt(10);
            return easy[n];
        } else if (level == 2) {
            int n = rand.nextInt(25);
            return medium[n];
        } else {
            int n = rand.nextInt(25);
            return hard[n];
        }
    }
}
