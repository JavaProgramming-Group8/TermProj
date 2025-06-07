package shootingspaceship;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

public class BossLevel extends GameWithPause{
    protected Boss boss;
    protected ArrayList<EnemyShot> bossShots;
    protected int bossShotSpeed = 3;

    private Image backgroundImage;

    private long lastFireTime = 0;
    private final long FIRE_INTERVAL = 200;

    public BossLevel(JFrame frame) {
        super(frame);
        java.net.URL imgUrl = getClass().getResource("/shootingspaceship/img.png");
        if (imgUrl != null) {
            backgroundImage = new ImageIcon(imgUrl).getImage();
        }

        if(timer != null) {
            timer.stop();
        }
        enemies.clear();
		
        boss = new Boss(width / 2, height / 5, 1, 0, width, height, 0);
        enemies.add(boss);

        bossShots = new ArrayList<>();

        this.player = new Dragon(250, 400, 0, 500);
        

        addKeyListener(new BossKeyListener());
        setFocusable(true);
        requestFocusInWindow();
    }
    
    private void bossColliededWithShot() {
		 if (boss != null && boss.hp > 0) {
	            if (boss.isCollidedWithShot(shots)) {
	                if (boss.hp <= 0) {
	                    enemies.remove(boss);
	                    boss = null;
	                    triggerGameClear();
	                }
	            }
	        }
	}
	
	private void playerColliededWithShot() {
		Iterator<EnemyShot> enemyShotList = bossShots.iterator();
        while (enemyShotList.hasNext()) {
        	EnemyShot shot = (EnemyShot) enemyShotList.next();
           	
           	int[] playerXBound = {player.getX(), player.getX()-10, player.getX(), player.getX()+10};
           	int[] playerYBound = {player.getY(), player.getY()+15, player.getY()+10, player.getY()+15};
            Polygon playerBound = new Polygon(playerXBound, playerYBound, 4);
            Rectangle shotBound = new Rectangle(shot.getX(), shot.getY(), shot.radius, shot.radius);
            
            if(playerBound.intersects(shotBound)) {
            	shot.collided();
            	enemyShotList.remove();
        
            	triggerGameClear();
            	return;
            }
        }
	}
	
    private class BossKeyListener implements KeyListener {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                if (player instanceof Dragon) {
                    Dragon dragon = (Dragon) player;
                    dragon.setFiring(true);
                }
            }

            switch (e.getKeyCode())
            {
            case KeyEvent.VK_LEFT:
            	playerMoveRight = false;
            	playerMoveLeft = true;
            	break;
            	
        	case KeyEvent.VK_RIGHT:
        		playerMoveRight = true;
        		playerMoveLeft = false;
        		break;
        	}
        }

        @Override
        public void keyReleased(KeyEvent e)
        {
            if (e.getKeyCode() == KeyEvent.VK_UP)
            {
                if (player instanceof Dragon)
                {
                    Dragon dragon = (Dragon) player;
                    dragon.setFiring(false);
                    dragon.resetFireOnce();
                }
            }

            switch (e.getKeyCode())
            {
            case KeyEvent.VK_LEFT:
            	playerMoveLeft = false;
            	break;
            	
        	case KeyEvent.VK_RIGHT:
        		playerMoveRight = false;
        		break;
        	}
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }
    }
	
	@Override
	public void run() {
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

		while (true) {
			if (!isPaused) {
	        	if (player instanceof Dragon)
	        	{
	        		Dragon dragon = (Dragon) player;
	        		
	        		if (dragon.isFiring())
	        		{
	        			long now = System.currentTimeMillis();
	        			
	        			if (now - lastFireTime >= FIRE_INTERVAL)
	        			{
	        				List<Shot> newShots = dragon.generateShots();
	        				
	        				for (Shot s : newShots)
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

		        			lastFireTime = now;
	        			}
	        		}
	        		
	        		else
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
			 
             Iterator<Enemy> enemyList = enemies.iterator();
             while (enemyList.hasNext()) {
                 Enemy enemy = (Enemy) enemyList.next();
                 enemy.move();
                 
                 if(enemy instanceof Boss) {
                 	Boss newBoss = (Boss) enemy;
                 	EnemyShot newBossShot = newBoss.bossShot();
                 	if(newBossShot != null) {
                 		bossShots.add(newBossShot);
                 	}
                 }
             }
             
             Iterator<EnemyShot> enemyShotList = bossShots.iterator();
             while (enemyShotList.hasNext()) {
            	 EnemyShot shot = (EnemyShot) enemyShotList.next();
            	 shot.moveShot(bossShotSpeed);
            	 
            	 if(shot.getY() > height || !shot.alive) {
            		 enemyShotList.remove();
            	 }
             }
             
             
             bossColliededWithShot();
             playerColliededWithShot();
             
             repaint();
			}
			try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {}

            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		}
	}
	
	public void paintComponent(Graphics g) {
        // 배경 그리기
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        player.drawPlayer(g);

        for (int i = 0; i < shots.length; i++) {
            if (shots[i] != null) {
                shots[i].drawShot(g);
            }
        }

        boss.draw(g);

        for(EnemyShot shot: bossShots) {
            if(shot != null) {
                shot.drawShot(g);
            }
        }

        if (player instanceof Dragon) {
            Dragon dragon = (Dragon) player;
            int gauge = dragon.getGauge();
            int barW = 200;
            int barH = 15;
            int barX = (getWidth() - barW) / 2;
            int barY = getHeight() - barH - 30;

            double gaugeRatio = gauge / 100.0;
            int filledWidth = (int) (barW * gaugeRatio);

            g.setColor(Color.GRAY);
            g.fillRect(barX, barY, barW, barH);
            g.setColor(Color.YELLOW);
            g.fillRect(barX, barY, filledWidth, barH);
            g.setColor(Color.BLACK);
            g.drawRect(barX, barY, barW, barH);
        }
    }
	
	public void triggerGameClear() {
	    frame.getContentPane().removeAll();
	    GameClear clearScreen = new GameClear(frame);
	    clearScreen.setBounds(0, 0, 500, 500);
	    frame.add(clearScreen);
	    frame.revalidate();
	    frame.repaint();
	}
}
