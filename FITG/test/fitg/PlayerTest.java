/*
 * PlayerTest.java
 * Author: Carson Stauffer
 */

package fitg;


import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.*;


public class PlayerTest {
     
    public void setUP() throws Exception{
         
    }
    
    @Test
    //Check rebel player's force points initialized to zero
    public void testRebelForcePoints() {
        Player player = new Player(SIDE.REBEL);
        
        assertTrue(player.GetForcePoints() == 0);
    }
    
    @Test
    //Check imperial player's force points initialized to zero
    public void testImperialForcePoints() {
        Player player = new Player(SIDE.IMPERIAL);
        
        assertTrue(player.GetForcePoints() == 0);
    }
    
    @Test
    //Check rebel player is on rebel side
    public void testPlayerSide() {
        Player rPlayer = new Player(SIDE.REBEL);
        Player iPlayer = new Player(SIDE.IMPERIAL);
        
        assertTrue(rPlayer.GetPlayerSide() == SIDE.REBEL);
        assertTrue(iPlayer.GetPlayerSide() == SIDE.IMPERIAL);
    }
    
    
    @Test
    //Check player is not phasing player
    public void testPhasingPlayer(){
        Player rPlayer = new Player(SIDE.REBEL);
        Player iPlayer =  new Player(SIDE.IMPERIAL);
        
        assertTrue(rPlayer.GetIsPhasingPlayer() == false);
        assertTrue(iPlayer.GetIsPhasingPlayer() == false);
    }
    
}
