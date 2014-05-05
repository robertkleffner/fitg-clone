package fitg;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class FITG
{   
    public static GUI MainGui;
    
    public static void SplashScreen()
    {
        
    }

    public static void main(String[] args)
    {
        //Menu options go here
        //game = new Game(0);
        //game.RunGame();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run()
            {
                MainGui = new GUI();
                MainGui.setVisible(true);
            }
        });
    }
}
