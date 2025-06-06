package shootingspaceship;

import java.util.*;

public abstract class DragonShot extends Shot {

	public DragonShot(int x, int y) {
		// TODO Auto-generated method stub
		super(x, y);
	}
	
	public abstract Shot[] createShots();
	
	public List<Shot> fire()
	{
		return Arrays.asList(createShots());
	}
}
