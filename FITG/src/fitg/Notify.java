/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fitg;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.JDialog;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author shea newton
 */
public class Notify {
    JDialog frame;
    private int xPos;
    private int yPos;
    private String message;
    private String name;    
    
    public Notify(String detMessage){
        name = "Notification";
//        xPos = x;
//        yPos = y;
        message = detMessage;
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
        frame = new JDialog(null, name, Dialog.ModalityType.APPLICATION_MODAL);
        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JPanel mainPanel = new JPanel(new MigLayout("fill, insets 10","[fill]","[]10[fill]10[fill]10[fill]"));
        
        
        frame.add(mainPanel);
        String text = "<html>";
        text += message;
                       
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
 //       frame.setLocation(xPos, yPos);
        frame.setVisible(true);

    }
    
}

