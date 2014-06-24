/*
 * A class to show a list of highscores.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class GameStartMenu implements KeyListener {

    GamePanel panel;
    int x, y, w, h;
    String head;
    FontMetrics metrics;
    String[] message;

	public GameStartMenu(GamePanel p) {
        panel = p;
        p.addKeyListener(this);

        metrics = p.getGraphics().getFontMetrics(p.getFont());
        
        message = new String[] {
            "37BEC-AMVIOUS -- Game Of The Gods",
                "",
                "This is by general concencus of the population of me considered",
                "the best game ever created by a mortal.",
                "",
                "Don't let any squares get past you or they will find their way into",
                "your mothers house and do horrible things to her.",
                "",
                "Move up and down with <w> and <s>. Aim and shoot with that annoyingly",
                "useless thing called a mouse.",
                "",
                "You can also stop the annoying bastards by blocking them with yourself,",
                "this will gain you 10 times as many points as if you shot them but",
                "will immobilise you for a short time.",
                "",
                "DON'T FUCKING DIE.",
                "",
                "And if you manage to, then good job, because I havn't added any way",
                "for you to.",
                "",
                "Now press a key to start."
        };

        h = 20;
        w = 20;

        for (int i = 0; i < message.length; i++) {
            h += metrics.getHeight();

            int iw = metrics.stringWidth(message[i]) + 20;
            if (iw > w) w = iw;
        }

        x = panel.getWidth() / 2 - w / 2;
        y = panel.getHeight() / 2 - h / 2;

	}

	public void paint(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(x, y, w, h);
        g.setColor(Color.white);
        g.drawRect(x, y, w, h);

        for (int i = 0; i < message.length; i++) {
            String line = message[i];
            int iy = y + 10 + (i + 1) * metrics.getHeight();
            g.drawString(line, x + w / 2 - metrics.stringWidth(line) / 2, iy);
        }
    }

    public void keyReleased(KeyEvent e) {
        panel.removeKeyListener(this);
        panel.startGame();
    }

    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {}
}