/* PlanetMenu.java
* Popup window that appears when clicking topmost orbital for a planet
*/

package fitg.graphics;
 
import fitg.Planet;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.JDialog;
import net.miginfocom.swing.MigLayout;
 
/* FrameDemo.java requires no other files. */
public class PlanetMenu {
    JDialog frame;
    JFrame myParent;
    private int xPos;
    private int yPos;
    private Planet planet;
    private String name;
    private String loyalty;
    private Integer pdbLevel;
    
    public PlanetMenu(JFrame parent, int x, int y, Planet planetObj){
        myParent = parent;
        xPos = x;
        yPos = y;
        planet = planetObj;
        name = planetObj.GetPlanetName();
        loyalty = planetObj.loyalty.toString();
        pdbLevel = planetObj.PDBLevel;
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
        //Create and set up the window.
        frame = new JDialog(myParent, name, Dialog.ModalityType.APPLICATION_MODAL);
        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JPanel mainPanel = new JPanel(new MigLayout("fill, insets 10","[fill]","[]10[fill]10[fill]10[fill]"));
        
        
        frame.add(mainPanel);
        String text = "<html>";
        text += "<h3>" + name +"</h3>";
        text += "<br> Loyalty: " + loyalty +"</br>";
        text += "<br> PDB Level: " + pdbLevel +"</br>";
                
        JLabel title = new JLabel(text);
        

        JButton close = new JButton("Close");
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event)
            {
                //System.exit(0);
                frame.dispose();
            }
        });
        //add items to mainPanel
        mainPanel.add(title, "wrap");
        mainPanel.add(close);
 
        //Display the window.
        frame.pack();
        frame.setLocation(xPos, yPos);
        frame.setVisible(true);

    }
 

    
}
