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
public class StarSystemTest {
    public Planet planet;
    public StarSystemTest() {
       //planet = new Planet(int provinceID, int systemID, int planetID, String tmpName, int orbital);
       planet = new Planet(1, 2, 3, "NotEarth", 4);
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
     * Test of AddPlanet and GetPlanetByID methods, of class StarSystem.
     */
    @Test
    public void testAddPlanet() {
        System.out.println("AddPlanet");
        StarSystem instance = new StarSystem(1, 2, "TestSystem", 1, 2);
        instance.AddPlanet(planet);
        Planet expResult = planet;
        Planet result = instance.GetPlanetByID(planet.GetPlanetID());
        assertEquals(expResult, result);
    }

    /**
     * Test of GetSystemID method, of class StarSystem.
     */
    @Test
    public void testGetSystemID() {
        System.out.println("GetSystemID");
        StarSystem instance = new StarSystem(1, 2, "TestSystem", 1, 2);   
        int expResult = 2;
        int result = instance.GetSystemID();
        assertEquals(expResult, result);
    }

        /**
         * Test of GetPlanetObj method, of class StarSystem.
         * Method GetPlaetObj needs revision, works unpredictably and returns 
         * unpredictable results. In it's current state, it's untestable... 
         */
  //  @Test
  //  public void testGetPlanetObj() {
  //      System.out.println("GetPlanetObj");
  //      StarSystem instance = new StarSystem(1, 2, "TestSystem", 1, 2);   
  //      instance.AddPlanet(planet);
  //      List<Planet> p = new ArrayList();
  //      p.add(planet);
  //      Planet expResult = p.get(planet.GetPlanetID());
  //      Planet result = instance.GetPlanetObj(planet.GetPlanetID());
  //      assertEquals(expResult, result);
  //  }

    
    /**
     * Test of GetSystemName method, of class StarSystem.
     */
    @Test
    public void testGetSystemName() {
        System.out.println("GetSystemName");
        StarSystem instance = new StarSystem(1, 2, "TestSystem", 1, 2);   
        String expResult = "TestSystem";
        String result = instance.GetSystemName();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetPlanets method, of class StarSystem.
     */
    @Test
    public void testGetPlanets() {
        System.out.println("GetPlanets");
        StarSystem instance = new StarSystem(1, 2, "TestSystem", 1, 2);  
        instance.AddPlanet(planet);
        List<Planet> testlist = new ArrayList();
        testlist.add(planet);
        List<Planet> expResult = testlist;
        List<Planet> result = instance.GetPlanets();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetX method, of class StarSystem.
     */
    @Test
    public void testGetX() {
        System.out.println("GetX");
        StarSystem instance = new StarSystem(1, 2, "TestSystem", 0.0F, 0.0F);   
        float expResult = 0.0F;
        float result = instance.GetX();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of GetY method, of class StarSystem.
     */
    @Test
    public void testGetY() {
        System.out.println("GetY");
        StarSystem instance = new StarSystem(1, 2, "TestSystem", 0.0F, 0.0F);   
        float expResult = 0.0F;
        float result = instance.GetY();
        assertEquals(expResult, result, 0.0);
    }
    
}
