package shootingspaceship;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class GameWithPause extends Shootingspaceship {
    private boolean isPaused = false;
    private boolean isGameOver = false;
    private long lastFireTime = 0;
    private final long FIRE_INTERVAL = 200;
    private JFrame frame;
    private GaugeBar gaugeBar = new GaugeBar(20, 20, 100, 10);

    public GameWithPause(JFrame frame) {
        super();
        this.frame = frame;
        
        this.player = new Dragon(250, 400, 0, 500);
        
        setFocusable(true);
        requestFocusInWindow();

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_P && !isGameOver) {
                    isPaused = !isPaused;
                    repaint();

                    if (isPaused) {
                        JOptionPane.showMessageDialog(GameWithPause.this,
                                "일시정지 중입니다.\nP 키를 다시 눌러 계속합니다.",
                                "일시정지",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                
                if (e.getKeyCode() == KeyEvent.VK_UP)
                {
                	if (player instanceof Dragon)
                	{
                		Dragon dragon = (Dragon) player;
                		
                		for (Shot s : dragon.fire())
                		{
                			for (int i=0; i<shots.length; ++i)
                			{
                				if (shots[i] == null)
                				{
                					shots[i] = s;
                					
                					break;
                				}
                			}
                		}
                		
                		dragon.setFiring(true);
                		lastFireTime = System.currentTimeMillis();
                	}
                }
            }
            
            public void keyReleased(KeyEvent e)
            {
            	if (e.getKeyCode() == KeyEvent.VK_UP)
            	{
            		if (player instanceof Dragon)
            		{
            			((Dragon) player).setFiring(false);
            		}
            	}
            }
        });
    }

    @Override
    public void run() {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

        while (true) {
            if (isGameOver) break;

            if (!isPaused) {
            	if (player instanceof Dragon)
            	{
            		Dragon dragon = (Dragon) player;
            		
            		if (!dragon.isFiring())
            		{
            			dragon.rechargeGauge();
            		}
            	}
            	
                for (int i = 0; i < shots.length; i++) {
                    if (shots[i] != null) {
                        shots[i].moveShot(shotSpeed);
                        if (shots[i].getY() < 0) {
                            shots[i] = null;
                        }
                    }
                }

                if (playerMoveLeft) {
                    player.moveX(playerLeftSpeed);
                } else if (playerMoveRight) {
                    player.moveX(playerRightSpeed);
                }
                
                List<Object> enemiesToRemove = new ArrayList<>();
                for (Object obj: enemies)
                {
                	if (obj instanceof Enemy)
                	{
                		Enemy enemy = (Enemy) obj;
                		enemy.move();
                		
                		if (enemy.isCollidedWithPlayer(player))
                		{
                			triggerGameOver();
                			break;
                		}
                		
                		if (enemy.isCollidedWithShot(shots))
                		{
                			enemiesToRemove.add(enemy);
                			
                			if (player instanceof Dragon)
                			{
                				Dragon dragon = (Dragon) player;
                				dragon.getScoreSystem().addScore(10);
                			}
                		}
                	}
                }
                enemies.removeAll(enemiesToRemove);
                
                if (player instanceof Dragon)
                {
                	Dragon dragon = (Dragon) player;
                	
                	if(dragon.isFiring())
                	{
                		long currentTime = System.currentTimeMillis();
                		
                		if (currentTime - lastFireTime >= FIRE_INTERVAL)
                		{
                    		for (Shot s : dragon.fire())
                    		{
                    			for (int i=0; i < shots.length; ++i)
                    			{
                    				if (shots[i] == null)
                    				{
                    					shots[i] = s;
                    					break;
                    				}
                    			}
                    		}
                    		
                    		lastFireTime = currentTime;
                		}
                	}
                }

                repaint();
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                break;
            }

            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
    	if (player instanceof Dragon)
    	{
    		((Dragon) player).getGaugeBar().draw(g, ((Dragon) player).getGauge());
    	}
    	
        if (isGameOver) return;

        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());

        player.drawPlayer(g);
        for (Object obj : enemies)
        {
        	if (obj instanceof Enemy)
        	{
        		Enemy enemy = (Enemy) obj;
        		enemy.draw(g);
        	}
        }

        for (Shot s : shots) {
            if (s != null) s.drawShot(g);
        }
        
        if (player instanceof Dragon)
        {
        	Dragon dragon = (Dragon) player;
        	
        	int score = dragon.getScoreSystem().getScore();
        	
        	g.setColor(Color.WHITE);
        	g.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        	g.drawString("Score: " + score, getWidth()-130, 30);
        	
        	int gauge = dragon.getGauge();
        	int barW = 200;
        	int barH = 15;
        	int barX = (getWidth()-barW)/2;
        	int barY = getHeight()-barH-30;
        	
        	double gaugeRatio = gauge/100.0;
        	int filledWidth = (int)(barW * gaugeRatio);
        	
        	g.setColor(Color.GRAY);
        	g.fillRect(barX, barY, barW, barH);
        	g.setColor(Color.YELLOW);
        	g.fillRect(barX, barY, filledWidth, barH);
        	g.setColor(Color.BLACK);
        	g.drawRect(barX, barY, barW, barH);
        }

        if (isPaused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.WHITE);
            g.setFont(new Font("맑은 고딕", Font.BOLD, 32));
            g.drawString("일시정지", getWidth() / 2 - 80, getHeight() / 2);
        }
    }

    private void triggerGameOver() {
        isGameOver = true;

        frame.getContentPane().removeAll();
        GameOverScreen over = new GameOverScreen(frame);
        over.setBounds(0, 0, 500, 500);
        frame.add(over);
        frame.revalidate();
        frame.repaint();
    }
}