package shootingspaceship;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.util.Random;

public class ItemEnemy extends Enemy{
	protected Item deadItem;
	private Random random = new Random();
	private Image enemyImage;
	
	public ItemEnemy(int x, int y, float delta_x, float delta_y, int max_x, int max_y, float delta_y_inc, ScoreSystem scoreSystem) {
		super(x, y, delta_x, delta_y, max_x, max_y, delta_y_inc);
		
		 int nerfOrBuff = random.nextInt(2); 

	        if (nerfOrBuff == 1) {
	            this.deadItem = new BuffItem(x, y, scoreSystem); 
	        } else { 
	            this.deadItem = new NerfItem(x, y);
	        }

		this.enemyImage = new ImageIcon(getClass().getResource("/shootingspaceship/img_6.png")).getImage();
	}
	
	public Item generateItem() {
		return deadItem;
	}
	
	@Override
	public void draw(Graphics g) {
		if (enemyImage != null) {
			g.drawImage(enemyImage, (int) x_pos - 15, (int) y_pos - 15, 30, 30, null);
		} else {
			g.setColor(Color.magenta);
			int[] x_poly = {(int) x_pos, (int) x_pos - 10, (int) x_pos, (int) x_pos + 10};
			int[] y_poly = {(int) y_pos + 15, (int) y_pos, (int) y_pos + 10, (int) y_pos};
			g.fillPolygon(x_poly, y_poly, 4);
		}
    }
	

}
