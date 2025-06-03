package shootingspaceship;

import java.awt.*;

public class Item {
	protected int x, y;
	protected int speed = 1; 
	
	public Item(int x, int y) {
		this.x = x; 
		this.y = y;
	}
	
	public void move() {
		y += speed;
	}
	
	public void applyTo(Object obj) {
	}
	
	public void applyTo(Shootingspaceship ship) {
	}
	
    public void draw(Graphics g) {
    }
    
    public boolean isCollideWith(Player player) {
    	int dx = Math.abs(x - player.getX());
    	int dy = Math.abs(y - player.getY());
    	return dx <= 30 && dy <= 30;
    }

}



