package fitg.graphics.framework;

import fitg.FITG;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;

public class RenderViewport extends JPanel implements ComponentListener, MouseListener, MouseMotionListener, MouseWheelListener {
    private Camera _camera;
    private Timer _redrawTimer;
    private boolean _mousePressedOverObject;
    private int _oldX;
    private int _oldY;
    public List<Renderable> ItsRenderObjects;
    
    public RenderViewport() {
        Rectangle2D.Float viewport = new Rectangle2D.Float(0, 0, this.getWidth(), this.getHeight());
        _camera = new Camera(viewport, new Rectangle2D.Float(0, 0, 4000, 3000));
        final JPanel thisPanel = this;
        _redrawTimer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                thisPanel.validate();
                thisPanel.repaint();
            }
        });
        _redrawTimer.start();
        ItsRenderObjects = new ArrayList();
        
        addComponentListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        
        _mousePressedOverObject = false;
    }
    
    public void InitializeGraphics() {
        Graphics2D g = (Graphics2D)this.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        _camera.SetViewportDimensions(this.getWidth(), this.getHeight());
    }
    
    public Camera GetCamera() {
        return _camera;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        for (Renderable r : ItsRenderObjects) {
            r.Render(g2d, _camera);
        }
    }
    
    @Override
    public void componentHidden(ComponentEvent e) {
        
    }
    
    @Override
    public void componentMoved(ComponentEvent e) {
        
    }
    
    @Override
    public void componentResized(ComponentEvent e) {
        _camera.SetViewportDimensions(this.getWidth(), this.getHeight());
    }
    
    @Override
    public void componentShown(ComponentEvent e) {
        
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        // ignored
    }

    @Override
    public void mousePressed(MouseEvent me) {
        for (Renderable p : ItsRenderObjects) {
            if (p instanceof Interactive) {
                Interactive i = (Interactive) p;
                i.Deselect();
                if (i.Contains(me.getX(), me.getY(), _camera)&& _mousePressedOverObject==false) {
                    _mousePressedOverObject = i.MousePressed(me.getX(), me.getY(), _camera);
                    i.Select(me.getX(), me.getY(), _camera);
                }
            }
        }
        
        _oldX = me.getX();
        _oldY = me.getY();
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        if (_mousePressedOverObject) {
            for (Renderable p : ItsRenderObjects) {
                if (p instanceof Interactive) {
                    Interactive i = (Interactive) p;
                    i.MouseRelease();
                }
            }
        }
        _mousePressedOverObject = false;
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        
    }

    @Override
    public void mouseExited(MouseEvent me) {
        
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        if (!_mousePressedOverObject) {
            _camera.SetX(_camera.GetX() - (me.getX() - _oldX));
            _camera.SetY(_camera.GetY() - (me.getY() - _oldY));
        }
        _oldX = me.getX();
        _oldY = me.getY();
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        for (Renderable p : ItsRenderObjects) {
            if (p instanceof Interactive) {
                Interactive i = (Interactive) p;
                i.Unhover();
            }
        }
        
        for (Renderable p : ItsRenderObjects) {
            if (p instanceof Interactive) {
                Interactive i = (Interactive) p;
                if (i.Contains(me.getX(), me.getY(), _camera))
                    i.Hover(me.getX(), me.getY(), _camera);
            }
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        double scroll = e.getPreciseWheelRotation() * 0.05;
        _camera.Zoom((float)scroll, e.getX(), e.getY());
        FITG.MainGui.slider.setValue((int) (_camera.GetScale() * 100));
    }
}
