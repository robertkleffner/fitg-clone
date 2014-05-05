package fitg.graphics.framework;

import java.awt.Graphics2D;

public abstract class Renderable {
    protected int _layer;
    protected float _x;
    protected float _y;
    
    public Renderable() {
        _x = 0;
        _y = 0;
    }
    
    public abstract void Render(Graphics2D graphics, Camera camera);
    
    public void SetLayer(int layer) {
        _layer = layer;
    }
    
    public void SetPosition(float x, float y) {
        _x = x;
        _y = y;
    }
    
    public float GetX() { return _x; }
    public float GetY() { return _y; }
}
