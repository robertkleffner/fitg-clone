package fitg;
import java.io.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Matt
 */
public class Board
{
    private static Board _instance = null;
    
    IOControl io;
    //Locations locations;
    private int environCount;
    private List<Province> Provinces;
    private List<Environ> environs;
    public StackList stacks;
    //public EntityLists entities;    //might not be used
    public Hashtable selectedUnits;
    public ArrayList<Entity> selectedEntity = null;
    public ArrayList<Integer> selectedStackID = null;
    public boolean inMovement = false;
    private Tables table;
    
    private Board()
    {
        io = new IOControl();
        //locations = new Locations();
        Provinces = new ArrayList();
        environs = new ArrayList();
        stacks = new StackList();
        CreateMap();
    }
    
    public static Board instance() {
        if (_instance == null) {
            _instance = new Board();
        }
        return _instance;
    }
    
    private void CreateMap()
    {
        IOControl io = new IOControl();
        String line = "";
        int provinceID = 0;
        int systemID = 0;
        float systemX, systemY;
        String systemName = "";
        int planetID = 0;
        String planetName = "";
        String planetRace = "";
        int environID = 0;
        //String environType = "";
        Environ.EnvironType envType;
        int environSize = 0;
        String environRace = "";
        String environResources = "";
        String environCreature = "";
        String environPolitics = "";
        char envChar;
        BufferedReader br = null;
        int count = 0;
        int orbital = 0;
        try {
            InputStream in = getClass().getResourceAsStream("galaxy.dat");
            
            br = new BufferedReader(new InputStreamReader(in));
            //br = new BufferedReader( new FileReader("testFile.txt"));
            Province currProv = null;
            StarSystem currSystem = null;
            Planet currPlanet = null;
            
            while( (line = br.readLine()) != null){
                String[] tokens = line.split(",");
                //new province
                if(!line.contains("\t"))
                {
                    //System.out.println("province");
                    //Add the previous province to the list now that it has all needed info.
                    line = line.trim();
                    tokens = line.split(",");
                    provinceID = Integer.parseInt(tokens[0]);
                    provinceID -= 1;
                    currProv = new Province(provinceID);
                    this.Provinces.add(currProv);
                    //io.println("Added Province: " + provinceID);
                }
                //new system
                else if(line.contains("\t") && !line.contains("\t\t"))
                {
                    orbital = 0;
                    line = line.trim();
                    tokens = line.split(",");
                    systemID = Integer.parseInt(tokens[0]);
                    systemID -= 1;
                    systemName = tokens[1];
                    systemX = Float.parseFloat(tokens[2]);
                    systemY = Float.parseFloat(tokens[3]);
                    currSystem = new StarSystem(currProv.GetProvinceID(), systemID, systemName, systemX, systemY);
                    currProv.AddSystem(currSystem);
                    //this.GetProvinceObj(provinceID).AddSystem(currSystem);
                    //io.println("Added System: " + systemName);
                }
                //new planet
                else if(line.contains("\t\t") && !line.contains("\t\t\t"))
                {
                    line = line.trim();
                    tokens = line.split(",");
                    planetID = Integer.parseInt(tokens[0]);
                    planetID -= 1;
                    orbital++;
                    planetName = tokens[1];
                    if(tokens[2].contains("-") || tokens[2].contains("Secret"))
                        planetRace = "0";
                    else
                        planetRace = tokens[2];

                    currPlanet = new Planet(currProv.GetProvinceID(), currSystem.GetSystemID(), planetID, planetName, orbital);
                    currSystem.AddPlanet(currPlanet);
                    //this.GetProvinceObj(provinceID).GetSystemObj(systemID).AddPlanet(currPlanet);
                    //io.println("Added planet " + planetName +" in " + systemID + ": " + systemName);
                }
                //new environ
                else if(line.contains("\t\t\t"))
                {
                    //Create environ with 
                    
                    line = line.trim();
                    tokens = line.split(",");
                    //if(tokens.length == 7)
                    //{
                        envChar = tokens[1].charAt(0);
                        System.out.println(envChar);
                        switch (envChar)
                        {
                            case 'U':
                                envType = Environ.EnvironType.Urban;
                                break;
                            case 'W':
                                envType = Environ.EnvironType.Wild;
                                break;
                            case 'F':
                                envType = Environ.EnvironType.Fire;
                                break;
                            case 'L':
                                envType = Environ.EnvironType.Liquid;
                                break;
                            case 'A':
                                envType = Environ.EnvironType.Air;
                                break;
                            case 'S':
                                envType = Environ.EnvironType.Sub;
                                break;
                            default:
                                envType = Environ.EnvironType.Urban;
                        }
                        environSize = Integer.parseInt(tokens[2]);
                        environRace = tokens[3];
                        environResources = tokens[4];
                        environCreature = tokens[5];
                        environPolitics = tokens[6];
                        
                        Environ tmpEnviron = new Environ(currProv.GetProvinceID(), currSystem.GetSystemID(), currPlanet.GetPlanetID(), environID, environSize, envType, environCreature, environRace);
                        currPlanet.AddEnviron(tmpEnviron);
                        currPlanet.AddEnvironID(environID);
                        environs.add(tmpEnviron);
                        //this.GetProvinceObj(provinceID).GetSystemObj(systemID).GetPlanetObj(planetID).AddEnviron(tmpEnviron);
                        //this.GetProvinceObj(provinceID).GetSystemObj(systemID).GetPlanetObj(planetID).AddEnvironID(environID);
                        
                        //io.println("environ of type " + environType + " in: " + planetName + ": peopled by: " + planetRace);
                        environID++;
                    //}
                }
            }
            //Provinces.get(0).PrintSystems();
        } catch (FileNotFoundException e) {
            //System.err.println("Unable to find the file");
        } catch (IOException e) {
            //System.err.println("Unable to read the file");
        } 
        environCount = environID;
    }
    
    public Province GetProvinceByID(int provinceID)
    {
        for (Province p : Provinces)
        {
            if (p.GetProvinceID() == provinceID)
                return p;
        }

        return null;
    }
    public List<Province> GetProvinces()
    {
        return this.Provinces;
    }
    
    public Province GetProvinceObj(int provID)
    {
     return Provinces.get(provID);   
    }
    
    public void PrintAll()
    {
        IOControl io = new IOControl();
        
        for(Province p : Provinces)
        {
            for(StarSystem s : p.GetSystems())
            {
                for(Planet planet : s.GetPlanets())
                {
                    for( Environ env : planet.GetEnvirons())
                    {
                    //Environ env = this.environs.get(envID);
                    io.println(String.format("ProvinceID: %d, SystemID: %s, PlanetID: %s, EnvironID: %d, type: %s, size: %d", env.GetProvinceID(), env.GetSystemID(), env.GetPlanetID(), env.GetEnvironID(), env.GetEnvironType(), env.GetEnvironSize()));
                    }
                }
            }
        }
    }
    
    public void PrintEnvirons()
    {
        IOControl io = new IOControl();
        for(Environ env : environs)
        {
            io.println(this.GetLocationString(env.GetEnvironID()));
        }
    }
    
    public String GetLocationString(int envID)
    {
        Environ env = environs.get(envID);
        Province tmpProv = GetProvinceObj(env.GetProvinceID());
        StarSystem tmpSys = tmpProv.GetSystemObj(env.GetSystemID());
        Planet tmpPlanet = tmpSys.GetPlanetObj(env.GetPlanetID());
        return String.format("%d : Province: %d, System: %s, Planet: %s, Environ: %d EnvironType: %s,  EnvironSize: %d", envID, env.GetProvinceID(), tmpSys.GetSystemName(), tmpPlanet.GetPlanetName(), envID, env.GetEnvironType(),env.GetEnvironSize()); 
    }

   public int GetEnvironCount()
   {
       return this.environCount;
   }
   
   public Environ GetEnviron(int location){
       return environs.get(location);
   }
   //This move function is used with the movement environ menu, moves 
   //all units from their respective stacks into a new stack and then 
   //places them in the destEnvID
   public void MoveStack(int destEnvID)
   {
       Movement move = new Movement();
       move.Move(selectedEntity, selectedStackID, destEnvID);
       //TODO: Any units that move should have there hasMoved property to true
       /*Stack movingUnits = stacks.AddStack();
       movingUnits.SetSide(selectedEntity.get(0).GetSide());
       movingUnits.SetLocationID(destEnvID);
       int newStackID = movingUnits.GetStackID();
       List<Entity> entities;
       Notify notify; 
       Character c; 
       boolean canMove = false;

       
                //Enumeration ids = selectedUnits.keys();
                //for(JToggleButton i : buttons)
                //while(ids.hasMoreElements())
                for(int i = 0; i<selectedEntity.size(); i+=1)
                {
                    
                   // System.out.println("I AM MOVING! Entity: " + selectedEntity.get(i));
                    Entity tempEntity = selectedEntity.get(i);
                    int stackID = selectedStackID.get(i);
                    stacks.UnitSwitchStacks(tempEntity, stackID, newStackID);
                   // System.out.println("MY STACK ID: "+stackID);
                    //keyID is the Entity, stackID is int, the stackID
                    //Entity keyID = (Entity) ids.nextElement();
                    //int stackID = (Integer) selectedUnits.get(keyID);
                    //get unit's stack
                    //Stack tempStack = stacks.GetStackObj(stackID);
                    //getting entity from stack
                    //Entity tempEntity = tempStack.GetMember(keyID);
                    //remove entity from current stack
                   // if(stacks.GetStackObj(stackID).RemoveFromStack(tempEntity))
                   // {
                        //if stack is empty after removal, remove stack
                    //    stacks.RemoveStack(stacks.GetStackObj(stackID));
                    //}
                    //add entity to new stack for movement
                    //movingUnits.AddToStack(tempEntity);
                    System.out.println("Units left: "+ stacks.GetStackObj(stackID).GetEntities());
                }
                //set stack location to destEnv
                
                //add stack to destEnv
           

                entities = movingUnits.GetEntities();
                
                // Military stacks do not undergo detection routines (for now...?)
                for (Entity e : entities) { 
                    if (e.ET == EntityType.MILITARY) { 
                        canMove = true;
                        AddStackToEnviron(destEnvID, movingUnits.GetStackID(), false);                
                    }
                }
                for (Entity e : entities) { 
                    if (e.ET == EntityType.CHARACTER) { 
                        c = (Character)e;
                        if (c.GetHasShip()) {
                            canMove = true;
                            AddStackToEnviron(destEnvID, movingUnits.GetStackID(), false);
                        }
                    }
                }
                if (!canMove) {
//                    notify = new Notify("HEY! Characters shouldn't be moving without a ship or military units!");
                    AddStackToEnviron(destEnvID, movingUnits.GetStackID(), true);                                                                
                }*/
   }
   
   public void DetectionRoutine(int envID, Stack envstack){
       char detResult = 'X';
       Character c = null;
       Character a = null;       
       Integer evasion = null;
       List<Entity> entities = envstack.GetEntities();
       Notify notify;
       
       if (environs.get(envID).GetPlanetControl() == envstack.GetSide() || environs.get(envID).GetPlanetControl() == SIDE.NEUTRAL){
           return;
       }
       // Military stacks do not undergo detection routines (for now...?)
       for (Entity e : entities) { 
           if (e.ET == EntityType.MILITARY) { 
               return;
           }
       }
              
       // find character with a ship and calculate evasion rating and
       // result of detection routine.
       for (Entity e : entities) { 
           if (e.ET == EntityType.CHARACTER) { 
               c = (Character)e;
               if (c.GetHasShip()) {
                   evasion = c.GetEvasionRating();
                   detResult = table.Detection(evasion);
                   break;
               }
           }
       }
	// U = U: Undetected
	// D = D: Detected
	// Dd = I: Detected and Damaged (I for Injured)
	// E = E: Eliminated
       
       if (evasion != null) {
           if (detResult == 'U') {
               if (envstack.GetSide() == SIDE.IMPERIAL) {
                   environs.get(envID).detectedImperials = false;
                   JOptionPane.showMessageDialog(null, "Movement Undetected!!");
//                   notify  = new Notify("Movement Undetected!!");
               }
               else {
                   environs.get(envID).detectedRebels = false;
                   JOptionPane.showMessageDialog(null, "Movement Undetected!!");
//                   notify  = new Notify("Movement Undetected!!");
               }
           }
           if (detResult == 'D') {
               if (envstack.GetSide() == SIDE.IMPERIAL) {
                   environs.get(envID).detectedImperials = true;
                   JOptionPane.showMessageDialog(null, "Forces detected!!");                   
//                   notify  = new Notify("Forces detected!!");
               }
               else {
                   environs.get(envID).detectedRebels = true;
                   JOptionPane.showMessageDialog(null, "Forces detected!!");                   
//                   notify  = new Notify("Forces detected!!");
               }
           }
           else if (detResult == 'I') {
               if (envstack.GetSide() == SIDE.IMPERIAL) {
                   environs.get(envID).detectedImperials = true;
                   if (c.GetShip().IsDamaged()){
                       for (Entity e : entities){
                           a = (Character)e;
                           a.SetInPlay(false);
                           a.SetDead(true);
                       }
                       RemoveStackFromEnviron(envID, envstack.GetStackID());
                       envstack = new Stack();
                       JOptionPane.showMessageDialog(null, "Forces eliminated!!");                   
//                       notify  = new Notify("Forces Eliminated!!");
                   }
                   else { 
                       c.GetShip().SetDamaged(true);
                       JOptionPane.showMessageDialog(null, "Forces detected!! Ship Damaged!!");                   
//                       notify  = new Notify("Forces detected!! Ship Damaged!!");
                   }
               }
               else {
                   environs.get(envID).detectedRebels = true;
                   if (c.GetShip().IsDamaged()){
                       for (Entity e : entities){
                           a = (Character)e;
                           a.SetInPlay(false);
                           a.SetDead(true);
                       }
                       RemoveStackFromEnviron(envID, envstack.GetStackID());
                       envstack = new Stack();
                       JOptionPane.showMessageDialog(null, "Forces eliminated!!");                   
//                       notify  = new Notify("Forces Eliminated!!");
                   }
                   else {
                       c.GetShip().SetDamaged(true);
                       JOptionPane.showMessageDialog(null, "Forces detected!! Ship Damaged!!");                   
//                       notify  = new Notify("Forces detected!! Ship Damaged!!");
                   }
               }
           }
           else if (detResult == 'E') {
               for (Entity e : entities){
                   a = (Character)e;
                   a.SetInPlay(false);
                   a.SetDead(true);
               } 
               RemoveStackFromEnviron(envID, envstack.GetStackID());
               envstack = new Stack();
               JOptionPane.showMessageDialog(null, "Forces eliminated!!");                   
//               notify  = new Notify("Forces eliminated!!");
           }
       }
   }
   
   public void AddStackToEnviron(int envID, int stackID, boolean isInit)
   {
       if (isInit) { // only check for valid combat if this method is being used during 
                     // movement phase, i.e. not during initalization
           environs.get(envID).AddStack(stackID); // actually moving to the new environ
           environs.get(envID).CheckHasMilitary(); // scan all stacks in environ and raise detected flags 
       }
       else {

           environs.get(envID).AddStack(stackID); // actually moving to the new environ
           Stack movingUnits = environs.get(envID).GetStackByID(stackID); // clumsy. pass in a stack id then use it
                                                                          // to get access to that stack... 
           
           DetectionRoutine(envID, movingUnits);
           environs.get(envID).CheckHasMilitary(); // scan all stacks in environ and raise detected flags 
           

    //       Stack movingUnits = environs.get(envID).GetStackByID(stackID); // clumsy. pass in a stack id then use it
                                                                            // to get access to that stack... 
   // Unused at the moment, rather than automatically prompting
   // player with combat dialog, they will use Resolve Combat 
   // button in movement dialog.
           
          /* if( Game.instance().turn.GetPhasingSide() == SIDE.IMPERIAL){
               if (environs.get(envID).detectedRebels) {
                    prompt for ombat option
                   CombatRoleMenu docombat = new CombatRoleMenu(null, 
                           environs.get(envID).GetUnitsForSide(SIDE.IMPERIAL), 
                           environs.get(envID).GetUnitsForSide(SIDE.REBEL), true);
                   docombat.createGUI();
               } 
           }
           if( Game.instance().turn.GetPhasingSide() == SIDE.REBEL){
               if(environs.get(envID).detectedImperials){
                    prompt for ombat option
                   CombatRoleMenu docombat = new CombatRoleMenu(null, 
                           environs.get(envID).GetUnitsForSide(SIDE.REBEL), 
                           environs.get(envID).GetUnitsForSide(SIDE.IMPERIAL), true);
                   docombat.createGUI();
               }
           } */
       }
   }
   
   public void RemoveStackFromEnviron(int envID, int stackID)
   {
       environs.get(envID).RemoveStack(stackID);
       environs.get(envID).CheckHasMilitary(); // scan all stacks in environ and raise or lower detected flags 
   }
   
   public List<Integer> GetStacksInEnviron(int envID)
   {
       Environ env;
       env = this.environs.get(envID);
       return env.GetListOfStacksInEnviron();
   }
   
   public void PrintStacksInEnviron(int envID, SIDE side)
   {
       Stack stack;
       List<Integer> stackIDs;
       Environ env;
       env = this.environs.get(envID);
       stackIDs = env.GetListOfStacksInEnviron();
       
       for(int i : stackIDs)
       {
           stack = this.stacks.GetStackObj(i);
           if(stack.GetSide() == side)
                io.println("Stack " + i);
       }
       
   }
   
   public Boolean ValidEnemy( int stackID, int envID, SIDE side)
   {
       Environ env = this.environs.get(envID);
       Boolean isValid = false;
       Stack stack;
       for (int i : env.GetListOfStacksInEnviron())
       {
           if(i == stackID)
               isValid = true;
       }
       
       stack = this.stacks.GetStackObj(stackID);
       if(stack.GetSide() != side)
           isValid = false;
       
       return isValid;
   }
   
   /*-------------------------------------------------------------------------
    * Character Combat
    */
   public void CharacterCombat()
   {
   
   }
   //End CharacterCombat -----------------------------------------------------
   
   public StackList GetStackList() {
       return stacks;
   }
   
   public Planet GetPlanetByName(String name)
   {
       for(Province p : this.Provinces)
           for(StarSystem s : p.GetSystems())
               for(Planet pl : s.GetPlanets())
                   if(pl.GetPlanetName().equals(name))
                       return pl;
       return null;
   }
   
   
}
