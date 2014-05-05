/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fitg;
import java.util.*;

/**
 *
 * @author Matt
 */
public class StarSystem {
    private int SystemID;
    private int ProvinceID;
    private String Name;
    private float x, y;
    private List<Planet> Planets;
    
    public StarSystem (int provinceID, int systemID, String tmpName, float x, float y)
    {
        this.ProvinceID = provinceID;
        this.SystemID = systemID;
        this.Name = tmpName;
        this.x = x;
        this.y = y;
        
        Planets = new ArrayList();
    }

    public void AddPlanet(Planet planet)
    {
        Planets.add(planet);
    }
    
    public int GetSystemID()
    {
        return this.SystemID;
    }
    
    public String GetSystemName()
    {
        return Name;
    }
    
    public Planet GetPlanetByID(int planetID)
    {
        for (Planet p : Planets)
        {
            if (p.GetPlanetID() == planetID)
                return p;
        }
        return null;
    }
    
    // This is scary and fails unit tests. Unless every planet's ID is the 
    // same as their position in the Planets list, this return unpredcitable
    // results. 
    public Planet GetPlanetObj(int planetID)
    {
        Planet p = Planets.get(planetID);
        return p;
    }
    public List<Planet> GetPlanets()
    {
        return this.Planets;
    }
    
    public float GetX()
    {
        return x;
    }
    
    public float GetY()
    {
        return y;
    }
}
