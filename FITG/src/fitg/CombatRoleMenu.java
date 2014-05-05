package fitg;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;

public class CombatRoleMenu {

    public enum COMBAT {
        MILITARY, SQUAD, CHARACTER;
    }
         
    JDialog frame;
    public JDialog myParent;
    
    private JCheckBox defendersBox;
    private JButton acceptButton;
    private JButton exitButton;
    private JButton breakoffButton;
    private JComboBox jComboBox;
    private int thisCombat = 0;
    private int enemyCombat = 0;
    private List<Integer> friendlyList;
    private List<Integer> enemyList;
    private Stack friendlyStack;            // attacking stack
    private Stack enemyStack;               // defending stack
    HashMap characters;
    private boolean environCombat;
    private COMBAT combatType;
    private Character selectedLeader;
    public JLabel ourRating;
    public JLabel enemyRating;
    public boolean friendlyCharacter;
    public boolean friendlyMilitary;
    public boolean enemyCharacter;
    public boolean enemyMilitary;
    private boolean firefight = false;
    private Stack squad;
    private JLabel txtCharDmg;
    private JPanel charPanel =  null;
    private JPanel charPanelDefenders = null;
    private JPanel combatantPanel = null;
    private int characterDamage;
    
    HashMap <JLabel, Character> charmap = new HashMap<JLabel, Character>();
    HashMap <JLabel, Character> woundMap = new HashMap<JLabel, Character>();
    
    final Stack defendingStack = new Stack();
    
    public CombatRoleMenu(JDialog parent, List<Integer> ourUnits, List<Integer> enemyUnits, 
            boolean envCombat, CombatRoleMenu.COMBAT type) 
    {
        myParent = parent;
        friendlyList = ourUnits;
        enemyList = enemyUnits;
        environCombat = envCombat;
        combatType = type;
        friendlyCharacter = false;
        friendlyMilitary = false;
        enemyCharacter = false;
        enemyMilitary = false;
        
        createGUI();
    }
      
    //set parent to JFrame if this breaks
    /*
    public CombatRoleMenu(JDialog parent, List<Integer> ourUnits, List<Integer> enemyUnits, boolean envCombat) 
    {
        myParent = parent;
        friendlyList = ourUnits;
        enemyList = enemyUnits;
        environCombat = envCombat;
        // combatType = type;
        friendlyCharacter = false;
        friendlyMilitary = false;
        enemyCharacter = false;
        enemyMilitary = false;
        
        createGUI();
    }
    */

    private void UpdateCharacterDamageDisplay()
    {
        this.txtCharDmg.setText(Integer.toString(this.characterDamage));
    }
    
    public void setCombatType(COMBAT t){
        combatType = t;
    }
    
    public void setModal(boolean modal) {
        frame.setModal(modal);
    }

    public JDialog getFrame() {
        return frame;
    }
    
    public void setLeader(Character x)
    {
        selectedLeader = x;
    }
    
    public HashMap getCharacters()
    {
        return characters;
    }
    
    // Multipurpose function
    private void CalculateMilitaryForces()
    {
        if (combatType == COMBAT.CHARACTER) {
            ourRating.setText(Integer.toString(Combat.getCharacterCombatRating(friendlyStack, 0)));
            enemyRating.setText(Integer.toString(Combat.getCharacterCombatRating(defendingStack, 1)));
        }
        if (combatType == COMBAT.MILITARY) {
            ourRating.setText(Integer.toString(Combat.getEnvironCombatRating(friendlyStack)));
            enemyRating.setText(Integer.toString(Combat.getEnvironCombatRating(enemyStack)));
        }
        if (combatType == COMBAT.SQUAD) {
            squad = Combat.createSquadCharacter(friendlyStack);
            System.out.println("SquadRating: " + Combat.getCharacterCombatRating(squad, 0));
            ourRating.setText(Integer.toString(Combat.getCharacterCombatRating(squad, 0)));
            enemyRating.setText(Integer.toString(Combat.getCharacterCombatRating(defendingStack, 1)));           
        }
    }

    
     
    public int[] doCombat(Stack friendlyStack, Stack enemyStack)
    {
        int[] results;
        results = null;

        if(combatType == COMBAT.MILITARY) {
            if (selectedLeader == null)
            {
                results = Combat.militaryCombat(friendlyStack, enemyStack);
            }
            else
            {
                results = Combat.militaryCombat(enemyStack, enemyStack, selectedLeader, null);
            }
        }
        else if (combatType == COMBAT.SQUAD) {
            
            results = Combat.characterCombat(squad, enemyStack);
        }
        else {
            results = Combat.characterCombat(friendlyStack, defendingStack);
            
            breakoffButton.setEnabled(true);
        }

        return results;
    }
        
    //Create and set up the window.
    public void createGUI() 
    {
        // HashMap including (String name, Entity) pairs
        characters = new HashMap();
        friendlyCharacter = false;
        friendlyMilitary = false;
        enemyCharacter = false;
        enemyMilitary = false;
        
        Border blackline = BorderFactory.createLineBorder(Color.black, 1);
        
        friendlyStack = new Stack();
        friendlyStack.SetSide(Game.instance().turn.GetPhasingSide());

        // Populate the list of Leaders
        for (int i = 0; i < friendlyList.size(); i++)
        {
            Stack tempStack = Board.instance().stacks.GetStackObj(friendlyList.get(i));
            //friendlyStack = Board.instance().stacks.GetStackObj(friendlyList.get(i));
            //List<Entity> entities = friendlyStack.GetEntities();
            List<Entity> entities = tempStack.GetEntities();
            boolean first = false;
                    
            for (int j = 0; j < entities.size(); j++)
            {
                Entity character = entities.get(j);
                if (character.ET == EntityType.CHARACTER)
                {
                    if (!first)
                    {
                        first = true;
                        selectedLeader = (Character)character;
                    }
                    
                    Character leader = (Character)character;
                    characters.put(leader.GetName(), leader);
                    thisCombat += leader.GetAttackRating();
                    friendlyCharacter = true;
                }
                
                if (character.ET == EntityType.MILITARY)
                {
                    friendlyMilitary = true;
                }
                
                friendlyStack.AddToStack(character);
            }
        }

        enemyStack = new Stack();
        enemyStack.SetSide(Game.instance().turn.GetNonPhasingSide());
        // populate enemyStack
        for (int i = 0; i < enemyList.size(); i++)
        {
            for (int j = 0; j < Board.instance().stacks.GetStackObj(enemyList.get(i)).GetEntities().size(); j++)
            {
                //enemyStack.AddToStack(Board.instance().stacks.GetStackObj(enemyList.get(i)).GetEntities().get(j));
                enemyStack = Board.instance().stacks.GetStackObj(enemyList.get(i)); 
            }
        }
                
        frame = new JDialog(myParent, "Character Selection", Dialog.ModalityType.APPLICATION_MODAL);
//        myParent.dispose();
        
        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        jComboBox = new javax.swing.JComboBox();
        acceptButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        breakoffButton = new javax.swing.JButton();
               
        if (combatType == COMBAT.SQUAD)
        {       
            System.out.println("Combat is SQUAD");
            frame.setTitle("Squad Combat Menu");
        }
        
        if (combatType == COMBAT.MILITARY)
        {       
            System.out.println("Combat is MILITARY");
            frame.setTitle("Military Combat Menu");
        }
        
        if (combatType == COMBAT.CHARACTER)
        {
            
            System.out.println("Combat is CHARACTER");
            frame.setTitle("Character Combat Menu");
        }
        
        //  Create labels
        JLabel jLabel1 = new javax.swing.JLabel();
        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel1.setText("ROLE SELECTION");

        JLabel jLabel2 = new javax.swing.JLabel();
        jLabel2.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel2.setText("Leader");

        jComboBox.setEditable(true);
        
        String[] leaders = (String[])characters.keySet().toArray( new String[characters.size()]);
        if (leaders.length > 0)
        {
            jComboBox.setModel(new javax.swing.DefaultComboBoxModel(leaders));
        }
        else
        {
            jComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"NONE"}));
        }
        
        jComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event)
            {
                JComboBox combo = (JComboBox)event.getSource();
                
                HashMap characterList = getCharacters();
                String leaderName = (String)combo.getSelectedItem();
                setLeader((Character)characterList.get(leaderName));
                
                CalculateMilitaryForces();
            }
        });
        
        /*
        // Setup attack button
        acceptButton.setText("Attack");
        if (thisCombat == 0 || enemyCombat == 0)
        {
            acceptButton.setEnabled(true);
        }
        */
        
        acceptButton.setText("Attack");
        acceptButton.setEnabled(true);                
        acceptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int[] results;
                CombatResultMenu resultMenu = null;
                
                if (combatType == COMBAT.CHARACTER)
                {
                    results = doCombat(friendlyStack, defendingStack);
                    
                    if (firefight == true){
                        results[0] = 2 * results[0];
                        results[1] = 2 * results[1];
                    }
                    
                    resultMenu = new CombatResultMenu(combatType, frame, 
                            friendlyStack, defendingStack, results, environCombat);
                    
                }
                
                if (combatType == COMBAT.SQUAD)
                {
                    results = doCombat(squad, defendingStack);
                    
                    if (firefight == true){
                        results[0] = 2 * results[0];
                        results[1] = 2 * results[1];
                    }
                    
                    resultMenu = new CombatResultMenu(combatType, frame,
                            squad, defendingStack, results, environCombat);
                }
                
                if (combatType == COMBAT.MILITARY)
                {
                    results = doCombat(friendlyStack, enemyStack);
                    resultMenu = new CombatResultMenu(combatType, frame, 
                            friendlyStack, enemyStack, results, environCombat);
                }
                
                JDialog menuFrame = resultMenu.getFrame();
                menuFrame.setAlwaysOnTop(true);
                
                CalculateMilitaryForces();
            }
        });

        exitButton.setText("Exit");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                frame.dispose();
            }
        });
        
        breakoffButton.setText("Breakoff");
        breakoffButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int breakoff = 0;
                Boolean escape = false;
                
                for (Entity e : friendlyStack.GetEntities())
                {
                    breakoff += ((Character) e).GetCombatRating();
                }
                
                if (Tables.Breakoff(breakoff, true))
                {
                    System.out.println("Breakoff success!");
                    JOptionPane.showMessageDialog(myParent, "Breakoff Successful!");             
                    frame.dispose();
//                    myParent.dispose();
                }
                else
                {
                    breakoffButton.setEnabled(false);
                    JOptionPane.showMessageDialog(myParent, "Breakoff Failed!");
                }
            }
        });
        
        // Create the combat ratings panel
        JLabel jLabel3 = new javax.swing.JLabel();
        jLabel3.setText(Game.instance().turn.GetPhasingSide() +" Combat Rating: ");
        ourRating = new javax.swing.JLabel();

        JLabel jLabel4 = new javax.swing.JLabel();
        jLabel4.setText(Game.instance().turn.GetNonPhasingSide() + " Combat Rating: ");
        enemyRating = new javax.swing.JLabel();

        CalculateMilitaryForces();
        
        JPanel combatRatings = new JPanel(new MigLayout("fill", "[][]", "[][]"));
        TitledBorder ratingsTitle = BorderFactory.createTitledBorder("Combat Ratings");
        combatRatings.setBorder(ratingsTitle);
        combatRatings.add(jLabel3);
        combatRatings.add(ourRating, "wrap");
        combatRatings.add(jLabel4);
        combatRatings.add(enemyRating);

        // Create the selection panel
        JPanel selectPanel = new JPanel(new MigLayout("fill", "[][]", "[][]"));
        TitledBorder selectTitle = BorderFactory.createTitledBorder("Role Selection");
        selectPanel.setBorder(selectTitle);
        selectPanel.add(jLabel2);
        selectPanel.add(jComboBox);

        //Create the action panel
        JPanel actionPanel = new JPanel(new MigLayout("fill", "[][]", "[]"));
        actionPanel.add(acceptButton);
        actionPanel.add(exitButton);
        
        JPanel mainPanel;
        
        /* ----------------- BEGIN Richard's Code Block --------------------- */
        
        if (combatType == COMBAT.CHARACTER || combatType == COMBAT.SQUAD) // && Game.instance().turn.phase != Phase.Movement)
        {
            mainPanel = new JPanel(new MigLayout("", "[center]50[center]", "[center]5[center]"));
            
            JPanel characterPanel = new JPanel(new MigLayout("fill", "[][]", "[][]"));
            final JButton firefightButton = new javax.swing.JButton();
            firefightButton.setText("Firefight");
            
            firefightButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                firefight = true;
                firefightButton.setEnabled(false);
                }
            });
            
            TitledBorder defendersTitle = BorderFactory.createTitledBorder("Active Defenders");
            characterPanel.setBorder(defendersTitle);
                    
            /******* Code for active and inactive defenders. ********/
            defendingStack.SetSide(enemyStack.GetSide());
            for (final Entity e : enemyStack.GetEntities())
            {
                if (e.ET == EntityType.CHARACTER && !((Character) e).GetIsDead() 
                                && !((Character) e).GetIsCaptured())
                {
                    
                    
                    JCheckBox tempBox = new JCheckBox(((Character) e).GetName());
                    tempBox.addItemListener(new ItemListener()
                    {
                        @Override
                        public void itemStateChanged(ItemEvent i)
                        {
                            Object source = i.getItemSelectable();

                            if (i.getStateChange() == ItemEvent.SELECTED) 
                            {
                                defendingStack.AddToStack(e);
                                enemyStack.RemoveFromStack(e);
                                System.out.println("SELECTED");
                                CalculateMilitaryForces();
                            }

                            else if (i.getStateChange() == ItemEvent.DESELECTED) 
                            {
                                defendingStack.RemoveFromStack(e);
                                enemyStack.AddToStack(e);
                                System.out.println("DESELECTED");
                                CalculateMilitaryForces();
                            }

                            for (Entity e : defendingStack.GetEntities())
                            {
                                System.out.println(((Character) e).GetName());
                            }
                        }
                    });               
                    characterPanel.add(tempBox);                    
                }
            }

            //Character c = null;
            boolean col = false;     
            List<Entity> chars = friendlyStack.GetEntities();
            Stack defendingChars = enemyStack;
            
            if(combatType == COMBAT.SQUAD)
            {
                defendingChars = squad;
            }
            
            charPanel = new JPanel(new MigLayout("fill", "[][]", "[center][center]"));
            TitledBorder charTitle = BorderFactory.createTitledBorder("Attackers");
            charTitle.setTitleJustification(TitledBorder.CENTER);
            charPanel.setBorder(charTitle);
            
            combatantPanel = new JPanel(new MigLayout("fill", "[][]", "[center][center]"));
            TitledBorder combatantTitle = BorderFactory.createTitledBorder("Combatants");
            combatantTitle.setTitleJustification(TitledBorder.CENTER);
            combatantPanel.setBorder(combatantTitle);
            
            charPanelDefenders = new JPanel(new MigLayout("fill", "[][]", "[center][center]"));
            TitledBorder charPanelDefendersTitle = BorderFactory.createTitledBorder("Defenders");
            charPanelDefendersTitle.setTitleJustification(TitledBorder.CENTER);
            charPanelDefenders.setBorder(charPanelDefendersTitle);
            
            //  Attackers
            for (Entity e : friendlyStack.GetEntities())
            {
               if (e.ET == EntityType.CHARACTER && !((Character) e).GetIsDead() 
                                && !((Character) e).GetIsCaptured())
               {
               
                JLabel templabel = new JLabel();
                ImageIcon charIcon = new ImageIcon(getClass().getResource(((Character) e).GetImageFilename()));
                templabel.setIcon(charIcon);
                templabel.setHorizontalTextPosition(JLabel.CENTER);
                templabel.setVerticalTextPosition(JLabel.BOTTOM);
                           
                //JLabelCharacter templabel = new JLabelCharacter(c);
             
                CalculateMilitaryForces();
                
                charmap.put(templabel, ((Character) e));
               
                // Load up character thingys
                if (col)
                {
                        
                        charPanel.add(templabel, "wrap, h 140!, w 100!");
                        //charPanel.add(wound);
                        col = false;
                }
                else
                {
                        charPanel.add(templabel, "h 140!, w 100!");
                        //charPanel.add(wound);
                        col = true;
                }   
               }
            }
            
            // Defenders
            for (Entity e : defendingChars.GetEntities())
            {
               if (e.ET == EntityType.CHARACTER && !((Character) e).GetIsDead() 
                                && !((Character) e).GetIsCaptured())
               {
               
                JLabel templabel = new JLabel();
                ImageIcon charIcon = new ImageIcon(getClass().getResource(((Character) e).GetImageFilename()));
                templabel.setIcon(charIcon);
                templabel.setHorizontalTextPosition(JLabel.CENTER);
                templabel.setVerticalTextPosition(JLabel.BOTTOM);
                           
                //JLabelCharacter templabel = new JLabelCharacter(c);
             
                
                CalculateMilitaryForces();
                
                charmap.put(templabel, ((Character) e));
               

                // Load up character thingys
                if (col)
                {
                        
                        charPanelDefenders.add(templabel, "wrap, h 140!, w 100!");
                        //charPanel.add(wound);
                        col = false;
                }
                else
                {
                        charPanelDefenders.add(templabel, "h 140!, w 100!");
                        //charPanel.add(wound);
                        col = true;
                }   
               }
            }
            mainPanel.add(firefightButton);
            combatantPanel.add(charPanel, "west");
            combatantPanel.add(charPanelDefenders, "east");
            actionPanel.add(breakoffButton);
            mainPanel.add(characterPanel, "wrap");
            mainPanel.add(combatantPanel);
            
            
            //mainPanel.add(charPanel, "wrap");
            
        }
        else
        {
            mainPanel = new JPanel(new MigLayout("fill","[fill]","[fill]"));
            mainPanel.add(selectPanel, "wrap");
        }
        
        /* ----------------- END Richard's Code Block ----------------------- */
        
        frame.add(mainPanel);
        
        mainPanel.add(combatRatings, "wrap");
        mainPanel.add(actionPanel);

        //Display the window.
        frame.pack();
        frame.setLocationRelativeTo(myParent);
        frame.setVisible(true);
    }
}
