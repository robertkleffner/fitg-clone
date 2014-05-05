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
public class Province {
    private int ProvinceID;
    private List<StarSystem> Systems;
    
    public Province(int provinceID)
    {
        this.ProvinceID = provinceID;
        Systems = new ArrayList();
        
    }
    public void PrintSystems()
    {
        IOControl io = new IOControl();
        io.print(Systems.toString());
        
    }
   /* private void CreateSystems(Locations locations)
    {
        int maxSystems = (int) (Math.random() * 5 + 1);
        
        for (int i = 1; i <= maxSystems; i++)
        {
            System newSystem = new System(this.ProvinceID, i, locations);
            
            
            this.Systems.add(newSystem);
        }
    }*/
    public void AddSystem(StarSystem system)
    {
        Systems.add(system);
    }
    
    public int GetProvinceID()
    {
        return this.ProvinceID;
    }

    public StarSystem GetSystemByID(int systemID)
    {
        for (StarSystem s : Systems)
        {
            if (s.GetSystemID() == systemID)
                return s;
        }
        
        return null;
    }
        
    public List<StarSystem> GetSystems()
    {
        return this.Systems;
    }
    // This appears to have the same fault as the GetPlanetObj method in 
    // StarSytem class... Untestable...
    public StarSystem GetSystemObj(int sysID)
    {
        return Systems.get(sysID);
    }
}
