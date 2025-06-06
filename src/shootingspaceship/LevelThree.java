package shootingspaceship;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class LevelThree extends GameWithPause implements NerfEffect{
	private JFrame frame;
	private ArrayList<BasicEnemy> splitedBaby;
	private ArrayList<Item> items;
	protected ScoreSystem scoreLevelThree;
	private Random random;

	private boolean nerfed = false;
	private long nerfTime = 0; 

	
	public LevelThree(JFrame frame) {
		super(frame);
		this.frame = frame;
		this.splitedBaby = new ArrayList<>();
		this.items = new ArrayList<>();
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
	                newEnemy = new BasicEnemy(randomPosX, 0, horSpeed, downSpeed, width, height, enemyDownSpeedInc);
	                break;
	            case 1:
	                newEnemy = new HPEnemy(randomPosX, 0, horSpeed, downSpeed, width, height, enemyDownSpeedInc);
	                break;
	            case 2:
	                newEnemy = new ItemEnemy(randomPosX, 0, horSpeed, downSpeed, width, height, enemyDownSpeedInc, scoreLevelThree);
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
	            
	            //아이템 아래로 이동 및 효과적용 
	            Iterator<Item> it = items.iterator();
	            while (it.hasNext()) {
	            	Item item = it.next();
	            	item.move();
	            	
	            	if(item.isCollideWith(player)) {
	            		item.applyTo(player);
	            		it.remove();
	            	}
	            }
	            
	            if(scoreLevelThree.getScore() > 300) {
	            	scoreLevelThree.scoreReset();
	            	bossLevelTrigger();
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
	
	//nerf item 동작
	public void activateNerfEffect() {
		System.out.println("효과제발");
		nerfed = true;
		nerfTime = System. currentTimeMillis() + 15000;
	}
	
	private boolean isNerfActive() {
		return nerfed && System.currentTimeMillis() < nerfTime;
	}
	
	public void NerfKey(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_LEFT) {
			playerMoveRight = true;
		} else if (key == KeyEvent.VK_RIGHT) {
			playerMoveLeft = true;
		} else if (key == KeyEvent.VK_DOWN) {
			for (int i = 0; i < shots.length; ++i) {
				if (shots[i] == null) {
					shots[i] = player.generateShot();
					break;
				}
			}
		}
		
	}
	
	public void bossLevelTrigger() {
		frame.getContentPane().removeAll();
		JFrame frame = new JFrame("Boss Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BossLevel gamePanel = new BossLevel(frame);
        frame.getContentPane().add(gamePanel, null); 
        frame.pack(); 
        frame.setResizable(false);
        frame.setVisible(true); 

        gamePanel.start(); 
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
                    ArrayList<BasicEnemy> babyEnemies = splitEnemy.generateEnemy(splitEnemy.babyMaxDownSpeed, splitEnemy.babyMaxHorizonSpeed);
                    if (babyEnemies != null) {
                        splitedBaby.addAll(babyEnemies);
                    }
                } 
                else if(enemy instanceof ItemEnemy) {
                	ItemEnemy itemEnemy = (ItemEnemy) enemy;
                	Item item = itemEnemy.generateItem();
                	if(item != null) {
                		items.add(item);//아이템을 리스트에 추가
                	}
                }
                enemyList.remove();
            }
            if (enemy.isCollidedWithPlayer(player)) {
                triggerGameOver();
            }
        }
        
        Iterator itemList = items.iterator();
        while (itemList.hasNext()) {
        	Item item = (Item) itemList.next();
        	item.draw(g);
        }
        
        g.setColor(Color.WHITE); // 점수 글자색
        g.setFont(new Font("맑은 고딕", Font.BOLD, 16)); // 점수 폰트
        g.drawString("Score: " + scoreLevelThree.getScore(), 10, 40);
        
        
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

