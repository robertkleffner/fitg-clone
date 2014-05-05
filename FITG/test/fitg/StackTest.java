/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fitg;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alex
 */
public class StackTest {
    
    public StackTest() {
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
     * Test of AddToStack method, of class Stack.
     */
    @Test
    public void testAddToStack() {
        System.out.println("AddToStack");
        Entity entity = new Entity(EntityType.CHARACTER, SIDE.NEUTRAL);
        Stack instance = new Stack();
        instance.AddToStack(entity);
        
        assertEquals(instance.IsInStack(entity), true);
            
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of RemoveUnit method, of class Stack.
     */
    @Test
    public void testRemoveUnit() {
        System.out.println("RemoveUnit");
        Entity entity = new Entity(EntityType.CHARACTER, SIDE.NEUTRAL);
        Stack instance = new Stack();
        instance.AddToStack(entity);
        instance.RemoveUnit(entity);
        
        assertEquals(instance.IsInStack(entity), false);
        
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of RemoveFromStack method, of class Stack.
     */
    /*@Test
    public void testRemoveFromStack() {
        System.out.println("RemoveFromStack");
        Entity entity = null;
        Stack instance = new Stack();
        Boolean expResult = null;
        Boolean result = instance.RemoveFromStack(entity);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

    /**
     * Test of GetLocationID method, of class Stack.
     */
    @Test
    public void testGetLocationID() {
        System.out.println("GetLocationID");
        Stack instance = new Stack();
        int expResult = 0;
        instance.SetLocationID(0);
        int result = instance.GetLocationID();
        assertEquals(expResult, result);

        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of SetLocationID method, of class Stack.
     */
    @Test
    public void testSetLocationID() {
        System.out.println("SetLocationID");
        int destID = 0;
        Stack instance = new Stack();
        instance.SetLocationID(destID);
        assertEquals(instance.GetLocationID(), destID);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of GetStackID method, of class Stack.
     */
    /*@Test
    public void testGetStackID() {
        System.out.println("GetStackID");
        Stack instance = new Stack();
        int expResult = 0;
        int result = instance.GetStackID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

    /**
     * Test of SetStackID method, of class Stack.
     */
    /*@Test
    public void testSetStackID() {
        System.out.println("SetStackID");
        int stackID = 0;
        Stack instance = new Stack();
        instance.SetStackID(stackID);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

    /**
     * Test of GetSide method, of class Stack.
     */
    /*@Test
    public void testGetSide() {
        System.out.println("GetSide");
        Stack instance = new Stack();
        SIDE expResult = null;
        SIDE result = instance.GetSide();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

    /**
     * Test of GetMember method, of class Stack.
     */
    /*@Test
    public void testGetMember() {
        System.out.println("GetMember");
        int index = 0;
        Stack instance = new Stack();
        Entity expResult = null;
        Entity result = instance.GetMember(index);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

    /**
     * Test of GetEntities method, of class Stack.
     */
    /*@Test
    public void testGetEntities() {
        System.out.println("GetEntities");
        Stack instance = new Stack();
        List expResult = null;
        List result = instance.GetEntities();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

    /**
     * Test of GetET method, of class Stack.
     */
    /*@Test
    public void testGetET() {
        System.out.println("GetET");
        int index = 0;
        Stack instance = new Stack();
        EntityType expResult = null;
        EntityType result = instance.GetET(index);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

    /**
     * Test of SetSide method, of class Stack.
     */
    /*@Test
    public void testSetSide() {
        System.out.println("SetSide");
        SIDE side = null;
        Stack instance = new Stack();
        instance.SetSide(side);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

    /**
     * Test of IsMissionCapableStack method, of class Stack.
     */
    /*@Test
    public void testIsMissionCapableStack() {
        System.out.println("IsMissionCapableStack");
        Stack instance = new Stack();
        boolean expResult = false;
        boolean result = instance.IsMissionCapableStack();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/
}