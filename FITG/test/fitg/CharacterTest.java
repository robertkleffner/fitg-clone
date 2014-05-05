/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fitg;

import java.util.List;
import java.util.ArrayList;
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
public class CharacterTest {
    
    public CharacterTest() {
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
     * Test of GetName method, of class Character.
     */
    @Test
    public void testGetName() {
        System.out.println("GetName");
        Character instance = new Character("name test", "image test", "title test", "race test", SIDE.REBEL, 1, 1, 1, 1, 1, 1, "homePlanet test", "special test");
        String expResult = "name test";
        String result = instance.GetName();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetImageFilename method, of class Character.
     */
    @Test
    public void testGetImageFilename() {
        System.out.println("GetImageFilename");
        Character instance = new Character("name test", "image test", "title test", "race test", SIDE.REBEL, 1, 1, 1, 1, 1, 1, "homePlanet test", "special test");
        String expResult = "image test";
        String result = instance.GetImageFilename();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetTitle method, of class Character.
     */
    @Test
    public void testGetTitle() {
        System.out.println("GetTitle");
        Character instance = new Character("name test", "image test", "title test", "race test", SIDE.REBEL, 1, 1, 1, 1, 1, 1, "homePlanet test", "special test");
        String expResult = "title test";
        String result = instance.GetTitle();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetRace method, of class Character.
     */
    @Test
    public void testGetRace() {
        System.out.println("GetRace");
        Character instance = new Character("name test", "image test", "title test", "race test", SIDE.REBEL, 1, 1, 1, 1, 1, 1, "homePlanet test", "special test");
        String expResult = "race test";
        String result = instance.GetRace();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetSide method, of class Character.
     */
    @Test
    public void testGetSide() {
        System.out.println("GetSide");
        Character instance = new Character("name test", "image test", "title test", "race test", SIDE.REBEL, 1, 1, 1, 1, 1, 1, "homePlanet test", "special test");
        SIDE expResult = SIDE.REBEL;
        SIDE result = instance.GetSide();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetCombatRating method, of class Character.
     */
    @Test
    public void testGetCombatRating() {
        System.out.println("GetCombatRating");
        Character instance = new Character("name test", "image test", "title test", "race test", SIDE.REBEL, 1, 1, 1, 1, 1, 1, "homePlanet test", "special test");
        int expResult = 1;
        int result = instance.GetCombatRating();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetEnduranceRating method, of class Character.
     */
    @Test
    public void testGetEnduranceRating() {
        System.out.println("GetEnduranceRating");
        Character instance = new Character("name test", "image test", "title test", "race test", SIDE.REBEL, 1, 1, 1, 1, 1, 1, "homePlanet test", "special test");
        int expResult = 1;
        int result = instance.GetEnduranceRating();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetIntelligenceRating method, of class Character.
     */
    @Test
    public void testGetIntelligenceRating() {
        System.out.println("GetIntelligenceRating");
        Character instance = new Character("name test", "image test", "title test", "race test", SIDE.REBEL, 1, 1, 1, 1, 1, 1, "homePlanet test", "special test");
        int expResult = 1;
        int result = instance.GetIntelligenceRating();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetLeadershipRating method, of class Character.
     */
    @Test
    public void testGetLeadershipRating() {
        System.out.println("GetLeadershipRating");
        String currentPlanetName = "";
        Character instance = new Character("name test", "image test", "title test", "race test", SIDE.REBEL, 1, 1, 1, 1, 1, 1, "homePlanet test", "special test");
        int expResult = 1;
        int result = instance.GetLeadershipRating();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetDiplomacyRating method, of class Character.
     */
    @Test
    public void testGetDiplomacyRating() {
        System.out.println("GetDiplomacyRating");
        Character instance = new Character("name test", "image test", "title test", "race test", SIDE.REBEL, 1, 1, 1, 1, 1, 1, "homePlanet test", "special test");
        int expResult = 1;
        int result = instance.GetDiplomacyRating();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetNavigationRating method, of class Character.
     */
    @Test
    public void testGetNavigationRating() {
        System.out.println("GetNavigationRating");
        Character instance = new Character("name test", "image test", "title test", "race test", SIDE.REBEL, 1, 1, 1, 1, 1, 1, "homePlanet test", "special test");
        int expResult = 1;
        int result = instance.GetNavigationRating();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetHomePlanet method, of class Character.
     */
    @Test
    public void testGetHomePlanet() {
        System.out.println("GetHomePlanet");
        Character instance = new Character("name test", "image test", "title test", "race test", SIDE.REBEL, 1, 1, 1, 1, 1, 1, "homePlanet test", "special test");
        String expResult = "homePlanet test";
        String result = instance.GetHomePlanet();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetIsWounded method, of class Character.
     */
    @Test
    public void testGetIsWounded() {
        System.out.println("GetIsWounded");
        Character instance = new Character("name test", "image test", "title test", "race test", SIDE.REBEL, 1, 1, 1, 1, 1, 1, "homePlanet test", "special test");
        boolean expResult = false;
        boolean result = instance.GetIsWounded();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetMaxWounds method, of class Character.
     */
    @Test
    public void testGetMaxWounds() {
        System.out.println("GetMaxWounds");
        Character instance = new Character("name test", "image test", "title test", "race test", SIDE.REBEL, 1, 1, 1, 1, 1, 1, "homePlanet test", "special test");
        int expResult = 1;
        int result = instance.GetMaxWounds();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetCurrentWounds method, of class Character.
     */
    @Test
    public void testGetCurrentWounds() {
        System.out.println("GetCurrentWounds");
        Character instance = new Character("name test", "image test", "title test", "race test", SIDE.REBEL, 1, 1, 1, 1, 1, 1, "homePlanet test", "special test");
        int expResult = 0;
        int result = instance.GetCurrentWounds();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetIsDead method, of class Character.
     */
    @Test
    public void testGetIsDead() {
        System.out.println("GetIsDead");
        Character instance = new Character("name test", "image test", "title test", "race test", SIDE.REBEL, 1, 1, 1, 1, 1, 1, "homePlanet test", "special test");
        boolean expResult = false;
        boolean result = instance.GetIsDead();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetIsDetected method, of class Character.
     */
    @Test
    public void testGetIsDetected() {
        System.out.println("GetIsDetected");
        Character instance = new Character("name test", "image test", "title test", "race test", SIDE.REBEL, 1, 1, 1, 1, 1, 1, "homePlanet test", "special test");
        boolean expResult = false;
        boolean result = instance.GetIsDetected();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetIsInPlay method, of class Character.
     */
    @Test
    public void testGetIsInPlay() {
        System.out.println("GetIsInPlay");
        Character instance = new Character("name test", "image test", "title test", "race test", SIDE.REBEL, 1, 1, 1, 1, 1, 1, "homePlanet test", "special test");
        boolean expResult = false;
        boolean result = instance.GetIsInPlay();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetFound method, of class Character.
     */
    @Test
    public void testGetFound() {
        System.out.println("GetFound");
        Character instance = new Character("name test", "image test", "title test", "race test", SIDE.REBEL, 1, 1, 1, 1, 1, 1, "homePlanet test", "special test");
        boolean expResult = false;
        boolean result = instance.GetFound();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetPossessionList method, of class Character.
     */
    @Test
    public void testGetPossessionList() {
        System.out.println("GetPossessionList");
        Character instance = new Character("name test", "image test", "title test", "race test", SIDE.REBEL, 1, 1, 1, 1, 1, 1, "homePlanet test", "special test");
        List expResult = new ArrayList();
        List result = instance.GetPossessionList();
        assertEquals(expResult.size(), result.size());
    }

    /**
     * Test of GetHasShip method, of class Character.
     */
    @Test
    public void testGetHasShip() {
        System.out.println("GetHasShip");
        Character instance = new Character("name test", "image test", "title test", "race test", SIDE.REBEL, 1, 1, 1, 1, 1, 1, "homePlanet test", "special test");
        boolean expResult = false;
        boolean result = instance.GetHasShip();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetIsCaptured method, of class Character.
     */
    @Test
    public void testGetIsCaptured() {
        System.out.println("GetIsCaptured");
        Character instance = new Character("name test", "image test", "title test", "race test", SIDE.REBEL, 1, 1, 1, 1, 1, 1, "homePlanet test", "special test");
        boolean expResult = false;
        boolean result = instance.GetIsCaptured();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetIsRoyalty method, of class Character.
     */
    @Test
    public void testGetIsRoyalty() {
        System.out.println("GetIsRoyalty");
        Character instance = new Character("name test", "image test", "title test", "race test", SIDE.REBEL, 1, 1, 1, 1, 1, 1, "homePlanet test", "special test");
        boolean expResult = false;
        boolean result = instance.GetIsRoyalty();
        assertEquals(expResult, result);
    }

    /**
     * Test of SetDead method, of class Character.
     */
    @Test
    public void testSetDead() {
        System.out.println("SetDead");
        boolean status = true;
        Character instance = new Character("name test", "image test", "title test", "race test", SIDE.REBEL, 1, 1, 1, 1, 1, 1, "homePlanet test", "special test");
        instance.SetDead(status);
        boolean expResult = true;
        boolean result = instance.GetIsDead();
        assertEquals(expResult, result);
    }

    /**
     * Test of SetDetected method, of class Character.
     */
    @Test
    public void testSetDetected() {
        System.out.println("SetDetected");
        boolean status = true;
        Character instance = new Character("name test", "image test", "title test", "race test", SIDE.REBEL, 1, 1, 1, 1, 1, 1, "homePlanet test", "special test");
        instance.SetDetected(status);
        boolean expResult = true;
        boolean result = instance.GetIsDetected();
        assertEquals(expResult, result);
    }

    /**
     * Test of SetInPlay method, of class Character.
     */
    @Test
    public void testSetInPlay() {
        System.out.println("SetInPlay");
        boolean status = true;
        Character instance = new Character("name test", "image test", "title test", "race test", SIDE.REBEL, 1, 1, 1, 1, 1, 1, "homePlanet test", "special test");
        instance.SetInPlay(status);
        boolean expResult = true;
        boolean result = instance.GetIsInPlay();
        assertEquals(expResult, result);
    }

    /**
     * Test of SetFound method, of class Character.
     */
    @Test
    public void testSetFound() {
        System.out.println("SetFound");
        boolean status = true;
        Character instance = new Character("name test", "image test", "title test", "race test", SIDE.REBEL, 1, 1, 1, 1, 1, 1, "homePlanet test", "special test");
        instance.SetFound(status);
        boolean expResult = true;
        boolean result = instance.GetFound();
        assertEquals(expResult, result);
    }

    /**
     * Test of SetIsCaptured method, of class Character.
     */
    @Test
    public void testSetIsCaptured() {
        System.out.println("SetIsCaptured");
        boolean status = true;
        Character instance = new Character("name test", "image test", "title test", "race test", SIDE.REBEL, 1, 1, 1, 1, 1, 1, "homePlanet test", "special test");
        instance.SetIsCaptured(status);
        boolean expResult = true;
        boolean result = instance.GetIsCaptured();
        assertEquals(expResult, result);
    }

    /**
     * Test of AddPossession method, of class Character.
     */
    @Test
    public void testAddPossession() {
        System.out.println("AddPossession");
        Possession newPossession = new Possession(Possession.PossessionType.Companion, "Test", "Test");
        Character instance = new Character("name test", "image test", "title test", "race test", SIDE.REBEL, 1, 1, 1, 1, 1, 1, "homePlanet test", "special test");
        instance.AddPossession(newPossession);
        List<Possession> expResult = instance.GetPossessionList();
        
        assertEquals(newPossession.GetName(), expResult.get(0).GetName());
        assertEquals(newPossession.GetPossessionType(), expResult.get(0).GetPossessionType());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of RemovePossessionByObject method, of class Character.
     */
    @Test
    public void testRemovePossessionByObject() {
        System.out.println("RemovePossessionByObject");
        Possession p = new Possession(Possession.PossessionType.Companion, "Test", "Test");
        Character instance = new Character("name test", "image test", "title test", "race test", SIDE.REBEL, 1, 1, 1, 1, 1, 1, "homePlanet test", "special test");
        instance.AddPossession(p);
        
        instance.RemovePossessionByObject(p);
        
        int expResult = 0;
        
        List<Possession> pList = instance.GetPossessionList();
        
        assertEquals(expResult, pList.size());
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of ApplyWoundtoCharacter method, of class Character.
     */
    @Test
    public void testApplyWoundtoCharacter() {
        System.out.println("ApplyWoundtoCharacter");
        int value = 1;
        Character instance = new Character("name test", "image test", "title test", "race test", SIDE.REBEL, 1, 2, 1, 1, 1, 1, "homePlanet test", "special test");
        boolean expResult = false;
        boolean result = instance.ApplyWoundtoCharacter(value);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of HealCharacter method, of class Character.
     */
    @Test
    public void testHealCharacter() {
        System.out.println("HealCharacter");
        Character instance = new Character("name test", "image test", "title test", "race test", SIDE.REBEL, 1, 1, 1, 1, 1, 1, "homePlanet test", "special test");
        int expResult = 1;
        
        instance.ApplyWoundtoCharacter(1);
        
        assertEquals(expResult, instance.GetCurrentWounds());
        
        instance.HealCharacter();
        
        expResult = 0;
        
        assertEquals(expResult, instance.GetCurrentWounds());
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}