package fitg.graphics.framework;

public abstract class Interactive extends Renderable {
    protected boolean Selected;
    protected boolean Hovered;
    protected boolean MousePressed;
    
    public Interactive() {
        super();
        Selected = false;
        Hovered = false;
        MousePressed = false;
    }
    
    public void Deselect() { Selected = false; }
    public void Unhover() { Hovered = false; }
    public void MouseRelease() { MousePressed = false; }
    public boolean MousePressed(float x, float y, Camera c) { MousePressed = true; return false; }
    public void Select(float x, float y, Camera c) { Selected = true; }
    public void Hover(float x, float y, Camera c) { Hovered = true; }
    
    public abstract boolean Contains(float x, float y, Camera c);
}
