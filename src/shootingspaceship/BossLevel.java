package shootingspaceship;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class BossLevel extends GameWithPause{
	protected Boss boss;
	protected ArrayList<EnemyShot> bossShots;
	protected int bossShotSpeed = 3;

	public BossLevel(JFrame frame) {
		super(frame);
		
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
        
            	triggerGameOver();
            	return;
            }
        }
	}
	
	@Override
	public void run() {
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

		while (true) {
			if (!isPaused) { // ★ 일시정지 체크 추가
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
}
