/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2022
 * Instructor: Prof. Brian King
 *
 * Name: Vy Tran
 * Section: 01 - 9am
 * Date: 10/9/22
 * Time: 6:59 PM
 *
 * Project: csci205_hw
 * Package: hw1
 * Class: GuessEvaluator
 *
 * Description:
 * A class analyzing player's guess
 * ****************************************
 */

package hw1;

import java.util.ArrayList;

/**
 * A class used to analyze player's guess (i.e. check if guess is valid
 * and how close it is to the secret words)
 */
public class GuessEvaluator {
    /** Secret word used as key of the game */
    private String secretWord;
    /** List of good words used to evaluate player's guesses */
    private ArrayList<String> finalWordList;

    public GuessEvaluator() {
        this.secretWord = "";
        this.finalWordList = new ArrayList<>();
    }

    /**
     * Set word list to be the list provided
     *
     * @param finalWordList - a list of words
     */
    public void setWordList(ArrayList<String> finalWordList) {
        this.finalWordList = finalWordList;
    }

    /**
     * Set the secret word to be the word provided
     *
     * @param secretWord - string representing a word
     */
    public void setSecretWord(String secretWord) {
        this.secretWord = secretWord;
    }

    /**
     * Check if player's guess only contains letters
     *
     * @param guess - str representing player's guess
     * @return - true if guess only contains letters, false otherwise
     */
    private boolean isValidGuess(String guess) {
        return guess.matches("[a-zA-Z]+");
    }

    /**
     * Analyze the guess, print proper message to let user know about any error
     * If there's no error, print an encoded string to let user know
     * how close their guess is to the secret word
     *
     * @param guess - str representing user's guess
     * @return - encoded string if guess is valid, null otherwise
     */
    public String analyzeGuess(String guess) {
        guess = guess.toLowerCase();
        if (!isValidGuess(guess)) {
            System.out.println("Invalid guess. Only letters are allowed. Try again!");
            return null;
        }
        if (guess.length() != 5) {
            System.out.println("Invalid guess. Only 5-letter words are allowed. Try again!");
            return null;
        }
        if (!finalWordList.contains(guess)) {
            System.out.println("Your guess is not in word list. Try again!");
            return null;
        }
        String guessAnalysis = "";
        for (int i = 0; i < guess.length(); i++) {
            String letter = guess.substring(i, i+1);
            if (!secretWord.contains(letter)) {
                guessAnalysis += "-";
            }
            else {
                if (!letter.equals(secretWord.substring(i, i+1))) {
                    guessAnalysis += "+";
                }
                else {
                    guessAnalysis += "*";
                }
            }
        }
        return guessAnalysis;
    }
}
