package shootingspaceship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameOverScreen extends JPanel {
    public GameOverScreen(JFrame frame) {
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        JPanel center = new JPanel();
        center.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 100));

        JLabel label = new JLabel("게임 오버!");
        label.setFont(new Font("맑은 고딕", Font.BOLD, 36));

        JButton restart = new JButton("다시 시작");
        restart.setFont(new Font("맑은 고딕", Font.PLAIN, 20));

        restart.addActionListener(e -> {
            frame.getContentPane().removeAll();
            StartScreen start = new StartScreen(frame);
            start.setBounds(0, 0, 500, 500);
            frame.add(start);
            frame.revalidate();
            frame.repaint();
        });

        center.add(label);
        center.add(restart);

        add(center, BorderLayout.CENTER); // 가운데 정렬
    }
}
