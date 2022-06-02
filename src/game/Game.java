package game;

import exceptions.IncorrectGuessLengthException;
import util.Type;

public class Game {
    private String word;
    public String[] guesses;
    public Type[][] validation;

    public final int maxGuesses;
    public final int wordLength;
    public int totalGuesses;

    public Game() {this(6, 5);}

    public Game(int maxGuesses, int wordLength) {
        this.maxGuesses = maxGuesses;
        this.wordLength = wordLength;
        guesses = new String[maxGuesses];
        totalGuesses = 0;
        newWord();
    }

    private void newWord() {
        word = "CRANE";
        resetValidation();
    }

    private void resetValidation() {
        validation = new Type[maxGuesses][wordLength];
    }

    private void validate() {
        for (int i = 0; i < maxGuesses; i++) {
            String g = guesses[i];
            if (g == null) {
                continue;
            }
            for (int j = 0; j < wordLength; j++) {  //TODO: what if guess / word has multiple occurences of the same letter
                if (g.charAt(j) == word.charAt(j)) {
                    validation[i][j] = Type.POSITION;
                } else if (word.indexOf(g.charAt(j)) != -1) {
                    validation[i][j] = Type.LETTER;
                }
                else {
                    validation[i][j] = Type.WRONG;
                }
            }
        }

    }

    public boolean guess(String guessWord) throws IncorrectGuessLengthException {
        if (guessWord.length() != wordLength) throw new IncorrectGuessLengthException();
        guesses[totalGuesses++] = guessWord;
        validate();
        return guessWord.equals(word);
    }
}
