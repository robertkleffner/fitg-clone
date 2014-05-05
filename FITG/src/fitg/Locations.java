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
public class Locations
{
    private List<Environ> LocationList;
    
    public Locations()
    {
        LocationList = new ArrayList();
    }
    
    public int AddNewLocation(Environ e)
    {
        int counter = LocationList.size();
        
        e.SetEnvironID(counter);
        
        LocationList.add(e);
        
        return counter;
    }

    public Environ GetLocationObj(int id)
    {
        return LocationList.get(id);
    }

    public int GetLocationListSize()
    {
        return LocationList.size();
    }
    

}
