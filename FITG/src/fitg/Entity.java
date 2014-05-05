/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fitg;

/**
 *
 * @author Alex
 * 
 * 
 * 
 * Entity is the lookup information for any non-possession piece on the map
 * 
 */
public class Entity {
    public EntityType ET;  // used to determine what type of entity is being dealt with
    public int ID;         // currently not used. could store stackID of stack that contains this
    public boolean hasMoved; // used to determine whether entity has moved yet
    public boolean hasSearched; // used to determine whether entity has searched yet
    public SIDE side;

    public Entity(EntityType et, SIDE side)
    {
        ET = et;
        this.side = side;
        hasMoved = false;
        hasSearched = false;
    }
    
    public void SetHasMoved(boolean moved)
    {
        hasMoved = moved;
    }
    
    public boolean GetHasMoved()
    {
        return hasMoved;
    }
    
    public void SetHasSearched(boolean searched)
    {
        hasSearched = searched;
    }
    
    public boolean GetHasSearched()
    {
        return hasSearched;
    }
    
    public SIDE GetSide() { return this.side; }
}
