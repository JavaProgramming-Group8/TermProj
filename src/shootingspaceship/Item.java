package shootingspaceship;

import java.awt.*;

public class Item {
	protected int x_pos, y_pos;
	protected int speed = 1; 
	protected final int collision_distance = 30; 
	
	public Item(int x, int y) {
		this.x_pos = x; 
		this.y_pos = y;
	}
	
	public void move() {
		y_pos += speed;  
	}
	
	public void applyTo(Object obj) {
	}
	
    public void draw(Graphics g) {
    }
    
    //Enemy 클래스에도 isCollidedWithPlayer있지만 Item is-a Enemy 관계가 성립하지 않아서 안됨
    public boolean isCollideWithPlayer(Player player) {
    	if((-collision_distance <= (y_pos - player.getY()) && (y_pos - player.getY()) <= collision_distance)) {
    		if ((-collision_distance <= (x_pos - player.getX()) && (x_pos - player.getX()) <= collision_distance)) {
    			return true; 
    		}
    	}
    	return false;
    }

}


