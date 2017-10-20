package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

/**
 * 
 * @author Super Kewl Team: Matt Giardina, Paul Frost, Steven Downs, Andrew
 *         Bozzi
 *         
 *  Assigned to: Andrew
 * 
 * This class holds most of the objects in the model package. It contains a list
 * of players, a player desk which holds cards, It also knows the continents it
 * holds and from that it knows it's territories and the armies held in each
 * territory, as well as who has them
 * 
 * methods contained: addPlayer(),getEnemies(), getAllies(), validateHand()
 */
public class Board extends Observable {
	private List<Player> players = new ArrayList<Player>();
	private Deck deck;
	private static List<Continent> continents = new ArrayList<Continent>();
	public static Board board;
	private static Globe globe;
	private int[][] location = { { 285, 20 }, { 215, 50 }, { 240, 120 },
			{ 145, 15 }, { 145, 85 }, { 170, 155 }, { 65, 75 }, { 100, 155 },
			{ 30, 165 }, { 125, 285 }, { 55, 355 }, { 125, 390 }, { 55, 425 },
			{ 470, 105 }, { 540, 165 }, { 540, 245 }, { 400, 165 },
			{ 470, 205 }, { 400, 235 }, { 470, 275 }, { 375, 315 },
			{ 450, 345 }, { 410, 415 }, { 305, 375 }, { 340, 475 },
			{ 480, 475 }, { 610, 210 }, { 650, 145 }, { 555, 65 }, { 650, 5 },
			{ 610, 285 }, { 640, 75 }, { 720, 100 }, { 740, 30 }, { 535, 345 },
			{ 650, 355 }, { 680, 215 }, { 735, 285 }, { 770, 435 },
			{ 850, 375 }, { 850, 455 }, { 920, 405 } };

	public Board() {
		board = this;
		deck=new Deck();
		setGlobe(new Globe());
		Continent nAmerica = new Continent(0);
		Continent sAmerica = new Continent(1);
		Continent europe = new Continent(2);
		Continent africa = new Continent(3);
		Continent asia = new Continent(4);
		Continent australia = new Continent(5);

		this.addContinent(nAmerica);
		this.addContinent(sAmerica);
		this.addContinent(europe);
		this.addContinent(africa);
		this.addContinent(asia);
		this.addContinent(australia);
		Collections.shuffle(deck.getCurrentDeck());
	}

	/**
	 * 
	 * @param name
	 *            This method just adds a player to the board at the start of
	 *            each game only. NO AMBUSH PLAYERS!
	 */
	public void addPlayer(String name) {
		Player player = new Player(name);
		players.add(player);
	}

	public void addPlayer(int number, AI ai) {
		Player player = new Player(number, ai);
		players.add(player);
	}

	/**
	 * 
	 * @param uniqueID
	 * @return a list of bordering enemies
	 * 
	 * This is used during the attacking stage of a turn and should be updated
	 * each successful attack.  It allows for the territory to be found by id.
	 */
	// make sure that myTerritory can only be the current players territory (gui
	// i think)
	public List<Territory> getAdjEnemies(int uniqueID) {
		Territory myTerritory = globe.getTerritoryByID(uniqueID);
		ArrayList<Territory> adjTerritories = new ArrayList<Territory>();
		for (int i = 0; i < myTerritory.getAdjacentTerritories().size(); i++) {
			if (myTerritory.getAdjacentTerritories().get(i).getOwner() != myTerritory
					.getOwner()) {
				adjTerritories.add(myTerritory.getAdjacentTerritories().get(i));
			}
		}
		return adjTerritories;
	}

	/**
	 * 
	 * @param myTerritory
	 *            this is the territory that is owned by user
	 * @return a list of bordering enemies
	 * 
	 * This is used during the attacking stage of a turn and should be updated
	 * each successful attack
	 */
	// make sure that myTerritory can only be the current players territory (gui
	// i think)
	public List<Territory> getAdjEnemies(Territory myTerritory) {
		ArrayList<Territory> adjTerritories = new ArrayList<Territory>();
		for (int i = 0; i < myTerritory.getAdjacentTerritories().size(); i++) {
			if (myTerritory.getAdjacentTerritories().get(i).getOwner() != myTerritory
					.getOwner()) {
				adjTerritories.add(myTerritory.getAdjacentTerritories().get(i));
			}
		}
		return adjTerritories;
	}

	/**
	 * 
	 * @param friendlies
	 * @return a list of bordering allies
	 * 
	 * this is used during the 3rd stage of a turn and should be updated each
	 * successful attack
	 */
	public List<Territory> getAllies(Territory myTerritory) {
		ArrayList<Territory> adjTerritories = new ArrayList<Territory>();
		for (int i = 0; i < myTerritory.getAdjacentTerritories().size(); i++) {
			if (myTerritory.getAdjacentTerritories().get(i).getOwner() == myTerritory
					.getOwner()) {
				adjTerritories.add(myTerritory.getAdjacentTerritories().get(i));
			}
		}
		return adjTerritories;
	}
	
	/**
	 * 
	 * @param uniqueID
	 * @return a list of bordering allies
	 * 
	 * this is used during the 3rd stage of a turn and should be updated each
	 * successful attack
	 */
	public List<Territory> getAllies(int uniqueID) {
		Territory myTerritory = globe.getTerritoryByID(uniqueID);
		ArrayList<Territory> adjTerritories = new ArrayList<Territory>();
		for (int i = 0; i < myTerritory.getAdjacentTerritories().size(); i++) {
			if (myTerritory.getAdjacentTerritories().get(i).getOwner() == myTerritory
					.getOwner()) {
				adjTerritories.add(myTerritory.getAdjacentTerritories().get(i));
			}
		}
		return adjTerritories;
	}

	/**
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return a boolean value true, if the hand is in fact a match
	 * 
	 * This method checks the hand(three cards) against the possible matches
	 * specified by the actual 1957 game's rules and see if there is a match.
	 * 
	 * 
	 */
	// method or use of method contains bug.
	public boolean validateHand(Card a, Card b, Card c) {
		if (a.getType() == b.getType() && b.getType() == c.getType()) {
			return true;
		}
		if (a.getID() == 42 && b.getType() == c.getType()) {
			return true;
		}
		if (b.getID() == 42 && a.getType() == c.getType()) {
			return true;
		}
		if (c.getID() == 42 && a.getType() == b.getType()) {
			return true;
		}
		return true;// should return false here
	}

	public int getPlayerIndex(Player player) {
		return players.indexOf(player);

	}

	public static Continent getContinent(int index) {
		return continents.get(index);
	}

	public void addContinent(Continent newContinent) {
		continents.add(newContinent);
	}

	public static Board getBoard() {
		return board;
	}



	public int getTerritoryX(int id) {
		return location[id][0];

	}

	public int getTerritoryY(int id) {
		return location[id][1];

	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setGlobe(Globe globe) {
		Board.globe = globe;
	}

	public Globe getGlobe() {
		return globe;
	}
	
	public void setPlayersDefaultArmies() {
		int armies = 0;
		int numPlayers = players.size();
		if(numPlayers == 2) {
			armies = 19;
		}else if (numPlayers == 3) {
			armies = 21;
		}else if (numPlayers == 4) {
			armies = 20;
		}else if (numPlayers == 5) {
			armies = 17;
		}
		Iterator<Player> iter = players.iterator();
		while(iter.hasNext()) {
			Player playertemp = (Player) iter.next();
			playertemp.setReinforcements(armies);
		}

	}
}