package fitg.graphics;

import fitg.Entity;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

public class UnitToggleButton extends JToggleButton
{
    private Entity entity;
    
    public void UnitToggleButton(ImageIcon image, Entity e)
    {
        entity = e;
    }
}
