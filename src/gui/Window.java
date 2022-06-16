package gui;

import timedle.Timedle;
import util.Language;
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
        JMenu langMenu = addMenu(menuBar, "New Game");
        JMenu themeMenu = addMenu(menuBar, "Theme");

        settingsMenu.add(langMenu);
        settingsMenu.add(themeMenu);
        english = addMenuItem(langMenu, "English");
        slovene = addMenuItem(langMenu, "Slovenščina");
        light = addMenuItem(themeMenu, "Light");
        dark = addMenuItem(themeMenu, "Dark");


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

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
            Timedle.playNewGame(Language.ENGLISH, theme,  this);
        }
        else if (e.getSource() == slovene) {
            Timedle.playNewGame(Language.SLOVENE, theme, this);
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
    }
}
