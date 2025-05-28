package shootingspaceship;

import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class SplitEnemy extends Enemy{
	protected float babyMaxDownSpeed = 1;
	protected float babyMaxHorizonSpeed = 1;
	private Random random;


	public SplitEnemy(int x, int y, float delta_x, float delta_y, int max_x, int max_y, float delta_y_inc) {
		super(x, y, delta_x, delta_y, max_x, max_y, delta_y_inc);
		this.random = new Random();
	}
	
	public ArrayList<Enemy> generateEnemy(float babyMaxDownSpeed, float babyMaxHorizonSpeed){
		ArrayList<Enemy> babyEnemies = new ArrayList<>();
		
		for(int i = 0; i<4; ++i) {
			int babyX = (int) (x_pos + (random.nextInt(40)-20));
			int babyY = (int) (y_pos + (random.nextInt(40)-20));
			
			float downspeed;
            do {
                downspeed = random.nextFloat() * babyMaxDownSpeed;
            } while (downspeed == 0);

            float horspeed = random.nextFloat() * 2 * babyMaxHorizonSpeed - babyMaxHorizonSpeed;

            Enemy babyEnemy = new Enemy(babyX, babyY, horspeed, downspeed, max_x, max_y, delta_y_inc);
            babyEnemies.add(babyEnemy);
		}
		return babyEnemies;
	}
	
	
	@Override
	public void draw(Graphics g) {
        g.setColor(Color.orange);
        int[] x_poly = {(int) x_pos, (int) x_pos - 10, (int) x_pos, (int) x_pos + 10};
        int[] y_poly = {(int) y_pos + 15, (int) y_pos, (int) y_pos + 10, (int) y_pos};
        g.fillPolygon(x_poly, y_poly, 4);
    }
	
}

