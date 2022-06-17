package game;

import java.util.Arrays;

public class Guesser {
    public char[] letters;
    private Game game;
    private int pos;

    public Guesser(Game game) {
        this.game = game;
        letters = new char[game.wordLength];
        Arrays.fill(letters, ' ');
        pos = 0;
    }

    public void guess(char c) {
        if (pos == game.wordLength) return;
        letters[pos++] = c;
    }

    /**
     * deletes last letter, if it is not in previous word.
     */
    public void undo() {
        if (game.lastWord != null){
            letters = game.lastWord.toCharArray();
            letters[game.wordLength - 1] = ' ';
            pos = game.wordLength - 1;
            game.lastWord = null;
            return;
        }
        else if (pos == 0) return;
        letters[--pos] = ' ';
    }

    /**
     * tries to guess the word if the length is right
     * @return word guessed
     */
    public String submit() {
        if (pos != game.wordLength) return null;
        String out = new String(letters);
        Arrays.fill(letters, ' ');
        pos = 0;
        return out;
    }
}
