/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fitg;

import fitg.graphics.ArcButton;
import java.util.*;
/**
 *
 * @author Matt
 */

/**
 *
 * @author Matt
 */
public class Environ {
    
    public enum EnvironType{
        Urban,
        Wild,
        Fire,
        Liquid,
        Sub,
        Air
    }
    
    //-------------Private Member Variables--------------\\
    private int EnvironID;
    private int PlanetID;
    private int SystemID;
    private int ProvinceID;
    private EnvironType EnvType;
    private CombatCreature.CreatureName locals;
    private CombatCreature.CreatureName creature;
    private int ResourceRating;
    private boolean ResourceStar;
    private int CoupRating;
    private String SovereignName;
    private boolean SovereignSummoned;
    private boolean SovereignAssasinated;
    private boolean MissionsPerformed;
    private ArcButton _environButton;
    private List<Integer> StackIDs;
    private int EnvironSize;
    public boolean imperialMilitary;
    public boolean rebelMilitary;
    public boolean detectedRebels;  
    public boolean detectedImperials;
    
    public Environ(int provinceID, int systemID, int planetID, int environID, int environSize, EnvironType environType, String creatureName, String raceName)
    {
        this.ProvinceID = provinceID;
        this.SystemID = systemID;
        this.PlanetID = planetID;
        this.EnvironID = environID;
        this.EnvType = environType;
        SetCreature(creatureName);
        SetRace(raceName);
        this.StackIDs = new ArrayList();
        this.EnvironSize = environSize;
        this.imperialMilitary = false;
        this.rebelMilitary = false;
        this.detectedImperials = false;
        this.detectedRebels = false;
    }
    public void SetEnvironSize(int size)
    {
        EnvironSize = size;
    }
    public int GetEnvironSize()
    {
        return EnvironSize;
    }
    
    public void SetMissionsPerformed()
    {
        MissionsPerformed = true;
    }
    
    public boolean GetMissionsPerformed()
    {
        return MissionsPerformed;
    }
    public void SetEnvironButton(ArcButton b) {
        _environButton = b;
    }
    
    public int GetEnvironID()
    {
        return this.EnvironID;
    }
    
    public void SetEnvironID(int n)
    {
        this.EnvironID = n;
    }
    public int GetPlanetID()
    {
        return this.PlanetID;
    }
    public int GetSystemID()
    {
        return this.SystemID;
    }
    public int GetProvinceID()
    {
        return this.ProvinceID;
    }
    public EnvironType GetEnvironType()
    {
        return this.EnvType;
    }
    public EnvironType AddStack(int stackID)
    {
        //java.lang.System.out.println("Adding Unit: " + unitName);
        if (this.StackIDs.contains(stackID) == false)
            this.StackIDs.add(stackID);
        
        return this.EnvType;
    }
    public void RemoveStack(int stackID)
    {
        //java.lang.System.out.println("Removing Unit: " + unitName);
        if (this.StackIDs.contains(stackID))
            this.StackIDs.remove((Integer)stackID);
    }
    public List<Integer> GetListOfStacksInEnviron()
    {
        return this.StackIDs;
    }
    
    public ArcButton GetEnvironButton() {
        return _environButton;
    }
    
    public Stack GetMissionCapableStack()
    {
        for (int i: StackIDs)
        {
            Stack tmpStack = Board.instance().stacks.GetStackObj(i);
            if (tmpStack.IsMissionCapableStack() && tmpStack.GetSide() == Game.instance().turn.GetPhasingSide())
            {
                return tmpStack;
            }
//            else
//                return null;
        }
        return null;
    }
    
    public SIDE GetPlanetControl() 
    {
        SIDE control = Board.instance().GetProvinceObj(ProvinceID).
                GetSystemObj(SystemID).GetPlanetObj(PlanetID).controlledby;
        return control;
    }
    // Return the planet name that this environ is a member
    public String GetPlanetName()
    {
        return Board.instance().GetProvinceObj(ProvinceID).GetSystemObj(SystemID).GetPlanetObj(PlanetID).GetPlanetName();
    }
    public String GetLocationString()
    {
        Province tmpProv = Board.instance().GetProvinceObj(ProvinceID);
        StarSystem tmpSys = tmpProv.GetSystemObj(SystemID);
        Planet tmpPlanet = tmpSys.GetPlanetObj(PlanetID);
        return String.format("Province: %d, System: %s, Planet: %s, Environ: %d EnvironType: %s,  EnvironSize: %s", ProvinceID, tmpSys.GetSystemName(), tmpPlanet.GetPlanetName(), EnvironID, EnvType,EnvironSize); 
    }
    
    public Stack GetStackByID(int stackID){
        List<Integer> list = GetListOfStacksInEnviron();
        Stack stack;
        
        for (int i : list)
        {
           stack = Board.instance().stacks.GetStackObj(i);
           if(stack.GetStackID() == stackID)
           {
               return stack;
           }
        }
        return null;
    }
    // Set the creature to the correct type based on the input string
    private void SetCreature(String creatureName)
    {
        if (creatureName.isEmpty())
            this.creature = null;
        else if (creatureName.equals("0"))
            this.creature = null;
        else
        {
            try
            {
                this.creature = CombatCreature.CreatureName.valueOf(creatureName);
            }
            catch (Exception ex)
            {
                this.creature = null;
            }
        }    
    }
    // Set the race to the correct type based on the input string
    private void SetRace(String raceName)
    {
        if (raceName.isEmpty())
            this.locals = null;
        else
        {
            try
            {
                this.locals = CombatCreature.CreatureName.valueOf(raceName);
            }
            catch (Exception ex)
            {
                this.locals = null;
            }
        }    
    }
    // Get the locals type value
    public CombatCreature.CreatureName GetCreature()
    {
        return this.creature;
    }
    
    public CombatCreature.CreatureName GetLocals()
    {
        return this.locals;
    }
    public void SetDetected (SIDE s, boolean b){
       if (s == SIDE.IMPERIAL){
           if (b == true) {
               detectedImperials = true;
           } else detectedImperials = false;
       }  
       else {
           if (b == true) {
               detectedRebels = true;
           }
           else detectedRebels = false;
       }
    }
    
    // method to scan all stacks in environ and sets imperialMilitary and rebelMilitary flags 
    // in order to decide whether combat during movement phase is possible...
    
    public boolean CheckHasMilitary(){ 
        List<Integer> list = GetListOfStacksInEnviron();
        Stack stack = null;
        boolean changedimperial = false;
        boolean changedrebel = false;
                
        for (int i : list) {
            stack = Board.instance().stacks.GetStackObj(i);
            List<Entity> entities = stack.GetEntities();
            for (Entity e : entities) { 
                if (e.ET == EntityType.MILITARY) { 
                    if (e.side == SIDE.IMPERIAL) {
                        imperialMilitary = true;
                        changedimperial = true;
                    }
                    else {
                        rebelMilitary = true;
                        changedrebel = true;
                    }                  
                }
            }
        }
        if (!changedimperial){
            imperialMilitary = false;
        }
        if (!changedrebel){
            rebelMilitary = false;
        }
        if (imperialMilitary || rebelMilitary){
            return true;
        }
        else return false;
    }
    
    public boolean CheckHasMilitarySide(SIDE checkside)
    {
       List<Integer> list = GetListOfStacksInEnviron();
        Stack stack = null;
        Boolean confirm = false;
                
        for (int i : list) 
        {
            stack = Board.instance().stacks.GetStackObj(i);
            List<Entity> entities = stack.GetEntities();
            for (Entity e : entities) 
            { 
                if (e.ET == EntityType.MILITARY) 
                { 
                    if (e.side == checkside) 
                    {
                        confirm = true;
                    }
                    
                }
            }
        }
        
        return confirm;
    }
    
    public List<Stack> GetStacksForSide(SIDE s) {
        List<Integer> list = GetListOfStacksInEnviron();
        List<Stack> units = new ArrayList();
        Stack stack = null;
        
        for (int i : list)
        {
           stack = Board.instance().stacks.GetStackObj(i);
           if(stack.GetSide() == s)
           {
               units.add(stack);
           }
        }
        return units;
    }
    public List<Integer> GetUnitsForSide(SIDE s) {
        List<Integer> list = GetListOfStacksInEnviron();
        List<Integer> units = new ArrayList();
        Stack stack;
        
        for (int i : list)
        {
           stack = Board.instance().stacks.GetStackObj(i);
           if(stack.GetSide() == s)
           {
               units.add(list.get(list.indexOf(i)));
           }
        }
        
        return units;
    }
    
    public void Diplomacy()
    {
        Planet tmpPlanet = Board.instance().GetPlanetByName(this.GetPlanetName());
        boolean isImperial;
        
        //Determine phasing player
        if(Game.instance().turn.GetPhasingSide() == SIDE.IMPERIAL)
            isImperial = true;
        else
            isImperial = false;
        
        //Move loyalty appropriately
            switch(tmpPlanet.loyalty)
            {
                case Unrest:
                {
                    if(isImperial)
                        tmpPlanet.loyalty = Planet.PlanetLoyalty.Dissent;
                    else
                        //Possibly display dialogue box stating that no furth diplomacy progress may be made
                    {}
                    break;
                }
                case Dissent:
                {
                    if(isImperial)
                        tmpPlanet.loyalty = Planet.PlanetLoyalty.Neutral;
                    else
                        tmpPlanet.loyalty = Planet.PlanetLoyalty.Unrest;
                    break;
                }
                case Neutral:
                {
                    if(isImperial)
                        tmpPlanet.loyalty = Planet.PlanetLoyalty.Loyal;
                    else
                        tmpPlanet.loyalty = Planet.PlanetLoyalty.Dissent;
                    break;
                }
                case Loyal:
                {
                    if(isImperial)
                        tmpPlanet.loyalty = Planet.PlanetLoyalty.Patriotic;
                    else
                        tmpPlanet.loyalty = Planet.PlanetLoyalty.Neutral;
                    break;
                }
                case Patriotic:
                {
                    if(isImperial)
                    {} //Possibly display a message that this planet can be no more patriotic
                    else
                        tmpPlanet.loyalty = Planet.PlanetLoyalty.Loyal;
                    break;
                }
            }
         
        
   
    }
    
    //Returns true if the environ can be searched this phase
    public boolean IsSearchable(){

        boolean detectedCharPresent = false;
        SIDE phasingSide = Game.instance().turn.GetPhasingSide();
        SIDE nonPhasingSide = Game.instance().turn.GetNonPhasingSide();
        
        List<Stack> phasingStacks = this.GetStacksForSide(phasingSide);
        List<Stack> nonPhasingStacks = this.GetStacksForSide(nonPhasingSide);
        
        //if the side who is hiding has detected characters present, set phasingCharPresent = true;
        for (Stack s : phasingStacks) {
            if(s.HasDetectedChar() == true) {detectedCharPresent = true;}
        }
        
        //If detected phasing characters are present, check that searching team (nonphasing) has units present
        //who also have not yet searched
        if(detectedCharPresent == true){
            if (nonPhasingStacks.isEmpty() == false){
                for (Stack s : nonPhasingStacks) {
                    if(s.HasCharToSearch()){
                        return true;
                    }
                    
                }
            }
        }
        return false;
    }
    
}
