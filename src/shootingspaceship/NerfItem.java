package shootingspaceship;

import java.awt.*;

public class NerfItem extends Item {
	public NerfItem(int x, int y) {
		super(x, y);
	}
	
	@Override
	public void applyTo(Object obj) {
		if(obj instanceof NerfEffect) 
			((NerfEffect)obj).startNerfEffect(); 
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(x_pos, y_pos, 15, 15);
	}

}
