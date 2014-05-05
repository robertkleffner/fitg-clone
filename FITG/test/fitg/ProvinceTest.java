/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import java.util.ArrayList;

/**
 *
 * @author shea
 */
public class ProvinceTest {
    
    public StarSystem starsys;
    
    public ProvinceTest() {
        starsys = new StarSystem(1, 2, "TestSystem", 0.0F, 0.0F);
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
     * Test of GetProvinceID method, of class Province.
     */
    @Test
    public void testGetProvinceID() {
        System.out.println("GetProvinceID");
        Province instance = new Province(5);
        instance.AddSystem(starsys);
        int expResult = 5;
        int result = instance.GetProvinceID();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetSystemByID method, of class Province.
     */
    @Test
    public void testGetSystemByID() {
        System.out.println("GetSystemByID");
        int systemID = 0;
        Province instance = new Province(5);
        instance.AddSystem(starsys);
        StarSystem expResult = starsys;
        StarSystem result = instance.GetSystemByID(2);
        assertEquals(expResult, result);
    }

    /**
     * Test of GetSystems method, of class Province.
     */
    @Test
    public void testGetSystems() {
        System.out.println("GetSystems");
        List<StarSystem> s = new ArrayList();
        Province instance = new Province(5);
        s.add(starsys);
        instance.AddSystem(starsys);
        List<StarSystem> expResult = s;
        List<StarSystem> result = instance.GetSystems();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetSystemObj method, of class Province.
     * Untestable method...
    @Test
    public void testGetSystemObj() {
        System.out.println("GetSystemObj");
        int sysID = 0;
        Province instance = new Province(5);
        instance.AddSystem(starsys);
        StarSystem expResult = null;
        StarSystem result = instance.GetSystemObj(sysID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
     */    
}
