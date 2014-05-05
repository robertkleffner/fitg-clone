/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
 * @author Alex
 */
public class ScenarioSelector {
    JDialog frame;
    JFrame myParent;
    int _xClick;
    int _yClick;

    //set parent to JFrame if this breaks
    public ScenarioSelector(JFrame p, int x, int y){
        myParent = p;
        _xClick = x;
        _yClick = y;
        createGUI();
        //System.out.println("Test access to gui, name: " + FITG.MainGui.getName());
    }
    
    public void setModal(boolean modal){
        frame.setModal(modal);
    }
    
    public JDialog getFrame(){
        return frame;
    }
    
    //Create and set up the window.
    public void createGUI() {
        frame = new JDialog(myParent, "Scenario Selection", Dialog.ModalityType.APPLICATION_MODAL);
        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JPanel mainPanel = new JPanel(new MigLayout("insets 10","[fill]","[]10[]10[]10[]"));
        
        frame.add(mainPanel);
        
        /*****************Create CharacterPanel***************/
        JPanel characterPanel = new JPanel(new MigLayout("fill","[fill]","[fill]"));
        
        //create border with title
        TitledBorder characterTitle = BorderFactory.createTitledBorder("Scenarios");
        characterPanel.setBorder(characterTitle);     

        /*****************Adding objects to main panel************/
        
        JButton varu = new JButton("Varu");
        JButton egrix = new JButton("Egrix");

        varu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event)
            {
                Game.instance().scenario = "varu.dat";
                frame.dispose();
            }
        });
        
        egrix.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event)
            {
                Game.instance().scenario = "egrix.dat";
                frame.dispose();
            }
        });
        
        mainPanel.add(characterPanel, "wrap");
              
        characterPanel.add(varu);
        characterPanel.add(egrix);
        
        //Display the window.
        frame.setLocation(_xClick, _yClick);
        frame.pack();
        frame.setVisible(true);
    }
}
