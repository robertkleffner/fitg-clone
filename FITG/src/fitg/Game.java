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
public class Game
{
    private static Game _instance = null;
    
    public Turn turn;
    public Board map;
    public IOControl io;
    private List<Character> characters;
    private List<Possession> possessions;
    private int maxTurns;        // Max turns for game
    private int victoryPoints;     // Victory points required to win
    static Parsers parse;
    public boolean gameOver=false;
    public GAMETYPE gameType;
    public String scenario;
    public float startzoom;
    public float startx;
    public float starty;
    public String winner;
    public Boolean rebeltakeover;
    
    private Game()
    {
        winner = "Imperial";
        rebeltakeover = false;
        
        
        //gameType = GAMETYPE.PROVINCE;
        gameType = GAMETYPE.GALACTIC;
        //gameType = GAMETYPE.STARSYSTEM;
        
        // Initialize game map
        map = Board.instance();
        
        // Initialize Input/Output 
        io = new IOControl();
        
        // Initialize parser
        parse = new Parsers();
        
        // Initialize Character list;
        characters = new ArrayList();
        
        // Fill list with Characters.
        characters = parse.parseCharacters();
        // Fill list with Possessions.        
        possessions = parse.parsePossessions();
        
        maxTurns = 2;
        
        // Create Rebel Player
        Player rebelPlayer = new Player(SIDE.REBEL);

        // Create Imperial Player
        Player imperialPlayer = new Player(SIDE.IMPERIAL);
        

        // Create Turn object
        turn = new Turn(rebelPlayer, imperialPlayer);
        
        
    }
    
    public static Game instance() {
        if (_instance == null) {
            _instance = new Game();
        }
        return _instance;
    }
    
    //does not setup anything specific - that is done in Start phase in GUI.java
    public void SetupGame()
    { 
        // Initialize game map
        map = Board.instance();
        
        // Initialize Input/Output 
        io = new IOControl();
        
        parse = new Parsers();
        
        // Initialize Character list;
        characters = new ArrayList();
        
        // Fill list with Characters.
        characters = parse.parseCharacters();
        // Fill list with Possessions.        
        possessions = parse.parsePossessions();
        
        // Create Rebel Player
        Player rebelPlayer = new Player(SIDE.REBEL);

        // Create Imperial Player
        Player imperialPlayer = new Player(SIDE.IMPERIAL);
        
        // Create Turn object
        turn = new Turn(rebelPlayer, imperialPlayer);
        turn.phase = Phase.Start;
    }
    
    //game/turn sequence goes here
    public void RunGame()
    {
        System.out.println("Begin Run Game");
      
        if(gameType == GAMETYPE.STARSYSTEM)
        {
            System.out.println("In starsytem");
            while((turn.GetGameRound() < maxTurns)&&(!gameOver))
            {
                io.println("Begin StarSystem Game");
                turn.StarSystemTurn();
            }
        }
        else if(gameType == GAMETYPE.PROVINCE)
        {
            io.println("num of prov: " + map.GetProvinces().size());
            //io.println("num of locations/environs: " + map.locations.GetLocationListSize());
            io.println("num systems in 0st prov: " + map.GetProvinceByID(0).GetSystems().size());
            io.println("num systems in 1st prov: " + map.GetProvinceByID(1).GetSystems().size());
            io.println("num systems in 2st prov: " + map.GetProvinceByID(2).GetSystems().size());
            io.println("num systems in 3st prov: " + map.GetProvinceByID(3).GetSystems().size());
            io.println("num systems in 4st prov: " + map.GetProvinceByID(4).GetSystems().size());
            io.println("num planet in sys 0: " + map.GetProvinceByID(0).GetSystemObj(5).GetPlanets().size());
            io.println("num planet in sys 0: " + map.GetProvinceByID(1).GetSystemObj(3).GetPlanets().size());
            io.println("num planet in sys 0: " + map.GetProvinceByID(2).GetSystemObj(4).GetPlanets().size()); 
            io.println("num planet in sys 0: " + map.GetProvinceByID(3).GetSystemObj(4).GetPlanets().size()); 
            io.println("num planet in sys 0: " + map.GetProvinceByID(4).GetSystemObj(4).GetPlanets().size()); 
            
            //map.PrintAll();
            //io.println("Loc list size: " + map.locations.GetLocationListSize());
            io.println("envcount size: " + map.GetEnvironCount()); 
            
            map.PrintEnvirons();
        }
        else if(gameType == GAMETYPE.GALACTIC)
        {
            while(turn.GetGameRound() < maxTurns)
            {
                io.println("Begin Galactic Game");
                turn.GalacticTurn();
            }
        }
    }
    
    // returns character with arg name, and removes it from characters
    public Character GetCharacter(String name)
    {
        Character r;
        for(Character c : characters)
        {
            if(name.equals(c.GetName()))
            {
                r = c;
                characters.remove(c);
                return r;
            }
        }
        
        return null;
    }
    //returns random character from the phasing player's side, removes it from characters
    // If no characters at left, it will return null
    public Character GetRandomCharacter()
    {
        Character r = null;
        SIDE currentSide = Game.instance().turn.GetPhasingSide();
        List<Character> phasingChars = new ArrayList();
        
        // Build list containing characters for the phasing side
        for (Character c : characters)
        {
            if (c.GetSide() == currentSide)
            {
                phasingChars.add(c);
            }
        }
        
        // Verify that the list is not empty - return null if it is
        if (phasingChars.isEmpty())
            return null;
        
        // Dice roll for character index - substract off 1 for zero base
        int roll = Tables.DiceRoll(phasingChars.size()) - 1;
        
        // Attempt to get character at index
        // If it fails, just assign the first char in the list
        try
        {
            r = phasingChars.get(roll);
        }
        catch(Exception ex)
        {
            r = phasingChars.get(0);
        }
              
        characters.remove(r);
        
        return r;
    }
    // returns possession with arg name
    // should probably return a copy(?), but it doesn't matter yet
    public Possession GetPossession(String name)
    {
        for(Possession p : possessions)
        {
            if(name.equals(p.GetName()))
            {
                return p;
            }
        }
        
        return null;
    }
    
    public void SetVictoryPoints(int vp){this.victoryPoints = vp;} 
    public void SetMaxTurns(int turns){this.maxTurns = turns;}

    public int GetMaxTurns(){return this.maxTurns;}
}
