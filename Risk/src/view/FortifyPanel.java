package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.Fortify;
import model.Game;
import model.StageEnum;

/**
 * This Panel will show the visual representation of being able to move armies
 * from one of the user's territories to one of their neighboring territories.
 * The user can only move one set of Territories per battle won and also per
 * turn after the attack stage. This panel is used as a pop-up in the Battle
 * stage if a Territory is won, and it is also used in the PlayerPanel for the
 * fortify stage of the User's turn.
 * 
 * @author Matt Giardina
 * 
 */
public class FortifyPanel extends JPanel {

  private Fortify fortifyObject; // The Fortify Reference
  private JLabel source; // The Number of Armies in the Source Territory
  public static JLabel amountToMove; // The Current Amount of Armies to Move
  private JLabel destination; // # of Armies - Destination Territory
  private JLabel sourcePlanet; // The Image of the Source Planet
  private JLabel destinationPlanet; // The Image of the Destination Planet
  private JLabel arrow; // The Image of the Arrow between the Planets
  private JLabel minusSign; // The Remove (-) Label that is used as a button
  private JLabel plusSign; // The Add (+) label that is used as a button
  private JLabel move; // the Move Button to move between Territories

  /**
   * The Constructor for the FortifyPanel that will get two arguments
   * 
   * @param fortifyObject
   *          - The reference to a Fortify object so we can move armies
   * @param isBattleFortify
   *          - Will tell if in Battle stage to do different things
   */
  public FortifyPanel(Fortify fortifyObject, boolean isBattleFortify) {
    // Initialize Global Variables
    this.fortifyObject = fortifyObject;
    source = new JLabel("(" + fortifyObject.getSource().getNumDice() + ")");
    destination = new JLabel("(" + fortifyObject.getDestination().getNumDice() + ")");
    sourcePlanet = new JLabel();
    destinationPlanet = new JLabel();
    arrow = new JLabel("");
    arrow.setIcon(new ImageIcon("images/fortify_arrow.png"));

    // Set up Buttons
    minusSign = new JLabel();
    minusSign.setIcon(new ImageIcon("images/fortifypanel_minus.png"));
    minusSign.setEnabled(false); // Disable Button Initially
    plusSign = new JLabel();
    plusSign.setIcon(new ImageIcon("images/fortifypanel_plus.png"));
    move = new JLabel();
    move.setIcon(new ImageIcon("images/fortifypanel_movearmies.png"));
    move.setHorizontalAlignment(SwingConstants.CENTER);
    move.setHorizontalTextPosition(SwingConstants.CENTER);

    // Set up Source Planet
    sourcePlanet.setIcon(new ImageIcon("images/planets/planet" + (fortifyObject.getSource().uniqueID + 1) + ".png"));
    sourcePlanet.setHorizontalAlignment((int) this.CENTER_ALIGNMENT);
    sourcePlanet.setHorizontalTextPosition(SwingConstants.CENTER);

    // Set up Destination Planet
    destinationPlanet.setIcon(new ImageIcon("images/planets/planet" + (fortifyObject.getDestination().uniqueID + 1) + ".png"));
    destinationPlanet.setHorizontalAlignment((int) this.CENTER_ALIGNMENT);
    destinationPlanet.setHorizontalTextPosition(SwingConstants.CENTER);

    // Add Action Listeners
    minusSign.addMouseListener(new FortifyButtonListener("-", isBattleFortify));
    plusSign.addMouseListener(new FortifyButtonListener("+", isBattleFortify));
    move.addMouseListener(new FortifyButtonListener("Move", isBattleFortify));

    // Initial Setup of Panel
    this.setBackground(Color.DARK_GRAY);
    this.setLayout(null);
    this.setVisible(true);
    this.setBounds(new Rectangle(400, 150));
    this.setPreferredSize(new Dimension(400, 150));

    // Center Items
    amountToMove.setHorizontalAlignment((int) this.CENTER_ALIGNMENT);
    amountToMove.setHorizontalTextPosition(SwingConstants.CENTER);
    arrow.setHorizontalAlignment((int) this.CENTER_ALIGNMENT);
    arrow.setHorizontalTextPosition(SwingConstants.CENTER);
    source.setHorizontalAlignment((int) this.CENTER_ALIGNMENT);
    source.setHorizontalTextPosition(SwingConstants.CENTER);

    // Set Text Colors & Fonts
    amountToMove.setForeground(Color.white);
    amountToMove.setFont(new Font("Arial", Font.BOLD, 40));
    source.setForeground(Color.white);
    destination.setForeground(Color.white);

    // Position Each Item in the Panel
    source.setBounds(0, 0, 70, 40);
    amountToMove.setBounds(70, 0, 260, 40);
    destination.setBounds(330, 0, 70, 40);
    sourcePlanet.setBounds(0, 40, 70, 70);
    arrow.setBounds(70, 40, 260, 70);
    destinationPlanet.setBounds(330, 40, 70, 70);
    minusSign.setBounds(0, 110, 70, 40);
    move.setBounds(70, 110, 260, 40);
    plusSign.setBounds(330, 110, 70, 40);

    // Add Everything to the Panel
    this.add(source);
    this.add(amountToMove);
    this.add(destination);
    this.add(sourcePlanet);
    this.add(arrow);
    this.add(destinationPlanet);
    this.add(minusSign);
    this.add(move);
    this.add(plusSign);
  }

  /**
   * The Listener that will be used for the (-), (+), and (move) buttons
   * 
   * @author Matt Giardina
   * 
   */
  private class FortifyButtonListener implements MouseListener {

    private String text;
    private boolean isBattleFortify;
    private boolean isFinished = false;

    /**
     * Construct the ButtonListener
     * 
     * @param text
     *          - The Text on the button to compare with
     * @param isBattle
     *          - Is it part of the Battle stage?
     */
    public FortifyButtonListener(String text, boolean isBattle) {
      this.text = text;
      isBattleFortify = isBattle;
    }

    /**
     * When the mouse is pressed and released (a click).
     */
    public void mouseClicked(MouseEvent e) {
      // Get Current Amount
      int amount = Integer.parseInt(amountToMove.getText());

      // When '-' Button is Clicked
      if (text.compareTo("-") == 0) {
        if (amount != 0) {
          amount--;
          amountToMove.setText(amount + "");
        }
      }
      // When '+' Button is Clicked
      else if (text.compareTo("+") == 0) {
        if (fortifyObject.getSource().getNumDice() - amount > 1) {
          amount++;
          amountToMove.setText(amount + "");
        }
      }
      // When 'Move' Button is Clicked
      else {
        Sound.playMove();
        fortifyObject.moveArmies(amount);
        amountToMove.setText("0");
        move.setEnabled(false);
        plusSign.setEnabled(false);
        minusSign.setEnabled(false);
        source.setText("(" + fortifyObject.getSource().getNumDice() + ")");
        destination.setText("(" + fortifyObject.getDestination().getNumDice() + ")");
        setEnabled(false);
        repaint();
        isFinished = true;
        Game.game.update();
        if (!isBattleFortify)
          Game.stage = StageEnum.AI_TURN;

        // MIGHT HAVE TO CHANGE
        // Game.game.stage = StageEnum.REINFORCE;
        Game.game.update();
      }

      // If the Move Button was not Clicked
      if (!isFinished) {
        // Enable / Disable Buttons if Needed
        if (amount == 0) {
          minusSign.setEnabled(false);
          move.setEnabled(false);
        } else {
          minusSign.setEnabled(true);
          if (!isBattleFortify)
            move.setEnabled(true);
        }
        if (fortifyObject.getSource().getNumDice() - amount <= 1)
          plusSign.setEnabled(false);
        else
          plusSign.setEnabled(true);

        Game.game.update(fortifyObject);
        repaint();
      }

    }

    /**
     * When the mouse hovers over the Label
     */
    public void mouseEntered(MouseEvent e) {
      if (text.compareTo("+") == 0) {
        plusSign.setIcon(new ImageIcon("images/fortifypanel_plus_hover.png"));
      } else if (text.compareTo("-") == 0) {
        minusSign.setIcon(new ImageIcon("images/fortifypanel_minus_hover.png"));
      } else {
        move.setIcon(new ImageIcon("images/fortifypanel_movearmies_hover.png"));
      }

    }

    /**
     * When the mouse leaves the hover area of the Label
     */
    public void mouseExited(MouseEvent e) {
      if (text.compareTo("+") == 0) {
        plusSign.setIcon(new ImageIcon("images/fortifypanel_plus.png"));
      } else if (text.compareTo("-") == 0) {
        minusSign.setIcon(new ImageIcon("images/fortifypanel_minus.png"));
      } else {
        move.setIcon(new ImageIcon("images/fortifypanel_movearmies.png"));
      }

    }

    public void mousePressed(MouseEvent e) {
      // TODO Auto-generated method stub

    }

    public void mouseReleased(MouseEvent e) {
      // TODO Auto-generated method stub

    }

  }

}
