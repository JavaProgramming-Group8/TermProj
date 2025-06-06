package shootingspaceship;

import java.awt.*;

public class BuffItem extends Item {
	protected ScoreSystem scoreSystem;
	
	public BuffItem(int x, int y, ScoreSystem scoreSystem) {
		super(x, y);
		this.scoreSystem = scoreSystem;
	}
	
	@Override
	public void applyTo(Object obj) {
		if (obj instanceof ScoreReceiver) {
			((ScoreReceiver)obj).addScore(20);
		}
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(x_pos, y_pos, 15, 15);
	}

}