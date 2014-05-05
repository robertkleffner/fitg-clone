/* CharacterCardPopup.java
 * author: Carson Stauffer
 * 
 * Displays a 'Character Card' which contain stats about a
 * character. This card displays when hovering over a 
 * character button.
 */
package fitg;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.JDialog;
import net.miginfocom.swing.MigLayout;


public class CharacterCardPopup {
    JDialog frame;
    JFrame myParent;
    Character myChar;
    //constructor
    public CharacterCardPopup(Character c){
        //myParent = parent;    
        myChar = c;
        createGUI();
        setModal();
    }
    
    public void setModal(){
        frame.setAlwaysOnTop(true);
        //frame.setModal(false);
    }    
    
    public JDialog getFrame(){
        return frame;
    }
    
    public void close(){
        frame.dispose();
    }
    public void createGUI(){
        frame = new JDialog(myParent, "Character Card");
        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JPanel mainPanel = new JPanel(new MigLayout("fill, insets 10","[fill]","[]10[fill]10[fill]10[fill]"));
  
        frame.add(mainPanel);
        
        //*********** titlePanel *****************
        //Initialize title panel
        JPanel titlePanel = new JPanel(new MigLayout());
        JLabel name = new JLabel(""+myChar.GetName());
  
        //Add objects to titlePanel
        titlePanel.add(name, "center");
        
        //************* statPanel ****************
        //Initialize statPanel
        JPanel statPanel = new JPanel(new MigLayout("fill"));
        JLabel combatLabel = new JLabel("Combat:");
        JLabel combatVal = new JLabel(""+myChar.GetCombatRating());
        JLabel enduranceLabel = new JLabel("Endurance:");
        JLabel enduranceVal = new JLabel(""+myChar.GetEnduranceRating());
        JLabel intelligenceLabel = new JLabel("Intelligence:");
        JLabel intelligenceVal = new JLabel(""+myChar.GetIntelligenceRating());
        JLabel leadershipLabel = new JLabel("Leadership:");
        //TODO PASS CURRENT PLANET INTO THIS FUNCTION
        JLabel leadershipVal = new JLabel(""+myChar.GetLeadershipRating());
        JLabel diplomacyLabel = new JLabel("Diplomacy:");
        JLabel diplomacyVal = new JLabel(""+myChar.GetDiplomacyRating());
        JLabel navigationLabel = new JLabel("Navigation:");
        JLabel navigationVal = new JLabel(""+myChar.GetNavigationRating());
        
        //Add objects to statPanel
        statPanel.add(combatLabel);
        statPanel.add(combatVal,"wrap");
        statPanel.add(enduranceLabel);
        statPanel.add(enduranceVal,"wrap");
        statPanel.add(intelligenceLabel);
        statPanel.add(intelligenceVal,"wrap");
        statPanel.add(leadershipLabel);
        statPanel.add(leadershipVal,"wrap");
        statPanel.add(diplomacyLabel);
        statPanel.add(diplomacyVal,"wrap");
        statPanel.add(navigationLabel);
        statPanel.add(navigationVal,"wrap");
        
        JLabel bonusHeader = new JLabel("Specials");
        statPanel.add(bonusHeader, "wrap");
        for (String s : myChar.GetSpecialDescriptions())
        {
            JLabel newBonus = new JLabel(""+s);
            statPanel.add(newBonus, "wrap");
        }
        
        
        //*************** mainPanel *******************
        //Add objects to mainPanel
        mainPanel.add(titlePanel, "wrap");
        mainPanel.add(new JSeparator(SwingConstants.HORIZONTAL), "wrap");
        mainPanel.add(statPanel, "wrap");
        
        //change this to diplay window near the character card
        frame.setLocationRelativeTo(null);

        frame.pack();
        frame.setVisible(true);
    }
}
