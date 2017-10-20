package model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Super Kewl Team
 * 
 * Assigned to: Steven, Andrew
 */

public class EasyAI extends Player implements AI {
	public EasyAI(int number, AI ai) {
		super(number, ai);
		this.setAttackDefault(3);
		this.setDefenseDefault(2);
	}

	public EasyAI() {
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

	public void calcDefaultDice(Territory t) {
		if (t.getNumDice() == 1) {
			this.setDefenseDefault(1);
		} else {
			this.setDefenseDefault(2);
			this.setAttackDefault(3);
			if (t.getNumDice() < 3) {
				if (t.getNumDice() < 2) {
					this.setAttackDefault(1);
				} else {
					this.setAttackDefault(2);
				}
			}
		}
	}

	// this method will go through a bunch of cases to determine if it is going
	// to attack, and proceeds to fight
	public void attack(Player attacker) {
		Territory defending = checkAreaForEnemy(attacker);
		attacker.setAttackTerritory(defending);
		if (attacker instanceof EasyAI || attacker instanceof HardAI) {
			for (int i = 0; i < attacker.getTerritories().size(); i++) {
				calcDefaultDice(attacker.getTerritories().get(i));
				if (Board.board.getAdjEnemies(defending).contains(
						attacker.getTerritories().get(i))) {
					attacker.setFromTerritory(attacker.getTerritories().get(i));
					break;
				}
			}
			if (attacker.getFromTerritory().getNumDice() > 1
					&& defending.getNumDice() > 0) {
				Attack attack = new Attack(attacker.getFromTerritory(),
						defending);
				attack.setAttackDice(2);
				Game.game.update(attack);
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			battleAI(attacker);

		}
	}

	// private void battleAI(Player attacker) {
	// for (int i = 0; i < attacker.getTerritories().size(); i++) {
	// for (int j = 0; j < Board.board.getAdjEnemies(
	// attacker.getTerritories().get(i)).size(); j++) {
	//
	// Iterator<Territory> attackerTerritories = attacker
	// .getTerritories().iterator();
	//
	// while (attackerTerritories.hasNext()) {
	//
	// Territory baseTerritory = attackerTerritories.next();
	//
	// Iterator<Territory> targets = Board.getBoard()
	// .getAdjEnemies(baseTerritory).iterator();
	//
	// while (targets.hasNext()) {
	//
	// Territory target = targets.next();
	// calcDefaultDice(baseTerritory);
	//
	// while (target.getOwner() != attacker
	// && baseTerritory.getNumDice() > 1
	// && (target.getOwner() instanceof EasyAI || target
	// .getOwner() instanceof HardAI)) {
	// Attack attack = new Attack(baseTerritory, target);
	//
	// attack.fightRound();
	//
	// }
	//
	// }
	// }
	// }
	// }
	// }

	private void battleAI(Player attacker) {
		for (int i = 0; i < attacker.getTerritories().size(); i++) {
			for (int j = 0; j < Board.board.getAdjEnemies(
					attacker.getTerritories().get(i)).size(); j++) {
				calcDefaultDice(attacker.getTerritories().get(i));
				while (attacker.getTerritories().get(i).getNumDice() > 1) {
					Territory defend = Board.board.getAdjEnemies(
							attacker.getTerritories().get(i)).get(j);
					Attack attack = new Attack(
							attacker.getTerritories().get(i), defend);
					if (defend.getOwner() instanceof HardAI
							|| defend.getOwner() instanceof EasyAI) {
						attack.fightRound();
					}
				}
			}
		}
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
		// Territory from = fortifier.getTerritories().get(
		// (int) (Math.random() * fortifier.getTerritories().size() + 1));
		// Territory to = Board.getBoard().getAllies(from)
		// .get(
		// (int) (Math.random()
		// * Board.getBoard().getAllies(from).size() + 1));
		// to.adjustDice(from.getNumDice() - 1);
		// from.adjustDice(-(from.getNumDice() - 1));
	}
}
