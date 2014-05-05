/* EnvironMenu.java
 * This file contains a menu that will be used in FITG. This menu will pop-up
 * when the environ menu button, which are located on each environ, is clicked.
 * This will allow the player to select units for movement, combat, as well
 * as allowing the player to launch searches on an environ.
 * 
 * 
 * Written by: Taylor Trabun
 * Last Modified: 10/14/13
 *
 * This menu is now unused!! There should still be some functionality here that tells
 * the user the planet info though...somehow...  - Shea
 */

package fitg;
 
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.JDialog;
import net.miginfocom.swing.MigLayout;
 
/* FrameDemo.java requires no other files. */
public class EnvironMenu {
    JDialog frame;
    JFrame myParent;
    private int xPos;
    private int yPos;
    private String menuName;
    private Environ environ;
    
    public EnvironMenu(JFrame parent, int x, int y, String location, Environ env){
        myParent = parent;
        //Position of menuEvent click
        xPos = x;
        yPos = y;
        menuName = location;
        environ = env;
        
        createGUI();

        //setModal();
        //EnvironMenu gui = new EnvironMenu(parent);
        //gui.createGUI();
        //gui.setGUIFront();
    }
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public static void showGUI(){
        
    }
    
    public void setModal(){
        //frame.setAlwaysOnTop(true);
        frame.setModal(true);

    }
    
    public JDialog getFrame(){
        return frame;
    }
    
    public void createGUI() {
        //Create and set up the window.
        frame = new JDialog(myParent, menuName, Dialog.ModalityType.APPLICATION_MODAL);
        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JPanel mainPanel = new JPanel(new MigLayout("fill, insets 10","[fill]","[]10[fill]10[fill]10[fill]10[fill]30[]"));
        
        
        frame.add(mainPanel);
        
        /*****************Initializing Mission Panel***************
        //Panel to organize all buttons for mission options
        JPanel missionPanel = new JPanel(new MigLayout("fill","[fill][fill]","[fill]"));
        //create border with title
        TitledBorder missionTitle = BorderFactory.createTitledBorder("Missions");
        missionPanel.setBorder(missionTitle);
      
        
        //////Creating buttons to mission panel
        JButton mission1 = new JButton("Mission 1");
        JButton mission2 = new JButton("Mission 2");
        JButton mission3 = new JButton("Mission 3");
        JButton mission4 = new JButton("Mission 4");
        
        //////Open CharacterSelectionMenu when mission button is clicked
        mission1.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event)
            {
                CharacterSelectionMenu characterSelectionMenu = new CharacterSelectionMenu(frame);
                JDialog menuFrame = characterSelectionMenu.getFrame();
                menuFrame.setAlwaysOnTop(true);
            }
        });
        
        //////Adding objects to mission panel
        missionPanel.add(mission1);
        missionPanel.add(mission2, "wrap");
        missionPanel.add(mission3);
        missionPanel.add(mission4);
       /*
        
        /*****************Initializing Search Panel***************/
        //Panel to organize all buttons for search options
        JPanel searchPanel = new JPanel(new MigLayout("fill","[fill]","[fill]"));
        //create border with title
        TitledBorder searchTitle = BorderFactory.createTitledBorder("Search");
        searchPanel.setBorder(searchTitle);
        
        //////Creating buttons for search panel
        JButton searchEnv = new JButton("Search Environs");       
        searchEnv.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event)
            {
                //SearchMenu searchMenu = new SearchMenu(frame, environ);
                SearchMenu searchMenu = new SearchMenu(frame, environ, frame.getX(), frame.getY());
                JDialog menuFrame = searchMenu.getFrame();
                menuFrame.setAlwaysOnTop(true);
            }

//            private List<Integer> getSideStacks() {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
        });
        
        //////Adding objects to search panel
        searchPanel.add(searchEnv, "wrap");
        
        
        /*****************Adding objects to main panel************/
        
        JButton selectUnits = new JButton("Move Units");
        selectUnits.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event)
            {
                //TODO: HAVE SIDE SHOWING DEPENDENT ON TURN
                UnitSelectionMenu unitSelectionMenu = new UnitSelectionMenu(frame, environ.GetUnitsForSide(Game.instance().turn.GetPhasingSide()), environ, frame.getX(), frame.getY());
                JDialog menuFrame = unitSelectionMenu.getFrame();
                menuFrame.setAlwaysOnTop(true);
            }
        });
        
        JButton resolveCombat = new JButton("Resolve Combat");
        resolveCombat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event)
            {
                // TODO: change side to be dynamic instead of fixed
                // The boolean value at the end is used because this call is obviously for environ combat
                CombatRoleMenu combatMenu = new CombatRoleMenu(frame, 
                        environ.GetUnitsForSide(Game.instance().turn.GetPhasingSide()), 
                        environ.GetUnitsForSide(Game.instance().turn.GetNonPhasingSide()), 
                        true, CombatRoleMenu.COMBAT.MILITARY);
                JDialog menuFrame = combatMenu.getFrame();
                menuFrame.setAlwaysOnTop(true);
            }
        });
        
        
        final JButton performMissions = new JButton("Perform Missions");
         if(environ.GetMissionCapableStack() == null)
         {
             performMissions.setEnabled(false);
             performMissions.setText("<html>Perform Missions<br> Disabled becuase there are <br> no Mission capable stacks<br> on this environ</html>");
         }
         
         if(environ.GetMissionsPerformed() == true)
         {
             performMissions.setEnabled(false);
             performMissions.setText("Already Completed");
         }
        
        performMissions.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event)
            {
                //Mission.Initialize();
                MissionMenu2 missionMenu = new MissionMenu2(environ, frame);
                JDialog menuFrame = missionMenu.getFrame();
                menuFrame.setAlwaysOnTop(true);
                if(environ.GetMissionsPerformed() == true)
                {
                     performMissions.setEnabled(false);
                     performMissions.setText("Already Completed");
                     //TODO: Make sure that MissionsPerformed is set to false in all environs at start of each mission phase
                }
            }
        });      
        
        JButton close = new JButton("Close");
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event)
            {
                //System.exit(0);
                frame.dispose();
            }
        });
        
        //add selectUnits button, mission, and search panels, close to the main panel
        mainPanel.add(selectUnits, "wrap");
        mainPanel.add(resolveCombat, "wrap");
        mainPanel.add(performMissions, "wrap");
      //  mainPanel.add(missionPanel, "wrap");  //old mission panel, not needed
        mainPanel.add(searchPanel, "wrap");
        mainPanel.add(close);
        
        
 
        //Display the window.
        frame.pack();
        frame.setLocation(xPos, yPos);
        frame.setVisible(true);

    }
}
