
package fitg;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;
/**
 *
 * @author richardpark and shea newton, repurposed ben's DamageMenu code.
 */
public class CharacterDamageMenu {
  
    JDialog frame;
    JDialog myParent;
    private int[] results;
    private int damage;
    private JLabel damageLabel;
    private JButton closeButton;
    private Stack friendlyStack;
    private Stack enemyStack;
    private Stack selectedStack;    // Stack of currently selected units
    
    private boolean friendly;
    private int id;
   // private Character c;
    HashMap<JLabel, Character> charmap = new HashMap<JLabel, Character>();
    
    // If friendly is returned as true, it is the phasing players turn and we 
    // are distributingdamage to the friendly stack.
    // Also friendly == attacker
    
    public CharacterDamageMenu(JDialog parent, int[] damage, Stack friendlyUnits, 
            Stack enemyUnits, boolean friendly)
    {
        myParent = parent;
        friendlyStack = friendlyUnits;
        enemyStack = enemyUnits;
        this.friendly = friendly;
        results = damage;
        id = 0;
        //charmap = null;
        if (friendly == true)
        {
            this.damage = damage[0];
        }
        else
        {
            this.damage = damage[1];
        }
        
        createGUI();
    }
    
    public void setModal(boolean modal)
    {
        frame.setAlwaysOnTop(true);
        frame.setModal(modal);
    }
    
    public JDialog getFrame()
    {
        return frame;
    }
    
 
/*    
    public void checkResolve()
    {
        if (damage == 0)
        {
            resolveButton.enable();
        }
        else
        {
            resolveButton.disable();
        }
    }
 */   
    
    public void createGUI()
    {
        int col = 1;
        
        frame = new JDialog(myParent, "Damage Distribution", Dialog.ModalityType.APPLICATION_MODAL);
        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JPanel mainPanel = new JPanel(new MigLayout("fill, insets 10","[fill]","[]10[fill]10[fill]"));
        // Create the damage distribution panel
        JPanel damagePanel = new JPanel(new MigLayout("fill", "[][]", "[][]"));
        TitledBorder damageTitle = BorderFactory.createTitledBorder("Assign Damage");
        damagePanel.setBorder(damageTitle);
        
        JLabelCharacter labelCharacter;
//        List<JLabel> labels = new ArrayList<JLabel>();
        
        Stack stack;
        if (friendly == true)
        {
            stack = friendlyStack;
        }
        else
        {
            stack = enemyStack;
        }
        
        damageLabel = new JLabel(Integer.toString(damage));
        selectedStack = Board.instance().stacks.AddStack();
        
        List<Entity> entities = stack.GetEntities();
        
        // Populate the panel with buttons
        

        Character c = null;
        for (int i = 0; i < entities.size(); i++)
        {
            Entity e = entities.get(i);      
            if (e.ET == EntityType.CHARACTER) {
                c = (Character)e;
                if(c.GetIsCaptured() || c.GetIsDead()){
                    continue;
                }
            }
            else{
                continue;
            }
                        
            JLabel templabel = new JLabel();
            ImageIcon charIcon = new ImageIcon(getClass().getResource(c.GetImageFilename()));
            templabel.setIcon(charIcon);
            templabel.setHorizontalTextPosition(JLabel.CENTER);
            templabel.setVerticalTextPosition(JLabel.BOTTOM);
            templabel.setText("<html><center> <h3>" + c.GetName() + "</h3>" + 
                    "Endurnace: " + (c.GetEnduranceRating() - c.GetCurrentWounds()) + "</center></html>");
            charmap.put(templabel, c);
            if((col % 3) == 0)
            {
                damagePanel.add(templabel, "wrap");
  //              labels.add(templabel);
            }
            else
            {
                damagePanel.add(templabel);
//                labels.add(templabel);
            }
            // Button logic for each unit button
            templabel.addMouseListener(new MouseAdapter() 
            {
                public void mousePressed(MouseEvent e)
                {
                        if(damage > 0) {
                            Object source = e.getSource();
                            if (source instanceof JLabel) {
                                JLabel lbl = (JLabel)source;                                
                                Character c = charmap.get(lbl);
                                if (c.GetCurrentWounds() != c.GetEnduranceRating()){
                                    damage--;
                                    damageLabel.setText(Integer.toString(damage));
                                    if (c.ApplyWoundtoCharacter(1)) {
                                        System.out.println("Character Dies");
                                        //   disableButton(templabel);
                                    }
                                    lbl.setText("<html><center> <h3>" + c.GetName() + "</h3>" + 
                                            "Endurnace: " + (c.GetEnduranceRating() - c.GetCurrentWounds()) +
                                            "</center></html>");
                                }
                            }
                        }
                }
            });
            col += 1;
        }
        
        JPanel labelPanel = new JPanel(new MigLayout("fill", "[center]", "[][]"));
        JLabel label = new JLabel("Damage to distribute:");
        labelPanel.add(label);
        labelPanel.add(damageLabel);

      
        // Adding Buttons
        closeButton = new JButton("Close");
        //checkResolve();
        
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (friendly)
                {
                    // function call for destroying stack of selected units
//                    Combat.destroy(selectedStack);
                    
                    CharacterDamageMenu damageMenu = new CharacterDamageMenu(frame, results,
                            friendlyStack, enemyStack, !friendly);
                    JDialog menuFrame = damageMenu.getFrame();
                    menuFrame.setAlwaysOnTop(friendly);
                    frame.hide();
                }
                else
                {
                    frame.dispose();
                 //   myParent.dispose();
                }
            }
        });
        
        // Add menu objects to the Main Panel
        frame.add(mainPanel);
        mainPanel.add(damagePanel, "wrap");
        mainPanel.add(labelPanel, "wrap");
        mainPanel.add(closeButton);
        
        // Display the window
        frame.pack();
        frame.setVisible(true);
    }
    
 }
