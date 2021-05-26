package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

    private ImageIcon titleImage;
    private ImageIcon headRight;
    private ImageIcon headLeft;
    private ImageIcon headUp;
    private ImageIcon headDown;
    private ImageIcon tail;
    private ImageIcon fruitImage;
    private int[] snakeXLength = new int[750];
    private int[] snakeYLength = new int[750];
    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;
    private int lengthOfSnake = 3;
    private Timer timer;
    private int delay = 100;
    private int moves = 0;
    private int score = 0;
    // Fruit params
    private int[] fruitXPos = {25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 375,
    400, 425, 450, 475, 500, 525, 550, 575, 600, 625, 650, 675, 700, 725, 750, 775, 800, 825, 850};
    private int[] fruitYPos = {75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 375,
            400, 425, 450, 475, 500, 525, 550, 575, 600, 625};
    private Random random = new Random();
    private int xPos = random.nextInt(34);
    private int yPos = random.nextInt(23);

    public GamePlay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        if (moves == 0) {
            snakeXLength[0] = 100;
            snakeXLength[1] = 75;
            snakeXLength[2] = 50;

            snakeYLength[0] = 100;
            snakeYLength[1] = 100;
            snakeYLength[2] = 100;
        }

        // Display title
        titleImage = new ImageIcon("src/title.png");
        titleImage.paintIcon(this, g, 25, 5);

        // Display gameplay border
        g.setColor(Color.DARK_GRAY);
        g.drawRect(24, 74, 851, 577);

        // Display gameplay background
        g.setColor(Color.BLACK);
        g.fillRect(25, 75, 850, 575);

        // Draw score and length
        g.setColor(Color.WHITE);
        g.setFont(new Font("areal", Font.PLAIN, 14));
        g.drawString("Scores: " + score, 780, 30);
        g.setColor(Color.WHITE);
        g.setFont(new Font("areal", Font.PLAIN, 14));
        g.drawString("Length of Snake: " + lengthOfSnake, 700, 50);

        // Initial position of the head image
        headRight = new ImageIcon("src/headRight.png");
        headRight.paintIcon(this, g, snakeXLength[0], snakeYLength[0]);

        for (int i = 0; i < lengthOfSnake; i++) {
            if (i == 0 && right) {
                headRight = new ImageIcon("src/headRight.png");
                headRight.paintIcon(this, g, snakeXLength[i], snakeYLength[i]);
            }

            if (i == 0 && left) {
                headLeft = new ImageIcon("src/headLeft.png");
                headLeft.paintIcon(this, g, snakeXLength[i], snakeYLength[i]);
            }

            if (i == 0 && up) {
                headUp = new ImageIcon("src/headUp.png");
                headUp.paintIcon(this, g, snakeXLength[i], snakeYLength[i]);
            }

            if (i == 0 && down) {
                headDown = new ImageIcon("src/headDown.png");
                headDown.paintIcon(this, g, snakeXLength[i], snakeYLength[i]);
            }

            if (i != 0) {
                tail = new ImageIcon("src/tail.png");
                tail.paintIcon(this, g, snakeXLength[i], snakeYLength[i]);
            }

            fruitImage = new ImageIcon("src/fruit.png");
            if (fruitXPos[xPos] == snakeXLength[0] && fruitYPos[yPos] == snakeYLength[0]) {
                score = score + 5;
                lengthOfSnake++;
                xPos = random.nextInt(34);
                yPos = random.nextInt(23);
            }
            fruitImage.paintIcon(this, g, fruitXPos[xPos], fruitYPos[yPos]);
        }

        for (int i = 1; i < lengthOfSnake; i++) {
            if (snakeXLength[i] == snakeXLength[0] && snakeYLength[i] == snakeYLength[0]) {
                right = false;
                left = false;
                up = false;
                down = false;

                g.setColor(Color.RED);
                g.setFont(new Font("areal", Font.BOLD, 20));
                g.drawString("GAME OVER! SCORE: " + score, 250, 300);

                g.setColor(Color.WHITE);
                g.setFont(new Font("areal", Font.BOLD, 20));
                g.drawString("ENTER TO PLAY AGAIN" + score, 350, 400);
            }
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.restart();
        if (right) {
            for (int n = lengthOfSnake - 1; n >= 0; n--) {
                snakeYLength[n+1] = snakeYLength[n];
            }
            for (int n = lengthOfSnake; n >= 0; n--) {
                if (n == 0) {
                    snakeXLength[n] = snakeXLength[n] + 25;
                } else {
                    snakeXLength[n] = snakeXLength[n-1];
                }
                if (snakeXLength[n] > 850) {
                    snakeXLength[n] = 25;
                }
            }
            repaint();
        }

        if (left) {
            for (int n = lengthOfSnake - 1; n >= 0; n--) {
                snakeYLength[n+1] = snakeYLength[n];
            }
            for (int n = lengthOfSnake; n >= 0; n--) {
                if (n == 0) {
                    snakeXLength[n] = snakeXLength[n] - 25;
                } else {
                    snakeXLength[n] = snakeXLength[n-1];
                }
                if (snakeXLength[n] < 25) {
                    snakeXLength[n] = 85;
                }
            }
            repaint();
        }

        if (up) {
            for (int n = lengthOfSnake - 1; n >= 0; n--) {
                snakeXLength[n+1] = snakeXLength[n];
            }
            for (int n = lengthOfSnake; n >= 0; n--) {
                if (n == 0) {
                    snakeYLength[n] = snakeYLength[n] - 25;
                } else {
                    snakeYLength[n] = snakeYLength[n-1];
                }
                if (snakeYLength[n] < 75) {
                    snakeYLength[n] = 625;
                }
            }
            repaint();
        }

        if (down) {
            for (int n = lengthOfSnake - 1; n >= 0; n--) {
                snakeXLength[n+1] = snakeXLength[n];
            }
            for (int n = lengthOfSnake; n >= 0; n--) {
                if (n == 0) {
                    snakeYLength[n] = snakeYLength[n] + 25;
                } else {
                    snakeYLength[n] = snakeYLength[n-1];
                }
                if (snakeYLength[n] > 625) {
                    snakeYLength[n] = 75;
                }
            }
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            moves++;
            right = true;
            if (!left) {
                right = true;
            } else {
                right = false;
                left = true;
            }
            up = false;
            down = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            moves++;
            left = true;
            if (!right) {
                left = true;
            } else {
                right = true;
                left = false;
            }
            up = false;
            down = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            moves++;
            up = true;
            if (!down) {
                up = true;
            } else {
                up = false;
                down = true;
            }
            left = false;
            right = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            moves++;
            down = true;
            if (!up) {
                down = true;
            } else {
                down = false;
                up = true;
            }
            left = false;
            right = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            moves = 0;
            score = 0;
            lengthOfSnake = 3;
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
