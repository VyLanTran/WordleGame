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
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextProcessor {
    private URL url;
    private TreeMap<String, Integer> wordMap;
    private TreeSet<String> goodWords;
    private TreeSet<String> masterDictionary;
    private int totalWords;
    private int totalWordsKept;
    private ArrayList<String> validButWrongLength;

    public TextProcessor(URL url) {
        this.url = url;
        this.masterDictionary = new TreeSet<>();
        this.wordMap = new TreeMap<>();
        this.goodWords = new TreeSet<>();
        this.totalWords = 0;
        this.validButWrongLength = new ArrayList<>();
    }

    public void setMasterDictionary(TreeSet<String> masterDictionary) {
        this.masterDictionary = masterDictionary;
    }

    private boolean isGoodWord(String word) {
        return masterDictionary.contains(word);
    }

    public Set<String> getSetOfGoodWords() {
        return goodWords;
    }

    public void processTextAtURL() throws IOException {
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
