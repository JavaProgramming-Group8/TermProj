package shootingspaceship;

import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;
import javax.swing.*;

public class GameWithPause extends Shootingspaceship {
    protected boolean isPaused = false;
    private boolean isGameOver = false;
    protected JFrame frame;

    public GameWithPause(JFrame frame) {
        super();
        this.frame = frame;

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
            }
        });
    }

    @Override
    public void run() {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

        while (true) {
            if (isGameOver) break;

            if (!isPaused) {
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

                Iterator it = enemies.iterator();
                while (it.hasNext()) {
                    Enemy enemy = (Enemy) it.next();
                    enemy.move();

                    if (enemy.isCollidedWithPlayer(player)) {
                        triggerGameOver();
                        break;
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
        if (isGameOver) return;

        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());

        player.drawPlayer(g);
        for (Iterator it = enemies.iterator(); it.hasNext();) {
            Enemy enemy = (Enemy) it.next();
            enemy.draw(g);

            if (enemy.isCollidedWithShot(shots)) {
                it.remove();
            }
        }
        for (Shot s : shots) {
            if (s != null) s.drawShot(g);
        }

        if (isPaused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.WHITE);
            g.setFont(new Font("맑은 고딕", Font.BOLD, 32));
            g.drawString("일시정지", getWidth() / 2 - 80, getHeight() / 2);
        }
    }
    
    protected void triggerGameClear() {
    	if (th != null) {
            th.interrupt(); 
        }
    	frame.getContentPane().removeAll();
        GameClear clear = new GameClear(frame);
        clear.setBounds(0, 0, 500, 500);
        frame.add(clear);
        frame.revalidate();
        frame.repaint();
    }

    protected void triggerGameOver() {
        isGameOver = true;

        frame.getContentPane().removeAll();
        GameOverScreen over = new GameOverScreen(frame);
        over.setBounds(0, 0, 500, 500);
        frame.add(over);
        frame.revalidate();
        frame.repaint();
    }
}