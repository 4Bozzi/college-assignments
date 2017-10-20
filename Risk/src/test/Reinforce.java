package test;

import java.util.Scanner;

import model.Bonus;
import model.Player;
import model.Territory;

/**
 * 
 * @author Super Kewl Team
 * 
 *         This is the class which takes care of the Reinforcement stage. It is
 *         called by the GUI.
 * 
 *         methods contained:Reinforce(Territory, Territory) Reinforce(Player,
 *         Territory) Reinforce(int, Player) setToBoard(int, Player)
 *         setToBoard()
 * 
 */
public class Reinforce {
	private int number_of_reinforcements;
	private Player player;

	/**
	 * 
	 * @param from_territory
	 * @param to_territory
	 * 
	 *            This constructor takes in two Territories for args and
	 *            initializes instance variables. It also has checks to make
	 *            sure that you can Reinforce the selected territory.
	 */
	public Reinforce(Territory from_territory, Territory to_territory) {
		player = from_territory.getOwner();
		Bonus bonus = new Bonus();
		bonus.getBonus(player);
		if (from_territory.getOwner() == to_territory.getOwner()
				&& from_territory.getAdjacentTerritories().contains(
						to_territory)) {
			setToBoard();
		} else {
			System.out.println("Not your territory.");
		}
	}
/**
 * 
 * @param player
 * @param territory
 * 
 * just another possible constructor just in case the other one cannot be used.
 */
	public Reinforce(Player player, Territory territory) {
		if (territory.getOwner() == player && player.getReinforcements() >= 1) {
			territory.adjustDice(1);
			player.decrementReinforcements();
		}

	}
/**
 * 
 * @param total
 * @param player
 * 
 * One last overloaded constructor
 */
	public Reinforce(int total, Player player) {
		number_of_reinforcements = total;
		System.out.println("number of Reinforcements: " + total);
		setToBoard(total, player);
	}
/**
 * 
 * @param total
 * @param player
 * 
 * This method 
 */
	private void setToBoard(int total, Player player) {
		Scanner scan = new Scanner(System.in);
		System.out.println("where do you want to send you reinforcements? ");

		Territory territory = player.getTerritories().get(scan.nextInt());
		while (number_of_reinforcements > 0) {
			if (player.getTerritories().contains(territory)) {
				player.setToTerritory(territory);
			}
			System.out
					.println("where do you want to send you reinforcements? ");
			territory = player.getTerritories().get(scan.nextInt());
			number_of_reinforcements--;
			System.out.println("Number of reinforcements left: "
					+ number_of_reinforcements);
		}
	}

	private void setToBoard() {
		Scanner scan = new Scanner(System.in);
		System.out.println("where do you want to send you reinforcements? ");
		int territory = scan.nextInt();
		while (number_of_reinforcements > 0) {
			if (player.getTerritories().contains(territory)) {
				player.setToTerritory(player.getTerritories().get(territory));
			}
			System.out
					.println("where do you want to send you reinforcements? ");
			territory = scan.nextInt();
			number_of_reinforcements--;
			System.out.println("Number of reinforcements left: "
					+ number_of_reinforcements);
		}
	}
}
