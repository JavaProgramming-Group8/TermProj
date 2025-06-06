package shootingspaceship;

public class NormalShot extends DragonShot {
	
	private int x, y;

	public NormalShot(int x, int y) {
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
						new Shot(x, y)
				};
	}

}
