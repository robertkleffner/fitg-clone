/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fitg;

import java.util.ArrayList;
import java.util.List;
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
public class MovementTest {
    
    private Stack testStack;
    private Character charOne;
    private MilitaryForce unitOne;
    private MilitaryForce unitTwo;
    //an environ on a planet different than Charkan (the origin)
    private int destinationID;
    private int stackID;
    private ArrayList<Entity> entities = new ArrayList();
    private ArrayList<Integer> stackIds = new ArrayList();
    public MovementTest() {

    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        Game.instance().gameType = GAMETYPE.STARSYSTEM;
                //create stack
        //testStack = new Stack();
        //create imperial units (these military units are non-mobile)
        unitOne = new MilitaryForce(SIDE.IMPERIAL, "Line", 3, 2, false);
        stackID = Board.instance().stacks.NewMilitaryForce(30, unitOne);
                        System.out.println("Mobile: "+unitOne.GetMobile());
        unitTwo = new MilitaryForce(SIDE.IMPERIAL, "Line", 3, 2, false);
        Board.instance().stacks.GetStackObj(stackID).AddToStack(unitTwo);
        System.out.println("Mobile: "+unitTwo.GetMobile());
        //charOne = Game.instance().GetCharacter("Vans Ka-Tie-A");
        
        //add units to stack
        //testStack.AddToStack(unitOne);
        //testStack.AddToStack(unitTwo);
        //testStack.AddToStack(charOne);
        //set location as environ in Charkan
        //testStack.SetLocationID(30);
        //add stack to environ
        //Board.instance().GetEnviron(30).AddStack(testStack.GetStackID());
        //int stackID = Board.instance().stacks.NewCharacter(30, charOne);
        //Board.instance().stacks.GetStackObj(stackID).AddToStack(charOne);
        
        //initialize ArrayLists for move function
        for(Entity e : Board.instance().stacks.GetStackObj(stackID).GetEntities()){
            entities.add(e);
            stackIds.add(stackID);
        }
        
    }
    
    @After
    public void tearDown() {

    }

    /**
     * Test of JudgeMove method, of class Movement using valid movement (same planet).
     */
    @Test
    public void testValidJudgeMoveSamePlanet() {
        System.out.println("Valid Move (Same Planet)");      
        
        //Possession pos = Game.instance().GetPossession("Imperial spaceship");
        //pos.SetCharacterAssignment("Vans Ka-Tie-A");
        //charOne.AddPossession(pos);
        
        
        //Different environ on Charkan
        destinationID = 29;
        Movement instance = new Movement();
        instance.Move(entities, stackIds, destinationID);
        
        System.out.println("Dest Ents Angoff: "+Board.instance().GetEnviron(destinationID).GetStacksForSide(SIDE.IMPERIAL).iterator().next().GetEntities());
        
        ArrayList <Entity> compare = new ArrayList();
        for(Stack s : Board.instance().GetEnviron(destinationID).GetStacksForSide(SIDE.IMPERIAL)){
            for(Entity e : s.GetEntities()){
                compare.add(e);
            }
        }
        assertTrue(compare.containsAll(entities));
        //charOne.RemovePossessionByObject(pos);
    }
    
        /**
     * Test of JudgeMove method, of class Movement using invalid movement (different planet).
     */
    @Test
    public void testInvalidJudgeMoveDiffPlanet() {
        System.out.println("Invalid Move (Different Planet)");      
        

        
        //Different environ on Angoff
        destinationID = 20;
        Movement instance = new Movement();
        instance.Move(entities, stackIds, destinationID);
        ArrayList <Entity> compare = new ArrayList();
        if(Board.instance().GetEnviron(destinationID).GetStacksForSide(SIDE.IMPERIAL).size() > 0){
                  
            for(Stack s : Board.instance().GetEnviron(destinationID).GetStacksForSide(SIDE.IMPERIAL)){

                for(Entity e : s.GetEntities()){
                    compare.add(e);

                }
            }
        }
        assertFalse(compare.containsAll(entities));
    }
}