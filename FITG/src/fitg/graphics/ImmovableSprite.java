/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fitg.graphics;

import fitg.graphics.framework.Camera;
import fitg.graphics.framework.Renderable;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

/**
 *
 * @author Cyborg
 */
public class ImmovableSprite extends Renderable {
    private Image _image;
    
    public ImmovableSprite(Image image) {
        _image = image;
    }
    
    @Override
    public void Render(Graphics2D g, Camera c) {
        AffineTransform t = AffineTransform.getTranslateInstance(_x, _y);
        g.drawImage(_image, t, null);
    }
}
