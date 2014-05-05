/* SearchMenu.java
* Main window for searches
*/

package fitg;

/**
 * @author Eric Doman
 */

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.JDialog;
import net.miginfocom.swing.MigLayout;
    
public class SearchMenu {
    JDialog frame;
    JDialog myParent;
    List<Integer> allStacksByID;
    Environ environ;
    int _xClick;
    int _yClick;
    private List<JToggleButton> buttons = new ArrayList<JToggleButton>();
    private ArrayList<Entity> possibleEntity = new ArrayList<Entity>();
    private ArrayList<Integer> possibleStackID = new ArrayList<Integer>();
    private List<Entity> searchers = new ArrayList<Entity>();
    private CombatRoleMenu.COMBAT combatType;
    boolean charCanSearch;
    boolean unitCanSearch;
    
//    private Stack tempStack;
    
    public SearchMenu(JDialog parent, Environ env, int x, int y){
        myParent = parent;
        allStacksByID = env.GetListOfStacksInEnviron();
        environ = env;
        _xClick = x;
        _yClick = y;
        createGUI();
        charCanSearch = false;
        unitCanSearch = false;     
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
        
        //if(environ.IsSearchable() == true){
        
        /***************** Create panels for units/characters ***************/
        JPanel entityPanel = new JPanel(new MigLayout("fill","[fill][fill]","[fill]"));
        TitledBorder entityBorder = BorderFactory.createTitledBorder("Entities Who Can Search");
        entityPanel.setBorder(entityBorder);
        
        JPanel characterPanel = new JPanel(new MigLayout("fill","[fill][fill]"));
        TitledBorder characterBorder = BorderFactory.createTitledBorder("Eligible Characters");
        characterPanel.setBorder(characterBorder);
        
        JPanel unitPanel = new JPanel(new MigLayout("fill","[fill][fill]"));
        TitledBorder unitBorder = BorderFactory.createTitledBorder("Eligible Units");
        unitPanel.setBorder(unitBorder);
        

        //SetButtonGroup - There can only be one character or military force searching at a time
        ButtonGroup buttongroup = new ButtonGroup();
        
        int col=1;       
        for(int id : allStacksByID)    
        {
            col=1;
            Stack tempStack = Board.instance().stacks.GetStackObj(id);
            //Stack tempStack = Board.instance().stacks.GetStackObj(allStacksByID.get(i));
            List<Entity> entities = tempStack.GetEntities();
            

            for (int j = 0; j < entities.size(); j++)
            {
                Entity tempEntity = entities.get(j);
                
                //add entity itself to "searchers" list
                //TODO: only mark entities that are involved in the search
                searchers.add(tempEntity);
                
                if(tempEntity.side == Game.instance().turn.GetNonPhasingSide()){
                    if (!entities.get(j).GetHasSearched())
                    {                    
                        //JToggleButton tempButton = new JToggleButton(unit);            
                        possibleStackID.add(tempStack.GetStackID());
                        tempEntity =(entities.get(j));
                        JToggleButton tempButton = new JToggleButton();
                        possibleEntity.add(tempEntity);  

                        // Set the icons of the charcters instead of using generic image
                        if(tempEntity.ET == EntityType.CHARACTER)
                         {
                            charCanSearch = true;
                            Character c = (Character)tempEntity;
                            JLabelCharacter labelChar = new JLabelCharacter(c);
                            if((col%3)==0){characterPanel.add(labelChar, "wrap");}
                            else{characterPanel.add(labelChar);}
                         }

                        else if(tempEntity.ET == EntityType.MILITARY){
                            //Set military button labels to name of unit type
                            unitCanSearch = true;
                            String Type = "Military";
                            MilitaryForce m = (MilitaryForce)tempEntity;
                            tempButton.setText("<html><center>"+m.GetType() + "<p>(" + Type + ")</center></html>");


                        }

                        if((col%3)==0)
                        {
                            //entityPanel.add(tempButton, "wrap");
//                            if(tempEntity.ET == EntityType.CHARACTER){characterPanel.add(tempButton, "wrap");}
//                            else if(tempEntity.ET == EntityType.MILITARY){unitPanel.add(tempButton, "wrap");}
                            if(tempEntity.ET == EntityType.MILITARY){unitPanel.add(tempButton, "wrap");}
                            
                            buttons.add(tempButton);
                            buttongroup.add(tempButton);
                        }
                        else
                        {
                            //entityPanel.add(tempButton);
//                            if(tempEntity.ET == EntityType.CHARACTER){characterPanel.add(tempButton);}
//                            else if(tempEntity.ET == EntityType.MILITARY){unitPanel.add(tempButton);}
                            if(tempEntity.ET == EntityType.MILITARY){unitPanel.add(tempButton);}
                            buttons.add(tempButton);
                            buttongroup.add(tempButton);
                        }
                        col+=1;
                    }
                }
            }

        }         
        
        if (charCanSearch == true) { entityPanel.add(characterPanel, "wrap"); }
        if (unitCanSearch == true) { entityPanel.add(unitPanel, "wrap"); }
               
        /*****************Adding objects to main panel************/
        
        //TODO: Only let buttons be clicked when allowed
        //TODO: Make searchByChar and searchByUnit actually search using characters and units 
        JButton searchByCharButton = new JButton("Search Using Characters");
        JButton searchByUnitButton = new JButton("Search Using Units");
        JButton cancel = new JButton("Cancel");
        
        searchByCharButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event)
            {
//                List<Integer> stacks = environ.GetListOfStacksInEnviron(); 
                combatType = CombatRoleMenu.COMBAT.CHARACTER;
                MarkSearchersDetected();
                if(Game.instance().turn.GetNonPhasingSide() == SIDE.IMPERIAL){
                    environ.detectedImperials = true;
                } else {
                    environ.detectedRebels = true;
                }
                SearchResults searchResults = new SearchResults(frame, environ, EntityType.CHARACTER,
                        allStacksByID, combatType,_xClick, _yClick);
            }
        });
        
        searchByUnitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event)
            {
//                List<Integer> stacks = environ.GetListOfStacksInEnviron(); 
                combatType = CombatRoleMenu.COMBAT.MILITARY;
                MarkSearchersDetected();
                SearchResults searchResults = new SearchResults(frame, environ, EntityType.MILITARY,
                        allStacksByID, combatType,_xClick, _yClick);
            }
        });
        
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event)
            {
                frame.dispose();
            }
        });
        
        //add entityPanel, Select, and Cancel buttons
        JLabel emptyMessage = new JLabel("You have no one to search with!");
        
        if( charCanSearch == false && unitCanSearch == false){
            mainPanel.add(emptyMessage);
        }else{
            
            mainPanel.add(entityPanel, "wrap");
            if(charCanSearch == true) {mainPanel.add(searchByCharButton , "wrap");}
            if(unitCanSearch == true) {mainPanel.add(searchByUnitButton , "wrap");}
        }    
        mainPanel.add(cancel);
 
        
        
        //Display the window.
        frame.setLocation(_xClick, _yClick);
        frame.pack();
        frame.setVisible(true);

       // }
    }
    
    private void MarkSearchersDetected (){
        for (Entity e : searchers){
            e.SetHasSearched(true);
        }
    }
    
}