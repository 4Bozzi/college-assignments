package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import model.Board;
import model.Player;
import model.Territory;
/**
 *The MapPanel class is the panel that displays the map with the planets and all of their 
 *corresponding buffs, auras, etc.
 *
 * @author Super Kewl Team
 * 
 * Assigned to: Paul
 *
 */
public class MapPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	JLayeredPane layeredPane = new JLayeredPane();
	private ArrayList<Buff> BuffList = new ArrayList<Buff>();
	private ArrayList<Aura> AuraList = new ArrayList<Aura>();
	private ArrayList<ArmyCount> ArmyCountList = new ArrayList<ArmyCount>();
	public static MapPanel mapPanel;
	//During an attack or fortify, this is what holds the source territory
	private Territory sourceTerritory;
	private Territory attackingTerritory;
	
	/**
	 * This is the constructor for a Map Panel that just basically sets up the map panel with
	 * the correct planets in the right places with the number of armies and the auras and buffs.
	 */
	public MapPanel() {
		mapPanel = this;
		this.setLayout(null);
	    this.setBackground(Color.DARK_GRAY);
		layeredPane.setSize(1000, 550);
		Iterator<Player> playerIterator = Board.getBoard().getPlayers()
				.iterator();

		while (playerIterator.hasNext()) {

			Player player = (Player) playerIterator.next();
			int playerIndex = Board.getBoard().getPlayerIndex(player);
			Iterator<Territory> territoryIterator = player.getTerritories()
					.iterator();

			while (territoryIterator.hasNext()) {
				Territory territory = territoryIterator.next();
				int id = territory.getUniqueID();

				// setup buffs
				Buff tempBuff = new Buff(id, this);
				layeredPane.add(tempBuff, 0);
				BuffList.add(tempBuff);
				
				//set up armycounts
				ArmyCount tempArmyCount = new ArmyCount(id);
				layeredPane.add(tempArmyCount, 1);
				ArmyCountList.add(tempArmyCount);
					
				// Setup Auras and add to list
				Aura tempAura = new Aura(id, playerIndex);
				layeredPane.add(tempAura, 2);
				AuraList.add(tempAura);

				// set up planets
				layeredPane.add(new Planet(id), 3);
			}
		}

		this.add(layeredPane);

	}

	/**
	 * This is overriding the paintComponent method for the JPanel. This allows
	 * the background image to be seen.
	 */
	public void paintComponent(Graphics g) {
		Image img = new ImageIcon("images/background.png").getImage();
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		setLayout(null);
		g.drawImage(img, 0, 0, null);
	}
	/**
	 * This method calls all the other update methods in this class to update everything.
	 */
	public void update(Observable arg0, Object arg1) {
	    if(arg1 instanceof Integer){
		clearBuffs();
		}
		updateBuffs();
		updateAuras();
		updateArmyCount();
		// Currently this makes the MapPanel Blink.
		this.paintAll(this.getGraphics());
	}

	public Buff getBuff(int uniqueID) {
		Iterator<Buff> iter = BuffList.iterator();
		while (iter.hasNext()) {
			Buff tempBuff = iter.next();
			if (tempBuff.getID() == uniqueID)
				return tempBuff;
		}
		return null;

	}
	
	/**
	 * This method calls the update buffs and updates the entire list of buffs.
	 */
	public void updateBuffs() {
		Iterator<Buff> iter = BuffList.iterator();
		while (iter.hasNext()) {
			Buff tempBuff = iter.next();
			tempBuff.updateBuff();
		}
	}
	
	/**
	 * Clear buffs clears the entire list of buffs.
	 */
	public void clearBuffs() {
		Iterator<Buff> iter = BuffList.iterator();
		while (iter.hasNext()) {
			Buff tempBuff = iter.next();
			tempBuff.clearBuff();
		}
	}
	
	/**
	 * This method updates the list that has all of the army counts
	 */
	public void updateArmyCount() {
		Iterator<ArmyCount> iter = ArmyCountList.iterator();
		while (iter.hasNext()) {
			ArmyCount tempArmyCount = iter.next();
			tempArmyCount.updateArmies();
		}
		//System.out.print("update army count");
	}
	
	/**
	 * This method updates the list of aura images.
	 */
	public void updateAuras() {
		Iterator<Aura> iter = AuraList.iterator();
		while (iter.hasNext()) {
			Aura tempAura = iter.next();
			tempAura.updateAura();
		}
		//System.out.print("update auras");
	}
	
	/**
	 * Getters and setters.
	 * 
	 */
	public MapPanel getMapPanel() {
		return this.mapPanel;
	}
	public void setSourceTerritory(Territory source) {
		sourceTerritory = source;
	}
	public Territory getSourceTerritory() {
		return sourceTerritory;
	}
	
	public void setAttackingTerritory(Territory attacking) {
		attackingTerritory = attacking;
	}
	
	public Territory getAttackingTerritory() {
		return attackingTerritory;
	}
}
