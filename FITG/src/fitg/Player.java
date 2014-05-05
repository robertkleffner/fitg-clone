/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fitg;
import java.util.*;
/**
 *
 * @author Matt
 */
public class Player {
    private SIDE PlayerSide;             // Player Type value
    private boolean PhasingPlayer;              // Flag to denote if this player is the phasing player
    private int ForcePoints;                    // Player's current ForcePoint
    private List<Ship> ListofShips;             // List of Ship obj. Will be changed to Stack obj once the class is complete
    
    // Initialize Class
    public Player(SIDE side)
    {
        this.PlayerSide = side;
        this.PhasingPlayer = false;
        this.ForcePoints = 0;
        this.ListofShips = new ArrayList();
    }
    // Get Player side value
    public SIDE GetPlayerSide() { return this.PlayerSide; }
    // Get Phasing Player status
    public boolean GetIsPhasingPlayer() { return this.PhasingPlayer; }
    // Set Phasing Player status
    public void SetIsPhasingPlayer(boolean flag) { this.PhasingPlayer = flag; }
    // Get ForcePoint value
    public int GetForcePoints() { return this.ForcePoints; }
    // Add ForcePoints to the Player's current total
    public void AddForcePoints (int n) { this.ForcePoints += n; }
    // Add a ship object to the player. This will be changed to a Stack obj
    // once the class has been included in the project.
    public void AddNewShipToPlayer (Ship ship) { this.ListofShips.add(ship); }
    // Return the players list of Ships. This will be changed to a Stack obj
    // once the class has been included in the project.
    public List<Ship> GetListofShips() { return this.ListofShips; }
    

    
}
