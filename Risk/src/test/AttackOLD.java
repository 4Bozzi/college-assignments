/*package test;

import java.util.Scanner;

import model.Player;
import model.Reinforce;
import model.Territory;

*//**
 * 
 * @author Super Kewl Team
 * 
 *         This class is called inside Player Class's method call attack which
 *         took no argument. Atttack is in charge of calling Battle and if a
 *         battle can be continued.
 * 
 *         methods contained: continueBattle(Boolean) getAttacker()
 *         setAttackerWin(boolean) getAttackerWin() setTerritoryGained(boolean)
 *         getTerritoryGained(boolean) getAttackDefault() getDefenseDefault()
 *         adjAttackDefault(int) adjDefenseDefault(int) getAttackingArmy()
 *         getDefendingArmy() setAttackingArmy(int) setDefendingArmy(int)
 * 
 *//*
public class AttackOLD {

	private Player attackingPlayer;
	private Player defendingPlayer;
	private Territory defending_Territory;
	private Territory attacking_Territory;
	private int attackDefault;
	private int defenseDefault;
	private boolean attackerWon;
	private boolean territoryGained;
	private Battle battle;

	*//**
	 * 
	 * @param p
	 *            is a player This constructor sets up the fields to be used in
	 *            both attack and Battle. Battle will access these fields
	 *            through getters and setters.
	 * 
	 *//*
	public Attack(Territory from, Territory to) {
		attackingPlayer = from.getOwner();
		defendingPlayer = to.getOwner();
		defending_Territory = to;
		attacking_Territory = from;
		attackDefault = from.getOwner().getAttackDefault();
		defenseDefault = to.getOwner().getDefenseDefault();
		if (attacking_Territory.getOwner() != defending_Territory.getOwner()
				&& attacking_Territory.getNumDice() >= 2) {
			battle = new Battle(this);
			System.out.println("Territory " + territoryGained);
		} else {
			System.out.println("Insuffiencent Army mass");
		}
	}

	*//**
	 * 
	 * @param value
	 * 
	 *            This method determines if the player wants to continue to
	 *            fight by saying true and a new Battle starts, if the player
	 *            wishes to stop his attack then the stats are given.
	 *//*
	public void continueBattle(Boolean value) {
		boolean attackerLost = false;
		if (attackerLost || attackerWon) {
			System.out.println("I can't let you do that Star Fox!");
		} else if (1 == attacking_Territory.getNumDice()) {
			// Territory
			territoryGained = false;
			attackerLost = true;
			System.out.println("attacker Loses!!!!!!!!!!!");
		} else if (value) {
			battle.resetAttackersLost();
			battle.resetDefendersLost();
			battle.battle();
			System.out.println("NEW ATTACK!!!!!!!!!!!");
		} else {
			System.out.println("attacker Retreats!!!!!!!!!!!");
		}
	}

	public void winnings() {
			System.out.println("attacker Wins!!!!!!!!!!!");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("Should be getting Stuff!!!!!!!!!!");
			defendingPlayer.getTerritories().remove(defending_Territory);
			attackingPlayer.addTerritories(defending_Territory);
			theCardMess();
			defending_Territory.setOwner(attackingPlayer);
			System.out.println("Attacker Gets a Card!!!!!!!!!");
			attackingPlayer.draw();
			System.out.println("");
			System.out.println("");
			System.out.println("");
			attackerWon = true;
	}

	public Territory getAttackingTerritory() {
	  return attacking_Territory;
	}
	
	public Territory getDefendingTerritory() {
	  return defending_Territory;
	}
	
	public Player getAttacker() {
		return attackingPlayer;
	}

	public void setAttackerWin(boolean value) {
		attackerWon = value;
	}

	public boolean getAttackerWin() {
		return attackerWon;
	}

	public void setTerritoryGained(boolean b) {
		territoryGained = b;
	}

	public boolean getTerritoryGained() {
		return territoryGained;
	}

	public int getAttackDefault() {
		return attackDefault;
	}

	public int getDefenseDefault() {
		return defenseDefault;
	}

	public void adjAttackDefault(int adj) {
		attackDefault += adj;
	}

	public void adjDefenseDefault(int adj) {
		defenseDefault += adj;
	}

	public int getAttackingArmy() {
		return attacking_Territory.getNumDice();
	}

	public int getDefendingArmy() {
		return defending_Territory.getNumDice();
	}

	public void setAttackingArmy(int troops) {
		attacking_Territory.adjustDice(troops);
	}

	public void setDefendingArmy(int troops) {
		defending_Territory.adjustDice(troops);
	}

	public void setDefenseDefault(int i) {
		defenseDefault = i;
	}

	public void setAttackDefault(int i) {
		attackDefault = i;
	}

	public boolean playerGONE() {
		System.out.println(defending_Territory.getOwner().getTerritories()
				.size());
		return defending_Territory.getOwner().getTerritories().size() == 0;
	}

	public void theCardMess() {
		System.out.println("Entered the Card Mess");
		System.out.println(playerGONE());
		if (playerGONE()) {

			attackingPlayer.playersHand().getHandFromLosingPlayer(
					defendingPlayer.playersHand().getCurrentDeck());

			System.out.println("Your new Deck size is: "
					+ attackingPlayer.playersHand().getCurrentDeck().size());
			System.out.println(attackingPlayer.playersHand().getCurrentDeck());
			System.out.println("WON some Cards");
			int total = 0;
			if (attackingPlayer.playersHand().getCurrentDeck().size() >= 6) {
				System.out.println("Ummm... ");
				System.out.println("... ");
				System.out.println("... ");
				System.out.println("your deck is too big.");
				System.out.println();
				while (attackingPlayer.playersHand().getCurrentDeck().size() > 4) {
					System.out
							.println("============================================");
					System.out.println(attackingPlayer.playersHand()
							.getCurrentDeck());
					System.out.println("There have been: "
							+ attackingPlayer.playersHand().getTurnIns()
							+ " here before you.");

					total = attackingPlayer.playersHand().turnIn(
							attackingPlayer.playersHand().getCurrentDeck()
									.remove(0),
							attackingPlayer.playersHand().getCurrentDeck()
									.remove(0),
							attackingPlayer.playersHand().getCurrentDeck()
									.remove(0))
							+ total;
				}
				total += attackingPlayer.getAttackDefault();
				System.out.println(total);
				new Reinforce(total, attackingPlayer);
			}
		}
	}
}
*/