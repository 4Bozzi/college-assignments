/*package test;

import java.util.ArrayList;
import java.util.List;

import model.Player;

*//**
 * 
 * @author Super Kewl Team
 * 
 *         This class keeps track of the stages in each turn and is pretty much
 *         the nuts and bolts of this program as far as game play. The primary
 *         purpose is making sure each active player goes through each stage of
 *         a turn and each turn is completed. So this means there are no major
 *         actions directly contained in the this class but accessed through the
 *         getStage Method.
 * 
 *         Methods contained: getStage(), setStage()
 * 
 *//*
public class Turn {
	private int currentStage;
	private Player player;

	public Turn(Player player) {
		currentStage = 0;
		this.player = player;
		new Stage(true);
	}

	public void nextStage(boolean value) {
		if (currentStage < 2) {
			currentStage++;
			new Stage(value);
			if (!value) {
				System.out.println("skipped a phase!");
			}
		}

		else {
			System.out.println("\nNeed to Start a new Turn");
		}
	}

	*//**
	 * @param the
	 *            int value of the next stage must be <=3
	 * @return the int value of the next stage
	 *//*
	public int getStage() {
		return currentStage;
	}

	*//**
	 * 
	 * @author Super Kewl Team This Class controls the action which is to take
	 *         place in each stage Though for now there is only one method, but
	 *         there maybe several helper classes or stage specific methods that
	 *         will be called in the action method
	 * 
	 *         methods contained: action()
	 *//*
	private class Stage {
		private ArrayList<String> stages = new ArrayList<String>(3);

		public Stage(boolean value) {
			stages.add(0, "reinforce");
			stages.add(1, "attack");
			stages.add(2, "fortify");
			action(value);
		}

		*//**
		 * 
		 * @param stage
		 *            stage<3 && stage>0
		 * 
		 *            This determines the actions that are made in each stage of
		 *            a turn
		 *//*
		public void action(boolean value) {
			System.out.println("Stage Number: " + getStage());
			if (stages.get(getStage()).equals("reinforce") && value) {
				player.reinforce();
				System.out.println("REINFORCE!");
			}
			if (stages.get(getStage()).equals("attack") && value) {
				player.attack();
				System.out.println("ATTACK!");
			}
			if (stages.get(getStage()).equals("fortify") && value) {
				player.fortify();
				System.out.println("FORTIFY!");
			}
		}
	}

}
*/