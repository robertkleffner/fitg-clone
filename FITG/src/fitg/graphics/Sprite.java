package fitg.graphics;

import fitg.graphics.framework.Camera;
import fitg.graphics.framework.Renderable;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

public class Sprite extends Renderable {
    private Image _image;
    
    public Sprite(Image image) {
        _image = image;
    }
    
    @Override
    public void Render(Graphics2D g, Camera c) {
        AffineTransform t = AffineTransform.getTranslateInstance(_x - c.GetX(), _y - c.GetY());
        g.drawImage(_image, t, null);
    }
}
