package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import model.Fortify;
import model.Game;

/**
 * This frame is used to prompt the user to move armies to a new planet once it
 * has defeated this enemy planet. This is done because the user must move some
 * of its armies (at least one) to the new planet that it gained.
 * 
 * @author Matt Giardina
 * 
 */
public class PopUpFrame extends JFrame {

  /**
   * Constructor that takes in a Fortify Object that will contain the methods
   * necessary to move armies from one allied planet to another.
   * 
   * @param fortifyObject
   *          - The Fortify reference that makes this all possible
   */
  public PopUpFrame(Fortify fortifyObject) {
    // Set up this Frame
    this.setLayout(new BorderLayout());
    this.setBackground(Color.DARK_GRAY);
    this.setVisible(true);
    this.setDefaultCloseOperation(this.HIDE_ON_CLOSE);
    this.setLocationRelativeTo(null);
    this.setResizable(true);

    // Disable Background Panels
    Game.game.getRiskFrame().setEnabled(false);

    // Set up and Add Labels to this Frame (FortifyPanel)
    FortifyPanel.amountToMove = new JLabel("0");
    this.add(new FortifyPanel(fortifyObject, true), BorderLayout.CENTER);
    JLabel theLabel = new JLabel("You Won The Territory.  Move Armies to it!");
    theLabel.setForeground(Color.LIGHT_GRAY);
    theLabel.setBackground(Color.DARK_GRAY);
    theLabel.setHorizontalAlignment(SwingConstants.CENTER);
    theLabel.setHorizontalAlignment(SwingConstants.CENTER);
    this.add(theLabel, BorderLayout.NORTH);
    
    // Clean up the Frame
    this.pack();

    // Annonymous Inner Class (Close Listener)
    this.addWindowListener(new WindowListener() {

      public void windowActivated(WindowEvent e) {
        // TODO Auto-generated method stub

      }

      public void windowClosed(WindowEvent e) {
        Game.game.getRiskFrame().setEnabled(true);
      }

      public void windowClosing(WindowEvent e) {
        Game.game.getRiskFrame().setEnabled(true);

      }

      public void windowDeactivated(WindowEvent e) {
        // TODO Auto-generated method stub

      }

      public void windowDeiconified(WindowEvent e) {
        // TODO Auto-generated method stub

      }

      public void windowIconified(WindowEvent e) {
        // TODO Auto-generated method stub

      }

      public void windowOpened(WindowEvent e) {
        // TODO Auto-generated method stub

      }

    });
  }

}
