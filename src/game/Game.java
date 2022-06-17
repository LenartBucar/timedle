package game;

import exceptions.IncorrectGuessLengthException;
import util.Language;
import util.Mode;
import util.Type;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Game {
    private String word;
    public String[] guesses;
    public Type[][] validation;

    public final int maxGuesses;
    public final int wordLength;
    public int totalGuesses;

    public Language lang;

    public Timer timer;
    //private static final long DELAY = 10_000;
    public Mode mode;

    public String lastWord;
    public boolean win;

    public Game() {this(6, 5, Language.ENGLISH, Mode.MEDIUM);}

    public Game(Language lang) {this(6, 5, lang, Mode.MEDIUM);}

    public Game(Mode mode) {this(6, 5, Language.ENGLISH, mode);}

    public Game(Language lang, Mode mode) {this(6, 5, lang, mode);}

    public Game(int maxGuesses, int wordLength, Language lang, Mode mode) {
        this.maxGuesses = maxGuesses;
        this.wordLength = wordLength;
        guesses = new String[maxGuesses];
        totalGuesses = 0;
        this.lang = lang;
        this.mode = mode;
        this.lastWord = null;
        this.win = false;
        newWord();

        setTimer();


    }

    /**
     * sets new timer with delay, specified in mode
     */
    public void setTimer(){
        timer = new Timer();
        TimerTask swap = new WordSwapper(this);
        timer.scheduleAtFixedRate(swap, mode.getDelay(), mode.getDelay());
    }

    private static final Random random = new Random ();

    /**
     * chooses a random word to guess from dictionary
     */
    protected void newWord() {
        if (win) return;
        String[] words = lang.getWords();
        //String[] arrayWords = words.toArray(new String[words.size()]);
        int i = random.nextInt(words.length);
        word = words[i].toUpperCase();
        System.out.println(word);
        resetValidation();
    }

    /**
     * resets validation matrix and checks if any of the previous guesses became correct
     */
    public void resetValidation() {
        validation = new Type[maxGuesses][wordLength];
        for (int i = 0; i < totalGuesses; i++) {
            if (word.equals(guesses[i])) endGame();
        }
    }

    /**
     * fills validation matrix based on mach of letters
     */
    public void validate() {
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

    /**
     * colors word red if it is not in wordlist, continues if the word is too short, otherwise adds word to guesses
     * @param guessWord word to guess
     * @return boolean whether guessWord is the winning word
     * @throws IncorrectGuessLengthException
     */
    public boolean guess(String guessWord) throws IncorrectGuessLengthException {
        if (guessWord.length() != wordLength) throw new IncorrectGuessLengthException();
        if (!lang.containsWord(guessWord)) {
            validate();
            lastWord = guessWord;
            return false;
        }
        guesses[totalGuesses++] = guessWord;
        validate();
        return guessWord.equals(word);
    }

    /**
     * sets game to win state and disables further guessing
     */
    public void endGame(){
        win = true;
    }

}

/**
 * changes word with frequency specified in game mode
 */
class WordSwapper extends TimerTask {
    private Game game;

    public WordSwapper (Game game) {
        super();
        this.game = game;
    }

    @Override
    public void run() {
        game.newWord();
        game.resetValidation();
        game.validate();
    }
}
