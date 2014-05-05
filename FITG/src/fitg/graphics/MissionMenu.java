/* MissionMenu.java
* Main window for missions
*/

package fitg.graphics;
 
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.JDialog;
import net.miginfocom.swing.MigLayout;
 
/* FrameDemo.java requires no other files. */
public class MissionMenu {
    JDialog frame;
    JFrame myParent;
    //private int xPos;
    //private int yPos;
    private String menuName = "Mission Menu";
    
    public MissionMenu(JFrame parent){
        myParent = parent;
        createGUI();
    }

    public static void showGUI(){
        
    }
    
    public void setModal(){
        //frame.setAlwaysOnTop(true);
        frame.setModal(true);

    }
    public JDialog getFrame(){
        return frame;
    }
    public void createGUI() {
        frame = new JDialog(myParent, menuName, Dialog.ModalityType.APPLICATION_MODAL);
        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JPanel mainPanel = new JPanel(new MigLayout("fill, insets 10","[fill]","[]10[fill]10[fill]10[fill]"));
        
         frame.add(mainPanel);
       
        // Create panel for characters
        JPanel charPanel = new JPanel(new MigLayout("fill, insets 10","[fill]","[]10[fill]10[fill]10[fill]"));

        //TODO: populate charPanel with characters eligible for missions
        
        JLabel title = new JLabel(menuName);
        

        JButton missionButton = new JButton("Resolve Missions");
        missionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event)
            {
                //System.exit(0);
                frame.dispose();
            }
        });
        //add items to mainPanel
        mainPanel.add(title, "wrap");
        mainPanel.add(charPanel, "wrap");
        mainPanel.add(missionButton);
 
        
        frame.pack();
        //frame.setLocation(xPos, yPos);
        frame.setVisible(true);

    }
 

    
}
