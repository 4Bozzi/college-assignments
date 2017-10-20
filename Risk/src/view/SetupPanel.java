package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The Panel that contains all of the components that will determine the game
 * setup from the User Input.
 * 
 * @author Matt Giardina
 */
@SuppressWarnings("serial")
public class SetupPanel extends JPanel {

  private JFrame parent; // The Parent Panel

  /**
   * The Constructor for this SetupPanel
   * 
   * @param input
   *          - The JFrame that this Panel is in.
   */
  public SetupPanel(JFrame input) {
    // Hold instance of Parent Frame
    parent = input;

    // Layout this panel
    this.setLayout(new GridLayout(4, 2));

    // Add Number of Player Drop Down & Associated Label
    String[] numPlayerItems = { "2", "3", "4", "5" };
    JComboBox numPlayers = new JComboBox(numPlayerItems);
    this.add(new JLabel("Number of Players:"));
    this.add(numPlayers);

    // Add Human Player Name Label and Text Field
    this.add(new JLabel("Human Player Name:"));
    JTextField humanName = new JTextField();
    JLabel notifyError = new JLabel("");
    this.add(humanName);

    // Add Computer Difficulty Label and Drown Down
    String[] comDiffItems = { "Easy", "Hard" };
    JComboBox comDiff = new JComboBox(comDiffItems);
    this.add(new JLabel("Computer Diffifulty:"));
    this.add(comDiff);

    // Add Risk Button & Notify Label to Panel
    // Make Start Risk Button
    JButton startRisk = new JButton("Play Risk Now!");
    humanName.addKeyListener(new TextListener(startRisk, humanName, notifyError));
    startRisk.setEnabled(false);
    startRisk.addActionListener(new ButtonListener(numPlayers, humanName, comDiff));
    this.add(startRisk);
    this.add(notifyError);
  }

  /**
   * The Play Risk Now Button listener that is used to set up the game if all
   * fields are valid.
   * 
   * @author Matt Giardina
   * 
   */
  private class ButtonListener implements ActionListener {

    private JComboBox numPlayer; // The number of Players
    private JTextField playerName; // The Human Player Name
    private JComboBox difficulty; // The Computer Difficulty

    /**
     * The Constructor for this Button Listener
     * 
     * @param numPlayer
     *          - Number of Players (Including AIs)
     * @param humanName
     *          - The Human Player Name
     * @param difficulty
     *          - The Difficulty of the Computer
     */
    public ButtonListener(JComboBox numPlayer, JTextField humanName, JComboBox difficulty) {
      this.numPlayer = numPlayer;
      this.difficulty = difficulty;
      playerName = humanName;
    }

    /**
     * On Mouse Click!
     */
    public void actionPerformed(ActionEvent e) {
      int diff = 0;

      if (((String) difficulty.getSelectedItem()).compareTo("Hard") == 0)
        diff = 1;

      // Create a new Game!
      new model.Game(Integer.parseInt((String) numPlayer.getSelectedItem()), playerName.getText(), diff);

      // Hide the Setup Frame
      parent.setVisible(false);
    }

  }

  /**
   * The Listener for the Human Player Name. If there is text in the Human
   * Player Name JTextField the PlayRisk Button will be enabled and the user
   * will be able to start the game because there is a default value for the
   * number of players and also the difficulty of the computer.
   * 
   * @author Matt Giardina
   * 
   */
  private class TextListener implements KeyListener {

    private JButton theButton;
    private JTextField theTextField;
    private JLabel theLabel;

    public TextListener(JButton theButton, JTextField theTextField, JLabel theLabel) {
      this.theButton = theButton;
      this.theTextField = theTextField;
      this.theLabel = theLabel;
    }

    public void keyPressed(KeyEvent e) {
      if (theTextField.getText().compareTo("") == 0) {
        theButton.setEnabled(false);
      } else if (theTextField.getText().length() >= 25) {
        theTextField.setText("");
        theLabel.setText("Human Name To Long (Max 25 Characters)");
      } else
        theButton.setEnabled(true);
    }

    public void keyReleased(KeyEvent e) {
      if (theTextField.getText().compareTo("") == 0)
        theButton.setEnabled(false);
      else if (theTextField.getText().length() >= 25) {
        theTextField.setText("");
        theLabel.setText("Human Name To Long (Max 25 Characters)");
      } else
        theButton.setEnabled(true);
    }

    public void keyTyped(KeyEvent e) {
      if (theTextField.getText().compareTo("") == 0)
        theButton.setEnabled(false);
      else if (theTextField.getText().length() >= 25) {
        theTextField.setText("");
        theLabel.setText("Human Name To Long (Max 25 Characters)");
      } else
        theButton.setEnabled(true);
    }

  }

}
