package shootingspaceship;

import java.awt.*;
import javax.swing.ImageIcon;

public class NerfItem extends Item {
    protected Image nerfImage = new ImageIcon(getClass().getResource("/shootingspaceship/nerf.png")).getImage();;

    public NerfItem(int x, int y) {
        super(x, y);
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
