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
 *
 * ****************************************
 */

package hw1;

import java.util.ArrayList;

public class GuessEvaluator {
    public String secretWord;
    private String currentGuess;
    private String guessAnalysis;
    public ArrayList<String> wordList;
    public GuessEvaluator() {
//        this.secretWord = "";
//        this.currentGuess = "";
//        this.guessAnalysis = "";
//        this.wordList = new ArrayList<>();
    }

    public void setWordList(ArrayList<String> wordList) {
        this.wordList = wordList;
    }

    public void setSecretWord(String secretWord) {
        this.secretWord = secretWord;
    }

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
        if (!wordList.contains(guess)) {
            System.out.println("Your guess is not in word list. Try again!");
            return null;
        }
        String encodedString = "";
        for (int i = 0; i < guess.length(); i++) {
            String letter = guess.substring(i, i+1);
            if (!secretWord.contains(letter)) {
                encodedString += "-";
            }
            else {
                if (!letter.equals(secretWord.substring(i, i+1))) {
                    encodedString += "+";
                }
                else {
                    encodedString += "*";
                }
            }
        }

        return encodedString;
    }

    public boolean isValidGuess(String guess) {
        return guess.matches("[a-zA-Z]+");
    }

    public static void main(String[] args) {
        GuessEvaluator g = new GuessEvaluator();
        g.secretWord = "house";
        ArrayList<String> list = new ArrayList<>();
        list.add("house");
        list.add("guhse");
        g.setWordList(list);
        System.out.println(g.analyzeGuess("guhse"));
    }
}
