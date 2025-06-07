package shootingspaceship;

import javax.swing.*;
import java.awt.*;

public class Boss extends HPEnemy{
	protected int shot_x;
	protected int shot_y;
	protected int bossShotCoolTime;
	protected Image bossImage;
	int bossWidth = 60;  // 너비 조절
	int bossHeight = 60; // 높이 조절
	
	public Boss(int x, int y, float delta_x, float delta_y, int max_x, int max_y, float delta_y_inc) {
		super(x, y, delta_x, delta_y, max_x, max_y, delta_y_inc);
		this.shot_x = x;
		this.shot_y = y;
		this.hp = 50;
		this.bossShotCoolTime = 30;
	}
	
	@Override
	public void move() {
		x_pos += delta_x;
		shot_x += delta_x;
		
		if(x_pos < 0) {
			x_pos = 0;
			shot_x = 0;
			delta_x = -delta_x;
		} else if(x_pos > max_x) {
			x_pos = max_x;
			shot_x = max_x;
			delta_x = -delta_x;
		}
		--bossShotCoolTime;
	}
	
	
	public EnemyShot bossShot() {
		if(hp > 0 && bossShotCoolTime <= 0) {
			bossShotCoolTime = 30;
			EnemyShot enemyShot = new EnemyShot(shot_x, shot_y);
			return enemyShot;
		}
		return null;
	}
	
	@Override
	public void draw(Graphics g) {
		this.bossImage = new ImageIcon(getClass().getResource("/shootingspaceship/img_1.png")).getImage();
//		g.setColor(Color.yellow);
//        int[] x_poly = {(int) x_pos, (int) x_pos - 20, (int) x_pos, (int) x_pos + 20};
//        int[] y_poly = {(int) y_pos + 30, (int) y_pos, (int) y_pos + 20, (int) y_pos};
//        g.fillPolygon(x_poly, y_poly, 4);
		if (bossImage != null) {
			g.drawImage(bossImage, (int)x_pos - 20, (int)y_pos, bossWidth, bossHeight, null);
		} else {
			g.setColor(Color.yellow);
			int[] x_poly = {(int) x_pos, (int) x_pos - 20, (int) x_pos, (int) x_pos + 20};
			int[] y_poly = {(int) y_pos + 30, (int) y_pos, (int) y_pos + 20, (int) y_pos};
			g.fillPolygon(x_poly, y_poly, 4);
		}
		
		if (hp > 0) {
            int barWidth = 400; 
            int barHeight = 20;
            int barX = 50;
            int barY = 2;

            g.setColor(Color.red);
            g.fillRect(barX, barY, barWidth, barHeight);

            g.setColor(Color.green);
            float hpFill = (float) hp / 50;
            g.fillRect(barX, barY, (int)(barWidth * hpFill), barHeight);
       }
	}

	
}
