package fitg.graphics;

import fitg.Environ;
import fitg.EnvironMenu;
import fitg.Board;
import fitg.FITG;
import fitg.GUI;
import fitg.Game;
import fitg.MissionMenu2;
import fitg.Movement;
import fitg.Phase;
import fitg.Planet;
import fitg.Province;
import fitg.SIDE;
import fitg.SearchMenu;
import fitg.StarSystem;
import fitg.UnitSelectionMenu;
import fitg.graphics.framework.Camera;
import fitg.graphics.framework.Interactive;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
// Added for PDB square
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class ArcButton extends Interactive {

    public static final float MinArcLength = 20;
    protected final Color selectedColor = new Color(100, 150, 255, 135);
    protected final Color hoverColor = new Color(230, 230, 230, 135);
    protected final Color defaultColor = new Color(65, 65, 65, 135);
    protected final Color clickColor = hoverColor.brighter();
    protected final Color borderColor = Color.GREEN;
    protected final float arcWidth = 40;
    protected final float unitCountCircleRadius = 12;
    protected Shape _shape;
    private final ButtonLocation _location;
    private Dimension _size;
    private int _xClick;
    private int _yClick;
    private Environ _environ;
    private Planet _planet;
    private float _startAngle;
    private int _moveOrigin;
    private int _pulseCounter;  // rgb color for pulse display
    private int _pulseOffset;   // pulse offset value
    
    private Font _environFont = new Font("Dialog", Font.ITALIC, 14);

    public ArcButton() {
        _location = ButtonLocation.PLANET;
        _size = new Dimension(100, 100);
    }

    public ArcButton(Dimension d, ButtonLocation bl, Environ e, float startAngle) {
        super();
        _size = d;
        _location = bl;
        GetArcShape(0, 0, 1);
        _environ = e;
        _startAngle = startAngle;
        _pulseCounter = 0;
        _pulseOffset = 1;
    }

    public void SetDiameter(int d) {
        _size = new Dimension(d, d);
    }

    public Dimension GetSize() {
        return _size;
    }

    public void SetPlanet(Planet p) {
        _planet = p;
    }

    public ButtonLocation GetButtonLocation() {
        return _location;
    }

    @Override
    public boolean Contains(float x, float y, Camera c) {
        return _shape.contains(x, y);
    }

    @Override
    public void Render(Graphics2D g, Camera camera) {
        GetArcShape(camera.GetX(), camera.GetY(), camera.GetScale());

        Graphics2D g2 = (Graphics2D) g;

        Color envColor = GetArcFillColor();
        g2.setColor(envColor);
        g2.fill(_shape);

        g2.setColor(borderColor);
        g2.draw(_shape);
        
        if (_location != ButtonLocation.PLANET && _location != ButtonLocation.ORBIT)
            DrawUnitCounts(g, camera);

        if (_location == ButtonLocation.PLANET)
            DrawPDBSquare(g, camera);
    }

    @Override
    public boolean MousePressed(float x, float y, Camera c) {
        MousePressed = true;
        _xClick = Math.round(x);
        _yClick = Math.round(y);
        return true;
    }

//  Old functionality that used the EnvironMenu whenever an environ was clicked on.     
//    @Override
//    public void MouseRelease() {
//        if (IsEnvironClicked() && !Board.instance().inMovement) {
//            ShowEnvironMenu();
//        } else if (IsEnvironClicked() && Board.instance().inMovement) {
//            ShowUnitMovement();
//            FITG.MainGui.NotMovementUpdateSidePanel();
//        } else if (IsPlanetClicked()) {
//            ShowPlanetMenu();
//        } else if (MousePressed == true) {
//            MousePressed = false;
//        }
//    }
    
//  Game Phase is checked here when an environ is clicked so that only relevant frames
//  are available to the player, i.e. mission frame, movement frame and search frame.
//  TODO: Add environ info somewhere when environ is clicked, some of the info was in the previous
//  incarnation of EnvironMenu.   
    @Override
    public void MouseRelease() {
        if (IsEnvironClicked() && (Game.instance().turn.phase == Phase.Movement)) {
            if (!Board.instance().inMovement) {
                UnitSelectionMenu unitSelectionMenu = new UnitSelectionMenu(
                        null, _environ.GetUnitsForSide(Game.instance().turn.GetPhasingSide()),
                        _environ, _xClick, _yClick);
                JDialog menuFrame = unitSelectionMenu.getFrame();
                menuFrame.setAlwaysOnTop(true);
            } else if (Board.instance().inMovement) {
                ShowUnitMovement();
                FITG.MainGui.NotMovementUpdateSidePanel();
                FITG.MainGui.GetStageLabel().setText("<html><center>" + Game.instance().turn.phase.toString() + " Phase<p>Select an environ with units to move</center></html>");
            }
            MousePressed = false;
        }
        if (IsEnvironClicked() && (Game.instance().turn.phase == Phase.Reaction)) {
            if (!Board.instance().inMovement) {
                UnitSelectionMenu unitSelectionMenu = new UnitSelectionMenu(
                        null, _environ.GetUnitsForSide(Game.instance().turn.GetNonPhasingSide()),
                        _environ, _xClick, _yClick);
                JDialog menuFrame = unitSelectionMenu.getFrame();
                menuFrame.setAlwaysOnTop(true);
            } else if (Board.instance().inMovement) {
                ShowUnitMovement();
                FITG.MainGui.NotMovementUpdateSidePanel();
            }
            MousePressed = false;
        } 
        else if (IsEnvironClicked() && (Game.instance().turn.phase == Phase.Search)) {
            //Open Search Menu
            //THIS NEEDS CHANGED TO 'FALSE'. left at 'true' for testing
//            if (_environ.IsSearchable() == false){
//                //Display error if no units/characters present to search with
//                //TODO: Check that characters/units have not already performed search
//                JOptionPane.showMessageDialog(null, "<html><center>Can't search this Environ!</center></html>");
//            }else{         
                SearchMenu searchMenu = new SearchMenu(null, _environ, _xClick, _yClick);
                JDialog menuFrame = searchMenu.getFrame();
                menuFrame.setAlwaysOnTop(true);
//            }
            MousePressed = false;
        } 
        else if (IsEnvironClicked() && (Game.instance().turn.phase == Phase.Missions)) {
            //Mission.Initialize();
            if (_environ.GetMissionCapableStack() == null) {
                MousePressed = false;
            }
            else {
                MissionMenu2 missionMenu = new MissionMenu2(_environ, null);
                JDialog menuFrame = missionMenu.getFrame();
                menuFrame.setAlwaysOnTop(true);
                MousePressed = false;
            }
        } 
        else if (IsPlanetClicked()) {
            ShowPlanetMenu();
            MousePressed = false;
        } 
        else MousePressed = false;
    }
    
    private boolean IsEnvironClicked() {
        return MousePressed == true && ButtonLocation.PLANET != _location && ButtonLocation.ORBIT != _location;
    }
    
    private boolean IsPlanetClicked() {
        return MousePressed == true && ButtonLocation.PLANET == _location;
    }
    
    private void ShowEnvironMenu() {
        MousePressed = false;
        Province tmpProv = Board.instance().GetProvinceObj(_environ.GetProvinceID());
        StarSystem tmpSys = tmpProv.GetSystemObj(_environ.GetSystemID());
        Planet tmpPlanet = tmpSys.GetPlanetObj(_environ.GetPlanetID());
        EnvironMenu environMenu = new EnvironMenu(null, _xClick, _yClick, 
                String.format("Planet: %s, Environ: %d, EnvironType: %s, EnvironSize: %d", 
                tmpPlanet.GetPlanetName(), _environ.GetEnvironID(),
                String.valueOf(_environ.GetEnvironType()),
                _environ.GetEnvironSize()), _environ);
    }
    
    private void ShowPlanetMenu() {
        PlanetMenu planetMenu = new PlanetMenu(null, _xClick, _yClick, _planet);
        MousePressed = false;
    }
    
    private void ShowUnitMovement() {
        //Board.instance().MoveStack(_environ.GetEnvironID());
        Movement move = new Movement();
        System.out.println("Stacks on dest environ before: "+_environ.GetListOfStacksInEnviron());
        move.Move(Board.instance().selectedEntity, Board.instance().selectedStackID, 
                  _environ.GetEnvironID());
        System.out.println("Stacks on dest environ After: "+_environ.GetListOfStacksInEnviron());
        System.out.println();
        MousePressed = false;
        //JOptionPane.showMessageDialog(null, "Units moved to environ: " + _environ.GetEnvironID());
        Board.instance().inMovement = false;
        Board.instance().selectedEntity.clear();
        Board.instance().selectedStackID.clear();
        FITG.MainGui.NotMovementUpdateSidePanel();
        FITG.MainGui.getPort().revalidate();
    }
    
    private Color GetArcFillColor() {
//        if (MousePressed)
//            return clickColor;
//        if (Hovered)
//            return hoverColor;
//        if (Selected)
//            return selectedColor;
        
        if (_location == ButtonLocation.PLANET) {
            return (_planet.loyalty == Planet.PlanetLoyalty.Patriotic) ? ColorDictionary.PlanetPatriotic :
                    (_planet.loyalty == Planet.PlanetLoyalty.Loyal) ? ColorDictionary.PlanetLoyal :
                    (_planet.loyalty == Planet.PlanetLoyalty.Neutral) ? ColorDictionary.PlanetNeutral :
                    (_planet.loyalty == Planet.PlanetLoyalty.Dissent) ? ColorDictionary.PlanetDissent :
                    ColorDictionary.PlanetUnrest;
        } else if (_location != ButtonLocation.ORBIT) {
            return (_environ.GetEnvironType() == Environ.EnvironType.Air) ? ColorDictionary.EnvironAir :
                    (_environ.GetEnvironType() == Environ.EnvironType.Fire) ? ColorDictionary.EnvironFire :
                    (_environ.GetEnvironType() == Environ.EnvironType.Liquid) ? ColorDictionary.EnvironLiquid :
                    (_environ.GetEnvironType() == Environ.EnvironType.Sub) ? ColorDictionary.EnvironSubterranean :
                    (_environ.GetEnvironType() == Environ.EnvironType.Urban) ? ColorDictionary.EnvironUrban :
                    ColorDictionary.EnvironWild;
        }
        
        return defaultColor;
    }

    private void GetArcShape(float camX, float camY, float scale) {
        Shape inner = new Ellipse2D.Float(arcWidth, arcWidth,
                _size.width - 2 * arcWidth, _size.width - 2 * arcWidth);
        if (ButtonLocation.CENTER == _location) {
            _shape = inner;
        } else if (ButtonLocation.PLANET == _location) {
            Shape outer = new Arc2D.Float(
                    0, 0, _size.width, _size.height,
                    _startAngle, 40f, Arc2D.PIE);
            Area area = new Area(outer);
            area.subtract(new Area(inner));
            _shape = area;
        } else if (ButtonLocation.ORBIT == _location) {
            Shape outer = new Arc2D.Float(
                    0, 0, _size.width, _size.height,
                    _startAngle, 80f, Arc2D.PIE);
            Area area = new Area(outer);
            area.subtract(new Area(inner));
            _shape = area;
        } else if (_environ == null) {
            Shape outer = new Arc2D.Float(
                    0, 0, _size.width, _size.height,
                    _startAngle, MinArcLength, Arc2D.PIE);
            Area area = new Area(outer);
            area.subtract(new Area(inner));
            _shape = area;
        } else {
            Shape outer = new Arc2D.Float(
                    0, 0, _size.width, _size.height,
                    _startAngle, MinArcLength * _environ.GetEnvironSize(), Arc2D.PIE);
            Area area = new Area(outer);
            area.subtract(new Area(inner));
            _shape = area;
        }

        // offset according to camera
        AffineTransform at = new AffineTransform();
        at.scale(scale, scale);
        at.translate(_x - camX, _y - camY);
        _shape = at.createTransformedShape(_shape);
    }
    
    private void DrawUnitCounts(Graphics2D g, Camera c) {
        int imperialCount = _environ.GetUnitsForSide(SIDE.IMPERIAL).size();
        int rebelCount = _environ.GetUnitsForSide(SIDE.REBEL).size();
        int level = 0;
        
        if (imperialCount > 0) {
            DrawUnitCountCircle(g, c, imperialCount, level, SIDE.IMPERIAL, _environ.detectedImperials);
            level++;
        }
        
        if (rebelCount > 0) {
            DrawUnitCountCircle(g, c, rebelCount, level, SIDE.REBEL, _environ.detectedRebels);
        }
    }
    
    private void DrawUnitCountCircle(Graphics2D g, Camera c, int units, int position, SIDE s, boolean Detected) {
        float center = _size.width * 0.5f;
        float radius = center - arcWidth * 0.5f;
        double angle = Math.toRadians(-_startAngle - MinArcLength * _environ.GetEnvironSize() + GetSpacing(position));
        float x = radius * (float)Math.cos(angle) + center;
        float y = radius * (float)Math.sin(angle) + center;
        x += _x - c.GetX() - unitCountCircleRadius;
        y += _y - c.GetY() - unitCountCircleRadius;
        x *= c.GetScale();
        y *= c.GetScale();
        float diameter = unitCountCircleRadius * 2 * c.GetScale();
        Ellipse2D.Float unitCircle = new Ellipse2D.Float(x, y, diameter, diameter);
        
        Color fill = (s == SIDE.IMPERIAL) ? ColorDictionary.ImperialUnitCircle : ColorDictionary.RebelUnitCircle;
        
        // If a stack is detected - make the fill color pulse
        if (Detected)
        {
            // Offset red for rebels, blue for imperials
            if (s == SIDE.REBEL)
                fill = new Color(_pulseCounter, fill.getGreen(), fill.getBlue(), fill.getAlpha());
            else
                fill = new Color(fill.getRed(), fill.getGreen(), _pulseCounter, fill.getAlpha());
            
            // Change pulsecounter value by step. Bouncing back and forth within  the range to create pulse effect
            if (_pulseCounter <= 100)
                _pulseOffset = 4;
            else if (_pulseCounter >= 250)
                _pulseOffset = -4;
            
            // Apply offset
            _pulseCounter += _pulseOffset;
        }
        
        g.setColor(fill);
        g.fill(unitCircle);
        
        g.setColor(ColorDictionary.UnitCircleBorder);
        g.draw(unitCircle);
        
        if (c.GetScale() > 0.4f) {
            g.setColor(ColorDictionary.UnitCircleFont);
            g.setFont(_environFont.deriveFont(_environFont.getSize2D() * c.GetScale()));
            float nameY = unitCircle.y + unitCircle.height / 2 + _environFont.getSize2D() * c.GetScale() / 3;
            float nameX = unitCircle.x + unitCircle.width / 2 - (float)g.getFontMetrics().getStringBounds(Integer.toString(units), g).getWidth() / 2;
            g.drawString(Integer.toString(units), nameX, nameY);
        }
    }
    // PDB Square - Copied from the DrawUnitCountCirle. Only Drawn with Planet ArcButton object
    // Square is positioned to appear as if it is in the orbit box
    // The PDB level will be displayed in the square
    // When the PDB is DOWN, the square will get crosses drawn thru the corners to give a visual indicator.
    private void DrawPDBSquare(Graphics2D g, Camera c) {
        float center = _size.width * 0.5f;
        float radius = center - arcWidth * 0.5f;
        double angle = Math.toRadians(-_startAngle + 65);
        float x = radius * (float)Math.cos(angle) + center;
        float y = radius * (float)Math.sin(angle) + center;
        float pdbSize = 12;
        x += _x - c.GetX() - pdbSize;
        y += _y - c.GetY() - pdbSize;
        x *= c.GetScale();
        y *= c.GetScale();
        float side = unitCountCircleRadius * 2 * c.GetScale();
        Rectangle2D.Float pdbSquare = new Rectangle2D.Float(x, y, side, side);

        Color fill;
        
        // Set PDB fill color to be the same as the SIDE color
        if (_planet.controlledby == SIDE.IMPERIAL)
            fill = ColorDictionary.ImperialUnitCircle;
        else if (_planet.controlledby == SIDE.REBEL)
            fill = ColorDictionary.RebelUnitCircle;
        else
            fill = ColorDictionary.PlanetNeutral;
        
        g.setColor(fill);
        g.fill(pdbSquare);
        
        g.setColor(ColorDictionary.PDBBorder);
        g.draw(pdbSquare);
        
        // If the PDB is DOWN, draw crosses thru the square
        if (_planet.GetPDB_IS_UP() == false)
        {
            Line2D.Float crossOut1 = new Line2D.Float(pdbSquare.x, pdbSquare.y, pdbSquare.x + side, pdbSquare.y + side);
            Line2D.Float crossOut2 = new Line2D.Float(pdbSquare.x, pdbSquare.y + side, pdbSquare.x + side, pdbSquare.y);
            g.setColor(ColorDictionary.PDBBorder);
            g.draw(crossOut1);
            g.draw(crossOut2);
        }

        
        if (c.GetScale() > 0.4f) {
            g.setColor(ColorDictionary.UnitCircleFont);
            g.setFont(_environFont.deriveFont(_environFont.getSize2D() * c.GetScale()));
            float nameY = pdbSquare.y + pdbSquare.height / 2 + _environFont.getSize2D() * c.GetScale() / 3;
            float nameX = pdbSquare.x + pdbSquare.width / 2 - (float)g.getFontMetrics().getStringBounds(Integer.toString(_planet.PDBLevel), g).getWidth() / 2;
            g.drawString(Integer.toString(_planet.PDBLevel), nameX, nameY);
        }
    }
    private float GetSpacing(int position) {
        float resetAngle = (_size.width == Planet.FirstOrbitalSize) ? 20 :
                (_size.width == Planet.SecondOrbitalSize) ? 12 : 8;
        float spacingAngle = (_size.width == Planet.FirstOrbitalSize) ? 25 :
                (_size.width == Planet.SecondOrbitalSize) ? 15 : 11;
        return resetAngle + (position * spacingAngle);
    }
}
