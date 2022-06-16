package gui;

import timedle.Timedle;
import util.Language;
import util.Mode;
import util.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window  extends JFrame implements ActionListener {
    public Canvas canvas;
    public Theme theme;

    private JMenuItem english;
    private JMenuItem slovene;
    private JMenuItem dark;
    private JMenuItem light;
    private JMenuItem slow;
    private JMenuItem medium;
    private JMenuItem fast;

    private JButton newGame;

    private static final int REFRESH_DELAY = 1000;

    public Window() {
        this.setLayout(new GridLayout());

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        this.add(main);

        canvas = new Canvas(1000, 1000);
        main.add(canvas);

        theme = Theme.LIGHT;

        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu settingsMenu = addMenu(menuBar, "Settings");
        JMenu modeMenu = addMenu(menuBar, "Mode");
        JMenu langMenu = addMenu(menuBar, "Language");
        JMenu themeMenu = addMenu(menuBar, "Theme");

        settingsMenu.add(langMenu);
        settingsMenu.add(themeMenu);
        english = addMenuItem(langMenu, "English");
        slovene = addMenuItem(langMenu, "Slovene");
        light = addMenuItem(themeMenu, "Light");
        dark = addMenuItem(themeMenu, "Dark");
        fast = addMenuItem(modeMenu, "Fast");
        medium = addMenuItem(modeMenu, "Medium");
        slow = addMenuItem(modeMenu, "Slow");



        JButton b = new JButton("New Game");
        b.addActionListener(this);
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGame = b;
        main.add(newGame);



        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        ActionListener taskPerformer = evt -> canvas.repaint();
        new Timer(REFRESH_DELAY, taskPerformer).start();
    }

    public void refreshGUI() {
        canvas.repaint();
    }

    private JMenu addMenu(JMenuBar menuBar, String title) {
        JMenu menu = new JMenu(title);
        menuBar.add(menu);
        return menu;
    }

    private JMenuItem addMenuItem(JMenu menu, String title) {
        JMenuItem menuItem = new JMenuItem(title);
        menu.add(menuItem);
        menuItem.addActionListener(this);
        return menuItem;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == english) {
            Timedle.playNewGame(Language.ENGLISH, canvas.game.mode,  this);
        }
        else if (e.getSource() == slovene) {
            Timedle.playNewGame(Language.SLOVENE, canvas.game.mode, this);
        }
        else if (e.getSource() == dark) {
            theme = Theme.Dark;
            this.setBackground(theme.getBackgroundColor());
            canvas.theme = Theme.Dark;
        }
        else if (e.getSource() == light) {
            theme = Theme.LIGHT;
            this.setBackground(theme.getBackgroundColor());
            canvas.theme = Theme.LIGHT;
        }
        else if (e.getSource() == slow) {
            canvas.game.mode = Mode.SLOW;
            canvas.game.setTimer();
        }
        else if (e.getSource() == medium) {
            canvas.game.mode = Mode.MEDIUM;
            canvas.game.setTimer();
        }
        else if (e.getSource() == fast) {
            canvas.game.mode = Mode.FAST;
            canvas.game.setTimer();
        }
        else if (e.getSource() == newGame) {
            Timedle.playNewGame(canvas.game.lang, canvas.game.mode, this);
        }
    }
}
