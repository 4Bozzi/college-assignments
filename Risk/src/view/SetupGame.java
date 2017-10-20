package view;

import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * The Frame that the user Sees after the Splash Screen.  This will allow the
 * user to set up the number of players, difficulty, and of course their player
 * name!
 * 
 * @author Matt Giardina
 */
@SuppressWarnings("serial")
public class SetupGame extends JFrame {

  /**
   * The Constructor for this JFrame
   */
  public SetupGame() {
    // Initial Setup of Panel
    this.setVisible(false);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    this.setTitle("Super Kewl Risk: Game Setup");
    
    // Add Our Logo to the Frame (Created by: Matt Giardina)
    JLabel riskLogo = new JLabel();
    riskLogo.setIcon(new ImageIcon("images/Risk_Logo.png"));
    this.add(BorderLayout.CENTER, riskLogo);
   
    // Add a setup Panel to the Frame
    this.add(BorderLayout.SOUTH, new SetupPanel(this));
    
    // Show the user our skills
    this.pack();
  }
}
