/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fitg;
import java.util.*;
import javax.swing.*;



/**
 *
 * @author matt
 */
public class Possession {
    private String name;                    // Possession name
    private String imageFilename;           // Image filename
    private String info;                    // General information
    private boolean inPlay;                 // flag for if item is currently in play
    private boolean destroyed;              // flag for if item has been destroyed
    private int lifespan;                   // lifespan counter
    private boolean inoperative;            // flag for if item is currently inpoerative
    public enum PossessionType { Ship, Companion, Weapon, Object }; // possession types
    private PossessionType type;            // type value for this object
    private String assignment;              // name of character currently assigned to
    private int combatBonus;                // combat bonus value
    private int defenseBonus;               // combat bonus while defending
    private int enduranceBonus;             // endurance bonus value
    private int intelligenceBonus;          // intelligence bonus value
    private int leadershipBonus;            // leadership bonus value
    private int diplomacyBonus;             // diplomacy bonus value
    private int navigationBonus;            // navigation bonus value
    private int hidingBonus;                // character hiding bonus
    private boolean itemHasSpecial;         // flag for whether or not this possession has a usable special
    private boolean specialHasBeenUsed;     // flag for whether or not the special has been used
    private boolean removeItemOnceUsed;     // flag for whether or not this possession is removed from the game once used
    private boolean rollAfterUse;           // flag for if a roll occurs to determine inoperative status
    private int inoperativeRoll;            // roll value for when item becomes inoperative
    private List<String> statItems;         // List of stat items from dat file
    private List<Map.Entry<Mission.MissionType,Integer>> missionBonusDictionary; // Dictionary for mission + bonus draw
    private int specialCheckGameRoundValue; // Stores the game round value for when a check on using specials was performed
    private Phase specialCheckGamePhase;    // stored the game phase value for when a check on using specials was performed
    private List<String> bonusDescriptions; // List of bonus descriptions to be displayed to the user
    
    
    // Basic class initializer
    public Possession(PossessionType possessionType, String name, String imageFile)
    {
        this.name  = name;
        this.type = possessionType;
        this.imageFilename = imageFile;
        this.inPlay = false;
        this.destroyed = false;
        this.lifespan = 100;
        this.inoperative = false;
        this.assignment = null;
        this.combatBonus = 0;
        this.defenseBonus = 0;
        this.enduranceBonus = 0;
        this.intelligenceBonus = 0;
        this.leadershipBonus = 0;
        this.diplomacyBonus = 0;
        this.navigationBonus = 0;
        this.hidingBonus = 0;
        
        
        this.statItems = new ArrayList();
        this.missionBonusDictionary = new ArrayList();
        this.bonusDescriptions = new ArrayList();
        this.specialCheckGameRoundValue = -1;
        this.specialCheckGamePhase = Phase.Interphase;
        this.itemHasSpecial = false;
        this.specialHasBeenUsed = false;
        this.removeItemOnceUsed = false;
        this.rollAfterUse = false;
        this.inoperativeRoll = 0;
    }
    
    // ************************************************************************
    // Getters
    // ************************************************************************
    public String GetName() 
    {
        if (this.inoperative)
            return this.name + " (inoperative)";
        
        if (this.destroyed)
            return this.name + " (destroyed)";
        
        return this.name;
    }
    // Get possession type
    public PossessionType GetPossessionType() { return this.type; }
    // Get Character name this possession is assigned to
    public String GetCharacterAssignment() { return this.assignment; }
    public boolean GetInPlay() { return this.inPlay; }
    public boolean GetIsDestroyed() { return this.destroyed; }
    public boolean GetIsInoperative() { return this.inoperative; }
    public int GetCombatBonus() 
    { 
        if (this.itemHasSpecial)
        {
            if (PromptUser())
                return this.combatBonus;
            else
                return 0;
        }
        return this.combatBonus;
    }
    public int GetDefenseCombatBonus()
    { 
        if (this.itemHasSpecial)
        {
            if (PromptUser())
                return this.defenseBonus;
            else
                return 0;
        }
        return this.defenseBonus; 
    }
    public int GetEnduranceBonus() 
    { 
        if (this.itemHasSpecial)
        {
            if (PromptUser())
                return this.enduranceBonus;
            else
                return 0;
        }
        return this.enduranceBonus; 
    }
    public int GetIntelligenceBonus() 
    { 
        if (this.itemHasSpecial)
        {
            if (PromptUser())
                return this.intelligenceBonus;
            else
                return 0;
        }
        return this.intelligenceBonus; 
    }
    public int GetLeadershipBonus() 
    { 
        if (this.itemHasSpecial)
        {
            if (PromptUser())
                return this.leadershipBonus;
            else
                return 0;
        }
        return this.leadershipBonus;
    }
    public int GetDiplomacyBonus() 
    { 
        if (this.itemHasSpecial)
        {
            if (PromptUser())
                return this.diplomacyBonus;
            else
                return 0;
        }
        return this.diplomacyBonus; 
    }
    public int GetNavigationBonus() 
    { 
        if (this.itemHasSpecial)
        {
            if (PromptUser())
                return this.navigationBonus;
            else
                return 0;
        }
        return this.navigationBonus; 
    }
    public int GetHidingBonus() 
    { 
        if (this.itemHasSpecial)
        {
            if (PromptUser())
                return this.hidingBonus;
            else
                return 0;
        }
        return this.hidingBonus; 
    }
    
    public String GetBonusDescriptionHTML()
    {
        String s = "None";
        
        for (String d : this.bonusDescriptions)
        {
            if (s.equals("None"))
                s = d;
            else
                s += d;     
            s+= "<br>";
        }
        
        return s;
    }
    // GetMissionBonusDraws will return the total number of bonus draws for the
    // mission type.
    public int GetMissionBonusDraws(Mission.MissionType missionTypeValue)
    {
        int bonusDraws = 0;
        
        for (Map.Entry<Mission.MissionType,Integer> e : this.missionBonusDictionary)
        {
            if (e.getKey() == missionTypeValue)
            {
                bonusDraws = e.getValue();
                return bonusDraws;
            }
        }
        
        return bonusDraws;
    }
    
    
    
    
    
    // ************************************************************************
    // Setters
    // ************************************************************************
    // Set Character name that this possession is assigned to
    public void SetCharacterAssignment(String characterName) { this.assignment = characterName; }
    public void SetInPlay(boolean status) { this.inPlay = status; }
    public void SetInoperative(boolean status) { this.inoperative = status; }
    // Get possession type
    public void SetPossessionType(PossessionType type) { this.type = type; }
    // Set Character name this possession is assigned to
    public void SetCombatBonus(int combatBonus) { this.combatBonus = combatBonus; }
    public void SetEnduranceBonus(int enduranceBonus) { this.enduranceBonus = enduranceBonus; }
    public void SetIntelligenceBonus(int intelligenceBonus) { this.intelligenceBonus = intelligenceBonus; }
    public void SetLeadershipBonus(int leadershipBonus) { this.leadershipBonus = leadershipBonus; }
    public void SetDiplomacyBonus(int diplomacyBonus) { this.diplomacyBonus = diplomacyBonus; }
    public void SetNavigationBonus(int navigationBonus) { this.navigationBonus = navigationBonus; }
    
    
    public static PossessionType GetPossessionType(String itemtype)
    {
        if(itemtype.equals("Weapon"))
            return PossessionType.Weapon;
        else if(itemtype.equals("Object"))
            return PossessionType.Object;
        else if(itemtype.equals("Companion"))
            return PossessionType.Companion;
        else if(itemtype.equals("Ship"))
            return PossessionType.Ship;
        return PossessionType.Ship;
    }
    // bonuses for possessions are stored as generic stat1, stat2, stat3.... in the
    // dat file. This method will accept the stat name to be used for parsing
    // and apply the stat bonus
    public void AddPossessionStat(String item)
    {
        this.statItems.add(item);

        ApplyStatBonus(item);
    }
    // Big switch statement for applying stat items to the possession
    // Lots of TODO: items remain
    private void ApplyStatBonus(String item)
    {
        StatType t = StatType.valueOf(item);
        
        switch (t)
        {
            case stat1:
                this.combatBonus = 1;
                AddBonusDescription("plus 1 Combat Rating");
                break;
            case stat2:
                AddMissionBonus(Mission.MissionType.Assassination, 1);
                AddBonusDescription("plus 1 Assassination Bonus Draw");
                break;
            case stat3:
                AddMissionBonus(Mission.MissionType.Assassination, 3);
                AddBonusDescription("plus 3 Assassination Bonus Draw");
                break;
            case stat4:
                this.combatBonus = 3;
                AddBonusDescription("plus 3 Combat Rating");
                break;
            case stat5:
                // TODO: once used, item is removed from game
                this.itemHasSpecial = true;
                this.specialHasBeenUsed = false;
                this.removeItemOnceUsed = true;
                this.rollAfterUse = false;
                AddBonusDescription("Single Use");
                break;
            case stat6:
                // TODO: adds +2 to user selected attribute for one game turn
                AddBonusDescription("plus 2 to any stat (not implemented)");
                break;
            case stat7:
                // TODO: once used, item is removed from game
                this.itemHasSpecial = true;
                this.specialHasBeenUsed = false;
                this.removeItemOnceUsed = true;
                this.rollAfterUse = false;
                AddBonusDescription("Single Use");
                break;
            case stat8:
                // TODO: PDB level -1 during detection
                AddBonusDescription("PDB level minus 1 during detection");
                break;
            case stat9:
                this.hidingBonus = 2;
                AddBonusDescription("plus 2 to Hiding");
                // TODO: does not apply during character search
                break;
            case stat10:
                this.hidingBonus = 4;
                AddBonusDescription("plus 4 to Hiding");
                break;
            case stat11:
                // TODO: Irate locals combat strength cut in half
                AddBonusDescription("Reduce Irate locals combat strength in half (not implemented)");
                break;
            case stat12:
                // TODO: Set PDB on planet to down
                AddBonusDescription("Set PDB to down");
                break;
            case stat13:
                // TODO: after each use, Roll dice, if >= 3, item becomes inoperable
                this.itemHasSpecial = true;
                this.specialHasBeenUsed = false;
                this.removeItemOnceUsed = false;
                this.rollAfterUse = true;
                this.inoperativeRoll = 3;
                AddBonusDescription("Chance to become inoperative");
                break;
            case stat14:
                // TODO: heal all wounds - can not be used between combat rounds
                AddBonusDescription("Heal all wounds");
                break;
            case stat15:
                // TODO: after each use, Roll dice, if roll = 6, item becomes inoperable
                this.itemHasSpecial = true;
                this.specialHasBeenUsed = false;
                this.removeItemOnceUsed = false;
                this.rollAfterUse = true;
                this.inoperativeRoll = 6;
                AddBonusDescription("Chance to become inoperative");
                break;
            case stat16:
                // TODO: damage -1
                AddBonusDescription("Reduce damage by 1");
                break;
            case stat17:
                // TODO: may purchase ship in deck, except S-XIII
                AddBonusDescription("Can be used to purchase a ship");
                break;
            case stat18:
                // TODO: negates enemy gather information mission
                AddBonusDescription("Negates enemy gather info mission");
                break;
            case stat19:
                AddMissionBonus(Mission.MissionType.Assassination, 2);
                AddMissionBonus(Mission.MissionType.Coup, 2);
                AddMissionBonus(Mission.MissionType.Diplomacy, 2);
                AddMissionBonus(Mission.MissionType.Sovereign, 2);
                AddMissionBonus(Mission.MissionType.Sabotage, 2);
                AddMissionBonus(Mission.MissionType.FreePrisoners, 2);
                AddMissionBonus(Mission.MissionType.GatherInfo, 2);
                AddBonusDescription("plus 2 Assassination Bonus draw");
                AddBonusDescription("plus 2 Coup Bonus draw");
                AddBonusDescription("plus 2 Diplomacy Bonus draw");
                AddBonusDescription("plus 2 Sovereign Bonus draw");
                AddBonusDescription("plus 2 Sabotage Bonus draw");
                AddBonusDescription("plus 2 Free Prisoners Bonus draw");
                AddBonusDescription("plus 2 Gather Info Bonus draw");
                break;
            case stat20:
                this.intelligenceBonus = 1;
                AddBonusDescription("plus 1 Intelligence");
                break; 
            case stat21:
                // TODO: Disable enemy character for 1 game phase
                AddBonusDescription("Can disable an enemy character");
                break;
            case stat22:
                // TODO: after each use, Roll dice, if roll = 5 or 6, item becomes inoperable
                this.itemHasSpecial = true;
                this.specialHasBeenUsed = false;
                this.removeItemOnceUsed = false;
                this.rollAfterUse = true;
                this.inoperativeRoll = 5;
                AddBonusDescription("Chance to become inoperative");
                break;
            case stat23:
                this.defenseBonus = 2;
                AddBonusDescription("plus 2 to combat when defending");
                break;
            case stat24:
                // TODO: Ignore first creature attack on mission
                AddBonusDescription("Ignore first creature attack");
                break;
            case stat25:
                // TODO: May take a wound, but is removed from play afterward
                AddBonusDescription("Can absorb 1 wound, but is removed from game");
                break;
            case stat26:
                // TODO: after each use, Roll dice, if roll = 6, item becomes inoperable
                this.itemHasSpecial = true;
                this.specialHasBeenUsed = false;
                this.removeItemOnceUsed = false;
                this.rollAfterUse = true;
                this.inoperativeRoll = 6;
                AddBonusDescription("Chance to become inoperative");
                break;
            case stat27:
                this.diplomacyBonus = 1;
                AddBonusDescription("plus 1 Diplomacy");
                break;
            case stat28:
                // TODO: No suprise bonus for creatures during attack
                AddBonusDescription("Cannot be suprised during creature attack");
                break;
            case stat29:
                // TODO: Ignore creature that attack with int
                AddBonusDescription("Ignore creatures that attack with intelligence");
                break;
            case stat30:
                this.diplomacyBonus = 1;
                this.intelligenceBonus = 1;
                AddBonusDescription("plus 1 Diplomacy");
                AddBonusDescription("plus 1 Intelligence");
                break;
            case stat31:
                // TODO: Ignore first abort mission action
                AddBonusDescription("Ignore first abort mission action");
                break;
            case stat32:
                // TODO: During Galactic game, reveal Planet secret
                AddBonusDescription("Can reveal Planetary Secret");
                break;
        }
        
    }
    
    private void AddBonusDescription(String s)
    {
        this.bonusDescriptions.add(s);
    }
    // Add mission bonus draw value to list
    private void AddMissionBonus(Mission.MissionType m, int n)
    {
            Map.Entry<Mission.MissionType,Integer> missionNameAndBonusValue = new AbstractMap.SimpleEntry<Mission.MissionType,Integer>(m,n);
            this.missionBonusDictionary.add(missionNameAndBonusValue);
    }
    // Enumerable values for stat values from .dat file
    // Used for switch statement in applying bonuses
    private enum StatType {
        stat1,
        stat2,
        stat3,
        stat4,
        stat5,
        stat6,
        stat7,
        stat8,
        stat9,
        stat10,
        stat11,
        stat12,
        stat13,
        stat14,
        stat15,
        stat16,
        stat17,
        stat18,
        stat19,
        stat20,
        stat21,
        stat22,
        stat23,
        stat24,
        stat25,
        stat26,
        stat27,
        stat28,
        stat29,
        stat30,
        stat31,
        stat32      
    }
    
    private boolean PromptUser()
    {        
        if (Game.instance().turn.GetGameRound() == this.specialCheckGameRoundValue && Game.instance().turn.phase == this.specialCheckGamePhase)
        {
            return this.specialHasBeenUsed;
        }
        
        this.specialCheckGameRoundValue = Game.instance().turn.GetGameRound();
        this.specialCheckGamePhase = Game.instance().turn.phase;
        
        String buttons[] = { "Yes", "No" };
        
        int returnValue = JOptionPane.showOptionDialog(null,
            "Would you like to use the " + this.name + "\n for " + this.assignment,
            "Possession Special",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            buttons,
            buttons[0]);

        //JOptionPane.showMessageDialog(null, "You choose: " + returnValue);
        
        if (returnValue == 0)
        {
            ActivateSpecial();
            return true;
        }

        this.specialHasBeenUsed = false;
        
        return false;
    }
    
    private void ActivateSpecial()
    {
        this.specialHasBeenUsed = true;
        
        if (this.removeItemOnceUsed)
            SingleUseItem();
        
        if (this.rollAfterUse)
            RollAfterUse();
        
    }
    
    private void SingleUseItem()
    {
        this.destroyed = true;
    }
    
    private void RollAfterUse()
    {
        int diceValue = Tables.DiceRoll();
        
        if (diceValue >= this.inoperativeRoll)
        {
            JOptionPane.showMessageDialog(null, this.name + " will be used, but it has become inoperative.");
            this.inoperative = true;            
        }
    }
}
