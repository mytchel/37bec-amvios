/*
 * Class to control a game.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.image.VolatileImage;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.image.*;
import java.util.Random;
import java.io.*;

public class GameControler implements MouseListener, MouseMotionListener, KeyListener {

    public static int SCORE_SHOT = 500;

    // Some variables such as how long there is left in the game, the target and the score.
    GamePanel panel; 
    Player player;
    ArrayList<Part> others;
    ArrayList<Bullet> bullets;
    String highscoreHolder;
    long highscore;
    long score;
    int addChance;
    int lives;
    int width, height;
    boolean paused;
    boolean end;
    HighscoreBox hsbox;
    Random rand;

    FontMetrics metrics;

	public GameControler(GamePanel p, int w, int h) {
        panel = p;

        rand = new Random();
        metrics = p.getGraphics().getFontMetrics(p.getFont());
        width = w;
        height = h;

        player = new Player(this);
        others = new ArrayList<Part>();
        for (short i = 0; i < 10; i++)
            others.add(newEnemy());
        bullets = new ArrayList<Bullet>();

        readHighscore();
        score = 0;
        lives = 3;
        addChance = 1000;

        paused = true;
        end = false;

        // Listen to mouse and key events on the panel, this is so I can figure out if
        // the target was clicked by some faggot playing this game.
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);
        panel.addKeyListener(this);
	}

	public void paint(Graphics g) {
        short i;
        String message;
        
        g.setFont(panel.getFont());

        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);

        for (i = 0; i < bullets.size(); i++)
            bullets.get(i).paint(g);

        player.paint(g);
      
        for (i = 0; i < others.size(); i++)
            others.get(i).paint(g);

        g.setColor(Color.white);
        g.drawString("Score: " + score + "       Lives: " + lives, 20, 20);
        message = "Try Beat " + highscoreHolder + " with " + highscore;
        g.drawString(message, getWidth() - metrics.stringWidth(message) - 20, 20);

        if (paused || end) {
            g.setColor(Color.white);
            message = "Paused";
            if (end) message = "Game Over";
            g.drawString(message, getWidth() / 2 - metrics.stringWidth(message) / 2, getHeight() / 2 - metrics.getHeight());
            if (end) hsbox.paint(g);
        }
    }

    public void update() {
        short i, j;
        if (paused || end) return;

        player.update();
 
        for (i = 0; i < bullets.size(); i++)
            bullets.get(i).update();
               
        if (rand.nextInt(1000) == 0) others.add(new Friend(this));
        if (rand.nextInt(addChance--) == 0) {
            others.add(newEnemy());
            if (addChance < 50) addChance = 50;
        }

        for (i = 0; i < others.size(); i++) {
            Part p = others.get(i);
            if (!p.isAlive()) {
                removeOther(p);
                continue;
            }
            p.update();

            if (p.collides(player)) {
                player.hit();
                score += SCORE_SHOT * 10;
            }

            for (j = 0; j < bullets.size(); j++) {
                Bullet b = bullets.get(j);
                if (p.collides(b)) b.hitSomething();
            }
        }

        if (lives < 1) {
            if (hsbox == null) hsbox = new HighscoreBox(this, score);
            end = true;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public long getScore() {
        return score;
    }

    public void addScore(long s) {
        score += s;
    }

    public void lowerLives() {
        lives--;
    }

    public void increaseLives() {
        lives++;
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
    }
    
    public void togglepause() {
        System.out.println("Toggling Pause");
        paused = !paused;
    }

    public boolean isPaused() {
        return paused;
    }

    public void givePlayerBullet(Bullet b) {
        player.giveBullet(b);
    }

    public void addBullet(Bullet b) {
        bullets.add(b);
    }

    public void removeBullet(Bullet b) {
        bullets.remove(b);
    }

    public void removeOther(Part p) {
        others.remove(p);
    }

    public void addOther(Part p) {
        others.add(p);
    }

    public Part newEnemy() {
        Part e;
        switch (rand.nextInt(10)) {
            case 1: e = new ExplosiveBulletEnemy(this);
                    break;
            case 2: e = new ExplosiveBulletEnemy(this);
                    break;
            case 3: e = new GoldEnemy(this);
                    break;
            default: e = new Enemy(this, Enemy.PATTERNS[rand.nextInt(Enemy.PATTERNS.length)]);
                     break;
        }
        
        return e;
    }

    public GamePanel getPanel() {
        return panel;
    }

    public void readHighscore() {
        highscore = 0;
        highscoreHolder = "nobody";
        try {

            File file = new File(Game.HIGHSCORES_FILE());
            if (!file.exists()) return;

            BufferedReader br = new BufferedReader(new FileReader(file));

            String line = br.readLine();
            if (line == null) return;

            String[] parts = line.split(":");
            if (parts.length != 2) return;
            highscoreHolder = parts[0];
            highscore = Long.parseLong(parts[1]);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Random getRandom() {
        return rand;
    }

    // Listens to mouse pressed events on the panel.
    public void mousePressed(MouseEvent e) {
        if (paused || end) return;
        player.mousePressed(e);
    }

    public void mouseReleased(MouseEvent e) {
        if (paused || end) return;
        player.mouseReleased(e);
    }
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}

    public void mouseDragged(MouseEvent e) {
        if (paused || end) return;
        player.mouseMoved(e);
    }

    public void mouseMoved(MouseEvent e) {
        if (paused || end) return;
        player.mouseMoved(e);
    }

    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == ' ') panel.setMode(GamePanel.PAUSE);
    }

    public void keyPressed(KeyEvent e) {
        if (paused || end) return;
        player.keyPressed(e);
    }
    public void keyReleased(KeyEvent e) {
        if (paused || end) return;
        player.keyReleased(e);
    }
}
