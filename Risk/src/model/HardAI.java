package model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 * @author Super Kewl Team
 * 
 *  Assigned to: Steven, Andrew
 *
 */

public class HardAI extends Player implements AI {
	public HardAI(int number, AI ai) {
		super(number, ai);
		this.setAttackDefault(3);
		this.setDefenseDefault(2);
	}
	public HardAI() {
		super(0, null);
		this.setAttackDefault(3);
		this.setDefenseDefault(2);
	}

	public void reinforce(Player reinforcer) {
		while (reinforcer.getReinforcements() > 0) {
			reinforcer.getTerritories().get(
					reinforcer.getReinforcements()
							% reinforcer.getTerritories().size()).adjustDice(1);
			reinforcer.decrementReinforcements();
		}
	}

	// this method will go through a bunch of cases to determine if it is going
	// to attack, and proceeds to fight
	public void attack(Player attacker) {
		if (!attacker.isPlayerHuman()) {
			battleAI(attacker);
		} else {
			battleHuman(attacker);
		}
	}

	private void battleAI(Player attacker) {
		for (int i = 0; i < attacker.getTerritories().size(); i++) {
			for (int j = 0; j < Board.getBoard().getAdjEnemies(
					attacker.getTerritories().get(i)).size(); j++) {
				if (Board.getBoard().getAdjEnemies(
						attacker.getTerritories().get(i)).get(j).getNumDice() < attacker
						.getTerritories().get(i).getNumDice()) {
					attacker.setAttackDefault(3);
					Attack attack = new Attack(Board.getBoard().getAdjEnemies(
							attacker.getTerritories().get(i)).get(j), attacker
							.getTerritories().get(i));
					while (attacker.getFromTerritory().getNumDice() - 1 > attacker
							.getToTerritory().getNumDice()
							&& attacker.getFromTerritory().getNumDice() - 1 > 1) {
						attack.fightRound();
					}
				}
			}
		}
	}

	private void battleHuman(Player attacker) {
		Territory defending = checkAreaForEnemy(attacker);
		Attack attack = new Attack(defending, attacker.getFromTerritory());
		attack.fightRound();
	}

	private Territory checkAreaForEnemy(Player attacker) {
		for (int i = 0; i < attacker.getTerritories().size(); i++) {
			for (int j = 0; j < Board.getBoard().getAdjEnemies(
					attacker.getTerritories().get(i)).size(); j++) {
				if (attacker.getTerritories().get(i).getNumDice() - 1 > Board
						.getBoard().getAdjEnemies(
								attacker.getTerritories().get(i)).get(j)
						.getNumDice()) {
					attacker.setAttackTerritory(Board.getBoard().getAdjEnemies(
							attacker.getTerritories().get(i)).get(j));
				}
			}
		}
		return attacker.getToTerritory();
	}

	public void fortify(Player fortifier) {
		Territory from = fortifier.getTerritories().get(
				(int) (Math.random() * fortifier.getTerritories().size() + 1));
		Territory to = Board.getBoard().getAllies(from)
				.get(
						(int) (Math.random()
								* Board.getBoard().getAllies(from).size() + 1));
		to.adjustDice(from.getNumDice() - 1);
		from.adjustDice(-(from.getNumDice() - 1));
	}

	// if a player owns a card representing a territory they own, and have 3
	// valid cards, return true
	// in reinforce stage call this and if true call turnin on the cards
	public boolean hasCardAndTerritory(Player player) {
		for (int i = 0; i < player.playersHand().getCurrentDeck().size(); i++) {
			for (int j = 0; j < player.getTerritories().size(); j++) {
				if (player.playersHand().getCurrentDeck().get(i).getID() == player
						.getTerritories().get(j).getUniqueID()) {
					return true;
				}
			}
		}
		return false;
	}

	// This method should check if the provided territory has no adjacent
	// enemies,
	// if so it is safe to take all but one territory from it during the fortify
	// stage, to fortify outer
	// territories
	public boolean isTerritorySafe(Territory territory) {
		if (Board.getBoard().getAdjEnemies(territory.getUniqueID()).size() == 0) {
			return true;
		}
		return false;
	}

	// checks if the territory is a bottle neck, if it is the AI can check if it
	// is safe and determine if it
	// should be fortified/reinforced
	public boolean isBottleNeck(Territory territory) {
		if (territory.getUniqueID() == 1 || territory.getUniqueID() == 12) { // need
			// a
			// list
			// of
			// bottle
			// necks

			return true;
		}
		return false;
	}

	// not sure how to implement this but want to be able to check if i almost
	// have every territory in a
	// continent. Used for going after the last territory to seal it up.
	public Territory almostFullContinent(Player player) {
		for (int i = 0; i < 6; i++) {
			ArrayList<Territory> myContinentTers = new ArrayList<Territory>();
			for (int j = 0; j < Board.getContinent(i).getTerritories().size(); j++) {
				if (!player.getTerritories().contains(
						Board.getContinent(i).getTerritories().get(j))) {
					myContinentTers.add(Board.getContinent(i).getTerritories()
							.get(j));
				}
			}

			if (myContinentTers.size() == 1) {
				return myContinentTers.get(0);
			}
		}
		return null;
	}

	// this is to be used if the ai finds that it owns all of the territories
	// except for 1 in a continent
	// this method takes in the ENEMY territory that it needs to attack to own
	// the entire continent
	// it should figure out which territory it wants to use to attack that
	// territory
	public void attackLastTerritory(Territory territory) {
		Iterator<Territory> iter = Board.getBoard().getAdjEnemies(
				territory.getUniqueID()).iterator();
		Territory largest = iter.next();
		while (iter.hasNext()) {
			Territory terrtemp = iter.next();
			if (terrtemp.getNumDice() > largest.getNumDice()) {
				largest = terrtemp;
			}
		}
		// do an attack from largest to territory
	}

	// check if an enemy has a continent, used to go after it to break it up.
	public Player enemyHasAContinent() {
		for (int i = 0; i < Board.getBoard().getPlayers().size(); i++) {
			if (Board.getBoard().getPlayers().get(i).fullContinent() == 0) {
				return Board.getBoard().getPlayers().get(i);
			}
		}
		return null;
	}

	// This class decides the attack(s) for the round
	public void think(Player player) {
		int index = 0;
		while (index < player.getTerritories().size()) {
			player.getTerritories().get(index);
		}
	}
	
	public void calcDefaultDice(Territory t) {
		//doesn't do stuff
	}
	public void calcDefaultDice(Territory defense, Territory attack) {
		if(attack.getOwner().isPlayerHuman()&& Board.board.getPlayerIndex(attack.getOwner())!=Board.board.getPlayerIndex(defense.getOwner())){
			if(defense.getNumDice()>=attack.getNumDice()){
				defense.getOwner().setDefenseDefault(2);
			}
			else{
				defense.getOwner().setDefenseDefault(1);
			}
		}
		else{
			if(attack.getNumDice()>=defense.getNumDice()){
				attack.getOwner().setDefenseDefault(2);
			}
			else{
				attack.getOwner().setDefenseDefault(1);
			}
		}
	}
}
