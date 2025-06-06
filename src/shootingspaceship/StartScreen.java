package shootingspaceship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartScreen extends JPanel {
    private JFrame frame;

    public StartScreen(JFrame frame) {
        this.frame = frame;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.BLACK);

        JLabel title = new JLabel("슈팅 게임", SwingConstants.CENTER);
        title.setFont(new Font("맑은 고딕", Font.BOLD, 36));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(Color.WHITE);
        add(Box.createVerticalStrut(60));
        add(title);
        add(Box.createVerticalStrut(40));

        Dimension btnSize = new Dimension(200, 45);

        JButton startButton = new JButton("게임 시작");
        startButton.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        startButton.setMaximumSize(btnSize);
        startButton.setPreferredSize(btnSize);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        JButton howToButton = new JButton("게임 방법");
        howToButton.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        howToButton.setMaximumSize(btnSize);
        howToButton.setPreferredSize(btnSize);
        howToButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        howToButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showInstructions();
            }
        });

        add(startButton);
        add(Box.createVerticalStrut(20));
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
                "■ 조작법\n" +
                "← / → : 플레이어 이동\n" +
                "↓ : 총알 발사\n" +
                "P : 일시정지/재개\n\n" +
                "■ 게임 목표\n" +
                "- 적을 처치하고 점수를 획득하세요.\n" +
                "- 점수가 300점이 넘으면 보스 스테이지로 진입합니다.\n" +
                "- 아이템을 먹으면 다양한 효과가 적용됩니다.\n\n" +
                "■ 팁\n" +
                "- 적의 공격과 아이템을 잘 피하세요!\n" +
                "- 보스를 처치하면 게임이 클리어됩니다.",
                "게임 방법",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
