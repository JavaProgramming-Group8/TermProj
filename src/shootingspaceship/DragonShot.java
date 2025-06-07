package shootingspaceship;

import java.util.*;

public abstract class DragonShot extends Shot {

	public DragonShot(int x, int y) {
		super(x, y);
	}
	
	public abstract List<Shot> fire();
	
	public Shot generateShot()
	{
		List<Shot> shots = fire();
		
		if ((shots != null) && (!shots.isEmpty()))
		{
			return shots.get(0);
		}
		
		return null;
	}
}
