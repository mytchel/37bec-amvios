/*
 * The target you must click repeatidly to flip some bits that represent your worth.
 */

import java.awt.*;
import java.awt.image.*;
import java.util.Random;
import java.lang.Math;
import javax.imageio.*;
import java.io.*;

public class Friend extends Part {
    
    BufferedImage image, imageHit;
    int lives;
    int hit;
    boolean showHit;

	public Friend(GameControler c) {
        super(c);
        
        // Give it a random location on the screen and a random velocity.
        x = rand.nextInt(controler.getWidth()) - controler.getWidth();
        y = rand.nextInt(controler.getHeight());
        xv = rand.nextInt(5);
        yv = rand.nextInt(10) - 5;
        w = 64;
        h = 64;
        lives = 3;

        hit = 0;
        showHit = false;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/images/friend.png"));
            imageHit = ImageIO.read(getClass().getResourceAsStream("/images/friend_hit.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	public void paint(Graphics g) {
        if (showHit) g.drawImage(imageHit, x - w / 2, y - h / 2, null);
        else g.drawImage(image, x - w / 2, y - h / 2, null);
	}

    public void update() {
        if (hit > 0) {
            if (hit % 10 == 0) showHit = !showHit;
            hit--;
        } else showHit = false;

        x += xv;
        y += yv;

        if (y + h > controler.getHeight() || y < 0) yv = -yv;

        if (x > controler.getWidth()) {
            controler.addScore(GameControler.SCORE_SHOT * 100);
            controler.removeOther(this);
        }
    }

    public boolean collides(Player p) {
        return false;
    }

    public boolean collides(Bullet b) {
        if (collidesSquare(b) && hit == 0) {
            if (lives < 1) {
                for (double a = -3.14; a < 3.14; a += 0.5) controler.addBullet(new Bullet(controler, x, y, a));
                controler.removeOther(this);
                controler.addScore(GameControler.SCORE_SHOT * -100);
            } else {
                showHit = true;
                hit = 50;
                lives--;
            }
            return true;
        }
        return false;
    }
}