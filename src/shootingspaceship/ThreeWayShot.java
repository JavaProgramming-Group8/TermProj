package shootingspaceship;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ThreeWayShot extends DragonShot {
	
	private int x, y;
	private List<Shot> shots;

	
	public ThreeWayShot(int x, int y)
	{
		super(x, y);
		this.x = x;
		this.y = y;
	}

	@Override
	public List<Shot> fire() {
		List<Shot> shots = new ArrayList<>();

		shots.add(new Shot(x - 15, y) {
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

		shots.add(new Shot(x, y) {
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

		shots.add(new Shot(x + 15, y) {
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
			shots = fire(); // 한 번만 생성
		}
		for (Shot shot : shots) {
			shot.drawShot(g); // 이미지가 그려지는 오버라이딩 메서드 호출됨
		}
	}
}
