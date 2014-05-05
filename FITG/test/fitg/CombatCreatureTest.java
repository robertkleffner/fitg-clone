/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fitg;

import fitg.CombatCreature.CreatureName;
import fitg.Environ.EnvironType;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author qeo
 */
public class CombatCreatureTest {
    
    public CombatCreatureTest() {
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
     * Test of getName method, of class CombatCreature.
     */
    @Test
    public void testConstructor()
    {
        System.out.println("testConstructor(): builds all possible creatures and checks that they were named correctly.");
        for( CreatureName name : CreatureName.values() )
        {
            CombatCreature instance = new CombatCreature( name, EnvironType.Air );
            String result = instance.getName();
            assertEquals( name.toString() , result);
        }
    }

    /**
     * Test of getStrength method, of class CombatCreature.
     */
    @Test
    public void testIrateLocal() {
        System.out.println("testConstructor(): tests a specific irate local race, Kayns");
        CombatCreature instance = new CombatCreature( CreatureName.Kayns, EnvironType.Wild );
        int expResult = 7;
        int result = instance.getStrength();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of getStrength method, of class CombatCreature.
     */
    @Test
    public void testGetStrength() {
        System.out.println("getStrength(): tests the simple getter for strength, using a standard creature");
        CombatCreature instance = new CombatCreature( CreatureName.Alweg, EnvironType.Air );
        int expResult = 8;
        int result = instance.getStrength();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of getIntelligence method, of class CombatCreature.
     */
    @Test
    public void testGetIntelligence() {
        System.out.println("getIntelligence(): tests the simple getter for intelligence, specific creature Drusers");
        CombatCreature instance = new CombatCreature( CreatureName.Drusers, EnvironType.Air );
        int expResult = 5;
        int result = instance.getIntelligence();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of isImmortal method, of class CombatCreature.
     */
    @Test
    public void testIsImmortal() {
        System.out.println("isImmortal(): tests the boolean flag for immortality, specific creature Drants");
        CombatCreature instance = new CombatCreature( CreatureName.Drants, EnvironType.Air );
        boolean expResult = true;
        boolean result = instance.isImmortal();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEndurance method, of class CombatCreature.
     */
    @Test
    public void testGetEndurance() {
        System.out.println("getEndurance(): tests the endurance getter, specific creature FrostMist");
        CombatCreature instance = new CombatCreature( CreatureName.FrostMist, EnvironType.Air );
        int expResult = 5;
        int result = instance.getEndurance();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCombatStat method, of class CombatCreature.
     */
    @Test
    public void testGetCombatStat() {
        System.out.println("getCombatStat(): tests the combat stat getter using Vorozion");
        CombatCreature instance = new CombatCreature( CreatureName.Vorozion, EnvironType.Air );
        int expResult = 6;
        int result = instance.getCombatStat();
        assertEquals(expResult, result);
    }

    /**
     * Test of isSurprise method, of class CombatCreature.
     */
    @Test
    public void testIsSurprise() {
        System.out.println("isSurprise(): tests if the race Leonus is a surprise attack");
        CombatCreature instance = new CombatCreature( CreatureName.Leonus, EnvironType.Air );
        boolean expResult = true;
        boolean result = instance.isSurprise();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSurpriseShift method, of class CombatCreature.
     */
    @Test
    public void testGetSurpriseShift() {
        System.out.println("getSurpriseShift(): gets the surprise column shift for Leonus");
        CombatCreature instance = new CombatCreature( CreatureName.Leonus, EnvironType.Air );
        instance.StartOfCombat();
        int expResult = 1;
        int result = instance.getSurpriseShift();
        assertEquals(expResult, result);
    }

    /**
     * Test of isINTCombat method, of class CombatCreature.
     */
    @Test
    public void testIsINTCombat() {
        System.out.println("isINTCombat(): test to confirm setting the isINTCombat flag");
        CombatCreature instance = new CombatCreature( CreatureName.Crunge, EnvironType.Air );
        boolean expResult = true;
        boolean result = instance.isINTCombat();
        assertEquals(expResult, result);
    }

    /**
     * Test of isFirefight method, of class CombatCreature.
     */
    @Test
    public void testIsFirefight() {
        System.out.println("isFirefight(): tests to confirm setting of the Firefight flag");
        CombatCreature instance = new CombatCreature( CreatureName.Vrialta, EnvironType.Air );
        boolean expResult = true;
        boolean result = instance.isFirefight();
        assertEquals(expResult, result);
    }

    /**
     * Test of isAttackWithSpecificColumn method, of class CombatCreature.
     */
    @Test
    public void testIsAttackWithSpecificColumn() {
        System.out.println("isAttackWithSpecificColumn(): checks flag of attacks with specific column");
        CombatCreature instance = new CombatCreature( CreatureName.Elilad, EnvironType.Air );
        boolean expResult = true;
        boolean result = instance.isAttackWithSpecificColumn();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSpecificColumn method, of class CombatCreature.
     */
    @Test
    public void testGetSpecificColumn() {
        System.out.println("getSpecificColumn(): returns a specific attack column, usually 0");
        CombatCreature instance = new CombatCreature( CreatureName.Elilad, EnvironType.Air );
        int expResult = 0;
        int result = instance.getSpecificColumn();
        assertEquals(expResult, result);
    }

    /**
     * Test of isCombatOver method, of class CombatCreature.
     */
    @Test
    public void testIsCombatOver() {
        System.out.println("isCombatOver(): tests if the combat over flag is correctly set after killing a creature");
        CombatCreature instance = new CombatCreature( CreatureName.Anons, EnvironType.Air );
        instance.applyWounds( 10 );
        boolean expResult = true;
        boolean result = instance.isCombatOver();
        assertEquals(expResult, result);
    }

    /**
     * Test of canBreakoff method, of class CombatCreature.
     */
    @Test
    public void testCanBreakoff() {
        System.out.println("canBreakoff(): confirms that the breakoff flag is working when you cannot break off");
        CombatCreature instance = new CombatCreature( CreatureName.Chardireeds, EnvironType.Air );
        instance.StartOfCombat();
        boolean expResult = false;
        boolean result = instance.canBreakoff();
        assertEquals(expResult, result);
    }

    /**
     * Test of getBreakoffRollAdjust method, of class CombatCreature.
     */
    @Test
    public void testGetBreakoffRollAdjust() {
        System.out.println("getBreakoffRollAdjust(): test the return of the breakoff roll adjust");
        CombatCreature instance = new CombatCreature( CreatureName.Gyrogos, EnvironType.Air );
        int expResult = 1;
        int result = instance.getBreakoffRollAdjust();
        assertEquals(expResult, result);
    }

    /**
     * Test of StartOfCombat method, of class CombatCreature.
     */
    @Test
    public void testStartOfCombat() {
        System.out.println("StartOfCombat(): tests the start of combat function");
        CombatCreature instance = new CombatCreature( CreatureName.Gyrogos, EnvironType.Air );
        instance.StartOfCombat();
        int expResult = 1;
        assertEquals( expResult, instance.getCombatRoundCounter() );
    }

    /**
     * Test of EndRound method, of class CombatCreature.
     */
    @Test
    public void testEndRound() {
        System.out.println("EndRound(), specific creature effects: Chantens");
        CombatCreature instance = new CombatCreature( CreatureName.Chantens, EnvironType.Air );
        instance.StartOfCombat();
        int goRounds = 5;
        int expResult = 8;
        while( instance.getCombatRoundCounter() < goRounds )
        {
            instance.StartRound();
            instance.EndRound();
        }
        assertEquals( expResult, instance.getStrength() );
    }

    /**
     * Test of gaveWounds method, of class CombatCreature.
     */
    @Test
    public void testGaveWounds() {
        System.out.println("gaveWounds(), specific creature effects: Dindin");
        CombatCreature instance = new CombatCreature( CreatureName.Dindin, EnvironType.Air );
        int expResult = 9;        
        int x = 1;
        while( x < 5 )
        {
            instance.gaveWounds();
            x++;
        }

        assertEquals( expResult, instance.getStrength() );
    }

    /**
     * Test of applyWounds method, of class CombatCreature.
     */
    @Test
    public void testApplyWounds() {
        System.out.println("applyWounds(), specific creature effects: Queemer");
        CombatCreature instance = new CombatCreature( CreatureName.Queemer, EnvironType.Air );
        instance.applyWounds( 5 );
        int expResult = 8
        assertEquals( expResult, instance.getStrength() );
    }
}