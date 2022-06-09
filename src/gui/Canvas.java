package gui;

import exceptions.IncorrectGuessLengthException;
import game.Game;
import game.Guesser;
import util.Type;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

public class Canvas extends JPanel implements MouseListener, MouseMotionListener, KeyListener{
    protected Game game;

    private static final int SQUARE_SIZE = 50;
    private static final int SPACING = SQUARE_SIZE / 5;

    private static final int FONT_SIZE = SQUARE_SIZE - SPACING;

    protected Color defaultColour;
    protected Stroke lineWidth;
    protected Font letterFont;

    private Guesser guesser;

    public Canvas(int x, int y) {
        this.setPreferredSize(new Dimension(x, y));

        this.game = null;

        this.defaultColour = Color.BLACK;
        this.lineWidth = new BasicStroke(2.0F);
        this.letterFont = new Font("Arial", Font.BOLD, FONT_SIZE);

        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setFocusable(true);
    }

    public void setGame(Game g) {
        this.game = g;
        this.guesser = new Guesser(g);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (this.game == null) return;
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int h = getHeight();
        int w = getWidth();

        int x, y;
        int x0 = x = (w - (game.wordLength - 1) * (SQUARE_SIZE + SPACING)) / 2; //why -1?
        int y0 = y = SQUARE_SIZE + SPACING;

        g2.setColor(defaultColour);
        g2.setStroke(lineWidth);

        g2.setFont(letterFont);
        FontMetrics metrics = g.getFontMetrics(letterFont);

        for (int i = 0; i < game.maxGuesses; i++) {
            for (int j = 0; j < game.wordLength; j++) {
                if (i < game.totalGuesses) {
                    fillSquare(g, game.validation[i][j], x, y, SQUARE_SIZE, SQUARE_SIZE);
                    String str = String.valueOf(game.guesses[i].charAt(j));
                    Rectangle r = new Rectangle(x - SQUARE_SIZE / 2, y - SQUARE_SIZE/2, SQUARE_SIZE, SQUARE_SIZE);
                    centerString(g, r, str, letterFont);
                    //g2.drawString(String.valueOf(game.guesses[i].charAt(j)), x - (FONT_SIZE - SPACING) / 2, y + (FONT_SIZE - SPACING) / 2);
                } else if (i == game.totalGuesses) {
                    String str = String.valueOf(guesser.letters[j]);
                    Rectangle r = new Rectangle(x - SQUARE_SIZE / 2, y - SQUARE_SIZE/2, SQUARE_SIZE, SQUARE_SIZE);
                    centerString(g, r, str, letterFont);
                    //g2.drawString(str, x - (FONT_SIZE - SPACING) / 2, y + (FONT_SIZE - SPACING) / 2);
                }
                drawSquare(g, x, y, SQUARE_SIZE, SQUARE_SIZE);
                x += SQUARE_SIZE + SPACING;
            }
            x = x0;
            y += SQUARE_SIZE + SPACING;
        }
    }



    protected void drawSquare(Graphics g, int x, int y, int h, int w) {
        g.drawRect(x - w/2, y - h/2, w, h);
    }
    protected void fillSquare(Graphics g, Type t, int x, int y, int h, int w) {
        g.setColor(t.getColour());
        g.fillRect(x - w/2, y - h/2, w, h);
        g.setColor(defaultColour);
    }
    public void centerString(Graphics g, Rectangle r, String s, Font font) {
        FontRenderContext frc = new FontRenderContext(null, true, true);
        Rectangle2D r2D = font.getStringBounds(s, frc);
        int rWidth = (int) Math.round(r2D.getWidth());
        int rHeight = (int) Math.round(r2D.getHeight());
        int rX = (int) Math.round(r2D.getX());
        int rY = (int) Math.round(r2D.getY());

        int a = (r.width / 2) - (rWidth / 2) - rX;
        int b = (r.height / 2) - (rHeight / 2) - rY;

        g.setFont(font);
        g.drawString(s, r.x + a, r.y + b);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (this.game == null) return;
        int key = e.getKeyCode();
        if (key >= KeyEvent.VK_A && key <= KeyEvent.VK_Z) {
            guesser.guess(Character.toUpperCase(e.getKeyChar()));
        }
        if (key == KeyEvent.VK_BACK_SPACE) {
            guesser.undo();
        }
        if (key == KeyEvent.VK_ENTER) {
            String w = guesser.submit();
            if (w != null) {
                try {
                    game.guess(w);
                } catch (IncorrectGuessLengthException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
