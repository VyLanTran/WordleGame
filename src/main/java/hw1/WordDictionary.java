/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2022
 * Instructor: Prof. Brian King
 *
 * Name: Vy Tran
 * Section: 01 - 9am
 * Date: 10/9/22
 * Time: 7:00 PM
 *
 * Project: csci205_hw
 * Package: hw1
 * Class: WordDictionary
 *
 * Description:
 * A class representing a set of unique good words
 * ****************************************
 */

package hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class that stores a list of unique words to run the game,
 * and generates a random secret word
 */

public class WordDictionary {
    /** URL address of the dictionary */
    private final String DICTIONARY_URL = "https://www.gutenberg.org/cache/epub/29765/pg29765.txt";
    /** Name of the file that stores all unique good words */
    private final String FILENAME = "words.txt";
    /** List of novels to read in */
    private final String[] LIST_OF_BOOK_NAME = new String[]{"The Great Gatsby",
                                                            "Jane Eyre",
                                                            "The Adventures of Tom Sawyer",
                                                            "The Wonderful Wizard of Oz",
                                                            "The Essays of Ralph Waldo Emerson",
                                                            "The Truth about the Titanic"};
    /** List of URL addresses of novels to read in */
    private final String[] LIST_OF_TEXT_URLS = new String[]{"https://www.gutenberg.org/cache/epub/64317/pg64317.txt",
                                                            "https://www.gutenberg.org/files/1260/1260-0.txt",
                                                            "https://www.gutenberg.org/files/74/74-0.txt",
                                                            "https://www.gutenberg.org/files/55/55-0.txt",
                                                            "https://www.gutenberg.org/cache/epub/16643/pg16643.txt",
                                                            "https://www.gutenberg.org/cache/epub/67584/pg67584.txt"};
    /** Set of words read from the dictionary */
    private TreeSet<String> masterDictionary;
    /** Set of 5-letter good words from novels after being filtered */
    private Set<String> finalWordSet;
    /** List of all unique good words filtered */
    private ArrayList<String> finalWordList;

    public WordDictionary() {
        this.masterDictionary = new TreeSet<>();
        this.finalWordSet = new TreeSet<>();
        this.finalWordList =  new ArrayList<>();
    }

    public TreeSet<String> getMasterDictionary() {
        return this.masterDictionary;
    }

    /**
     * Read in the given dictionary and add 5-letter words
     * with all uppercase letters to {@link #masterDictionary}
     *
     * @throws IOException if web page is not found
     */
    public void generateMasterDictionary() throws IOException {
        URL pageLocation = new URL(DICTIONARY_URL);
        Scanner scnr = new Scanner(pageLocation.openStream());
        while (scnr.hasNext()) {
            String word = scnr.next();
            Pattern p = Pattern.compile("[A-Z]+");
            Matcher m = p.matcher(word);
            if (word.length() == 5 && m.matches()) {
                masterDictionary.add(word.toLowerCase());
            }
        }
    }

    /**
     * Read in all given novels and store good words in {@link #finalWordSet}
     * Write all good words to a file named "words.txt"
     *
     * @throws IOException
     */
    public void generateNewWordSet() throws IOException {
        for (int i = 0; i < LIST_OF_BOOK_NAME.length; i++) {
            TextProcessor textProcessor = new TextProcessor(new URL(LIST_OF_TEXT_URLS[i]));
            textProcessor.setMasterDictionary(masterDictionary);
            textProcessor.processTextAtURL();
            finalWordSet.addAll(textProcessor.getSetOfGoodWords());
            System.out.println("Reading in " + LIST_OF_BOOK_NAME[i] + "......done");
        }
        PrintStream out = new PrintStream(FILENAME);
        for (String word : finalWordSet) {
            out.println(word);
        }
        System.out.println("Keeping " + finalWordSet.size() + " valid words for the game...");
        System.out.println("Storing word dataset as " + FILENAME + "...");
        System.out.println("READY!");
        System.out.println();
    }

    /**
     * Read words from "words.txt" and stores them in a list of words
     * This function is required because sometimes the file is reused.
     * In that case, {@link #finalWordSet}, which is created only when generating new file
     * is empty, so it can't be used
     *
     * @return - list of words read from "words.txt"
     * @throws FileNotFoundException
     */
    public ArrayList<String> getWordListFromFile() throws FileNotFoundException {
        // Read in words.txt
        Scanner scnr = new Scanner(new File(FILENAME));
        while (scnr.hasNext()) {
            finalWordList.add(scnr.next());
        }
        return finalWordList;
    }

    /**
     * Choose a random word from the word list
     *
     * @return - a word from list of good words
     */
    public String getRandomWordFromList() {
        // Generate a random number less than or equal to size of the words list
        int n = (int) (Math.random() * finalWordList.size());
        return finalWordList.get(n);
    }
}
