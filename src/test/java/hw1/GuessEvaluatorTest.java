package hw1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GuessEvaluatorTest {
    /** GuessEvaluator used for all tests */
    private GuessEvaluator guessEvaluator;

    @BeforeEach
    void setUp() {
        guessEvaluator = new GuessEvaluator();
    }

    /**
     * Verify that any word made up of letters only is valid
     * Check that a word having a non-letter character "_" is invalid
     */
    @Test
    void isValidGuess() {
        assertTrue(guessEvaluator.isValidGuess("HOUSE"));
        assertFalse(guessEvaluator.isValidGuess("_common"));
    }

    /**
     * Testing 4 cases
     * 1. A guess with wrong length will return null
     * 2. 3 other cases with valid guesses will return correct encoded string
     */
    @Test
    void analyzeGuess() {
        guessEvaluator.setSecretWord("house");
        ArrayList<String> finalWordList = new ArrayList<>();
        finalWordList.add("house");
        finalWordList.add("great");
        finalWordList.add("horse");
        guessEvaluator.setWordList(finalWordList);

        assertEquals(null, guessEvaluator.analyzeGuess("monkey"));
        assertEquals("--+--", guessEvaluator.analyzeGuess("great"));
        assertEquals("**-**", guessEvaluator.analyzeGuess("horse"));
        assertEquals("*****", guessEvaluator.analyzeGuess("house"));
    }
}