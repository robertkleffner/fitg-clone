/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fitg;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Class for a custom JLabel that holds a character object

public class JLabelCharacter extends JLabel {
    public Character character;

    public JLabelCharacter(final Character c) {
        character = c;
        setCharacter();
        ImageIcon charIcon = new ImageIcon(getClass().getResource(c.GetImageFilename()));
        this.setIcon(charIcon);
        this.setHorizontalTextPosition(JLabel.CENTER);
        this.setVerticalTextPosition(JLabel.BOTTOM);

        //setText(text);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setToolTipText(GetPopUpText());
            }
        });
        //this.setToolTipText(GetPopUpText());
        //            this.addMouseListener(new MouseAdapter() {
        /* public void mouseEntered(MouseEvent e) {
        String text = "<html>";
        text += "<h3>" +character.GetName() + "</h3>";
        text += "Combat: " + character.GetCombatRating() + "<br>";
        text += "Endurance: " + character.GetEnduranceRating() + "<br>";
        text += "Intelligence: " + character.GetIntelligenceRating() + "<br>";
        text += "Leadership: " + character.GetLeadershipRating() + "<br>";
        text += "Diplomacy: " + character.GetDiplomacyRating() + "<br>";
        text += "Navigation: " + character.GetNavigationRating() + "<br>";
        // List Mission Bonus Draws
        text += "<h3>Bonus Draws</h3>";
        for (String s : character.GetSpecialDescriptions())
        {
        text += s + "<br>";
        }
        text += "<h3>Possessions</h3>";
        // List Character Possessions
        if (character.GetPossessionList().isEmpty())
        {
        text += "None";
        }
        else
        {
        for (Possession p : character.GetPossessionList())
        {
        text += p.GetName() + "<br>";
        // Display the possession bonus descriptions
        // Removed cause it's too much info for the mouseover
        //text += p.GetBonusDescriptionHTML() + "<br>";
        }
        }
        text += "<html>";
        setText(text);
        }
        public void mouseExited(MouseEvent e) {
        setText(character.GetName());
        }
         */
        //            });
    }

    public void setCharacter() {
        setText(character.GetName());
    }

    public Character getCharacter() {
        return character;
    }
    
    private String GetPopUpText()
    {
                //popup card
        ///*
        String text = "<html>";
        text += "<h3>" + character.GetName() + "</h3>";
        text += "Combat: " + character.GetCombatRating() + "<br>";
        text += "Endurance: " + character.GetEnduranceRating() + "<br>";
        text += "Intelligence: " + character.GetIntelligenceRating() + "<br>";
        text += "Leadership: " + character.GetLeadershipRating() + "<br>";
        text += "Diplomacy: " + character.GetDiplomacyRating() + "<br>";
        text += "Navigation: " + character.GetNavigationRating() + "<br>";
        // List Mission Bonus Draws
        text += "<h3>Bonus Draws</h3>";
        for (String s : character.GetSpecialDescriptions()) {
            text += s + "<br>";
        }
        text += "<h3>Possessions</h3>";
        // List Character Possessions
        if (character.GetPossessionList().isEmpty()) {
            text += "None";
        } else {
            for (Possession p : character.GetPossessionList()) {
                text += p.GetName() + "<br>";
                // Display the possession bonus descriptions
                // Removed cause it's too much info for the mouseover
                text += p.GetBonusDescriptionHTML() + "<br>";
            }
        }
        text += "<html>";
        
        return text;
    }
    
}
