package view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

/**
 * The main container that holds several panels which make up the risk Game.
 * 
 * @author Matt Giardina
 */
public class RiskFrame extends JFrame {

  private static final long serialVersionUID = 1L;
  private MapPanel map; // The Map Panel
  private PlayerPanel playerPanel; // The Player Panel
  private MenuBar menu; // And of Course the Menu

  /**
   * The Constructor for the RiskFrame
   */
  public RiskFrame() {
    // Setup this Frame
    this.setTitle("CSC335: Super Kewl Space Risk (Giardina, Frost, Downs, Bozzi)");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    this.setSize(1000, 753);
    this.setLocationRelativeTo(null);
    this.setResizable(false);
    this.setBackground(Color.DARK_GRAY);

    // Create Panels to Add
    map = new MapPanel();
    menu = new MenuBar();
    playerPanel = new PlayerPanel();

    // Add Panels and Menus!
    this.add(map, BorderLayout.CENTER);
    this.add(menu, BorderLayout.NORTH);
    this.add(playerPanel, BorderLayout.SOUTH);
    this.setBackground(Color.DARK_GRAY);

    // Don't forget me!
    this.setVisible(true);
  }

  /**
   * Get the Map Panel
   * 
   * @return - An instance of the Map Panel in this Frame
   */
  public MapPanel getMapPanel() {
    return map;
  }

  /**
   * Get the Player Panel
   * 
   * @return - An instance of the Player Panel in this Frame
   */
  public PlayerPanel getPlayerPanel() {
    return playerPanel;
  }
}