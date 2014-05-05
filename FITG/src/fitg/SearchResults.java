/* SearchMenu.java
* Main window for searches
*/

package fitg;

/**
 *
 * @author Eric Doman
 */

import static fitg.Operations.turn;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class SearchResults {
    JDialog frame;
    JDialog myParent;
    private int _xClick;
    private int _yClick;
    List<Integer> stacksByID = new ArrayList<Integer>();
//    List<Entity> stacksByEntity = new ArrayList<Entity>();
    public boolean enemyFound = false;
//    private Stack tempStack;
    private JButton acceptCombatButton;
    private JButton declineCombatButton;
    private JLabel outcomeMessage;
    private JLabel combatPrompt;
    private Tables tables = new Tables();
    private Environ environ;
    private EntityType searchType;
    private List<Integer> ourUnits; 
    private List<Integer> enemyUnits;
    private CombatRoleMenu.COMBAT combat;
    
    //set parent to JFrame if this breaks
    public SearchResults(JDialog parent, Environ env, EntityType et, List<Integer> stacks, CombatRoleMenu.COMBAT combatType, int mouseX, int mouseY){
        stacksByID = stacks;
        _xClick =  mouseX;
        _yClick = mouseY;
        myParent = parent;
        environ = env;
        searchType = et; // pass in character or military type for character or military search
        ourUnits = new ArrayList(); 
        enemyUnits = new ArrayList();
        combat = combatType;
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
       
        frame = new JDialog(myParent, "SearchResults", Dialog.ModalityType.APPLICATION_MODAL);
        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        
        //Closes Parent window when closing
        WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                myParent.dispose();
            }
        };
        frame.addWindowListener(exitListener);    
        
        acceptCombatButton = new javax.swing.JButton();
        declineCombatButton = new javax.swing.JButton();
        outcomeMessage = new javax.swing.JLabel("No units found!");
        combatPrompt = new javax.swing.JLabel("Would you like to engage in combat?");
        
        frame.setTitle("Search Results");
        
        //TODO: Implement Combat for ActionListener to envoke
        acceptCombatButton.setText("Yes");
        acceptCombatButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event)
            {
                if (combat == CombatRoleMenu.COMBAT.MILITARY)
                {
                    combat = CombatRoleMenu.COMBAT.SQUAD;
                }
                
                frame.dispose();
                myParent.dispose();
                CombatRoleMenu doCombat = new CombatRoleMenu(null, ourUnits, enemyUnits, true, combat);
            }
        });
        
        //If not, close frame      
        declineCombatButton.setText("No");       
        declineCombatButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event)
            {
                frame.dispose();
                myParent.dispose();
            }
        });
        
        // Create the main panel
        JPanel mainPanel = new JPanel(new MigLayout("fill","[]","[fill][fill]"));
        
        // Create the results panel
        JPanel selectPanel = new JPanel(new MigLayout("fill"));
        TitledBorder searchTitle = BorderFactory.createTitledBorder("Search Outcome");
        selectPanel.setBorder(searchTitle);
        
        JPanel combatPanel = new JPanel(new MigLayout("fill","[][]","[]"));
        
        JPanel unitPanel = new JPanel(new MigLayout("fill","[fill]"));        
        TitledBorder unitPanelBorder = BorderFactory.createTitledBorder("Found units");
        unitPanel.setBorder(unitPanelBorder);
        
        //Go through all stack on environ, perform search on enemy characters/units
        int col = 1;
        int hidingValue = 0;
        int searchValue = 0;
        int highestInt = 0;
        int highestLead = 0;
        int numHiding = 0;
        for (int id : stacksByID)
        {

            Stack tempStack = Board.instance().stacks.GetStackObj(id);

            //entities = list of entities in "tempStack"
            List<Entity> entities = tempStack.GetEntities(); 
            
            //Check all entities in "entities" and make a button if they're a character
            //TODO: create buttons for military units as well
            for (int i = 0; i < entities.size(); i++) {
                Entity tempEntity = entities.get(i);
                //if entity belongs to the phasing player
                if(tempEntity.side == turn.GetPhasingSide()){
                    //  enemyFound = true;
                    enemyUnits.add(id);
                    col = 1;
                    if(tempEntity.ET == EntityType.CHARACTER){
                        Character c = (Character)tempEntity;
                        if (c.GetIntelligenceRating() > highestInt) {
                        highestInt = c.GetIntelligenceRating();
                        numHiding += 1;
                        }
                        JLabelCharacter labelChar = new JLabelCharacter(c);
                            if((col%3)==0){unitPanel.add(labelChar, "wrap");}
                            else{unitPanel.add(labelChar);}                      
                    }
                    col += 1;
                }
                else { // this is an awful, awful solution...
                   ourUnits.add(id);
                    if (searchType == EntityType.CHARACTER){
                          if(tempEntity.ET == EntityType.CHARACTER){
                              Character c = (Character)tempEntity;
                              searchValue += c.GetIntelligenceRating();
                          }
                    }
                    else {
                        if(tempEntity.ET == EntityType.CHARACTER){
                            Character c = (Character)tempEntity;
                            if (c.GetLeadershipRating() > highestLead){
                                highestLead = c.GetLeadershipRating();
                            }
                        }
                        if(tempEntity.ET == EntityType.MILITARY){
                            MilitaryForce m = (MilitaryForce)tempEntity;
                            searchValue += m.GetEnvironStrength();
                        }
                    }         
                    
                    
                }
            }

        }
        // Do search 
       
        
        hidingValue = highestInt - numHiding + environ.GetEnvironSize();
        searchValue += highestLead;
        enemyFound = tables.Search(hidingValue, searchValue);
                
        selectPanel.add(outcomeMessage);
        
        if(enemyFound == true){
            environ.detectedRebels = true;
            environ.detectedImperials = true;
                outcomeMessage.setText(String.format("%s units found!",
                    //create string "Rebel" or "Imperial"
                    turn.GetPhasingSide().toString().substring(0, 1).toUpperCase() 
                    + turn.GetPhasingSide().toString().substring(1).toLowerCase()));
                combatPanel.add(combatPrompt);
                combatPanel.add(acceptCombatButton);
                combatPanel.add(declineCombatButton);
                mainPanel.add(unitPanel, "wrap");
        }
 
        mainPanel.add(selectPanel, "wrap");

        mainPanel.add(combatPanel);
        
        frame.add(mainPanel);

        frame.setLocation(_xClick,_yClick);
        frame.pack();
        frame.setVisible(true);

    }
    
}
