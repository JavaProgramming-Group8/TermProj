package shootingspaceship;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class LevelThree extends GameWithPause implements NerfEffect, ScoreReceiver{
    private JFrame frame;
    private ArrayList<BasicEnemy> splitedBaby;
    private ArrayList<Item> items;
    protected ScoreSystem scoreLevelThree;
    private Random random;

    private Image backgroundImage; // 배경 이미지 필드 추가

    private boolean isNerfMode = false;
    private long nerfEndTime = 0;
    
    private long lastFireTime = 0;
    private final long FIRE_INTERVAL = 200;
    
    public LevelThree(JFrame frame) {
        super(frame);
        this.frame = frame;
        this.splitedBaby = new ArrayList<>();
        this.items = new ArrayList<>();
        this.scoreLevelThree = new ScoreSystem();
        this.random = new Random();

        // 배경 이미지 로드 (이미지는 src/shootingspaceship/background.jpg에 위치)
        java.net.URL imgUrl = getClass().getResource("/shootingspaceship/background2.jpg");
        if (imgUrl != null) {
            backgroundImage = new ImageIcon(imgUrl).getImage();
        } else {
            System.out.println("배경 이미지가 없습니다: /shootingspaceship/background.jpg");
        }

        this.player = new Dragon(250, 400, 0, 500);
        Dragon dragon = (Dragon) this.player;
        dragon.setScoreSystem(scoreLevelThree);

        addKeyListener(new NerfKeyListener());

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
	
	private class NerfKeyListener implements KeyListener{
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_UP)
			{
				if(player instanceof Dragon)
				{
					Dragon dragon = (Dragon) player;
					dragon.setFiring(true);
				}
			}
			
			if(isNerfActive()) {
				e.consume();
				
				switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    playerMoveRight = true;
                    playerMoveLeft = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    playerMoveLeft = true;
                    playerMoveRight = false;
                    break;
                case KeyEvent.VK_DOWN:
                	if(player instanceof Dragon)
					{
						Dragon dragon = (Dragon) player;
						dragon.setFiring(false);
						dragon.resetFireOnce();
					}
                    break;
                	}
				} 
			}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_UP)
			{
				if(player instanceof Dragon)
				{
					Dragon dragon = (Dragon) player;
					dragon.setFiring(false);
					dragon.resetFireOnce();
				}
			}
			
			if(isNerfActive()) {
				e.consume();
				
				switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					playerMoveRight = false; 
					break; 
				case KeyEvent.VK_RIGHT:
					playerMoveLeft = false;
					break;					
				}
			} 
		}
		
		@Override
		public void keyTyped(KeyEvent e) {
			if (isNerfActive()) {
				e.consume();
			}
		}
	}

	
	@Override
	public void run() {
	    Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

	    while (true) {
	        if (isPaused) {
	            try {
	                Thread.sleep(10);
	            } catch (InterruptedException ex) {}
	            continue; // 일시정지 상태면 아래 로직을 건너뜀
	        }

	        // ↓↓↓ 기존 게임 로직 ↓↓↓
	        
	        if (!isPaused)
	        {
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
		        
		        boolean addSplitedEnemyNextFrame = false;

		        Iterator<Enemy> enemyList = enemies.iterator();
		        while (enemyList.hasNext()) {
		            Enemy enemy = (Enemy) enemyList.next();
		            enemy.move();
		            
		            if (enemy.isCollidedWithShot(shots))
		            {
		            	int points = getPointsForEnemy(enemy);
		            	scoreLevelThree.addScore(points);
		            	
		            	if (enemy instanceof SplitEnemy)
		            	{
		            		SplitEnemy splitEnemy = (SplitEnemy) enemy;
		            		ArrayList<BasicEnemy> babyEnemies = splitEnemy.generateEnemy(splitEnemy.babyMaxDownSpeed, splitEnemy.babyMaxHorizonSpeed);
		            		
		            		if (babyEnemies != null)
		            		{
		            			splitedBaby.addAll(babyEnemies);
		            			addSplitedEnemyNextFrame = true;
		            		}
		            	}
		            	
		            	else if (enemy instanceof ItemEnemy)
	            		{
	            			ItemEnemy itemEnemy = (ItemEnemy) enemy;
	            			Item item = itemEnemy.generateItem();
	            			
	            			if (item != null)
	            			{
	            				items.add(item);
	            			}
	            		}
	            		
	            		enemyList.remove();
		            }
		            
		            if (enemy.isCollidedWithPlayer(player))
	            	{
	            		triggerGameOver();
	            	}
		        }
		        
		        if (addSplitedEnemyNextFrame)
	            {
	            	enemies.addAll(splitedBaby);
	            	splitedBaby.clear();
	            }
		            
		            //아이템 아래로 이동 및 효과적용 
		        Iterator<Item> it = items.iterator();
		        while (it.hasNext()) {
		        	Item item = it.next();
		        	item.move();
		            	
		            if(item.isCollideWithPlayer(player)) {
		            	item.applyTo(this);
		            	it.remove();
		            	repaint();
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
		public void startNerfEffect() {
			isNerfMode = true; 
			nerfEndTime = System.currentTimeMillis() + 15000;
		}
		
		private boolean isNerfActive() {
			return isNerfMode && System.currentTimeMillis() < nerfEndTime;
		}
		
		//buff
		@Override
		public void addScore(int point) {
			scoreLevelThree.addScore(point);
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
        // 배경 그리기
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        player.drawPlayer(g);

        Iterator<Enemy> enemyList = enemies.iterator();
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
                		items.add(item);
                	}
                }
                enemyList.remove();
            }
            if (enemy.isCollidedWithPlayer(player)) {
                triggerGameOver();
            }
        }
        
        Iterator<Item> itemList = items.iterator();
        while (itemList.hasNext()) {
        	Item item = (Item) itemList.next();
        	item.draw(g);
        }
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        g.drawString("Score: " + scoreLevelThree.getScore(), 10, 40);
        
        
        for (int i = 0; i < shots.length; i++) {
            if (shots[i] != null) {
                shots[i].drawShot(g);
            }
        }
        
        if (player instanceof Dragon)
        {
        	Dragon dragon = (Dragon) player;
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
		
		
	}
}

