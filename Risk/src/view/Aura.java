package view;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import model.Board;

/**
 * 
 * @author Super Kewl Team
 * 
 * Assigned to: Paul
 *
 *The aura is the icon that explains who owns which territory. By default the human player is a red
 *rectangle.Each player has a different color and symbol to let them know which territories are 
 *theirs, although the AI's don't really use this for anything it is mostly for the Human player to
 *tell the other players apart.
 */
public class Aura extends JLabel {
	private int uniqueID;
	/**
	 * 
	 */
	private static final long serialVersionUID = 6248420906942225336L;
	
	/**
	 * This is the constructor for an aura. Sets the right icon for all the territories.
	 * 
	 * @param id = territory unique id
	 * @param playerIndex = the order which the player was added
	 */
	public Aura(int id, int playerIndex) {
		String filePath = "";
		uniqueID = id;
		if (playerIndex == 0)
			filePath = "images/redOwnership.png";
		if (playerIndex == 1)
			filePath = "images/blueOwnership.png";
		if (playerIndex == 2)
			filePath = "images/yellowOwnership.png";
		if (playerIndex == 3)
			filePath = "images/orangeOwnership.png";
		if (playerIndex == 4)
			filePath = "images/violetOwnership.png";
		ImageIcon icon = new ImageIcon(filePath);
		this.setIcon(icon);
		this.setBounds(Board.getBoard().getTerritoryX(id), Board.getBoard()
				.getTerritoryY(id), 70, 70);
	}

	/**
	 * When a player takes an enemies planet it this method will update the icon to the correct one.
	 */
	public void updateAura() {
		int playerIndex = Board.getBoard().getPlayerIndex(Board.getBoard().getGlobe().getTerritoryByID(uniqueID).getOwner());
		String filePath = "";

		if(playerIndex == 0)
			filePath = "images/redOwnership.png";
		else if(playerIndex == 1)
			filePath = "images/blueOwnership.png";
		else if(playerIndex == 2)
			filePath = "images/yellowOwnership.png";
		else if(playerIndex == 3)
			filePath = "images/orangeOwnership.png";
		else if(playerIndex == 4)
			filePath = "images/violetOwnership.png";
		ImageIcon icon = new ImageIcon(filePath);
		this.setIcon(icon);
	}
}
