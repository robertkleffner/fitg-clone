/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 11-17-2013 Started Creature Class.
 */
package fitg;
import java.util.*;

/**
 *
 * @author Jason
 */

public class CombatCreature
{
	public enum CreatureName
	{
		Alweg, Anons, Arags, Ardorats, Batranoban, Borks, Calmas, Cavalkus, Chantens, Chardireeds, Charkhans, Chlorofix,
		Crunge, Deaxins, Derigion, Dindin, Drants, Drusers, Elilad, Fog, FrostMist, Gach, Gadhars, Gamels,
		Gilekite, Glane, Gragg, Gyrogos, Henones, Hysnatons, Illias, Jopers, Kayns, Kinsog, Kirts, Laboroid,
		Leonids, Leonus, Lomrels, Magron, Mish, Moghas, Morna, Mowevs, Muggers, Namdasn, Onflam, Ornotins, Phans,
		Piorads, Propang, Prox, Queemer, Rhones, Rotron, Rylians, Sandiabs, Saurians, Segundens, Sekekers, SentryRobot,
		SentryRobots, Snorkas, SnowGiants, Spithid, Stromuse, Susperans, Suvans, Synestins, Telebots, Theshians, Thinagig, Thoks,
		Thunk, Ultraks, Urgaks, Ursi, Valaterix, Verfusier, Virus, Vorozion, Vrialta, Wyths, Xanthons, Yesters, YmBarror, Zernipak, Zop,
	}
	
	private CreatureName name;
	private String description;
	private int strength;
	private int intelligence;
	private int endurance;
	private boolean immortal;

	private int combatEndsAfter;

	private boolean breakoff;
	private int breakoffAfter;
	private int surpriseShift;
	private int breakoffRollAdjust;

	private int combatDefendType;
	private boolean firefight;
	private boolean attacksWithINT;
	private boolean standardAttack;

	private boolean attackOnSpecificColumn;
	private int specificColumn;

	private int woundsTaken; // Counts the number of hits against monster.
	private int woundsGiven;
	private boolean gaveWoundsThisRound;

	private boolean combatIsOver;

	// Other variables
	private int combatRound;

// *************************************************
// Stat Getters
//		Should be run ever round to update info, as
// 		it can change.	
// *************************************************

	public String getName()
	{
		return name.toString();
	}
	public String getDescription()
	{
		return description;
	}

	// Do not use these for combat, display only! Use getCombatStat()!
	public int getStrength()
	{
		if( name == CreatureName.Wyths )
		{
			// Should be this: strength - [highest int of mission group, no less than 0 ]
			return strength; // Just for now
		}
		else
		{
			return strength;
		}
	}
	public int getIntelligence()
	{
		return intelligence;
	}
	
	public boolean isImmortal()
	{
		return immortal;
	}

	public int getEndurance()
	{
		return endurance;
	}


// *************************************************
// Combat Info Getters
//		these *all* should be run and accounted for
//		on every combat round/start of combat
// *************************************************

	// Weither or not it's a "normal" character vs creature combat.
	// If False, will likely use SpecialCombat() method (implemented later)
	// If true, do normal mission combat.
	public boolean isStandardAttack()
	{
		return standardAttack;
	}

	// Use this one for doing combat calculations!
	public int getCombatStat()
	{
		if( attacksWithINT )
		{
			return getIntelligence();
		}
		else
		{
			return getStrength();
		}
	}

	public boolean isSurprise()
	{
		if( surpriseShift != 0 )
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public int getSurpriseShift()
	{
		if( combatRound == 1 )
		{
			return surpriseShift;
		}
		else
		{
			return 0;
		}
	}

	public boolean isINTCombat()
	{
		return attacksWithINT;
	}

	public boolean isFirefight()
	{
		return firefight;
	}

	public boolean isAttackWithSpecificColumn()
	{
		return attackOnSpecificColumn;
	}
	public int getSpecificColumn()
	{
		return specificColumn;
	}

	// Returns 0 for normal, 1 for int, 2 for combined int.
	public int defendType()
	{
		return combatDefendType;
	}

	// Use to check if combat is over, especially if over prematurely.
	public boolean isCombatOver()
	{
		return combatIsOver;
	}

	// Mostly for testing purposes
	public int getCombatRoundCounter()
	{
		return combatRound;
	}

	// Use to check if you can breakoff or not.
	public boolean canBreakoff()
	{
		if( breakoff == true && combatRound > breakoffAfter )
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public int getBreakoffRollAdjust()
	{
		if( name == CreatureName.Gyrogos )
		{
			if( combatRound > 1 )
			{
				return 1;
			}
		}
		else if( name == CreatureName.Propang )
		{
			if( combatRound == 1 )
			{
				return 1;
			}
		}
		
		return breakoffRollAdjust;
	}


// *************************************************
// Combat Methods
// *************************************************

	// Should be run once at the start of combat.
	public void StartOfCombat()
	{
		combatRound = 1;
		woundsGiven = 0;
		woundsTaken = 0;
		switch( name )
		{
			case Magron:
				// select one character at random.
				// They are removed from combat and used to be attacked each round.
				combatIsOver = true;
				break;
			case Morna:
				/*if( characters greatest Diplomacy >= 3 )
					// display alert: Disagreement with Morna resolved Diplomatically
					end combant now without combat */
				combatIsOver = true;
				break;
			case Rotron:
				/* if (no spaceship belongs to characters)
					no effect */
				combatIsOver = true;
				break;
		}
	}

	// Should be run at the start of every combat round.
	public void StartRound()
	{
		gaveWoundsThisRound = false;

		if( name == CreatureName.Virus )
		{
			// Set all characters to active defenders
		}
	}

	// Should be run at the end of every combat round.
	public void EndRound()
	{

		// Creature Specific Actions
		if (endurance > 0) // I'm not dead yet!
		{
			switch(name)
			{
				case Chantens:
					if( combatRound > 1 )
					{
						// Display alert: More trees arrive!
						strength += 1;
						endurance += 1;
					}
					break;
				case Gach:
					// display message: The Gach attacks itself!
					//apply wounds to itself on the +1 column
					break;
				case Gadhars:
					if( combatRound > 2 )
					{
						if( combatRound % 2 == 1 ) // Odd combat round
						{
							// display message: More Gadhars appear!
							strength += 2;
							endurance += 2;
						}
					}
					break;
				case Hysnatons:
					if( combatRound > 2 )
					{
						// No Bonus Draws for Current Mission
						// Combat continues.
					}
					break;
				case Magron:
					// apply 1 wound to grabbed character
					break;
				case Muggers:
					if( combatRound >= 3 )
					{
						// combat ends
						// lose one non ship possession at random
						// display alert: Muggers stole [item]!
					}
					break;
				case Thunk:
					if( combatRound > 1 )
					{
						strength += 1;
					}
					break;
				case Vorozion:
					if( !gaveWoundsThisRound ) // If it did not apply wounds this round
					{
						combatIsOver = true;
					}
					break;
			}
		}
		else if( endurance <= 0 )
		{
			combatIsOver = true;
		}
		combatRound++;
		if( combatRound > combatEndsAfter)
		{
			combatIsOver = true;
		}
	}

	// Needs to be run at the end of combat, if the player won.
	public void endCombat()
	{
		// Synestins: REBELS get planetary shift in their favor by 1 upon victory.
	}

	// Needs to be run for each wound the creature causes
	public void gaveWounds()
	{
		gaveWoundsThisRound = true;
		woundsGiven++;

		switch( name )
		{
			case Dindin: 
				// gains +1 STR for each of the first two wounds.
				if( woundsGiven <= 2 )
				{
					strength += 1;
				}
				break;
		}

	}

	// Run each time a wound is taken.
	private void tookWounds()
	{
		woundsTaken++;
		// Queemer gains +1 STR for each wound taken (done in applyWounds() )
	}

	public void applyWounds( int wounds )
	{
		if( name != CreatureName.Queemer )
		{
			strength = strength - wounds;
		}
		else if( name == CreatureName.Queemer )
		{
			strength = strength + wounds;
		}

		if( !immortal )
		{
			endurance = endurance - wounds;
		}

		if( strength < 0 )
		{
			strength = 0;
		}

		if( endurance <= 0 )
		{
			combatIsOver = true;
			endurance = 0;
		}

		while( wounds > 0 )
		{
			tookWounds();
			wounds--;
		}
	}

// *************************************************
// The Ridiculous Constructor
// *************************************************
	public CombatCreature( CreatureName n , Environ.EnvironType e )
	{
		// Special Constructor Names
		int alwaysAvailable = 0;
		int neverAvailable = 999;
		int immortalhp = 999;
		int doesNotEnd = 999;

		// Start Setting Actual Variables
		name = n;
		
		// Default values
		strength = 0;
		endurance = 0;
		immortal = false;
		intelligence = 0;

		breakoff = true;
		breakoffAfter = alwaysAvailable;
		breakoffRollAdjust = 0;

		combatEndsAfter = doesNotEnd;

		surpriseShift = 0;

		firefight = false;
		standardAttack = true;

		woundsTaken = 0;
		attacksWithINT = false;
		specificColumn = 0;
		attackOnSpecificColumn = false;

		combatIsOver = false;
		
		// Creature Specific Values
		switch( n )
		{
			case Alweg:
				description = "Derailed Public Transport Capsule";
				strength = 8;
				endurance = immortalhp;
				immortal = true;
				combatEndsAfter = 1;
				breakoff = false;
				breakoffAfter = neverAvailable;
				break;
			case Anons:
				description = "Irate Locals";
				strength = 4;
				endurance = 2;
				firefight = true;
				break;
			case Arags:
				description = "Mole People";
				strength = 4;
				endurance = 3;
				break;
			case Ardorats:
				description = "Irate Locals";
				strength = 4;
				endurance = 4;
				break;
			case Batranoban:
				description = "Pterodactyl-like Creature";
				strength = 4;
				endurance = 3;
				breakoffAfter = 1;
				surpriseShift = 1;
				break;
			case Borks:
				description = "Irate Locals";
				strength = 8;
				endurance = 2;
				break;
			case Calmas:
				description = "Irate Locals";
				strength = 5;
				endurance = 4;
				break;
			case Cavalkus:
				description = "Irate Locals";
				strength = 4;
				endurance = 3;
				firefight = true;
				break;
			case Chantens:
				description = "Intelligent Trees";
				strength = 5;
				endurance = 5;
				break;
			case Chardireeds:
				description = "Elastic Shapeshifter";
				strength = 4;
				endurance = 4;
				breakoffAfter = 1;
				surpriseShift = 1;
				break;
			case Charkhans:
				description = "Irate Locals";
				strength = 5;
				endurance = 4;
				break;
			/* case Chlorofix:
				isCombat = false;

				adjustBonusDraws = -1;
				break; */

			case Crunge:
				description = "Ordinary-looking Rock Formation";
				strength = 0;
				endurance = immortalhp;
				immortal = true;
				combatEndsAfter = 1;
				breakoff = false;
				attacksWithINT = true;

				// getHighestIntelligence();
				// Highest Intelligence - 4 = woundsIncurred.
				// Apply woundsIncurred to EACH character.
				standardAttack = false;

				break;
			case Deaxins:
				description = "Irate Locals";
				strength = 5;
				endurance = 5;
				firefight = true;
				break;
			case Derigion:
				description = "Giant Flying Lizard";
				strength = 4;
				endurance = 6;

				break;
			case Dindin:
				description = "Giant Aquatic Carinvore";
				strength = 7;
				endurance = 5;
				breakoffRollAdjust = 1;

				break;
			case Drants:
				description = "Squad of Flying Insects";
				strength = 0;
				endurance = immortalhp;
				immortal = true;
				combatEndsAfter = 1;
				breakoff = false;
				breakoffAfter = neverAvailable;

				// TODO: Characters attacked once with the 0 differentail column.

				break;
			case Drusers:
				description = "Cultists That Worship the Mind";
				strength = 0;
				endurance = 5;
				intelligence = 5;
				attacksWithINT = true;
				// Attacks with INT.
				// Characters use Combined INT.
				// Only Endurance is Reduced.

				break;
			case Elilad:
				description = "Electric Intelligence";
				strength = 0;
				endurance = immortalhp;
				immortal = true;
				attackOnSpecificColumn = true;
				specificColumn = 0;

				// Characters attacked on 0 differentail column until breakoff achieved.
				// Breakoff calculated on 0 column.

				break;
			/* case Fog:
				isCombat = false;

				// if( highestINT < 3 )
				// adjustBonusDraws = no bonus draws;
				
				break; */
			case FrostMist:
				description = "Intelligent Spirit";
				strength = 5;
				endurance = 5;
				standardAttack = false;
				break;
				// Applies 1 Wound to all characters.

			case Gach:
				description = "Two-headed Feline With Conflicting Personalities";
				strength = 7;
				endurance = 6;
				break;
			case Gadhars:
				description = "Pack of Vicious Wolf-Like Creatures";
				strength = 5;
				endurance = 4;
				break;
			case Gamels:
				description = "Furry, One-Horned Quadraped";
				strength = 3;
				endurance = 2;
				break;
			case Gilekite:
				description = "Large Shell Creature";
				strength = 3;
				endurance = 3;
				breakoffAfter = 1;
				surpriseShift = 2;
				break;
			case Glane:
				description = "Vicious Winged Humanoid";
				strength = 6;
				endurance = 6;
				firefight = true;
				break;
			case Gragg:
				description = "Six-legged Lizard";
				strength = 3;
				endurance = 2;
				breakoffAfter = 1;
				surpriseShift = 1;
				break;
			case Gyrogos:
				description = "Invisible Spinning Current";
				strength = 6;
				endurance = immortalhp;
				immortal = true;
				breakoffRollAdjust = 1;
				break;
			case Henones:
				description = "Irate Locals";
				strength = 6;
				endurance = 6;
				break;
			case Hysnatons:
				description = "Hypnotic Sewer Snakes";
				strength = 5;
				endurance = 4;
				break;
			case Illias:
				description = "Irate Locals";
				strength = 4;
				endurance = 4;
				break;
			case Jopers:
				description = "Irate Locals";
				strength = 6;
				endurance = 2;
				break;
			case Kayns:
				description = "Irate Locals";
				if( e == Environ.EnvironType.Wild )
				{
					strength = 7;
					endurance = 6;
				}
				else if( e == Environ.EnvironType.Sub )
				{
					strength = 6;
					endurance = 4;
				}
				break;
			case Kinsog:
				description = "Tunneling Dragon Worm";
				strength = 3;
				endurance = 2;
				breakoffAfter = 1;
				surpriseShift = 2;
				break;
			case Kirts:
				description = "Irate Locals";
				strength = 5;
				endurance = 4;
				break;
			case Laboroid:
				description = "Malfunctioning Worker Robot";
				strength = 3;
				endurance = 3;
				breakoffAfter = 1;
				surpriseShift = 2;
				break;
			case Leonids:
				description = "Irate Locals";
				strength = 6;
				endurance = 4;
				break;
			case Leonus:
				description = "Cross Between Lion and Reptile";
				strength = 5;
				endurance = 4;
				breakoffAfter = 1;
				surpriseShift = 1;
				break;
			case Lomrels:
				description = "Large Rideable Canine";
				strength = 5;
				endurance = 3;
				break;
			case Magron:
				description = "Carniverous Plant";
				strength = 0;
				endurance = 5;
				breakoff = false;
				breakoffAfter = neverAvailable;
				// Applies 1 wound to selected character at end of each round.
				standardAttack = false;
				break;
			case Mish:
				description = "Swarm of Armored Insects";
				strength = 6;
				endurance = 3;
				breakoff = false;
				breakoffAfter = neverAvailable;
				break;
			case Moghas:
				description = "Irate Locals";
				strength = 7;
				endurance = 3;
				firefight = true;
				break;
			case Morna:
				description = "Chieftain of Local Hydro-clan";
				strength = 6;
				endurance = 4;
				break;
			case Mowevs:
				description = "Irate Locals";
				strength = 4;
				endurance = 4;
				firefight = true;
				break;
			case Muggers:
				description = "Local Thieves";
				strength = 5;
				endurance = 4;
				combatEndsAfter = 3;
				breakoff = false;
				breakoffAfter = neverAvailable;
				break;
			case Namdasn:
				description = "Shapeshifting Sentient Sand";
				strength = 4;
				endurance = 4;
				breakoff = false;
				breakoffAfter = neverAvailable;
				surpriseShift = 2;
				break;
			case Onflam:
				description = "Flying, Flaming Being";
				strength = 6;
				endurance = 2;
				breakoff = false;
				breakoffAfter = neverAvailable;
				break;
			case Ornotins:
				description = "Irate Locals";
				strength = 6;
				endurance = 4;
				break;
			case Phans:
				description = "Irate Locals";
				strength = 4;
				endurance = 4;
				break;
			case Piorads:
				description = "Irate Locals";
				if( e == Environ.EnvironType.Urban )
				{
					strength = 4;
					endurance = 4;
				}
				else if( e == Environ.EnvironType.Sub )
				{
					strength = 6;
					endurance = 6;
				}
				break;
			case Propang:
				description = "Overzealous Messenger Robot";
				strength = 5;
				endurance = 3;
				breakoffRollAdjust = 1;
				break;
			case Prox:
				description = "Large, Crawling, Carnivorous Insect";
				strength = 6;
				endurance = 4;
				breakoffRollAdjust = -1;
				break;
			case Queemer:
				description = "Gelatinous Blob";
				strength = 3;
				endurance = 5;
				break;
			case Rhones:
				description = "Irate Locals";
				if( e == Environ.EnvironType.Urban )
				{
					strength = 5;
				}
				else
				{
					strength = 4;
				}
				endurance = 4;
				break;
			case Rotron:
				description = "Metal-Eating Reptile";
				strength = 3;
				endurance = 3;
				combatEndsAfter = 2;
				breakoff = false;
				breakoffAfter = neverAvailable;

				standardAttack = false;
				break;
			/* case Sandiabs:
				description = "Feisty Desert Rats";
				Non-combat
				break; */
			case Rylians:
				description = "Irate Locals";
				strength = 8;
				endurance = 2;
				firefight = true;
				break;
			case Saurians:
				description = "Irate Locals";
				if( e == Environ.EnvironType.Urban )
				{
					strength = 5;
					endurance = 6;
				}
				else if( e == Environ.EnvironType.Wild )
				{
					strength = 5;
					endurance = 4;
				}
				else if( e == Environ.EnvironType.Sub )
				{
					strength = 5;
					endurance = 2;
				}
				else if( e == Environ.EnvironType.Liquid )
				{
					strength = 5;
					endurance = 2;
				}
				break;
			case Segundens:
				description = "Irate Locals";
				if( e == Environ.EnvironType.Urban )
				{
					strength = 6;
					endurance = 4;
				}
				else if( e == Environ.EnvironType.Liquid )
				{
					strength = 5;
					endurance = 2;
				}
			case Sekekers:
				description = "Pack of Rodents";
				strength = 6;
				endurance = 2;
				break;
			case SentryRobot:
				description = "Single Sentry Robot";
				strength = 4;
				endurance = 2;
				break;
			case SentryRobots:
				description = "Two Sentry Robots";
				strength = 7;
				endurance = 4;
				break;
			case Snorkas:
				description = "Sentient Piosonous Seaweed";
				strength = 8;
				endurance = immortalhp;
				immortal = true;
				combatEndsAfter = 1;
				breakoff = false;
				breakoffAfter = neverAvailable;
				break;
			case SnowGiants:
				description = "Big, Evil Guys With Clubs";
				strength = 6;
				endurance = 4;
				break;
			case Spithid:
				description = "Delicate Spider-like Creature";
				strength = 6;
				endurance = 1;
				break;
			case Stromuse:
				description = "Surprise Passerby Attacker";
				strength = 2;
				endurance = 2;
				breakoffAfter = 1;
				surpriseShift = 3;
				break;
			case Susperans:
				description = "Irate Locals";
				strength = 4;
				endurance = 4;
				break;
			case Suvans:
				description = "Irate Locals";
				if( e == Environ.EnvironType.Urban )
				{
					strength = 6;
					endurance = 2;
				}
				else if( e == Environ.EnvironType.Liquid )
				{
					strength = 7;
					endurance = 4;
				}
				break;
			case Synestins:
				description = "Religious Sect Members";
				// Display message if rebel that they are controlling locals?
				strength = 5;
				endurance = 4;
				// Can initiate domino effect for Rebels
				break;
			case Telebots:
				description = "Overloaded Information Android";
				strength = 0;
				endurance = 3;
				intelligence = 3;

				attacksWithINT = true;
				break;
			case Theshians:
				description = "Irate Locals";
				strength = 4;
				endurance = 2;
				break;
			case Thinagig:
				description = "Human-like Being of Malleable Stone";
				strength = 8;
				endurance = 8;
				breakoffRollAdjust = -1;
				break;
			case Thoks:
				description = "Irate Locals";
				strength = 6;
				endurance = 5;
				firefight = true;
				break;
			case Thunk:
				description = "Savage Wooly Mammoth";
				strength = 5;
				endurance = 4;
				break;
			/*case Valaterix:
				non-combat;
			*/
			case Ultraks:
				description = "Irate Locals";
				strength = 4;
				endurance = 4;
				break;
			case Urgaks:
				description = "Irate Locals";
				strength = 6;
				endurance = 4;
				break;
			case Ursi:
				description = "Irate Locals";
				strength = 7;
				endurance = 6;
				firefight = true;
				break;
			case Verfusier:
				description = "Metallic Being on a Luminous Elipse";
				strength = 4;
				endurance = 4;
				breakoffRollAdjust = 1;
				break;
			case Virus:
				description = "Intelligent Germ Liquid";
				strength = 10;
				endurance = immortalhp;
				immortal = true;
				break;
			case Vorozion:
				description = "Highly Evolved Thought Being";
				strength = 0;
				endurance = immortalhp;
				immortal = true;
				intelligence = 6;
				breakoff = false;
				breakoffAfter = neverAvailable;

				attacksWithINT = true;
				break;
			case Vrialta:
				description = "Imperial Knight Deserter";
				// If we do music, this guy gets his own epic theme.
				strength = 10;
				endurance = 10;
				firefight = true;
				break;
			case Wyths:
				description = "Telepathic Being that Fights With Conjured Images";
				strength = 9;
				endurance = 4;
				breakoffRollAdjust = 1;
				break;
			case Xanthons:
				description = "Irate Locals";
				if( e == Environ.EnvironType.Fire )
				{
					strength = 8;
					endurance = 6;
				}
				break;
			case Yesters:
				description = "Irate Locals";
				strength = 6;
				if( e == Environ.EnvironType.Urban )
				{
					endurance = 2;
				}
				else if( e == Environ.EnvironType.Air )
				{
					endurance = 4;
				}
				break;
			case YmBarror:
				description = "Plant/Animal Symbiote";
				strength = 4;
				endurance = 4;
				breakoffAfter = 1;
				// Gets two attack die rolls. Unimplemented.
				break;
			case Zernipak:
				description = "Malfunctioning Construction Vehicle";
				strength = 5;
				endurance = 4;
				break;
			/* case Zop:
				Non-combat;
				break; */
			default:
				description = "empty";
				break;

		}
	}
}