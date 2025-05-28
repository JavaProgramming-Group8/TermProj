package shootingspaceship;

import java.awt.Graphics;
import java.awt.Color;

public class HPEnemy extends Enemy {
	protected int hp;
	
	
	public HPEnemy(int x, int y, float delta_x, float delta_y, int max_x, int max_y, float delta_y_inc) {
		super(x, y, delta_x, delta_y, max_x, max_y, delta_y_inc);
		this.hp = 5;
	}
	
	@Override
	public boolean isCollidedWithShot(Shot[] shots) {
        for (Shot shot : shots) {
            if (shot == null) {
                continue;
            }
            if (-collision_distance <= (y_pos - shot.getY()) && (y_pos - shot.getY() <= collision_distance)) {
                if (-collision_distance <= (x_pos - shot.getX()) && (x_pos - shot.getX() <= collision_distance)) {
                    shot.collided();
                    --hp;
                    
                    if(hp <= 0) {
                    	return true;
                    }
                    else {
                    	return false;
                    }
                }
            }
        }
        return false;
    }
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.blue);
        int[] x_poly = {(int) x_pos, (int) x_pos - 10, (int) x_pos, (int) x_pos + 10};
        int[] y_poly = {(int) y_pos + 15, (int) y_pos, (int) y_pos + 10, (int) y_pos};
        g.fillPolygon(x_poly, y_poly, 4);
		
		if (hp > 0) {
	            int barWidth = 20; 
	            int barHeight = 5;
	            int barX = (int) x_pos - barWidth/2;
	            int barY = (int) y_pos - barHeight*2;


	            g.setColor(Color.red);
	            g.fillRect(barX, barY, barWidth, barHeight);

	            g.setColor(Color.green);
	            float hpFill = (float) hp / 5;
	            g.fillRect(barX, barY, (int) (barWidth * hpFill), barHeight);
	       }
		
	}

}
