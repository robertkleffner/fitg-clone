/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fitg;
import java.util.*;
/**
 *
 * @author iantanimoto, Matt
 */
public class Turn
{
    //private static Turn turn = null;
    private SIDE phasingSide;
    private SIDE nonPhasingSide;
    private Player RebelPlayer;     // Rebel Player class object
    private Player ImperialPlayer;  // Imperial Player class object
    private Player PhasingPlayer;   // To be set to rebel or imperial
    private Player NonPhasingPlayer;// Rebel or Imperial player
    private int GameRound;          // Counter to track rounds of play with a turn
    private IOControl io;           // Local IOControl class for input/output
    //private Locations gameLocations; // Game Locations for units
    private Operations ops;
    
    public Phase phase;
    
    public Turn(Player rebel, Player imperial)
    {
        this.RebelPlayer = rebel;
        this.ImperialPlayer = imperial;
        this.GameRound = 0;
        io = new IOControl();
        ops = new Operations(this);
        phase = Phase.Movement;

        this.nonPhasingSide = SIDE.IMPERIAL;
        this.phasingSide = SIDE.REBEL;
        this.RebelPlayer.SetIsPhasingPlayer(true);
        this.ImperialPlayer.SetIsPhasingPlayer(false);
        this.PhasingPlayer = this.RebelPlayer;
        this.NonPhasingPlayer = this.ImperialPlayer;
    }

    // Method to control turn flow and call the needed turn stage methods
    public void TurnControl()
    {
        // Switch Players
        if (this.PhasingPlayer.GetPlayerSide() == SIDE.REBEL)
        {
            this.phasingSide = SIDE.IMPERIAL;
            this.nonPhasingSide = SIDE.REBEL;
            this.RebelPlayer.SetIsPhasingPlayer(false);
            this.ImperialPlayer.SetIsPhasingPlayer(true);
            this.PhasingPlayer = this.ImperialPlayer;
            this.NonPhasingPlayer = this.RebelPlayer;
        }
        else
        {
            this.phasingSide = SIDE.REBEL;
            this.nonPhasingSide = SIDE.IMPERIAL;
            this.RebelPlayer.SetIsPhasingPlayer(true);
            this.ImperialPlayer.SetIsPhasingPlayer(false);
            this.PhasingPlayer = this.RebelPlayer;
            this.NonPhasingPlayer = this.ImperialPlayer;
            //Update game round
            GameRound++;
        }
        
//            if (this.phasingSide == SIDE.REBEL)
//                io.println("Rebel Turn");
//            else 
//                io.println("Imperial Turn");
            
        // Display Phasing Player info
        io.println("Phasing Player is the " + this.PhasingPlayer.GetPlayerSide().toString() + " Player.");

    }

//    public static Turn instance() {
//        if (turn == null) {
//            turn = new Turn((rebelPlayer, imperialPlayer);
//        }
//        return turn;
//    }
    
    public void TestTurnControl(){
          TurnControl();
    }

    public void StarSystemTurn()
    {
        // Run Operations Phase
        ops.OperationsPhase();
        
        // Run Search Phase
        SearchPhase();
        
        // Run Missions Phase
        MissionsPhase();
        
        //Switch phasing player
        TurnControl();
    }
    public void GalacticTurn()
    {
        TurnControl();
        
        // Run Galactic Stage
        GalacticStage();
        
        // Run Operations Phase
        ops.OperationsPhase();
        
        // Run Search Phase
        SearchPhase();
        
        // Run Missions Phase
        MissionsPhase();
        
        // Run Interphase
        GameTurnInterPhase();
    }
    
    // Interphase
    private void GameTurnInterPhase()
    {
        io.println("Game Turn Interphase");

        CheckMap();
        this.GameRound++;
        io.println("Game Turn Complete");
    }
    
    public SIDE GetPhasingSide()
    {
        return phasingSide;
    }
    
    public SIDE GetNonPhasingSide()
    {
        return nonPhasingSide;
    }
    
    
    // Review Map for Rebel Control
    private void CheckMap()
    {
        io.println("Checking Map");
    }
    
        // Galactic Stage Method
    private void GalacticStage()
    {
        io.println("Beginning Galactic Stage");
    }
    private void SearchPhase()
    {
        
        io.println("Beginning Search Phase");
    }
    
    // Mission Phase
    private void MissionsPhase()
    {
        ArrayList<Integer> missionEnvirons;
        io.println("Beginning Missions Phase");
        //Pop up List of environs where missions may be done
        io.println("Please select a mission capable location.");
        missionEnvirons = Board.instance().stacks.GetMissionCapableEnvirons();
        for (Integer i : missionEnvirons)
        {
            io.println(Board.instance().GetLocationString(i));
        }
        //
    }
    
    public int GetGameRound()
    {
        return this.GameRound;
    }
}
