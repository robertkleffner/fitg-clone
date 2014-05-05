/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fitg;
import java.io.*; 
import java.net.URL; 
import javax.sound.sampled.*; 
/** 
 * 
 * @author matttr 
 * 
 * Code Example was found at http://www.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html
 */ 

public enum SoundEffect { SCREAM("sounds/WilhelmScream.wav"); 

    private Clip clip; 

    SoundEffect(String soundFile) { 
      try { 
         // Use URL (instead of File) to read from disk and JAR. 
         URL url = this.getClass().getClassLoader().getResource(soundFile); 
         // Set up an audio input stream piped from the sound file. 
         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url); 
         // Get a clip resource. 
         clip = AudioSystem.getClip(); 
         // Open audio clip and load samples from the audio input stream. 
         clip.open(audioInputStream); 
      } catch (UnsupportedAudioFileException e) { 
         e.printStackTrace(); 
      } catch (IOException e) { 
         e.printStackTrace(); 
      } catch (LineUnavailableException e) { 
         e.printStackTrace(); 
      } 
    } 
    
    // Play or Re-play the sound effect from the beginning, by rewinding. 
    public void play() 
    { 
        if (clip.isRunning()) 
           clip.stop();   // Stop the player if it is still running 

        clip.setFramePosition(0); // rewind to the beginning 
        clip.start();     // Start playing 
    } 
    
    // Optional static method to pre-load all the sound files. 
    static void init() { 
       values(); // calls the constructor for all the elements 
    } 
} 
