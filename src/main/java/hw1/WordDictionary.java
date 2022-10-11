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
 *
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

public class WordDictionary {
    private final String DICTIONARY_URL = "https://www.gutenberg.org/cache/epub/29765/pg29765.txt";
    private TreeSet<String> masterDictionary;
    private final String fileName = "words.txt";
    public String[] LIST_OF_BOOK_NAME;
    public String[] LIST_OF_TEXT_URLS;
    private Set<String> wordSet;
    private TextProcessor textProcessor;

    public WordDictionary(String fileName) {
        this.LIST_OF_BOOK_NAME = new String[]{"Pride and Prejudice",
                                                "The Adventures of Sherlock Holmes",
                                                "The Jazz Singer",
                                                "The Adventures of Huckleberry Finn",
                                                "The Essays of Ralph Waldo Emerson",
                                                "The Truth about the Titanic"};
        this.LIST_OF_TEXT_URLS = new String[]{"https://www.gutenberg.org/files/1342/1342-0.txt",
                                              "https://www.gutenberg.org/files/1661/1661-0.txt",
                                              "https://www.gutenberg.org/cache/epub/67583/pg67583.txt",
                                              "https://www.gutenberg.org/files/76/76-0.txt",
                                              "https://www.gutenberg.org/cache/epub/16643/pg16643.txt",
                                              "https://www.gutenberg.org/cache/epub/67584/pg67584.txt"};
        this.masterDictionary = new TreeSet<>();
        this.wordSet = new TreeSet<>();
        this.textProcessor = null;
    }

    private void generateMasterDictionary() throws IOException {
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

    private void generateNewWordSet() throws IOException {
        generateMasterDictionary();
        for (int i = 0; i < LIST_OF_BOOK_NAME.length; i++) {
            textProcessor = new TextProcessor(new URL(LIST_OF_TEXT_URLS[i]), masterDictionary);
            wordSet.addAll(textProcessor.getSetOfWords());
            System.out.println("Reading in " + LIST_OF_BOOK_NAME[i] + "......done");
        }
        PrintStream out = new PrintStream(fileName);
        for (String word : wordSet) {
            out.println(word);
        }
        System.out.println("Keeping " + wordSet.size() + " valid words for the game...");
        System.out.println("Storing word dataset as " + fileName + "...");
    }

//    public void addWords(List<String> wordList) {
//
//    }

    public boolean isWordInSet(String word) {
        return wordSet.contains(word);
    }

    public String getRandomWord() throws FileNotFoundException {
        // Read in words.txt
        Scanner scnr = new Scanner(new File(fileName));
        ArrayList<String> words = new ArrayList<>();
        while (scnr.hasNext()) {
            words.add(scnr.next());
        }
        // Generate a random number <= size of the words set
        int n = (int) (Math.random() * words.size());
        return words.get(n);
    }

    public static void main(String[] args) throws IOException {
        WordDictionary dict = new WordDictionary("https://www.gutenberg.org/files/1342/1342-0.txt");
        dict.generateNewWordSet();
        System.out.println();

        for (int i = 0; i < 200; i++) {
            System.out.println(dict.getRandomWord());
        }
    }
}
