package fitg;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.border.TitledBorder;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class RoleMenu {
    JDialog frame;
    JDialog myParent;
    private JButton jButton1;
    private JButton jButton2;
    private JComboBox jComboBox1;
    private JComboBox jComboBox2;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;

    //set parent to JFrame if this breaks
    public RoleMenu(JDialog parent){
        myParent = parent;
        createGUI();
    }
    
    public void setModal(boolean modal){
        frame.setModal(modal);
    }
    
    public JDialog getFrame(){
        return frame;
    }
    
    //Create and set up the window.
    public void createGUI() {
       
        frame = new JDialog(myParent, "Character Selection", Dialog.ModalityType.APPLICATION_MODAL);
        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jComboBox2 = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        frame.setTitle("RoleGUI");

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel2.setText("Pilot");

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel3.setText("Leader");

        jComboBox1.setEditable(true);
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Hans Solo", "Luke Skywalker", "Princess Lea", "Item 4" }));

        jComboBox2.setEditable(true);
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Darth Vader", "Chewbacca", "Item 3", "Item 4" }));

        jButton1.setText("Launch Mission");

        jButton2.setText("Cancel");
        jButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event)
            {
                frame.dispose();
            }
        });

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel1.setText("ROLE SELECTION");
        
        // Create the main panel
        JPanel mainPanel = new JPanel(new MigLayout("fill","[]","[fill][fill]"));
        
        // Create the selection panel
        JPanel selectPanel = new JPanel(new MigLayout("fill","[][]","[][]"));
        TitledBorder searchTitle = BorderFactory.createTitledBorder("Role Selection");
        selectPanel.setBorder(searchTitle);
        selectPanel.add(jLabel2);
        selectPanel.add(jComboBox1, "wrap");
        selectPanel.add(jLabel3);
        selectPanel.add(jComboBox2);
        
        //Create the action panel
        JPanel actionPanel = new JPanel(new MigLayout("fill","[][]","[]"));
        actionPanel.add(jButton1);
        actionPanel.add(jButton2);
        
        frame.add(mainPanel);
        
        mainPanel.add(selectPanel, "wrap");
        mainPanel.add(actionPanel);
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);

    }
    
}
