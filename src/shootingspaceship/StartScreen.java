package shootingspaceship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartScreen extends JPanel {
    private JFrame frame;

    public StartScreen(JFrame frame) {
        this.frame = frame;
        setLayout(new GridLayout(3, 1));

        JLabel title = new JLabel("슈팅 게임", SwingConstants.CENTER);
        title.setFont(new Font("맑은 고딕", Font.BOLD, 36));
        add(title);

        JButton startButton = new JButton("게임 시작");
        startButton.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        JButton howToButton = new JButton("게임 방법");
        howToButton.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        howToButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showInstructions();
            }
        });

        add(startButton);
        add(howToButton);
    }

    private void startGame() {
        frame.getContentPane().removeAll();

        LevelThree game = new LevelThree(frame);
        game.setBounds(0, 0, 500, 500);
        frame.add(game);
        frame.revalidate();
        frame.repaint();

        game.requestFocusInWindow();
        game.start();
    }

    private void showInstructions() {
        JOptionPane.showMessageDialog(this,
                "← / → : 이동\n↑ : 공격\nP : 일시정지",
                "게임 방법",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
