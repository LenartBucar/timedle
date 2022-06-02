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

    public void undo() {
        if (pos == 0) return;
        letters[--pos] = ' ';
    }

    public String submit() {
        if (pos != game.wordLength) return null;
        String out = new String(letters);
        Arrays.fill(letters, ' ');
        pos = 0;
        return out;
    }
}
