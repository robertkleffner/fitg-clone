
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fitg;
import java.util.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Charlie
 */
public class Action {
    //---------------Values for ActionCard------------\\
    public boolean hasTwo;
    public Mission.MissionType missionSuccess;
    public Mission.MissionType missionSuccess2;
    public String actionEvent;
    public String description;
    public boolean creatureAttacks;
    public boolean noMoreCreatureAttacks;
    public int creatureAttackNumber;
    
    public  List<Mission.MissionType>  SuccessfulMissions = new ArrayList();
    
 
    
    //---------------Constructor, serving as draw function-----\\
    
    //I know this is an ugly, behemoth, but effect was needed as fast as possible
    public Action(int cardNum, Environ.EnvironType envType)
    {
        int cardEnv = 0;
        creatureAttacks = false;
        creatureAttackNumber = 0;
        noMoreCreatureAttacks = false;
        
        if(envType == Environ.EnvironType.Urban)
            cardEnv = 1;
        else if(envType == Environ.EnvironType.Fire || envType == Environ.EnvironType.Liquid
                || envType == Environ.EnvironType.Air || envType == Environ.EnvironType.Sub)
            cardEnv = 2;
        else if(envType == Environ.EnvironType.Wild)
            cardEnv = 3;
            
        switch ( cardNum ){
            case 1:
           {
               switch(cardEnv){
                   case 1:
                   {
                       missionSuccess = Mission.MissionType.Sabotage;
                       missionSuccess2 = Mission.MissionType.GainChars;
                       CoupAbort4();
                       break;
                   }
                   case 2:
                   {
                       missionSuccess = Mission.MissionType.FreePrisoners;
                       missionSuccess = Mission.MissionType.GatherInfo;
                       LocalsHelp();
                       break;
                   }
                   case 3:
                   {
                       missionSuccess = Mission.MissionType.Rebellion;
                       missionSuccess2 = Mission.MissionType.SubvertTroops;
                       NoCreatures();
                       break;
                   }
                   default:
                   {
                       System.out.println("Should never have gotten here. ERROR");
                   }
               }
               break;
           }
           case 2:
           {   
               switch(cardEnv){
                   case 1:
                   {
                       missionSuccess = Mission.MissionType.GainPossessions;
                       missionSuccess2 = Mission.MissionType.GainChars;
                       IrateLocals();
                       break;
                   }
                   case 2:
                   {
                       missionSuccess = Mission.MissionType.Coup;
                       missionSuccess2 = Mission.MissionType.Coup;
                       LocalsHelp();
                       break;
                   }
                   case 3:
                   {
                       missionSuccess = Mission.MissionType.Diplomacy;
                       missionSuccess2 = Mission.MissionType.Steal;
                       ControversialPolitics();
                       break;
                   }
                   default:
                   {
                       System.out.println("Should never have gotten here. ERROR");
                   }
               }
               break;
           }
           case 3:
           {
               switch(cardEnv){
                   case 1:
                   {
                       missionSuccess = Mission.MissionType.Diplomacy;
                       missionSuccess2 = Mission.MissionType.Diplomacy;
                       RebelsChickenOut();
                       break;
                   }
                   case 2:
                   {
                       missionSuccess = Mission.MissionType.GainChars;
                       missionSuccess2 = Mission.MissionType.GatherInfo;
                       WeatherDisturbances();
                       break;
                   }
                   case 3:
                   {
                       missionSuccess = Mission.MissionType.Rebellion;
                       missionSuccess2 = Mission.MissionType.Sabotage;
                       WrongSoldier();
                       break;
                   }
                   default:
                   {
                       System.out.println("Should never have gotten here. ERROR");
                   }
               }
               break;
           }
           case 4:
           {
               switch(cardEnv){
                   case 1:
                   {
                       missionSuccess = Mission.MissionType.GainPossessions;
                       missionSuccess2 = Mission.MissionType.GainPossessions;
                       EnemyReveals();
                       break;
                   }
                   case 2:
                   {
                       missionSuccess = Mission.MissionType.StartRebelCamp;
                       missionSuccess2 = Mission.MissionType.Steal;
                       IrateLocals();
                       break;
                   }
                   case 3:
                   {
                       missionSuccess = Mission.MissionType.Coup;
                       missionSuccess2 = Mission.MissionType.Coup;
                       CreatureAttack(0);
                       break;
                   }
                   default:
                   {
                       System.out.println("Should never have gotten here. ERROR");
                   }
               }
               break;
           }
           case 5:
           {
               switch(cardEnv){
                   case 1:
                   {
                       missionSuccess = Mission.MissionType.Sabotage;
                       missionSuccess2 = Mission.MissionType.Steal;
                       LocalsHelp();
                       break;
                   }
                   case 2:
                   {
                       missionSuccess = Mission.MissionType.GainChars;
                       missionSuccess2 = Mission.MissionType.GainPossessions;
                       CreatureAttack(1);
                       break;
                   }
                   case 3:
                   {
                       missionSuccess = Mission.MissionType.StartRebelCamp;
                       missionSuccess2 = Mission.MissionType.StartRebelCamp;
                       CivilWar();
                       break;
                   }
                   default:
                   {
                       System.out.println("Should never have gotten here. ERROR");
                   }
               }
               break;
           }
           case 6:
           {
               switch(cardEnv){
                   case 1:
                   {
                       missionSuccess = Mission.MissionType.Coup;
                       missionSuccess2 = Mission.MissionType.Sovereign;
                       CommitAttrocityAdv();
                       break;
                   }
                   case 2:
                   {
                       missionSuccess = Mission.MissionType.GainPossessions;
                       missionSuccess2 = Mission.MissionType.GainPossessions;
                       CivilWar();
                       break;
                   }
                   case 3:
                   {
                       missionSuccess = Mission.MissionType.Rebellion;
                       missionSuccess2 = Mission.MissionType.Rebellion;
                       LocalsHelp();
                       break;
                   }
                   default:
                   {
                       System.out.println("Should never have gotten here. ERROR");
                   }
               }
               break;
           }
           case 7:
           {
               switch(cardEnv){
                   case 1:
                   {
                       missionSuccess = Mission.MissionType.Diplomacy;
                       missionSuccess2 = Mission.MissionType.QuestionPrisoners;
                       CoupAbort6();
                       break;
                   }
                   case 2:
                   {
                       missionSuccess = Mission.MissionType.FreePrisoners;
                       missionSuccess2 = Mission.MissionType.GainChars;
                       BadFoodNBD();
                       break;
                   }
                   case 3:
                   {
                       missionSuccess = Mission.MissionType.StartRebelCamp;
                       missionSuccess2 = Mission.MissionType.Sovereign;
                       PopulaceGoesWild();
                       break;
                   }
                   default:
                   {
                       System.out.println("Should never have gotten here. ERROR");
                   }
               }
               break;
           }
           case 8:
           {
               switch(cardEnv){
                   case 1:
                   {
                       missionSuccess = Mission.MissionType.FreePrisoners;
                       missionSuccess2 = Mission.MissionType.GatherInfo;
                       CharactersDetectedSearch();
                       break;
                   }
                   case 2:
                   {
                       missionSuccess = Mission.MissionType.Assassination;
                       missionSuccess2 = Mission.MissionType.Spaceship;
                       RumorsNBD();
                       break;
                   }
                   case 3:
                   {
                       missionSuccess = Mission.MissionType.Coup;
                       missionSuccess2 = Mission.MissionType.SubvertTroops;
                       CharactersDetectedSearch();
                       break;
                   }
                   default:
                   {
                       System.out.println("Should never have gotten here. ERROR");
                   }
               }
               break;
           }
           case 9:
           {
               switch(cardEnv){
                   case 1:
                   {
                       missionSuccess = Mission.MissionType.StartRebelCamp;
                       missionSuccess2 = Mission.MissionType.Sovereign;
                       ControversialPolitics();
                       break;
                   }
                   case 2:
                   {
                       missionSuccess = Mission.MissionType.GainPossessions;
                       missionSuccess2 = Mission.MissionType.GainPossessions;
                       LocalsHelp();
                       break;
                   }
                   case 3:
                   {
                       missionSuccess = Mission.MissionType.Rebellion;
                       missionSuccess2 = Mission.MissionType.QuestionPrisoners;
                       IrateLocals();
                       break;
                   }
                   default:
                   {
                       System.out.println("Should never have gotten here. ERROR");
                   }
               }
               break;
           }
           case 10:
           {
               switch(cardEnv){
                   case 1:
                   {
                       missionSuccess = Mission.MissionType.Rebellion;
                       missionSuccess2 = Mission.MissionType.Sovereign;
                       LocalsHelp();
                       break;
                   }
                   case 2:
                   {
                       missionSuccess = Mission.MissionType.FreePrisoners;
                       missionSuccess2 = Mission.MissionType.GainPossessions;
                       ConfusingLocal();
                       break;
                   }
                   case 3:
                   {
                       missionSuccess = Mission.MissionType.Coup;
                       missionSuccess2 = Mission.MissionType.Sabotage;
                       FindEnemySquad();
                       break;
                   }
                   default:
                   {
                       System.out.println("Should never have gotten here. ERROR");
                   }
               }
               break;
           }
           case 11:
           {
               switch(cardEnv){
                   case 1:
                   {
                       missionSuccess = Mission.MissionType.GainChars;
                       missionSuccess2 = Mission.MissionType.GatherInfo;
                       PopulaceGoesWild();
                       break;
                   }
                   case 2:
                   {
                       missionSuccess = Mission.MissionType.Rebellion;
                       missionSuccess2 = Mission.MissionType.Rebellion;
                       CharactersDetectedSearch();
                       break;
                   }
                   case 3:
                   {
                       missionSuccess = Mission.MissionType.Sabotage;
                       missionSuccess2 = Mission.MissionType.Sovereign;
                       CreatureAttack(2);
                       break;
                   }
                   default:
                   {
                       System.out.println("Should never have gotten here. ERROR");
                   }
               }
               break;
           }
           case 12:
           {
               switch(cardEnv){
                   case 1:
                   {
                       missionSuccess = Mission.MissionType.Rebellion;
                       missionSuccess2 = Mission.MissionType.Steal;
                       CreatureAttack(0);
                       break;
                   }
                   case 2:
                   {
                       missionSuccess = Mission.MissionType.StartRebelCamp;
                       missionSuccess2 = Mission.MissionType.StartRebelCamp;
                       LocalsRaidEnemy();
                       break;
                   }
                   case 3:
                   {
                       missionSuccess = Mission.MissionType.GainPossessions;
                       missionSuccess2 = Mission.MissionType.GainPossessions;
                       LocalsShelter();
                       break;
                   }
                   default:
                   {
                       System.out.println("Should never have gotten here. ERROR");
                   }
               }
               break;
           }
           case 13:
           {
               switch(cardEnv){
                   case 1:
                   {
                       missionSuccess = Mission.MissionType.Coup;
                       missionSuccess2 = Mission.MissionType.Coup;
                       WeatherDisturbances();
                       break;
                   }
                   case 2:
                   {
                       missionSuccess = Mission.MissionType.Sabotage;
                       missionSuccess2 = Mission.MissionType.Sovereign;
                       CoupAbort3();
                       break;
                   }
                   case 3:
                   {
                       missionSuccess = Mission.MissionType.FreePrisoners;
                       missionSuccess2 = Mission.MissionType.GatherInfo;
                       LocalsHelp();
                       break;
                   }
                   default:
                   {
                       System.out.println("Should never have gotten here. ERROR");
                   }
               }
               break;
           }
           case 14:
           {
               switch(cardEnv){
                   case 1:
                   {
                       missionSuccess = Mission.MissionType.Diplomacy;
                       missionSuccess2 = Mission.MissionType.Diplomacy;
                       CreatureAttack(1);
                       break;
                   }
                   case 2:
                   {
                       missionSuccess = Mission.MissionType.Coup;
                       missionSuccess2 = Mission.MissionType.Sabotage;
                       WeatherDisturbances();
                       break;
                   }
                   case 3:
                   {
                       missionSuccess = Mission.MissionType.GainPossessions;
                       missionSuccess2 = Mission.MissionType.GatherInfo;
                       CoupAbort1();
                       break;
                   }
                   default:
                   {
                       System.out.println("Should never have gotten here. ERROR");
                   }
               }
               break;
           }
           case 15:
           {
               switch(cardEnv){
                   case 1:
                   {
                       missionSuccess = Mission.MissionType.Rebellion;
                       missionSuccess2 = Mission.MissionType.StartRebelCamp;
                       CoupAbort5();
                       break;
                   }
                   case 2:
                   {
                       missionSuccess = Mission.MissionType.FreePrisoners;
                       missionSuccess2 = Mission.MissionType.FreePrisoners;
                       CommitAttrocityGalatic();
                       break;
                   }
                   case 3:
                   {
                       missionSuccess = Mission.MissionType.Sabotage;
                       missionSuccess2 = Mission.MissionType.Coup;
                       ConfusingLocal();
                       break;
                   }
                   default:
                   {
                       System.out.println("Should never have gotten here. ERROR");
                   }
               }
               break;
           }
           case 16:
           {
               switch(cardEnv){
                   case 1:
                   {
                       missionSuccess = Mission.MissionType.FreePrisoners;
                       missionSuccess2 = Mission.MissionType.GatherInfo;
                       WrongSoldier();
                       break;
                   }
                   case 2:
                   {
                       missionSuccess = Mission.MissionType.Coup;
                       missionSuccess2 = Mission.MissionType.SubvertTroops;
                       Accidents();
                       break;
                   }
                   case 3:
                   {
                       missionSuccess = Mission.MissionType.Sovereign;
                       missionSuccess2 = Mission.MissionType.Sovereign;
                       ControversialPolitics();
                       break;
                   }
                   default:
                   {
                       System.out.println("Should never have gotten here. ERROR");
                   }
               }
               break;
           }
           case 17:
           {
               switch(cardEnv){
                   case 1:
                   {
                       missionSuccess = Mission.MissionType.Assassination;
                       missionSuccess2 = Mission.MissionType.SubvertTroops;
                       CharactersDetectedSearch();
                       break;
                   }
                   case 2:
                   {
                       missionSuccess = Mission.MissionType.Diplomacy;
                       missionSuccess2 = Mission.MissionType.Sovereign;
                       WrongSoldier();
                       break;
                   }
                   case 3:
                   {
                       missionSuccess = Mission.MissionType.GainPossessions;
                       missionSuccess2 = Mission.MissionType.GainPossessions;
                       BadFoodNBD();
                       break;
                   }
                   default:
                   {
                       System.out.println("Should never have gotten here. ERROR");
                   }
               }
               break;
           }
           case 18:
           {
               switch(cardEnv){
                   case 1:
                   {
                       missionSuccess = Mission.MissionType.Coup;
                       missionSuccess2 = Mission.MissionType.Spaceship;
                       IrateLocals();
                       break;
                   }
                   case 2:
                   {
                       missionSuccess = Mission.MissionType.StartRebelCamp;
                       missionSuccess2 = Mission.MissionType.SubvertTroops;
                       RebelsChickenOut();
                       break;
                   }
                   case 3:
                   {
                       missionSuccess = Mission.MissionType.GainPossessions;
                       missionSuccess2 = Mission.MissionType.GainChars;
                       WeatherDisturbances();
                       break;
                   }
                   default:
                   {
                       System.out.println("Should never have gotten here. ERROR");
                   }
               }
               break;
           }
           case 19:
           {
               switch(cardEnv){
                   case 1:
                   {
                       missionSuccess = Mission.MissionType.SubvertTroops;
                       missionSuccess2 = Mission.MissionType.SubvertTroops;
                       RumorsNBD();
                       break;
                   }
                   case 2:
                   {
                       missionSuccess = Mission.MissionType.FreePrisoners;
                       missionSuccess2 = Mission.MissionType.FreePrisoners;
                       FindEnemySquad();
                       break;
                   }
                   case 3:
                   {
                       missionSuccess = Mission.MissionType.StartRebelCamp;
                       missionSuccess2 = Mission.MissionType.Spaceship;
                       WeatherDisturbances();
                       break;
                   }
                   default:
                   {
                       System.out.println("Should never have gotten here. ERROR");
                   }
               }
               break;
           }
           case 20:
           {
               switch(cardEnv){
                   case 1:
                   {
                       missionSuccess = Mission.MissionType.StartRebelCamp;
                       missionSuccess2 = Mission.MissionType.StartRebelCamp;
                       ConfusingLocal();
                       break;
                   }
                   case 2:
                   {
                       missionSuccess = Mission.MissionType.Coup;
                       missionSuccess2 = Mission.MissionType.Sovereign;
                       EnemyReveals();
                       break;
                   }
                   case 3:
                   {
                       missionSuccess = Mission.MissionType.GainChars;
                       missionSuccess2 = Mission.MissionType.GatherInfo;
                       ConfusingLocal();
                       break;
                   }
                   default:
                   {
                       System.out.println("Should never have gotten here. ERROR");
                   }
               }
               break;
           }
           case 21:
           {
               switch(cardEnv){
                   case 1:
                   {
                       missionSuccess = Mission.MissionType.FreePrisoners;
                       missionSuccess2 = Mission.MissionType.GainChars;
                       CharactersDetected();
                       break;
                   }
                   case 2:
                   {
                       missionSuccess = Mission.MissionType.Diplomacy;
                       missionSuccess2 = Mission.MissionType.Diplomacy;
                       PopulaceGoesWild();
                       break;
                   }
                   case 3:
                   {
                       missionSuccess = Mission.MissionType.Assassination;
                       missionSuccess2 = Mission.MissionType.Steal;
                       CreatureAttack(1);
                       break;
                   }
                   default:
                   {
                       System.out.println("Should never have gotten here. ERROR");
                   }
               }
               break;
           }
           case 22:
           {
               switch(cardEnv){
                   case 1:
                   {
                       missionSuccess = Mission.MissionType.Diplomacy;
                       missionSuccess2 = Mission.MissionType.Sabotage;
                       LocalsRaidEnemy();
                       break;
                   }
                   case 2:
                   {
                       missionSuccess = Mission.MissionType.Rebellion;
                       missionSuccess2 = Mission.MissionType.QuestionPrisoners;
                       CharactersDetectedSearch();
                       break;
                   }
                   case 3:
                   {
                       missionSuccess = Mission.MissionType.FreePrisoners;
                       missionSuccess2 = Mission.MissionType.FreePrisoners;
                       RebelsChickenOut();
                       break;
                   }
                   default:
                   {
                       System.out.println("Should never have gotten here. ERROR");
                   }
               }
               break;
           }
           case 23:
           {
               switch(cardEnv){
                   case 1:
                   {
                       missionSuccess = Mission.MissionType.GainPossessions;
                       missionSuccess2 = Mission.MissionType.GatherInfo;
                       CreatureAttack(2);
                       break;
                   }
                   case 2:
                   {
                       missionSuccess = Mission.MissionType.Sabotage;
                       missionSuccess2 = Mission.MissionType.Coup;
                       CharactersDetected();
                       break;
                   }
                   case 3:
                   {
                       missionSuccess = Mission.MissionType.Rebellion;
                       missionSuccess2 = Mission.MissionType.GainChars;
                       LocalsRaidEnemy();
                       break;
                   }
                   default:
                   {
                       System.out.println("Should never have gotten here. ERROR");
                   }
               }
               break;
           }
           case 24:
           {
               switch(cardEnv){
                   case 1:
                   {
                       missionSuccess = Mission.MissionType.Sabotage;
                       missionSuccess2 = Mission.MissionType.Coup;
                       BadFoodNBD();
                       break;
                   }
                   case 2:
                   {
                       missionSuccess = Mission.MissionType.Rebellion;
                       missionSuccess2 = Mission.MissionType.Rebellion;
                       CoupAbort5();
                       break;
                   }
                   case 3:
                   {
                       missionSuccess = Mission.MissionType.FreePrisoners;
                       missionSuccess2 = Mission.MissionType.GainChars;
                       CharactersDetectedSearch();
                       break;
                   }
                   default:
                   {
                       System.out.println("Should never have gotten here. ERROR");
                   }
               }
               break;
           }
           case 25:
           {
               switch(cardEnv){
                   case 1:
                   {
                       missionSuccess = Mission.MissionType.GainChars;
                       missionSuccess2 = Mission.MissionType.GainChars;
                       CivilWar();
                       break;
                   }
                   case 2:
                   {
                       missionSuccess = Mission.MissionType.Rebellion;
                       missionSuccess2 = Mission.MissionType.Sabotage;
                       WeatherDisturbances();
                       break;
                   }
                   case 3:
                   {
                       missionSuccess = Mission.MissionType.Diplomacy;
                       missionSuccess2 = Mission.MissionType.Diplomacy;
                       PopulaceGoesWild();
                       break;
                   }
                   default:
                   {
                       System.out.println("Should never have gotten here. ERROR");
                   }
               }
               break;
           }
           case 26:
           {
               switch(cardEnv){
                   case 1:
                   {
                       missionSuccess = Mission.MissionType.Coup;
                       missionSuccess2 = Mission.MissionType.Sabotage;
                       LocalsHelp();
                       break;
                   }
                   case 2:
                   {
                       missionSuccess = Mission.MissionType.Diplomacy;
                       missionSuccess2 = Mission.MissionType.GainChars;
                       CoupAbort4();
                       break;
                   }
                   case 3:
                   {
                       missionSuccess = Mission.MissionType.FreePrisoners;
                       missionSuccess2 = Mission.MissionType.GainPossessions;
                       RumorsNBD();
                       break;
                   }
                   default:
                   {
                       System.out.println("Should never have gotten here. ERROR");
                   }
               }
               break;
           }
           case 27:
           {
               switch(cardEnv){
                   case 1:
                   {
                       missionSuccess = Mission.MissionType.FreePrisoners;
                       missionSuccess2 = Mission.MissionType.GainPossessions;
                       LocalsRaidEnemy();
                       break;
                   }
                   case 2:
                   {
                       missionSuccess = Mission.MissionType.Rebellion;
                       missionSuccess2 = Mission.MissionType.Rebellion;
                       CreatureAttack(2);
                       break;
                   }
                   case 3:
                   {
                       missionSuccess = Mission.MissionType.Sabotage;
                       missionSuccess2 = Mission.MissionType.Steal;
                       CharactersDetected();
                       break;
                   }
                   default:
                   {
                       System.out.println("Should never have gotten here. ERROR");
                   }
               }
               break;
           }
           case 28:
           {
               switch(cardEnv){
                   case 1:
                   {
                       missionSuccess = Mission.MissionType.Assassination;
                       missionSuccess2 = Mission.MissionType.Assassination;
                       LocalsShelter();
                       break;
                   }
                   case 2:
                   {
                       missionSuccess = Mission.MissionType.StartRebelCamp;
                       missionSuccess2 = Mission.MissionType.Sovereign;
                       CreatureAttack(1);
                       break;
                   }
                   case 3:
                   {
                       missionSuccess = Mission.MissionType.FreePrisoners;
                       missionSuccess2 = Mission.MissionType.StartRebelCamp;
                       CoupAbort2();
                       break;
                   }
                   default:
                   {
                       System.out.println("Should never have gotten here. ERROR");
                   }
               }
               break;
           }
           case 29:
           {
               switch(cardEnv){
                   case 1:
                   {
                       missionSuccess = Mission.MissionType.Rebellion;
                       missionSuccess2 = Mission.MissionType.Steal;
                       CharactersDetectedSearch();
                       break;
                   }
                   case 2:
                   {
                       missionSuccess = Mission.MissionType.FreePrisoners;
                       missionSuccess2 = Mission.MissionType.GatherInfo;
                       PopulaceGoesWild();
                       break;
                   }
                   case 3:
                   {
                       missionSuccess = Mission.MissionType.StartRebelCamp;
                       missionSuccess2 = Mission.MissionType.StartRebelCamp;
                       CreatureAttack(2);
                       break;
                   }
                   default:
                   {
                       System.out.println("Should never have gotten here. ERROR");
                   }
               }
               break;
           }
           case 30:
           {
               switch(cardEnv){
                   case 1:
                   {
                       missionSuccess = Mission.MissionType.FreePrisoners;
                       missionSuccess2 = Mission.MissionType.FreePrisoners;
                       PopulaceGoesWild();
                       break;
                   }
                   case 2:
                   {
                       missionSuccess = Mission.MissionType.Sabotage;
                       missionSuccess2 = Mission.MissionType.Steal;
                       CreatureAttack(0);
                       break;
                   }
                   case 3:
                   {
                       missionSuccess = Mission.MissionType.Diplomacy;
                       missionSuccess2 = Mission.MissionType.Diplomacy;
                       EnemyReveals();
                       break;
                   }
                   default:
                   {
                       System.out.println("Should never have gotten here. ERROR");
                   }
               }
               break;
           }
           default:
           {
               System.out.println("Rolled an invalid number on ActionCard Draw.");
               break;
           }
        }
        SuccessfulMissions.add(missionSuccess);
        SuccessfulMissions.add(missionSuccess2);
    }
    

       
   public void Accidents()
   {
     System.out.println("Performing Accidents Will Happen, assign a wound to any one character."); 
     JOptionPane.showMessageDialog(null, "Accidents Will Happen... ");
     
     //Assign a single wound to any character in any active mission group
     
   }
   
   public void CreatureAttack(String CreatureName)
   {
     System.out.println("Performing Creature Attack. Creature Name: " + CreatureName);
     JOptionPane.showMessageDialog(null, "Creature Attack " + CreatureName);
     
     
   }
   
   public void CreatureAttack(int numSentrys)
   {
     System.out.println("Performing Creature Attack. NO named creature so fight " + numSentrys + " robotic sentrys");
     //JOptionPane.showMessageDialog(null, "Creature Attack! If there is no creature, fight " + numSentrys + " robotic sentrys");
     this.creatureAttacks = !this.noMoreCreatureAttacks;
     this.creatureAttackNumber = numSentrys;
   }
    
   public void CommitAttrocityAdv()
   {
       System.out.println("Performing Attrocity (advanced game only).");
       JOptionPane.showMessageDialog(null, "Performing Attrocity (advanced game only).");
       //Implementation being skipped until we get past StarSystem game
   }
   
   public void CommitAttrocityGalatic()
   {
       System.out.println("Performing Attrocity (Galatic game only).");
       JOptionPane.showMessageDialog(null, "Performing Attrocity (Galatic game only).");
       //Implementation being skipped until Galactic game
   }
   
   public void CharactersDetected()
   {
       System.out.println("Performing Characters Detected.");
       JOptionPane.showMessageDialog(null, "Characters Detected");
       
       //All characters in active mission groups detected
       List<Character> activeChars = Mission.GetAssignedChars();
       for (Character c : activeChars)
       {
           if(c.GetIsInPlay())
               c.SetDetected(true);
       }
   }
   
   public void CharactersDetectedSearch()
   {
       System.out.println("Performing Characters Detected and Search");
       JOptionPane.showMessageDialog(null, "Performing Characters Detected and Search");
       
       //All characters in active mission groups detected
       List<Character> activeChars = Mission.GetAssignedChars();
       for (Character c : activeChars)
       {
           if(c.GetIsInPlay())
               c.SetDetected(true);
       }
       
       //Non-Phasing player is allowed to conduct a search
       //TODO:
   }
   
   
   public void RumorsNBD()
   {
       System.out.println("Performing Delayed by Rumors and NO bonus draws.");
       JOptionPane.showMessageDialog(null, "Performing Delayed by Rumors and NO bonus draws.");
       
       //TODO: Bonus Draws set to 0 in all active missions
       for (Mission m: Mission.MissionList)
       {
           m.SetBonusDraws(0);
       }
   }
   
   public void ConfusingLocal()
   {
       System.out.println("Performing Confusing Local.");
       JOptionPane.showMessageDialog(null, "Performing Confusing Local.");
   }
   
   public void CoupAbort1()
   {
       System.out.println("Peforming Coup Abort 1 and Characters Detected");
       JOptionPane.showMessageDialog(null, "Coup Abort 1 and Characters Detected");
   }
   
   public void CoupAbort2()
   {
       System.out.println("Performing Coup Abort 2 and Characters Detected.");
       JOptionPane.showMessageDialog(null, "Performing Coup Abort 2 and Characters Detected");
   }
   
   public void CoupAbort3()
   {
       System.out.println("Performing Coup Abort 3 and Characters Detected.");
       JOptionPane.showMessageDialog(null, "Performing Coup Abort 3 and Characters Detected");
   }
   
   public void CoupAbort4()
   {
       System.out.println("Performing Coup Abort 4 and Characters Detected.");
       JOptionPane.showMessageDialog(null, "Performing Coup Abort 4 and Characters Detected");
   }
   
   public void CoupAbort5()
   {
       System.out.println("Performing Coup Abort 5 and Characters Detected.");
       JOptionPane.showMessageDialog(null, "Performing Coup Abort 5 and Characters Detected");
   }
   
   public void CoupAbort6()
   {
       System.out.println("Performing Coup Abort 6 and Characters Detected.");
       JOptionPane.showMessageDialog(null, "Performing Coup Abort 6 and Characters Detected");
   }
   
   public void ControversialPolitics()
   {
       System.out.println("Performaing Controversial Politics.");
       JOptionPane.showMessageDialog(null, "Performing Controversial Politics");
   }
   
   public void CivilWar()
   {
       System.out.println("Performaing Civil War.");
       JOptionPane.showMessageDialog(null, "Performing Civil War");
   }
   
   public void BadFoodNBD()
   {
       System.out.println("Performing Disagreeable food and no bonus draws.");
       JOptionPane.showMessageDialog(null, "Performing Disagreeable food and no bonus draws");
       
       //TODO: Set bonus draws to 0 in all active missions
   }
   
   public void EnemyReveals()
   {
       System.out.println("Performing Enemy Reveals Location, characters detected.");
       JOptionPane.showMessageDialog(null, "Performing Enemy Reveals Location, characters detected");
   }
   
   public void IrateLocals()
   {
       System.out.println("Performaing Irate Locals.");
       JOptionPane.showMessageDialog(null, "Performing Irate Locals");
       
       this.creatureAttacks = true;
       this.creatureAttackNumber = -1;
       
   }
   
   public void LocalsHelp()
   {
       System.out.println("Performaing Locals Expedite mission.");
       JOptionPane.showMessageDialog(null, "Performing Locals Expedite Mission");
   }
   
   public void LocalsRaidEnemy()
   {
       System.out.println("GOOD NEWS EVERYONE! Locals raided the enemy!");
       JOptionPane.showMessageDialog(null, "Performing Locals raided the enemy!");
   }
   
   public void LocalsShelter()
   {
       System.out.println("GOOD NEWS EVERYONE! Locals sheltered you from searches!");
       JOptionPane.showMessageDialog(null, "Performing Locals sheltered you from searches!");
   }
   
   public void FindEnemySquad()
   {
           System.out.println("Performaing Stumbled onto enemy squad.");
           JOptionPane.showMessageDialog(null, "Performing Stumbled onto enemy squad.");
   }
   
   public void NoCreatures()
   {
       System.out.println("GOOD NEWS EVERYONE! No more creature attacks in this environ!");
       JOptionPane.showMessageDialog(null, "Performing no more creature attacks in this environ!");
       noMoreCreatureAttacks = true;
   }
   
   public void PopulaceGoesWild()
   {
       System.out.println("Performing Populace Goes Wild!");
       JOptionPane.showMessageDialog(null, "Performing Populace Goes Wild!");
   }
   
   public void RebelsChickenOut()
   {
       System.out.println("Performing Rebels Chicken Out. Characters Detected.");
       JOptionPane.showMessageDialog(null, "Performing Rebels Chicken Out and Characters Detected");
   }
   
   public void WeatherDisturbances()
   {
       System.out.println("Performing Weather Disturbances hamper enemy. No enemy searches in this environ.");
       JOptionPane.showMessageDialog(null, "Performing Weather Disturbances");
   }
   
   public void WrongSoldier()
   {
       System.out.println("Performing Wrong Soldier Contacted. Subvert Troops aborted.");
       JOptionPane.showMessageDialog(null, "Performing Wrong Soldier");
   }
}



