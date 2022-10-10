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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextProcessor {
    private final String DICTIONARY_URL = "https://www.gutenberg.org/cache/epub/29765/pg29765.txt";
    private URL url;
    private Map<String, Integer> wordMap;
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
        ArrayList<String> discarded = new ArrayList<>();
        ArrayList<String> valid = new ArrayList<>();
        ArrayList<String> modified = new ArrayList<>();
        Scanner scnr = new Scanner(url.openStream());
        while (scnr.hasNext()) {
            totalWords++;
            String word = scnr.next();
            if (word.matches("[a-z]+") && word.matches("[^0-9A-Z]+")) {
                Pattern p = Pattern.compile(".+(\\p{Punct}+)");
                Matcher m = p.matcher(word);
                if (m.matches()) {
                    modified.add(word);
                    word = word.substring(0,m.start(1));
                    valid.add(word);
//                    modified.add(word);
                }
                else {
                    valid.add(word);
                }
            }
            else discarded.add(word);
        }

        for (String w : discarded) {
            System.out.println(w);
        }
//        for (String w : modified) {
//            System.out.println(w);
//        }
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

    public static void main(String[] args) throws IOException {
        TextProcessor t = new TextProcessor(new URL("https://www.bucknell.edu/"));
//        t.generateMasterDictionary();
//        for (String word : t.masterDictionary) {
//            System.out.println(word);
//        }

//        String word = "a";
//        Pattern p = Pattern.compile("[(^0-9A-Z)(a-z)]+");
//        Matcher m = p.matcher(word);
//        if (m.matches()) {
//            System.out.println(true);
//        }
//        else {
//            System.out.println(false);
//        }

        t.processTextAtURL(new URL("https://www.gutenberg.org/files/1342/1342-0.txt"));
//        System.out.println(t.totalWords);
    }
}
