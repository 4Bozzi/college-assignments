package view;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * The Splash Frame that will tell the user a little about this project.
 * 
 * @author Matt Giardina
 */
@SuppressWarnings("serial")
public class SplashFrame extends JFrame {

  private static JProgressBar theBar; // The Loading Bar
  private SetupGame theGameGUI; // The Setup Game Frame

  /**
   * The Constructor for the Splash (Artwork By: Matt Giardina)
   */
  public SplashFrame() {

    // Set up this Frame
    this.setTitle("Super Kewl Risk");
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    this.setLayout(new BorderLayout());

    // Show the user the screen
    theGameGUI = new SetupGame();
    theGameGUI.setVisible(false);

    // Set up the Progress Bar
    theBar = new JProgressBar();
    theBar.setIndeterminate(true);
    theBar.setToolTipText("Loading...");

    this.add(BorderLayout.SOUTH, theBar);

    // Setup & Add our Logo
    JLabel ourLogo = new JLabel();
    ourLogo.setIcon(new ImageIcon("images/Risk_Logo.png"));
    this.add(BorderLayout.NORTH, ourLogo);

    // Show the User
    this.pack();
    this.setVisible(true);

    // Fake Timer
    long begin = System.currentTimeMillis();
    // changed to one second
    while (System.currentTimeMillis() - begin < 1000) {
    }

    // Move on to the Next Screen
    this.setVisible(false);
    theGameGUI.setVisible(true);
  }

  /**
   * The Starting point of the whole risk Game
   * 
   * @param args
   *          - The Command Line Arguments (If Any)
   */
  public static void main(String args[]) {
    
    // Set to GTK+ look and feel to make it as cross platform as possible.
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InstantiationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (UnsupportedLookAndFeelException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    new SplashFrame(); // Start the Splash Frame
  }

}
