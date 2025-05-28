package shootingspaceship;

import java.awt.Color;
import java.awt.Graphics;

public class EnemyShot extends Shot{
	
	public EnemyShot(int x, int y) {
		super(x,y);
		this.alive = true;
	}
	
	public void drawShot(Graphics g) {
	        if (!alive) {
	            return;
	        }
	        
	        g.setColor(Color.green);
	        g.fillOval(x_pos, y_pos, radius, radius);
	    }
}
