package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Attack;
import model.Board;
import model.Fortify;
import model.Game;

/**
 * BattlePanel allows a visual representation of the interaction and the
 * attacking between two territories that are not allies. This panel will also
 * allow the user to select how many dice it would like to roll from 1-3 or from
 * 1-2 depending on how many allies they currently have and also if they are
 * attacking or defending.
 * 
 * @author Matt Giardina
 * 
 */
public class BattlePanel extends JPanel {

  private JLabel attackTerr;
  private JLabel defendTerr;
  private JLabel attack;
  private JLabel retreat;
  private JLabel attacker;
  private JLabel defender;
  private JComboBox numToRoll;
  private Attack doAttack;
  private int numAttack;
  private boolean isAttacker;

  /**
   * Constructor for the BattlePanel Class
   * 
   * @param theAttack
   *          - The attack instance to display and perform.
   * @param isAttacker
   *          - Is the Human the Attacker or Defender?
   */
  public BattlePanel(Attack theAttack, final boolean isAttacker) {
    // Get Arguments and Initialize Globals
    this.isAttacker = isAttacker;
    numAttack = 0;
    doAttack = theAttack;
    attack = new JLabel();
    retreat = new JLabel();
    attackTerr = new JLabel();
    defendTerr = new JLabel();

    // Set up the Frame
    this.setLayout(null);
    this.setBounds(new Rectangle(400, 150));
    this.setPreferredSize(new Dimension(400, 150));
    this.setVisible(true);
    this.setBackground(Color.DARK_GRAY);

    // Set up Attacker/Defender Labels
    if (!doAttack.validAttack())
      attack.setEnabled(false);
    attacker = new JLabel("Attacker: " + doAttack.getAttackingTerritory().getOwner().getName());
    attacker.setHorizontalAlignment((int) this.CENTER_ALIGNMENT);
    attacker.setForeground(Color.white);
    attacker.setBounds(new Rectangle(0, 0, 200, 20));
    defender = new JLabel("Defender: " + doAttack.getDefendingTerritory().getOwner().getName());
    defender.setHorizontalAlignment((int) this.CENTER_ALIGNMENT);
    defender.setForeground(Color.white);
    defender.setBounds(new Rectangle(200, 0, 200, 20));

    // Set up Attack & Retreat Buttons
    attack.setIcon(new ImageIcon("images/battlepanel_attackenemy.png"));
    attack.setBounds(new Rectangle(0, 110, 200, 40));
    if (doAttack.getAttackingTerritory().getNumDice() == 1)
      attack.setEnabled(false);
    attack.addMouseListener(new ButtonListener("Attack"));
    retreat.setIcon(new ImageIcon("images/battlepanel_retreat.png"));
    retreat.setBounds(new Rectangle(200, 110, 200, 40));
    retreat.addMouseListener(new ButtonListener("Retreat"));

    // Set up Territory Information Labels
    attackTerr.setIcon(new ImageIcon("images/planets/planet" + (doAttack.getAttackingTerritory().uniqueID + 1) + ".png"));
    attackTerr.setText("(" + doAttack.getAttackingTerritory().getNumDice() + ")");
    attackTerr.setForeground(Color.white);
    attackTerr.setHorizontalAlignment((int) this.CENTER_ALIGNMENT);
    attackTerr.setBounds(new Rectangle(0, 20, 200, 70));
    defendTerr.setIcon(new ImageIcon("images/planets/planet" + (doAttack.getDefendingTerritory().uniqueID + 1) + ".png"));
    defendTerr.setText("(" + doAttack.getDefendingTerritory().getNumDice() + ")");
    defendTerr.setForeground(Color.white);
    defendTerr.setHorizontalAlignment((int) this.CENTER_ALIGNMENT);
    defendTerr.setBounds(new Rectangle(200, 20, 200, 70));

    // Do Change Dice Thing
    ArrayList<Integer> theDice = new ArrayList<Integer>();
    theDice = setupDiceNumToRoll(theDice);
    numToRoll = new JComboBox(theDice.toArray());
    numToRoll.setEditable(false);
    numToRoll.setSelectedIndex(0); // Set the Selected to 1
    numToRoll.setBounds(100, 90, 200, 20);

    // Add everything to the panel
    this.add(attacker, 0);
    this.add(defender, 1);
    this.add(attackTerr, 2);
    this.add(defendTerr, 3);
    this.add(attack, 4);
    this.add(retreat, 5);
    this.add(numToRoll, 6);

    this.repaint();
  }

  /**
   * Create the ArrayList that will contain all of the available options for the
   * number of dice to roll.
   * 
   * @param theDice
   *          - The Dice of the User
   * @return
   */
  private ArrayList<Integer> setupDiceNumToRoll(ArrayList<Integer> theDice) {
    // Always Add 1
    theDice.add(1);

    // If the Human Player is the Attacker
    if (isAttacker) {
      if (doAttack.getAttackingTerritory().getNumDice() == 2) {
        // Do Nothing
      } else if (doAttack.getAttackingTerritory().getNumDice() == 3)
        theDice.add(2);
      else {
        theDice.add(2);
        theDice.add(3);
      }
    }
    // If the Human Player is the Defender
    else {
      if (doAttack.getDefendingTerritory().getNumDice() >= 2)
        theDice.add(2);
    }

    return theDice;
  }

  /**
   * The Listener that will do given actions on a button depending on whether or
   * not the text is attack or retreat.
   * 
   * @author Matt Giardina
   * 
   */
  private class ButtonListener implements MouseListener {

    private String text;

    // CONSTRUCTOR //
    public ButtonListener(String text) {
      this.text = text;
    }

    public void mouseClicked(MouseEvent e) {
      boolean finished = false;

      if (text.compareTo("Attack") == 0) {
        // If not enough to attack, disable button
        if (!doAttack.validAttack()) {
          attack.setEnabled(false);
          repaint();
          Game.game.update(doAttack);
        } else {
          attack.setEnabled(true);
          Sound.playAttack();

          // Set up the Number of Dice to Roll
          if (isAttacker) {
            System.out.println(numToRoll.getSelectedItem());
            doAttack.setAttackDice((Integer) numToRoll.getSelectedItem());

          } else
            doAttack.setDefendDice((Integer) numToRoll.getSelectedItem());

          doAttack.fightRound();

          if (doAttack.getAttackingTerritory().getOwner() == doAttack.getDefendingTerritory().getOwner()) {
            if (doAttack.getAttackingTerritory().getOwner().getName().compareTo(Board.getBoard().getPlayers().get(0).getName()) == 0) {
              new PopUpFrame(new Fortify(doAttack.getAttackingTerritory(), doAttack.getDefendingTerritory()));
              Game.game.update(1);
              finished = true;
              Sound.playDestroy(); // Play Sound Destroy Sound!
            } else {
              JOptionPane.showMessageDialog(null, "You Lost the Territory");
              Sound.playDestroy(); // Play Sound Destroy Sound!
              Game.game.update(1);
              finished = true;
            }
          }
          if (!finished) {
            if (!doAttack.validAttack())
              attack.setEnabled(false);

            // Refresh the Text
            attackTerr.setText("(" + doAttack.getAttackingTerritory().getNumDice() + ")");
            defendTerr.setText("(" + doAttack.getDefendingTerritory().getNumDice() + ")");

            doAttack = new Attack(doAttack.getAttackingTerritory(), doAttack.getDefendingTerritory());
            Game.game.update(doAttack);
          }
        }
      } else
        Game.game.update(1);

      // Refresh the Text
      attackTerr.setText("(" + doAttack.getAttackingTerritory().getNumDice() + ")");
      defendTerr.setText("(" + doAttack.getAttackingTerritory().getNumDice() + ")");

      repaint();
    }

    /**
     * When the mouse button is hovered over.
     */
    public void mouseEntered(MouseEvent e) {
      if (text.compareTo("Attack") == 0 && attack.isEnabled()) {
        attack.setIcon(new ImageIcon("images/battlepanel_attackenemy_hover.png"));
      } else
        retreat.setIcon(new ImageIcon("images/battlepanel_retreat_hover.png"));
    }

    /**
     * When the mouse leaves the hover region
     */
    public void mouseExited(MouseEvent e) {
      if (text.compareTo("Attack") == 0 && attack.isEnabled()) {
        attack.setIcon(new ImageIcon("images/battlepanel_attackenemy.png"));
      } else
        retreat.setIcon(new ImageIcon("images/battlepanel_retreat.png"));

    }

    public void mousePressed(MouseEvent e) {
      // TODO Auto-generated method stub

    }

    public void mouseReleased(MouseEvent e) {
      // TODO Auto-generated method stub

    }
  }
}
