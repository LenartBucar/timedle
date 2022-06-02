package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window  extends JFrame implements ActionListener{
    public Canvas canvas;

    public Window() {
        this.setLayout(new GridLayout());

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        this.add(main);

        canvas = new Canvas(1000, 1000);
        main.add(canvas);

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

    }
}
