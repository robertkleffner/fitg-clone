package fitg.graphics.framework;

import java.awt.geom.Rectangle2D;

public class Camera {
    private final float MaxScale = 1.0f;
    private final float MinScale = 0.27f;
    private Rectangle2D.Float _viewport;
    private Rectangle2D.Float _currentBounds;
    private Rectangle2D.Float _maxBounds;
    private float _scale;
    
    public Camera(Rectangle2D.Float viewport, Rectangle2D.Float bounds) {
        _viewport = viewport;
        _currentBounds = bounds;
        _maxBounds = (Rectangle2D.Float)bounds.clone();
        _scale = 1.0f;
    }
    
    public void SetX(float x) {
        _viewport.x = x;
        if (_viewport.x < _currentBounds.x)
            _viewport.x = _currentBounds.x;
        if (_viewport.x * _scale + _viewport.width > _currentBounds.width)
            _viewport.x -= (_viewport.x * _scale + _viewport.width) - _currentBounds.width;
    }
    
    public void SetY(float y) {
        _viewport.y = y;
        if (_viewport.y < _currentBounds.y)
            _viewport.y = _currentBounds.y;
        if (_viewport.y * _scale + _viewport.height > _currentBounds.height)
            _viewport.y -= (_viewport.y * _scale + _viewport.height) - _currentBounds.height;
    }
    
    public void SetScale(float scale) {
        _scale = scale;
        if (_scale > MaxScale)
            _scale = MaxScale;
        if (_scale < MinScale)
            _scale = MinScale;
        _currentBounds.width = _maxBounds.width * _scale;
        _currentBounds.height = _maxBounds.height * _scale;
    }
    
    public void Zoom(float zoomAmount, float mouseX, float mouseY) {
        float oldScale = GetScale();
        SetScale(GetScale() + zoomAmount);
        SetX((GetX() + mouseX/oldScale) - (mouseX/GetScale()));
        SetY((GetY() + mouseY/oldScale) - (mouseY/GetScale()));
    }
    
    public float GetX() { return _viewport.x; }
    public float GetY() { return _viewport.y; }
    public float GetScale() { return _scale; }
    
    public Rectangle2D.Float GetBounds() {
        return _currentBounds;
    }
    
    public void SetBounds(Rectangle2D.Float bounds) {
        _currentBounds = bounds;
    }
    
    public void SetViewportDimensions(float width, float height) {
        _viewport.width = width;
        _viewport.height = height;
    }
}
