/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fitg;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 *
 * @author richardpark
 */
public class Parsers {
    //  Method to parse characters
    public List<Character> parseCharacters()
    {  
        List<Character> characters;
        characters = new ArrayList();
        
        
            
        try{
            
            String strLine;
            InputStream in = getClass().getResourceAsStream("charactr.dat");    
            BufferedReader br = new BufferedReader(new InputStreamReader(in));         

            int [] stats = new int[5];
            //System.out.println("Still working");
            while( (strLine = br.readLine() ) != null)
            {   
                Character avatar = null;
                
                String[] token = strLine.split("\\s*,\\s*");
                
                // Modified character initialization to use constructor. Character fields
                // have been changed back to private fields
                /*
                avatar = new Character();
                avatar.name = token[0];
                avatar.imageFile = token[1];
                avatar.title = token[2];
                avatar.race = token[3];
                avatar.side = SIDE.valueOf(token[4].toUpperCase());
                avatar.combat = Integer.valueOf(token[5]);
                avatar.endurance = Integer.valueOf(token[6]);
                avatar.intelligence = Integer.valueOf(token[7]);
                avatar.leadership = Integer.valueOf(token[8]);
                avatar.diplomacy = Integer.valueOf(token[9]);
                avatar.navigation = Integer.valueOf(token[10]);
                avatar.homePlanet = token[11];
                avatar.specials = token[12];
                */
//                String name, String image, String title, String race, SIDE side, int combatRating, int enduranceRating, int intelligenceRating,
//            int leadershipRating, int diplomacyRating, int navigationRating, String homePlanet, String special
                avatar = new Character(token[0], token[1], token[2], token[3], SIDE.valueOf(token[4].toUpperCase()), Integer.valueOf(token[5]), Integer.valueOf(token[6])
                        , Integer.valueOf(token[7]), Integer.valueOf(token[8]), Integer.valueOf(token[9]), Integer.valueOf(token[10]), token[11], token[12]);
                
                //System.out.println(avatar.navigation);
                
                /*
                for (int i = 0; i < 6; i++)
                {
                    stats[i] = Integer.parseInt(token[i+6]);
                }
                System.out.println(token[0]);
                avatar = new Character(token[0], token[1], token[2], token[3], SIDE.valueOf(token[4].toUpperCase()), stats[0], stats[1], stats[2], stats[3], stats[4], stats[5], token[11], token[12]);
                */
                characters.add(avatar);
            }
            //Close the input stream
            in.close();
        
        }catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
        return characters;
    }
    
        //  Method to parse characters
    public List<Possession> parsePossessions()
    {  
        List<Possession> possessions;
        possessions = new ArrayList();
            
        try{
            
            String strLine;
            InputStream in = getClass().getResourceAsStream("possessn.dat");    
            BufferedReader br = new BufferedReader(new InputStreamReader(in));         

            int [] stats = new int[5];
            //System.out.println("Still working");
            while( (strLine = br.readLine() ) != null)
            {   
                Possession item;
                Ship ship;

                String[] token = strLine.split("\\s*,\\s*");

                if(token[0].equals("Spaceship"))
                {
                    ship = new Ship(token[1], token[2], Integer.parseInt(token[3]), Integer.parseInt(token[4]), Integer.parseInt(token[5]), Integer.parseInt(token[6]), true );
                    possessions.add(ship);
                }
                else
                {
                    item = new Possession(Possession.GetPossessionType(token[0]), token[1], token[2]);
                    
                    // Add stat items to possession
                    for(int i = 3; i < token.length; i++)
                    {
                        item.AddPossessionStat(token[i]);
                    }
                    
                    possessions.add(item);
                }
            }
            //Close the input stream
            in.close();
        
        }catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
        return possessions;
    }
    

}
