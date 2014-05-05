package fitg;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MilitaryForceTest {
    
    public MilitaryForce military;
    public MilitaryForceTest()
    {
        military = new MilitaryForce(SIDE.IMPERIAL, "Enemy", 1, 2, true);
    }
    
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp()
    {
    }
    
    @After
    public void tearDown()
    {
    }

    /**
     * Test of SetEntityID method, of class MilitaryForce.
     */
    @Test
    public void testSetEntityID()
    {
        System.out.println("SetEntityID");
        int id = 0;
        military.SetEntityID(id);
        assertEquals(military.ID, id);
    }

    /**
     * Test of GetEnvironStrength method, of class MilitaryForce.
     */
    @Test
    public void testGetEnvironStrength()
    {
        System.out.println("GetEnvironStrength");
        int expResult = 1;
        int result = military.GetEnvironStrength();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetSpaceStrenght method, of class MilitaryForce.
     */
    @Test
    public void testGetSpaceStrenght()
    {
        System.out.println("GetSpaceStrenght");
        int expResult = 2;
        int result = military.GetSpaceStrength();
        assertEquals(expResult, result);
    }
}