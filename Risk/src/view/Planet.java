package view;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import model.Board;
/**
 * The Planet class is used to display the image of the correct planet based on the territory id 
 * number
 * 
 * @author Super Kewl Team
 * 
 * Assigned to: Paul
 *
 */
public class Planet extends JLabel {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private int uniqueID;
	
	/**
	 * This is the Planet constructor, takes in a territory id and calculates the correct image
	 * to display at what location.
	 * 
	 * @param id - The territories unique id number
	 */
	public Planet(int id) {
		
		String filepath = "images/planets/planet"+ (id+1) +".png";
		ImageIcon icon = new ImageIcon(filepath);
		this.setIcon(icon);
		this.setBounds(Board.getBoard().getTerritoryX(id), Board.getBoard()
				.getTerritoryY(id), 70, 70);
		uniqueID = id;
		

	}
}
