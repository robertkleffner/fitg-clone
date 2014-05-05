package fitg;
import java.util.*;
/**
 * A stack contains a list of Entities called members 
 * When a new unit is first created, it is put in it's own new stack.
 * Stacks are stored in a StackList object in the Map class.
 * 
 */
public class Stack {
    
    private List<Entity> members;
    
    private int stackID;
    private int locationID;

    private Entity leader;
    private boolean mobile;
    private boolean active;
    private SIDE side;
    
    public Stack()
    {
        members = new ArrayList();
    }
    
    public void AddToStack(Entity entity)
    {
        if (!members.contains(entity))
            members.add(entity);
    }
    
    public void RemoveUnit(Entity entity)
    {
        members.remove(entity);
    }
    
    //returns true if stack is empty after operation
    public Boolean RemoveFromStack(Entity entity)
    {
        members.remove(entity);
        return members.isEmpty();
    }
    
    public int GetLocationID()
    {
        return locationID;
    }
    
    public void SetLocationID(int destID)
    {
        locationID = destID;
    }
    
    public int GetStackID()
    {
        return stackID;
    }
    
    public void SetStackID(int stackID)
    {
        this.stackID = stackID;
    }
    
    public SIDE GetSide()
    {
        return side;
    }
    public Entity GetMember(int index)
    {
        return members.get(index);
    }
    
    // BEN: Added this to access all entities within the stack. 
    // Better way to do this?
    public List<Entity> GetEntities()
    {
        return members;
    }
    
    public Boolean IsInStack(Entity ent)
    {
        return this.members.contains(ent);
    }
    
    public EntityType GetET(int index)
    {
        return members.get(index).ET;
    }
    
    public void SetSide(SIDE side)
    {
        this.side = side;
    }
    
    public boolean IsMissionCapableStack()
    {
        //A stack is considered mission capable if it has characters but no
        //military units
        boolean hasChar = false;
        
        for (Entity e : members)
        {
            if(e.ET == EntityType.CHARACTER)
                hasChar = true;
            //A stack is immediately disqualified if it has military forces
            if(e.ET == EntityType.MILITARY || e.ET == EntityType.SQUAD)
                  return false;  
        }
        if(hasChar)
            return true;
        else
            return false;
        
        
    }
    
    //returns true if at least one character in the stack is detected
    public boolean HasDetectedChar(){
        List<Entity> tempEntities = this.GetEntities();
        for (Entity e : tempEntities) {
            if (e.ET == EntityType.CHARACTER) {
                Character c = (Character)e;
                if(c.GetIsDetected() == true){
                    return true;
                }
            }
        }
        return false;
    }
    
    //returns true if at least one entity in the stack has not searched yet
    public boolean HasCharToSearch(){
        List<Entity> tempEntities = this.GetEntities();
        for (Entity e : tempEntities){
            if( e.hasSearched == false){
                return true;
            }
        }
        return false;
    }
}
