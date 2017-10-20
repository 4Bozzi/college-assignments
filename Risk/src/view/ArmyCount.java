package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

import model.Board;
import model.Territory;
	/**
	 * The armycount class keeps track of the number of armies on a planet.
	 * 
	 * @author Super Kewl Team
	 * 
	 * Assigned to: Paul
	 *
	 */
public class ArmyCount extends JLabel{
	private static final long serialVersionUID = 1L;
	//stores territory that this army represents
	private Territory territory;
	//current armies, this should get updated
	private int armies;
	@SuppressWarnings("unused")
    private int uniqueID;
	
	/**
	 * This is the constructor that creates a number to display on the planet so you can see how
	 * many armies the planet has.
	 * 
	 * @param id the territory unique ID number.
	 */
	public ArmyCount(int id) {
		this.setBounds(Board.getBoard().getTerritoryX(id)+30, Board.getBoard()
				.getTerritoryY(id)+20, 30, 30);
		uniqueID = id;
		territory = Board.getBoard().getGlobe().getTerritoryByID(id);
		armies = territory.getNumDice();
		this.setText(""+armies);
		this.setFont(new Font("Arial", Font.BOLD, 18));
		this.setForeground(Color.black);
		
	}
	/**
	 * This method updates the number of armies being displayed.
	 */
	public void updateArmies() {
		this.setText(""+ territory.getNumDice());
	}

}
