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
//    private ArrayList<String> masterDictionary;
//    private ArrayList<String> fullMasterDictionary;
    private Set<String> masterDictionary;
    private int totalWords;
    private int totalGoodWordsDiscarded;
    private int totalGoodWords;
    private int totalUniqueWords;

    private ArrayList<String> goodWords;
    private ArrayList<String> invalidWords;
    private ArrayList<String> validButWrongLength;
    private ArrayList<String> otherErrors;

    public TextProcessor(URL url) {
        this.url = url;
        this.masterDictionary = new TreeSet<>();
        this.wordMap = new TreeMap<>();
        this.totalWords = 0;
        this.totalGoodWordsDiscarded = 0;
        this.totalGoodWords = 0;
        this.totalUniqueWords = 0;

//        this.fullMasterDictionary = new ArrayList<>();
        this.goodWords = new ArrayList<>();
        this.invalidWords = new ArrayList<>();
        this.validButWrongLength = new ArrayList<>();
        this.otherErrors = new ArrayList<>();
    }

    private void generateMasterDictionary() throws IOException {
        URL pageLocation = new URL(DICTIONARY_URL);
        Scanner scnr = new Scanner(pageLocation.openStream());
        while (scnr.hasNext()) {
            String word = scnr.next();
            Pattern p = Pattern.compile("[A-Z]+");
            Matcher m = p.matcher(word);
//            if (m.matches() && !fullMasterDictionary.contains(word.toLowerCase())) {
//                fullMasterDictionary.add(word.toLowerCase());
//            }
//            if (word.length() == 5 && m.matches() && !masterDictionary.contains(word.toLowerCase())) {
//                masterDictionary.add(word.toLowerCase());
//            }
            if (word.length() == 5 && m.matches()) {
                masterDictionary.add(word.toLowerCase());
            }
        }
//        for (String w : fullMasterDictionary) {
//            if (w.length() == 5) {
//                masterDictionary.add(w);
//            }
//        }
//        System.out.println(fullMasterDictionary.size());
//        for (String w : fullMasterDictionary) {
//            System.out.println(w);
//        }
    }

    private void processTextAtURL(URL url) throws IOException {
        generateMasterDictionary();
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
                    }
                    else if (isWordValid(word) && wordMap.containsKey(word)) {
                        wordMap.put(word, wordMap.get(word) + 1);
                        goodWords.add(word);
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
        totalGoodWords = goodWords.size();
        totalGoodWordsDiscarded = totalWords - totalGoodWords;
        totalUniqueWords = wordMap.size();

        System.out.println("Total number of words processed: " + totalWords);
        System.out.println("Total good words by wrong length: " + validButWrongLength.size());
        System.out.println("Total number of words kept: " + totalGoodWords);
        System.out.println("Number of unique words: " + totalUniqueWords);
        System.out.println("Top 20 most frequently occurring words");
        List<Map.Entry<String, Integer>> frequency = sortByReverseFrequency(wordMap);
        for (Map.Entry pair : frequency) {
            System.out.println(pair.getKey() + ": " + pair.getValue());
        }


//        for (String w : invalidWords) {
//            System.out.println(w);
//        }
        System.out.println("Invalid: " + invalidWords.size());

//        for (String w : validButWrongLength) {
//            System.out.println(w);
//        }
        System.out.println("Valid but wrong length: " + validButWrongLength.size());
//
//        for (String w : otherErrors) {
//            System.out.println(w);
//        }
        System.out.println("Other: " + otherErrors.size());

//        for (String w : goodWords) {
//            System.out.println(w);
//        }
        System.out.println("Valid: " + goodWords.size());

        System.out.println("Unique words: " + wordMap.size());
    }

    public Set<String> getSetOfWords() throws IOException {
        processTextAtURL(url);
        TreeSet<String> setOfGoodWords = new TreeSet<>();
        for (String word : wordMap.keySet()) {
            setOfGoodWords.add(word);
        }
        return setOfGoodWords;
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
        TextProcessor t = new TextProcessor(new URL("https://www.gutenberg.org/files/1342/1342-0.txt"));
//        t.generateMasterDictionary();
//        for (String word : t.masterDictionary) {
//            System.out.println(word);
//        }

//        t.printReport();
        Set<String> s = t.getSetOfWords();
        System.out.println(s.size());
//        for (String w : s) {
//            System.out.println(w);
//        }
    }
}
