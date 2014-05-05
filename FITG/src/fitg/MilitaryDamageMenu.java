package fitg;

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

public class MilitaryDamageMenu
{
    JDialog frame;
    JDialog myParent;
    private int[] results;
    private int damage;
    private int flag = 0;
    private int totalStr = 0;
    private int buttonsUnclicked = 0;
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
    public MilitaryDamageMenu(JDialog parent, int[] damage, Stack friendlyUnits, 
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
    
    public int getFlag()
    {
        return flag;
    }
    
    public void setFlag(int x)
    {
        flag = x;
    }
    
    public int getTotalStrength()
    {
        return totalStr;
    }
    
    public int getUnclickedButtons()
    {
        return buttonsUnclicked;
    }
    
    public void setUnclickedButtons(int x)
    {
        buttonsUnclicked = x;
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
        
        // Changed to use a local stack for storing units to be deleted
        //selectedStack = Board.instance().stacks.AddStack();
        selectedStack = new Stack();
        final List<Entity> entities = stack.GetEntities();
        MilitaryForce m = null;
        // Populate the panel with buttons
        for (int i = 0; i < entities.size(); i++)
        {
            final Entity unit = entities.get(i);
            
            String mobilityType = "Non-Mobile";
            if(unit.ET == EntityType.MILITARY) {
                m = (MilitaryForce)unit;
            } else continue;
            
            if(m.GetMobile())
            {
                mobilityType = "Mobile";
            }
            
            final int strength;
            
            if (inSpace)
            {
                strength = m.GetSpaceStrength();
            }
            else
            {
                strength = m.GetEnvironStrength();
            }
            
            totalStr += strength;
            
            JToggleButton tempButton = new JToggleButton("<html><center>" + m.GetType() + ": " + strength + "<p>(" + mobilityType + ")</center></html>");
            
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
            
            if (strength < lowest)
            {
                lowest = strength;
            }
            
            buttonsUnclicked += 1;
            
            // Button logic for each unit button
            tempButton.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent event)
                {
                    int flag = getFlag();
                    int buttons = getUnclickedButtons();
                    JToggleButton temp = (JToggleButton)event.getSource();
                    MilitaryForce tempForce = (MilitaryForce)unit;

                    if(event.getStateChange()==ItemEvent.SELECTED)
                    {
                        buttons -= 1;
                        setUnclickedButtons(buttons);
                        
                        if (strength <= damage)
                        {
                            damage -= strength;
                            
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
                        else if (strength > damage)
                        {
                            setFlag(1);
                            temp.setSelected(false);
                        }
                        
                        damageLabel.setText(Integer.toString(damage));
                        checkResolve();
                    }
                    
                    if(event.getStateChange()==ItemEvent.DESELECTED)
                    {
                        buttons += 1;
                        setUnclickedButtons(buttons);
                        
                        if (flag == 1)
                        {
                            setFlag(0);
                        }
                        else
                        {
                            damage += strength;
                            
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
                int total = getTotalStrength();
                int buttons = getUnclickedButtons();
                
                if (damage >= lowest)
                {
                    button.setEnabled(false);
                }
                else
                {
                    button.setEnabled(true);
                }
                
                if (buttons == 0)
                {
                    button.setEnabled(true);
                }
                
                if (friendly && button.isEnabled())
                {
                    // function call for destroying stack of selected units
                    Combat.destroy(selectedStack);

<<<<<<< .mine
                    /*DamageMenu damageMenu = new DamageMenu(frame, results,
=======
                    MilitaryDamageMenu damageMenu = new MilitaryDamageMenu(frame, results,
>>>>>>> .r515
                            friendlyStack, enemyStack, inSpace, !friendly);
                    JDialog menuFrame = damageMenu.getFrame();
                    menuFrame.setAlwaysOnTop(friendly);
                    frame.hide();*/
                }
                else if (button.isEnabled())
                {
                    // Selected stack deleted before changing sides
                    Combat.destroy(selectedStack);
                    frame.dispose();
                    myParent.dispose();
                }
                
                button.setEnabled(true);
            }
        });
        
        // Add menu objects to the Main Panel
        frame.add(mainPanel);
        mainPanel.add(damagePanel, "wrap");
        mainPanel.add(labelPanel, "wrap");
        mainPanel.add(resolveButton);
        
        // Display the window
        frame.pack();
        frame.setLocationRelativeTo(myParent);
        frame.setVisible(true);
    }
}
