package shootingspaceship;

import java.awt.*;

public class GaugeBar {
	
	private int x, y, width, height;
	private double currentGauge = 100.0;
	private final double MAX_GAUGE = 100.0;
	private final double MIN_GAUGE = 0.0;
	
	public GaugeBar (int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void draw(Graphics g, double percent)
	{
		g.setColor(Color.GRAY);
		g.fillRect(x, y, width, height);
		
		g.setColor(Color.YELLOW);
		
		g.fillRect(x, y, width, height);
		
		g.setColor(Color.BLACK);
		g.fillRect(x, y, width, height);
	}
	
	public int getCurrentGauge()
	{
		return (int) currentGauge;
	}
	
	public void increaseGauge(double amount)
	{
		currentGauge = Math.min(currentGauge + amount, MAX_GAUGE);
	}
	
	public void decreaseGauge(double amount)
	{
		currentGauge = Math.max(currentGauge - amount, MIN_GAUGE);
	}
}
