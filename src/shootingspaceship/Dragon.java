package shootingspaceship;

import java.util.*;

public class Dragon extends Player {
	private Attack attack;
	
	public Dragon (int x, int y, int min_x, int max_x, Attack attack)
	{
		super(x, y, min_x, max_x);
		this.attack = attack;
	}
	
	@Override
	public Shot generateShot()
	{
		ArrayList<Shot> shots = attack.fire(this.getX(), this.getY());
		return shots.get(0);
	}
	
	public ArrayList<Shot> generateShots()
	{
		return attack.fire(this.getX(), this.getY());
	}
	
	public void setAttack(Attack attack)
	{
		this.attack = attack;
	}
}