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
import java.io.FileNotFoundException;
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
    private final int NUMBER_OF_GUESSES = 6;
    private int guessNumber;
    private String secretWord;
    private String currentGuess;
    private GameState gameState;
    private WordDictionary wordSet;
    private GuessEvaluator guessEvaluator;

    public Wordle() {
        wordSet = new WordDictionary();
        guessEvaluator  = new GuessEvaluator();
    }

    public void askUserForRegenerateFile() throws IOException {
        // words.txt exists and player doesn't want to regenerate
        if (new File("words.txt").exists()) {
            boolean doesRepeatQuestion = true;
            do {
                System.out.print("words.txt exists. Do you want to regenerate the file? [y|n]: ");
                Scanner scnr = new Scanner(System.in);
                String input = scnr.next();
                if (input.equals("Y") || input.equals("y")) {
                    wordSet. generateNewWordSet();
                    doesRepeatQuestion = false;
                }
                else if (input.equals("N") || input.equals("n")) doesRepeatQuestion = false;
                else System.out.println("Invalid answer. Only [y|n] allowed. Try again!");
            }
            while (doesRepeatQuestion);
        }
        else {
            wordSet. generateNewWordSet();
        }
    }

    public void setUpWholeGame() throws FileNotFoundException {
        ArrayList<String> wordList = wordSet.getWordListFromFile();
        guessEvaluator.setWordList(wordList);
    }

    public void initNewGame() {
        guessNumber = 0;
        currentGuess = null;
        gameState = GameState.NEW_GAME;
        // Set a secret word
        secretWord = wordSet.getRandomWord();
        guessEvaluator.setSecretWord(secretWord);
    }

    public void playOneTurn() {
        Scanner scnr = new Scanner(System.in);
        if (gameState == GameState.NEW_GAME) {
            gameState = GameState.GAME_IN_PROGRESS;
            System.out.println("Random secret word is: " + secretWord);
            System.out.println("Ready to play Wordle! You have " + NUMBER_OF_GUESSES + " guesses.");
            while (gameState == GameState.GAME_IN_PROGRESS) {
                guessNumber++;
                boolean isGuessValid;
                do {
                    System.out.print("Guess  " + guessNumber + ": ");
                    currentGuess = scnr.next();
                    // check valid or not
                    isGuessValid = guessEvaluator.analyzeGuess(currentGuess) != null;
                }
                while (!isGuessValid);
                System.out.print("   -->    " + guessEvaluator.analyzeGuess(currentGuess));
                if (!guessEvaluator.analyzeGuess(currentGuess).equals("*****")) {
                    if ((guessNumber + 1) <= NUMBER_OF_GUESSES) {
                        System.out.println("    Try again. " + (NUMBER_OF_GUESSES - guessNumber) + " guesses left.");
                    }
                    else {
                        System.out.println("    GAME OVER");
                        System.out.println("The answer is: " + secretWord);
                        gameState = GameState.GAME_LOSER;
                        System.out.println();
                    }
                }
                else {
                    System.out.println("    YOU WON! You guessed the word in " + guessNumber + " turns!");
                    gameState = GameState.GAME_WINNER;
                    System.out.println();
                }
            }
        }
    }

    public void playNextTurn() throws IOException {
        boolean doesRepeatQuestion = true;
        do {
            System.out.print("Would you like to play again? [Y/N]: ");
            Scanner scnr = new Scanner(System.in);
            String input = scnr.next();
            if (input.equals("N") || input.equals("n")) {
                System.out.println("Goodbye!");
                doesRepeatQuestion = false;
            }
            else if (input.equals("Y") || input.equals("y")) {
                initNewGame();
                playOneTurn();
            }
            else {
                System.out.println("Invalid answer. Only [Y/N] allowed");
            }
        }
        while (doesRepeatQuestion);
    }

    public static void main(String[] args) throws IOException {
        Wordle wordle = new Wordle();
        wordle.askUserForRegenerateFile();
        wordle.setUpWholeGame();
        wordle.initNewGame();

        wordle.playOneTurn();
        wordle.playNextTurn();
    }
}
