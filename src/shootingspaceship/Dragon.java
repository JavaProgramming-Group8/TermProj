package shootingspaceship;

import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.util.*;

public class Dragon extends Player {
	
	protected boolean firing = false;
	private boolean fireOnce = true;
	private ScoreSystem scoreSystem = new ScoreSystem();
	private GaugeBar gaugeBar = new GaugeBar(20, 20, 100, 10);

	private Image img4; // img_4 이미지 필드 추가
	
	public Dragon(int x, int y, int min_x, int max_x)
	{
		super(x, y, min_x, max_x);
		img4 = new ImageIcon(getClass().getResource("/shootingspaceship/img_4.png")).getImage();
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
	
	public void setScoreSystem(ScoreSystem scoreSystem)
	{
		this.scoreSystem = scoreSystem;
	}
	
	public void resetFireOnce()
	{
		fireOnce = true;
	}
	
	@Override
	public Shot generateShot()
	{
		List<Shot> shots = generateShots();
		
		if ((shots == null) || shots.isEmpty())
		{
			return null;
		}
		
		else
		{
			return shots.get(0);
		}
	}
	
	public List<Shot> generateShots()
	{
		int score = scoreSystem.getScore();

		DragonShot currentWeapon;

		int shotX = getX() + 25; // 이미지 중앙 (50/2)
		int shotY = getY();

		if (score >= 200)
		{
			currentWeapon = new ThreeWayShot(shotX, shotY);
		}
		else if (score >= 100)
		{
			currentWeapon = new DoubleShot(shotX, shotY);
		}
		else
		{
			currentWeapon = new NormalShot(shotX, shotY);
		}
		
		if (gaugeBar.getCurrentGauge() <= 0)
		{
			if (isFiring())
			{
				if (fireOnce)
				{
					fireOnce = false;
					
					return currentWeapon.fire();
				}
				
				else
				{
					return Collections.emptyList();
				}
			}
			
			else
			{
				fireOnce = true;
				return Collections.emptyList();
			}
		}
		
		if (isFiring())
		{
			gaugeBar.decreaseGauge(5);
		}
		
		return currentWeapon.fire();
	}
	
	// 드래곤 그리기 메서드 추가
	public void drawPlayer(Graphics g) {
        g.drawImage(img4, getX(), getY(), 50, 50, null);
    }
	
}
