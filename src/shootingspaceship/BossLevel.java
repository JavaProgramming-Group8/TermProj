package shootingspaceship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;

public class BossLevel extends Shootingspaceship{
	protected Boss boss;
	protected ArrayList<EnemyShot> bossShots;
	protected int bossShotSpeed = 3;

    private JFrame frame;
    private boolean isGameOver = false;
	
	public BossLevel(JFrame frame) {
		super();
		this.frame = frame;
		
		if(timer != null) {
			timer.stop();
		}
		enemies.clear();
		
		boss = new Boss(width / 2, height / 5, 1, 0, width, height, 0);
		enemies.add(boss);
		
		bossShots = new ArrayList<>();
		
	}
	
	private void bossColliededWithShot() {
		 if (boss != null && boss.hp > 0) {
	            if (boss.isCollidedWithShot(shots)) {
	                if (boss.hp <= 0) {
	                    enemies.remove(boss);
	                    boss = null;
	                    //게임 클리어 화면
	                }
	            }
	        }
	}
	
	private void playerColliededWithShot() {
		Iterator enemyShotList = bossShots.iterator();
        while (enemyShotList.hasNext()) {
        	EnemyShot shot = (EnemyShot) enemyShotList.next();
           	
           	int[] playerXBound = {player.getX(), player.getX()-10, player.getX(), player.getX()+10};
           	int[] playerYBound = {player.getY(), player.getY()+15, player.getY()+10, player.getY()+15};
            Polygon playerBound = new Polygon(playerXBound, playerYBound, 4);
            Rectangle shotBound = new Rectangle(shot.getX(), shot.getY(), shot.radius, shot.radius);
            
            if(playerBound.intersects(shotBound)) {
            	shot.collided();
            	enemyShotList.remove();
        
            	triggerGameOver();
            	return;
            }
        }
	}
	
	@Override
	public void run() {
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		
		while(true) {
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

             Iterator enemyList = enemies.iterator();
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
             
             Iterator enemyShotList = bossShots.iterator();
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

             try {
                 Thread.sleep(10);
             } catch (InterruptedException ex) {
                 
             }

             Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		}
	}
	
	public void paintComponent(Graphics g) {
		initImage(g);

        
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
	
	
	public static void main(String[] args) {
        JFrame frame = new JFrame("Boss Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BossLevel gamePanel = new BossLevel(frame);
        frame.getContentPane().add(gamePanel, null); 
        frame.pack(); 
        frame.setResizable(false);
        frame.setVisible(true); 

        gamePanel.start(); 
    }

}
