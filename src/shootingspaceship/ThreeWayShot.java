package shootingspaceship;

public class ThreeWayShot extends DragonShot {
	
	private int x, y;
	
	public ThreeWayShot(int x, int y)
	{
		super(x, y);
		this.x = x;
		this.y = y;
	}
	
	@Override
	public Shot[] createShots()
	{
		return new Shot[]
				{
						new Shot(x-20, y),
						new Shot(x, y),
						new Shot(x+20, y)
				};
	}
}
