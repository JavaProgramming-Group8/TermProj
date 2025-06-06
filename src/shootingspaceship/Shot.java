package shootingspaceship;

import java.awt.Color;
import java.awt.Graphics;

public class Shot {

	protected int x_pos;
	protected int y_pos;
	protected boolean alive;
	protected final int radius = 3;

    public Shot(int x, int y) {
        x_pos = x;
        y_pos = y;
        alive = true;
    }

    public int getY() {
        return y_pos;
    }

    public int getX() {
        return x_pos;
    }

    public void moveShot(int speed) {
        y_pos += speed;
    }

    public void drawShot(Graphics g) {
        if (!alive) {
            return;
        }
        g.setColor(Color.yellow);
        g.fillOval(x_pos, y_pos, radius, radius);
    }

    public void collided() {
        alive = false;
    }
}
