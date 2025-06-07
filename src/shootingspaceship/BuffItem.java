package shootingspaceship;

import java.awt.*;
import javax.swing.*;

public class BuffItem extends Item {
	protected ScoreSystem scoreSystem;
	protected Image buffItemImage = new ImageIcon(getClass().getResource("/shootingspaceship/buff.png")).getImage();;

	public BuffItem(int x, int y, ScoreSystem scoreSystem) {
		super(x, y);
		this.scoreSystem = scoreSystem;
	}

	@Override
	public void applyTo(Object obj) {
		if (obj instanceof ScoreReceiver) {
			((ScoreReceiver) obj).addScore(20);
		}
	}

	@Override
	public void draw(Graphics g) {
//		g.setColor(Color.WHITE);
//		g.fillRect(x_pos, y_pos, 15, 15);
		if (buffItemImage != null) {
			g.drawImage(buffItemImage, x_pos, y_pos, 20, 20, null);
		} else {
			g.setColor(Color.WHITE);
			g.fillRect(x_pos, y_pos, 15, 15); // fallback
		}

	}

}