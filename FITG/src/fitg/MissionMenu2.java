/* MissionMenu.java
 * Main window for missions
 * 
 * Most of the code used to implement the drag and drop was based on information
 * found in this pdf  http://oreilly.com/catalog/jswing/chapter/dnd.beta.pdf
 *
 *
 *   THIS IS THE CLASS CURRENTLY USED FOR THE MISSION MENU
 *
 */
package fitg;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.JDialog;
import net.miginfocom.swing.MigLayout;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.TransferHandler;
import java.awt.*;

import java.awt.Cursor;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.ArrayList;
import java.util.List;

/* FrameDemo.java requires no other files. */
public class MissionMenu2 {
    
 
    
    public static void showGUI() {
    }
    public DragListenerImp dragListener = new DragListenerImp(); 
    public JLabelCharacter lastDraggedLabel;    
    DataFlavor dataFlavor = new DataFlavor(Character.class,
            Character.class.getSimpleName());
    JDialog frame;
    JDialog myParent;
    private Environ environ;
    private String menuName = "Mission Menu";
    private boolean mission_started = false;
    private String text;
    public MissionMenu2(Environ e, JDialog parent) {
        environ = e;
        Mission.Initialize();
        Mission.set_environ(e);
        myParent = parent;
        text = "";
        createGUI();
    }

    public void setModal() {
        //frame.setAlwaysOnTop(true);
        frame.setModal(true);

    }

    public JDialog getFrame() {
        return frame;
    }

    public void createGUI() {
            
        
        frame = new JDialog(myParent, menuName, Dialog.ModalityType.APPLICATION_MODAL);
        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(800,600));
        JPanel mainPanel = new JPanel(new MigLayout("fill, insets 10", "[fill]", "[]10[fill]10[fill]10[fill]"));
        frame.add(mainPanel);
        
        
        JPanel mainMissionPanel = new JPanel(new MigLayout("fill", "[fill]", "[fill]"));
        //Create A Panel for Each Available Mission
        for (Mission m : Mission.MissionList) {
            JPanelMission missionPanel = new JPanelMission(m, frame);
            //missionPanel.setLayout(new MigLayout("fill"));
            TitledBorder missionTitle = BorderFactory.createTitledBorder(m.name());   
            missionPanel.setPreferredSize(new Dimension(400, 140));
            
            //missionPanel.setMinimumSize(new Dimension(100,140));
            missionPanel.setBorder(missionTitle);
            int bonusDraws = m.bonusDraws_();
            String outcomeString = "Bonus Draws: " + bonusDraws;
            JTextAreaMission outcomeText = new JTextAreaMission(m);
            outcomeText.setText(outcomeString);
            outcomeText.setLineWrap(true);
            outcomeText.setWrapStyleWord(true);
            outcomeText.setEditable(false);
            TitledBorder outcomeTitle = BorderFactory.createTitledBorder("Outcome");
            outcomeText.setBorder(outcomeTitle);
            
            // allow objects to be dropped in panel
            new DropTargetListImp(missionPanel); 

            // Start all characters in Unassigned Mission Panel
            if (m.type() == Mission.MissionType.None) {
                for (Character c : Mission.GetAvailChars()) {
                    JLabelCharacter labelCharacter = new JLabelCharacter(c);
                    missionPanel.add(labelCharacter);
 
                    // Make the JPanelCharacter objects draggable
                    DragSource ds = new DragSource();
                    ds.createDefaultDragGestureRecognizer(labelCharacter,
                    DnDConstants.ACTION_MOVE, new DragGestureListImp());
                    ds.addDragSourceListener(dragListener);
                }
                //add the "Not Assigned" characters to mainPanel
                mainPanel.add(missionPanel, "wrap");
            }
            else {
                mainMissionPanel.add(missionPanel);
                mainMissionPanel.add(outcomeText, "wrap");
                }
        }

        //create scrollpane to scroll through all missions
        JScrollPane missionScrollPane = new JScrollPane(mainMissionPanel);
        missionScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        mainPanel.add(missionScrollPane, "wrap");    
        
        
        // Create a button for draws pushable the number of times equal to environ size
        final int draws = environ.GetEnvironSize();
        
            final JButton runAction = new JButton ("Draw Action: Remaining: "+Mission.draws_);
            runAction.paintImmediately(runAction.getVisibleRect());
            runAction.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                  Mission.environ().SetMissionsPerformed();
                  //If normal draw Decrement remaining draws
                  if(runAction.getText().contains("Remaining"))
                  {
                      Mission.ResolveAction(0);
                      Mission.SetDraws(Mission.draws_--);
                      System.out.println(Mission.draws_--);
                      runAction.setText("Draw Action: Remaining: "+Mission.draws_);
                  }
                  if(runAction.getText().contains("Bonus Draws"))
                  {
                      Mission.BonusDraws();
                      runAction.setEnabled(false);
                  }
                  if(Mission.draws_ <= 0)
                  {
                    //Change the draw action button into bonus draws
                    runAction.setText("Bonus Draws");
                    runAction.repaint();
                  }
                  mission_started = true;
                }
              });
            

            mainPanel.add(runAction, "wrap");
        
        JButton close = new JButton("Close");
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                //System.exit(0);
                frame.dispose();
            }
        });
        //add items to mainPanel
        mainPanel.add(close, "span");
        frame.pack();
        //frame.setLocation(xPos, yPos);
        frame.setVisible(true);
    }

    // Class for a  Custom Jpanel that holds a mission object
    public class JPanelMission extends JPanel {

        private Mission mission;
        public JDialog parentDialog;
        
        public JPanelMission(Mission m, JDialog myFrame) {
            this.setMission(m);
            m.panel = this;
            parentDialog = myFrame;
        }

        public void setMission(Mission mission) {
            this.mission = mission;
        }

        public Mission getMission() {
            return mission;
        }
    }
    
        // Class for a  Custom Jpanel that holds a mission object
    public class JTextAreaMission extends JTextArea {

        private Mission mission;

        public JTextAreaMission(Mission m) {
            this.setMission(m);
            m.textArea = this;
        }

        public void setMission(Mission mission) {
            this.mission = mission;
        }

        public Mission getMission() {
            return mission;
        }
    }    
    // Class for a  Custom Jpanel that holds a mission object
    
   // Class that allows characters to be transferred( dragged )between components
    class TransferableCharacter implements Transferable {

        private Character character;

        public TransferableCharacter(Character cha) {
            this.character = cha;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{dataFlavor};
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return flavor.equals(dataFlavor);
        }

        @Override
        public Object getTransferData(DataFlavor flavor)
                throws UnsupportedFlavorException, IOException {

            if (flavor.equals(dataFlavor)) {
                return character;
            } else {
                throw new UnsupportedFlavorException(flavor);
            }
        }
    }

    class DragGestureListImp implements DragGestureListener {

        @Override
        public void dragGestureRecognized(DragGestureEvent event) {
            
            if(mission_started == false)
            {   
            
                Cursor cursor = null;
                JLabelCharacter lblCharacter = (JLabelCharacter) event.getComponent();
                lastDraggedLabel = lblCharacter;

                if (event.getDragAction() == DnDConstants.ACTION_MOVE) {
                    cursor = DragSource.DefaultMoveDrop;
                }
                Character character = lblCharacter.getCharacter();
                event.startDrag(cursor, new TransferableCharacter(character));
            }
        }
    }

    public class DragListenerImp extends JFrame implements DragSourceListener {

        public void dragEnter(DragSourceDragEvent dsde) {
            //  System.out.println("Drag Enter");
        }

        public void dragOver(DragSourceDragEvent dsde) {
            // System.out.println("Drag Over");
        }

        public void dragExit(DragSourceEvent dse) {
            //   System.out.println("Drag Exit");
        }

        public void dragDropEnd(DragSourceDropEvent dsde) {
            // System.out.print("Drag Drop End: ");
            
            if (dsde.getDropSuccess()) {
                // System.out.println("Succeeded"); 
  
                JPanelMission parent = (JPanelMission) lastDraggedLabel.getParent();
                // remove character label from previous mission panel
                parent.remove(lastDraggedLabel);
                // Remove character from mission
                parent.mission.MisionGroupRemove(lastDraggedLabel.character);
                parent.mission.textArea.setText("Bonus Draws: " + parent.mission.bonusDraws_());
                frame.repaint(); // redraw screen so old label is not visible

            } else {
                System.out.println("Failed");

            }
        }

        public void dropActionChanged(DragSourceDragEvent dsde) {
            //  System.out.println("Drop Action Changed");
        }
    }

    class DropTargetListImp extends DropTargetAdapter implements DropTargetListener {

        private DropTarget dropTarget;
        private JPanelMission panel;

        public DropTargetListImp(JPanelMission panel) {
            this.panel = panel;

            dropTarget = new DropTarget(panel, DnDConstants.ACTION_MOVE, this,
                    true, null);
        }

        public void drop(DropTargetDropEvent event) {
            try {
                Transferable tr = event.getTransferable();
                Character ch = (Character) tr.getTransferData(dataFlavor);


                if (event.isDataFlavorSupported(dataFlavor)) {
                    event.acceptDrop(DnDConstants.ACTION_MOVE);

                    // Create a new copy of the character in this panel
                    JLabelCharacter labelCharacter = new JLabelCharacter(ch);
                    DragSource ds = new DragSource();
                    ds.createDefaultDragGestureRecognizer(labelCharacter,
                    DnDConstants.ACTION_MOVE, new DragGestureListImp());
                    ds.addDragSourceListener(dragListener);
                                        
                  
                    // Update Character and Mission Objects 
                    this.panel.add(labelCharacter);
                    this.panel.mission.MissionGroupAdd(labelCharacter.character);
                    labelCharacter.character.SetCurrentMission(this.panel.mission.type());
                    System.out.format("Assigned %s to %s\n", labelCharacter.character.GetName(), this.panel.mission.name());
                    this.panel.mission.panel = this.panel;
                    event.dropComplete(true);
                    event.getSourceActions();
                    this.panel.validate();
                    this.panel.mission.textArea.setText("Bonus Draws: " + this.panel.mission.bonusDraws_());
                    return;
                }
                event.rejectDrop();
            } catch (Exception e) {
                e.printStackTrace();
                event.rejectDrop();
            }
        }
    }
}
