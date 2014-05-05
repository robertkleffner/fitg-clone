/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
 * @author shea
 */
public class TurnTest {
    public Game g;
    public SIDE rside;
    public SIDE iside;
    public Player rebel;
    public Player imperial;
    public Board map;

    public TurnTest() {
        g = Game.instance();
        rside = SIDE.REBEL;
        iside = SIDE.IMPERIAL;
        rebel = new Player(rside);
        imperial = new Player(iside);
        map = Board.instance();
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
     * Test of GetPhasingSide method, of class Turn.
     */
    @Test
    public void testGetPhasingSide() {
        System.out.println("GetPhasingSide");
        Turn instance = new Turn(rebel, imperial);
        SIDE expResult = SIDE.REBEL;
        SIDE result = instance.GetPhasingSide();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of GetNonPhasingSide method, of class Turn.
     */
    @Test
    public void testGetNonPhasingSide() {
        System.out.println("GetNonPhasingSide");
        Turn instance = new Turn(rebel, imperial);
        SIDE expResult = SIDE.IMPERIAL;
        SIDE result = instance.GetNonPhasingSide();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of TestTurnControl Side Change method, of class Turn.
     */
    @Test
    public void testSideChangeOnTurn() {
        System.out.println("SideChangeOnTurn");
        Turn instance = new Turn(rebel, imperial);
        instance.TestTurnControl();
        SIDE expResult = SIDE.IMPERIAL;
        SIDE result = instance.GetPhasingSide();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of GetGameRound method, of class Turn.
     */
    @Test
    public void testGetGameRound() {
        System.out.println("GetGameRound");
        Turn instance = new Turn(rebel, imperial);
        instance.TestTurnControl();
        instance.TestTurnControl();
        int expResult = 1;
        int result = instance.GetGameRound();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
