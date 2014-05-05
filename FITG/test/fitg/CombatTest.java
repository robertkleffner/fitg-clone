/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fitg;

import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;


/**
 *
 * @author richardpark
 */
public class CombatTest {
    
    Parsers parseCharacters = new Parsers();
    //List<Character> characters = parseCharacters();
    
    public CombatTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of missionCombat method, of class Combat.
     */
    @Test
    public void testMissionCombat() {
        System.out.println("missionCombat");
        Stack missionStack = null;
        Character creature = null;
        Stack expResult = null;
        Stack result = Combat.missionCombat(missionStack, creature);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createParty method, of class Combat.
     */
    @Test
    public void testCreateParty() {
        
        Character testCharacter = new Character();
        Stack testStack = new Stack();
        //Entity testEntity = new Entity(EntityType.CHARACTER, SIDE.REBEL);
        
        //testCharacter.GetName() = "MadMax";
        testCharacter.side = SIDE.REBEL;
        
        testStack.AddToStack(testCharacter);

        List<Character> temp = new ArrayList();
        temp.add(testCharacter);
        
        System.out.println("createParty");
        
        List expResult = temp;
        List result = Combat.createParty(testStack);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of doCharacterCombat method, of class Combat.
     */
    @Test
    public void testDoCharacterCombat() {
        System.out.println("doCharacterCombat");
        List<Character> attackers = null;
        List<Character> defenders = null;
        Combat.CombatObjective objective = Combat.CombatObjective.KILL;
        
        int[] expResult = null;
        int[] result = Combat.doCharacterCombat(attackers, defenders, objective);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getDifferential method, of class Combat.
     */
    @Test
    public void testGetDifferential() {
        System.out.println("getDifferential");
        int expResult = 0;
        int result = Combat.getDifferential();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of assignWounds method, of class Combat.
     */
    @Test
    public void testAssignWounds() {
        System.out.println("assignWounds");
        int wounds = 0;
        List<Character> party = null;
        Stack role = null;
        Stack expResult = null;
        Stack result = Combat.assignWounds(wounds, party, role);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of activateDefenders method, of class Combat.
     */
    @Test
    public void testActivateDefenders() {
        System.out.println("activateDefenders");
        List<Character> defenders = null;
        List expResult = null;
        List result = Combat.activateDefenders(defenders);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of whoIsInParty method, of class Combat.
     */
    @Test
    public void testWhoIsInParty() {
        System.out.println("whoIsInParty");
        List<Character> party = null;
        String group = "";
        Combat.whoIsInParty(party, group);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of characterCombat method, of class Combat.
     */
    @Test
    public void testCharacterCombat() {
        System.out.println("characterCombat");
        Stack attacker = null;
        Stack defender = null;
        Combat.CombatType type = null;
        Combat.CombatObjective objective = null;
        Stack[] expResult = null;
        Stack[] result = Combat.characterCombat(attacker, defender, type, objective);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of militaryCombat method, of class Combat.
     */
    @Test
    public void testMilitaryCombat() {
        System.out.println("militaryCombat");
        Stack attacker = null;
        Stack defender = null;
        int[] expResult = null;
        int[] result = Combat.militaryCombat(attacker, defender);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}