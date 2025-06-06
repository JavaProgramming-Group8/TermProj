package shootingspaceship;

import java.awt.*;

public class NerfItem extends Item {
	public NerfItem(int x, int y) {
		super(x, y);
	}
	
	@Override
	public void applyTo(Object obj) {
		System.out.println("[NerfItem] applyTo called. Object type: " + obj.getClass().getSimpleName());

        if (obj instanceof NerfEffect) {
            System.out.println("[NerfItem] Object is NerfEffect. Activating effect...");
            ((NerfEffect)obj).activateNerfEffect();
        } else {
            System.out.println("[NerfItem] Object is NOT NerfEffect.");
        }
		/*
		if(obj instanceof NerfEffect) {
			((NerfEffect)obj).activateNerfEffect(); 
		}*/
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(x_pos, y_pos, 15, 15);
	}

}
