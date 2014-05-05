// Mission.java
// Description: The Mission class contains both static and non static members.
//    Static members apply to every misssion, and will be used to resolve 
//    mission outcomes per environ. To use call Mission.Initialize()
//    Prior running missions on an envrion Mission.set_environ() must
//       be passed the current environ.

package fitg;
import java.awt.Color;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author tell
 */

public class Mission {
    
    public enum MissionType {
        None,               // For when character is not assigned to a mission
        Assassination,
        StartRebelCamp,
        Coup,
        Diplomacy,
        Sovereign,
        FreePrisoners,
        GainChars,
        Steal,
        GatherInfo,
        Spaceship,
        GainPossessions,
        QuestionPrisoners,
        Rebellion,
        Sabotage,
        SubvertTroops
    }
        public enum MissionStatus {
        NotSet,Success,Failure,Aborted               // For when character is not assigned to a mission
    }
    //--- ------STATIC CLASS Methods and Fields---------------------------------
    public static List<Mission> MissionList;  // Holds all available missions  
    private static Environ environ_;     // environ to resolve mission list on
    public static Environ environ() { return environ_;}
    public static int draws_;
    public JPanel myParent;
    
    public  static void Initialize()
    {
        MissionList = new ArrayList();
        MissionList.add( new Mission("Not Assigned","description", MissionType.None));
        MissionList.add( new Mission("Gather Information","description", MissionType.GatherInfo));
        MissionList.add( new Mission("Gain Characters","description", MissionType.GainChars));
        MissionList.add( new Mission("Diplomacy","description", MissionType.Diplomacy));
        MissionList.add( new Mission("Assassination","description", MissionType.Assassination));
        MissionList.add( new Mission("Start Rebel Camp","description", MissionType.StartRebelCamp));
    }

    // Sets up missions for new environ. Clears all missiongroups,
    public static void set_environ(Environ environ) 
    { 
        environ_ = environ;
       for(Mission m : MissionList)
       {
           m.ResetMission();
       }
       draws_ = environ.GetEnvironSize();
    }
    
    public static void ResolveAction(int actionNumber)
    {
        int DiceRoll = Tables.DiceRoll(30);
        Action action = new Action(DiceRoll, environ().GetEnvironType() );
        for( Mission m : MissionList)
        {
            if(!m.missionGroup().isEmpty())
            {
                if (action.creatureAttacks)
                {
                    CreatureCombatMenu creatureAttack = new CreatureCombatMenu(m.panel.parentDialog, m.missionGroup_, environ_, action.creatureAttackNumber);                    
                }
                if(action.SuccessfulMissions.contains(m.type()))
                {
                    m.setStatus(MissionStatus.Success);
                    m.panel.setBackground(Color.GREEN);
                    m.panel.paintImmediately(m.panel.getVisibleRect());
                    m.Success();
                    m.textArea.setText("\nSuccess!\n");
                }
                else
                {
                    if(m.panel.getBackground() != Color.GREEN)
                    {
                        m.setStatus(MissionStatus.Failure);
                        m.panel.setBackground(Color.RED);
                        m.panel.paintImmediately(m.panel.getVisibleRect());
                    }
                }
            }
        }
        System.out.format("Rolled Dice:%d\n", DiceRoll );
    }
    
    public static void BonusDraws()
    {
        int DiceRoll = Tables.DiceRoll(30);
        Action action = new Action(DiceRoll, environ().GetEnvironType() );
        for( Mission m : MissionList)
        {
            if(!m.missionGroup().isEmpty())
            {
                if(action.SuccessfulMissions.contains(m.type()))
                {
                    m.setStatus(MissionStatus.Success);
                    m.panel.setBackground(Color.GREEN);
                    m.Success();
                }
                else
                {
                    //if(m.status_ == MissionStatus.NotSet || m.status_ == MissionStatus.Failure)
                    if(m.panel.getBackground() != Color.GREEN)
                    {
                        m.setStatus(MissionStatus.Failure);
                        m.panel.setBackground(Color.RED);
                    }
                }
            }
        }
        System.out.format("Rolled Dice:%d\n", DiceRoll );
    }
    // Returns a list of all characters on the environ that the mission is being
    // run that are allowed to be assigned to missions, and have not yet been
    // assigned to a mission
    public static List<Character> GetAvailChars()
    {
       Stack envStack = environ().GetMissionCapableStack();
       List <Character> availChars = new ArrayList();
       for(Entity c : envStack.GetEntities() )
       {    
           Character tempChar = (Character)c;
           if(tempChar.GetCurrentMissionAssignment() == MissionType.None)
           {
               availChars.add(tempChar);
           }   
       }
       return availChars;     
    }
    
    public static List<Character> GetAssignedChars()
    {
        Stack envStack = environ().GetMissionCapableStack();
       List <Character> assignedChars = new ArrayList();
       for(Entity c : envStack.GetEntities() )
       {    
           Character tempChar = (Character)c;
           if(tempChar.GetCurrentMissionAssignment() != MissionType.None)
           {
               assignedChars.add(tempChar);
           }   
       }
       return assignedChars;  
    }
    
    //------- NON STATIC Methids and Fields ------------------------------------
    public Mission(String missName,String missDesc, MissionType missionType)
    {   
        name_ = missName;
        description_ = missDesc;
        type_ = missionType;  
        status_ = MissionStatus.NotSet;
        //For TESTING purposes
        //TODO: calculate bonus draws
        bonusDraws_ = 4;
        missionGroup_ = new ArrayList();  
    }   
    //----- Private Fields
    private  List<Character> missionGroup_;
    private  String name_;
    private  String description_;
    private  MissionType type_;
    private  MissionStatus status_;
    private int bonusDraws_;
    
    public MissionMenu2.JPanelMission panel;
    public MissionMenu2.JTextAreaMission textArea;
    
        
    //----- Getters and Setters
    public String name()        { return name_; } 
    public String description() { return description_; } 
    public MissionType type()   { return type_; }
    public MissionStatus status()   { return status_; }
    public void SetBonusDraws(int newBonusDraws) {this.bonusDraws_ = newBonusDraws;}
    public int bonusDraws_() 
    {   int tmpBonusDraws = 0;
        for (Character c: this.missionGroup_)
        {
            tmpBonusDraws += c.GetMissionBonusDraws();
        }
        return tmpBonusDraws;
    }
    static void SetDraws(int remDraws) {Mission.draws_ = remDraws;}
    public void setStatus(MissionStatus status)
    { 
        status_ = status;
        System.out.format("Setting Mission:%s Status to: %s\n", this.name(), this.status().toString() );

    }
    public List<Character> missionGroup() { return missionGroup_; }
     
    public void MissionGroupAdd(Character character )
    {
        missionGroup_.add(character);
        character.SetCurrentMission(type());     // Assign Character to this mission 
    }
    public void MisionGroupRemove(Character character )
    {
        missionGroup_.remove(character);
        character.SetCurrentMission(MissionType.None); 
    } 
    
    // Empties mission group and resets fields.     
    // It calls Mission Group Remove on every charactr, so that all the 
    // characters that were set to a mission, are set back to MissionType none
    public void ResetMission()
    {
         for(Character c : missionGroup())
           {
               MisionGroupRemove(c);
           }
    }
    
    public boolean IsActive()
    {
        return true;
    }
    
    public void RunActionEvent(String actionCode)
    {
        System.out.println("Action Performed: " + actionCode);
    }
    
    public boolean StillAlive()
    {
        int count = 0;
        
        //Count all dead characters
        for(Character c: this.missionGroup())
        {
            if (c.GetIsInPlay())
                count++;
        }
        //If every member of the team is dead then the mission has failed
        if(count == this.missionGroup().size())
            return false;
        else
            return true;
    }
    
    public Action DrawAction(Environ.EnvironType envType)
    {
        int roll = Tables.DiceRoll(30);
        Action actionCard = new Action(roll, envType);
       return actionCard;
    }
    
    public void Success()
    {
        System.out.println("The" + this.type() + " mission was a success!");
        switch (this.type())
        {
            case Diplomacy:
            {
                Mission.environ_.Diplomacy();
                break;
            }
            case GainChars:
            {
                //Add a character
                Character newChar = Game.instance().GetRandomCharacter();
                if (newChar != null)
                {
                    JOptionPane.showMessageDialog(myParent, "You receive " + newChar.GetName());
                    System.out.println("****************OMGWTFBBQ**********" + newChar.GetName());
                    newChar.SetInPlay(true);
                    Stack myStack = new Stack();
                    myStack.AddToStack(newChar);
                    myStack.SetLocationID(Mission.environ_.GetEnvironID());
                    //System.out.println("***************OMGWTFBBQ*************" + Mission.environ_.GetEnvironID());
                    Board.instance().stacks.NewCharacter(Mission.environ_.GetEnvironID(), newChar);
                }
                else
                {
                    JOptionPane.showMessageDialog(myParent, "There are no more characters available!");
                }
                break;
            }
            case GatherInfo:
            {
                //SetDetected
                break;
            }
            case GainPossessions:
            {
                //Grab possession
                break;
            }
            case Rebellion:
            {
                //Set to Rebellion
                break;
            }
                
        }
    }
    
}

