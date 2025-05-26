package shootingspaceship;

import javax.swing.*;

public class MainLauncher {
    public static void main(String[] args) {
        JFrame frame = new JFrame("슈팅 게임");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        StartScreen startScreen = new StartScreen(frame);
        startScreen.setBounds(0, 0, 500, 500);
        frame.add(startScreen);

        frame.setVisible(true);
    }
}
