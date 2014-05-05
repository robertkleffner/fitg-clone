/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fitg;
import java.util.*;
/**
 *
 * @author Matt
 * @updated Jason
 * @updated Alex
 */
public class Ship extends Possession
{
    // Ship Info
    private int Capacity;           // Max capacity of the ship
    private int[] PassangerIDs;     // Array of current passangers
    private int PilotID;            // ID of pilot
    private int NumPassangers;      // Current number of passangers
    private int PassangerIDCounter; // Used internally for GetPassanger function

    private int Maneuver;
    private int Shields;
    private int Cannons;
    
    private boolean Hyperjumpable;
    private boolean Detected;
    private boolean Damaged;
    
    // Name, ID, Capacity, Hyperjumpable
    public Ship(String name, String image, int can, int shi, int man, int cap, boolean hyper )
    {
        super(Possession.PossessionType.Ship, name, image);
        Capacity = cap;
        PilotID = 0;
        NumPassangers = 0;
        PassangerIDCounter = 0;
        
        // Inititalize empty passanger list
        PassangerIDs = new int[Capacity];
        int x = 0;
        while( x < Capacity )
        {
            PassangerIDs[x] = 0;
            x++;
        }

        Hyperjumpable = hyper;
        Detected = false;
        Damaged = false;
        Shields = shi;
        Cannons = can;
        Maneuver = man;
    }

    // Use this one!
    public boolean CanHyperjump()
    {
        return Hyperjumpable;
    }

    public int GetShields()
    {
        return Shields;
    }
    public int GetCannons()
    {
        return Cannons;
    }
    public int GetManeuver()
    {
        return Maneuver;
    }

    public void SetDamaged( Boolean d)
    {
        Damaged = d;
    }
    public Boolean IsDamaged()
    {
        return Damaged;
    }


    // I'm not sure that Detection should go here on the ship, or with the characters?
    public void SetDetected( Boolean d )
    {
        Detected = d;
    }
    public void ShipWasDetected()
    {
        Detected = true;
    }
    public void ResetDetection()
    {
        Detected = false;
    }
    public Boolean IsDetected()
    {
        return Detected;
    }
    
    // Will set the first passanger as pilot if no other passangers
    // Returns true if add was successful
    // Returns false if it failed (full)
    public boolean AddPassanger( int newID )
    {
        if( NumPassangers >= Capacity )
        {
            return false;
        }
        else
        {
            if( NumPassangers == 0 ) // Empty ship, first person becomes pilot!
            {
                PilotID = newID;
            }
            PassangerIDs[NumPassangers-1] = newID;
            NumPassangers++;
            return true;
        }
    }

    public void RemovePassanger( int removeID )
    {
        int x = 0;
        while( x < Capacity );
        {
            if( PassangerIDs[x] == removeID )
            {
                if( removeID == PilotID )
                {

                }

                PassangerIDs[x] = 0;
                NumPassangers--;
                PassangerListFix();
                return;
            }
            x++;
        }
        return;
    }

    public boolean IsPassanger( int pID )
    {
        int x = 0;
        while( x < Capacity )
        {
            if( PassangerIDs[x] == pID )
            {
                return true;
            }
            x++;
        }
        return false;
    }

    // Each time this function is called, it returns a SINGLE passanger.
    // It keeps track of what has been given out.
    // When all passangers have been given, it returns a 0 and goes back to the top.
    public int GetPassanger()
    {
        int PassangerToReturn = PassangerIDs[PassangerIDCounter];
        if( PassangerToReturn == 0 ) // End of the list
        {
            PassangerIDCounter = 0;
            return 0;
        }
        else if ( PassangerIDCounter == Capacity ) // Ship is full and we're now asking for Capacity +1
        {
            PassangerIDCounter = 0;
            return 0;
        }
        else if ( PassangerToReturn > 0 ) // Is a passanger, return it
        {
            PassangerIDCounter++;
            return PassangerToReturn;
        }
        else // Should never happen.
        {
            System.out.println( "Error in Ship.GetPassanger(): else hit." );
            return 0;
        }
    }

    public int GetPilot()
    {
        return PilotID;
    }
    public void SetPilot( int p )
    {
        PilotID = p;
    }
    public boolean IsPilot( int pID )
    {
        if( pID == PilotID )
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    // This fixes holes in the list of passangers caused by removing someone.
    private void PassangerListFix()
    {
        int x = 0;
        int y = 1;
        while( x < Capacity )
        {
            if( PassangerIDs[x] == 0 ) // An empty is found
            {
                if( PassangerIDs[y] != 0 && y < Capacity ) // Next entry is not empty, so need to fix
                {
                    PassangerIDs[x] = PassangerIDs[y];
                    PassangerIDs[y] = 0;
                }
                else
                {
                    // Do nothing, you're at the end of a list
                }
            }
            x++;
            y++;
        }

    }
}
