/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fitg;

import fitg.graphics.ButtonLocation;
import fitg.graphics.ArcButton;
import fitg.graphics.ColorDictionary;
import fitg.graphics.framework.Camera;
import fitg.graphics.framework.Interactive;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.*;

/**
 *
 * @author Matt
 */
public class Planet extends Interactive {
    
    public enum PlanetLoyalty {
        Unrest,
        Dissent,
        Neutral,
        Loyal,
        Patriotic
    }
    
    private int planetID;
    private int systemID;
    private int provinceID;
    private String _name;
    private List<Environ> environs;
    private List<Integer> environIDs;
    //Variables that can be initialized to a standard beginning unless set in ScenarioLoader.java
    public int PDBLevel = 0;
    private boolean PDBStatus;  // Flag for PDB status: True = UP, False = Down
    public SIDE controlledby = SIDE.NEUTRAL;
    public PlanetLoyalty loyalty = PlanetLoyalty.Neutral;
    
    
    //GUI specific variables
    private List<ArcButton> _environButtons;
    private ArcButton _planetButton;
    private int _orbital;
    public static final int FirstOrbitalSize = 200;
    public static final int SecondOrbitalSize = 325;
    public static final int ThirdOrbitalSize = 450;
    
    private static Font _planetFont = new Font("Dialog", Font.ITALIC, 14);
    
    public Planet(int provinceID, int systemID, int planetID, String tmpName, int orbital)
    {
        environs = new ArrayList();
        this.planetID = planetID;
        this.systemID = systemID;
        this.provinceID = provinceID;
        this._name = tmpName;
        environIDs = new ArrayList();
        this.PDBStatus = true;
        _orbital = orbital;
        _environButtons = new ArrayList();
    }
    
    public void AddEnviron(Environ environ)
    {
        environs.add(environ);
    }
    
    public void AddEnvironID(int environID)
    {
        environIDs.add(environID);
    }
    
    public int GetPlanetID()
    {
        return this.planetID;
    }
    public String GetPlanetName()
    {
            return _name;
    }
    
    public Environ GetEnvironObj(int envID)
    {
        return environs.get(envID);
    }
    
    public List<Environ> GetEnvirons()
    {
        return environs;
    }
    
    public List<Integer> GetEnvironIDs()
    {
        return environIDs;
    }
    
    // Get PDB UP status. Returns true when up, false when down
    public boolean GetPDB_IS_UP() { return this.PDBStatus; }
    // Set PDB status to UP
    public void SetPDB_UP() { this.PDBStatus = true; }
    // Set PDB status to DOWN
    public void SetPDB_DOWN() { this.PDBStatus = false; }
    
    
    
    
    //GUI specific methods
    
    @Override
    public void Render(Graphics2D g, Camera c) {
        for (ArcButton b : _environButtons) {
            b.Render(g, c);
        }
        
        if (c.GetScale() > 0.4f) {
            g.setColor(ColorDictionary.PlanetFont);
            g.setFont(_planetFont.deriveFont(_planetFont.getSize2D() * c.GetScale()));
            float nameY = _planetButton.GetY() + 15 + 10 * c.GetScale() - c.GetY();
            float nameX = _planetButton.GetX() + _planetButton.GetSize().width * 0.5f;
            nameX -= (float)g.getFontMetrics().getStringBounds(_name, g).getWidth() * 0.5f;
            nameX -= c.GetX();
            g.drawString(_name, nameX * c.GetScale(), nameY * c.GetScale());
        }
    }
    
    public void BuildPlanet() {
        int diameter = GetOrbitalSize();
        Dimension planetSize = new Dimension(diameter, diameter);
        _planetButton = new ArcButton(planetSize, ButtonLocation.PLANET, null, ButtonLocation.PLANET.getStartDegree());
        _planetButton.SetPlanet(this);
        _environButtons.add(_planetButton);
        _environButtons.add(new ArcButton(planetSize, ButtonLocation.ORBIT, null, ButtonLocation.ORBIT.getStartDegree()));
        _environButtons.add(new ArcButton(planetSize, ButtonLocation.ENV_ONE, environs.get(0), ButtonLocation.ENV_ONE.getStartDegree()));
        environs.get(0).SetEnvironButton(_environButtons.get(0));
        float angle = 0;
        if (environs.size() > 1){
            angle += ButtonLocation.ENV_ONE.getStartDegree() + ArcButton.MinArcLength * environs.get(0).GetEnvironSize();
            _environButtons.add(new ArcButton(planetSize, ButtonLocation.ENV_TWO, environs.get(1), angle));
            environs.get(1).SetEnvironButton(_environButtons.get(1));
        }
        if (environs.size() > 2){
            angle += ArcButton.MinArcLength * environs.get(1).GetEnvironSize();
            _environButtons.add(new ArcButton(planetSize, ButtonLocation.ENV_THREE, environs.get(2), angle));
            environs.get(2).SetEnvironButton(_environButtons.get(2));
        }
        
    }
    
    @Override
    public void SetPosition(float x, float y) {
        super.SetPosition(x, y);
        for (ArcButton b : _environButtons) {
            b.SetPosition(x, y);
        }
    }
    
    private int GetOrbitalSize() {
        if (_orbital == 1)
            return FirstOrbitalSize;
        if (_orbital == 2)
            return SecondOrbitalSize;
        return ThirdOrbitalSize;
    }

    @Override
    public boolean Contains(float x, float y, Camera c) {
        float os = GetOrbitalSize() * c.GetScale();
        float relX = _x - c.GetX(), relY = _y - c.GetY();
        relX *= c.GetScale();
        relY *= c.GetScale();
        return x > relX && y > relY && x < relX + os && y < relY + os;
    }

    @Override
    public void Select(float x, float y, Camera c) {
        for (ArcButton b : _environButtons) {
            if (b.Contains(x, y, c))
                b.Select(x, y, c);
        }
    }

    @Override
    public void Hover(float x, float y, Camera c) {
        for (ArcButton b : _environButtons) {
            if (b.Contains(x, y, c))
                b.Hover(x, y, c);
        }
    }
    
    @Override
    public boolean MousePressed(float x, float y, Camera c) {
        boolean environPressed = false;
        for (ArcButton b : _environButtons) {
            if (b.Contains(x, y, c)) {
                b.MousePressed(x, y, c);
                environPressed = true;
            }
        }
        return environPressed;
    }
    
    @Override
    public void Unhover() {
        super.Unhover();
        for (ArcButton b : _environButtons) {
            b.Unhover();
        }
    }
    
    @Override
    public void Deselect() {
        super.Deselect();
        for (ArcButton b : _environButtons) {
            b.Deselect();
        }
    }
    
    @Override
    public void MouseRelease() {
        super.MouseRelease();
        for (ArcButton b : _environButtons) {
            b.MouseRelease();
        }
    }
}
