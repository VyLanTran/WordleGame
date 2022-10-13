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
 *
 * ****************************************
 */

package hw1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextProcessor {
    private URL url;
    private TreeMap<String, Integer> wordMap;
    private TreeSet<String> goodWords;
    private TreeSet<String> masterDictionary;
    private int totalWords;
    private int totalGoodWordsDiscarded;
    private int totalGoodWords;
    private int totalUniqueWords;

    private ArrayList<String> invalidWords;
    private ArrayList<String> validButWrongLength;
    private ArrayList<String> otherErrors;

    public TextProcessor(URL url, TreeSet<String> masterDictionary) {
        this.url = url;
        this.masterDictionary = masterDictionary;
        this.wordMap = new TreeMap<>();
        this.goodWords = new TreeSet<>();
        this.totalWords = 0;
        this.totalGoodWordsDiscarded = 0;
        this.totalGoodWords = 0;
        this.totalUniqueWords = 0;

        this.invalidWords = new ArrayList<>();
        this.validButWrongLength = new ArrayList<>();
        this.otherErrors = new ArrayList<>();
    }

    private void processTextAtURL(URL url) throws IOException {
        Scanner scnr = new Scanner(url.openStream());
        while (scnr.hasNext()) {
            totalWords++;
            String word = scnr.next();
            if (word.matches("^[^0-9A-Z]*[a-z]+[^0-9A-Z]*$") && !word.contains("’")) {
                word = word.replaceAll("[”“]","");
                Pattern p = Pattern.compile("(\\p{Punct}*)([a-z]+)(\\p{Punct}*)");
                Matcher m = p.matcher(word);
                if (m.matches()) {
                    word = m.group(2);
                    if (isWordValid(word) && !wordMap.containsKey(word)) {
                        wordMap.put(word, 1);
                        goodWords.add(word);
                        totalGoodWords++;
                    }
                    else if (isWordValid(word) && wordMap.containsKey(word)) {
                        wordMap.put(word, wordMap.get(word) + 1);
                        goodWords.add(word);
                        totalGoodWords++;
                    }
                    else {validButWrongLength.add(word);}
                }
                else {otherErrors.add(word);}
            }
            else {invalidWords.add(word);}
        }
    }

    private boolean isWordValid(String word) {
        return masterDictionary.contains(word);
    }

    public void printReport() {
        totalGoodWordsDiscarded = totalWords - totalGoodWords;
        totalUniqueWords = wordMap.size();

        System.out.println("Total number of words processed: " + totalWords);
        System.out.println("------------------------");
        System.out.println("Total good words by wrong length: " + validButWrongLength.size());
        System.out.println("Total number of words kept: " + totalGoodWords);
        System.out.println("Invalid words: " + invalidWords.size());
        System.out.println("Words discarded by other errors: " + otherErrors.size());
        System.out.println("------------------------");
        System.out.println("Number of unique words: " + totalUniqueWords);
        System.out.println("Top 20 most frequently occurring words");
//        List<Map.Entry<String, Integer>> frequency = sortByReverseFrequency(wordMap);
//        for (Map.Entry pair : frequency) {
//            System.out.println(pair.getKey() + ": " + pair.getValue());
//        }

        int count = 0;
//        for (String word : validButWrongLength) {
//            if (word.length() == 5) {
//                count++;
//                System.out.println(word);
//            }
//        }
        System.out.println("Should not delete: " + count);


//        for (String w : validButWrongLength) {
//            System.out.println(w);
//        }
        System.out.println("Word with other errors and will be discarded: ");
//        for (String w : otherErrors) {
//            System.out.println(w);
//        }

//        for (String w : goodWords) {
//            System.out.println(w);
//        }

        System.out.println("-------------------------------");
        System.out.println("Invalid words: ");
//        for (String w : invalidWords) {
//            System.out.println(w);
//        }

    }

    public Set<String> getSetOfWords() throws IOException {
        processTextAtURL(url);
        return goodWords;
    }

    private List<Map.Entry<String, Integer>> sortByReverseFrequency(TreeMap<String, Integer> map) {
        List<Map.Entry<String, Integer>> listOfKeysValues = new LinkedList<>();
        for (Map.Entry<String, Integer> pair : map.entrySet()) {
            listOfKeysValues.add(pair);
        }

        Comparator<Map.Entry> comparator = Comparator.comparing(Map.Entry<String, Integer>::getValue);
        Collections.sort(listOfKeysValues, comparator.reversed());

        return listOfKeysValues;
    }

    public static void main(String[] args) throws IOException {
        WordDictionary dict = new WordDictionary();
        dict.generateMasterDictionary();
        TextProcessor t = new TextProcessor(new URL("https://www.gutenberg.org/files/1342/1342-0.txt"), dict.masterDictionary);
        t.processTextAtURL(new URL("https://www.gutenberg.org/files/1342/1342-0.txt"));
        t.printReport();
    }
}
