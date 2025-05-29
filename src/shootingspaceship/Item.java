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
	
	public void applyTo(Shootingspaceship ship) {
	}
	
    public void draw(Graphics g) {
    }
    
    public boolean isCollideWith(Player player) {
    	int dx = Math.abs(x - player.getX());
    	int dy = Math.abs(y - player.getY());
    	return dx <= 10 && dy <= 10;
    }
}

class BuffItem extends Item{

	public BuffItem(int x, int y) {
		super(x, y);
	}
	
	
	//한 번에 두 발 쏘는 구조로 변경??
	@Override
	public void applyTo(Shootingspaceship ship) {
	}
	
}

class NerfItem extends Item {
	public NerfItem(int x, int y) {
		super(x, y);
	}
	
	@Override
	public void applyTo(Shootingspaceship ship) {
		boolean temp = ship.playerMoveLeft; 
		ship.playerMoveLeft = ship.playerMoveRight;
		ship.playerMoveRight = temp; 	
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
	}
}

