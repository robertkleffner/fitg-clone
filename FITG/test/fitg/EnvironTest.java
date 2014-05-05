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
 * Created: 11/16/2013
 * Last Edited by: Trevor Marquis
 * Date Last Edited: 11/16/2013 
 */

public class EnvironTest {
    
    public EnvironTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of SetEnvironSize method, of class Environ.
     */
    @Test
    public void testSetEnvironSize() {
        System.out.println("SetEnvironSize");
        int size = 0;
        //Environ instance = null;
        Environ instance = new Environ(1, 1, 1, 1, 1, Environ.EnvironType.Air);
        int expResult = 1;
        //int result =
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of GetEnvironSize method, of class Environ.
     */
    @Test
    public void testGetEnvironSize() {
        System.out.println("GetEnvironSize");
        //Environ instance = null;
        Environ instance = new Environ(1, 1, 1, 1, 1, Environ.EnvironType.Air);
        int expResult = 1;
        int result = instance.GetEnvironSize();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of SetEnvironButton method, of class Environ.
     */
    @Test
    public void testSetEnvironButton() {
        System.out.println("SetEnvironButton");
        ArcButton b = null;
        //Environ instance = null;
        Environ instance = new Environ(1, 1, 1, 1, 1, Environ.EnvironType.Air);
        instance.SetEnvironButton(b);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of GetEnvironID method, of class Environ.
     */
    @Test
    public void testGetEnvironID() {
        System.out.println("GetEnvironID");
        //Environ instance = null;
        Environ instance = new Environ(1, 1, 1, 1, 1, Environ.EnvironType.Air);
        int expResult = 1;
        int result = instance.GetEnvironID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of SetEnvironID method, of class Environ.
     */
    @Test
    public void testSetEnvironID() {
        System.out.println("SetEnvironID");
        int n = 0;
        //Environ instance = null;
        Environ instance = new Environ(1, 1, 1, 1, 1, Environ.EnvironType.Air);
        instance.SetEnvironID(n);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of GetPlanetID method, of class Environ.
     */
    @Test
    public void testGetPlanetID() {
        System.out.println("GetPlanetID");
        //Environ instance = null;
        Environ instance = new Environ(1, 1, 1, 1, 1, Environ.EnvironType.Air);
        int expResult = 1;
        int result = instance.GetPlanetID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of GetSystemID method, of class Environ.
     */
    @Test
    public void testGetSystemID() {
        System.out.println("GetSystemID");
        //Environ instance = null;
        Environ instance = new Environ(1, 1, 1, 1, 1, Environ.EnvironType.Air);
        int expResult = 1;
        int result = instance.GetSystemID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of GetProvinceID method, of class Environ.
     */
    @Test
    public void testGetProvinceID() {
        System.out.println("GetProvinceID");
        //Environ instance = null;
        Environ instance = new Environ(1, 1, 1, 1, 1, Environ.EnvironType.Air);
        int expResult = 1;
        int result = instance.GetProvinceID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of GetEnvironType method, of class Environ.
     */
    @Test
    public void testGetEnvironType() {
        System.out.println("GetEnvironType");
        //Environ instance = null;
        Environ instance = new Environ(1, 1, 1, 1, 1, Environ.EnvironType.Air);
        Environ.EnvironType expResult = Environ.EnvironType.Air;
        Environ.EnvironType result = instance.GetEnvironType();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of AddStack method, of class Environ.
     */
    @Test
    public void testAddStack() {
        System.out.println("AddStack");
        int stackID = 0;
        //Environ instance = null;
        Environ instance = new Environ(1, 1, 1, 1, 1, Environ.EnvironType.Air);
        Environ.EnvironType expResult = Environ.EnvironType.Air;
        Environ.EnvironType result = instance.AddStack(stackID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of RemoveStack method, of class Environ.
     */
    @Test
    public void testRemoveStack() {
        System.out.println("RemoveStack");
        int stackID = 0;
        //Environ instance = null;
        Environ instance = new Environ(1, 1, 1, 1, 1, Environ.EnvironType.Air);
        instance.RemoveStack(stackID);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of ListOfStacksInEnviron method, of class Environ.
     */
    @Test
    public void testListOfStacksInEnviron() {
        System.out.println("ListOfStacksInEnviron");
        //Environ instance = null;
        Environ instance = new Environ(1, 1, 1, 1, 1, Environ.EnvironType.Air);
        List expResult = new ArrayList();
        List result = instance.GetListOfStacksInEnviron();
        assertEquals(expResult.size(), result.size());
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of GetEnvironButton method, of class Environ.
     */
    @Test
    public void testGetEnvironButton() {
        System.out.println("GetEnvironButton");
        //Environ instance = null;
        Environ instance = new Environ(1, 1, 1, 1, 1, Environ.EnvironType.Air);
        ArcButton expResult = null;
        ArcButton result = instance.GetEnvironButton();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of GetMissionCapableStack method, of class Environ.
     */
    @Test
    public void testGetMissionCapableStack() {
        System.out.println("GetMissionCapableStack");
        //Environ instance = null;
        Environ instance = new Environ(1, 1, 1, 1, 1, Environ.EnvironType.Air);
        Stack expResult = null;
        Stack result = instance.GetMissionCapableStack();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of GetLocationString method, of class Environ.
     */
   /* @Test
    public void testGetLocationString() {
        System.out.println("GetLocationString");
        //Environ instance = null;
        Environ instance = new Environ(1, 1, 1, 1, 1, Environ.EnvironType.Air);
        String expResult = "";
        String result = instance.GetLocationString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }
*/
    
    /**
     * Test of GetUnitsForSide method, of class Environ.
     */
    @Test
    public void testGetUnitsForSide() {
        System.out.println("GetUnitsForSide");
        SIDE s = null;
        //Environ instance = null;
        Environ instance = new Environ(1, 1, 1, 1, 1, Environ.EnvironType.Air);
        List expResult = new ArrayList();
        List result = instance.GetUnitsForSide(s);
        assertEquals(expResult.size(), result.size());
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }
}