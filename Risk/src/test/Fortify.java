/*package test;

import java.util.Scanner;

public class Fortify {
	private Territory from;
	private Territory to;

	public Fortify(Territory from_territory, Territory to_territory) {
		to = to_territory;
		from = from_territory;
		System.out.println("to Owner: "+to.getOwner());
		System.out.println("From Owner: "+from.getOwner());
		System.out.println("adjacentTerritories: "+Board.getBoard().getAllies(from_territory));
		if (Board.getBoard().getAllies(from_territory).contains(to_territory)) {
			fortify();
			System.out.println("Your fortify just happened");
		}
		else{
			System.out.println("Not your territory or not adjacent.");
		}
	}

	private void fortify() {
		boolean continueToFortify = true;
		Scanner scan = new Scanner(System.in);
		while (continueToFortify && from.getNumDice() > 1) {
			from.adjustDice(-1);
			System.out.println("The number of dice now in from Territory: "+from.getNumDice());
			to.adjustDice(1);
			System.out.println("The number of dice now in to Territory: "+to.getNumDice());
			if (from.getNumDice() > 1) {
				System.out
						.println("Continue to fortify your position? (true/false) ");
				continueToFortify = scan.nextBoolean();
			} else {
				break;
			}
		}
	}

}
*/