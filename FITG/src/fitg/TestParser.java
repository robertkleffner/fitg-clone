/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fitg;

import java.io.*;
import java.util.*;

/**
 *
 * @author Charlie
 */
public class TestParser {
     public static void main(String[] args) {
        String line = "";
        int provinceID = 0;
        int systemID = 0;
        String systemName = "";
        int planetID = 0;
        String planetName = "";
        String planetRace = "";
        int environID = 0;
        String environType = "";
        int environSize = 0;
        String environRace = "";
        String environResources = "";
        String environCreature = "";
        String environPolitics = "";
        BufferedReader br = null;
        int count = 0;
        try {
            br = new BufferedReader( new FileReader("C:\\Users\\Charlie\\Desktop\\testFile.txt"));
            while( (line = br.readLine()) != null){
                String[] tokens = line.split(",");

                if(!line.contains("\t"))
                {
                    //System.out.println("province");
                    line = line.trim();
                    tokens = line.split(",");
                    provinceID = Integer.parseInt(tokens[0]);
                }
                if(line.contains("\t") && !line.contains("\t\t"))
                {
                    line = line.trim();
                    tokens = line.split(",");
                    systemID = Integer.parseInt(tokens[0]);
                    systemName = tokens[1];
                }
                if(line.contains("\t\t") && !line.contains("\t\t\t"))
                {
                    //System.out.println("planet in " + systemID + ": " + systemName);
                    line = line.trim();
                    tokens = line.split(",");
                    planetID = Integer.parseInt(tokens[0]);
                    planetName = tokens[1];
                    if(tokens[2].contains("-") || tokens[2].contains("Secret"))
                        planetRace = "0";
                    else
                        planetRace = tokens[2];
                }
                if(line.contains("\t\t\t"))
                {
                    //Create environ with 
                    //System.out.println("environ in: " + planetName + ": peopled by: " + planetRace);
                    line = line.trim();
                    tokens = line.split(",");
                    if(tokens.length == 7)
                    {
                        environID = Integer.parseInt(tokens[0]);
                        environType = tokens[1];
                        environSize = Integer.parseInt(tokens[2]);
                        environRace = tokens[3];
                        environResources = tokens[4];
                        environCreature = tokens[5];
                        environPolitics = tokens[6];

                        
                    }
                }
            }
        } catch (FileNotFoundException e) {
            //System.err.println("Unable to find the file");
        } catch (IOException e) {
            //System.err.println("Unable to read the file");
        }
    }
        
    }


