package fitg;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;

public class CombatResultMenu {
    
    JDialog frame;
    JDialog myParent;
    private int[] results;
    private Stack friendlyStack;
    private Stack enemyStack;
    private boolean environCombat;
    private CombatRoleMenu.COMBAT CombatType;
    
    public CombatResultMenu(CombatRoleMenu.COMBAT CT, 
            JDialog parent, Stack friendly, Stack enemy, int[] damage, boolean envCombat)
    {
        myParent = parent;
        friendlyStack = friendly;
        enemyStack = enemy;
        results = damage;
        environCombat = envCombat;
        CombatType = CT;
        createGUI();
    }
    
    public void setModal(boolean modal) {
        frame.setModal(modal);
    }

    public JDialog getFrame() {
        return frame;
    }
    
    public void doDamage(CombatRoleMenu.COMBAT CombatType, Stack stack, int results) {
                
        if (CombatType == CombatRoleMenu.COMBAT.MILITARY){
                            
        }
        if (CombatType == CombatRoleMenu.COMBAT.SQUAD){
                    
        }
        if (CombatType == CombatRoleMenu.COMBAT.CHARACTER){
        
        }
                  
    }
    
    public void createGUI()
    {
        frame = new JDialog(myParent, "Combat Result", Dialog.ModalityType.APPLICATION_MODAL);

        // Create the main panel
        JPanel mainPanel = new JPanel(new MigLayout("fill","",""));
        
        // Create the combat results panel
                
        JPanel combatPanel = new JPanel(new MigLayout("fill", "[][]", "[][]"));
        TitledBorder combatTitle = BorderFactory.createTitledBorder("Combat Results");
        JLabel attackerResult = new javax.swing.JLabel();
        JLabel defenderResult = new javax.swing.JLabel();
                
        if (CombatType == CombatRoleMenu.COMBAT.MILITARY){
            attackerResult.setText("Damage dealt to attacking " + friendlyStack.GetSide() + ": " + Integer.toString(results[0]));
            defenderResult.setText("Damage dealt to defending " + enemyStack.GetSide() + ": " + Integer.toString(results[1]));            
            doDamage(CombatType, friendlyStack, results[0]);
            doDamage(CombatType, enemyStack, results[1]);
        }
        if (CombatType == CombatRoleMenu.COMBAT.SQUAD){
            attackerResult.setText("Damage dealt to " + friendlyStack.GetSide() + ": " + Integer.toString(results[0]));
            defenderResult.setText("Damage dealt to defending " + enemyStack.GetSide() + ": " +  Integer.toString(results[1]));            
            doDamage(CombatType, friendlyStack, results[0]);
            doDamage(CombatType, enemyStack, results[1]);
        }
        if (CombatType == CombatRoleMenu.COMBAT.CHARACTER){
            attackerResult.setText("Damage dealt to attacking " + friendlyStack.GetSide() + ": " + Integer.toString(results[0]));
            defenderResult.setText("Damage dealt to defending " + enemyStack.GetSide() + ": " + Integer.toString(results[1]));            
            doDamage(CombatType, friendlyStack, results[0]);
            doDamage(CombatType, enemyStack, results[1]);
        }
        
        combatPanel.setBorder(combatTitle);
        combatPanel.add(attackerResult, "wrap");
        combatPanel.add(defenderResult);
        
        // Create continue button for continue panel
        JButton continueButton = new javax.swing.JButton();
        continueButton.setText("Continue");
        
        continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (CombatType == CombatRoleMenu.COMBAT.MILITARY) {
                    MilitaryDamageMenu militaryDamageMenu = new MilitaryDamageMenu(frame, results, 
                            friendlyStack, enemyStack, environCombat, true);
                    JDialog menuFrame = militaryDamageMenu.getFrame();
                    menuFrame.setAlwaysOnTop(environCombat);
                    frame.hide();
                }
                else{
                    if (CombatType == CombatRoleMenu.COMBAT.CHARACTER) {
                        CharacterDamageMenu damageMenu = new CharacterDamageMenu(frame, results, 
                        friendlyStack, enemyStack, true);
                        JDialog menuFrame = damageMenu.getFrame();
                        menuFrame.setAlwaysOnTop(environCombat);
                        frame.hide();
                   }
                else
                    if (CombatType == CombatRoleMenu.COMBAT.SQUAD) {
                    CharacterDamageMenu damageMenu = new CharacterDamageMenu(frame, results, 
                    friendlyStack, enemyStack, true);
                    JDialog menuFrame = damageMenu.getFrame();
                    menuFrame.setAlwaysOnTop(environCombat);
                    frame.hide();
                    }
                }   
            }
        });
        
        frame.add(mainPanel);
        mainPanel.add(combatPanel, "wrap");
        mainPanel.add(continueButton);
        
        // Display the window
        frame.pack();
        frame.setLocationRelativeTo(myParent);
        frame.setVisible(true);
    }
}
