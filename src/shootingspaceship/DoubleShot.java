package shootingspaceship;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class DoubleShot extends DragonShot {
	
	private int x, y;
	private List<Shot> shots;


	public DoubleShot(int x, int y)
	{
		super(x, y);
		this.x = x;
		this.y = y;
	}

	@Override
	public List<Shot> fire() {
		List<Shot> shots = new ArrayList<>();
		shots.add(new Shot(x - 7, y) {
			private final Image shotImage = new ImageIcon(getClass().getResource("/shootingspaceship/img_3.png")).getImage();

			@Override
			public void drawShot(Graphics g) {
				if (!alive) return;
				if (shotImage != null) {
					g.drawImage(shotImage, x_pos - 5, y_pos - 5, 10, 10, null);
				} else {
					super.drawShot(g);
				}
			}
		});
		shots.add(new Shot(x + 7, y) {
			private final Image shotImage = new ImageIcon(getClass().getResource("/shootingspaceship/img_3.png")).getImage();

			@Override
			public void drawShot(Graphics g) {
				if (!alive) return;
				if (shotImage != null) {
					g.drawImage(shotImage, x_pos - 5, y_pos - 5, 10, 10, null);
				} else {
					super.drawShot(g);
				}
			}
		});
		return shots;
	}

	@Override
	public void drawShot(Graphics g) {
		if (shots == null) {
			shots = fire(); // 최초 1회만 생성
		}
		for (Shot shot : shots) {
			shot.drawShot(g); // 이미지 그리는 오버라이드된 메서드 호출
		}
	}
}
