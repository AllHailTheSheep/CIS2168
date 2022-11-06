package lab8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CheatingHangman {
    private static Logger log = LogManager.getLogger();

    private static Set<Character> guessed = new HashSet<Character>();
    private static int incorrectGuesses = 0;
    private static List<Character> board = new ArrayList<Character>();
    private static  Integer SIZE = null;

    public static void main(String[] args) {
        // load from file and sort into a map by length
        Map<Integer, Set<String>> dict = readDictionary();
        
        for (Map.Entry<Integer, Set<String>> entry : dict.entrySet()) {
            log.debug("loaded " + entry.getValue().size() + " words of size " + entry.getKey());
        }

        // take size input
        Scanner in = new Scanner(System.in);
        SIZE = getIntegerInputBetweenBounds("How many letters should the word you want to find be? ",
                                            Collections.min(dict.keySet()), Collections.max(dict.keySet()), in);
        log.debug("word size set to " + SIZE);

        // initialize things for the loop
        Set<String> words = dict.get(SIZE);
        
        for (int i = 0; i < SIZE; i++) {
            board.add('_');
        }
        boolean gameOver = false;
        while (!gameOver) {
            // summarize
            summarize(guessed, incorrectGuesses, board);

            // take next user input and add to guessed
            Character guess = getCharacterAlphabeticalInput("Next guess: ", in);
            guessed.add(guess);

            log.debug(guess);
            // determine new possible word groups and choose best option
            words = chooseNextWords(words, guess);
            String[] wordsArray = words.toArray(new String[0]);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < wordsArray.length; i++) {
                if (i % 20 == 0) {
                    sb.append("\n");
                }
                if (i == 0) {
                    sb.append(wordsArray[i]);
                } else {
                    sb.append(", " + wordsArray[i]);
                }
            }
            log.debug(sb.toString());

            // update board
            ArrayList<Integer> key = wordToFamily(wordsArray[0], guess);
            for (int i = 0; i < wordsArray[0].length(); i++) {
                if (key.get(i) == 1) {
                    board.set(i, guess);
                }
            }

            // check win/loss conditions
            if (incorrectGuesses == 6) {
                summarize(guessed, incorrectGuesses, board);
                System.out.println("Game over! The correct word was " + wordsArray[0]);
                gameOver = true;
            }
            if (!board.contains('_')) {
                System.out.println("Congratulations! You win!");
                gameOver = true;
            }

        }



    }

    private static Set<String> chooseNextWords(Set<String> words, Character guess) {
        Map<ArrayList<Integer>, Set<String>> groups = new HashMap<ArrayList<Integer>, Set<String>>();
        for (String word : words) {
            log.debug("word is: " + word);
            ArrayList<Integer> key = wordToFamily(word, guess);
            if (groups.containsKey(key)) {
                groups.get(key).add(word);
                StringBuilder sb = new StringBuilder();
                sb.append("added word " + word + " to key ");
                for (int i : key) {
                    sb.append(i + " ");
                }
                log.debug(sb.toString());
            } else {
                StringBuilder sb = new StringBuilder();
                groups.put(key, new HashSet<String>(Arrays.asList(word)));
                sb.append("put word " + word + " in key ");
                for (int i : key) {
                    sb.append(i + " ");
                }
                log.debug(sb.toString());
            }
        }
        ArrayList<Integer> bestKey = null;
        int highest = Integer.MIN_VALUE;
        for (Map.Entry<ArrayList<Integer>, Set<String>> entry : groups.entrySet()) {
            if (entry.getValue().size() > highest) {
                bestKey = entry.getKey();
                highest = entry.getValue().size();
            }
        }
        Set<String> bestWords = groups.get(bestKey);
        log.info("Next word family: " + bestKey);
        if (!bestKey.contains(1)) {
            incorrectGuesses++;
        }
        return bestWords;
    }

    private static ArrayList<Integer> wordToFamily(String word, Character guess) {
        ArrayList<Integer> key = new ArrayList<Integer>();
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guess) {
                key.add(1);
            } else {
                key.add(0);
            }
        }
        return key;
    }
    

    private static void summarize(Set<Character> guessed, int incorrectGuesses, List<Character> board) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        if (guessed.size() > 0) {
            sb.append("Guessed Letters: ");
            int i = 0;
            for (Character c : guessed) {
                if (i == 0) {
                    sb.append(c);
                    i++;
                } else {
                    sb.append(", " + c);
                }
            }
            sb.append("\n");
        }
        switch(incorrectGuesses) {
            // these ascii pics came from https://gist.github.com/chrishorton/8510732aa9a80a03c829b09f12e20d9c
            // i copied and pasted, then ran them through replaceAll("\n", "\\n")
            case 0: {
                sb.append("  +---+\n  |   |\n      |\n      |\n      |\n      |\n=========");
                break;
            }
            case 1: {
                sb.append("  +---+\n  |   |\n  O   |\n      |\n      |\n      |\n=========");
                break;
            }
            case 2: {
                sb.append("  +---+\n  |   |\n  O   |\n  |   |\n      |\n      |\n=========");
                break;
            }
            case 3: {
                sb.append("  +---+\n  |   |\n  O   |\n /|   |\n      |\n      |\n=========");
                break;
            }
            case 4: {
                sb.append("  +---+\n  |   |\n  O   |\n /|\\  |\n      |\n      |\n=========");
                break;
            }
            case 5: {
                sb.append("  +---+\n  |   |\n  O   |\n /|\\  |\n /    |\n      |\n=========");
                break;
            }
            case 6: {
                sb.append("  +---+\n  |   |\n  O   |\n /|\\  |\n / \\  |\n      |\n=========");
            }
        }
        sb.append("\n");
        for (Character c : board) {
            sb.append(c + " ");
        }
        log.info(sb.toString());
        System.out.println(sb.toString());
    }


    private static Map<Integer, Set<String>> readDictionary() {
        Map<Integer, Set<String>> dictionary = new HashMap<Integer, Set<String>>();
        try {
            Scanner fileIn = new Scanner(new File("lab8/dictionary.txt"));
            while (fileIn.hasNextLine()) {
                String line = fileIn.nextLine();
                int l = line.length();
                if (dictionary.containsKey(l)) {
                    dictionary.get(l).add(line.toLowerCase());
                } else {
                    dictionary.put(l, new HashSet<String>(Arrays.asList(line)));
                }

            }
        } catch (FileNotFoundException e) {
            System.err.println("Unable to open file.");
            e.printStackTrace();
        }
        return dictionary;
    }


    private static boolean validateIntBetweenBounds(int v, int lowerBound, int upperBound) {
        // search through dictionary to make sure there are words of that size
        if (v >= lowerBound && v <= upperBound) {
            return true;
        }
        return false;
    }

    private static  Integer getIntegerInputBetweenBounds(String prompt, int lowerBound, int upperBound, Scanner in) {
        if (prompt != null ){
            System.out.print(prompt);
        }
        Integer res = null;
        if (in.hasNextInt()){
            res = in.nextInt();
            if (!validateIntBetweenBounds(res, lowerBound, upperBound)) {
                System.out.println("Input must be from " + lowerBound + " to " + upperBound + "!");
                in.nextLine();
                res = getIntegerInputBetweenBounds(prompt, lowerBound, upperBound, in);
            }
        } else {
            System.out.println("Input must be an integer!");
            in.nextLine();
            res = getIntegerInputBetweenBounds(prompt, lowerBound, upperBound, in);
            if (!validateIntBetweenBounds(res, lowerBound, upperBound)) {
                in.nextLine();
                res = getIntegerInputBetweenBounds(prompt, lowerBound, upperBound, in);
            }
        }
        return res;
    }
    
    private static Character getCharacterAlphabeticalInput(String prompt, Scanner in) {
        if (prompt != null ){
            System.out.print(prompt);
        }
        Character res = null;
        if (in.hasNext("[a-zA-Z]")){
            res = in.next().charAt(0);
            if (guessed.contains(res)) {
                System.out.println("You've already guessed " + res);
                Character[] guessedArray = guessed.toArray(new Character[guessed.size()]);
                for (int i = 0; i < guessed.size(); i++) {
                    if (i == 0) {
                        System.out.print(guessedArray[i]);
                    } else {
                        System.out.print(", " + guessedArray[i]);
                    }
                }
                System.out.println();
                in.nextLine();
                res = getCharacterAlphabeticalInput(prompt, in);
            }
        } else {
            System.out.println("Input must be a character from a-z or A-Z!");
            in.nextLine();
            res = getCharacterAlphabeticalInput(prompt, in);
            if (!Character.isAlphabetic(res)) {
                in.nextLine();
                res = getCharacterAlphabeticalInput(prompt, in);
            }
        }
        return res;
    }
}
