package shootingspaceship;

public class DoubleShot extends DragonShot {
	
	private int x, y;

	public DoubleShot(int x, int y) {
		// TODO Auto-generated method stub
		super(x, y);
		this.x = x;
		this.y = y;
	}
	
	@Override
	public Shot[] createShots()
	{
		return new Shot[]
				{
						new NormalShot(x-10, y),
						new NormalShot(x+10, y)
				};
	}

}
