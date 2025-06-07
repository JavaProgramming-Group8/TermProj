package shootingspaceship;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class NormalShot extends DragonShot {
	
	private int x, y;
	private List<Shot> shots;

	public NormalShot(int x, int y) {
		super(x, y);
		this.x = x;
		this.y = y;
	}

	@Override
	public List<Shot> fire() {
		List<Shot> shots = new ArrayList<>();
		shots.add(new Shot(x, y) {
			private final Image shotImage = new ImageIcon(getClass().getResource("/shootingspaceship/img_3.png")).getImage();

			@Override
			public void drawShot(Graphics g) {
				if (!alive) return;
				if (shotImage != null) {
					g.drawImage(shotImage, x_pos - 5, y_pos - 5, 10, 10, null); // 이미지 중심 위치 보정
				} else {
					super.drawShot(g); // 이미지 없으면 기본 원 그리기
				}
			}
		});
		return shots;
	}

	@Override
	public void drawShot(Graphics g) {
		if (shots == null) {
			shots = fire(); // 캐싱: 매번 새로 생성 안 함
		}
		for (Shot shot : shots) {
			shot.drawShot(g); // 이게 위에서 오버라이드한 이미지 그리기 로직 호출
		}
	}
}
