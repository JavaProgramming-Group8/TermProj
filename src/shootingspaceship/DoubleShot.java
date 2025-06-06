package shootingspaceship;

public class DoubleShot extends Shot {
	private int power;
	
	public DoubleShot(int x, int y)
	{
		super(x, y);
		this.power = 2;
	}
	
	public int getPower()
	{
		return power;
	}
}
