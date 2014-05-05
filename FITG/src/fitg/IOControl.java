/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fitg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Matt
 */
public class IOControl
 {
    
    BufferedReader br;
    
    public IOControl()
    {
        //  open up standard input
        br = new BufferedReader(new InputStreamReader(java.lang.System.in));
    }
    
    public String getLine(){
      String input = null;

      try
      {
         input = br.readLine();
      } catch (IOException ioe)
      {
         java.lang.System.out.println("IO error trying to read input!");
         java.lang.System.exit(1);
      }

      return input;
    }
    
      
    public int getInt()
    {
      String input = null;

      try
      {
         input = br.readLine();
      } catch (IOException ioe)
      {
         java.lang.System.out.println("IO error trying to read input!");
         java.lang.System.exit(1);
      }

      return Integer.parseInt(input);
    }
    
    public void starttext()
    {
        //java.lang.System.out.println("Starting");
        java.lang.System.out.println();
        java.lang.System.out.println("Commands:");
        java.lang.System.out.println("--------------------");
        java.lang.System.out.println("l : list ships and their locations");
        java.lang.System.out.println("m : move a ship to new destination");
        java.lang.System.out.println("c : create a new ship");
        java.lang.System.out.println("n : Quit phase");
        java.lang.System.out.println("q or Q : Quit game");
    }
    
    public void println(String out)
    {
        java.lang.System.out.println(out);
    }
    
    public void print(String out)
    {
        java.lang.System.out.print(out);
    }
}