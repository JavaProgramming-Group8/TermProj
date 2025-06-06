package shootingspaceship;

import java.util.*;

public class ThreeWayShot extends DragonShot {
	
	private int x, y;
	
	public ThreeWayShot(int x, int y)
	{
		super(x, y);
		this.x = x;
		this.y = y;
	}
	
	@Override
	public List<Shot> fire()
	{
		List<Shot> shots = new ArrayList<>();
		shots.add(new Shot(x-15, y));
		shots.add(new Shot(x, y));
		shots.add(new Shot(x+15, y));
		
		return shots;
	}
}
