package hw1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class TextProcessorTest {
    /** TextProcessor used in every test */
    private TextProcessor textProcessor;

    /** Generate a master dictionary for the TextProcessor */
    @BeforeEach
    void setUp() throws IOException {
        this.textProcessor = new TextProcessor(new URL("https://www.gutenberg.org/cache/epub/64317/pg64317.txt"));
        WordDictionary wordDictionary = new WordDictionary();
        wordDictionary.generateMasterDictionary();
        textProcessor.setMasterDictionary(wordDictionary.getMasterDictionary());
    }

    /**
     * Check that a good word appears in the master dictionary
     * and a meaningless word doesn't
     */
    @Test
    void isGoodWord() {
        assertTrue(textProcessor.isGoodWord("there"));
        assertFalse(textProcessor.isGoodWord("hywduem"));
    }

    /**
     * Verify that the function to scan and filter text runs without exceptions
     */
    @Test
    void processTextAtURL() {
        assertDoesNotThrow(() -> textProcessor.processTextAtURL());
    }
}