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
    private final int NUMBER_OF_GUESSES = 6;
    private final String FILENAME = "words.txt";
    private int guessNumber;
    private String secretWord;
    private String currentGuess;
    private GameState gameState;
    private WordDictionary wordDictionary;
    private GuessEvaluator guessEvaluator;
    private ArrayList<String> finalWordList;

    public Wordle() {
        wordDictionary = new WordDictionary();
    }

    public void initNewGame() {
        this.guessNumber = 0;
        this.secretWord = wordDictionary.getRandomWordFromList();
        this.currentGuess = "";
        this.gameState = GameState.NEW_GAME;
        this.guessEvaluator  = new GuessEvaluator();
        guessEvaluator.setSecretWord(secretWord);
        guessEvaluator.setWordList(finalWordList);
    }

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
                    // check if guess is valid
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

    public static void main(String[] args) throws IOException {
        Wordle wordle = new Wordle();
        wordle.askUserForRegenerateFile();
        wordle.initNewGame();
        wordle.playOneTurn();
        wordle.playNextTurn();
    }
}
