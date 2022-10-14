package hw1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class WordDictionaryTest {
    /** WordDictionary used for all tests */
    private WordDictionary wordDictionary;

    @BeforeEach
    void setUp() {
        wordDictionary = new WordDictionary();
    }

    /**
     * Check that the function to generate a master dictionary works
     * without any exception
     */
    @Test
    void generateMasterDictionary() {
        assertDoesNotThrow(() -> wordDictionary.generateMasterDictionary());
    }

    /**
     * Check that the function to read in all novels works
     * well without any exception
     */
    @Test
    void generateNewWordSet() {
        assertDoesNotThrow(() -> wordDictionary.generateNewWordSet());
    }

    /**
     * Verify that a word with wrong length will not be a random secret word
     * @throws IOException
     */
    @Test
    void getRandomWordFromList() throws IOException {
        wordDictionary.generateMasterDictionary();
        wordDictionary.generateNewWordSet();
        wordDictionary.getWordListFromFile();
        assertNotEquals("complicated", wordDictionary.getRandomWordFromList());
    }
}