package shootingspaceship;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameClear extends JPanel {
	public GameClear(JFrame frame) {
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setOpaque(false);
        
        JLabel label = new JLabel("게임 클리어!");
        label.setFont(new Font("맑은 고딕", Font.BOLD, 36));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        center.add(Box.createVerticalStrut(100));
        center.add(label);
        center.add(Box.createVerticalStrut(50));


        JButton exitButton = new JButton("종료");
        exitButton.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(e -> System.exit(0));
        center.add(exitButton);

        add(center, BorderLayout.CENTER);
    }

}