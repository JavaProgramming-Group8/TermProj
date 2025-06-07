package shootingspaceship;

import java.awt.Color;
import java.awt.Graphics;

public class BasicEnemy extends Enemy{
	public BasicEnemy (int x, int y, float delta_x, float delta_y, int max_x, int max_y, float delta_y_inc) {
		super(x, y, delta_x, delta_y, max_x, max_y, delta_y_inc);
	}
	
	
	@Override
	public void draw(Graphics g) {
//        g.setColor(Color.red);
//        int[] x_poly = {(int) x_pos, (int) x_pos - 10, (int) x_pos, (int) x_pos + 10};
//        int[] y_poly = {(int) y_pos + 15, (int) y_pos, (int) y_pos + 10, (int) y_pos};
//        g.fillPolygon(x_poly, y_poly, 4);
        super.draw(g);
    }

}
