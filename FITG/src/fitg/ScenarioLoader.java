/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fitg;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Alex
 */
public class ScenarioLoader {
    
    public void LoadFile()
    {
        try{
            String strLine;
            InputStream in = getClass().getResourceAsStream(Game.instance().scenario);    
            BufferedReader br = new BufferedReader(new InputStreamReader(in));         

            int state = 0;
            
            while( (strLine = br.readLine() ) != null)
            {   
                String[] token = strLine.split("\\s*,\\s*");

                if(token[0].charAt(0) != '#')
                switch(state)
                {
                    case 0:  // game type stuff
                        if(token[0].equals("gametype"))
                            Game.instance().gameType = GAMETYPE.valueOf(token[1]);
                        else if(token[0].equals("scenario"))
                            Game.instance().scenario = token[1];
                        else if(token[0].equals("turns"))
                            Game.instance().SetMaxTurns(Integer.parseInt(token[1]));
                        else if(token[0].equals("vp"))
                            Game.instance().SetVictoryPoints(Integer.parseInt(token[1]));
                        else if(token[0].charAt(0) == '%')
                            state++;
                        break;
                    case 1:  // characters
                        if(token[0].charAt(0) == '%')
                            state++;
                        else
                        {
                            Character character = Game.instance().GetCharacter(token[1]);
                            if(character != null)
                                Board.instance().stacks.NewCharacter(Integer.parseInt(token[2]), character); // puts in Environ 30 (in Egrix)
                        }
                        break;
                    case 2:  // planets
                        Planet p = Board.instance().GetPlanetByName(token[0]);
                        if(p != null)
                        {
                            p.PDBLevel = Integer.parseInt(token[1]);
                            p.controlledby = SIDE.valueOf(token[4]);
                            p.loyalty = Planet.PlanetLoyalty.valueOf(token[5]);
                        }
                        if(token[0].charAt(0) == '%')
                            state++;
                        break;
                    case 3:  // units
                        if(token[0].charAt(0) == '%')
                            state++;
                        else
                        {
                            MilitaryForce m = new MilitaryForce(SIDE.valueOf(token[0]), token[1], Integer.parseInt(token[2]), Integer.parseInt(token[3]), Boolean.parseBoolean(token[5]));
                            Board.instance().stacks.NewMilitaryForce(Integer.parseInt(token[4]), m);
                        }
                        break;
                    case 4:  // possessions
                        if(token[0].charAt(0) == '%')
                            state++;
                        else
                        {
                            Character c = Board.instance().stacks.GetCharacterByName(token[1]);
                            Possession pos = Game.instance().GetPossession( token[3] );
                            if(c != null && pos != null)
                            {
                                pos.SetCharacterAssignment(token[1]);
                                c.AddPossession(pos);
                            }
                        }
                        break;
                    case 5:  // camera
                        if(token[0].charAt(0) == '%')
                            state++;
                        else
                        {
                            if(token[0].equalsIgnoreCase("zoom"))
                            {
                                Game.instance().startzoom = Float.parseFloat(token[1]);
                            }
                            if(token[0].equalsIgnoreCase("x"))
                            {
                                Game.instance().startx = Float.parseFloat(token[1]);
                            }
                            if(token[0].equalsIgnoreCase("y"))
                            {
                                Game.instance().starty = Float.parseFloat(token[1]);
                            }
                        }
                        break;
                    default:
                        break;
                }
                
            }
            //Close the input stream
            in.close();
        
        }catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    public static void InitEgrix()
    {
        Character character;
        MilitaryForce military;
        Possession pos;
        int stackID;
        
        //set max turns and victory points 
        Game.instance().SetMaxTurns(6);
        Game.instance().SetVictoryPoints(12);
        
        //--------------------------------------------------------------------
        //init characters and put them in environ 30 for now
        //  if character has possession, add possession
        //add rebel characters
        character = Game.instance().GetCharacter("Boccanegra");

        if(character != null)
        {

            pos = Game.instance().GetPossession("Planetary Privateer");
            pos.SetCharacterAssignment("Boccanegra");
            character.AddPossession(pos);
            
            pos = Game.instance().GetPossession("Cache of Rare Gems");
            pos.SetCharacterAssignment("Boccanegra");
            character.AddPossession(pos);
            
            stackID = Board.instance().stacks.NewCharacter(30, character); // puts in Environ 30 (in Egrix)
        
            character = Game.instance().GetCharacter("Doctor Sontag");
            if(character != null)
                Board.instance().stacks.GetStackObj(stackID).AddToStack(character);

            character = Game.instance().GetCharacter("Frun Sentel");
            if(character != null)
            {
                Board.instance().stacks.GetStackObj(stackID).AddToStack(character);

                pos = Game.instance().GetPossession("Helian Drug");
                pos.SetCharacterAssignment("Frun Sentel");
                character.AddPossession(pos);
                
                pos = Game.instance().GetPossession("Scrambler");
                pos.SetCharacterAssignment("Frun Sentel");
                character.AddPossession(pos);
                
                pos = Game.instance().GetPossession("High Energy Sniper's Rifle");
                pos.SetCharacterAssignment("Frun Sentel");
                character.AddPossession(pos);
            }
            
        }
        
        
        //add imperial characters
        character = Game.instance().GetCharacter("Vans Ka-Tie-A");
        if(character != null)
        {
            pos = Game.instance().GetPossession("Imperial spaceship");
            pos.SetCharacterAssignment("Vans Ka-Tie-A");
            character.AddPossession(pos);
            
            stackID = Board.instance().stacks.NewCharacter(30, character); // puts in Environ 30 (in Egrix)
        
            character = Game.instance().GetCharacter("Jon Kidu");
            if(character != null)
                Board.instance().stacks.GetStackObj(stackID).AddToStack(character);
        }
        
        
        //----------------------------------------------------------
        //Init units and put them in environ 29 for now
        military = new MilitaryForce(SIDE.IMPERIAL, "Line", 3, 2, false);
        stackID = Board.instance().stacks.NewMilitaryForce(29, military);
        military = new MilitaryForce(SIDE.IMPERIAL, "Patrol", 1, 2, false);
        Board.instance().stacks.GetStackObj(stackID).AddToStack(military);
        military = new MilitaryForce(SIDE.IMPERIAL, "Patrol", 1, 2, false);
        Board.instance().stacks.GetStackObj(stackID).AddToStack(military);
        military = new MilitaryForce(SIDE.IMPERIAL, "Patrol", 1, 2, false);
        Board.instance().stacks.GetStackObj(stackID).AddToStack(military);
        military = new MilitaryForce(SIDE.IMPERIAL, "Militia", 1, 0, false);
        Board.instance().stacks.GetStackObj(stackID).AddToStack(military);
        // Init enemy military units in environ 29 for testing
        military = new MilitaryForce(SIDE.REBEL, "Line", 3, 2, false);
        stackID = Board.instance().stacks.NewMilitaryForce(29, military);
        military = new MilitaryForce(SIDE.REBEL, "Patrol", 1, 2, false);
        Board.instance().stacks.GetStackObj(stackID).AddToStack(military);
        military = new MilitaryForce(SIDE.REBEL, "Patrol", 1, 2, false);
        Board.instance().stacks.GetStackObj(stackID).AddToStack(military);
        
        
        //----------------------------------------------------------------------
        //planet state
        Planet p = Board.instance().GetPlanetByName("Quibron");

        p.PDBLevel = 1;
        p.controlledby = SIDE.IMPERIAL;
        p.loyalty = Planet.PlanetLoyalty.Loyal;
        
        p = Board.instance().GetPlanetByName("Angoff");
        p.PDBLevel = 2;
        p.controlledby = SIDE.IMPERIAL;
        p.loyalty = Planet.PlanetLoyalty.Neutral;
        
        p = Board.instance().GetPlanetByName("Charkhan");
        p.PDBLevel = 0;
        p.controlledby = SIDE.IMPERIAL;
        p.loyalty = Planet.PlanetLoyalty.Patriotic;
        
        System.out.println("Build Egrix backend setup.");
    }

    public static void InitProvince()
    {
        //set max turns and victory points (made up values)
        Game.instance().SetMaxTurns(10);
        Game.instance().SetVictoryPoints(15);
    }
        
    public static void InitGalactic()
    {
        //set max turns and victory points (made up values)
        Game.instance().SetMaxTurns(20);
        Game.instance().SetVictoryPoints(20);
    }
}
