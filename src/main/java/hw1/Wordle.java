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

/**
 * Different states of the game
 */
enum GameState {
    NEW_GAME,               // At the beginning of the game, ready to play
    GAME_IN_PROGRESS,       // In the middle of the game
    GAME_WINNER,            // Player wins, stop the game
    GAME_LOSER              // Player loses, stop the game
}

/**
 * A class that controls the overall flow of the game, operates how the game is
 * running, and gives proper instruction to guide user through the game
 */
public class Wordle {
    /** Number of guesses allowed */
    private final int NUMBER_OF_GUESSES = 6;
    /** Name of the file that stored valid words */
    private final String FILENAME = "words.txt";
    /** Number of guesses player took so far */
    private int guessNumber;
    /** Secret word of the game */
    private String secretWord;
    /** Current guess of player */
    private String currentGuess;
    /** Current state of the game */
    private GameState gameState;
    /** A WordDictionary object that holds valid words */
    private WordDictionary wordDictionary;
    /** A GuessEvaluator object to assess guesses */
    private GuessEvaluator guessEvaluator;
    /** List of good words */
    private ArrayList<String> finalWordList;

    public Wordle() {
        wordDictionary = new WordDictionary();
    }

    /**
     * Reset the state and other statistic of the game to start a new turn
     */
    public void initNewGame() {
        this.guessNumber = 0;
        this.secretWord = wordDictionary.getRandomWordFromList();
        this.currentGuess = "";
        this.gameState = GameState.NEW_GAME;
        this.guessEvaluator  = new GuessEvaluator();
        guessEvaluator.setSecretWord(secretWord);
        guessEvaluator.setWordList(finalWordList);
    }

    /**
     * Check if "words.txt" exists, ask user if they want to reuse the file or regenerate
     * a new one. If the file doesn't exist, automatically create a new file.
     * Read words from the file and store in a list
     *
     * @throws IOException
     */
    public void askUserForRegenerateFile() throws IOException {
        // If "words.txt" exists, ask player if they want to regenerate the file
        if (new File(FILENAME).exists()) {
            boolean doesRepeatQuestion = true;
            do {
                System.out.print("\"" + FILENAME + "\" exists. Do you want to regenerate the file? [y|n]: ");
                Scanner scnr = new Scanner(System.in);
                String input = scnr.next();
                if (input.equals("Y") || input.equals("y")) {
                    wordDictionary.generateMasterDictionary();
                    wordDictionary. generateNewWordSet();
                    doesRepeatQuestion = false;
                }
                else if (input.equals("N") || input.equals("n"))
                    doesRepeatQuestion = false;
                else
                    System.out.println("Invalid answer. Only [y|n] allowed. Try again!");
            }
            while (doesRepeatQuestion);
        }
        // If "words.txt" doesn't exist, automatically generate a new file
        else {
            wordDictionary.generateMasterDictionary();
            wordDictionary. generateNewWordSet();
        }
        finalWordList = wordDictionary.getWordListFromFile();
    }

    /**
     * Operate one turn of the game
     */
    public void playOneTurn() {
        Scanner scnr = new Scanner(System.in);
        if (gameState == GameState.NEW_GAME) {
            gameState = GameState.GAME_IN_PROGRESS;

            // Remove the comment of this line if you want to see the key beforehand
//            System.out.println("***** Random secret word is: " + secretWord + " *****");
            System.out.println("Ready to play Wordle! You have " + NUMBER_OF_GUESSES + " guesses.");
            while (gameState == GameState.GAME_IN_PROGRESS) {
                guessNumber++;
                boolean isGuessValid;
                // Allow user to try again if the guess is invalid (eg. not in correct form,
                // wrong length, not exist in word list)
                do {
                    System.out.print("Guess  " + guessNumber + ": ");
                    currentGuess = scnr.next();
                    // Check if guess is valid
                    isGuessValid = guessEvaluator.analyzeGuess(currentGuess) != null;
                }
                while (!isGuessValid);

                // Print out the encoded guess after analyzed
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

    /**
     * Ask user if they want to play again
     * If they do, reset the game and rerun it
     */
    public void playNextTurn() {
        Scanner scnr = new Scanner(System.in);
        boolean doesRepeatQuestion = true;
        do {
            System.out.print("Would you like to play again? [Y/N]: ");
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

    /**
     * Main function of the program to demonstrate the game
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Wordle wordle = new Wordle();
        wordle.askUserForRegenerateFile();
        wordle.initNewGame();
        wordle.playOneTurn();
        wordle.playNextTurn();
    }
}
