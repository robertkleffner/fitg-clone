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
 * @author TaylorTrabun
 */
public class EntityTest {
    
    public EntityTest() {
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
     * Test of SetHasMoved method, of class Entity.
     */
    @Test
    public void testSetHasMoved() {
        System.out.println("SetHasMoved");
        boolean moved = true;
        Entity instance = new Entity(EntityType.MILITARY, SIDE.IMPERIAL);
        instance.SetHasMoved(moved);
        //Make sure the SetHasMoved worked correctly
        assertTrue(instance.GetHasMoved());
    }

    /**
     * Test of GetHasMoved method, of class Entity.
     */
    @Test
    public void testGetHasMoved() {
        System.out.println("GetHasMoved");
        Entity instance = new Entity(EntityType.MILITARY, SIDE.IMPERIAL);
        //initial hasMoved is set to false
        assertFalse(instance.GetHasMoved());

    }
    
    /**
     * Test of GetSide method, of class Entity.
     */
    @Test
    public void testGetSide() {
        System.out.println("GetSide");
        Entity instance = new Entity(EntityType.MILITARY, SIDE.IMPERIAL);
        SIDE expResult = SIDE.IMPERIAL;
        SIDE result = instance.GetSide();
        assertEquals(expResult, result);

    }
}