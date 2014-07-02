/*
 * A star in it's own right.
*/

import java.lang.Math;
import java.util.Random;
import java.awt.Color;
import java.awt.Graphics;

public class Star {
    public static int MAX_BRIGHTNESS = 200;

    GameControler controler;
    Color color;
    short x, y;
    short a, r, g, b;
    boolean brighter;

    public Star(GameControler co) {
	controler = co;
	setPlace();
        a = (short)controler.getRandom().nextInt(MAX_BRIGHTNESS);
    }

    /*
     * Increases or decreases the brightness of the star then draw it.
     */
    public void paint(Graphics gr) {
	if (brighter) a++;
	else a--;
	// If the brightness is greater than the maximum start getting dimmer.
	if (a > MAX_BRIGHTNESS) brighter = false;
	// If it is below 0 then give it another place.
	if (a < 0) setPlace();
	
	color = new Color(r, g, b, a);

	gr.setColor(color);
        gr.fillRect(x, y, 5, 5);
    }

    public void setPlace() {
	// Pick a random place.
        x = (short) controler.getRandom().nextInt(controler.getWidth());
        y = (short) controler.getRandom().nextInt(controler.getHeight());

	// Set the color, alpha 0, green full, red random, and blue either full or
	// random depending on what red was. This creates colors more in line with
	// that of stars.
	
	a = 0;
        r = (short) controler.getRandom().nextInt(255);
        g = 255;
	if (r < 50) b = 255;
        else b = (short) controler.getRandom().nextInt(255);
	brighter = true;
    }
}