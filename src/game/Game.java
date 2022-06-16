package game;

import exceptions.IncorrectGuessLengthException;
import util.Language;
import util.Theme;
import util.Type;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

public class Game {
    private String word;
    public String[] guesses;
    public Type[][] validation;

    public final int maxGuesses;
    public final int wordLength;
    public int totalGuesses;

    public Language lang;
    public Theme theme;

    public Game() {this(6, 5, Language.ENGLISH, Theme.LIGHT);}

    public Game(Language lang) {this(6, 5, lang, Theme.LIGHT);}

    public Game(Language lang, Theme theme) {this(6, 5, lang, theme);}

    public Game(int maxGuesses, int wordLength, Language lang, Theme theme) {
        this.maxGuesses = maxGuesses;
        this.wordLength = wordLength;
        guesses = new String[maxGuesses];
        totalGuesses = 0;
        this.lang = lang;
        this.theme = theme;
        newWord();
    }

    private static final Random random = new Random ();

    /**
     * chooses a random word to guess from dictionary
     */
    private void newWord() {
        HashSet<String> words = lang.getWords();
        String[] arrayWords = words.toArray(new String[words.size()]);
        int i = random.nextInt(arrayWords.length);
        word = arrayWords[i].toUpperCase();
        System.out.println(word);
        resetValidation();
    }

    private void resetValidation() {
        validation = new Type[maxGuesses][wordLength];
    }

    private void validate() {
        for (int i = 0; i < maxGuesses; i++) {
            String g = guesses[i];
            if (g == null) continue;
            for (int j = 0; j < wordLength; j++) {
                char c = word.charAt(j);
                if (g.charAt(j) == c) validation[i][j] = Type.POSITION;
                else {
                    for (int k = 0; k < wordLength; k++) {
                        if (g.charAt(k) == c && validation[i][k] == null) {
                            validation[i][k] = Type.LETTER;
                            break;
                        }
                    }
                }
            }
            for (int l = 0; l < wordLength; l++) {
                if (validation[i][l] == null) validation[i][l] = Type.WRONG;
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
