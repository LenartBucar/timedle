package timedle;

import game.Game;
import gui.Window;

public class Timedle {

    public static void main(String[] args) {
        Game game = new Game();
        Window window = new Window();
        window.canvas.setGame(game);
        window.pack();
        window.setVisible(true);
    }
}
