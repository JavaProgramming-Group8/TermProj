package shootingspaceship;

import java.util.*;

public class Dragon extends Player {
	
	private double gauge = 0;
	private final int GAUGE_COST = 5;
	private final int SHOT_DELAY = 50;
	private long lastShotTime = 0;
	
	protected boolean firing = false;
	private ScoreSystem scoreSystem = new ScoreSystem();
	private GaugeBar gaugeBar = new GaugeBar(20, 20, 100, 10);
	
	public Dragon(int x, int y, int min_x, int max_x)
	{
		super(x, y, min_x, max_x);
	}
	
	public void setFiring(boolean firing)
	{
		this.firing = firing;
	}
	
	public boolean isFiring()
	{
		return this.firing;
	}
	
	public void rechargeGauge()
	{
		rechargeGauge(0.5);
	}
	
	public void rechargeGauge(double amount)
	{
		gaugeBar.increaseGauge(amount);
	}
	
	public int getGauge()
	{
		return gaugeBar.getCurrentGauge();
	}
	
	public GaugeBar getGaugeBar()
	{
		return gaugeBar;
	}
	
	public ScoreSystem getScoreSystem()
	{
		return scoreSystem;
	}
	
	public List<Shot> fire()
	{
		long currentTime = System.currentTimeMillis();
		
		if (currentTime - lastShotTime < SHOT_DELAY)
		{
			return Collections.emptyList();
		}
		
		int gaugeValue = gaugeBar.getCurrentGauge();
		
		List<Shot> shots = new ArrayList<>();
		
		boolean filledGauge = gaugeValue > 0;
		
		if (filledGauge)
		{
			gaugeBar.decreaseGauge(GAUGE_COST);
		}
		
		else
		{
			if (firing)
			{
				return Collections.emptyList();
			}
		}
		
		DragonShot weapon;
		int score = scoreSystem.getScore();
		
		if (score >= 300)
		{
			weapon = new ThreeWayShot(getX(), getY());
		}
		
		else if (score >= 200)
		{
			weapon = new DoubleShot(getX(), getY());
		}
		
		else
		{
			weapon = new NormalShot(getX(), getY());
		}
		
		shots.addAll(weapon.fire());
		lastShotTime = currentTime;
		
		return shots;
		
	}
	
}
