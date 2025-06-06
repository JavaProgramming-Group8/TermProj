package shootingspaceship;

import java.util.*;

public class NormalShot extends DragonShot {
	
	private int x, y;

	public NormalShot(int x, int y) {
		super(x, y);
		this.x = x;
		this.y = y;
	}
	
	@Override
	public List<Shot> fire()
	{
		List<Shot> shots = new ArrayList<>();
		shots.add(new Shot (x, y));
		
		return shots;
	}

}
