package shootingspaceship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameOverScreen extends JPanel {
    private JFrame frame;

    public GameOverScreen(JFrame frame) {
        this.frame = frame;
        setLayout(null);

        JLabel label = new JLabel("게임 오버!", SwingConstants.CENTER);
        label.setFont(new Font("맑은 고딕", Font.BOLD, 36));
        label.setBounds(100, 100, 300, 50);
        add(label);

        JButton retryButton = new JButton("다시하기");
        retryButton.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        retryButton.setBounds(150, 200, 200, 50);
        retryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                StartScreen startScreen = new StartScreen(frame);
                startScreen.setBounds(0, 0, 500, 500);
                frame.add(startScreen);
                frame.revalidate();
                frame.repaint();
            }
        });
        add(retryButton);

        JButton exitButton = new JButton("종료");
        exitButton.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        exitButton.setBounds(150, 270, 200, 50);
        exitButton.addActionListener(e -> System.exit(0));
        add(exitButton);
    }
}