package shootingspaceship;

import java.util.*;

public class Attack {
	private String attackType;
	
	public Attack(String attacktype)
	{
		this.attackType = attacktype;
	}
	
	public void setAttackType(String attacktype)
	{
		this.attackType = attacktype;
	}
	
	public ArrayList<Shot> fire(int x, int y)
	{
		ArrayList<Shot> shots = new ArrayList<>();
		
		if (attackType.equals("NORMAL"))
		{
			shots.add(new NormalShot(x, y));
		}
		
		else if (attackType.equals("DOUBLE"))
		{
			shots.add(new DoubleShot(x, y));
		}
		
		else if (attackType.equals("THREEWAY"))
		{
			shots.add(new ThreeWayShot(x, y));
			shots.add(new ThreeWayShot(x-10, y-10));
			shots.add(new ThreeWayShot(x+10, y+10));
		}
		
		return shots;
	}
}
