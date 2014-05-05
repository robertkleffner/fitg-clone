/* CharacterSelectionMenu.java
 * 
 */
package fitg;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.JDialog;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Carson
 */
public class CharacterSelectionMenu {
    JDialog frame;
    JDialog myParent;

    //set parent to JFrame if this breaks
    public CharacterSelectionMenu(JDialog parent){
        myParent = parent;
        createGUI();
    }
    
    public void setModal(boolean modal){
        frame.setModal(modal);
    }
    
    public JDialog getFrame(){
        return frame;
    }
    
    //Create and set up the window.
    public void createGUI() {
       
        frame = new JDialog(myParent, "Character Selection", Dialog.ModalityType.APPLICATION_MODAL);
        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JPanel mainPanel = new JPanel(new MigLayout("fill, insets 10","[fill]","[]10[fill]10[fill]"));
      
        
        frame.add(mainPanel);
        
        /*****************Create CharacterPanel***************/
        JPanel characterPanel = new JPanel(new MigLayout("fill","[fill][fill]","[fill]"));
        
        //create border with title
        TitledBorder characterTitle = BorderFactory.createTitledBorder("Characters");
        characterPanel.setBorder(characterTitle);
        
        //set character icon
        ImageIcon unit = new ImageIcon(getClass().getResource(
        "/images/glyphicons/png/glyphicons_043_group.png"));
        
        ///Create character buttons for characterPanel
        JToggleButton character1 = new JToggleButton(unit);
        JToggleButton character2 = new JToggleButton(unit);
        JToggleButton character3 = new JToggleButton(unit);
        JToggleButton character4 = new JToggleButton(unit);
        
        //Add character buttons to the characterPanel
        characterPanel.add(character1);
        characterPanel.add(character2, "wrap");
        characterPanel.add(character3);
        characterPanel.add(character4);
 
        /*****************Adding objects to main panel************/
        
        JButton select = new JButton("Select");
        JButton cancel = new JButton("Cancel");
        
        select.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event)
            {
                //frame.dispose();
                RoleMenu roleMenu = new RoleMenu(frame);
            }
        });
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event)
            {
                frame.dispose();
            }
        });
        //add characterPanel, Select, and Cancel buttons 
        mainPanel.add(characterPanel, "wrap");
        mainPanel.add(select , "wrap");        
        mainPanel.add(cancel);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);

    }
    
}
