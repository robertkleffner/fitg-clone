
/*
 * DamangeMenu has been refactored to MilitaryDamageMenu in order to account for
 * Character and squad damage
 * 
 * package fitg;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;

public class DamageMenu
{
    JDialog frame;
    JDialog myParent;
    private int[] results;
    private int damage;
    private JLabel damageLabel;
    private JButton resolveButton;
    private Stack friendlyStack;
    private Stack enemyStack;
    private Stack selectedStack;    // Stack of currently selected units
    private boolean inSpace;
    private int lowest = 10000;     // Assuming no units have over 10000 str.
    private boolean friendly;       // friendly indicates whos resolving damage
                                    // true = attacker, false = defender
    
    // If friendly is returned as true, it is the phasing players turn and we 
    // are distributingdamage to the friendly stack.
    // Also friendly == attacker
    public DamageMenu(JDialog parent, int[] damage, Stack friendlyUnits, 
            Stack enemyUnits, boolean environCombat, boolean friendly)
    {
        myParent = parent;
        friendlyStack = friendlyUnits;
        enemyStack = enemyUnits;
        inSpace = !environCombat;
        this.friendly = friendly;
        results = damage;
        
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
        frame.setModal(modal);
    }
    
    public JDialog getFrame()
    {
        return frame;
    }
    
    public void checkResolve()
    {
        if (damage < lowest)
        {
            resolveButton.enable();
        }
        else
        {
            resolveButton.disable();
        }
    }
    
    public void createGUI()
    {
        frame = new JDialog(myParent, "Damage Distribution", Dialog.ModalityType.APPLICATION_MODAL);
        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JPanel mainPanel = new JPanel(new MigLayout("fill, insets 10","[fill]","[]10[fill]10[fill]"));
        
        // Create the damage distribution panel
        JPanel damagePanel = new JPanel(new MigLayout("fill", "[][]", "[][]"));
        TitledBorder damageTitle = BorderFactory.createTitledBorder("Damage Distribution");
        damagePanel.setBorder(damageTitle);
        int col = 1;
        final List<JToggleButton> buttons = new ArrayList<JToggleButton>();
        
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
        final List<Entity> entities = stack.GetEntities();
        
        // Populate the panel with buttons
        for (int i = 0; i < entities.size(); i++)
        {
            // not finding the right ID or entity IDs are not being distributed
            // 
            //final int id = entities.get(i).ID;      // this is a problem
            final Entity unit = entities.get(i);
            //int localID = entities.get(i).ID;
            
            String mobilityType = "Non-Mobile";
            MilitaryForce m = (MilitaryForce)unit;
            if(m.GetMobile())
            {
                mobilityType = "Mobile";
            }
            JToggleButton tempButton = new JToggleButton("<html><center>" + m.GetType() + "<p>(" + mobilityType + ")</center></html>");
            
            if((col % 3) == 0)
            {
                damagePanel.add(tempButton, "wrap");
                buttons.add(tempButton);
            }
            else
            {
                damagePanel.add(tempButton);
                buttons.add(tempButton);
            }
            
            MilitaryForce tempForce = m;
            if (inSpace && tempForce.GetSpaceStrength() < lowest)
            {
                lowest = tempForce.GetSpaceStrength();
            }
            else if (!inSpace && tempForce.GetEnvironStrength() < lowest)
            {
                lowest = tempForce.GetEnvironStrength();
            }
            
            // Button logic for each unit button
            tempButton.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent event)
                {
                    int flag = 0;
                    JToggleButton temp = (JToggleButton)event.getSource();
                    MilitaryForce tempForce = (MilitaryForce)unit;

                    if(event.getStateChange()==ItemEvent.SELECTED)
                    {
                        if (inSpace)
                        {
                            if (tempForce.GetSpaceStrength() <= damage)
                            {
                                damage -= tempForce.GetSpaceStrength();
                                // place unit in selectedStack
                                if(friendly == true)
                                {
                                    friendlyStack.RemoveUnit(tempForce);
                                }
                                else
                                {
                                    enemyStack.RemoveUnit(tempForce);
                                }
                                selectedStack.AddToStack(tempForce);
                            }
                            else if (tempForce.GetSpaceStrength() > damage)
                            {
                                flag = 1;
                                temp.setSelected(false);
                            }
                            else
                            {
                                temp.setSelected(false);
                                // offset the reselection of the item
                                damage -= tempForce.GetSpaceStrength();
                            }
                        }
                        else
                        {
                            if (tempForce.GetEnvironStrength() <= damage)
                            {
                                // place unit in selectedStack (repeat code)
                                damage -= tempForce.GetEnvironStrength();
                                if(friendly == true)
                                {
                                    friendlyStack.RemoveUnit(tempForce);
                                }
                                else
                                {
                                    enemyStack.RemoveUnit(tempForce);
                                }
                                selectedStack.AddToStack(tempForce);
                            }
                            else if (tempForce.GetEnvironStrength() > damage)
                            {
                                flag = 1;
                                temp.setSelected(false);
                            }
                            else
                            {
                                temp.setSelected(false);
                                // offset the reselection of the item
                                damage -= tempForce.GetEnvironStrength();
                            }
                        }
                        damageLabel.setText(Integer.toString(damage));
                        checkResolve();
                    }
                    if(event.getStateChange()==ItemEvent.DESELECTED)
                    {
                        if (flag == 1)
                        {
                            flag = 0;
                        }
                        else
                        {
                            if (inSpace)
                            {
                                damage += tempForce.GetSpaceStrength();
                            }
                            else
                            {
                                damage += tempForce.GetEnvironStrength();
                            }

                            // place units back in their selected stacks
                            if(damage >= lowest)
                            {
                                if(friendly == true)
                                {
                                    friendlyStack.AddToStack(tempForce);
                                }
                                else
                                {
                                    enemyStack.AddToStack(tempForce);
                                }
                                selectedStack.RemoveUnit(tempForce);
                            }

                            damageLabel.setText(Integer.toString(damage));
                            checkResolve();
                        }
                    }
                }
            });
            col += 1;
        }
        
        JPanel labelPanel = new JPanel(new MigLayout("fill", "[center]", "[][]"));
        JLabel label = new JLabel("Damage to distribute: ");
        labelPanel.add(label);
        labelPanel.add(damageLabel);
        
        // Adding Buttons
        resolveButton = new JButton("Resolve");
        checkResolve();
        
        resolveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JButton button = (JButton)event.getSource();
                
                if (damage >= lowest)
                {
                    button.setEnabled(false);
                }
                else
                {
                    button.setEnabled(true);
                }
                
                if (friendly)
                {
                    // function call for destroying stack of selected units
                    Combat.destroy(selectedStack);

                    DamageMenu damageMenu = new DamageMenu(frame, results,
                            friendlyStack, enemyStack, inSpace, !friendly);
                    JDialog menuFrame = damageMenu.getFrame();
                    menuFrame.setAlwaysOnTop(friendly);
                    frame.hide();
                }
                else
                {
                    frame.dispose();
                    myParent.dispose();
                }
            }
        });
        
        // Add menu objects to the Main Panel
        frame.add(mainPanel);
        mainPanel.add(damagePanel, "wrap");
        mainPanel.add(labelPanel, "wrap");
        mainPanel.add(resolveButton);
        
        // Display the window
        frame.pack();
        frame.setVisible(true);
    }
}
*/
