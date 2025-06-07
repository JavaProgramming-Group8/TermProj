package shootingspaceship;

import java.awt.*;
import javax.swing.ImageIcon;

public class NerfItem extends Item {
    private Image nerfImage;

    public NerfItem(int x, int y) {
        super(x, y);
        java.net.URL imgUrl = getClass().getResource("/shootingspaceship/nerf.png");
        if (imgUrl != null) {
            nerfImage = new ImageIcon(imgUrl).getImage();
        }
    }
    
    @Override
    public void applyTo(Object obj) {
        if(obj instanceof NerfEffect) 
            ((NerfEffect)obj).startNerfEffect(); 
    }
    
    @Override
    public void draw(Graphics g) {
        if (nerfImage != null) {
            g.drawImage(nerfImage, x_pos, y_pos, 15, 15, null);
        } else {
            g.setColor(Color.GRAY);
            g.fillRect(x_pos, y_pos, 15, 15);
        }
    }
}
