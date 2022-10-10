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

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class WordDictionary {
    public String[] LIST_OF_TEXT_URLS;
    private Set<String> wordSet;
    private TextProcessor textProcessor;

    public WordDictionary(String fileName) {
        this.LIST_OF_TEXT_URLS = new String[]{"https://www.gutenberg.org/files/1342/1342-0.txt",
                                              "https://www.gutenberg.org/files/1661/1661-0.txt",
                                              "https://www.gutenberg.org/cache/epub/67583/pg67583.txt",
                                              "https://www.gutenberg.org/files/76/76-0.txt",
                                              "https://www.gutenberg.org/cache/epub/16643/pg16643.txt",
                                              "https://www.gutenberg.org/cache/epub/67584/pg67584.txt"};
        this.wordSet = new TreeSet<>();
        this.textProcessor = null;
    }

    private void generateNewWordSet() throws IOException {
        for (String urlString : LIST_OF_TEXT_URLS) {
            textProcessor = new TextProcessor(new URL(urlString));
            wordSet.addAll(textProcessor.getSetOfWords());
        }
    }

//    public void addWords(List<String> wordList) {
//
//    }

    public boolean isWordInSet(String word) {
        return false;
    }

    public String getRandomWord() {
        return "";
    }

    public static void main(String[] args) throws IOException {
        WordDictionary dict = new WordDictionary("https://www.gutenberg.org/files/1342/1342-0.txt");
        dict.generateNewWordSet();
        for (String word : dict.wordSet) {
            System.out.println(word);
        }
    }
}
