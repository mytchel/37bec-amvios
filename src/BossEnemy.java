/*
 * I will rape you profoundly.
 */

import java.util.Random;
import java.lang.Math;
import java.awt.Graphics;
import java.awt.Color;

public class BossEnemy extends Enemy {

    public static String[] PATTERNS = {
	"    *    \n   ***   \n  *****  \n ******* \n*********\n ******* \n  *****  \n   ***   \n    *    \n",
	"*******\n*******\n*******\n*******\n*******\n*******\n*******\n",
	"*     *\n ***** \n ***** \n ***** \n*     *\n"
    };

    int lives;

    public BossEnemy(GameControler c) {
	this(c, PATTERNS[c.getRandom().nextInt(PATTERNS.length)]);
    }
    
    public BossEnemy(GameControler co, String pattern) {
        super(co, pattern);
        
	lives = parts.length / 2;
    }

    // They will have a hard time stopping me like this.
    public boolean collides(Player p) {
	boolean hit = false;
        for (ui = 0; ui < parts.length; ui++) {
            if (parts[ui].collides(p)) {
		parts[ui].hit();
		hit = true;
	    }
	}

        return hit;
    }

    /*
     * Go through the parts and tell any that the bullet collides with
     * to go and fall apart.
     */
    public boolean collides(Bullet b) {
        boolean hit = false;
        for (ui = 0; ui < parts.length; ui++) {
            EnemyPart e = parts[ui];
            if (e.collides(b)) {
		lives--;
                controler.addScore(controler.SCORE_SHOT);
                hit = true;
		if (lives < 1)
		    e.hit(b);
            }    
        }

        if (!shot && lives < 1) {
	    shot = true;
            for (ui = 0; ui < parts.length; ui++) {
                EnemyPart e = parts[ui];
                e.setXV(rand().nextInt(5));
                e.setYV(rand().nextInt(10) - 5);
                e.setBounce(true);
            } 
        }

        return hit;
    }
}