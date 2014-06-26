public class RenderThread extends Thread {
	GamePanel panel;
    boolean end;
    long frameTime;

	public RenderThread(GamePanel p, long t) {
		panel = p;
        frameTime = t;
	}
	
    public void run() {
        long start, time;

        end = false;
        /*
         * Repeat this until end becomes true. So until something calls the end method.
         */
		while (!end) {
            start = System.currentTimeMillis();

            // Repaint the panel.
            panel.repaint();
            
            // Figure out how long it took to paint, if this is positive sleep for however long it takes to
            // keep the frames the same length. Helps with consistancy.
            time = frameTime - (System.currentTimeMillis() - start);
            if (time > 0) {
                try {
                    Thread.sleep(time);
                } catch (Exception e) {}
            }
		}
	}

    public void end() {
        end = true;
    }
}