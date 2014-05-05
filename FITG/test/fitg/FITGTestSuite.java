/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fitg;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author matt
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    fitg.CharacterTest.class,
    fitg.CombatTest.class,
    fitg.EntityTest.class,
    fitg.MilitaryForce.class,
    fitg.PlayerTest.class,
    fitg.PossessionTest.class,
    fitg.ProvinceTest.class,
    fitg.SearchTest.class,
    fitg.StackTest.class,
    fitg.TurnTest.class,
    fitg.StarSystemTest.class
})

public class FITGTestSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
}