/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fitg;

import java.util.*;

/**
 *
 * @author Alex
 * 
 * StackList is an Arraylist of Stacks. 
 * All Entity objects are in stacks, all stacks are in the StackList in Map 
 * called stacks.
 * 
 * When a stack is destroyed or disbanded, its stackID is added to 
 * deadStackIndexes, and when new stacks are created, they pop an unused stackID
 * from deadStackIndexes if it is not empty.
 * 
 */
public class StackList {
    private List<Stack> stackList;
    private LinkedList<Integer> deadStackIndexes;
    
    public StackList()
    {
        deadStackIndexes = new LinkedList();
        stackList = new ArrayList();
    }
    

    //
    public Stack AddStack()
    {
        Stack stack;
        if(!deadStackIndexes.isEmpty())
        {
            System.out.println("Dead stack is not empty, stack ID should be: "+this.deadStackIndexes.peek());
            stack = this.GetStackObj(this.deadStackIndexes.pop());
            System.out.println("after pop deadstack: "+this.deadStackIndexes);
        }
        else
        {
            stack = new Stack();
            System.out.println("Stack ID should be: "+stackList.size());
            stack.SetStackID(stackList.size());
            stackList.add(stack.GetStackID(), stack);
        }
        System.out.println("After adding stack, deadStacks: "+this.deadStackIndexes);
        return stack;
    }

    //stack is left in stack list, index/stackID is added to deadstacks
    //take out any access to empty stack from gui at this time    
    public void RemoveStack(Stack stack)
    {
        Board.instance().RemoveStackFromEnviron(stack.GetLocationID(), stack.GetStackID());
        this.deadStackIndexes.add(stack.GetStackID());
        System.out.println("removed stack, deadstacks: "+this.deadStackIndexes);
    }
    
    //this function will be used when moving units between stacks
    public void UnitSwitchStacks(Entity unit, int stackID_from, int stackID_to)
    {
        Stack stack_from = this.GetStackObj(stackID_from);
        Stack stack_to = this.GetStackObj(stackID_to);
        
        // remove unit from old stack, if stack is now empty, remove stack
        // from active stacks
        if(stack_from.RemoveFromStack(unit)) 
            this.RemoveStack(stack_from);
        
        //add to new stack
        stack_to.AddToStack(unit);
    }
    
    public Stack GetStackObj(int stackID)
    {
        if(this.stackList.size() < stackID)
        {
            return null;    
        }
        return stackList.get(stackID);
    }
    
    //this returns number stacks in stacklist, including dead stacks
    public int GetListSize()
    {
        return stackList.size();
    }
    
    public int NewMilitaryForce(int locationID, MilitaryForce newForce)
    {
        Stack stack = null;
        for(int stackID : Board.instance().GetEnviron(locationID).GetListOfStacksInEnviron())
        {
            Stack ts = Board.instance().stacks.GetStackObj(stackID);
            if(ts.GetSide() == newForce.side)
                stack = ts;
        }
        if(stack == null)
        {
            stack = AddStack();
            stack.SetLocationID(locationID);
            Board.instance().AddStackToEnviron(locationID, stack.GetStackID(), true);
            stack.SetSide(newForce.GetSide());
        }
        stack.AddToStack(newForce);

        return stack.GetStackID();
    }
    
    public int NewCharacter(int locationID, Character character)
    {
        Stack stack = null;
        for(int stackID : Board.instance().GetEnviron(locationID).GetListOfStacksInEnviron())
        {
            Stack ts = Board.instance().stacks.GetStackObj(stackID);
            if(ts.GetSide() == character.side)
                stack = ts;
        }
        if(stack == null)
        {
            stack = AddStack();
            stack.SetLocationID(locationID);
            Board.instance().AddStackToEnviron(locationID, stack.GetStackID(), true);
            stack.SetSide(character.GetSide());
        }        
        
        stack.AddToStack(character);

        return stack.GetStackID();
    }
    
    public ArrayList<Integer> GetMissionCapableEnvirons()
    {
        ArrayList<Integer> missionEnvirons = new ArrayList();
        
        for (Stack s : stackList)
        {
            if(s.IsMissionCapableStack())
                missionEnvirons.add(s.GetLocationID());
        }
        
        return missionEnvirons;
    }
    
   public Character GetCharacterByName(String name)
   {
       for(Stack s : this.stackList)
       {
           for(Entity e : s.GetEntities())
           {
               if(e.ET == EntityType.CHARACTER)
               {
                   Character c = (Character)e;
                   if(c.GetName().equals(name))
                       return c;
               }
           }
       }
       return null;
   }

   //returns true if character is removed from a stack
   public Boolean KillCharacterByName(String name)
   {
       for(Stack s : this.stackList)
       {
           for(Entity e : s.GetEntities())
           {
               if(e.ET == EntityType.CHARACTER)
               {
                   Character c = (Character)e;
                   if(c.GetName().equals(name))
                   {
                       if(s.RemoveFromStack(e))
                           this.RemoveStack(s);
                       return true;
                   }
               }
           }
       }
       return false;
   }
   
   public Boolean CheckForRebelCharacters()
   {
       for(Stack s : this.stackList)
       {
           for(Entity e : s.GetEntities())
           {
               if(e.ET == EntityType.CHARACTER)
               {
                   Character c = (Character)e;
                   if(c.side == SIDE.REBEL)
                       return true;
               }
           }
       }
       return false;
   }
   
   public void RemoveEntity(Entity m)
   {
       for (Stack s : stackList)
       {
           for (Entity e : s.GetEntities())
           {
               Entity temp = e;
               if (temp.equals(m))
               {
                   if (s.RemoveFromStack(temp))
                       this.RemoveStack(s);
               }
           }
       }
   }
}
