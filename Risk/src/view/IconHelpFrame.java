package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

/**
 * This class is used to tell the user what each of the icons mean on the game
 * board. There are a lot of icons in each of the different stages on the board
 * at the same time so it can be confusing to the user.
 * 
 * @author Matt Giardina
 * 
 */
public class IconHelpFrame extends JFrame {

	JPanel thePanel = new JPanel();
  /**
   * Constructor
   */
  public IconHelpFrame() {
    // Initial Setup of this Panel
    this.setVisible(true);
    this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    this.add(thePanel);
    thePanel.setBackground(Color.DARK_GRAY);
    thePanel.setLayout(new GridLayout(6, 2));

    // Set up the Worker Bee
    JLabel theWorker = new JLabel();

    // ** START: Player Ownership Description ** //
    theWorker.setIcon(new ImageIcon("images/redOwnership.png"));
    theWorker.setText("A planet that is owned by a human player.");
    thePanel.add(theWorker);
    theWorker.setBackground(Color.DARK_GRAY);
    theWorker.setForeground(Color.LIGHT_GRAY);


    theWorker = new JLabel();
    theWorker.setIcon(new ImageIcon("images/blueOwnership.png"));
    theWorker.setText("A planet that is owned by the 1st computer player.");
    thePanel.add(theWorker);
    theWorker.setBackground(Color.DARK_GRAY);
    theWorker.setForeground(Color.LIGHT_GRAY);


    theWorker = new JLabel();
    theWorker.setIcon(new ImageIcon("images/yellowOwnership.png"));
    theWorker.setText("A planet that is owned by the 2nd computer player.");
    thePanel.add(theWorker);
    theWorker.setBackground(Color.DARK_GRAY);
    theWorker.setForeground(Color.LIGHT_GRAY);


    theWorker = new JLabel();
    theWorker.setIcon(new ImageIcon("images/orangeOwnership.png"));
    theWorker.setText("A planet that is owned by the 3rd computer player.");
    thePanel.add(theWorker);
    theWorker.setBackground(Color.DARK_GRAY);
    theWorker.setForeground(Color.LIGHT_GRAY);


    theWorker = new JLabel();
    theWorker.setIcon(new ImageIcon("images/violetOwnership.png"));
    theWorker.setText("A planet that is owned by the 4th computer player.");
    thePanel.add(theWorker);
    theWorker.setBackground(Color.DARK_GRAY);
    theWorker.setForeground(Color.LIGHT_GRAY);

    // ** END: Player Ownership Description ** //

    // ** START: Highlights and Selected Planets ** //
    theWorker = new JLabel();
    theWorker.setIcon(new ImageIcon("images/selectableBuff.png"));
    theWorker.setText("A planet that can be used in the give stage.");
    thePanel.add(theWorker);
    theWorker.setBackground(Color.DARK_GRAY);
    theWorker.setForeground(Color.LIGHT_GRAY);


    theWorker = new JLabel();
    theWorker.setIcon(new ImageIcon("images/selectBuff.png"));
    theWorker.setText("A planet that has been selected for this given stage.");
    thePanel.add(theWorker);
    theWorker.setBackground(Color.DARK_GRAY);
    theWorker.setForeground(Color.LIGHT_GRAY);

    // ** END: Highlights and Selected Planets ** //

    
    // ** START: Different Stage Bufffered Images ** //
    theWorker = new JLabel();
    theWorker.setIcon(new ImageIcon("images/reinforceBuff.png"));
    theWorker.setText("Planets that you can reinforce in the Reinforce Stage.");
    thePanel.add(theWorker);
    theWorker.setBackground(Color.DARK_GRAY);
    theWorker.setForeground(Color.LIGHT_GRAY);


    theWorker = new JLabel();
    theWorker.setIcon(new ImageIcon("images/targetBuff.png"));
    theWorker.setText("Neighboring planets that you may be able to attack.");
    thePanel.add(theWorker);
    theWorker.setBackground(Color.DARK_GRAY);
    theWorker.setForeground(Color.LIGHT_GRAY);

    theWorker = new JLabel();
    theWorker.setIcon(new ImageIcon("images/attacked.gif"));
    theWorker.setText("The current planet getting attacked.");
    thePanel.add(theWorker);
    theWorker.setBackground(Color.DARK_GRAY);
    theWorker.setForeground(Color.LIGHT_GRAY);


    theWorker = new JLabel();
    theWorker.setIcon(new ImageIcon("images/fortifyBuff.png"));
    theWorker.setText("Neighboring Territories that you can Fortify.");
    thePanel.add(theWorker);
    theWorker.setBackground(Color.DARK_GRAY);
    theWorker.setForeground(Color.LIGHT_GRAY);

    // ** END: Different Stage Buffered Images ** //

    theWorker = new JLabel("Super Kewl Space Risk");
    theWorker.setFont(new Font("Arial", Font.BOLD, 20));
    theWorker.setBorder(new MatteBorder(1,1,1,1, Color.LIGHT_GRAY));
    theWorker.setBackground(Color.DARK_GRAY);
    theWorker.setForeground(Color.LIGHT_GRAY);
    theWorker.setHorizontalAlignment(SwingConstants.CENTER);
    theWorker.setHorizontalTextPosition(SwingConstants.CENTER);
    thePanel.add(theWorker);

    thePanel.repaint();
    this.repaint();
    
    this.pack(); // Make it look nice
  }
}
