package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author super Kewl Team
 * 
 *  Assigned to: Steven
 *  
 *         This class is called by the GUI in order to access the actions needed
 *         to take and complete the stage attack.---- If you are to attack
 *         another territory, ie switch the territory you are attacking or the
 *         territory you are attacking from you will need to create a new
 *         Attack.
 * 
 *         methods contained: Attack(Territory, Territory) setDefendDice(int)
 *         setAttackDice(int) setDefendTerritory(Territory)
 *         setAttackTerritory(Territory) fightRound()
 *         setTerritoryGained(boolean) fight(List<Integer>, List<Integer>)
 *         roll(int) adjAttackDefault(int) adjDefenseDefault(int) playerGONE()
 *         getAttackingTerritory() getDefendingTerritory()
 *         continueBattle(Boolean) resetDefendersLost() resetAttackersLost()
 */
public class Attack {
	public static boolean ATTACK;
	private int defendDice;
	private int attackDice;
	private Territory attacking;
	private Territory defending;
	private int defendersLost;
	private int attackersLost;
	private boolean winSkirmish;

	/**
	 * 
	 * @param attacking
	 * @param defending
	 * 
	 *            This is the constructor for the for the attack class and takes
	 *            two territories as args one is the attacking territory the
	 *            other is the defending territory. This constructor just
	 *            initializes the instance variable for the class.
	 */
	public Attack(Territory attacking, Territory defending) {
		this.attacking = attacking;
		this.defending = defending;
		defendersLost = 0;
		attackersLost = 0;
		winSkirmish = false;
		defendDice = 1;
		attackDice = 1;
	}

	/**
	 * 
	 * @param int x
	 * 
	 *        This method just sets the default number of dice you wish to roll
	 *        when defending
	 */
	public void setDefendDice(int x) {
		defending.getOwner().setDefenseDefault(x);
		defendDice = defending.getOwner().getDefenseDefault();
	}

	/**
	 * 
	 * @param int x
	 * 
	 *        This method just sets the default number of dice you wish to roll
	 *        when attacking
	 */
	public void setAttackDice(int x) {
		attacking.getOwner().setAttackDefault(x);
		while (attacking.getNumDice() < attackDice + 1) {
			adjAttackDefault(-1);
		}
		attackDice = attacking.getOwner().getAttackDefault();
	}

	/**
	 * 
	 * @param t
	 * 
	 *            This method just sets the territory you wish to attack
	 */
	public void setDefendTerritory(Territory t) {
		attacking.getOwner().setToTerritory(t);
		defending = attacking.getOwner().getToTerritory();
	}

	/**
	 * 
	 * @param t
	 * 
	 *            This method just sets the territory you wish to attack from
	 */
	public void setAttackTerritory(Territory t) {
		attacking.getOwner().setAttackTerritory(t);
		attacking = attacking.getOwner().getFromTerritory();
	}

	/**
	 * This method is the heavy hitter in this class. This method keeps track of
	 * what goes on in one attack which is a roll, determining the armies lost,
	 * and determining if someone one or lost
	 */
	public void fightRound() {
		if (defending.getNumDice() > 0
				&& defending.getOwner() != attacking.getOwner()) {
			System.out.println("attacker is rolling " + attackDice + " dice.");
			List<Integer> attackerdie = new ArrayList<Integer>(attackDice);
			attackerdie = roll(attackerdie.size());
			Collections.sort(attackerdie);

			System.out.println("defender is rolling " + defendDice + " dice.");
			List<Integer> defenderdie = new ArrayList<Integer>(defendDice);
			defenderdie = roll(defenderdie.size());
			Collections.sort(defenderdie);
			setTerritoryGained(fight(attackerdie, defenderdie));
		}
	}

	/**
	 * 
	 * @param fight
	 * 
	 *            This method sets the winSkirmish if a territory has been won
	 */
	void setTerritoryGained(boolean fight) {
		winSkirmish = fight;
	}
	/**
	 * 
	 * @param attackerdie
	 * @param defenderdie
	 * @return boolean
	 * 
	 *         This method takes care of the armies lost and determining a
	 *         winner of the skirmish
	 */
	public boolean fight(List<Integer> attackerdie, List<Integer> defenderdie) {
		int counter = 0;
		while (counter < Math.min(attackDice, defendDice)) {
			System.out.println("Counter is " + counter);
			System.out.println("defender rolled a " + defenderdie.get(counter));
			System.out.println("attacker rolled a " + attackerdie.get(counter));
			if (attackerdie.get(counter) > defenderdie.get(counter)) {
				defendersLost--;
			} else {
				attackersLost--;
			}
			counter++;
		}
		defending.adjustDice(defendersLost);
		attacking.adjustDice(attackersLost);
		System.out.println("Attacking Army: " + attacking.getNumDice());
		System.out.println("Defending Army: " + defending.getNumDice());
		if (defending.getNumDice() == 0) {
			attacking.getOwner().addTerritories(defending);
			defending.getOwner().getTerritories().remove(defending);
			defending.setOwner(attacking.getOwner());
			Fortify fortify = new Fortify(attacking, defending);
			fortify.moveForceArmies();
			Game.game.update(this);
			resetDefendersLost();
			resetAttackersLost();
			return true;
		} else {
			resetDefendersLost();
			resetAttackersLost();
			return false;
		}
	}

	/**
	 * 
	 * @param size
	 * @return List<Integer>
	 * 
	 *         This method just rolls the dice in the array.
	 */
	List<Integer> roll(int size) {
		List<Integer> dieRoll = new ArrayList<Integer>(attackDice);
		for (int i = 0; i < attackDice; i++) {
			dieRoll.add((int) (Math.random() * 6) + 1);
		}
		return dieRoll;
	}

	/**
	 * 
	 * @param adj
	 * 
	 *            This is an adjust Attacking dice method inside the class
	 */
	private void adjAttackDefault(int adj) {
		attackDice += adj;
	}

	/**
	 * 
	 * @param adj
	 * 
	 *            This is an adjust defending dice method inside the class
	 */
	private void adjDefenseDefault(int adj) {
		defendDice += adj;
	}

	/**
	 * 
	 * @return boolean
	 * 
	 *         This class determines if a player has been wiped-off the board
	 *         completely
	 */
	public boolean playerGONE() {
		return defending.getOwner().getTerritories().size() == 0;
	}

	/**
	 * 
	 * @return Territory
	 * 
	 *         just a getter that returns the attacking territory
	 */
	public Territory getAttackingTerritory() {
		return attacking;
	}

	/**
	 * 
	 * @return Territory
	 * 
	 *         just a getter that returns the defending territory
	 */
	public Territory getDefendingTerritory() {
		return defending;
	}

	/**
	 * sets the defenders lost to zero
	 */
	private void resetDefendersLost() {
		defendersLost = 0;
	}

	/**
	 * sets the attackers lost to zero
	 */
	private void resetAttackersLost() {
		attackersLost = 0;
	}

	/**
	 * 
	 * @param player
	 * @return boolean
	 * 
	 *         This is a helper method for valid attack. It makes sure that the
	 *         rolls are valid
	 */
	private boolean validAttackDiceDefault(Player player) {
		if (attacking.getNumDice() > 1) {
			return attackDice < 4 && attackDice >= 1;
		}
		return false;
	}

	/**
	 * 
	 * @param player
	 * @return boolean
	 * 
	 *         This is a helper method for valid attack. It makes sure that the
	 *         rolls are valid
	 */
	private boolean validDefendDiceDefault(Player player) {
		return defendDice < 3 && defendDice >= 1;
	}

	/**
	 * 
	 * @return boolean
	 * 
	 *         This method is used by the GUI and checks if the attack about to
	 *         be made is valid. It utilizes two helper methods.
	 */
	public boolean validAttack() {
		boolean value = false;
		if (!winSkirmish && (attacking.getOwner() != defending.getOwner())
				&& Board.board.getAdjEnemies(attacking).contains(defending)
				&& validDefendDiceDefault(defending.getOwner())
				&& validAttackDiceDefault(attacking.getOwner())
				&& defending.getNumDice() > 0) {
			value = true;

		}
		System.out.println("Is Valid? " + value);
		return value;
	}

	public boolean wasTerritoryGained() {
		return winSkirmish;
	}
}
