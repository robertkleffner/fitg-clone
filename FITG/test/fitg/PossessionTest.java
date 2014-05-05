/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fitg;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Matt
 */
public class PossessionTest {
    
    public PossessionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of GetName method, of class Possession.
     */
    @Test
    public void testGetName() {
        System.out.println("GetName");
        Possession instance = new Possession(Possession.PossessionType.Companion, "Test", "Test");
        String expResult = "Test";
        String result = instance.GetName();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetPossessionType method, of class Possession.
     */
    @Test
    public void testGetPossessionType_0args() {
        System.out.println("GetPossessionType");
        Possession instance = new Possession(Possession.PossessionType.Companion, "Test", "Test");
        Possession.PossessionType expResult = Possession.PossessionType.Companion;
        Possession.PossessionType result = instance.GetPossessionType();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetCharacterAssignment method, of class Possession.
     */
    @Test
    public void testGetCharacterAssignment() {
        System.out.println("GetCharacterAssignment");
        Possession instance = new Possession(Possession.PossessionType.Companion, "Test", "Test");
        String expResult = null;
        String result = instance.GetCharacterAssignment();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetInPlay method, of class Possession.
     */
    @Test
    public void testGetInPlay() {
        System.out.println("GetInPlay");
        Possession instance = new Possession(Possession.PossessionType.Companion, "Test", "Test");
        boolean expResult = false;
        boolean result = instance.GetInPlay();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetIsDisabled method, of class Possession.
     */
    @Test
    public void testGetIsDisabled() {
        System.out.println("GetIsInoperative");
        Possession instance = new Possession(Possession.PossessionType.Companion, "Test", "Test");
        boolean expResult = false;
        boolean result = instance.GetIsInoperative();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetCombatBonus method, of class Possession.
     */
    @Test
    public void testGetCombatBonus() {
        System.out.println("GetCombatBonus");
        Possession instance = new Possession(Possession.PossessionType.Companion, "Test", "Test");
        int expResult = 0;
        int result = instance.GetCombatBonus();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetDefenseCombatBonus method, of class Possession.
     */
    @Test
    public void testGetDefenseCombatBonus() {
        System.out.println("GetDefenseCombatBonus");
        Possession instance = new Possession(Possession.PossessionType.Companion, "Test", "Test");
        int expResult = 0;
        int result = instance.GetDefenseCombatBonus();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetEnduranceBonus method, of class Possession.
     */
    @Test
    public void testGetEnduranceBonus() {
        System.out.println("GetEnduranceBonus");
        Possession instance = new Possession(Possession.PossessionType.Companion, "Test", "Test");
        int expResult = 0;
        int result = instance.GetEnduranceBonus();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetIntelligenceBonus method, of class Possession.
     */
    @Test
    public void testGetIntelligenceBonus() {
        System.out.println("GetIntelligenceBonus");
        Possession instance = new Possession(Possession.PossessionType.Companion, "Test", "Test");
        int expResult = 0;
        int result = instance.GetIntelligenceBonus();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetLeadershipBonus method, of class Possession.
     */
    @Test
    public void testGetLeadershipBonus() {
        System.out.println("GetLeadershipBonus");
        Possession instance = new Possession(Possession.PossessionType.Companion, "Test", "Test");
        int expResult = 0;
        int result = instance.GetLeadershipBonus();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetDiplomacyBonus method, of class Possession.
     */
    @Test
    public void testGetDiplomacyBonus() {
        System.out.println("GetDiplomacyBonus");
        Possession instance = new Possession(Possession.PossessionType.Companion, "Test", "Test");
        int expResult = 0;
        int result = instance.GetDiplomacyBonus();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetNavigationBonus method, of class Possession.
     */
    @Test
    public void testGetNavigationBonus() {
        System.out.println("GetNavigationBonus");
        Possession instance = new Possession(Possession.PossessionType.Companion, "Test", "Test");
        int expResult = 0;
        int result = instance.GetNavigationBonus();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetHidingBonus method, of class Possession.
     */
    @Test
    public void testGetHidingBonus() {
        System.out.println("GetHidingBonus");
        Possession instance = new Possession(Possession.PossessionType.Companion, "Test", "Test");
        int expResult = 0;
        int result = instance.GetHidingBonus();
        assertEquals(expResult, result);
    }

    /**
     * Test of SetCharacterAssignment method, of class Possession.
     */
    @Test
    public void testSetCharacterAssignment() {
        System.out.println("SetCharacterAssignment");
        String characterName = "Test";
        Possession instance = new Possession(Possession.PossessionType.Companion, "Test", "Test");
        instance.SetCharacterAssignment(characterName);
        String result = instance.GetName();
        assertEquals(result, characterName);
    }

    /**
     * Test of SetInPlay method, of class Possession.
     */
    @Test
    public void testSetInPlay() {
        System.out.println("SetInPlay");
        boolean status = true;
        Possession instance = new Possession(Possession.PossessionType.Companion, "Test", "Test");
        instance.SetInPlay(status);
        boolean result = instance.GetInPlay();
        assertEquals(result, status);
    }

    /**
     * Test of SetIsDisabled method, of class Possession.
     */
    @Test
    public void testSetIsDisabled() {
        System.out.println("SetIsDisabled");
        boolean status = true;
        Possession instance = new Possession(Possession.PossessionType.Companion, "Test", "Test");
        instance.SetInoperative(status);
        boolean result = instance.GetIsInoperative();
        assertEquals(result, status);
    }

    /**
     * Test of SetPossessionType method, of class Possession.
     */
    @Test
    public void testSetPossessionType() {
        System.out.println("SetPossessionType");
        Possession.PossessionType type = Possession.PossessionType.Object;
        Possession instance = new Possession(Possession.PossessionType.Companion, "Test", "Test");
        instance.SetPossessionType(type);
        Possession.PossessionType result = instance.GetPossessionType();
        assertEquals(result, type);
    }

    /**
     * Test of SetCombatBonus method, of class Possession.
     */
    @Test
    public void testSetCombatBonus() {
        System.out.println("SetCombatBonus");
        int combatBonus = 1;
        Possession instance = new Possession(Possession.PossessionType.Companion, "Test", "Test");
        instance.SetCombatBonus(combatBonus);
        int result = instance.GetCombatBonus();
        assertEquals(combatBonus, result);
    }

    /**
     * Test of SetEnduranceBonus method, of class Possession.
     */
    @Test
    public void testSetEnduranceBonus() {
        System.out.println("SetEnduranceBonus");
        int enduranceBonus = 1;
        Possession instance = new Possession(Possession.PossessionType.Companion, "Test", "Test");
        instance.SetEnduranceBonus(enduranceBonus);
        int result = instance.GetEnduranceBonus();
        assertEquals(enduranceBonus, result);
    }

    /**
     * Test of SetIntelligenceBonus method, of class Possession.
     */
    @Test
    public void testSetIntelligenceBonus() {
        System.out.println("SetIntelligenceBonus");
        int intelligenceBonus = 1;
        Possession instance = new Possession(Possession.PossessionType.Companion, "Test", "Test");
        instance.SetIntelligenceBonus(intelligenceBonus);
        int result = instance.GetIntelligenceBonus();
        assertEquals(intelligenceBonus, result);
    }

    /**
     * Test of SetLeadershipBonus method, of class Possession.
     */
    @Test
    public void testSetLeadershipBonus() {
        System.out.println("SetLeadershipBonus");
        int leadershipBonus = 1;
        Possession instance = new Possession(Possession.PossessionType.Companion, "Test", "Test");
        instance.SetLeadershipBonus(leadershipBonus);
        int result = instance.GetLeadershipBonus();
        assertEquals(result, leadershipBonus);
    }

    /**
     * Test of SetDiplomacyBonus method, of class Possession.
     */
    @Test
    public void testSetDiplomacyBonus() {
        System.out.println("SetDiplomacyBonus");
        int diplomacyBonus = 1;
        Possession instance = new Possession(Possession.PossessionType.Companion, "Test", "Test");
        instance.SetDiplomacyBonus(diplomacyBonus);
        int result = instance.GetDiplomacyBonus();
        assertEquals(diplomacyBonus, result);
    }

    /**
     * Test of SetNavigationBonus method, of class Possession.
     */
    @Test
    public void testSetNavigationBonus() {
        System.out.println("SetNavigationBonus");
        int navigationBonus = 0;
        Possession instance = new Possession(Possession.PossessionType.Companion, "Test", "Test");
        instance.SetNavigationBonus(navigationBonus);
        int result = instance.GetNavigationBonus();
        assertEquals(result, navigationBonus);
    }

    /**
     * Test of AddPossessionStat method, of class Possession.
     */
    @Test
    public void testAddPossessionStat_Stat1() {
        System.out.println("AddPossessionStat");
        String item = "stat1";
        Possession instance = new Possession(Possession.PossessionType.Companion, "Test", "Test");
        instance.AddPossessionStat(item);
        int expResult = 1;
        int result = instance.GetCombatBonus();
        assertEquals(expResult, result);

    }
}
