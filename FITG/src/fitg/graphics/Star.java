package fitg.graphics;

import fitg.graphics.framework.Camera;
import fitg.graphics.framework.Interactive;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Star extends Interactive {
    private Ellipse2D.Float _star;
    private float _radius;
    private String _name;
    private Font _starFont = new Font("Dialog", Font.ITALIC, 14);
    
    protected final Color selectedColor = new Color(58, 255, 0, 255);
    protected final Color hoverColor = new Color(255, 244, 110, 255);
    protected final Color defaultColor = new Color(255, 238, 34, 255);
    protected final Color clickColor = hoverColor.brighter();
    protected final Color borderColor = Color.red;
    
    public Star() {
        _radius = 50;
        _star = new Ellipse2D.Float();
        _star.x = 0;
        _star.y = 0;
        _star.width = _radius;
        _star.height = _radius;
        _name = "Default";
    }
    
    @Override
    public void SetPosition(float x, float y) {
        super.SetPosition(x, y);
        _star.x = x;
        _star.y = y;
    }
    
    public void SetRadius(float radius) {
        _radius = radius;
        _star.width = _radius;
        _star.height = _radius;
    }
    
    public void SetName(String name) {
        _name = name;
    }
    
    @Override
    public void Render(Graphics2D g, Camera c) {
        Color starColor = MousePressed ? clickColor :
                (Hovered ? hoverColor :
                (Selected ? selectedColor : defaultColor));
        
        // draw the star circle
        g.setColor(starColor);
        Ellipse2D.Float drawStar = GetDrawStar(c);
        g.fill(drawStar);
        
        g.setColor(borderColor);
        g.draw(drawStar);
        
        // draw the star name
        if (c.GetScale() > 0.4f) {
            g.setColor(Color.black);
            g.setFont(_starFont.deriveFont(_starFont.getSize2D() * c.GetScale()));
            float nameY = drawStar.y + drawStar.height / 2 + _starFont.getSize2D() / 3;
            float nameX = drawStar.x + drawStar.width / 2 - (float)g.getFontMetrics().getStringBounds(_name, g).getWidth() / 2;
            g.drawString(_name, nameX, nameY);
        }
    }

    @Override
    public boolean Contains(float x, float y, Camera c) {
        return GetDrawStar(c).contains(x, y);
    }
    
    @Override
    public boolean MousePressed(float x, float y, Camera c) {
        super.MousePressed(x, y, c);
        return true;
    }
    
    private Ellipse2D.Float GetDrawStar(Camera c) {
        Ellipse2D.Float bounds = (Ellipse2D.Float)_star.clone();
        bounds.x -= c.GetX();
        bounds.y -= c.GetY();
        bounds.x *= c.GetScale();
        bounds.y *= c.GetScale();
        bounds.width *= c.GetScale();
        bounds.height *= c.GetScale();
        return bounds;
    }
}
