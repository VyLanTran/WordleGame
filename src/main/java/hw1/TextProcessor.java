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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextProcessor {
    private final String DICTIONARY_URL = "https://www.gutenberg.org/cache/epub/29765/pg29765.txt";
    private URL url;
    private TreeMap<String, Integer> wordMap;
    private ArrayList<String> masterDictionary;
    private int totalWords;
    private int totalGoodWordsDiscarded;
    private int totalGoodWords;
    private int totalUniqueWords;

    public TextProcessor(URL url) {
        this.url = url;
        this.masterDictionary = new ArrayList<>();
        this.wordMap = new TreeMap<>();
        this.totalWords = 0;
        this.totalGoodWordsDiscarded = 0;
        this.totalGoodWords = 0;
        this.totalUniqueWords = 0;
    }

    private void generateMasterDictionary() throws IOException {
        URL pageLocation = new URL(DICTIONARY_URL);
        Scanner scnr = new Scanner(pageLocation.openStream());
        while (scnr.hasNext()) {
            String word = scnr.next();
            Pattern p = Pattern.compile("[A-Z]+");
            Matcher m = p.matcher(word);
            if (word.length() == 5 && m.matches() && !masterDictionary.contains(word.toLowerCase())) {
                masterDictionary.add(word.toLowerCase());
            }
        }
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
                    }
                    else if (isWordValid(word) && wordMap.containsKey(word)) {
                        wordMap.put(word, wordMap.get(word) + 1);
                    }
                }
            }
        }
    }

    private boolean isWordValid(String word) {
        return masterDictionary.contains(word);
    }

    public void printReport() {

    }

    public Set<String> getSetOfWords() {
        return null;
    }

    public void writeListOfWords(String fileName) {

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
        TextProcessor t = new TextProcessor(new URL("https://www.bucknell.edu/"));
        t.generateMasterDictionary();
//        for (String word : t.masterDictionary) {
//            System.out.println(word);
//        }

        t.processTextAtURL(new URL("https://www.gutenberg.org/files/1342/1342-0.txt"));
//        System.out.println(t.totalWords);
        System.out.println("Unique words: " + t.wordMap.size());
        List<Map.Entry<String, Integer>> frequency = t.sortByReverseFrequency(t.wordMap);
        for (Map.Entry pair : frequency) {
            System.out.println(pair.getKey() + ": " + pair.getValue());
        }
    }
}
