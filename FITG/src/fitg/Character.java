/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fitg;

import java.util.*;
import java.io.*;
import javax.sound.sampled.*;
import java.net.*;
/**
 *
 * @author Matt
 */
public class Character extends Entity {
    private String name;            // Character's name
    private String imageFile;       // filename for image
    private String title;           // Character's title
    private boolean isRoyalty;      // Royalty flag
    private String race;            // Character's race
    private int combat;             // combat rating
    private int combatBonus;        // combat bonuses from possessions
    private int defenseBonus;       // defensive combat bonus from possessions
    private int endurance;          // endurance rating
    private int enduranceBonus;     // endurance bonuses from possessions
    private int intelligence;       // intelligence rating
    private int intelligenceBonus;  // intelligence bonuses from possessions
    private int leadership;         // leadership rating
    private int leadershipBonus;    // leadership bonuses from posessions
    private int diplomacy;          // diplomacy rating
    private int diplomacyBonus;     // diplomacy bonuses from possessions
    private int navigation;         // navigation rating
    private int navigationBonus;    // navigation bonuses from possessions
    private String homePlanet;      // Character's home planet
    private String specials;        // special actions place holder
    private boolean wounded;        // is wounded flag
    private int currentWounds;      // current number of wounds
    private boolean dead;           // is dead flag
    private boolean inPlay;         // is in play flag
    private boolean isCaptured;     // is captured flag
    private boolean detected;       // is detected flag
    private boolean found;          // character has been found
    private boolean hasShip;        // character owns a spaceship flag
    private Mission.MissionType mission; // mision currently assigned to character
    private List<Possession> possessions; // obj list of possessions assigned to this character
    public JLabelCharacter label;
    private String currentPlanetName; // name of the planet that the character is on. Needed for determining royalty bonus
    private List<Map.Entry<Mission.MissionType,Integer>> missionBonusDictionary;
    private int hidingBonus;        // character's hiding bonus
    private List<String> specialDescriptions;   // List of character specials as strings
    private Ship ship;              // ship assigned to this character
    
    
    // Simple intializer for testing
    public Character()
    {
        super(EntityType.CHARACTER, SIDE.NEUTRAL);
        this.name = "TestChar";
        this.imageFile = "/images/Unknown.png"; // incase image doesn't exist
        this.wounded = false;
        this.dead = false;
        this.detected = false;
        this.inPlay = false;
        this.hasShip = false;
        this.possessions = new ArrayList();
        mission = Mission.MissionType.None;
    }
    // Initialize character attributes using data file
    public Character (String name, String image, String title, String race, SIDE side, int combatRating, int enduranceRating, int intelligenceRating,
            int leadershipRating, int diplomacyRating, int navigationRating, String homePlanet, String special)
    {
        super(EntityType.CHARACTER, side);
        this.name = name;
        this.imageFile = image;
        this.title = title;
        SetRoyaltyFlagBasedonTitle();
        this.race = race;
        this.combat = combatRating;
        this.endurance = enduranceRating;
        this.currentWounds = 0;
        this.intelligence = intelligenceRating;
        this.leadership = leadershipRating;
        this.diplomacy = diplomacyRating;
        this.navigation = navigationRating;
        this.homePlanet = homePlanet;
        this.specials = special;
        
        
        this.wounded = false;
        this.dead = false;
        this.detected = false;
        this.inPlay = false;
        this.found = false;
        this.hasShip = false;
        this.isCaptured = false;
        this.possessions = new ArrayList();
        this.specialDescriptions = new ArrayList();
        this.currentPlanetName = "";
        
        // Initialize bonus values
        this.combatBonus = 0;
        this.defenseBonus = 0;
        this.enduranceBonus = 0;
        this.intelligenceBonus = 0;
        this.leadershipBonus = 0;
        this.diplomacyBonus = 0;
        this.navigationBonus = 0;
        this.hidingBonus = 0;
        
        mission = Mission.MissionType.None;
        // Initialize mission bonus list
        BuildMissionBonusList();
        // Process character specials
        ApplyCharacterSpecial();
    }
    // ************************************************************************
    // Getters
    // ************************************************************************
    public String GetName() { return this.name; }
    public String GetImageFilename() { return this.imageFile; }
    public String GetTitle() { return this.title; }
    public String GetRace() { return this.race; }
    
    // Return base combat rating minus the current level wounds
    public int GetCombatRating() 
    { 
        CalculateAttributeBonuses();
        
        // Add combat rating and current bonuses but subtract off points for
        // current level of wounds. Rating can not go below zero
        int n = this.combat - this.currentWounds + this.combatBonus; 
        if (n < 0)
        {
            n = 0;
        }
        return n;
    }
    // Combat rating when characer is attacking 
    // TODO: Calculate bonuses from possessions that only apply
    // when attacking
    public int GetAttackRating() 
    { 
        CalculateAttributeBonuses();
        // Add combat rating and current bonuses but subtract off points for
        // current level of wounds. Rating can not go below zero
        int n = this.combat - this.currentWounds; 
        if (n < 0)
        {
            n = 0;
        }
        return n;
    }
    // Combat rating when character is defending
    // TODO: Calculate bonuses from possessions that only apply
    // when defending
    public int GetDefenseRating() 
    { 
        CalculateAttributeBonuses();
        // Add combat rating and current bonuses but subtract off points for
        // current level of wounds. Rating can not go below zero
        int n = this.combat - this.currentWounds + this.defenseBonus; 
        if (n < 0)
        {
            n = 0;
        }
        return n;
    }
        
    public int GetEnduranceRating() { CalculateAttributeBonuses(); return this.endurance + this.enduranceBonus;}
    public int GetIntelligenceRating() { CalculateAttributeBonuses(); return this.intelligence + this.intelligenceBonus;}
    public int GetLeadershipRating() 
    { 
        CalculateAttributeBonuses();
        // Apply royalty bonus to leadership rating if this character is royalty and
        // fighting on their home planet
        if (currentPlanetName.equals(this.homePlanet) && this.isRoyalty)
            return this.leadership + this.leadershipBonus + 1;
        
        return this.leadership + this.leadershipBonus;
    }
    public int GetDiplomacyRating() { CalculateAttributeBonuses(); return this.diplomacy + this.diplomacyBonus;}
    public int GetNavigationRating() { CalculateAttributeBonuses(); return this.navigation + this.navigationBonus;}
    public int GetEvasionRating()
    {
        int n = 0;
        
        if (this.hasShip)
        {
            for (Possession p : this.possessions)
            {
                if (p.GetPossessionType() == Possession.PossessionType.Ship)
                {
                    Ship s = (Ship)p;
                    n = s.GetManeuver(); 
                    break;
                }
            }
            CalculateAttributeBonuses();
            n += this.navigation + this.navigationBonus;
        }
        
        return n;
    }
    public String GetHomePlanet() { return this.homePlanet;}
    public boolean GetIsWounded() { return this.wounded;}
    public int GetMaxWounds() { return this.endurance;}
    public int GetCurrentWounds() { return this.currentWounds;}
    public boolean GetIsDead() { return this.dead;}
    public boolean GetIsDetected() { return this.detected;}
    public boolean GetIsInPlay() { return this.inPlay;}
    public boolean GetFound() { return this.found; }
    // Get the complete list of possessions owned by this character
    public List<Possession> GetPossessionList() { return this.possessions; }
    public boolean GetHasShip() { return this.hasShip;}
    public Ship GetShip() { return this.ship; }
    public boolean GetIsCaptured() { return this.isCaptured;}
    public boolean GetIsRoyalty() { return this.isRoyalty; }
    public Mission.MissionType GetCurrentMissionAssignment() { return this.mission; }
    public int GetHidingBonus() { return this.hidingBonus; }
    public List<String> GetSpecialDescriptions() 
    { 
        if (this.specialDescriptions.isEmpty())
            this.specialDescriptions.add("None");
        
        return this.specialDescriptions; 
    }
    
    
    // ************************************************************************
    // Setters
    // ************************************************************************
    public void SetDead(boolean status) { this.dead = status; SoundEffect.SCREAM.play(); }
    public void SetDetected(boolean status) { this.detected = status;}
    public void SetInPlay(boolean status) { this.inPlay = status;}
    public void SetFound(boolean status) { this.found = status;}
    public void SetIsCaptured(boolean status) { this.isCaptured = status;}
    public void SetCurrentMission(Mission.MissionType m) { this.mission = m; }
    public void SetCurrentPlanetName(String planetName) { this.currentPlanetName = planetName; }
    
    
    // ************************************************************************
    // Public Class Methods
    // ************************************************************************
    
    // GetMissionBonusDraws will return the total number of bonus draws for the
    // mission type. Bonus draw calculation will combine character attributes and
    // current active possessions.
    public int GetMissionBonusDraws()
    {
        if (this.mission == Mission.MissionType.None)
            return 0;
        
        int bonusDraws = 0;
        
        // Calculate bonus draws from character specials
        for (Map.Entry<Mission.MissionType,Integer> e : this.missionBonusDictionary)
        {
            if (e.getKey() == this.mission)
            {
                bonusDraws = e.getValue();
            }
        }
        
        // Calculate bonus draws from possessions
        for (Possession p : this.possessions)
        {
            if (p.GetInPlay() && p.GetIsInoperative() == false && p.GetIsDestroyed() == false)
            {
                bonusDraws += p.GetMissionBonusDraws(this.mission);
            }
            
        }

        return bonusDraws;
    }
    // Add a new possession object to this character
    public void AddPossession(Possession newPossession)
    {
        // Verify possession object is not null
        if (newPossession == null)
            return;
        
        // Set character assignment name in possession object
        newPossession.SetCharacterAssignment(this.name);
        
        // Set possession to be in play
        newPossession.SetInPlay(true);
        
        // Check if new possession is a ship - set hasShip flag true if yes
        if (newPossession.GetPossessionType() == Possession.PossessionType.Ship)
        {
            this.hasShip = true;
            this.ship = (Ship)newPossession;
        }
        
        // Add possession to list
        this.possessions.add(newPossession);
            
        // Add bonuses from possession to character attributes
        //CalculateAttributeBonuses();
    }
    // Remove a possession from this character by referencing the possession object
    public void RemovePossessionByObject(Possession p)
    {
        if (p.GetPossessionType() == Possession.PossessionType.Ship)
        {
            this.hasShip = false;
            this.ship = null;
            return;
        }
        
        // Check that the character has the possession
        if (this.possessions.contains(p))
        {
            // Set possession in play value to false
            p.SetInPlay(false);
            // clear character assignment name
            p.SetCharacterAssignment(null);
            // Remove possession object from possession list
            this.possessions.remove(p);
        }
        
        // Recalculate attribute bonuses
        //CalculateAttributeBonuses();
    }
    // Apply a number of wounds to a character. If the number of wounds is
    // greater or equal to the maximum number of wounds that a character can 
    // sustain, the character will be flagged as dead.
    // Method returns true/false on result:
    // True = character has died
    // False = character is not dead
    public boolean ApplyWoundtoCharacter(int value)
    {
        this.currentWounds += value;
        this.wounded = true;
        if (this.currentWounds >= this.endurance)
        {
            this.dead = true;
            SoundEffect.SCREAM.play();
            Board.instance().stacks.KillCharacterByName(name);
            return true;
        }
        
        return false;
    }
    // Heal a character by reseting wound count to zero
    public void HealCharacter()
    {
        this.wounded = false;
        this.currentWounds = 0;        
    }

    //  Added for testing purposes
    public void PrintChar()
    {
        System.out.println("-----------------------------");
        System.out.println("Name: " + this.name);
        System.out.println("ImageFile: " + this.imageFile);
        System.out.println("Title: " + this.title);
        System.out.println("Race: " + this.race);
        System.out.println("Side: " + this.side);
        System.out.println("Combat Rating: " + this.combat);
        System.out.println("Endurance Rating: " + this.endurance);
        System.out.println("Current Woudns: " + this.currentWounds);
        System.out.println("Intelligence: " + this.intelligence);
        System.out.println("Leadership: " + this.leadership);
        System.out.println("Diplomacy: " + this.diplomacy);
        System.out.println("Navigation: " + this.navigation);
        System.out.println("Home: " + this.homePlanet);
        System.out.println("Specials?: " + this.specials);
        System.out.println("-----------------------------");
    }
    
    
    
    // ************************************************************************
    // Private Class Methods
    // ************************************************************************
    
    // Calculate all aatribute bonuses from currently in play possessions
    // Bonuses are reset at the start of this method. Allows for dynamic 
    // recalculation of bonuses to account for new possessions getting added
    // to the character and possessions becoming inactive during play
    private void CalculateAttributeBonuses()
    {
        // Reset bonus values to zero
        this.combatBonus = 0;
        this.defenseBonus = 0;
        this.enduranceBonus = 0;
        this.intelligenceBonus = 0;
        this.leadershipBonus = 0;
        this.diplomacyBonus = 0;
        this.navigationBonus = 0;
        // add up each bonus value for each possession
        for (Possession p : this.possessions)
        {
            if (p.GetInPlay() && p.GetIsInoperative() == false && p.GetIsDestroyed() == false)
            {
                this.combatBonus += p.GetCombatBonus();
                this.defenseBonus += p.GetDefenseCombatBonus();
                this.enduranceBonus += p.GetEnduranceBonus();
                this.intelligenceBonus += p.GetIntelligenceBonus();
                this.leadershipBonus += p.GetLeadershipBonus();
                this.diplomacyBonus += p.GetDiplomacyBonus();
                this.navigationBonus += p.GetNavigationBonus();          
            }
        }
    }
    // Parse character title to determine if this character is royalty
    private void SetRoyaltyFlagBasedonTitle()
    {
        this.isRoyalty = false;
        
        // Look for Prince or Princess in character's title
        if (this.title.contains("Prince") || this.title.contains("Princess"))
            this.isRoyalty = true;        
    }
    
    // Using a key, value pair list to store bonus draw values for the associated mmission type.
    // This method will build the initial list, setting the bonus value to zero.
    private void BuildMissionBonusList()
    {
        this.missionBonusDictionary = new ArrayList();
        
        for (Mission.MissionType m : Mission.MissionType.values())
        {
            Map.Entry<Mission.MissionType,Integer> missionNameAndBonusValue = new AbstractMap.SimpleEntry<Mission.MissionType,Integer>(m,0);
            this.missionBonusDictionary.add(missionNameAndBonusValue);
        }
    }
    
    @Override 
    public String toString(){
        return name;
    }
    
    private void ApplyCharacterSpecial()
    {        
        if (this.specials.equals("RI"))
        {
            AddMissionBonus(Mission.MissionType.GatherInfo, 1);
        }
        else if (this.specials.equals("N"))
        {
            // No bonuses
        }
        else if (this.specials.equals("NIAA"))
        {
            AddMissionBonus(Mission.MissionType.GatherInfo, 1);
            AddMissionBonus(Mission.MissionType.Assassination, 2);
        }
        else if (this.specials.equals("RBAA"))
        {
            AddMissionBonus(Mission.MissionType.StartRebelCamp, 2);
        }
        else if (this.specials.equals("R&"))
        {
            // TODO: Unknown - character card not in image
        }
        else if (this.specials.equals("NTT"))
        {
            // TODO: Unknown - character card not in image
        }
        else if (this.specials.equals("NCCE"))
        {
            AddMissionBonus(Mission.MissionType.Coup, 2);
            AddMissionBonus(Mission.MissionType.Sovereign, 2);
        }
        else if (this.specials.equals("NA"))
        {
            AddMissionBonus(Mission.MissionType.Assassination, 1);
        }
        else if (this.specials.equals("NTPP%"))
        {
            AddMissionBonus(Mission.MissionType.SubvertTroops, 1);
            AddMissionBonus(Mission.MissionType.GainPossessions, 2);
            
            // TODO: add startship "Solar Merchant"
            
        }
        else if (this.specials.equals("NSS"))
        {
            AddMissionBonus(Mission.MissionType.Sabotage, 2);
        }
        else if (this.specials.equals("NZ"))
        {
            // TODO: Plus 1 to all stats except endurance when with Zina Adora
        }
        else if (this.specials.equals("NIId"))
        {
            AddMissionBonus(Mission.MissionType.GatherInfo, 2);
            
            // TODO: Can heal
        }
        else if (this.specials.equals("N"))
        {
            // TODO: Unknown - character card not in image
        }
        else if (this.specials.equals("Nh*"))
        {
            this.hidingBonus = 1;
            
            // TODO: add starship "Planetary Privateer
        }
        else if (this.specials.equals("R"))
        {
            // No bonus
        }
        else if (this.specials.equals("Nrs"))
        {
            // TODO: Can repair damaged ships/possessions
            
            // TODO: Ingore creature attacks by Imperial Sentries
        }
        else if (this.specials.equals("Ncp"))
        {
            // TODO: Ignore first creature attacks in special or wild environs
            
            // TODO: Can reveal planet secret
        }
        else if (this.specials.equals("Ni"))
        {
            // TODO: Ignore irate locals attacks
        }
        else if (this.specials.equals("NCB"))
        {
            // TODO: Unknown - character card not in image
        }
        else if (this.specials.equals("NAAA"))
        {
            // TODO: Unknown - character card not in image
        }
        else if (this.specials.equals("RAACC"))
        {
            AddMissionBonus(Mission.MissionType.Assassination, 1);
            AddMissionBonus(Mission.MissionType.Coup, 1);
        }
        else if (this.specials.equals("RBGI"))
        {
            AddMissionBonus(Mission.MissionType.GatherInfo, 1);
        }
        else if (this.specials.equals("RBASE"))
        {
            AddMissionBonus(Mission.MissionType.Assassination, 1);
            AddMissionBonus(Mission.MissionType.Sabotage, 1);
            AddMissionBonus(Mission.MissionType.Sovereign, 1);
        }
        else if (this.specials.equals("RBC "))
        {
            // TODO: Unknown - character card not in image
        }
        else if (this.specials.equals("RBCRBBBE"))
        {
            AddMissionBonus(Mission.MissionType.Coup, 1);
            AddMissionBonus(Mission.MissionType.Sovereign, 3);
        }
        else if (this.specials.equals("RBA"))
        {
            AddMissionBonus(Mission.MissionType.Assassination, 1);
        }
        else if (this.specials.equals("RBBC"))
        {
            // TODO: Unknown - character card not in image
        }
        else if (this.specials.equals("RBBIRBCRBBE"))
        {
            AddMissionBonus(Mission.MissionType.GatherInfo, 2);
            AddMissionBonus(Mission.MissionType.Sabotage, 1);
            AddMissionBonus(Mission.MissionType.Sovereign, 1);
        }
        else if (this.specials.equals("RBBS"))
        {
            AddMissionBonus(Mission.MissionType.Sabotage, 2);
        }
        else if (this.specials.equals("R"))
        {
            // No bonus
        }
        else if (this.specials.equals("RBS"))
        {
            AddMissionBonus(Mission.MissionType.Sabotage, 1);
        }
    }
    
    // Add mission bonus draw value to list
    private void AddMissionBonus(Mission.MissionType m, int n)
    {
        String desc = m.toString() + ": " + n;
        this.specialDescriptions.add(desc);
        for (Map.Entry<Mission.MissionType,Integer> e : this.missionBonusDictionary)
        {
            if (e.getKey() == m)
            {
                e.setValue(n);
                return;
            }
        }
    }
    

}
