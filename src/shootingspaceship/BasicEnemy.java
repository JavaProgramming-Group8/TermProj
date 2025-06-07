package shootingspaceship;

import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;

public class BasicEnemy extends Enemy{
	public BasicEnemy (int x, int y, float delta_x, float delta_y, int max_x, int max_y, float delta_y_inc) {
		super(x, y, delta_x, delta_y, max_x, max_y, delta_y_inc);
		this.enemyImage = new ImageIcon(getClass().getResource("/shootingspaceship/img_2.png")).getImage();
	}
	
	
	@Override
	public void draw(Graphics g) {
		if (enemyImage != null) {
			g.drawImage(enemyImage, (int) x_pos - 15, (int) y_pos - 15, 30, 30, null);  // null → this 도 가능
		} else {
			// fallback: 도형
			g.setColor(Color.red);
			int[] x_poly = {(int) x_pos, (int) x_pos - 10, (int) x_pos, (int) x_pos + 10};
			int[] y_poly = {(int) y_pos + 15, (int) y_pos, (int) y_pos + 10, (int) y_pos};
			g.fillPolygon(x_poly, y_poly, 4);
		}
    }

}
