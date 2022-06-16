package timedle;

import game.Game;
import gui.Window;
import util.Language;

public class Timedle {

    public static void main(String[] args) {
        Game game = new Game();
        Window window = new Window();
        window.canvas.setGame(game);
        window.setTitle("TIMEDLE");
        window.pack();
        window.setVisible(true);
    }

    public static void playNewGame (Language lang, Window window) {
        Game game = new Game(lang);
        window.canvas.setGame(game);
    }
}
