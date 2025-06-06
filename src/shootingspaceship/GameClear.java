package shootingspaceship;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;


public class GameClear extends JPanel {
	public GameClear(JFrame frame) {
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        JPanel center = new JPanel();
        center.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 100));

        JLabel label = new JLabel("게임 클리어!");
        label.setFont(new Font("맑은 고딕", Font.BOLD, 36));

        center.add(label);


        add(center, BorderLayout.CENTER); 
    }

}
