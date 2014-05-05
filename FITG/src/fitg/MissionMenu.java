/* MissionMenu.java
* Main window for missions
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
public class MissionMenu {
    JDialog frame;
    JDialog myParent;
    private Environ environ;
    private String menuName = "Mission Menu";
    String test;
    
    public MissionMenu(Environ e, JDialog parent){
        environ = e;
        myParent = parent;
        createGUI();
    }

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
        frame = new JDialog(myParent, menuName, Dialog.ModalityType.APPLICATION_MODAL);
        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JPanel mainPanel = new JPanel(new MigLayout("fill, insets 10","[fill]","[]10[fill]10[fill]10[fill]"));
        
        
        frame.add(mainPanel);
       
        JPanel missionPanel = new JPanel(new MigLayout("fill, insets 10","[fill]","[]10[fill]10[fill]10[fill]"));

        //Create list of missions
        for (Mission m : Mission.MissionList){
            JLabel missionName = new JLabel(m.name());
            
            //Create drop-down to assign characters to missions
            //TODO: check for (if character.mission != none >> remove from list
            JComboBox availableChars = new JComboBox(environ.GetMissionCapableStack().GetEntities().toArray());
            availableChars.insertItemAt("Add Character", 0);
            availableChars.setSelectedIndex(0);
            availableChars.setEditable(true);

            
            //Assign character to missions
            availableChars.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    //System.exit(0);
                    //m.MissionGroupAdd(character);
                    for (Entity c : environ.GetMissionCapableStack().GetEntities())
                    {
                        if (c.ET == EntityType.CHARACTER)
                        {
                            Character ch = (Character)c;
                            if (ch.GetName() == ((JComboBox)e.getSource()).getSelectedItem())
                                Mission.MissionList.get(0).MissionGroupAdd(ch);
                        }
                    }
                }
            });
            
            
            missionPanel.add(missionName);
            missionPanel.add(availableChars, "wrap");
        }

        

        JButton close = new JButton("Close");
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event)
            {
                //System.exit(0);
                frame.dispose();
            }
        });
        //add items to mainPanel
        mainPanel.add(missionPanel, "wrap");
        mainPanel.add(close, "span");
 
        
        frame.pack();
        //frame.setLocation(xPos, yPos);
        frame.setVisible(true);

    }
 

    
}
