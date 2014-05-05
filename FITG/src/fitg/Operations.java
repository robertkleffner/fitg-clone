/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fitg;

/**
 *
 * @author Alex, iantanimoto
 */
public class Operations
{
    static IOControl io;
    static Movement movement;
    static Turn turn;
    
    public Operations(Turn turn)
    {
        io = new IOControl();
        movement = new Movement();
        this.turn = turn;
    }
    
        // Operations Phase
    public void OperationsPhase()
    {
        io.println("Beginning Operations Phase");
        //io.starttext();
        String cmd = "Command: ";
              
        String input;
        boolean gameOver = false;
        do 
        {
            io.starttext();
            io.print(cmd);
            
            input = io.getLine();
            
            if ("m".equalsIgnoreCase(input))
            {
                //MoveUnit();
                MoveStack();
            }
            else if ("l".equalsIgnoreCase(input))
            {
                PrintUnits();
                //PrintUnitList(this.PhasingPlayer);
            }
            else if ("c".equalsIgnoreCase(input))
            {
                BuyUnit();
            }
            else if ("f".equalsIgnoreCase(input))
            {
                //();
            }
            else if ("n".equalsIgnoreCase(input))
            {
                //TODO: end game here
                
                gameOver = true;
            }
            else if ("q".equalsIgnoreCase(input))
            {
                //TODO: end game here
                
                Game.instance().gameOver = true;
                gameOver = true;
            }
            else
            {
                io.println("Unknown Option");
            }
        } while (gameOver != true);
        System.out.println("Outta the loop");
    }
    
    private void Fight()
    {
        int inp;
        Stack phasing, enemy;
        io.println("What unit do you want to fight?");
        PrintUnits();
        inp = io.getInt();
        
         //leave without moving if index is out of bounds
        if(inp >=  Board.instance().stacks.GetListSize() && inp < 0)
        {
            io.println("Bad Index");
            return;
        }
        
        phasing = Board.instance().stacks.GetStackObj(inp);
        if(phasing.GetSide() != turn.GetPhasingSide())
        {
            io.println("Not your piece");
            return; 
        }
        
        io.println("Choose enemy to fight");
        Board.instance().PrintStacksInEnviron(phasing.GetLocationID(), turn.GetNonPhasingSide());
        inp = io.getInt();
        
        if(Board.instance().ValidEnemy( inp , phasing.GetLocationID(), turn.GetNonPhasingSide()))
        {
            io.println("Invalid enemy");
            return;
        }
        
        enemy = Board.instance().stacks.GetStackObj(inp);
        
        //map.FightMilitary();
        
        
        
        
        
        
    }

    private void BuyUnit()
    {
        int inp, sid;
        io.println("Choose a place to put the unit");
        
        Board.instance().PrintEnvirons();
        io.print("Enter Location Number: ");
        inp = io.getInt();
        if (inp < Board.instance().GetEnvironCount() && inp >= 0)
        {
             sid = Board.instance().stacks.NewMilitaryForce(inp, new MilitaryForce(turn.GetPhasingSide()));
             Board.instance().AddStackToEnviron(inp, sid, true);
        }
        else
            io.println("Invalid location.");
    }
    
    private void PrintUnits()
    {
        Stack stack;
        for(int i = 0; i < Board.instance().stacks.GetListSize(); i++)
        {
            stack = Board.instance().stacks.GetStackObj(i);
            
            if(stack.GetSide() == turn.GetPhasingSide())
            {
                io.print(String.format("%d: ", stack.GetStackID()));
                io.print(String.format("Unit of type"));
                if(stack.GetET(0) == EntityType.MILITARY)
                {
                    io.print(" MilitaryForce ");
                }
                io.print("at Location ");

                io.println("    " + Board.instance().GetLocationString(stack.GetLocationID()));
            }
        }
    }
    
    private void MoveStack()
    {
        int inp;
        Movement move = new Movement();
        Stack stack;
        io.println("Choose unit to move from list:");
        PrintUnits();
        io.print("Enter Unit Number: ");
        inp = io.getInt();
        
        //leave without moving if index is out of bounds
        if(inp >=  Board.instance().stacks.GetListSize() && inp < 0)
        {
            io.println("Bad Index");
            return;
        }
        
        stack = Board.instance().stacks.GetStackObj(inp);
        if(stack.GetSide() != turn.GetPhasingSide())
        {
            io.println("Not your piece");
            return; 
        }
        
        io.println("Choose a place to go,");
        Board.instance().PrintEnvirons();
        io.print("Enter Destination Number: ");
        
        inp = io.getInt();
        if (inp < Board.instance().GetEnvironCount() && inp >=0)
            //move.Move(stack.GetStackID(), inp );
            io.println("Temp holder");
        else
            io.println("Bad Index");
        
        io.println("Removing Unit " + stack.GetStackID() + " from " + Board.instance().GetLocationString(stack.GetLocationID()));
        Board.instance().RemoveStackFromEnviron(stack.GetLocationID(), stack.GetStackID());
        stack.SetLocationID(inp);
        io.println("Adding Unit " + stack.GetStackID() + " to " + Board.instance().GetLocationString(stack.GetLocationID()));
        Board.instance().AddStackToEnviron(inp, stack.GetStackID(), false);

    }
}
