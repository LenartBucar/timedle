package gui;

import game.Game;
import timedle.Timedle;
import util.Language;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;

public class Window  extends JFrame implements ActionListener {
    public Canvas canvas;

    private JMenuItem english;
    private JMenuItem slovene;

    public Window() {
        this.setLayout(new GridLayout());

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        this.add(main);

        canvas = new Canvas(1000, 1000);
        main.add(canvas);

        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu langMenu = addMenu(menuBar, "Jezik");

        english = addMenuItem(langMenu, "English");
        slovene = addMenuItem(langMenu, "Slovenščina");


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
            Timedle.playNewGame(Language.ENGLISH, this);
        } else if (e.getSource() == slovene) {
            Timedle.playNewGame(Language.SLOVENE, this);
        }
    }
}
