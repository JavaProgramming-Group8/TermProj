package shootingspaceship;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class ImageShot extends Shot {

    protected Image shotImage = new ImageIcon(getClass().getResource("/shootingspaceship/img_3.png")).getImage();

    public ImageShot(int x, int y) {
        super(x, y);
    }

   
    @Override
    public void drawShot(Graphics g) {
        if (!alive) {
            return;
        }

        if (shotImage != null) {

        	g.drawImage(shotImage, x_pos - 5, y_pos - 5, 10, 10, null); // 크기 보정

        	} else {

        	g.setColor(Color.yellow);

        	g.fillOval(x_pos, y_pos, radius, radius);

        	}
    }
}