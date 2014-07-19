/*
 *          DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *                    Version 2, December 2004
 *
 * Copyright (C) 2014 Mytchel Hammond
 *
 * Everyone is permitted to copy and distribute verbatim or modified
 * copies of this file, and changing it is allowed as long
 * as the name is changed.
 *
 *            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 *  0. You just DO WHAT THE FUCK YOU WANT TO.
 *
 * -----------------------------------------------------------------
 *
 *
 * A star in it's own right.
 */

import java.lang.Math;
import java.util.Random;
import java.awt.Color;
import java.awt.Graphics2D;

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
        a = (short) controler.getRandom().nextInt(MAX_BRIGHTNESS);
    }

    /*
     * Increases or decreases the brightness of the star then draw it.
     */
    public void paint(Graphics2D gr) {
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
        g = 255;

	if (controler.getRandom().nextInt(2) == 1) {
	    r = (short) controler.getRandom().nextInt(100);
	    b = 255;
	} else {
	    r = 255;
	    b = (short) controler.getRandom().nextInt(100);
	}

	brighter = true;
    }
}
