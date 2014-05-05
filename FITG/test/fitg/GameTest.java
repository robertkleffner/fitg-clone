/*
 * To change this template, choose Tools | Templates
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
 * @author Corey
 */
public class GameTest {
    
    public GameTest() {
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
     * Test of RunGame method, of class Game.
     */
    @Test
    
    public void testSetupGame()
    {
        Game instance = new Game();
        instance.SetupGame(0);
        assertEquals(instance.gameType, GAMETYPE.STARSYSTEM);
    }
    
    public void testRunGame() {
        System.out.println("RunGame");
        Game instance = new Game();
        instance.RunGame();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of GetCharacter method, of class Game.
     */
    @Test
    public void testGetCharacter() {
        System.out.println("GetCharacter");
        String name = "";
        Game instance = new Game();
        Character expResult = null;
        Character result = instance.GetCharacter(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of GetPossession method, of class Game.
     */
    @Test
    public void testGetPossession() {
        System.out.println("GetPossession");
        String name = "";
        Game instance = new Game();
        Possession expResult = null;
        Possession result = instance.GetPossession(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of SetVictoryPoints method, of class Game.
     */
    @Test
    public void testSetVictoryPoints() {
        System.out.println("SetVictoryPoints");
        int vp = 0;
        Game instance = new Game();
        instance.SetVictoryPoints(vp);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of SetMaxTurns method, of class Game.
     */
    @Test
    public void testSetMaxTurns() {
        System.out.println("SetMaxTurns");
        int turns = 0;
        Game instance = new Game();
        instance.SetMaxTurns(turns);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}