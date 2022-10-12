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
 * Class: Wordle
 *
 * Description:
 *
 * ****************************************
 */

package hw1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

enum GameState {
    NEW_GAME,
    GAME_IN_PROGRESS,
    GAME_WINNER,
    GAME_LOSER
}

public class Wordle {
    private int guessNumber;
    private String secretWord;
    private String currentGuess;
    private GameState gameState;
    private WordDictionary wordSet;
    private GuessEvaluator guessEvaluator;

    public Wordle() {
        guessNumber = 0;
        currentGuess = null;
        wordSet = new WordDictionary();
        guessEvaluator  = new GuessEvaluator();
        gameState = GameState.NEW_GAME;
    }

    public void initNewGame() throws IOException {
        new Wordle();

        boolean doesRegenerate = true;
        // words.txt exists and player doesn't want to regenerate
        if (new File("words.txt").isFile()) {
            System.out.print("words.txt exists. Do you want to regenerate the file? [y|n]: ");
            Scanner scnr = new Scanner(System.in);
            if (scnr.next().equals("n")) doesRegenerate = false;
        }
        // words.txt doesn't exist or player wants to regenerate
        if (doesRegenerate) {
            wordSet.generateNewWordSet();
        }

        ArrayList<String> wordList = wordSet.getWordListFromFile();
//        System.out.println("Word list should have 1344 words: " + wordList.size());
        // Set a secret word
        this.secretWord = wordSet.getRandomWord();
        guessEvaluator.setWordList(wordList);
        guessEvaluator.setSecretWord(secretWord);
    }

    public void playGame() {
        Scanner scnr = new Scanner(System.in);
        if (gameState == GameState.NEW_GAME) {
            gameState = GameState.GAME_IN_PROGRESS;
            System.out.println("Ready to play Wordle! You have 6 guesses.");
            while (gameState == GameState.GAME_IN_PROGRESS) {
                if (guessNumber <= 6) {
                    guessNumber++;
                    System.out.print("Guess " + guessNumber + ": ");
                    currentGuess = scnr.next();
//                    System.out.println(currentGuess);
                    // check valid or not
                    System.out.print("   -->   " + guessEvaluator.analyzeGuess(currentGuess));
                    if (!guessEvaluator.analyzeGuess(currentGuess).equals("*****")) {
                        if ((guessNumber + 1) <= 6) {
                            System.out.println("    Try again. " + (6 - guessNumber) + " guesses left.");
                        }
                        else {
                            System.out.println("    GAME OVER");
                            System.out.println("The answer is: " + secretWord);
                            gameState = GameState.GAME_LOSER;
                        }
                    }
                    else {
                        System.out.println("    YOU WON! You guessed the word in " + guessNumber + " turns!");
                        gameState = GameState.GAME_WINNER;
                    }

                }
            }
        }
    }

    public void playNextTurn() {

    }

    public boolean isGameOver() {
        return gameState == GameState.GAME_LOSER;
    }

    public static void main(String[] args) throws IOException {
        Wordle wordle = new Wordle();
        wordle.initNewGame();
//        System.out.println("Guess number should be 0: " + wordle.guessNumber);
//        System.out.println("Game state should be new game: " + wordle.gameState);
        System.out.println("Random secret word is: " + wordle.secretWord);
        wordle.playGame();
    }
}
