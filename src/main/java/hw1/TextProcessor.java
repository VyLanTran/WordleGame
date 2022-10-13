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
 * Class: TextProcessor
 *
 * Description:
 * A class that reads in a stream of text, filter and clean to get
 * good words that can be used for the game
 * ****************************************
 */

package hw1;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class that reads in the content from an url address, filter and modify
 * to get all valid words that can be used to run the game
 */
public class TextProcessor {
    /** URL address of a web page */
    private URL url;
    /** A map storing good words and its frequency in the text */
    private TreeMap<String, Integer> wordMap;
    /** Set of all good words after filtering */
    private TreeSet<String> goodWords;
    /** Set of all 5-letter words in the dictionary */
    private TreeSet<String> masterDictionary;
    /** Total number of words processed in a book */
    private int totalWords;
    /** Total number of words kept after filtered */
    private int totalWordsKept;
    /** List of valid words but discarded because of wrong length */
    private ArrayList<String> validButWrongLength;

    public TextProcessor(URL url) {
        this.url = url;
        this.masterDictionary = new TreeSet<>();
        this.wordMap = new TreeMap<>();
        this.goodWords = new TreeSet<>();
        this.totalWords = 0;
        this.validButWrongLength = new ArrayList<>();
    }

    /**
     * Set master dictionary to be the set of words provided
     *
     * @param masterDictionary - a set of words provided
     */
    public void setMasterDictionary(TreeSet<String> masterDictionary) {
        this.masterDictionary = masterDictionary;
    }

    /**
     * Check if a word appeared in the dictionary
     *
     * @param word - word to check
     * @return - true if word is in dictionary, false otherwise
     */
    private boolean isGoodWord(String word) {
        return masterDictionary.contains(word);
    }

    /**
     * Get the set of good words
     *
     * @return set of good words
     */
    public Set<String> getSetOfGoodWords() {
        return goodWords;
    }

    /**
     * Scan through the stream of text from the URL, only keep valid 5-letter
     * words or word that can be cleaned to become valid words
     *
     * @throws IOException if web page is not found
     */
    public void processTextAtURL() throws IOException {
        Scanner scnr = new Scanner(url.openStream());
        while (scnr.hasNext()) {
            totalWords++;
            String word = scnr.next();
            // Discard words that contains numbers, uppercase letters, or contractions
            if (word.matches("^[^0-9A-Z]*[a-z]+[^0-9A-Z]*$") && !word.contains("’")) {
                // Clean quotation marks if it appeared before/after a word
                word = word.replaceAll("[”“]","");
                // Clean all other special marks before/after a word that can be turned into a valid one
                Pattern p = Pattern.compile("(\\p{Punct}*)([a-z]+)(\\p{Punct}*)");
                Matcher m = p.matcher(word);
                if (m.matches()) {
                    word = m.group(2);
                    if (isGoodWord(word) && !wordMap.containsKey(word)) {
                        wordMap.put(word, 1);
                        goodWords.add(word);
                        totalWordsKept++;
                    }
                    else if (isGoodWord(word) && wordMap.containsKey(word)) {
                        wordMap.put(word, wordMap.get(word) + 1);
                        goodWords.add(word);
                        totalWordsKept++;
                    }
                    else {validButWrongLength.add(word);}
                }
            }
        }
    }

    /**
     * Print some information about words discarded, words kept, and most popular words
     */
    public void printReport() {
        System.out.println("Total number of words processed: " + totalWords);
        System.out.println("Total good words by wrong length: " + validButWrongLength.size());
        System.out.println("Total number of words kept: " + totalWordsKept);
        System.out.println("Number of unique words: " + wordMap.size());
        System.out.println("Top 20 most frequently occurring words");
        List<Map.Entry<String, Integer>> frequency = sortByReverseFrequency(wordMap);
        for (Map.Entry<String, Integer> pair : frequency) {
            System.out.println(pair.getKey() + ": " + pair.getValue());
        }
    }

    /**
     * Sort the map of words from highest to lowest frequency
     *
     * @param map - map of word-frequency
     * @return - list of pairs of word-frequency after sorting
     */
    private List<Map.Entry<String, Integer>> sortByReverseFrequency(TreeMap<String, Integer> map) {
        List<Map.Entry<String, Integer>> listOfKeysValues = new LinkedList<>();
        for (Map.Entry<String, Integer> pair : map.entrySet()) {
            listOfKeysValues.add(pair);
        }
        Comparator<Map.Entry<String, Integer>> comparator = Comparator.comparing(Map.Entry<String, Integer>::getValue);
        Collections.sort(listOfKeysValues, comparator.reversed());
        return listOfKeysValues;
    }
}
