package shootingspaceship;

import java.util.*;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.ImageIcon;

public class Dragon extends Player {
	
	protected boolean firing = false;
	private boolean fireOnce = true;
	private ScoreSystem scoreSystem = new ScoreSystem();
	private GaugeBar gaugeBar = new GaugeBar(20, 20, 100, 10);
	protected Image playerImage = new ImageIcon(getClass().getResource("/shootingspaceship/img_4.png")).getImage();

	
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
		
		if (score >= 200)
		{
			currentWeapon = new ThreeWayShot(getX(), getY());
		}
		
		else if (score >= 100)
		{
			currentWeapon = new DoubleShot(getX(), getY());
		}
		
		else
		{
			currentWeapon = new NormalShot(getX(), getY());
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
	
	public void drawPlayer(Graphics g) {

	      if (playerImage != null) {
	          g.drawImage(playerImage, x_pos - 20, y_pos - 20, 40, 40, null); // 크기 조절 및 위치 보정
	      } else {
	          // fallback
	          g.setColor(Color.red);
	          int[] x_poly = {x_pos, x_pos - 10, x_pos, x_pos + 10};
	          int[] y_poly = {y_pos, y_pos + 15, y_pos + 10, y_pos + 15};
	          g.fillPolygon(x_poly, y_poly, 4);
	      }
	  }
	
}
