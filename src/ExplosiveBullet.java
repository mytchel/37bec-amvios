import java.awt.event.*;
import java.awt.*;
import java.lang.Math;

public class ExplosiveBullet extends Bullet {
   
    int more;

    public ExplosiveBullet(GameControler c, int m) {
        super(c);
        color = new Color(255, 0, 0);
        more = m;
    }

    public ExplosiveBullet(GameControler c) {
        this(c, 5);
    }

    public void init(int x, int y, double angle) {
        super.init(x, y, angle);
        System.out.println("inited");
/*        if (more > 0) {
            System.out.println("Gave another");
        }
        more = 0;
*/
    }

    public void update() {
        super.update();

        if (more > 0)
            controler.givePlayerBullet(new ExplosiveBullet(controler, more - 1));
        more = 0;
    }

    public void hitSomething() {
        super.hitSomething();
        for (double a = -3.14; a < 3.14; a += 0.5) {
            controler.addBullet(new Bullet(controler, (int) x, (int) y, a));
        }
    }
}
