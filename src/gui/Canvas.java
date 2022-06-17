package gui;

import exceptions.IncorrectGuessLengthException;
import game.Game;
import game.Guesser;
import org.w3c.dom.css.Rect;
import util.Theme;
import util.Type;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

public class Canvas extends JPanel implements MouseListener, MouseMotionListener, KeyListener{
    protected Game game;
    public Theme theme;

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
        this.theme = Theme.LIGHT;

        this.defaultColour = Color.BLACK;
        this.lineWidth = new BasicStroke(2.0F);
        this.letterFont = new Font("Arial", Font.BOLD, FONT_SIZE);

        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setFocusable(true);
    }

    /**
     * adds game to canvas and sets up guesser
     * @param g game to add to canvas
     */
    public void setGame(Game g) {
        this.game = g;
        this.guesser = new Guesser(g);
        repaint();
    }

    /**
     * computes preferred dimension od the window
     */
    public Dimension getDimension(){
        Dimension dim = new Dimension((game.wordLength + 2) * (SQUARE_SIZE + SPACING), (game.maxGuesses + 2) * (SQUARE_SIZE + SPACING));
        return dim;
    }

    /**
     * computes minimal dimension of the window, which still shows the whole board
     */
    public Dimension getMinDimension(){
        Dimension dim = new Dimension((game.wordLength + 2) * (SQUARE_SIZE + SPACING), (game.maxGuesses + 6) * (SQUARE_SIZE + SPACING));
        return dim;
    }

    /**
     * Paints title and board on canvas
     */
    @Override
    protected void paintComponent(Graphics g) {
        if (this.game == null) return;
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int h = getHeight();
        int w = getWidth();

        int x, y;
        int x0 = x = (w - (game.wordLength - 1) * (SQUARE_SIZE + SPACING)) / 2; //x coordinate of top left corner of board
        int y0 = y = (h - (game.maxGuesses - 1) * (SQUARE_SIZE + SPACING)) / 2; //y coordinate of top left corner of board

        g2.setColor(defaultColour);
        Rectangle rec = new Rectangle(x0 - SQUARE_SIZE / 2, Math.max(y0 - 2 * SQUARE_SIZE, 0),  game.wordLength * SQUARE_SIZE + (game.wordLength - 1) * SPACING, Math.min(y0, 2 * SQUARE_SIZE));
        centerString(g, rec, "TIMEDLE", letterFont); //paint title above the board

        g2.setStroke(lineWidth);
        g2.setFont(letterFont);
        setBackground(theme.getBackgroundColor());
        g2.setColor(theme.getLetterColor());

        //paints board
        for (int i = 0; i < game.maxGuesses; i++) {
            for (int j = 0; j < game.wordLength; j++) {
                if (i < game.totalGuesses) {
                    fillSquare(g2, game.validation[i][j].getColour(), x, y, SQUARE_SIZE);
                    String str = String.valueOf(game.guesses[i].charAt(j));
                    drawStr(g2, theme.getLetterColor(), str, x, y, SQUARE_SIZE);
                } else if (i == game.totalGuesses) {
                    if(game.lastWord == null) {
                        String str = String.valueOf(guesser.letters[j]);
                        drawStr(g2, theme.getLetterColor(), str, x, y, SQUARE_SIZE);
                    }
                    else{
                        fillSquare(g2, Color.red, x, y, SQUARE_SIZE);
                        String str = String.valueOf(game.lastWord.charAt(j));
                        drawStr(g2, theme.getLetterColor(), str, x, y, SQUARE_SIZE);
                    }
                }
                g2.setColor(theme.getLetterColor());
                g2.drawRoundRect(x - SQUARE_SIZE/2, y - SQUARE_SIZE/2, SQUARE_SIZE, SQUARE_SIZE, 10, 10);
                x += SQUARE_SIZE + SPACING;
            }
            x = x0;
            y += SQUARE_SIZE + SPACING;
        }
    }

    /**
     * fills rounded square with center in (x, y) and size d
     * @param g graphics
     * @param color color to fill with
     * @param x x coordinate of center of square
     * @param y y coordinate of center of square
     * @param d size of square
     */
    protected void fillSquare(Graphics2D g, Color color, int x, int y, int d){
        g.setColor(color);
        g.fillRoundRect(x - d/2, y - d/2, d, d, 10, 10);
    }

    /**
     * draws string in center of square with center on (x, y) and size d
     * @param g graphics
     * @param color color of string
     * @param str string to center
     * @param x x coordinate of center of square
     * @param y y coordinate of center of square
     * @param d size of square
     */
    protected void drawStr(Graphics2D g, Color color, String str, int x, int y, int d){
        g.setColor(color);
        Rectangle r = new Rectangle(x - SQUARE_SIZE/2, y - SQUARE_SIZE/2, d, d);
        centerString(g, r, str, letterFont);
    }

    /**
     * draws string in center of rectangle
     */
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
        // unneeded
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (this.game == null) return;
        if (game.win) return;
        boolean win = false;
        int key = e.getKeyCode();
        if (key >= KeyEvent.VK_A && key <= KeyEvent.VK_Z) { //inserts typed letter in guess
            guesser.guess(Character.toUpperCase(e.getKeyChar()));
        }
        if (key == KeyEvent.VK_BACK_SPACE) { //deletes last letter if possible
            guesser.undo();
        }
        if (key == KeyEvent.VK_ENTER) { //guesses the word typed
            String w = guesser.submit();
            if (w != null) {
                try {
                    win = game.guess(w);
                } catch (IncorrectGuessLengthException ex) {
                    throw new RuntimeException(ex);
                }
            }
            if(win) game.endGame();
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // unneeded
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // unneeded
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // unneeded
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // unneeded
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // unneeded
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // unneeded
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // unneeded
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // unneeded
    }
}
