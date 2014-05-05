/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fitg;

/**
 * Filename: UnitSelectionMenu
 * Author: Trevor Marquis 
 * Updated By: Taylor Trabun
 * Date Created: 10/26/2013 
 * Last Edited mm/dd/yy hh:mm: 10/26/2013 8:20 p.m.
 * 
 * This file is the Unit selection box.  
 * The player will use this box to select the units involved 
 * in a move on or amongst planets as well as those selected 
 * for a combat mission.
 * 
 * 11/1/13 - Refactored code to be using MigLayout as well as
 * to follow the design of the other menus.
 * 
 */

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.JDialog;
import net.miginfocom.swing.MigLayout;

public class UnitSelectionMenu {
    JDialog frame;
    JDialog myParent;
    List<Integer> unitStacks;
    Environ environ;
    int _xClick;
    int _yClick;
    private List<JToggleButton> buttons = new ArrayList<JToggleButton>();
    private Hashtable unitButtonAndID = new Hashtable();
    //this hashtable is used to store ids of entities shown on menu with stackID,
    //After move is clicked, selectedID list will only 
    //contain ids of entities selected to move and their stack ids.
    //private Hashtable possibleID = new Hashtable();
    //private Hashtable selectedID = new Hashtable();
    private ArrayList<Entity> possibleEntity = new ArrayList<Entity>();
    private ArrayList<Integer> possibleStackID = new ArrayList<Integer>();
    private ArrayList<Entity> selectedEntity = new ArrayList<Entity>();
    private ArrayList<Integer> selectedStackID = new ArrayList<Integer>();
    private Stack stack;
                
    //set parent to JFrame if this breaks
    public UnitSelectionMenu(JDialog parent, List<Integer> list, Environ env, int x, int y){
        myParent = parent;
        unitStacks = list;
        environ = env;
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
        frame = new JDialog(myParent, "Unit Selection", Dialog.ModalityType.APPLICATION_MODAL);
        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JPanel mainPanel = new JPanel(new MigLayout("insets 10","[fill]","[]10[]10[]10[]"));
        
        frame.add(mainPanel);
        
        /*****************Create CharacterPanel***************/
        JPanel characterPanel = new JPanel(new MigLayout("fill","[fill]","[fill]"));
        
        //create border with title
        TitledBorder characterTitle = BorderFactory.createTitledBorder("Units");
        characterPanel.setBorder(characterTitle);
        
        // Get units to populate the GUI

        int col = 1;      
        for (int i = 0; i < unitStacks.size(); i++)
        {
            col=1;
            stack = Board.instance().stacks.GetStackObj(unitStacks.get(i));
            List<Entity> entities = stack.GetEntities();
            
            JPanel stackPanel = new JPanel(new MigLayout("fill","[fill][fill][fill]"));
            TitledBorder currStack = BorderFactory.createTitledBorder("Stack " + (i+1));
            stackPanel.setBorder(currStack);
            
            for (int j = 0; j < entities.size(); j++)
            {
                //int id = entities.get(j).ID;
                //if (!entities.get(j).GetHasMoved())
                //{
                    //JToggleButton tempButton = new JToggleButton(unit);
                    
                    possibleStackID.add(stack.GetStackID());
                    Entity tempEntity =(entities.get(j));
                    JToggleButton tempButton = new JToggleButton();
                    possibleEntity.add(tempEntity);           
                    
                    // Set the icons of the charcters instead of using generic image
                    if(tempEntity.ET == EntityType.CHARACTER)
                     {
                        Character c = (Character)tempEntity;
                        ImageIcon charIcon = new ImageIcon(getClass().getResource(c.GetImageFilename()));
                        tempButton.setIcon(charIcon);        
                     }
                    else if(tempEntity.ET == EntityType.MILITARY){
                        //Set military button labels to name of unit type
                        String mobilityType = "Non-Mobile";
                        MilitaryForce m = (MilitaryForce)tempEntity;
                        if(m.GetMobile()){
                            mobilityType = "Mobile";
                        }
                        //button support html for text, used to allow newline
                        tempButton.setText("<html><center>"+m.GetType() + "<p>(" + mobilityType + ")</center></html>");
                    }

                    if((col%3)==0)
                    {
                        //characterPanel.add(tempButton, "wrap");
                        stackPanel.add(tempButton, "wrap");
                        buttons.add(tempButton);
                    }
                    else
                    {
                        //characterPanel.add(tempButton);
                        stackPanel.add(tempButton);
                        buttons.add(tempButton);
                    }
                    col+=1;
                    if(entities.get(j).GetHasMoved())
                    {
                        tempButton.setEnabled(false);
                    }
                }
            //}
            characterPanel.add(stackPanel, "wrap");
        }
        
        
        /*****************Adding objects to main panel************/
        JButton resolveCombat = new JButton("Resolve Combat");
        resolveCombat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event)
            {
                // TODO: change side to be dynamic instead of fixed
                // The boolean value at the end is used because this call is obviously for environ combat
                CombatRoleMenu combatMenu = new CombatRoleMenu(frame, 
                        environ.GetUnitsForSide(Game.instance().turn.GetPhasingSide()), 
                        environ.GetUnitsForSide(Game.instance().turn.GetNonPhasingSide()), true,
                        CombatRoleMenu.COMBAT.MILITARY);         
                /*
                CombatRoleMenu combatMenu = new CombatRoleMenu(frame, 
                        environ.GetUnitsForSide(environ.GetUnitsForSide(SIDE.IMPERIAL)), 
                        environ.GetUnitsForSide(environ.GetUnitsForSide(SIDE.REBEL)), true);
                */
                
                JDialog menuFrame = combatMenu.getFrame();
                menuFrame.setAlwaysOnTop(true);
            }
        });
        
        JButton move = new JButton("Move");
        //JButton resolve = new JButton("Resolve Combat");
        JButton cancel = new JButton("Cancel");
        

        move.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event)
            {
                int activatedButtons = 0;
                for(JToggleButton i : buttons){
                    if(i.isSelected()){
                        activatedButtons += 1;
                    }
                }
                System.out.println("Activated Buttons: "+activatedButtons);
                if(Game.instance().turn.phase == Phase.Reaction && activatedButtons!= 1){
                    JOptionPane.showMessageDialog(FITG.MainGui, "<html><center>You may only move 1 unit in the reaction phase<p>"
                                                                + "to an environ on the same planet.</center></html>");
                    return;
                }
                Enumeration ids = unitButtonAndID.keys();
                if((Board.instance().selectedEntity!=null)&&!(Board.instance().selectedEntity.isEmpty())){
                    Board.instance().selectedEntity.clear();
                }
                if((Board.instance().selectedStackID!=null)&&!(Board.instance().selectedStackID.isEmpty())){
                    Board.instance().selectedStackID.clear();
                }
                for(JToggleButton i : buttons)
                {
               
                    if(i.isSelected()){
                        selectedEntity.add(possibleEntity.get(buttons.indexOf(i)));
                        selectedStackID.add(possibleStackID.get(buttons.indexOf(i)));
                        
                    }
                }

                if(selectedEntity.isEmpty())
                {
                    JOptionPane.showMessageDialog(frame, "No units were selected. Please select units to move.");
                }
                else
                {
                    //TODO: add update of mainGUI's side vertical panel to include selected units
                    Board.instance().selectedEntity = selectedEntity;
                    Board.instance().selectedStackID = selectedStackID;
                    Board.instance().inMovement = true;
                    FITG.MainGui.GetStageLabel().setText("<html><center>" + Game.instance().turn.phase.toString() + " Phase<p>Select destination environ</center></html>");
                    FITG.MainGui.InMovementUpdateSidePanel();
                    frame.dispose();
                    if(myParent!=null){
                        myParent.dispose();
                    }
                }
            }
        });
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event)
            {
                frame.dispose();
            }
        });

        //add characterPanel, Select, and Cancel buttons 
        if(possibleEntity.isEmpty()){
            JLabel noUnitsText = new JLabel("<html><center>There are no units available" +
                                                    " for movement.<p>Please " +
                                                    "select a valid Environ.</center></html>");
            //noUnitsText.setLineWrap(true);
            //noUnitsText.setWrapStyleWord(true);
            //noUnitsText.setEditable(false);
            //noUnitsText.s
      
            cancel.setText("Close");
            
            mainPanel.add(noUnitsText,"wrap");
//            mainPanel.add(resolveCombat, "wrap");
            mainPanel.add(cancel);
            
            frame.pack();
            frame.setLocation(_xClick, _yClick);
            frame.setVisible(true);
            
            
            return;
        }
        
        mainPanel.add(characterPanel, "wrap");
        mainPanel.add(move , "wrap");

        //**********
        // 12-10-2013 RJP Added some logic that won't allow Military Combat unless
        // Both sides have Military units.  Button is not added if it fails check.
        if(environ.CheckHasMilitarySide(SIDE.REBEL) && environ.CheckHasMilitarySide(SIDE.IMPERIAL))
        {
          mainPanel.add(resolveCombat, "wrap");
        }
        //*********
        
        //mainPanel.add(resolve , "wrap"); 
        mainPanel.add(cancel);
        
        if(Board.instance().inMovement){
            SetSelectedUnits(Board.instance().selectedEntity);
        }
        
        //Display the window.
        frame.setLocation(_xClick, _yClick);
        frame.pack();
        frame.setVisible(true);
        //frame.setLocation(_xClick, _yClick);

    }
    
    public void SetSelectedUnits(ArrayList<Entity> selectedEntity){
        //any units already selected earlier will be toggled
        for(Entity i : selectedEntity){
            int selectButton = possibleEntity.indexOf(i);
            buttons.get(selectButton).setSelected(true);
        }
    }
    
    
}

