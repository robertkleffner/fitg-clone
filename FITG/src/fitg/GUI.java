package fitg;

import static fitg.Phase.Missions;
import static fitg.Phase.Movement;
import static fitg.Phase.Search;
import fitg.graphics.ImmovableSprite;
import fitg.graphics.framework.RenderViewport;
import fitg.graphics.SystemCreator;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.*;
import net.miginfocom.swing.MigLayout;

public class GUI extends JFrame {
    
    //JLabel mouseLoc;
    private JPopupMenu menu;
    private Toolkit toolkit;
    private boolean _fullscreen = false;
    //used to pass this parent frame to all constructors of children windows
    private JFrame myFrame = this;
    private CharacterCardPopup card;
    private boolean settingMovement = false;
    public JSlider slider;
    //vertical, currentUnits, and buttonPanel are all a part of showing the 
    //current units selected during movement
    
    private JPanel mainPanel;
    private JPanel mapPanel;
    private JPanel vertical;
    private JToolBar currentUnits;
    private JPanel buttonPanel;
    private JButton changePhase;
    private JButton editUnits;
    private JButton cancelMove;
    private RenderViewport viewport;
    //Header components
    private JPanel headerPanel;
    private JPanel imperialPanel;
    private JPanel stagePanel;
    private JPanel turnPanel;
    private JLabel turnLabel;
    private JPanel rebelPanel;
    private JLabel imperialLabel;
    private JLabel rebelLabel;
    private JLabel stageLabel;
    
    //private Stack currSelectedUnits;
    
    
    public GUI()
    {
        CreateSideAndStageHeader();
        initUI();
        SetupMoveButtons();
        //SetFullScreen(_fullscreen);
    }
    
    public JLabel GetStageLabel(){
        return stageLabel;
    }
    
    public RenderViewport getPort(){
        return viewport;
    }
    
    private void CreateSideAndStageHeader(){
        imperialLabel = new JLabel("Imperial", JLabel.CENTER);
        rebelLabel = new JLabel("Rebel", JLabel.CENTER);
        stageLabel = new JLabel("Stage: ", JLabel.CENTER);
        turnLabel = new JLabel("Turn: ", JLabel.CENTER);
        
        Border raisedbevel = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        Border loweredbevel = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        
        imperialPanel = new JPanel(new MigLayout("fill", "[]", "[]"));
        imperialPanel.add(imperialLabel, "align center");
        imperialPanel.setBorder(BorderFactory.createCompoundBorder(raisedbevel, 
                                                                   loweredbevel));
        //imperialPanel.setBackground(Color.CYAN);
        
        stagePanel = new JPanel(new MigLayout("fill", "[]", "[]"));
        stagePanel.add(stageLabel, "align center, wrap");
        stagePanel.add(turnLabel, "align center");
        stagePanel.setBorder(BorderFactory.createCompoundBorder(raisedbevel, 
                                                                loweredbevel));
        //turnPanel = new JPanel(new MigLayout("fill", "[]", "[]"));
        //turnPanel.add(turnLabel, "align center");
        //turnPanel.setBorder(BorderFactory.createCompoundBorder(raisedbevel, 
                                                                //loweredbevel));
        
        rebelPanel = new JPanel(new MigLayout("fill", "[]", "[]"));
        rebelPanel.add(rebelLabel, "align center");
        rebelPanel.setBorder(BorderFactory.createCompoundBorder(raisedbevel, 
                                                                loweredbevel));
        //rebelPanel.setBackground(Color.RED);
        //imperialPanel.setBackground(Color.CYAN);
        
        headerPanel = new JPanel(new MigLayout("fill", "[fill][fill][fill][fill]", "[fill]"));
        
        headerPanel.add(imperialPanel);
        
        headerPanel.add(stagePanel, "spanx 2");
        
        headerPanel.add(rebelPanel);
        
        
        
        //headerPanel.add(turnPanel, "skip 2, spanx 1, align center");
        
    }
    
    private void SetupMoveButtons(){
              editUnits = new JButton("<html><center>Edit Selected<p>Units</center></html>");
              editUnits.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent event)
              {
                int stackID = Board.instance().selectedStackID.get(0);
                Stack tempStack = Board.instance().stacks.GetStackObj(stackID);
                int stackLoc = tempStack.GetLocationID();
                
                //open unitSelectionMenu
                UnitSelectionMenu unitSelectionMenu = new UnitSelectionMenu(null,Board.instance().GetEnviron(stackLoc).GetUnitsForSide(Game.instance().turn.GetPhasingSide()),Board.instance().GetEnviron(stackLoc),viewport.getX(),viewport.getY());
                JDialog menuFrame = unitSelectionMenu.getFrame();
                menuFrame.setAlwaysOnTop(true);
                
              }
            });
        cancelMove = new JButton("<html><center>Cancel<p>Move</center></html>");
        cancelMove.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent event)
              {
                NotMovementUpdateSidePanel();
                Board.instance().inMovement = false;
                Board.instance().selectedEntity.clear();
                Board.instance().selectedStackID.clear();
              }
            });
        
    }
    
    private void initUI()
    {
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        Mission.Initialize();
        
        // Images belong to Glyphicons.com but are free to use
        ImageIcon iconNew = new ImageIcon(getClass().getResource(
                "/images/glyphicons/png/glyphicons_321_gamepad.png"));
        ImageIcon iconLoad = new ImageIcon(getClass().getResource(
                "/images/glyphicons/png/glyphicons_415_disk_open.png"));
        ImageIcon iconSave = new ImageIcon(getClass().getResource(
                "/images/glyphicons/png/glyphicons_446_floppy_save.png"));
        ImageIcon iconExit = new ImageIcon(getClass().getResource(
                "/images/glyphicons/png/glyphicons_388_exit.png"));
        
        JMenuItem nMenuItem = new JMenuItem("New", iconNew);
        nMenuItem.setMnemonic(KeyEvent.VK_N);
        nMenuItem.setToolTipText("New Game");
        nMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event)
            {
                System.exit(0);  // TODO: change this to new game functionality
            }
        });
        file.add(nMenuItem);
        
        JMenuItem lMenuItem = new JMenuItem("Load", iconLoad);
        lMenuItem.setMnemonic(KeyEvent.VK_L);
        lMenuItem.setToolTipText("Load Game");
        lMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event)
            {
                System.exit(0); // TODO: change this to load game functionality
            }
        });
        file.add(lMenuItem);
        
        JMenuItem sMenuItem = new JMenuItem("Save", iconSave);
        sMenuItem.setMnemonic(KeyEvent.VK_S);
        sMenuItem.setToolTipText("Save Game");
        sMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event)
            {
                System.exit(0); // TODO: change this to save game functionality
            }
        });
        file.add(sMenuItem);
        
        JMenuItem eMenuItem = new JMenuItem("Exit", iconExit);
        eMenuItem.setMnemonic(KeyEvent.VK_N);
        eMenuItem.setToolTipText("Exit to Windows");
        eMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event)
            {
                System.exit(0);
            }
        });
        file.add(eMenuItem);
        
        menubar.add(file);
        
        JMenu options = new JMenu("Options");
        menubar.add(options);
        
        JCheckBoxMenuItem fullscreenOpt = new JCheckBoxMenuItem("Fullscreen");
        options.add(fullscreenOpt);
        fullscreenOpt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event)
            {
                _fullscreen = !_fullscreen;
                SetFullScreen(_fullscreen);
            }
        });
        
        JMenu help = new JMenu("Help");
        help.setMnemonic(KeyEvent.VK_H);
        menubar.add(help);
        
        JMenuItem aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event)
            {
                //System.exit(0); // TODO: change this to about popup functionality
            }
        });
        help.add(aboutMenuItem);
        
        JMenuItem helpMenuItem = new JMenuItem("Help");
        helpMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event)
            {
                //System.exit(0); // TODO: change this to help popup functionality
            }
        });
        help.add(helpMenuItem);
        
        setJMenuBar(menubar);
        
        ImageIcon unit = new ImageIcon(getClass().getResource(
                "/images/glyphicons/png/glyphicons_043_group.png"));
        ImageIcon ship = new ImageIcon(getClass().getResource(
                "/images/glyphicons/png/glyphicons_255_boat.png"));
        ImageIcon chart = new ImageIcon(getClass().getResource(
                "/images/glyphicons/png/glyphicons_042_pie_chart.png"));
        ImageIcon character = new ImageIcon(getClass().getResource(
                "/images/Thysa.png"));
        
        //Reference later if needed
       /* this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e)
            {
                if (e.getButton() == MouseEvent.BUTTON2)
                {
                    mouseLoc.setText(e.getX() + " x " + e.getY());
                }
            }
        });
        */
        
        /* Panels and toolbars are listed below this line for organization
         * ------------------------------------------------------------------ */
        
        mainPanel = new JPanel(new MigLayout("flowy, fill, insets 30", "[fill]", "[fill][fill][fill]"));
        //mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        add(mainPanel);
        
        mainPanel.add(headerPanel);
        
        JButton stats = new JButton(chart);
        stats.setToolTipText("Open Player Stats");
        //        phasingPlayer.setText();
        final JLabel phasingPlayer = new JLabel("Phasing Player : " + Game.instance().turn.GetPhasingSide());
        phasingPlayer.setBorder(BorderFactory.createEmptyBorder(5, 2, 5, 10));
              
        final JLabel currentphase = new JLabel("Begin Game");
        currentphase.setBorder(BorderFactory.createEmptyBorder(5, 2, 5, 10));

        
        final JLabel turnlabel = new JLabel("Turn: 0");
        turnlabel.setBorder(BorderFactory.createEmptyBorder(5, 2, 5, 10));
        
        stageLabel.setText("<html><center>Begin Game<p>Click End Phase button to select a scenario and begin</center></html>");
        turnLabel.setText("Turn: 0");
        if(Game.instance().turn.GetPhasingSide()==SIDE.IMPERIAL){
            imperialPanel.setBackground(Color.CYAN);
            rebelPanel.setBackground(headerPanel.getBackground());
        }
        else{
            rebelPanel.setBackground(Color.RED);
            imperialPanel.setBackground(headerPanel.getBackground());
        }

        
        //final JLabel numberofphase = new JLabel("Phase: 0");
        //numberofphase.setBorder(BorderFactory.createEmptyBorder(5, 2, 5, 10));
        
        changePhase = new JButton("End Phase");
        changePhase.addActionListener(new ActionListener()
        {
            //int phasecounter = 0;
            int turncounter = 1;
            public void actionPerformed(ActionEvent event)
            {
                switch( Game.instance().turn.phase )
                {
                    case Start:
                    {
                        ScenarioSelector ss = new ScenarioSelector(myFrame, viewport.getWidth()/2 + viewport.getX(),viewport.getHeight()/2+ viewport.getY());
                        ScenarioLoader sl = new ScenarioLoader();
                        sl.LoadFile();
                        
                        viewport.GetCamera().SetX(Game.instance().startx);
                        viewport.GetCamera().SetY(Game.instance().starty);
                        viewport.GetCamera().SetScale(Game.instance().startzoom);
                        
                        currentphase.setText("Movement");
                        stageLabel.setText("<html><center>Movement Phase<p>Select an environ with units to move</center></html>");
                        Game.instance().turn.phase = Phase.Movement;
                        SetActivePlayerBox(false);
                        break;
                    }
                    case Movement:
                    {
                        currentphase.setText("Reaction Phase");
                        stageLabel.setText("<html><center>Reaction Phase<p>Select one unit to move to an eviron on the same planet with detected enemy units</center></html>");
                        Game.instance().turn.phase = Phase.Reaction;
                        SetActivePlayerBox(true);
                        //return all phasing player unit's hasMoved property
                        //to false; Maybe make this a seperate function? Not sure
                        //where to place function if so
                        for(int i = 0; i < Board.instance().stacks.GetListSize(); i += 1)
                        {
                            Stack s = Board.instance().stacks.GetStackObj(i);
                            if(s.GetSide() == Game.instance().turn.GetPhasingSide())
                            {
                                for(Entity e : s.GetEntities())
                                {
                                    e.SetHasMoved(false);
                                }
                            }
                        }
                        
                        break;
                    }
                    case Reaction:
                    {

                        currentphase.setText("Search Phase");
                        stageLabel.setText("<html><center>Search Phase<p>Select an environ to search</center></html>");
                        Game.instance().turn.phase = Phase.Search;
                        SetActivePlayerBox(true);
                        //return all phasing player unit's hasMoved property
                        //to false; Maybe make this a seperate function? Not sure
                        //where to place function if so
                        for(int i = 0; i < Board.instance().stacks.GetListSize(); i += 1)
                        {
                            Stack s = Board.instance().stacks.GetStackObj(i);
                            if(s.GetSide() == Game.instance().turn.GetNonPhasingSide())
                            {
                                for(Entity e : s.GetEntities())
                                {
                                    e.SetHasMoved(false);
                                }
                            }
                        }
                        
                        break;
                    }
                    case Search:
                    {
                        currentphase.setText("Missions Phase");
                        stageLabel.setText("<html><center>Mission Phase<p>Select an environ with a mission stack to start a mission</center></html>");
                        Game.instance().turn.phase = Phase.Missions;
                        for(int i = 0; i < Board.instance().stacks.GetListSize(); i += 1)
                        {
                            Stack s = Board.instance().stacks.GetStackObj(i);
                            if(s.GetSide() == Game.instance().turn.GetPhasingSide())
                            {
                                for(Entity e : s.GetEntities())
                                {
                                    e.SetHasSearched(false);
                                }
                            }
                        }
                        SetActivePlayerBox(false);
                        break;
                    }
                    case Missions:
                    {
                        currentphase.setText("Interphase Phase");
                        stageLabel.setText("<html><center>Interphase Phase</center></html>");
                        Game.instance().turn.phase = Phase.Interphase;
                        //SetActivePlayerBox(false);
                        
                        rebelPanel.setBackground(headerPanel.getBackground());
                        imperialPanel.setBackground(headerPanel.getBackground());

                        if(Game.instance().turn.GetPhasingSide() == SIDE.IMPERIAL)
                        {
                            turncounter++;
                            if(turncounter > Game.instance().GetMaxTurns() )
                            {
                                VictoryBox vb = new VictoryBox(myFrame, viewport.getWidth()/2 + viewport.getX(),viewport.getHeight()/2+ viewport.getY());
                                System.exit(0);
                            }
                        }
                        if(!Board.instance().stacks.CheckForRebelCharacters()|| Game.instance().rebeltakeover == true)
                        {
                            if(Game.instance().rebeltakeover)
                                Game.instance().winner = "Rebels";
                            new VictoryBox(myFrame, viewport.getWidth()/2 + viewport.getX(),viewport.getHeight()/2+ viewport.getY());
                            System.exit(0);
                        }
                        
                        Game.instance().turn.TurnControl();

                        break;
                    }
                    case Interphase:
                    {
                        currentphase.setText("Movement");
                        stageLabel.setText("<html><center>Movement Phase<p>Select an environ with units to move</center></html>");
                        Game.instance().turn.phase = Phase.Movement;

                        SetActivePlayerBox(false);
                        break;
                    }
                    default:
                    {
                        break;
                    }   
                }                    
                //turnlabel.setText("Turn : " + turncounter); 
                turnLabel.setText("Turn: " + turncounter);

                phasingPlayer.setText("Phasing Player : " + Game.instance().turn.GetPhasingSide());
                
                
//                if(Game.instance().turn.GetPhasingSide()==SIDE.IMPERIAL){
//                    imperialPanel.setBackground(Color.CYAN);
//                    rebelPanel.setBackground(headerPanel.getBackground());
//                }
//                else{
//                    rebelPanel.setBackground(Color.RED);
//                    imperialPanel.setBackground(headerPanel.getBackground());
//                }
             }
        });
        
        changePhase.setToolTipText("End current phase");
        
        
        
        vertical = new JPanel(new MigLayout("flowy, fill","[fill]"));
        buttonPanel = new JPanel(new MigLayout("fill","[fill]"));
        currentUnits = new JToolBar(JToolBar.VERTICAL);
        currentUnits.setFloatable(false);
        currentUnits.setMargin(new Insets(10,5,5,5));
        
        vertical.add(currentUnits, "wrap");
        vertical.add(buttonPanel, "south");
        buttonPanel.add(changePhase, "south");

        mapPanel = new JPanel(new MigLayout("fill, insets 0","[grow 0][fill][grow 0]",""));
        //mapPanel.setLayout(new BoxLayout(mapPanel, BoxLayout.X_AXIS));
        
        //Intialize Zoom Slider
        //format new JSlider(orientation, minVal, maxVal, initVal)
        //init value is just temporary.
        slider = new JSlider(JSlider.VERTICAL, 25, 100, 100);
        
        slider.addChangeListener(new ChangeListener()
        {
            public void stateChanged(ChangeEvent event)
            {
                if (!slider.getValueIsAdjusting()) {
                    float pos = (float)slider.getValue();
                    //System.out.println(pos);
                    System.out.println(((JSlider) event.getSource()).getValue());

                    viewport.GetCamera().SetScale(pos/100);
                }
            }
        });

        mapPanel.add(slider, "growy, growx 0");
        
        JPanel gameBoard = new JPanel(new MigLayout("fill,insets 0","[fill]","[]"));
        //gameBoard.setLayout(new BoxLayout(gameBoard, BoxLayout.Y_AXIS));
        // This should be initialized before being added
        InitRenderViewport(gameBoard);
        
        mapPanel.add(gameBoard, "growy, growx");
        mapPanel.add(vertical, "growy, growx 0");
        mainPanel.add(mapPanel);
        
        //temp layout build
        MigLayout bottomPanelLayout = new MigLayout(
          "fillx,insets 10",  //Layout Constraints
          "[left]10[center][center][center]10[right]", //Column constraints
          "[fill]"); //Row constraints
        JPanel bottomPanel = new JPanel(bottomPanelLayout);
        
        /*JLabel gamePhase = new JLabel("Game Phase: _phase_");
        gamePhase.setBorder(BorderFactory.createEmptyBorder(5, 2, 5, 50));
        bottomPanel.add(gamePhase, BorderLayout.WEST);
        * */        

        //bottomPanel.add(stats);

        bottomPanel.add(phasingPlayer);

        bottomPanel.add(currentphase);

        bottomPanel.add(turnlabel);
        
        //bottomPanel.add(numberofphase);
        
        //bottomPanel.add(changePhase, "wrap");
        
        //basic.add(bottomPanel);
        //mainPanel.add(bottomPanel);
        
        setTitle("Freedom in the Galaxy");
        setSize(GetScreenWorkingWidth(), GetScreenWorkingHeight());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public static int GetScreenWorkingWidth() {
        return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width - 50;
    }

    public static int GetScreenWorkingHeight() {
        return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height -50;
    }
    
    public void InMovementUpdateSidePanel(){
        
       ArrayList<Entity> selectedEntity = Board.instance().selectedEntity;
       ArrayList<Integer> selectedStackID = Board.instance().selectedStackID;
       //empty side panel
       currentUnits.removeAll();
       buttonPanel.removeAll();
       //for each selected unit, add a button to side panel
       for(Entity i : selectedEntity){
           JButton unitButton = new JButton();
           //set button properties
           if(i.ET == EntityType.CHARACTER){
               Character c = (Character)i;
               ImageIcon charIcon = new ImageIcon(getClass().getResource(c.GetImageFilename()));
               unitButton.setIcon(charIcon);
           }
           else if(i.ET == EntityType.MILITARY){
               String mobilityType = "Non-Mobile";
               MilitaryForce m = (MilitaryForce)i;
               if(m.GetMobile()){
                   mobilityType = "Mobile";
               }
               unitButton.setText("<html><center>"+m.GetType() + "<p>(" + mobilityType + ")</center></html>");
           }

           //add button to side panel
           currentUnits.add(unitButton,"wrap");
       }

       //add inMovement buttons to buttonPanel
       buttonPanel = new JPanel(new MigLayout("fill","[fill]"));
       buttonPanel.add(editUnits,"wrap");

       buttonPanel.add(cancelMove);
       
       //cancel move puts the game back into its regular non-movement state
       //and empty all selected unit lists
       
       mapPanel.remove(vertical);
       vertical = new JPanel(new MigLayout("flowy, fill","[fill]"));
       vertical.add(currentUnits, "wrap");
       vertical.add(buttonPanel, "south");
       mapPanel.add(vertical, "growy, growx 0");
       buttonPanel.doLayout();
       vertical.doLayout();
       mapPanel.doLayout();
       this.doLayout();      
       vertical.repaint();
       mapPanel.repaint();
       this.repaint();
           
           
        
    }
    
    public void NotMovementUpdateSidePanel(){
       currentUnits.removeAll();
       buttonPanel.removeAll();
       buttonPanel = new JPanel(new MigLayout("fill","[fill]"));
       buttonPanel.add(changePhase, "south");
       buttonPanel.doLayout();
       mapPanel.remove(vertical);
       vertical = new JPanel(new MigLayout("flowy, fill","[fill]"));
       vertical.add(currentUnits, "wrap");
       vertical.add(buttonPanel, "south");
       mapPanel.add(vertical, "growy, growx 0");
       
       vertical.doLayout();
       mapPanel.doLayout();
       this.doLayout();      
       vertical.repaint();
       mapPanel.repaint();
       this.repaint();
       vertical.validate();
    }
    
    /** 
     * Sets program to open in full screen mode
     */
    private void SetFullScreen(boolean fullscreen) {
        GraphicsDevice gd =
            GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        if (gd.isFullScreenSupported() && fullscreen) {
            setVisible(false);
            if(isDisplayable())
                dispose();     
            setResizable(false);
            setUndecorated(true);
            gd.setFullScreenWindow(this);      
            gd.setDisplayMode(new DisplayMode(GetScreenWorkingWidth(), GetScreenWorkingHeight(), 32, 60));
      
        } else {
            setVisible(false);
            if(gd.getFullScreenWindow() != null && isDisplayable())
                gd.getFullScreenWindow().dispose();
            gd.setFullScreenWindow(null);
            setResizable(false);
            setUndecorated(false);               
            getContentPane().setPreferredSize(new Dimension(GetScreenWorkingWidth(), GetScreenWorkingHeight())); 
            setVisible(true);        
            pack();
        }
    }
    
    /**
     * Loads the render viewport into the main form.
     */
    private void InitRenderViewport(JPanel container) {
        viewport = new RenderViewport();
        SystemCreator c = new SystemCreator();
        viewport.setPreferredSize(new Dimension(GetScreenWorkingWidth(), GetScreenWorkingHeight()));
        
        // Initialize game
        // arg is to set GameType. 0 is for star system/egrix. 
        Game.instance().SetupGame();
        

        
        ImmovableSprite s = new ImmovableSprite(new ImageIcon(getClass().getResource("/images/Space.jpg")).getImage());
        viewport.ItsRenderObjects.add(s);
        
        // Dynamically create systems based off of their saved positions
        for (int i = 0; Game.instance().map.GetProvinceByID(i) != null; i++)
        {
            for (int j = 0; Game.instance().map.GetProvinceByID(i).GetSystemByID(j) != null; j++)
            {
                StarSystem system = Game.instance().map.GetProvinceByID(i).GetSystemByID(j);
                c.CreateSystem(system, viewport);
            }
        }
        //growx and growy make the image grow in both the x and y direction
        container.add(viewport, "growx, growy");
    }
    
    private void SetActivePlayerBox(boolean invert)
    {
        if (invert)
        {
            if(Game.instance().turn.GetNonPhasingSide()==SIDE.IMPERIAL){
                imperialPanel.setBackground(Color.CYAN);
                rebelPanel.setBackground(headerPanel.getBackground());
            }
            else{
                rebelPanel.setBackground(Color.RED);
                imperialPanel.setBackground(headerPanel.getBackground());
            }
        }
        else
        {
            if(Game.instance().turn.GetPhasingSide()==SIDE.IMPERIAL){
                imperialPanel.setBackground(Color.CYAN);
                rebelPanel.setBackground(headerPanel.getBackground());
            }
            else{
                rebelPanel.setBackground(Color.RED);
                imperialPanel.setBackground(headerPanel.getBackground());
            }
        }
    }
    
}
