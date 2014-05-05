/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fitg;

import fitg.graphics.ArcButton;
import java.util.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Trevor Marquis
 * Created: 11/28/2013
 * Last Edited by: Trevor Marquis
 * Date Last Edited: 12/2/2013
 */
public class BoardTest {
    
    public BoardTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of instance method, of class Board.
     */
    @Test
    public void testInstance() {
        System.out.println("instance");
        //Board myBoard = Board.instance();
        //Board expResult = null;
        Board result = Board.instance();
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of GetProvinceByID method, of class Board.
     */
    @Test
    public void testGetProvinceByID() {
        System.out.println("GetProvinceByID");
       // Board instance = new Board();
        //int provinceID = 0;
        // Board instance = null;
        Province expResult = null;
        Province result = instance.GetProvinceByID(provinceID);
        //Province result = instance.GetProvinceByID(provinceID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of GetProvinces method, of class Board.
     */
    @Test
    public void testGetProvinces() {
        System.out.println("GetProvinces");
        //Board instance = null;
        //Board instance = new Board();
        List expResult = null;
        List result = instance.GetProvinces();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of GetProvinceObj method, of class Board.
     */
    @Test
    public void testGetProvinceObj() {
        System.out.println("GetProvinceObj");
        int provID = 0;
        //Board instance = null;
        //Board instance = new Board();
        Province expResult = null;
        Province result = instance.GetProvinceObj(provID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of PrintAll method, of class Board.
     */
    @Test
    public void testPrintAll() {
        System.out.println("PrintAll");
        //Board instance = null;
        //Board instance = new Board();
        instance.PrintAll();
        // TODO review the generated test code and remove the default call to fail.
       // fail("The test case is a prototype.");
    }

    /**
     * Test of PrintEnvirons method, of class Board.
     */
    @Test
    public void testPrintEnvirons() {
        System.out.println("PrintEnvirons");
        //Board instance = null;
        //Board instance = new Board();
        instance.PrintEnvirons();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of GetLocationString method, of class Board.
     */
    @Test
    public void testGetLocationString() {
        System.out.println("GetLocationString");
        int envID = 0;
        //Board instance = null;
        //Board instance = new Board();
        String expResult = "";
        String result = instance.GetLocationString(envID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of GetEnvironCount method, of class Board.
     */
    @Test
    public void testGetEnvironCount() {
        System.out.println("GetEnvironCount");
        //Board instance = null;
        //Board instance = new Board();
        int expResult = 0;
        int result = instance.GetEnvironCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of GetEnviron method, of class Board.
     */
    @Test
    public void testGetEnviron() {
        System.out.println("GetEnviron");
        int location = 0;
        //Board instance = null;
        //Board instance = new Board();
        Environ expResult = null;
        Environ result = instance.GetEnviron(location);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of MoveStack method, of class Board.
     */
    @Test
    public void testMoveStack() {
        System.out.println("MoveStack");
        int destEnvID = 0;
        //Board instance = null;
       // Board instance = new Board();
        instance.MoveStack(destEnvID);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of AddStackToEnviron method, of class Board.
     */
    @Test
    public void testAddStackToEnviron() {
        System.out.println("AddStackToEnviron");
        int envID = 0;
        int stackID = 0;
        boolean isInit = false;
        //Board instance = null;
        //Board instance = new Board();
        instance.AddStackToEnviron(envID, stackID, isInit);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of RemoveStackFromEnviron method, of class Board.
     */
    @Test
    public void testRemoveStackFromEnviron() {
        System.out.println("RemoveStackFromEnviron");
        int envID = 0;
        int stackID = 0;
        //Board instance = null;
        //Board instance = new Board();
        instance.RemoveStackFromEnviron(envID, stackID);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of GetStacksInEnviron method, of class Board.
     */
    @Test
    public void testGetStacksInEnviron() {
        System.out.println("GetStacksInEnviron");
        int envID = 0;
        //Board instance = null;
       // Board instance = new Board();
        List expResult = null;
        List result = instance.GetStacksInEnviron(envID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of PrintStacksInEnviron method, of class Board.
     */
    @Test
    public void testPrintStacksInEnviron() {
        System.out.println("PrintStacksInEnviron");
        int envID = 0;
        SIDE side = null;
        //Board instance = null;
       // Board instance = new Board();
        instance.PrintStacksInEnviron(envID, side);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of ValidEnemy method, of class Board.
     */
    @Test
    public void testValidEnemy() {
        System.out.println("ValidEnemy");
        int stackID = 0;
        int envID = 0;
        SIDE side = null;
        //Board instance = null;
        //Board instance = new Board();
        Boolean expResult = null;
        Boolean result = instance.ValidEnemy(stackID, envID, side);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of CharacterCombat method, of class Board.
     */
    @Test
    public void testCharacterCombat() {
        System.out.println("CharacterCombat");
        //Board instance = null;
        //Board instance = new Board();
        instance.CharacterCombat();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of GetStackList method, of class Board.
     */
    @Test
    public void testGetStackList() {
        System.out.println("GetStackList");
        //Board instance = null;
        //Board instance = new Board();
        StackList expResult = null;
        StackList result = instance.GetStackList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of GetPlanetByName method, of class Board.
     */
    @Test
    public void testGetPlanetByName() {
        System.out.println("GetPlanetByName");
        String name = "";
        //Board instance = null;
        //Board instance = new Board();
        Planet expResult = null;
        Planet result = instance.GetPlanetByName(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}