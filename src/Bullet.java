import java.awt.event.*;
import java.awt.*;
import java.lang.Math;

public class Bullet extends Part {
    public float SPEED = 30;

    public Bullet(GameControler c) {
        super(c, new Color(255, 0, 255));
    }

	public Bullet(GameControler c, float x, float y, float angle) {
        this(c);
        init(x, y, angle);
    }

    public void init(float x, float y, float angle) {
        this.x = x;
        this.y = y;
        xv = (float) (-SPEED * Math.cos(angle));
        yv = (float) (-SPEED * Math.sin(angle));
    }

	public void paint(Graphics g) {
        g.setColor(color);
        g.drawLine((int) x, (int) y, (int) (x - xv), (int) (y - yv));
	}

    public void update() {
        x += xv;
        y += yv;

        if (x < -SPEED) controler.removeBullet(this);
        if (x > controler.getWidth() + SPEED) controler.removeBullet(this);
        if (y < -SPEED) controler.removeBullet(this);
        if (y > controler.getHeight() + SPEED) controler.removeBullet(this);
    }

    public void hitSomething() {
        controler.removeBullet(this);
    }
}
