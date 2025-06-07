package shootingspaceship;

import javax.swing.*;
import java.awt.*;

public class Shot {

	protected int x_pos;
	protected int y_pos;
	protected boolean alive;
	protected final int radius = 3;
    protected Image shotImage = new ImageIcon(getClass().getResource("/shootingspaceship/img_3.png")).getImage();

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
//        if (!alive) {
//            return;
//        }
//        g.setColor(Color.yellow);
//        g.fillOval(x_pos, y_pos, radius, radius);
        if (!alive) return;

        if (shotImage != null) {
            g.drawImage(shotImage, x_pos - 5, y_pos - 5, 10, 10, null); // 크기 보정
        } else {
            g.setColor(Color.yellow);
            g.fillOval(x_pos, y_pos, radius, radius);
        }
    }

    public void collided() {
        alive = false;
    }
}
