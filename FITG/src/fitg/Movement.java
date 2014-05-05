/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fitg;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author iantanimoto
 */
public class Movement
{   
    public Movement()
    {
    }
    
    private boolean JudgeMove(Stack stack, Environ dest, boolean MoveAttempted)
    {
        if(Game.instance().gameType==GAMETYPE.STARSYSTEM)
        {
            boolean canMove = false;
            List<Entity> entities = stack.GetEntities();
            System.out.println("Origing Environ: "+Board.instance().GetEnviron(stack.GetLocationID()).GetPlanetName());
            System.out.println("Dest Environ: "+dest.GetPlanetName());

            //if movement is to another planet
            if(dest.GetPlanetID() != Board.instance().GetEnviron(stack.GetLocationID()).GetPlanetID() &&
               dest.GetSystemID() == Board.instance().GetEnviron(stack.GetLocationID()).GetSystemID())
            {
                System.out.println("Moving to a different planet");
                for(Entity e : entities)
                {
                    if(e.ET == EntityType.CHARACTER)
                    {
                        Character c = (Character) e;
                        if(c.GetHasShip())//TODO: && !e.GetHasMoved())
                        {
                            canMove = true;
                            break;
                        }
                    }
                    else if(e.ET == EntityType.MILITARY)
                    {
                        MilitaryForce m = (MilitaryForce) e;
                        if(m.GetMobile())//TODO: && !e.GetHasMoved())
                        {
                            canMove = true;
                            break;
                        }
                    }
                }
            } //Stay in starsystem for starsystem game, no leaving scenario starsystem
            else if(dest.GetSystemID() != Board.instance().GetEnviron(stack.GetLocationID()).GetSystemID()){
                System.out.println("Move outside of starsystem");
                canMove = false;
            }
            else
            {
                System.out.println("Move on same planet");
                canMove = true;
            }
            
            if (Game.instance().turn.phase == Phase.Reaction && canMove)
            {
                canMove = false;
                if(dest.GetPlanetID() != Board.instance().GetEnviron(stack.GetLocationID()).GetPlanetID()){
                    canMove = false;
                    return canMove;
                }
                System.out.println("Move is during reaction phase and unit CAN move");
                if (Game.instance().turn.GetNonPhasingSide() == SIDE.IMPERIAL){
                    if(dest.detectedRebels || dest.rebelMilitary){
                        canMove = true;
                        System.out.println("Reaction move made by imperial, canMove: "+canMove);
                    }
                }
                else{
                    if(dest.detectedImperials || dest.imperialMilitary){
                    canMove = true;
                    System.out.println("Reaction move made by rebel, canMove: "+canMove);
                    }
                }
            }
            
            System.out.println("Move valid?: "+canMove);
            return canMove;
        }
        else
        {
            System.out.println("Non-starsystem game, move is valid");
            return true;//Because anything can move anywhere. It doesn't matter yet.
        }
        // Todo: Implement rules
    }
    /*
    public int[] GetMoves(int pieceID)
    {
        int LocationListSize = map.locations.GetLocationListSize();
        int[] returnlist = new int[LocationListSize+1];
        int counter = 0;
        
        for (int id = 0; id<LocationListSize; id++)
        {
            if (JudgeMove(map.stacks.GetStackObj(pieceID), map.locations.GetLocationObj(id), false))
            {
                returnlist[counter] = id;
                counter++;
            }
        }
        returnlist[counter] = -1;
        return returnlist;
    }
    */
    public void Move(ArrayList<Entity> entities, ArrayList<Integer> stackIds, int destinationID)
    {

       System.out.println("Moving units: "+entities);
       Stack movingUnits = Board.instance().stacks.AddStack();
       movingUnits.SetSide(entities.get(0).GetSide());
       movingUnits.SetLocationID(Board.instance().stacks.GetStackObj(stackIds.get(0)).GetLocationID());
       //movingUnits.SetLocationID(destinationID);
       int newStackID = movingUnits.GetStackID();
       System.out.println("New stack id for movement: "+newStackID);
       Environ destinationEnviron = Board.instance().GetEnviron(destinationID);
       System.out.println("stacks in origin before: "+Board.instance().GetEnviron(Board.instance().stacks.GetStackObj(stackIds.get(0)).GetLocationID()).GetListOfStacksInEnviron());

       //List<Entity> entities;
       //Notify notify; 
       //Character c; 
       //boolean canMove = false;
       
       for(int i = 0; i<entities.size(); i+=1)
       {
           Entity tempEntity = entities.get(i);
           int stackID = stackIds.get(i);
           Board.instance().stacks.UnitSwitchStacks(tempEntity, stackID, newStackID);
       }
       //If valid move
       if (JudgeMove(movingUnits, destinationEnviron, false))
       {
           movingUnits.SetLocationID(destinationID);
           
           Board.instance().AddStackToEnviron(destinationID, newStackID, false);
           
           //set all unit's haveMoved, in moving stack, to true
           for(Entity e : movingUnits.GetEntities())
           {
               e.SetHasMoved(true);
           }
           System.out.println("stacks in origin after: "+Board.instance().GetEnviron(Board.instance().stacks.GetStackObj(stackIds.get(0)).GetLocationID()).GetListOfStacksInEnviron());

           //prevent dialogue from opening when not using GUI(ie: testing)
           if(FITG.MainGui != null)
                JOptionPane.showMessageDialog(FITG.MainGui, "Moved to "+Board.instance().GetEnviron(destinationID).GetLocationString());
       }
       //invalid move, move stacks back to respective stacks
       else
       {
           for(int i = 0; i<entities.size(); i+=1)
           {
                Entity tempEntity = entities.get(i);
                int stackID = stackIds.get(i);
                Board.instance().stacks.UnitSwitchStacks(tempEntity, newStackID, stackID);  
           }
 
           //Board.instance().stacks.RemoveStack(movingUnits);
           //redundancy to make sure that the stacks on the environ are updated
           Board.instance().AddStackToEnviron(Board.instance().stacks.GetStackObj(stackIds.get(0)).GetLocationID(), stackIds.get(0), true);
           //prevent dialogue from opening when not using GUI(ie: testing)
            System.out.println("stacks in origin after: "+Board.instance().GetEnviron(Board.instance().stacks.GetStackObj(stackIds.get(0)).GetLocationID()).GetListOfStacksInEnviron());

           if(FITG.MainGui != null)
                JOptionPane.showMessageDialog(FITG.MainGui, "<html><center>Invalid Move</center></html>");
           
            //movement was attemptable, but not possible by chance, something else happens
       }
    }
}
