package shootingspaceship;

import java.util.*;

public class DoubleShot extends DragonShot {
	
	private int x, y;

	public DoubleShot(int x, int y)
	{
		super(x, y);
		this.x = x;
		this.y = y;
	}
	
	@Override
	public List<Shot> fire()
	{
		List<Shot> shots = new ArrayList<>();
		shots.add(new ImageShot(x - 7, y));
		shots.add(new ImageShot(x + 7, y));
		
		return shots;
	}

}
