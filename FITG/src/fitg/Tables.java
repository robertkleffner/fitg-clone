/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fitg;
import java.util.*;
import java.lang.Math;
/**
 *
 * @author Jason
 */
public class Tables
{
	private static char[][] DetectionTable;
	private static int[][] MilitaryCombatAttackerTable;
	private static int[][] MilitaryCombatDefenderTable;
	private static int[][] SearchTable;
	private static int[][] SquadAttributes;
	// private int[][] IrateStarFaring; 	Should be elsewhere
	// private int[][] IrateOtherRaces;		Should be elsewhere
	private static int[][] CharacterCombatAttackerTable;
	private static int[][] CharacterCombatDefenderTable;
	private static char[][] CaptureAttackerTable;
	private static char[][] CaptureDefenderTable;
	private static int[][] BreakoffTable;
	private static int[] HyperjumpTable;

	private static Boolean DetectionTableBuilt = false;
	private static Boolean SearchTableBuilt = false;
	private static Boolean MilitaryCombatTablesBuilt = false;
	private static Boolean SquadTablesBuilt = false;
	private static Boolean CharacterCombatTablesBuilt = false;
	private static Boolean HyperjumpTableBuilt = false;


	// This is redundant now, but is still good to use at the start of the program.
	// Tables will be built as they are first needed if this is not run.
	public static void BuildTables()
	{
		BuildDetectionTable();
		BuildSearchTable();
		BuildMilitaryCombatTables();
		BuildSquadTables();
		BuildCharacterCombatTables();
		BuildHyperjumpTable();
	}
	public static Boolean TablesBuilt()
	{
		if( !DetectionTableBuilt )
		{
			System.out.println( "DetectionTable didn't build." );
			return false;
		}
		if( !SearchTableBuilt )
		{
			System.out.println( "Search Table Didn't Build" );
			return false;
		}
		if( !MilitaryCombatTablesBuilt )
		{
			System.out.println( "Military Combat Tables didn't Build" );
			return false;
		}
		if( !SquadTablesBuilt )
		{
			System.out.println( "SquadTables didn't build." );
			return false;
		}
		if( !CharacterCombatTablesBuilt )
		{
			System.out.println( "CharacterCombat tables didn't build." );
			return false;
		}
		if( !HyperjumpTableBuilt )
		{
			System.out.println( "HyperjumpTables didn't build." );
			return false;
		}

		return true;
	}

// ******** Detection ********
	// Detection Table:
	// U = U: Undetected
	// D = D: Detected
	// Dd = I: Detected and Damaged (I for Injured)
	// E = E: Eliminated
	public static char Detection( int EvasionValue )
	{
		if(!DetectionTableBuilt)
		{
			BuildDetectionTable();
		}

		if( EvasionValue > 9 ) // Handle 9+ evasion value
		{
			EvasionValue = 9;
		}
		int roll = DiceRoll( 6 );
		return DetectionTable[roll-1][EvasionValue];
	}

// ******** Military Combat ********
	// Military Combat is two functions to get the results.
	// First the ratio must be reduced (in favor of the defender) to
	// between 1-6 to 6-1. With the ratio, run the two functions:
	// MilitaryCombatAttacker( 6 to 1 ) as MilitaryCombatAttacker( 6, 1, diceroll)
	// and MilitaryCombatDefender the same way.
	// Each function will return how much each force losses in strength.
	public static int MilitaryCombatAttacker( int attacker, int defender )
	{
		int roll = DiceRoll();
		return MilitaryCombatAttacker( attacker, defender, roll );
	}
	public static int MilitaryCombatAttacker( int attacker, int defender, int roll )
	{
		if(!MilitaryCombatTablesBuilt)
		{
			BuildMilitaryCombatTables();
		}

		int column = attacker - defender + 5;

		if( column > 10 ) // Correct for overshifts
		{
			column = 10;
		}

		return MilitaryCombatAttackerTable[roll-1][column];
	}
	public static int MilitaryCombatDefender( int attacker, int defender )
	{
		int roll = DiceRoll();
		return MilitaryCombatDefender( attacker, defender, roll );
	}
	public static int MilitaryCombatDefender( int attacker, int defender, int roll )
	{
		if(!MilitaryCombatTablesBuilt)
		{
			BuildMilitaryCombatTables();
		}

		int column = attacker - defender + 5;

		if( column < 0 )
		{
			column = 0;
		}
		else if( column > 10 )
		{
			column = 10;
		}

		return MilitaryCombatAttackerTable[roll-1][column];
	}

// ******** Character Combat ********

	// Two functions similar to Military Combat
	// If a column shift is dictated by the rules, include column shift (left or right) in the value.
	public static int CharacterCombatAttacker( int differential, int roll )
	{
		return CharacterCombatAttacker( differential, roll, 0 );
	}
	public static int CharacterCombatAttacker( int differential, int roll, int colshift )
	{
		if(!CharacterCombatTablesBuilt)
		{
			BuildCharacterCombatTables();
		}

		int column = ConvertCombatColumn( differential );
		column = column + colshift; // Apply column shift
		
		// Correct for overshifts
		if( column > 9 )
		{
			column = 9;
		}
		else if( column < 0 )
		{
			column = 0;
		}

		return CharacterCombatAttackerTable[roll-1][column];
	}

	public static int CharacterCombatDefender( int differential, int roll )
	{
		return CharacterCombatDefender( differential, roll, 0 );
	}
	public static int CharacterCombatDefender( int differential, int roll, int colshift )
	{
		if(!CharacterCombatTablesBuilt)
		{
			BuildCharacterCombatTables();
		}

		int column = ConvertCombatColumn( differential );
		column = column + colshift; // Apply column shift
		
		// Correct for overshifts
		if( column > 9 )
		{
			column = 9;
		}
		else if( column < 0 )
		{
			column = 0;
		}
		
		return CharacterCombatDefenderTable[roll-1][column];
	}

	public static Boolean CaptureAttacker( int differential, int roll )
	{
		return CaptureAttacker( differential, roll, 0 );
	}
	public static Boolean CaptureAttacker( int differential, int roll, int colshift )
	{
		if(!CharacterCombatTablesBuilt)
		{
			BuildCharacterCombatTables();
		}

		int column = ConvertCombatColumn( differential );
		column = column + colshift; // Apply column shift

		// Correct for overshifts
		if( column > 9 )
		{
			column = 9;
		}
		else if( column < 0 )
		{
			column = 0;
		}

		if( CaptureAttackerTable[roll-1][column] == '*' )
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public static Boolean CaptureDefender( int differential, int roll )
	{
		return CaptureDefender( differential, roll, 0 );
	}
	public static Boolean CaptureDefender( int differential, int roll, int colshift )
	{
		if(!CharacterCombatTablesBuilt)
		{
			BuildCharacterCombatTables();
		}

		int column = ConvertCombatColumn( differential );
		column = column + colshift; // Apply column shift

		// Correct for overshifts
		if( column > 9 )
		{
			column = 9;
		}
		else if( column < 0 )
		{
			column = 0;
		}

		if( CaptureDefenderTable[roll-1][column] == '*' )
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static boolean Breakoff( int differential, boolean active )
	{
		return Breakoff( differential, active, 0 );
	}
	public static boolean Breakoff( int differential, boolean active, int colshift )
	{
		int result = BreakoffTable( differential, active, colshift );
		int roll = DiceRoll();
		if( roll <= result )
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public static int BreakoffTable( int differential, boolean active )
	{
		return BreakoffTable( differential, active, 0 );
	}
	public static int BreakoffTable( int differential, boolean active, int colshift )
	{
		if(!CharacterCombatTablesBuilt)
		{
			BuildCharacterCombatTables();
		}

		int column = ConvertCombatColumn( differential );

		column = column + colshift; // Apply column shift
		
		// Correct for overshifts
		if( column > 9 )
		{
			column = 9;
		}
		else if( column < 0 )
		{
			column = 0;
		}

		int row = 0;
		if( !active )
		{
			row = 1;
		}
		return BreakoffTable[row][column];
	}

	private static int ConvertCombatColumn( int differential )
	{
		if( differential <= -7 )
		{
			return 0;
		}
		else if( differential >= -6 && differential <= -4 )
		{
			return 1;
		}
		else if( differential == -3 || differential == -2 )
		{
			return 2;
		}
		else if( differential == -1 )
		{
			return 3;
		}
		else if( differential == 0 )
		{
			return 4;
		}
		else if( differential == 1 )
		{
			return 5;
		}
		else if( differential >= 2 || differential <= 3 )
		{
			return 6;
		}
		else if( differential >= 4 && differential <= 6 )
		{
			return 7;
		}
		else if( differential >= 7 && differential <= 10 )
		{
			return 8;
		}
		else if( differential >= 11 )
		{
			return 9;
		}
		else
		{
			System.out.println( "Error in Tables.ConvertCombatColumn(): else hit. differential = " + differential );
			return 0;
		}
	}

// ******** Search Table ********
	// Returns true if they are found, false if they are not found.
	public static boolean Search( int hidingvalue, int searchvalue )
	{
		if(!SearchTableBuilt)
		{
			BuildSearchTable();
		}

		int row = 0;
		int column = 0;
		
		// Translate hidingvalue to a row#
		if( hidingvalue <= 1 )
		{
			row = 0;
		}
		else if( hidingvalue == 2 || hidingvalue == 3 )
		{
			row = 1;
		}
		else if( hidingvalue == 4 || hidingvalue == 5 )
		{
			row = 2;
		}
		else if( hidingvalue == 5 || hidingvalue == 7 )
		{
			row = 3;
		}
		else if( hidingvalue >= 8 )
		{
			row = 4;
		}
		else
		{
			System.out.println( "Error in Tables.Search(): else hit. hidingvalue = " + hidingvalue );
			row = 4;
		}

		// Translate searchvalue to a column#
		if( searchvalue <= 1 )
		{
			column = 0;
		}
		else if( searchvalue == 2 || searchvalue == 3 )
		{
			column = 1;
		}
		else if( searchvalue >= 4 && searchvalue <= 6 )
		{
			column = 2;
		}
		else if( searchvalue >= 7 && searchvalue <= 9 )
		{
			column = 3;
		}
		else if( searchvalue >= 10 && searchvalue <= 13 )
		{
			column = 5;
		}
		else if( searchvalue >= 14 && searchvalue <= 17 )
		{
			column = 6;
		}
		else if( searchvalue >= 18 && searchvalue <= 22 )
		{
			column = 7;
		}
		else if( searchvalue >= 23 )
		{
			column = 8;
		}
		else
		{
			System.out.println( "Error in Tables.Search(): else hit. searchvalue = " + searchvalue );
			column = 8;
		}

		int tableresult = SearchTable[row][column];

		if( tableresult == 0 )
		{
			return false;
		}
		else if( tableresult == 6 )
		{
			return true;
		}
		else if( DiceRoll() <= tableresult )
		{
			return true;
		}
		else
		{
			return false;
		}
	}

// ******** Squad Attributes ********
	// Call SquadAttribute as strength, or 'C'ombat or 'E'ndurance
	// Example: SquadAttribute( 5, C );
	public static int SquadAttribute( int strength, char attribute )
	{
		if(!SquadTablesBuilt)
		{
			BuildSquadTables();
		}

		// Translate attribute to column
		int column = 0;
		if( attribute == 'C' )
		{
			column = 0;
		}
		else if( attribute == 'E' )
		{
			column = 1;
		}
		else
		{
			System.out.println( "Error in Tables.SquadAttribute(): else hit. attribute = " + attribute );
		}

		// Translate strength to row
		int row = 0;
		if( strength <= 1 )
		{
			row = 0;
		}
		else if( strength == 2 )
		{
			row = 1;
		}
		else if( strength == 3 || strength == 4 )
		{
			row = 2;
		}
		else if( strength >= 5 && strength <= 7 )
		{
			row = 3;
		}
		else if( strength >= 8 && strength <= 11 )
		{
			row = 4;
		}
		else if( strength >= 12 )
		{
			row = 5;
		}
		else
		{
			System.out.println( "Error in Tables.SquadAttribute(): else hit. strength = " + strength );
		}

		return SquadAttributes[row][column];
	}

// ******** Hyperjump Table ********
	// Hyperjump table:
	// 0 = 0: Successful jump; go to destination
	// 1 = 1: Slightly off; go to drift 1 area
	// 2 = 2: Way off course; go to drift 2
	// 3 = 1D: Slightly off results (1) and damaged
	// 4 = 2D: Way off results (2) and damaged
	// 5 = 2E: Very bad result. Military units cut in half and put in Drift 2.
	//			Non-military is destroyed and all abord lost.
	public static int Hyperjump( int navigationrating, int distance )
	{
		if(!HyperjumpTableBuilt)
		{
			BuildHyperjumpTable();
		}

		int jump = distance - navigationrating + DiceRoll();

		if( jump > 12 ) // Account for +12. (They're dead.)
		{
			jump = 12;
		}
		else if( jump < 1 ) // Don't think this can happen, but just in case...
		{
			jump = 1;
		}
		return HyperjumpTable[jump];
	}

// ******** Dice Rolls ********
	// Remember, a dice roll is never zero!
	// So if you're using it as a general random remember to subtract 1!
	public static int DiceRoll()
	{
		return DiceRoll( 6 );
	}
	public static int DiceRoll( int die )
	{
		double roll = Math.random() * (double) die;
		return (int) roll+1;
	}

// ******** Build Tables ********
	// Everything after this is boring; you don't need to look at it.
	// Pay no attention to the man behind the curtain!
	private static void BuildDetectionTable()
	{	
		if(!DetectionTableBuilt)
		{
			DetectionTable = new char[6][10];

			// Die 1
			DetectionTable[0][0] = 'I';
			DetectionTable[0][1] = 'D';
			DetectionTable[0][2] = 'U';
			DetectionTable[0][3] = 'U';
			DetectionTable[0][4] = 'U';
			DetectionTable[0][5] = 'U';
			DetectionTable[0][6] = 'U';
			DetectionTable[0][7] = 'U';
			DetectionTable[0][8] = 'U';
			DetectionTable[0][9] = 'U';
			
			// Die 2
			DetectionTable[1][0] = 'I';
			DetectionTable[1][1] = 'D';
			DetectionTable[1][2] = 'D';
			DetectionTable[1][3] = 'D';
			DetectionTable[1][4] = 'U';
			DetectionTable[1][5] = 'U';
			DetectionTable[1][6] = 'U';
			DetectionTable[1][7] = 'U';
			DetectionTable[1][8] = 'U';
			DetectionTable[1][9] = 'U';

			// Die 3
			DetectionTable[2][0] = 'I';
			DetectionTable[2][1] = 'I';
			DetectionTable[2][2] = 'D';
			DetectionTable[2][3] = 'D';
			DetectionTable[2][4] = 'D';
			DetectionTable[2][5] = 'U';
			DetectionTable[2][6] = 'U';
			DetectionTable[2][7] = 'U';
			DetectionTable[2][8] = 'U';
			DetectionTable[2][9] = 'U';

			// Die 4
			DetectionTable[3][0] = 'E';
			DetectionTable[3][1] = 'I';
			DetectionTable[3][2] = 'I';
			DetectionTable[3][3] = 'D';
			DetectionTable[3][4] = 'D';
			DetectionTable[3][5] = 'D';
			DetectionTable[3][6] = 'D';
			DetectionTable[3][7] = 'U';
			DetectionTable[3][8] = 'U';
			DetectionTable[3][9] = 'U';

			// Die 5
			DetectionTable[4][0] = 'E';
			DetectionTable[4][1] = 'I';
			DetectionTable[4][2] = 'I';
			DetectionTable[4][3] = 'I';
			DetectionTable[4][4] = 'D';
			DetectionTable[4][5] = 'D';
			DetectionTable[4][6] = 'D';
			DetectionTable[4][7] = 'D';
			DetectionTable[4][8] = 'D';
			DetectionTable[4][9] = 'U';

			// Die 6
			DetectionTable[5][0] = 'E';
			DetectionTable[5][1] = 'E';
			DetectionTable[5][2] = 'E';
			DetectionTable[5][3] = 'I';
			DetectionTable[5][4] = 'I';
			DetectionTable[5][5] = 'D';
			DetectionTable[5][6] = 'D';
			DetectionTable[5][7] = 'D';
			DetectionTable[5][8] = 'D';
			DetectionTable[5][9] = 'D';

			DetectionTableBuilt = true;
		}

	}

	private static void BuildSearchTable()
	{
		if(!SearchTableBuilt)
		{		
			SearchTable = new int[5][8];
		
			// 1 or less
			SearchTable[0][0] = 1;
			SearchTable[0][1] = 2;
			SearchTable[0][2] = 3;
			SearchTable[0][3] = 4;
			SearchTable[0][4] = 4;
			SearchTable[0][5] = 5;
			SearchTable[0][6] = 6;
			SearchTable[0][7] = 6;

			// 2, 3
			SearchTable[1][0] = 1;
			SearchTable[1][1] = 1;
			SearchTable[1][2] = 2;
			SearchTable[1][3] = 3;
			SearchTable[1][4] = 4;
			SearchTable[1][5] = 4;
			SearchTable[1][6] = 5;
			SearchTable[1][7] = 6;

			// 4, 5
			SearchTable[2][0] = 0;
			SearchTable[2][1] = 1;
			SearchTable[2][2] = 1;
			SearchTable[2][3] = 2;
			SearchTable[2][4] = 3;
			SearchTable[2][5] = 4;
			SearchTable[2][6] = 4;
			SearchTable[2][7] = 5;

			// 6, 7
			SearchTable[3][0] = 0;
			SearchTable[3][1] = 0;
			SearchTable[3][2] = 1;
			SearchTable[3][3] = 1;
			SearchTable[3][4] = 2;
			SearchTable[3][5] = 2;
			SearchTable[3][6] = 3;
			SearchTable[3][7] = 4;

			// 8 or more
			SearchTable[4][0] = 0;
			SearchTable[4][1] = 0;
			SearchTable[4][2] = 0;
			SearchTable[4][3] = 1;
			SearchTable[4][4] = 1;
			SearchTable[4][5] = 1;
			SearchTable[4][6] = 2;
			SearchTable[4][7] = 3;

			SearchTableBuilt = true;
		}
	}

	private static void BuildMilitaryCombatTables()
	{
		if(!MilitaryCombatTablesBuilt)
		{
			MilitaryCombatAttackerTable = new int[6][11];

			// Die 1
			MilitaryCombatAttackerTable[0][0] = 8;
			MilitaryCombatAttackerTable[0][1] = 7;
			MilitaryCombatAttackerTable[0][2] = 6;
			MilitaryCombatAttackerTable[0][3] = 5;
			MilitaryCombatAttackerTable[0][4] = 5;
			MilitaryCombatAttackerTable[0][5] = 4;
			MilitaryCombatAttackerTable[0][6] = 3;
			MilitaryCombatAttackerTable[0][7] = 3;
			MilitaryCombatAttackerTable[0][8] = 2;
			MilitaryCombatAttackerTable[0][9] = 2;
			MilitaryCombatAttackerTable[0][10] = 1;

			// Die 2
			MilitaryCombatAttackerTable[1][0] = 7;
			MilitaryCombatAttackerTable[1][1] = 6;
			MilitaryCombatAttackerTable[1][2] = 5;
			MilitaryCombatAttackerTable[1][3] = 5;
			MilitaryCombatAttackerTable[1][4] = 4;
			MilitaryCombatAttackerTable[1][5] = 3;
			MilitaryCombatAttackerTable[1][6] = 2;
			MilitaryCombatAttackerTable[1][7] = 2;
			MilitaryCombatAttackerTable[1][8] = 1;
			MilitaryCombatAttackerTable[1][9] = 1;
			MilitaryCombatAttackerTable[1][10] = 1;

			// Die 3
			MilitaryCombatAttackerTable[2][0] = 7;
			MilitaryCombatAttackerTable[2][1] = 5;
			MilitaryCombatAttackerTable[2][2] = 5;
			MilitaryCombatAttackerTable[2][3] = 4;
			MilitaryCombatAttackerTable[2][4] = 3;
			MilitaryCombatAttackerTable[2][5] = 0;
			MilitaryCombatAttackerTable[2][6] = 2;
			MilitaryCombatAttackerTable[2][7] = 2;
			MilitaryCombatAttackerTable[2][8] = 1;
			MilitaryCombatAttackerTable[2][9] = 1;
			MilitaryCombatAttackerTable[2][10] = 1;

			// Die 4
			MilitaryCombatAttackerTable[3][0] = 6;
			MilitaryCombatAttackerTable[3][1] = 5;
			MilitaryCombatAttackerTable[3][2] = 4;
			MilitaryCombatAttackerTable[3][3] = 3;
			MilitaryCombatAttackerTable[3][4] = 3;
			MilitaryCombatAttackerTable[3][5] = 2;
			MilitaryCombatAttackerTable[3][6] = 1;
			MilitaryCombatAttackerTable[3][7] = 1;
			MilitaryCombatAttackerTable[3][8] = 1;
			MilitaryCombatAttackerTable[3][9] = 0;
			MilitaryCombatAttackerTable[3][10] = 0;

			// Die 5
			MilitaryCombatAttackerTable[4][0] = 5;
			MilitaryCombatAttackerTable[4][1] = 4;
			MilitaryCombatAttackerTable[4][2] = 4;
			MilitaryCombatAttackerTable[4][3] = 3;
			MilitaryCombatAttackerTable[4][4] = 2;
			MilitaryCombatAttackerTable[4][5] = 1;
			MilitaryCombatAttackerTable[4][6] = 1;
			MilitaryCombatAttackerTable[4][7] = 0;
			MilitaryCombatAttackerTable[4][8] = 0;
			MilitaryCombatAttackerTable[4][9] = 0;
			MilitaryCombatAttackerTable[4][10] = 0;

			// Die 6
			MilitaryCombatAttackerTable[5][0] = 4;
			MilitaryCombatAttackerTable[5][1] = 4;
			MilitaryCombatAttackerTable[5][2] = 3;
			MilitaryCombatAttackerTable[5][3] = 3;
			MilitaryCombatAttackerTable[5][4] = 1;
			MilitaryCombatAttackerTable[5][5] = 0;
			MilitaryCombatAttackerTable[5][6] = 0;
			MilitaryCombatAttackerTable[5][7] = 0;
			MilitaryCombatAttackerTable[5][8] = 0;
			MilitaryCombatAttackerTable[5][9] = 0;
			MilitaryCombatAttackerTable[5][10] = 0;


			MilitaryCombatDefenderTable = new int[6][11];

			// Die 1
			MilitaryCombatDefenderTable[0][0] = 0;
			MilitaryCombatDefenderTable[0][1] = 0;
			MilitaryCombatDefenderTable[0][2] = 0;
			MilitaryCombatDefenderTable[0][3] = 0;
			MilitaryCombatDefenderTable[0][4] = 0;
			MilitaryCombatDefenderTable[0][5] = 0;
			MilitaryCombatDefenderTable[0][6] = 1;
			MilitaryCombatDefenderTable[0][7] = 2;
			MilitaryCombatDefenderTable[0][8] = 2;
			MilitaryCombatDefenderTable[0][9] = 3;
			MilitaryCombatDefenderTable[0][10] = 4;

			// Die 2
			MilitaryCombatDefenderTable[1][0] = 0;
			MilitaryCombatDefenderTable[1][1] = 0;
			MilitaryCombatDefenderTable[1][2] = 0;
			MilitaryCombatDefenderTable[1][3] = 0;
			MilitaryCombatDefenderTable[1][4] = 1;
			MilitaryCombatDefenderTable[1][5] = 2;
			MilitaryCombatDefenderTable[1][6] = 2;
			MilitaryCombatDefenderTable[1][7] = 3;
			MilitaryCombatDefenderTable[1][8] = 3;
			MilitaryCombatDefenderTable[1][9] = 4;
			MilitaryCombatDefenderTable[1][10] = 5;

			// Die 3
			MilitaryCombatDefenderTable[2][0] = 0;
			MilitaryCombatDefenderTable[2][1] = 0;
			MilitaryCombatDefenderTable[2][2] = 0;
			MilitaryCombatDefenderTable[2][3] = 0;
			MilitaryCombatDefenderTable[2][4] = 1;
			MilitaryCombatDefenderTable[2][5] = 1;
			MilitaryCombatDefenderTable[2][6] = 2;
			MilitaryCombatDefenderTable[2][7] = 3;
			MilitaryCombatDefenderTable[2][8] = 4;
			MilitaryCombatDefenderTable[2][9] = 4;
			MilitaryCombatDefenderTable[2][10] = 5;

			// Die 4
			MilitaryCombatDefenderTable[3][0] = 0;
			MilitaryCombatDefenderTable[3][1] = 0;
			MilitaryCombatDefenderTable[3][2] = 1;
			MilitaryCombatDefenderTable[3][3] = 1;
			MilitaryCombatDefenderTable[3][4] = 1;
			MilitaryCombatDefenderTable[3][5] = 2;
			MilitaryCombatDefenderTable[3][6] = 3;
			MilitaryCombatDefenderTable[3][7] = 3;
			MilitaryCombatDefenderTable[3][8] = 4;
			MilitaryCombatDefenderTable[3][9] = 5;
			MilitaryCombatDefenderTable[3][10] = 6;

			// Die 5
			MilitaryCombatDefenderTable[4][0] = 1;
			MilitaryCombatDefenderTable[4][1] = 1;
			MilitaryCombatDefenderTable[4][2] = 1;
			MilitaryCombatDefenderTable[4][3] = 1;
			MilitaryCombatDefenderTable[4][4] = 2;
			MilitaryCombatDefenderTable[4][5] = 2;
			MilitaryCombatDefenderTable[4][6] = 3;
			MilitaryCombatDefenderTable[4][7] = 4;
			MilitaryCombatDefenderTable[4][8] = 5;
			MilitaryCombatDefenderTable[4][9] = 6;
			MilitaryCombatDefenderTable[4][10] = 7;

			// Die 6
			MilitaryCombatDefenderTable[5][0] = 1;
			MilitaryCombatDefenderTable[5][1] = 1;
			MilitaryCombatDefenderTable[5][2] = 1;
			MilitaryCombatDefenderTable[5][3] = 2;
			MilitaryCombatDefenderTable[5][4] = 2;
			MilitaryCombatDefenderTable[5][5] = 3;
			MilitaryCombatDefenderTable[5][6] = 4;
			MilitaryCombatDefenderTable[5][7] = 5;
			MilitaryCombatDefenderTable[5][8] = 6;
			MilitaryCombatDefenderTable[5][9] = 7;
			MilitaryCombatDefenderTable[5][10] = 8;

			MilitaryCombatTablesBuilt = true;
		}
	}

	private static void BuildSquadTables()
	{
		if(!SquadTablesBuilt)
		{
			SquadAttributes = new int[6][2];


			SquadAttributes[0][0] = 4;
			SquadAttributes[1][0] = 5;
			SquadAttributes[2][0] = 8;
			SquadAttributes[3][0] = 10;
			SquadAttributes[4][0] = 12;
			SquadAttributes[5][0] = 14;

			SquadAttributes[0][1] = 4;
			SquadAttributes[1][1] = 4;
			SquadAttributes[2][1] = 6;
			SquadAttributes[3][1] = 6;
			SquadAttributes[4][1] = 8;
			SquadAttributes[5][1] = 8;

			SquadTablesBuilt = true;
		}
	}

	private static void BuildCharacterCombatTables()
	{
		if(!CharacterCombatTablesBuilt)
		{
			CharacterCombatAttackerTable = new int[6][10];

			// Die 1
			CharacterCombatAttackerTable[0][0] = 4;
			CharacterCombatAttackerTable[0][1] = 3;
			CharacterCombatAttackerTable[0][2] = 3;
			CharacterCombatAttackerTable[0][3] = 2;
			CharacterCombatAttackerTable[0][4] = 2;
			CharacterCombatAttackerTable[0][5] = 2;
			CharacterCombatAttackerTable[0][6] = 2;
			CharacterCombatAttackerTable[0][7] = 1;
			CharacterCombatAttackerTable[0][8] = 1;
			CharacterCombatAttackerTable[0][9] = 1;

			// Die 2
			CharacterCombatAttackerTable[1][0] = 4;
			CharacterCombatAttackerTable[1][1] = 3;
			CharacterCombatAttackerTable[1][2] = 2;
			CharacterCombatAttackerTable[1][3] = 2;
			CharacterCombatAttackerTable[1][4] = 2;
			CharacterCombatAttackerTable[1][5] = 1;
			CharacterCombatAttackerTable[1][6] = 1;
			CharacterCombatAttackerTable[1][7] = 1;
			CharacterCombatAttackerTable[1][8] = 1;
			CharacterCombatAttackerTable[1][9] = 0;	

			// Die 3
			CharacterCombatAttackerTable[2][0] = 3;
			CharacterCombatAttackerTable[2][1] = 3;
			CharacterCombatAttackerTable[2][2] = 2;
			CharacterCombatAttackerTable[2][3] = 2;
			CharacterCombatAttackerTable[2][4] = 1;
			CharacterCombatAttackerTable[2][5] = 1;
			CharacterCombatAttackerTable[2][6] = 1;
			CharacterCombatAttackerTable[2][7] = 1;
			CharacterCombatAttackerTable[2][8] = 0;
			CharacterCombatAttackerTable[2][9] = 0;

			// Die 4
			CharacterCombatAttackerTable[3][0] = 3;
			CharacterCombatAttackerTable[3][1] = 2;
			CharacterCombatAttackerTable[3][2] = 2;
			CharacterCombatAttackerTable[3][3] = 1;
			CharacterCombatAttackerTable[3][4] = 1;
			CharacterCombatAttackerTable[3][5] = 1;
			CharacterCombatAttackerTable[3][6] = 0;
			CharacterCombatAttackerTable[3][7] = 0;
			CharacterCombatAttackerTable[3][8] = 0;
			CharacterCombatAttackerTable[3][9] = 0;

			// Die 5
			CharacterCombatAttackerTable[4][0] = 2;
			CharacterCombatAttackerTable[4][1] = 2;
			CharacterCombatAttackerTable[4][2] = 1;
			CharacterCombatAttackerTable[4][3] = 1;
			CharacterCombatAttackerTable[4][4] = 0;
			CharacterCombatAttackerTable[4][5] = 0;
			CharacterCombatAttackerTable[4][6] = 0;
			CharacterCombatAttackerTable[4][7] = 0;
			CharacterCombatAttackerTable[4][8] = 0;
			CharacterCombatAttackerTable[4][9] = 0;

			// Die 6
			CharacterCombatAttackerTable[5][0] = 2;
			CharacterCombatAttackerTable[5][1] = 1;
			CharacterCombatAttackerTable[5][2] = 0;
			CharacterCombatAttackerTable[5][3] = 0;
			CharacterCombatAttackerTable[5][4] = 0;
			CharacterCombatAttackerTable[5][5] = 0;
			CharacterCombatAttackerTable[5][6] = 0;
			CharacterCombatAttackerTable[5][7] = 0;
			CharacterCombatAttackerTable[5][8] = 0;
			CharacterCombatAttackerTable[5][9] = 0;


			CharacterCombatDefenderTable = new int[6][10];

			// Die 1
			CharacterCombatDefenderTable[0][0] = 0;
			CharacterCombatDefenderTable[0][1] = 0;
			CharacterCombatDefenderTable[0][2] = 0;
			CharacterCombatDefenderTable[0][3] = 0;
			CharacterCombatDefenderTable[0][4] = 0;
			CharacterCombatDefenderTable[0][5] = 0;
			CharacterCombatDefenderTable[0][6] = 0;
			CharacterCombatDefenderTable[0][7] = 1;
			CharacterCombatDefenderTable[0][8] = 1;
			CharacterCombatDefenderTable[0][9] = 2;

			// Die 2
			CharacterCombatDefenderTable[1][0] = 0;
			CharacterCombatDefenderTable[1][1] = 0;
			CharacterCombatDefenderTable[1][2] = 0;
			CharacterCombatDefenderTable[1][3] = 0;
			CharacterCombatDefenderTable[1][4] = 0;
			CharacterCombatDefenderTable[1][5] = 0;
			CharacterCombatDefenderTable[1][6] = 1;
			CharacterCombatDefenderTable[1][7] = 1;
			CharacterCombatDefenderTable[1][8] = 2;
			CharacterCombatDefenderTable[1][9] = 2;	

			// Die 3
			CharacterCombatDefenderTable[2][0] = 0;
			CharacterCombatDefenderTable[2][1] = 0;
			CharacterCombatDefenderTable[2][2] = 0;
			CharacterCombatDefenderTable[2][3] = 0;
			CharacterCombatDefenderTable[2][4] = 0;
			CharacterCombatDefenderTable[2][5] = 1;
			CharacterCombatDefenderTable[2][6] = 1;
			CharacterCombatDefenderTable[2][7] = 1;
			CharacterCombatDefenderTable[2][8] = 2;
			CharacterCombatDefenderTable[2][9] = 3;

			// Die 4
			CharacterCombatDefenderTable[3][0] = 0;
			CharacterCombatDefenderTable[3][1] = 0;
			CharacterCombatDefenderTable[3][2] = 0;
			CharacterCombatDefenderTable[3][3] = 1;
			CharacterCombatDefenderTable[3][4] = 1;
			CharacterCombatDefenderTable[3][5] = 1;
			CharacterCombatDefenderTable[3][6] = 1;
			CharacterCombatDefenderTable[3][7] = 2;
			CharacterCombatDefenderTable[3][8] = 3;
			CharacterCombatDefenderTable[3][9] = 3;

			// Die 5
			CharacterCombatDefenderTable[4][0] = 0;
			CharacterCombatDefenderTable[4][1] = 0;
			CharacterCombatDefenderTable[4][2] = 1;
			CharacterCombatDefenderTable[4][3] = 1;
			CharacterCombatDefenderTable[4][4] = 1;
			CharacterCombatDefenderTable[4][5] = 1;
			CharacterCombatDefenderTable[4][6] = 2;
			CharacterCombatDefenderTable[4][7] = 2;
			CharacterCombatDefenderTable[4][8] = 3;
			CharacterCombatDefenderTable[4][9] = 4;

			// Die 6
			CharacterCombatDefenderTable[5][0] = 1;
			CharacterCombatDefenderTable[5][1] = 1;
			CharacterCombatDefenderTable[5][2] = 1;
			CharacterCombatDefenderTable[5][3] = 1;
			CharacterCombatDefenderTable[5][4] = 2;
			CharacterCombatDefenderTable[5][5] = 2;
			CharacterCombatDefenderTable[5][6] = 2;
			CharacterCombatDefenderTable[5][7] = 3;
			CharacterCombatDefenderTable[5][8] = 3;
			CharacterCombatDefenderTable[5][9] = 4;

			CaptureAttackerTable = new char[6][10];

			// Die 1
			CaptureAttackerTable[0][0] = '0';
			CaptureAttackerTable[0][1] = '0';
			CaptureAttackerTable[0][2] = '0';
			CaptureAttackerTable[0][3] = '0';
			CaptureAttackerTable[0][4] = '0';
			CaptureAttackerTable[0][5] = '*';
			CaptureAttackerTable[0][6] = '0';
			CaptureAttackerTable[0][7] = '0';
			CaptureAttackerTable[0][8] = '0';
			CaptureAttackerTable[0][9] = '0';

			// Die 2
			CaptureAttackerTable[1][0] = '0';
			CaptureAttackerTable[1][1] = '0';
			CaptureAttackerTable[1][2] = '0';
			CaptureAttackerTable[1][3] = '*';
			CaptureAttackerTable[1][4] = '*';
			CaptureAttackerTable[1][5] = '0';
			CaptureAttackerTable[1][6] = '*';
			CaptureAttackerTable[1][7] = '*';
			CaptureAttackerTable[1][8] = '*';
			CaptureAttackerTable[1][9] = '0'; 

			// Die 3
			CaptureAttackerTable[2][0] = '0';
			CaptureAttackerTable[2][1] = '*';
			CaptureAttackerTable[2][2] = '0';
			CaptureAttackerTable[2][3] = '0';
			CaptureAttackerTable[2][4] = '0';
			CaptureAttackerTable[2][5] = '0';
			CaptureAttackerTable[2][6] = '0';
			CaptureAttackerTable[2][7] = '0';
			CaptureAttackerTable[2][8] = '0';
			CaptureAttackerTable[2][9] = '0';

			// Die 4
			CaptureAttackerTable[3][0] = '0';
			CaptureAttackerTable[3][1] = '0';
			CaptureAttackerTable[3][2] = '*';
			CaptureAttackerTable[3][3] = '0';
			CaptureAttackerTable[3][4] = '0';
			CaptureAttackerTable[3][5] = '0';
			CaptureAttackerTable[3][6] = '0';
			CaptureAttackerTable[3][7] = '0';
			CaptureAttackerTable[3][8] = '0';
			CaptureAttackerTable[3][9] = '0';

			// Die 5
			CaptureAttackerTable[4][0] = '0';
			CaptureAttackerTable[4][1] = '0';
			CaptureAttackerTable[4][2] = '0';
			CaptureAttackerTable[4][3] = '0';
			CaptureAttackerTable[4][4] = '0';
			CaptureAttackerTable[4][5] = '0';
			CaptureAttackerTable[4][6] = '0';
			CaptureAttackerTable[4][7] = '*';
			CaptureAttackerTable[4][8] = '0';
			CaptureAttackerTable[4][9] = '0';

			// Die 6
			CaptureAttackerTable[5][0] = '0';
			CaptureAttackerTable[5][1] = '0';
			CaptureAttackerTable[5][2] = '0';
			CaptureAttackerTable[5][3] = '0';
			CaptureAttackerTable[5][4] = '0';
			CaptureAttackerTable[5][5] = '0';
			CaptureAttackerTable[5][6] = '0';
			CaptureAttackerTable[5][7] = '0';
			CaptureAttackerTable[5][8] = '0';
			CaptureAttackerTable[5][9] = '0';

			CaptureDefenderTable = new char[6][10];

			// Die 1
			CaptureDefenderTable[0][0] = '0';
			CaptureDefenderTable[0][1] = '0';
			CaptureDefenderTable[0][2] = '0';
			CaptureDefenderTable[0][3] = '0';
			CaptureDefenderTable[0][4] = '0';
			CaptureDefenderTable[0][5] = '0';
			CaptureDefenderTable[0][6] = '0';
			CaptureDefenderTable[0][7] = '0';
			CaptureDefenderTable[0][8] = '*';
			CaptureDefenderTable[0][9] = '0';

			// Die 2
			CaptureDefenderTable[1][0] = '0';
			CaptureDefenderTable[1][1] = '0';
			CaptureDefenderTable[1][2] = '0';
			CaptureDefenderTable[1][3] = '0';
			CaptureDefenderTable[1][4] = '0';
			CaptureDefenderTable[1][5] = '0';
			CaptureDefenderTable[1][6] = '0';
			CaptureDefenderTable[1][7] = '0';
			CaptureDefenderTable[1][8] = '0';
			CaptureDefenderTable[1][9] = '0'; 

			// Die 3
			CaptureDefenderTable[2][0] = '0';
			CaptureDefenderTable[2][1] = '0';
			CaptureDefenderTable[2][2] = '0';
			CaptureDefenderTable[2][3] = '0';
			CaptureDefenderTable[2][4] = '*';
			CaptureDefenderTable[2][5] = '0';
			CaptureDefenderTable[2][6] = '0';
			CaptureDefenderTable[2][7] = '*';
			CaptureDefenderTable[2][8] = '*';
			CaptureDefenderTable[2][9] = '0';

			// Die 4
			CaptureDefenderTable[3][0] = '0';
			CaptureDefenderTable[3][1] = '0';
			CaptureDefenderTable[3][2] = '0';
			CaptureDefenderTable[3][3] = '*';
			CaptureDefenderTable[3][4] = '0';
			CaptureDefenderTable[3][5] = '*';
			CaptureDefenderTable[3][6] = '*';
			CaptureDefenderTable[3][7] = '*';
			CaptureDefenderTable[3][8] = '0';
			CaptureDefenderTable[3][9] = '0';

			// Die 5
			CaptureDefenderTable[4][0] = '0';
			CaptureDefenderTable[4][1] = '0';
			CaptureDefenderTable[4][2] = '0';
			CaptureDefenderTable[4][3] = '0';
			CaptureDefenderTable[4][4] = '0';
			CaptureDefenderTable[4][5] = '0';
			CaptureDefenderTable[4][6] = '*';
			CaptureDefenderTable[4][7] = '0';
			CaptureDefenderTable[4][8] = '*';
			CaptureDefenderTable[4][9] = '0';

			// Die 6
			CaptureDefenderTable[5][0] = '0';
			CaptureDefenderTable[5][1] = '0';
			CaptureDefenderTable[5][2] = '0';
			CaptureDefenderTable[5][3] = '0';
			CaptureDefenderTable[5][4] = '0';
			CaptureDefenderTable[5][5] = '0';
			CaptureDefenderTable[5][6] = '0';
			CaptureDefenderTable[5][7] = '0';
			CaptureDefenderTable[5][8] = '0';
			CaptureDefenderTable[5][9] = '0';


			BreakoffTable = new int[2][10];

			// Active
			BreakoffTable[0][0] = 4;
			BreakoffTable[0][1] = 4;
			BreakoffTable[0][2] = 3;
			BreakoffTable[0][3] = 3;
			BreakoffTable[0][4] = 3;
			BreakoffTable[0][5] = 2;
			BreakoffTable[0][6] = 2;
			BreakoffTable[0][7] = 2;
			BreakoffTable[0][8] = 2;
			BreakoffTable[0][9] = 1;

			// Inactive
			BreakoffTable[1][0] = 6;
			BreakoffTable[1][1] = 6;
			BreakoffTable[1][2] = 5;
			BreakoffTable[1][3] = 5;
			BreakoffTable[1][4] = 4;
			BreakoffTable[1][5] = 4;
			BreakoffTable[1][6] = 4;
			BreakoffTable[1][7] = 3;
			BreakoffTable[1][8] = 3;
			BreakoffTable[1][9] = 3;

			CharacterCombatTablesBuilt = true;
		}
	}

	private static void BuildHyperjumpTable()
	{
		if(!HyperjumpTableBuilt)
		{
			HyperjumpTable = new int[13];

			HyperjumpTable[0] = 0;
			HyperjumpTable[1] = 1;
			HyperjumpTable[2] = 0;
			HyperjumpTable[3] = 0;
			HyperjumpTable[4] = 0;
			HyperjumpTable[5] = 1;
			HyperjumpTable[6] = 0;
			HyperjumpTable[7] = 2;
			HyperjumpTable[8] = 1;
			HyperjumpTable[9] = 2;
			HyperjumpTable[10] = 3;
			HyperjumpTable[11] = 4;
			HyperjumpTable[12] = 5;

			HyperjumpTableBuilt = true;
		}
	}
}