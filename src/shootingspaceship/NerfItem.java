package shootingspaceship;

import java.awt.*;

public class NerfItem extends Item {
	public NerfItem(int x, int y) {
		super(x, y);
	}
	
	@Override
	public void applyTo(Shootingspaceship ship) {
		if(ship instanceof NerfEffect) {
			((NerfEffect)ship).activateNerfEffect(); 
		}
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(x, y, 15, 15);
	}

}
