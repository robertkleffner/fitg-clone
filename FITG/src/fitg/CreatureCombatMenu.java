/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fitg;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.border.Border;
import javax.swing.JDialog;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.*;

/**
 *
 * @author Matt
 */
public class CreatureCombatMenu {
    private JDialog frame;
    private List<Character> characters;
    private Environ environ;
    private JDialog myParent;
    private int characterCombat;
    private int creatureCombat;
    private int characterDamage;
    private int creatureDamage;
    private CombatCreature creature;
    private enum Actions{ Fight, Exit, Wounds };
    private Actions currentEvent;
    private JLabel txtCharCombat;
    private JLabel txtCreaCombat;
    private JLabel txtCharDmg;
    private JLabel txtCreaDmg;
    private JLabel txtStatus;
    private JButton btnAction;
    private JButton btnFlee;
    private boolean breakOffSuccessful;
    private String combatTitle;
    
    HashMap<JLabel, Character> charmap = new HashMap<JLabel, Character>();
    
    public CreatureCombatMenu(JDialog parent, List<Character> chars, Environ e, int creatureAttackNumber)
    {
        this.characters = chars;
        this.environ = e;
        this.myParent = parent;
        
        CombatCreature.CreatureName creatureType;
        
        if (creatureAttackNumber == -1)
        {
            creatureType = e.GetLocals();
            combatTitle = "Irate Locals";
        }
        else 
        {
            creatureType = e.GetCreature();
            combatTitle = "Creature";
            if(creatureType == null || creatureAttackNumber > 0)
            {
                if (creatureAttackNumber > 1)
                    creatureType = CombatCreature.CreatureName.SentryRobots;
                else
                    creatureType = CombatCreature.CreatureName.SentryRobot;
            }
        }
        
        creature = new CombatCreature(creatureType, e.GetEnvironType());
        this.characterCombat = 0;
        this.creatureCombat = 0;
        this.characterDamage = 0;
        this.creatureDamage = 0;
        this.currentEvent = Actions.Fight;
        this.breakOffSuccessful = false;
        this.creature.StartOfCombat();
        
        CreateGUI();
    }
    
    private void CreateGUI()
    {        
        frame = new JDialog(myParent, "Creature Combat", Dialog.ModalityType.APPLICATION_MODAL);
        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        Border blackline = BorderFactory.createLineBorder(Color.black, 1);
        
        // Main window
        JPanel mainPanel = new JPanel(new MigLayout("", "[center]50[center]", "[center]5[center]"));
        
        // Title
        JLabel lblTitle = new JLabel(combatTitle + " Combat");
        
        // Character Combat Rating Label and display box
        JLabel lblCharCombat = new JLabel("Combat Strength");
        txtCharCombat = new javax.swing.JLabel(Integer.toString(0));
        txtCharCombat.setHorizontalAlignment(JLabel.CENTER);
        txtCharCombat.setBorder(blackline);
        UpdateCharacterCombatDisplay();
        
        // Creature Combat Rating Label and display box
        JLabel lblCreaCombat = new JLabel("Combat Strength");
        txtCreaCombat = new javax.swing.JLabel(Integer.toString(0));
        txtCreaCombat.setHorizontalAlignment(JLabel.CENTER);
        txtCreaCombat.setBorder(blackline);
        UpdateCreatureCombatDisplay();
        
        // Action Button
        btnAction = new javax.swing.JButton("FIGHT");
        btnAction.paintImmediately(btnAction.getVisibleRect());
        btnAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                if (currentEvent == Actions.Fight)
                    DoCombat();
                else if (currentEvent == Actions.Wounds)
                    UpdateStatus("You must assign wounds to the characters");
                else if (currentEvent == Actions.Exit)
                    frame.dispose();
            }
        });
                
                // Character Damage label and display box
        JLabel lblCharDmg = new javax.swing.JLabel("Wounds");
        txtCharDmg = new javax.swing.JLabel(Integer.toString(0));
        txtCharDmg.setHorizontalAlignment(JLabel.CENTER);
        txtCharDmg.setBorder(blackline);

        // Creature Damage label and display box
        JLabel lblCreaDmg = new javax.swing.JLabel("Wounds");
        txtCreaDmg = new javax.swing.JLabel(Integer.toString(0));
        txtCreaDmg.setHorizontalAlignment(JLabel.CENTER);
        txtCreaDmg.setBorder(blackline);
        
        // Add components
        mainPanel.add(lblTitle, "span, h 40!");
        // Labels
        mainPanel.add(lblCharCombat);
        mainPanel.add(lblCreaCombat, "wrap");
        // Combat values
        mainPanel.add(txtCharCombat, "h 20!, w 100!");
        mainPanel.add(txtCreaCombat, "wrap, h 20!, w 100!");
        // Add blank space
        mainPanel.add(new JPanel(new MigLayout()), "span, h 50!");
        
        // Action Button
        mainPanel.add(btnAction, "span, height 50!, width 100!");
        
        // More blank space
        mainPanel.add(new JPanel(new MigLayout()), "span, h 50!");
        
        // Damage labels
        mainPanel.add(lblCharDmg);
        mainPanel.add(lblCreaDmg, "wrap");
        // Damage values
        mainPanel.add(txtCharDmg, "h 20!, w 100!");
        mainPanel.add(txtCreaDmg, "wrap, h 20!, w 100!");
        
        // Create Panel to store Character objects
        JPanel charPanel = new JPanel(new MigLayout("fill", "[][]", "[center][center]"));
        TitledBorder charTitle = BorderFactory.createTitledBorder("Characters");
        charTitle.setTitleJustification(TitledBorder.CENTER);
        charPanel.setBorder(charTitle);
        
        Character c = null;
        boolean col = false;
        for (int i = 0; i < characters.size(); i++)
        {
           c = characters.get(i);
           if(c.GetIsCaptured() || c.GetIsDead())
                continue;

            JLabel templabel = new JLabel();
            ImageIcon charIcon = new ImageIcon(getClass().getResource(c.GetImageFilename()));
            templabel.setIcon(charIcon);
            templabel.setHorizontalTextPosition(JLabel.CENTER);
            templabel.setVerticalTextPosition(JLabel.BOTTOM);
            templabel.setText("<html><center> <h3>" + c.GetName() + "</h3>" + 
                    "Endurnace: " + (c.GetEnduranceRating() - c.GetCurrentWounds()) + "</center></html>");
            charmap.put(templabel, c);
            
            // Load up character thingys
            if (col)
            {
                charPanel.add(templabel, "wrap, h 140!, w 100!");
                col = false;
            }
            else
            {
                charPanel.add(templabel, "h 140!, w 100!");
                col = true;
            }

            // Button logic for each unit button
            templabel.addMouseListener(new MouseAdapter() 
            {
                public void mousePressed(MouseEvent e)
                {
                        if(characterDamage > 0 && currentEvent == Actions.Wounds) {
                            Object source = e.getSource();
                            if (source instanceof JLabel) {
                                JLabel lbl = (JLabel)source;
                                Character c = charmap.get(lbl);
                                if (c.GetCurrentWounds() != c.GetEnduranceRating()){
                                    characterDamage--;
                                    UpdateCharacterDamageDisplay();
                                    UpdateCharacterCombatDisplay();
                                    //dmgLabel.setText(Integer.toString(damage));
                                    if (c.ApplyWoundtoCharacter(1)) {
                                        System.out.println("Character Dies");
                                        //   disableButton(templabel);
                                    }
                                    lbl.setText("<html><center> <h3>" + c.GetName() + "</h3>" + 
                                            "Endurnace: " + (c.GetEnduranceRating() - c.GetCurrentWounds()) +
                                            "</center></html>");
                                }
                            }
                            if (characterDamage == 0)
                                ResolveCombat();
                        }
                        else if (currentEvent == Actions.Wounds)
                        {
                            ResolveCombat();
                        }
                }
            });
        }
        
        // Create scroll bar for Characters - in case there is a bunch
        JScrollPane charScroll = new JScrollPane(charPanel);
        charScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(charScroll, "west, w 250!, h 400!");
        
        // Create Creature panel
        JPanel creaPanel = new JPanel(new MigLayout("fill", "[]", "[top][top]"));
        TitledBorder creaTitle = BorderFactory.createTitledBorder(combatTitle);
        creaTitle.setTitleJustification(TitledBorder.CENTER);
        creaPanel.setBorder(creaTitle);
        
        JLabel lblNameLbl = new JLabel("<html><h3>" + combatTitle + ":<br><br>" + creature.getName() + "</h3></html>");
        
        creaPanel.add(lblNameLbl, "wrap");
        
        JLabel lblStr = new JLabel("Strength: " + creature.getStrength());
        creaPanel.add(lblStr);
        
        if (creature.isImmortal())
        {
            JLabel lblImmortal = new JLabel(creature.getName() + " is Immortal");
            creaPanel.add(lblImmortal);
        }
        
        // Add break off button - aka. run away
        JPanel buttons = new JPanel(new MigLayout("fill", "[]", "[][]"));
        
        // FLEE!!!!!! - Starts as hidden
        btnFlee = new JButton("Run Away!");
        btnFlee.addMouseListener(new MouseAdapter() {
           public void mousePressed(MouseEvent e) {
               if (currentEvent == Actions.Wounds)
                    UpdateStatus("Wounds must be assigned!");
               else if (currentEvent == Actions.Fight)
                   DoBreakOff();
               else
                   frame.dispose();
           }
        });
        btnFlee.setVisible(false);
        
        buttons.add(btnFlee, "h 50!, w 130!");

        
        // Add buttons to bottom of creature panel
        creaPanel.add(buttons, "south");
        
        // Add creature panel to mainwindow
        mainPanel.add(creaPanel, "east, w 200!, h 400!, wrap");
        
        // Status window - will be used to display messages
        JPanel statusPanel = new JPanel(new MigLayout("fill", "[]", "[]"));
        statusPanel.setBorder(blackline);
        
        // Blank label to store text
        txtStatus = new JLabel("You are attacked by " + this.creature.getDescription());
        statusPanel.add(txtStatus);
        
        // Attach status label to bottom of main window
        mainPanel.add(statusPanel, "south, h 100!");
        
        // Add mainpanel to frame
        frame.add(mainPanel);
        
        // display window
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    // Update value for character combat strength
    private void UpdateCharacterCombatDisplay()
    {
        this.characterCombat = 0;
        for (Character c : characters)
        {
            if (!c.GetIsDead() && !c.GetIsCaptured())
                characterCombat += c.GetDefenseRating();
        }
        this.txtCharCombat.setText(Integer.toString(characterCombat));
    }
    // Update value for creature combat strength
    private void UpdateCreatureCombatDisplay()
    {
        this.creatureCombat = this.creature.getCombatStat();
        this.txtCreaCombat.setText(Integer.toString(this.creatureCombat));
    }
    // Update Character wounds count
    private void UpdateCharacterDamageDisplay()
    {
        this.txtCharDmg.setText(Integer.toString(this.characterDamage));
    }
    // Update Creature wound count
    private void UpdateCreatureDamageDiplay(int n)
    {
        this.txtCreaDmg.setText(Integer.toString(n));
    }
    // Update action button with text based on current state
    private void UpdateActionButton()
    {
        if (this.currentEvent == Actions.Fight)
        {
            btnAction.setVisible(true);
            btnAction.setText("Fight");
        }
        else if (this.currentEvent == Actions.Wounds)
        {
            btnAction.setVisible(false);
            UpdateStatus("Apply wounds to the characters.");
        }
        else
        {
            btnAction.setText("Exit");
            btnAction.setVisible(true);
            UpdateStatus("Combat is over");
        }
    }
    private void DoCombat()
    {
//    get creature's stats to be used to simulate character combat
//    startofcombat()
//    startround()
//    do the combat rolls to determine damage
//    apply wounds to characters
//    gavewounds() for each wound applied to characters
//    applywounds(number of wounds)
//    endround()
//    check creature and character
//    check if combat continues
//    check if breakoff is possible
        

        this.creature.StartRound();
        int differential = creatureCombat - characterCombat;

        int charWounds = Tables.CharacterCombatDefender(differential, Tables.DiceRoll());
        this.characterDamage = charWounds;
        
        UpdateCharacterDamageDisplay();
        
        int creatureWounds = Tables.CharacterCombatAttacker(differential, Tables.DiceRoll());
        UpdateCreatureDamageDiplay(creatureWounds);
        
        // Display popup with damage values from each side
        JOptionPane.showMessageDialog(frame, "<html><p>Characters attack for " + creatureWounds + "<br>" + creature.getName() + " attacks for " + charWounds + "</p></html>", "Combat Results", JOptionPane.PLAIN_MESSAGE);
        
        // Notify creature class with each wound given to the characters
        for (int i = 0; i <= charWounds; i++)
        {
            this.creature.gaveWounds();
        }
        
        // Apply wounds to the creature
        this.creature.applyWounds(creatureWounds);
        
        this.creature.EndRound();
        
        // Update creature combat strength value
        UpdateCreatureCombatDisplay();
        
        // Check that characters can still do damage
        if (this.characterDamage > 0)
            this.currentEvent = Actions.Wounds;
        else
            ResolveCombat();
        
        UpdateActionButton();
    }
    // Attempt to break off from combat - window will now close if break off is successful
    private void DoBreakOff()
    {
        int differential = creatureCombat - characterCombat;
        
        if (Tables.Breakoff(differential, true, this.creature.getBreakoffRollAdjust()))
        {
            this.currentEvent = Actions.Exit;
            this.btnFlee.setText("Exit");
            JOptionPane.showMessageDialog(frame, "Your party has successfully run away!");
            frame.dispose();
        }
        else
        {
            this.currentEvent = Actions.Fight;
            UpdateStatus("Your attempt to run away has failed!");
        }
        
        UpdateActionButton();
    }
    // Determine if combat will continue, determine if break off is possible
    private void ResolveCombat()
    {
        if (!this.creature.isCombatOver())
        {
            if (this.characterCombat > 0)
            {
                if (this.creature.canBreakoff())
                {
                    UpdateStatus("Your party is now able to breakoff from combat");
                    this.btnFlee.setVisible(true);
                }
                else
                {
                    this.btnFlee.setVisible(false);
                }
                this.currentEvent = Actions.Fight;
            }
            else
            {
                JOptionPane.showMessageDialog(frame, "Your party has been defeated!");
                this.currentEvent = Actions.Exit;
                frame.dispose();
            }
        }
        else
        {
            JOptionPane.showMessageDialog(frame, "You are Victorious!");
            this.currentEvent = Actions.Exit;
            frame.dispose();
        }
        
        UpdateActionButton();
    }
    
    private void UpdateStatus(String s)
    {
        this.txtStatus.setText(s);
    }
}
