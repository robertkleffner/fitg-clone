 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 11-5-2013 -  Laying Groundwork for combat class.  Current strategy is to
 *              implement character, military and space combat as methods.
 * 11-11-2013 - Found way to extract combat rating of characters from
 *              stacks.  Iterate through list and use 
 *              ((extendedClass) var).method() to access method of extended 
 *              class.
 * 11-11-2013 - Character combat is an int array that returns the number of
 *              wounds the attacker and defender receives.
 * 11-12-2013 - BEN: Added a dummy military combat class to use until a real
 *              one is fleshed out.
 * 11-17-2013 - Reworked combat to return a stack array.  Fixed so wounds 
 *              dealt by attacker get applied to defender; vice versa.
 * 12-2-2013  - Reworked combat methods.  Commented out out of date and
 *              irrelevant methods.
 */
package fitg;
import java.util.*;
/**
 *
 * @author richardpark
 */
public class Combat {
   
    private Entity activeForces;
    private Entity inactiveForces;
    private Boolean fight;
    private Boolean breakawayFailed;
    private Boolean fireFight;  // False = hand-to-hand
    private Boolean capture;    // False = kill combat
    private Boolean surprise;
    private int damageGiven;
    private int damageTaken;
    public static int ATTACKER = 0;
    public static int DEFENDER = 1;
    
    public enum Role{ATTACKER, DEFENDER};
    public enum CombatObjective {KILL, CAPTURE};
    public enum CombatType {HAND2HAND, FIREFIGHT};
    
    public static IOControl io = new IOControl();
    
    
    /* 11-14-2013 - RICH: Began work on mission Combat.
     *            - Will need a creature class. Should include number of 
     *              rounds combat will occur and if breakoff allowed.
     */
    /*
    public static Stack missionCombat(Stack missionStack, Character creature)
    {
        int CombatStrength = 0;
        
        for (Entity e : missionStack.GetEntities())
        {
            if (e.ET == EntityType.CHARACTER)
              CombatStrength += ((Character) e).GetCombatRating();
        }

        return missionStack;
    }
    
    // Parses characters from stack, puts them in list usable for combat.
    public static List<Character> createParty(Stack stack)
    {
        List<Character> party = new ArrayList();
        for (Entity e : stack.GetEntities())
        {
            // Entity must be a character, not captured and not dead to be in party.
            if (e.ET == EntityType.CHARACTER && ((Character) e).GetIsCaptured() == false && ((Character) e).GetIsDead() == false)
                party.add(((Character) e));
                System.out.println(((Character) e).GetName() + " joins the battle.");
        }
        return party;
    }
    
    // Returns wounds as a result of combat between attacking and defending party
    public static int[] doCharacterCombat(List<Character> attackers, List<Character> defenders, CombatObjective objective)
    {
        int differential;
        int columnShift = 0;
        int[] wounds = new int[2];
        int[] roll = new int[2];
        
        
        // Default intial values of 0
        int[] combatRating = new int[2];
        
        // Sum attack combat rating
        for (Character c : attackers)
        {
            combatRating[ATTACKER] += c.GetAttackRating();
        }
        System.out.println("----------------------------------");
        System.out.println("Attacker's Combat Rating: " + combatRating[ATTACKER]);
        System.out.println("----------------------------------");

        
        // Sum defense combat rating
        for (Character c : defenders)
        {
            combatRating[DEFENDER] += c.GetDefenseRating();
        }
        System.out.println("----------------------------------");
        System.out.println("Defender's Combat Rating: " + combatRating[DEFENDER]);
        System.out.println("----------------------------------");
        
        // Get differential
        differential = combatRating[ATTACKER] - combatRating[DEFENDER];
        System.out.println("Combat Differential: " + differential);
        
        System.out.println("----------------------------------");
        
        if (objective == CombatObjective.CAPTURE)
        {
            columnShift = -2;
        }
        
        roll[ATTACKER] = Tables.DiceRoll();
        System.out.println("Attacker rolls a " + roll[ATTACKER]);
        roll[DEFENDER] = Tables.DiceRoll();
        System.out.println("Defender rolls a " + roll[DEFENDER]);
        
        System.out.println("----------------------------------");
        
        wounds[ATTACKER] = Tables.CharacterCombatAttacker(differential, roll[0], columnShift);
        System.out.println("Attacker deals " + wounds[ATTACKER] + " damage");
        wounds[DEFENDER] = Tables.CharacterCombatDefender(differential, roll[1], columnShift);
        System.out.println("Defender deals " + wounds[DEFENDER] + " damage");
      
        System.out.println("----------------------------------");
        
        return wounds;
    }
    
    public static int getDifferential(Stack attacker, Stack defender, EntityType type)
    {
        
     int differential = 0;
     int attackRating = 0;
     int defendRating = 0;
     
     for (Entity e : attacker.GetEntities())
     {
         attackRating += ((MilitaryForce) e).GetEnvironStrength();
     }
         
     return differential;
    }
    
    public static Stack assignWounds(int wounds, List<Character> party, Stack role)
    {
        Stack modified = new Stack();
        int assign;
        
        // Assign wounds until wounds are gone.
        while (wounds > 0 && party.isEmpty() == false)
        {   
            System.out.println("----------------------------------");
            System.out.println("Assign wounds[" + wounds + "]: ");
            // See who is available to assign wounds.
            for (Entity e : role.GetEntities())
            {
                if (e.ET == EntityType.CHARACTER && ((Character) e).GetIsCaptured() == false && party.contains((Character) e))
                System.out.println("[" + party.indexOf(e) + "] " + ((Character) e).GetName() + " current wounds: " + ((Character) e).GetCurrentWounds());
            }
            System.out.println("----------------------------------");
            
            // apply wound to character using index 
            assign = io.getInt();
            ((Character) role.GetMember(assign)).ApplyWoundtoCharacter(1);
            System.out.println("Wound applied to " +((Character) role.GetMember(assign)).GetName());
            // wound applied, decrement.
            wounds--;
               
            // Dead characters removed from party
            for (Character c : party)    
            {
                if (c.GetIsDead() == true)
                {
                    System.out.println(c.GetName() + " has perished!");
                }
            }
        }
        
        // Change stack
        modified = role;
        
        return modified;
    }
    
    // Returns list of characters player wants to use as active defenders in this round of combat.
    public static List<Character> activateDefenders(List<Character> defenders)
    {
        System.out.println("Choose your defenders: ");
        for (Character c : defenders)
        {
            System.out.println("[" + defenders.indexOf(c) + "] " + c.GetName());
            System.out.println("Combat Rating: " + c.GetDefenseRating());
            System.out.println("Endurance: " + c.GetEnduranceRating());
        }
              
        return defenders;
    }

    public static void whoIsInParty(List<Character> party, String group)
    { 
        System.out.println("----------------------------------");
        System.out.println(group + " group: ");
        for (Character c : party)
            System.out.println(c.GetName());
        System.out.println("----------------------------------");
    }
    
    public static Boolean breakoff(List<Character> active, List<Character> inactive)
    {
        Boolean success = false;
        return success;
    }
    
    // Method to determine if there is a character in either Stack, for Character Combat.
    public static Boolean characterCombatCapable(Stack attacker, Stack defender)
    {
        Boolean a, d, both;
        a = false;
        d = false;
        both = false;
        
        for (Entity e : attacker.GetEntities())
        {
            if (e.ET == EntityType.CHARACTER)
                a = true;
        }
        
        for (Entity e : defender.GetEntities())
        {
            if (e.ET == EntityType.CHARACTER)
                d = true;
        }
        
        if (a == true && d == true)
            both = true;
        
        return both;
    }
    */
    
    public static int getCharacterCombatRating(Stack combatant, int role)
    {
        int combatStrength = 0;
        
        switch(role)
        {
            case 0:

                for (Entity e : combatant.GetEntities())
                {
                    if (e.ET == EntityType.CHARACTER && !((Character) e).GetIsDead() && !((Character) e).GetIsCaptured())
                    {
                        combatStrength += ((Character) e).GetAttackRating();
                    }
                }
                System.out.println(combatStrength);
                break;

            case 1:
                
                for (Entity e : combatant.GetEntities())
                {
                    if (e.ET == EntityType.CHARACTER && !((Character) e).GetIsDead() && !((Character) e).GetIsCaptured())
                    {
                        combatStrength += ((Character) e).GetDefenseRating();
                    }
                }
                System.out.println(combatStrength);
                break;
        }
        
        return combatStrength;
    }
    
    // Combat between attacker and defender stacks
    public static int[] characterCombat(Stack attacker, Stack defender)
    {   
        int[] wounds = new int[2];
        int[] combatRating = new int[2];
        int[] diceRoll = new int[2];
        int differential;
        
        combatRating[ATTACKER] = getCharacterCombatRating(attacker, ATTACKER);
        combatRating[DEFENDER] = getCharacterCombatRating(attacker, ATTACKER);
        
        differential = combatRating[ATTACKER] - combatRating[DEFENDER];
        
        diceRoll[ATTACKER] = Tables.DiceRoll();
        diceRoll[DEFENDER] = Tables.DiceRoll();
        
        wounds[DEFENDER] = Tables.CharacterCombatAttacker(differential, diceRoll[ATTACKER]);
        wounds[ATTACKER] = Tables.CharacterCombatDefender(differential, diceRoll[DEFENDER]);
        
    
        return wounds;
    }
    
    
    public static int[] characterCombat(Stack attacker, Stack defender, 
            CombatType type, CombatObjective objective)
    {   
        int[] wounds = new int[2];
        int[] combatRating = new int[2];
        int[] diceRoll = new int[2];
        int differential;
        
        combatRating[ATTACKER] = getCharacterCombatRating(attacker, ATTACKER);
        combatRating[DEFENDER] = getCharacterCombatRating(attacker, ATTACKER);
        
        differential = combatRating[ATTACKER] - combatRating[DEFENDER];
        
        diceRoll[ATTACKER] = Tables.DiceRoll();
        diceRoll[DEFENDER] = Tables.DiceRoll();
        
        wounds[ATTACKER] = Tables.CharacterCombatDefender(differential, diceRoll[DEFENDER]);      
        wounds[DEFENDER] = Tables.CharacterCombatAttacker(differential, diceRoll[ATTACKER]);
        
        if (type == type.FIREFIGHT)
        {
            wounds[ATTACKER] = wounds[DEFENDER] * 2;
            wounds[DEFENDER] = wounds[ATTACKER] * 2;
            
            return wounds;
        }
        
        else
            return wounds;
    }
    
    // Destroy the units in a given stack
    public static void destroy(Stack units)
    {
        for (Entity e : units.GetEntities())
        {
            try
            {
                Board.instance().stacks.RemoveEntity(e);
            }
            catch (Exception ex){}
        }
        //Board.instance().stacks.RemoveStack(units);
    }
    
    // Calculate environ strength for combat
    public static int getEnvironCombatRating(Stack militaryForce)
    {
        int environCombatRating = 0;
        
        for (Entity e : militaryForce.GetEntities())
        {
            if (e.ET == EntityType.MILITARY)
            {
                environCombatRating += ((MilitaryForce) e).GetEnvironStrength();
            }
        }
        
        return environCombatRating;
    }
    
    // Calculate environ strength for combat given a specified leader
    public static int getEnvironCombatRating(Stack militaryForce, Character leader)
    {
        int environCombatRating = 0;
        
        // TODO: Insert logic for a given leader being chosen
        
        
        return environCombatRating;
    }
    
    // Calculate environ strength for combat
    public static int getSpaceCombatRating(Stack militaryForce)
    {
        int spaceCombatRating = 0;
        
        for (Entity e : militaryForce.GetEntities())
        {
            if (e.ET == EntityType.MILITARY)
            {
                spaceCombatRating += ((MilitaryForce) e).GetSpaceStrength();
            }
        }
        
        return spaceCombatRating;
    }
    
    // Calculate environ strength for combat given a specified leader
    public static int getSpaceCombatRating(Stack militaryForce, Character leader)
    {
        int spaceCombatRating = 0;
        
        // TODO: Insert logic for a given leader being chosen
        
        return spaceCombatRating;
    }
    
    // Determines combat ratio, rounds in favor of defender as per rules.
    public static int[] getMilitaryCombatRatio(int attacker, int defender)
    {
        
        int[] ratio = new int[2];
        
        ratio[ATTACKER] = 1;
        ratio[DEFENDER] = 1;
        
        attacker = attacker * 5;
        defender = defender * 5;
        
        if (attacker > defender && defender != 0)
        {   
            while (attacker % defender != 0)
            {
                defender++;
            }
         
            ratio[ATTACKER] = attacker / defender;
            ratio[DEFENDER] = 1;
        }
        
        else if (defender > attacker && attacker != 0)
        {
            while(defender % attacker != 0)
            {
                defender++;
            }

            ratio[ATTACKER] = 1;
            ratio[DEFENDER] = defender / attacker;

        }
        
        else if (defender == attacker)
        {
            ratio[ATTACKER] = 1;
            ratio[DEFENDER] = 1;
        }
        
        return ratio;
        
    }
    
    //  Calculates Columnshift
    public static int[] militaryLeaderShift(Character attackingLeader, Character defendingLeader, int[] ratio)
    {
        int columnShift = 0;
        int difference = 0;
        int attacker = 0;
        int defender = 0;
        int[] militaryCombatShiftTableLeft = new int[11];
        int[] militaryCombatShiftTableRight = new int[11];
        
        // Left half of column.
        for (int i = 1; i < 7; i++)
        {
            militaryCombatShiftTableLeft[i-1] = 1;
            militaryCombatShiftTableRight[i-1] = 7 - i;      
        }
        
        // Right half of column.
        for (int i = 1; i < 7; i++)
        {
            militaryCombatShiftTableLeft[i+4] = i;
            militaryCombatShiftTableRight[i+4] = 1;            
        }
        
        // Is there an attacking leader? if not set value to 0;
        if (attackingLeader == null)
        {
            attacker = 0;
        }
        else
        {
            attacker = attackingLeader.GetLeadershipRating();
        }
        
        // Is there a defending leader?  If not set value to 0;
        if (defendingLeader == null)
        {
            defender = 0;
        }
        else
        {
            defender = defendingLeader.GetLeadershipRating();
        }

        difference = attacker - defender;

        columnShift = 6 + difference;
        
        if (columnShift < 0)
        {
            columnShift = 0;
        }
        
        if (columnShift > 10)
        {
            columnShift = 10;
        }
        
        ratio[0] = militaryCombatShiftTableLeft[columnShift];
        ratio[1] = militaryCombatShiftTableRight[columnShift];
        
        System.out.println("ratio: " + ratio[0] + " / " + ratio[1]);
        
        System.out.println("columnShift: " + columnShift);
        
        return ratio;
    }
            
    // Logic for conducting militaryCombat
    public static int[] militaryCombat(Stack attacker, Stack defender)
    {
        int [] wounds = new int[2];
        int [] combatStrength = new int[2];
        int [] ratio = new int[2];
        
        combatStrength[ATTACKER] = getEnvironCombatRating(attacker);
        combatStrength[DEFENDER] = getEnvironCombatRating(defender);
        
        ratio = getMilitaryCombatRatio(combatStrength[ATTACKER], combatStrength[DEFENDER]);
        
        System.out.println("Combat Ratio: " + ratio[ATTACKER] + " / " + ratio[DEFENDER]);
        
        wounds[ATTACKER] = Tables.MilitaryCombatAttacker(ratio[ATTACKER], ratio[DEFENDER]);
        wounds[DEFENDER] = Tables.MilitaryCombatDefender(ratio[ATTACKER], ratio[DEFENDER]);
         
        return wounds;
    }
       
    public static int[] militaryCombat(Stack attacker, Stack defender, 
            Character attackingLeader, Character defendingLeader)
    {
        int [] wounds = new int[2];
        int [] combatStrength = new int[2];
        int [] ratio = new int[2];
        int columnShift = 0;
        
        
        combatStrength[ATTACKER] = getEnvironCombatRating(attacker);
        combatStrength[DEFENDER] = getEnvironCombatRating(defender);
        
        ratio = getMilitaryCombatRatio(combatStrength[ATTACKER], combatStrength[DEFENDER]);
        
        System.out.println("Combat Ratio: " + ratio[ATTACKER] + " / " + ratio[DEFENDER]);
        
        ratio = militaryLeaderShift(attackingLeader, defendingLeader, ratio);
              
        System.out.println("Combat Ratio: " + ratio[ATTACKER] + " / " + ratio[DEFENDER]);
        
        wounds[ATTACKER] = Tables.MilitaryCombatAttacker(ratio[ATTACKER], ratio[DEFENDER]);
        wounds[DEFENDER] = Tables.MilitaryCombatDefender(ratio[ATTACKER], ratio[DEFENDER]);
           
        return wounds;   
    }
  
    // get attributes for squad to partcipate in combat
    public static int[] getSquadAttributes(Stack squad)
    {
        int [] stats = new int[2];
        int strength;
        int endurance;
        
        // Get military strength to calculate squad strength
        strength = getEnvironCombatRating(squad);
        
        stats[0] = Tables.SquadAttribute(strength, 'C');
        stats[1] = Tables.SquadAttribute(strength, 'E');
                
        return stats;
    }
    
    public static Stack createSquadCharacter(Stack squad)
    {
        Stack squadStack = new Stack();
        int[] stats;
        int strength;
        int endurance;
        SIDE side = SIDE.NEUTRAL;
        
        stats = getSquadAttributes(squad);
        
        strength = stats[0];
        endurance = stats[1];
        
        squadStack.SetSide(squad.GetSide());
        
        Character squadCharacter = new Character("Squad", "/images/Unknown.png", "Elite", "Troopers", 
                side, strength, endurance,0,0,0,0, "Barracks", "SAIGON72");
        
        squadStack.AddToStack(squadCharacter);
        
        return squadStack;
    }
    
    public static int[] squadCombat(Stack attacker, Stack defender)
    {
        int [] wounds = new int[2];
        int [] combatStrength = new int[2];
        int [] diceRoll = new int[2];
        int differential;
        int [] squadAttributes = new int[2];
        
        // Calculate squad
        squadAttributes = getSquadAttributes(attacker);
        
        combatStrength[ATTACKER] = squadAttributes[0];
        combatStrength[DEFENDER] = getCharacterCombatRating(defender, DEFENDER);
        
        differential = combatStrength[ATTACKER] - combatStrength[DEFENDER];
        
        diceRoll[ATTACKER] = Tables.DiceRoll();
        diceRoll[DEFENDER] = Tables.DiceRoll();
        
        wounds[DEFENDER] = Tables.CharacterCombatAttacker(differential, diceRoll[ATTACKER]);
        wounds[ATTACKER] = Tables.CharacterCombatDefender(differential, diceRoll[DEFENDER]);
        
        return wounds;
    }
}
 