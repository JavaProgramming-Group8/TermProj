package shootingspaceship;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;


public class LevelThree extends Shootingspaceship {
	private JFrame frame;
	private ArrayList<Enemy> splitedBaby;
	protected ScoreSystem scoreLevelThree;
	private Random random;
	private boolean isGameOver = false;
	
	public LevelThree(JFrame frame) {
		super();
		this.frame = frame;
		this.splitedBaby = new ArrayList<>();
		this.scoreLevelThree = new ScoreSystem();
		this.random = new Random();
		
		if (timer != null) {
            timer.stop();
            for (ActionListener pastAL : timer.getActionListeners()) {
                timer.removeActionListener(pastAL);
            }
            timer.addActionListener(new addANewEnemyLevelThree());
            timer.start();
        }
	}
	
	private class addANewEnemyLevelThree implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
	        int randomPosX = (int) (random.nextFloat() * width);
	        float downSpeed = random.nextFloat() * enemyMaxDownSpeed * 2;
	        float horSpeed = random.nextFloat() * 2 * enemyMaxHorizonSpeed - enemyMaxHorizonSpeed;
	        Enemy newEnemy = null;
	        int enemyType = random.nextInt(4);

	        switch (enemyType) {
	            case 0:
	                newEnemy = new Enemy(randomPosX, 0, horSpeed, downSpeed, width, height, enemyDownSpeedInc);
	                break;
	            case 1:
	                newEnemy = new HPEnemy(randomPosX, 0, horSpeed, downSpeed, width, height, enemyDownSpeedInc);
	                break;
	            case 2:
	                newEnemy = new ItemEnemy(randomPosX, 0, horSpeed, downSpeed, width, height, enemyDownSpeedInc);
	                break;
	            case 3:
	                newEnemy = new SplitEnemy(randomPosX, 0, horSpeed, downSpeed, width, height, enemyDownSpeedInc);
	                break;
	        }
	        if (newEnemy != null) {
	            enemies.add(newEnemy);
	        }
		}
	}
	
	@Override
	public void run() {
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		
		 while (true) {
	           
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
	            }
	            
	            if (!splitedBaby.isEmpty()) {
	                enemies.addAll(splitedBaby);
	                splitedBaby.clear();
	            }

	            repaint();

	            try {
	                Thread.sleep(10);
	            } catch (InterruptedException ex) {
	            	
	            }

	            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
	        }
	}
	
	private int getPointsForEnemy(Enemy enemy) {
	    if (enemy instanceof SplitEnemy) {
	        return 30; 
	    } else if (enemy instanceof ItemEnemy) { 
	        return 20; 
	    } else if (enemy instanceof HPEnemy) {
	        return 30;
	    } else if (enemy instanceof Enemy) { 
	        return 10;
	    }
	    return 0; 
	}
	
    
    //void 클리어시 다음단계(boss)스크린
	
	private void triggerGameOver() {
        isGameOver = true;

        frame.getContentPane().removeAll();
        GameOverScreen over = new GameOverScreen(frame);
        over.setBounds(0, 0, 500, 500);
        frame.add(over);
        frame.revalidate();
        frame.repaint();
    }
	
	@Override
	public void paintComponent(Graphics g) {
		initImage(g);
		
		player.drawPlayer(g);
		
		Iterator enemyList = enemies.iterator();
        while (enemyList.hasNext()) {
            Enemy enemy = (Enemy) enemyList.next();
            enemy.draw(g);
            if (enemy.isCollidedWithShot(shots)) {
            	int points = getPointsForEnemy(enemy);
            	scoreLevelThree.addScore(points);
            	
                if(enemy instanceof SplitEnemy) {
                	SplitEnemy splitEnemy = (SplitEnemy) enemy;
                    ArrayList<Enemy> babyEnemies = splitEnemy.generateEnemy(splitEnemy.babyMaxDownSpeed, splitEnemy.babyMaxHorizonSpeed);
                    if (babyEnemies != null) {
                        splitedBaby.addAll(babyEnemies);
                    }
                } 
                else if(enemy instanceof ItemEnemy) {
                	ItemEnemy itemEnemy = (ItemEnemy) enemy;
                	Item item = itemEnemy.generateItem();
                	if(item != null) {
                		//아이템 능력 적용(applyTo)
                	}
                }
                enemyList.remove();
            }
            if (enemy.isCollidedWithPlayer(player)) {
                triggerGameOver();
            }
        }
        
        g.setColor(Color.WHITE); // 점수 글자색
        g.setFont(new Font("맑은 고딕", Font.BOLD, 16)); // 점수 폰트
        g.drawString("Score: " + scoreLevelThree.getScore(), 10, 40);
        
        //일정 score 달성시 게임 클리어(다음단계이동) 스크린 호출

        for (int i = 0; i < shots.length; i++) {
            if (shots[i] != null) {
                shots[i].drawShot(g);
            }
        }
		
		
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Boss Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        LevelThree gamePanel = new LevelThree(frame);
        frame.getContentPane().add(gamePanel, null); 
        frame.pack(); 
        frame.setResizable(false);
        frame.setVisible(true); 

        gamePanel.start(); 
    }

}

