# Wordle Game

This game is based on the original Wordle game (https://www.nytimes.com/games/wordle/index.html)

## How to run it

Download the zip file, import it in an IDE and run the file named "Wordle.java" (path: src/main/java/hw1/Wordle.java)
<br>
<bold>OR</bold>
<br>
Open the terminal, clone the source code, and run "Wordle.java"

## Rules
- You will have 6 tries to guess a hidden 5-letter word
- After each guess, the program will evaluate how close your answer is to the key
- Letters that don't appear in the key will be marked with the "-" symbol
- Letters that do appear in the key but at wrong position will be marked with the "+" symbol
- Letter that appear at the correct position will be marked with the "*" symbol

## About the project
- This is a simple a version of the Wordle game. All instructions and inputs will appear on the console only
- The program takes into account all possible cases (e.g: words with wrong lenth, words that don't exist, player wants to play again/quit the game, etc) and gives user proper instruction to avoid termination at the middle of the game
- The dictionary of the game is created by going through 6 novels on the Internet and filtering out all valid 5-letter words
