package timedle;

import game.Game;
import gui.Canvas;
import gui.Window;
import util.Language;
import util.Theme;

import java.awt.*;

public class Timedle {

    public static void main(String[] args) {
        Game game = new Game();
        Window window = new Window();
        window.canvas.setGame(game);
        window.setTitle("TIMEDLE");

        window.setMinimumSize(window.canvas.getMinDimension());
        window.setPreferredSize(window.canvas.getDimension());

        window.pack();
        window.setVisible(true);
    }

    public static void playNewGame (Language lang, Theme theme, Window window) {
        Game game = new Game(lang, theme);
        window.canvas.setGame(game);
    }
}
